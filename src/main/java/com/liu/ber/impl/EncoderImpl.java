package com.liu.ber.impl;

import com.liu.ber.Encoder;
import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.pdu.type.Type;
import com.liu.pdu.type.ValueType;
import com.liu.util.Util;


/**
 * BER编码
 *
 * @author : LiuYi
 * @version : 2.0
 * @date : 2022/4/30 17:05
 */
public class EncoderImpl implements Encoder {

    @Override
    public byte[] getSnmpCoding(SnmpMessage snmpMessage) {
        PDU pdu = (PDU) snmpMessage.getSnmpPdu();
        VariableBindings var = (VariableBindings) pdu.getVariableBindings();
        byte[] chief = getVarCoding(var);
        chief = getPduCoding(chief, pdu);
        chief = getSnmpMessageCoding(chief, snmpMessage);
        return chief;
    }

    @Override
    public byte[] getVarCoding(VariableBindings var) {
        //  一、编码VariableBindings
        //  1、编码OID
        //      ① 编码"V"
        byte[] chief = getOIDCoding(var);
        //  2、编码Value
        switch (var.getValueType()) {
            case ValueType.BOOLEAN:
                chief = Util.byteMerger(chief, getBooleanCoding(var));
                break;
            case ValueType.INTEGER:
                chief = Util.byteMerger(chief, getIntegerCoding(var));
                break;
            case ValueType.OCTET_STRING:
                chief = Util.byteMerger(chief, getStringCoding(var));
                break;
            case ValueType.NULL:
                chief = Util.byteMerger(chief, getNullCoding());
                break;
            case ValueType.OBJECT_IDENTIFIER:
                chief = Util.byteMerger(chief, getOIDCoding(var));
                break;
            case ValueType.COUNTER:
                chief = Util.byteMerger(chief, getCounterCoding(var));
                break;
            case ValueType.IPADDRESS:
                chief = Util.byteMerger(chief, getIpAddressCoding(var));
                break;
            case ValueType.TIMETICKS:
                chief = Util.byteMerger(chief, getTimeTicksCoding(var));
                break;
            default:
                break;
        }

        //  3、合并Var的整体TLV
        byte[] tlByte = {ValueType.getTypeByte(ValueType.SEQUENCE), (byte) chief.length};
        chief = Util.byteMerger(tlByte, chief);
        tlByte = new byte[]{ValueType.getTypeByte(ValueType.SEQUENCE), (byte) chief.length};
        chief = Util.byteMerger(tlByte, chief);

        return chief;
    }

    @Override
    public byte[] getPduCoding(byte[] var, PDU pdu) {
        byte[] chief = var;

        //  二、编码PDU
        //  1、编码Error status和Error index
        //      编码"TLV"
        byte[] tlvByte = {2, 1, 0, 2, 1, 0};
        chief = Util.byteMerger(tlvByte, chief);
        //  2、编码Request ID
        //      ① 编码"V"
        byte[] vByte = {Byte.parseByte(pdu.getRequestId())};
        //      ② 编码"TL"
        byte[] tlByte = new byte[]{ValueType.getTypeByte(ValueType.INTEGER), (byte) vByte.length};
        tlByte = Util.byteMerger(tlByte, vByte);
        //      ③ 合并"TLV"
        chief = Util.byteMerger(tlByte, chief);
        //  3、编码PDU type的TL
        //      ① 编码"T"
        byte[] tByte = new byte[0];
        if (Type.PDUTYPE[0].equals(pdu.getPduType())) {
            tByte = Util.intToBytes(160);
        } else if (Type.PDUTYPE[1].equals(pdu.getPduType())) {
            tByte = Util.intToBytes(161);
        } else if (Type.PDUTYPE[3].equals(pdu.getPduType())) {
            tByte = Util.intToBytes(163);
        }
        //      ② 编码"L"
        byte[] lByte = new byte[]{(byte) chief.length};
        tByte = Util.byteMerger(tByte, lByte);
        //  4、合并
        chief = Util.byteMerger(tByte, chief);
        return chief;
    }

    @Override
    public byte[] getSnmpMessageCoding(byte[] pdu, SnmpMessage snmp) {
        byte[] chief = pdu;
        //  三、编码SNMP Message头部
        //  1、编码Community
        //      ① 编码"V"
        char[] temp = snmp.getCommunity().toCharArray();
        byte[] vByte = new byte[temp.length];
        for (int i = 0; i < vByte.length; i++) {
            vByte[i] = (byte) temp[i];
        }
        //      ② 编码"L"
        byte[] lByte = Util.intToBytes(vByte.length);
        lByte = Util.byteMerger(lByte, vByte);
        //      ③ 编码"T"
        byte[] tByte = Util.intToBytes(4);
        tByte = Util.byteMerger(tByte, lByte);
        //      ④ 合并"TLV"
        chief = Util.byteMerger(tByte, chief);
        //  2、编码Version
        //      ① 编码"V"
        vByte = Util.intToBytes(snmp.getVersionId());
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

    @Override
    public byte[] getBooleanCoding(VariableBindings var) {
        //  编码"TLV"
        if (var.getValue().equals(ValueType.TRUE)) {
            return new byte[]{1, 1, (byte) 255};
        } else {
            return new byte[]{1, 1, 0};
        }
    }

    @Override
    public byte[] getIntegerCoding(VariableBindings var) {
        //  编码"V"
        byte[] vByte = Util.intToBytes(Integer.parseInt(var.getValue()));
        //  编码"TL"
        byte[] tlByte = {ValueType.getTypeByte(var.getValueType()), (byte) vByte.length};
        //  合并"TLV"
        return Util.byteMerger(tlByte, vByte);
    }

    @Override
    public byte[] getStringCoding(VariableBindings var) {
        //  编码"V"
        char[] temp = var.getValue().toCharArray();
        byte[] vByte = new byte[temp.length];
        for (int i = 0; i < vByte.length; i++) {
            vByte[i] = (byte) temp[i];
        }
        //  编码"TL"
        byte[] tlByte = {ValueType.getTypeByte(var.getValueType()), (byte) vByte.length};
        //  合并"TLV"
        return Util.byteMerger(tlByte, vByte);
    }

    @Override
    public byte[] getNullCoding() {
        //  编码"TLV"
        return new byte[]{5, 0};
    }

    @Override
    public byte[] getOIDCoding(VariableBindings var) {
        //  编码OID
        //  ① 编码"V"
        String[] strTemp = var.getObjectId().split("\\.");
        int length = strTemp.length - 1;
        int[] temp = new int[strTemp.length - 4];

        for (int i = 0; i < strTemp.length - 4; i++) {
            temp[i] = Integer.parseInt(strTemp[i + 4]);
            if (temp[i] > 127 && temp[i] <= 16383) {
                length++;
            } else if (temp[i] > 16383) {
                length += 2;
            }
        }

        byte[] chief = new byte[length];
        chief[0] = (byte) 43;
        chief[1] = (byte) 6;
        chief[2] = (byte) 1;
        byte[] data;
        for (int i = 3, j = 0; j < temp.length; j++) {
            if (temp[j] <= 127) {
                chief[i] = (byte) (temp[j]);
                i++;
            } else if (temp[j] <= 16383) {
                //和0与会被清0，和1与会被保持
                //和1或会被置1，和0或会被保持
                if (temp[j] <= 255) {
                    data = new byte[]{0, (byte) temp[j]};
                } else {
                    data = Util.intToBytes(temp[j]);
                }
                data[0] = (byte) (data[0] << 1);
                data[0] = (byte) (data[0] | 0x80);
                if ((data[1] & 0x80) == 128) {
                    data[0] = (byte) (data[0] | 0x01);
                    data[1] = (byte) (data[1] & 0x7f);
                } else {
                    data[1] = (byte) (data[1] & 0x7f);
                }
                System.arraycopy(data,0,chief,i,data.length);
                i += data.length;
            }
        }
        //   ② 编码"L"
        byte[] lByte = Util.intToBytes(chief.length);
        //   ③ 编码"T"
        byte[] tByte = Util.intToBytes(6);
        tByte = Util.byteMerger(tByte, lByte);
        chief = Util.byteMerger(tByte, chief);

        return chief;

    }

    @Override
    public byte[] getIpAddressCoding(VariableBindings var) {
        //  编码"V"
        String[] strTemp = var.getValue().split("\\.");
        byte[] vByte = new byte[strTemp.length];
        for (int i = 0; i < vByte.length; i++) {
            //  将字符转换为字节
            vByte[i] = (byte) Integer.parseInt(strTemp[i]);
        }
        //  编码"TL"
        byte[] tlByte = {ValueType.getTypeByte(var.getValueType()), (byte) vByte.length};
        //  合并"TLV"
        return Util.byteMerger(tlByte, vByte);
    }

    @Override
    public byte[] getCounterCoding(VariableBindings var) {
        //  编码"V"
        byte[] vByte = Util.intToBytes(Integer.parseInt(var.getValue()));
        //  编码"TL"
        byte[] tlByte = {ValueType.getTypeByte(var.getValueType()), (byte) vByte.length};
        //  合并"TLV"
        return Util.byteMerger(tlByte, vByte);
    }

    @Override
    public byte[] getTimeTicksCoding(VariableBindings var) {
        //  编码"V"
        byte[] vByte = Util.intToBytes(Integer.parseInt(var.getValue()));
        //  编码"TL"
        byte[] tlByte = {ValueType.getTypeByte(var.getValueType()), (byte) vByte.length};
        //  合并"TLV"
        return Util.byteMerger(tlByte, vByte);
    }

}
