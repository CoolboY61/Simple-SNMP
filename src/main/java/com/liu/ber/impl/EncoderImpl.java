package com.liu.ber.impl;

import com.liu.ber.Encoder;
import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.pdu.type.PduType;
import com.liu.pdu.type.ValueType;
import com.liu.util.Util;

/**
 * BER编码
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 17:05
 */
public class EncoderImpl implements Encoder {
    @Override
    public byte[] getSnmpMessageCoding(SnmpMessage snmpMessage) {
        PDU pdu = (PDU) snmpMessage.getSnmpPdu();
        VariableBindings var = (VariableBindings) pdu.getVariableBindings();


        //  一、编码VariableBindings
        //  1、编码OID
        //      ① 编码"V"
        String[] strTemp = var.getObjectId().split("\\.");
        byte[] chief = new byte[strTemp.length - 1];
        chief[0] = 43;
        chief[1] = 6;
        chief[2] = 1;
        chief[3] = 2;
        chief[4] = 1;
        for (int i = 5; i < chief.length; i++) {
            //  将字符转换为字节
            chief[i] = Byte.parseByte(strTemp[i + 1]);
        }
        //      ② 编码"L"
        byte[] lByte = Util.intToBytes(chief.length);
        //      ③ 编码"T"
        byte[] tByte = Util.intToBytes(6);
        tByte = Util.byteMerger(tByte, lByte);
        //      ④ 合并"TLV"
        chief = Util.byteMerger(tByte, chief);
        //  2、编码Value
        //  Value类型为NULL
        if (ValueType.NULL.equals(var.getValueType())) {
            //  编码"TL"
            byte[] tlByte = {5, 0};
            //  合并
            chief = Util.byteMerger(chief, tlByte);

            //  Value类型为INTEGER
        } else if (ValueType.INTEGER.equals(var.getValueType())) {
            //  ① 编码"V"
            byte[] vByte = Util.intToBytes(Integer.parseInt(var.getValue()));
            //  ② 编码"TL"
            byte[] tlByte = {ValueType.getTypeByte(var.getValueType()), (byte) vByte.length};
            tlByte = Util.byteMerger(tlByte, vByte);
            //  合并
            chief = Util.byteMerger(chief, tlByte);

            //  Value类型为OCTET STRING
        } else if (ValueType.OCTET_STRING.equals(var.getValueType())) {
            //  ① 编码"V"
            char[] temp = var.getValue().toCharArray();
            byte[] vByte = new byte[temp.length];
            for (int i = 0; i < vByte.length; i++) {
                vByte[i] = (byte) temp[i];
            }
            //  ② 编码"TL"
            byte[] tlByte = {ValueType.getTypeByte(var.getValueType()), (byte) vByte.length};
            tlByte = Util.byteMerger(tlByte, vByte);
            //  合并
            chief = Util.byteMerger(chief, tlByte);
        }
        //  3、合并Var的整体TLV
        byte[] tlByte = {48, (byte) chief.length};
        chief = Util.byteMerger(tlByte, chief);
        tlByte = new byte[]{48, (byte) chief.length};
        chief = Util.byteMerger(tlByte, chief);


        //  二、编码PDU
        //  1、编码Error status和Error index
        //      编码"TLV"
        byte[] tlvByte = {2, 1, 0, 2, 1, 0};
        chief = Util.byteMerger(tlvByte, chief);
        //  2、编码Request ID
        //      ① 编码"V"
        byte[] vByte = {Byte.parseByte(pdu.getRequestId())};
        //      ② 编码"TL"
        tlByte = new byte[]{2, (byte) vByte.length};
        tlByte = Util.byteMerger(tlByte, vByte);
        //      ③ 合并"TLV"
        chief = Util.byteMerger(tlByte, chief);
        //  3、编码PDU type的TL
        //      ① 编码"T"
        tByte = new byte[0];
        if (PduType.TYPE[0].equals(pdu.getPduType())) {
            tByte = Util.intToBytes(160);
        } else if (PduType.TYPE[1].equals(pdu.getPduType())) {
            tByte = Util.intToBytes(161);
        } else if (PduType.TYPE[3].equals(pdu.getPduType())) {
            tByte = Util.intToBytes(163);
        }
        //      ② 编码"L"
        lByte = new byte[]{(byte) chief.length};
        tByte = Util.byteMerger(tByte, lByte);
        //  4、合并
        chief = Util.byteMerger(tByte, chief);


        //  三、编码SNMP Message头部
        //  1、编码Community
        //      ① 编码"V"
        char[] temp = snmpMessage.getCommunity().toCharArray();
        vByte = new byte[temp.length];
        for (int i = 0; i < vByte.length; i++) {
            vByte[i] = (byte) temp[i];
        }
        //      ② 编码"L"
        lByte = Util.intToBytes(vByte.length);
        lByte = Util.byteMerger(lByte, vByte);
        //      ③ 编码"T"
        tByte = Util.intToBytes(4);
        tByte = Util.byteMerger(tByte, lByte);
        //      ④ 合并"TLV"
        chief = Util.byteMerger(tByte, chief);
        //  2、编码Version
        //      ① 编码"V"
        vByte = Util.intToBytes(snmpMessage.getVersionId());
        //      ② 编码"L"
        lByte = Util.intToBytes(vByte.length);
        lByte = Util.byteMerger(lByte, vByte);
        //      ③ 编码"T"
        tByte = Util.intToBytes(2);
        tByte = Util.byteMerger(tByte, lByte);
        //      ④ 合并"TLV"
        chief = Util.byteMerger(tByte, chief);


        //  四、编码整体TLV
        //  1、 编码"L"
        lByte = Util.intToBytes(chief.length);
        //  2、 编码"T"
        tByte = Util.intToBytes(48);
        tByte = Util.byteMerger(tByte, lByte);
        //  3、 合并"TLV"
        chief = Util.byteMerger(tByte, chief);

        return chief;
    }
}
