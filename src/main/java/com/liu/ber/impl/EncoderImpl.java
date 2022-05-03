package com.liu.ber.impl;

import com.liu.ber.Encoder;
import com.liu.pdu.GGSPdu;
import com.liu.pdu.ResponsePdu;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * BER编码
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 17:05
 */
public class EncoderImpl implements Encoder {
    private final Map<String, Byte> universal = new HashMap<>();

    @Override
    public byte[] getVariableBindingsCoding(VariableBindings var) {
        universal.put("INTEGER", (byte) 2);
        universal.put("OCTET STRING", (byte) 4);
        universal.put("OBJECT IDENTIFIER", (byte) 6);
        universal.put("NULL", (byte) 5);


        //  一、第一步编码OID的TLV
        //  1、编码“V”
        String[] strTemp = var.getObjectId().split("\\.");
        byte[] chief = new byte[strTemp.length - 1];
        chief[0] = 43;
        chief[1] = 6;
        chief[2] = 1;
        for (int i = 3; i < chief.length; i++) {
            //  将字符转换为字节
            chief[i] = Byte.parseByte(strTemp[i + 1]);
        }
        //  2、编码“T,L”
        byte[] tByte = Util.intToBytes(6);
        byte[] lByte = Util.intToBytes(chief.length);
        tByte = Util.byteMerger(tByte, lByte);
        //  3、拼接TLV
        chief = Util.byteMerger(tByte, chief);


        //  二、第二部编码value的TLV
        //  1、编码“V”0
        //      value为NULL型
        if ("NULL".equals(var.getValueType())) {
            byte[] lvByte = {5, 0};
            chief = Util.byteMerger(chief, lvByte);

            //  value为INTEGER型
        } else if ("INTEGER".equals(var.getValueType())) {
            byte[] vByte = Util.intToBytes(Integer.parseInt(var.getValue()));
            //  2、编码"TL"
            byte[] tlByte = {universal.get(var.getValueType()), (byte) vByte.length};
            tlByte = Util.byteMerger(tlByte, vByte);
            //  3、拼接“TLV”
            chief = Util.byteMerger(chief, tlByte);

            //  value为OCTET STRING型
        } else if ("OCTET STRING".equals(var.getValueType())) {
            char[] temp = var.getValue().toCharArray();
            byte[] vByte = new byte[temp.length];
            for (int i = 0; i < vByte.length; i++) {
                vByte[i] = (byte) temp[i];
            }
            //  2、编码"TL"
            byte[] tlByte = {universal.get(var.getValueType()), (byte) vByte.length};
            tlByte = Util.byteMerger(tlByte, vByte);
            //  3、拼接“TLV”
            chief = Util.byteMerger(chief, tlByte);
        }


        //  三、第三步合并整体TLV
        byte[] tlByte = {48, (byte) chief.length};
        chief = Util.byteMerger(tlByte, chief);
        return chief;
    }

    @Override
    public byte[] getGgsPduCoding(GGSPdu pdu, byte[] varByte) {
        //  一、第一步编码var的TLV
        //  1、编码TL
        byte[] tlByte = {48, (byte) varByte.length};
        //  2、合并TLV
        byte[] chief = Util.byteMerger(tlByte, varByte);

        //  二、第二步编码Error status和Error index的TLV
        //  1、编码TLV
        byte[] temp = {2, 1, 0, 2, 1, 0};
        //  2、合并
        chief = Util.byteMerger(temp, chief);

        //  三、第三步编码Request ID的TLV
        //  1、编码V
        byte[] vByte = {Byte.parseByte(pdu.getRequestId())};
        //  2、编码TLV
        tlByte = new byte[]{2, (byte) vByte.length};
        tlByte = Util.byteMerger(tlByte, vByte);
        chief = Util.byteMerger(tlByte, chief);

        //  四、第四步编码PDU type的TL
        byte[] tByte = new byte[0];
        if ("get-request".equals(pdu.getPduType())) {
            tByte = Util.intToBytes(160);
        } else if ("get-next-request".equals(pdu.getPduType())) {
            tByte = Util.intToBytes(161);
        } else if ("set-request".equals(pdu.getPduType())) {
            tByte = Util.intToBytes(163);
        }
        byte[] lByte = new byte[]{(byte) chief.length};

        //  五、合并
        tByte = Util.byteMerger(tByte, lByte);
        chief = Util.byteMerger(tByte, chief);

        return chief;
    }

    @Override
    public byte[] getResponsePduCoding(ResponsePdu pdu, byte[] varByte) {
        //  一、第一步编码var的TLV
        //  1、编码TL
        byte[] tlByte = {48, (byte) varByte.length};
        //  2、合并TLV
        byte[] chief = Util.byteMerger(tlByte, varByte);

        //  二、第二步编码Error status和Error index的TLV
        //  1、编码Error index的TLV
        byte[] vByte = Util.intToBytes(Integer.parseInt(pdu.getErrorIndex()));
        byte[] lByte = Util.intToBytes(vByte.length);
        lByte = Util.byteMerger(lByte, vByte);
        byte[] tByte = Util.intToBytes(2);
        tByte = Util.byteMerger(tByte, lByte);
        chief = Util.byteMerger(tByte, chief);
        //  2、编码Error status的TLV
        vByte = Util.intToBytes(Integer.parseInt(pdu.getErrorStatusId()));
        lByte = Util.intToBytes(vByte.length);
        lByte = Util.byteMerger(lByte, vByte);
        tByte = Util.intToBytes(2);
        tByte = Util.byteMerger(tByte, lByte);
        chief = Util.byteMerger(tByte, chief);

        //  三、第三步编码Request ID的TLV
        //  1、编码V
        vByte = Util.intToBytes(Integer.parseInt(pdu.getRequestId()));
        //  2、编码L
        lByte = Util.intToBytes(vByte.length);
        lByte = Util.byteMerger(lByte, vByte);
        //  3、编码T
        tByte = Util.intToBytes(2);
        tByte = Util.byteMerger(tByte, lByte);
        chief = Util.byteMerger(tByte, chief);

        //  四、第四步编码PDU type的TL
        tByte = Util.intToBytes(162);
        lByte = Util.intToBytes(chief.length);
        tlByte = Util.byteMerger(tByte, lByte);

        //  五、合并
        chief = Util.byteMerger(tlByte, chief);
        return chief;
    }

    @Override
    public byte[] getSnmpMessageCoding(SnmpMessage snmpMessage, byte[] pduByte) {

        //  一、第一步编码Community的TLV
        //  1、编码V
        char[] temp = snmpMessage.getCommunity().toCharArray();
        byte[] vByte = new byte[temp.length];
        for (int i = 0; i < vByte.length; i++) {
            vByte[i] = (byte) temp[i];
        }
        //  2、编码L
        byte[] lByte = Util.intToBytes(vByte.length);
        lByte = Util.byteMerger(lByte, vByte);
        //  3、编码T
        byte[] tByte = Util.intToBytes(4);
        tByte = Util.byteMerger(tByte, lByte);
        //  4、合并
        byte[] chief = Util.byteMerger(tByte, pduByte);

        //  二、第二步编码Version的TLV
        //  1、编码V
        vByte = Util.intToBytes(Integer.parseInt(snmpMessage.getVersionId()));
        //  2、编码L
        lByte = Util.intToBytes(vByte.length);
        lByte = Util.byteMerger(lByte, vByte);
        //  3、编码T
        tByte = Util.intToBytes(2);
        tByte = Util.byteMerger(tByte, lByte);
        //  4、合并
        chief = Util.byteMerger(tByte, chief);

        //  三、编写整体TLV
        //  1、编写L
        lByte = Util.intToBytes(chief.length);
        //  2、编写T
        tByte = Util.intToBytes(48);
        //  3、合并
        tByte = Util.byteMerger(tByte, lByte);
        chief = Util.byteMerger(tByte, chief);

        return chief;
    }

    @Override
    public byte[] getCoding(SnmpMessage snmpMessage, String type) {
        byte[] varByte;
        byte[] pduByte;
        byte[] snmpByte = new byte[0];
        VariableBindings var;
        if ("Get-Request".equals(type) || "Get-Next-Request".equals(type) || "Set-Request".equals(type)) {
            GGSPdu pdu = (GGSPdu) snmpMessage.getSnmpPdu();
            var = (VariableBindings) pdu.getVariableBindings();
            varByte = getVariableBindingsCoding(var);
            pduByte = getGgsPduCoding(pdu, varByte);
            snmpByte = getSnmpMessageCoding(snmpMessage, pduByte);
        } else if ("Get-Response".equals(type)) {
            ResponsePdu pdu = (ResponsePdu) snmpMessage.getSnmpPdu();
            var = (VariableBindings) pdu.getVariableBindings();
            varByte = getVariableBindingsCoding(var);
            pduByte = getResponsePduCoding(pdu, varByte);
            snmpByte = getSnmpMessageCoding(snmpMessage, pduByte);
        }
        return snmpByte;
    }

}
