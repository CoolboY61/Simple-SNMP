package com.liu.ber.impl;

import com.liu.ber.Decoder;
import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.Trap;
import com.liu.pdu.VariableBindings;
import com.liu.pdu.type.ValueType;
import com.liu.util.Util;


/**
 * BER解码
 *
 * @author : LiuYi
 * @version : 1.4
 * @date : 2022/4/30 17:05
 */
public class DecoderImpl implements Decoder {

    @Override
    public SnmpMessage getSnmpMessage(byte[] snmpData) {
        SnmpMessage snmpMessage = new SnmpMessage();
        PDU pdu = new PDU();
        Trap trap = new Trap();
        VariableBindings var = new VariableBindings();

        //  一、第一步解码SNMP的Version、Community
        byte[] pduData = getSnmp(snmpData, snmpMessage);
        byte[] varData = null;
        if ((pduData[0] & 0xff) == 162) {
            //  二、第二步解码PDU的Request ID、Error status、Error index
            varData = getPDU(pduData, pdu);
            //  三、第三步解码Variable bindings的Name、Value
            getVar(varData, var);
        } else if ((pduData[0] & 0xff) == 164) {
            getTrap(pduData, trap);
        }
        //  合并
        pdu.setVariableBindings(var);
        snmpMessage.setSnmpPdu(pdu);
        return snmpMessage;
    }

    @Override
    public byte[] getSnmp(byte[] snmpData, SnmpMessage snmpMessage) {
        //  一、第一步解码SNMP的Version、Community
        //  1、获取Version
        int version = snmpData[4] & 0xff;
        snmpMessage.setVersion(version);
        //  2、获取Community
        int communityLength = snmpData[6] & 0xff;
        byte[] communityData = new byte[communityLength];
        System.arraycopy(snmpData, 7, communityData, 0, communityLength);
        snmpMessage.setCommunity(new String(communityData));

        //  获取数据包整体长度
        int length = (snmpData[1] & 0xff) + 2;
        byte[] pduData = new byte[length - 7 - communityLength];
        //  截取PDU部分数据，继续解码
        System.arraycopy(snmpData, 7 + communityLength, pduData, 0, pduData.length);
        return pduData;
    }

    @Override
    public byte[] getPDU(byte[] pduData, PDU pdu) {
        //  二、第二步解码PDU的Request ID、Error status、Error index
        //  1、获取Request ID
        int requestId = pduData[4] & 0xff;
        pdu.setRequestId(String.valueOf(requestId));

        //  2、获取Error status
        int errorStatus = pduData[7] & 0xff;
        pdu.setErrorStatus(errorStatus);

        //  3、获取Error index
        int errorIndex = pduData[10] & 0xff;
        pdu.setErrorIndex(String.valueOf(errorIndex));

        //  截取Variable bindings部分数据，继续解码
        byte[] varData = new byte[pduData.length - 11];
        System.arraycopy(pduData, 11, varData, 0, varData.length);
        return varData;
    }

    @Override
    public void getTrap(byte[] trapData, Trap trap) {
        //  二、解码Trap的Enterprise、Agent addr、Generic trap、Specific trap、Time stamp、Variable bindings
        //  1、获取Enterprise
        int oidLength = trapData[3] & 0xff;
        byte[] oidData = new byte[oidLength];
        System.arraycopy(trapData, 4, oidData, 0, oidLength);
        trap.setEnterprise(getOID(oidData));

        byte[] tempData = new byte[trapData.length - (4 + oidLength)];
        System.arraycopy(trapData, 4 + oidLength, tempData, 0, tempData.length);
        //  2、获取Agent addr
        int agentAddrLength = tempData[1] & 0xff;
        byte[] agentAddrData = new byte[agentAddrLength];
        System.arraycopy(tempData, 2, agentAddrData, 0, agentAddrLength);
        trap.setAgentAddr(getIpAddress(agentAddrData));
        //  3、获取Generic trap和Specific trap
        byte[] tempData1 = new byte[tempData.length - (2 + agentAddrLength)];
        System.arraycopy(tempData, 2 + agentAddrLength, tempData1, 0, tempData1.length);

        int genericTrap = tempData1[2] & 0xff;
        trap.setGenericTrap(genericTrap);
        int specificTrap = tempData1[5] & 0xff;
        trap.setSpecificTrap(String.valueOf(specificTrap));

        byte[] tempData2 = new byte[tempData1.length - 6];
        System.arraycopy(tempData1, 6, tempData2, 0, tempData2.length);
        //  4、获取Time stamp
        int timeStampLength = tempData2[1] & 0xff;
        byte[] timeStampData = new byte[timeStampLength];
        System.arraycopy(tempData2, 2, timeStampData, 0, timeStampLength);
        trap.setTimeStamp(getTimeTicks(timeStampData));

        trap.setVariableBindings(null);

    }

    @Override
    public void getVar(byte[] varData, VariableBindings var) {
        //  三、第三步解码Variable bindings的Name、Value
        byte[] varDataTemp = new byte[varData.length];
        System.arraycopy(varData, 0, varDataTemp, 0, varData.length);
        varData = new byte[varDataTemp.length - 4];
        System.arraycopy(varDataTemp, 4, varData, 0, varData.length);
        //  1、获取Name
        int nameLength = varData[1] & 0xff;
        byte[] nameData = new byte[nameLength - 3];
        System.arraycopy(varData, 5, nameData, 0, nameData.length);
        var.setObjectId(getOID(nameData));

        //  2、获取Value
        int valueLength = varData.length - (2 + nameLength);
        byte[] valueData = new byte[valueLength];
        System.arraycopy(varData, (2 + nameLength), valueData, 0, valueLength);

        int typeNum = valueData[0] & 0xff;
        String type = ValueType.getTypeByNum(typeNum);
        int dataLength = valueData[1] & 0xff;
        byte[] data = new byte[dataLength];
        System.arraycopy(valueData, 2, data, 0, dataLength);

        switch (type) {
            case ValueType.BOOLEAN:
                var.setValueType(typeNum);
                var.setValue(getBoolean(data));
                break;
            case ValueType.INTEGER:
                var.setValueType(typeNum);
                var.setValue(getInteger(data));
                break;
            case ValueType.OCTET_STRING:
                var.setValueType(typeNum);
                var.setValue(getString(data));
                break;
            case ValueType.NULL:
                var.setValueType(typeNum);
                var.setValue(getNull(data));
                break;
            case ValueType.OBJECT_IDENTIFIER:
                var.setValueType(typeNum);
                var.setValue(getOID(data));
                break;
            case ValueType.SEQUENCE:
                var.setValueType(typeNum);
                var.setValue(getSequence(data));
                break;
            case ValueType.IPADDRESS:
                var.setValueType(typeNum);
                var.setValue(getIpAddress(data));
                break;
            case ValueType.COUNTER:
                var.setValueType(typeNum);
                var.setValue(getCounter(data));
                break;
            case ValueType.TIMETICKS:
                var.setValueType(typeNum);
                var.setValue(getTimeTicks(data));
                break;
            default:
                break;
        }

    }


    @Override
    public String getBoolean(byte[] data) {
        return Util.bytesToInt(data) == 0 ? ValueType.FALSE : ValueType.TRUE;
    }

    @Override
    public String getInteger(byte[] data) {
        return String.valueOf(Util.bytesToInt(data));
    }

    @Override
    public String getString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        char[] temp = Util.byteToChar(data);
        for (char c : temp) {
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String getNull(byte[] data) {
        return "null";
    }

    @Override
    public String getOID(byte[] data) {
        //和0与会被清0，和1与会被保持
        //和1或会被置1，和0或会被保持
        StringBuilder sb = new StringBuilder();
        sb.append("1.3.");
        for (int i = 1; i < data.length; i++) {
            if (0 == (data[i] & 0x80)) {
                sb.append((data[i] & 0x7f) & 0xff);
                if (i != (data.length - 1)) {
                    sb.append('.');
                }
            } else {
                byte[] temp = new byte[2];
                int last = (data[i] & 0x01) << 7;
                temp[1] = (byte) (data[i + 1] | last);
                temp[0] = (byte) (data[i] & 0x7f);
                temp[0] = (byte) (temp[0] >>> 1);
                sb.append(Util.bytesToInt(temp));
                if (i != (data.length - 1)) {
                    sb.append('.');
                }
                i++;
            }
        }
        return sb.toString();
    }

    @Override
    public String getSequence(byte[] data) {
        return "null";
    }

    @Override
    public String getIpAddress(byte[] data) {
        StringBuilder sb = new StringBuilder();
        int[] temp = Util.byteToDec(data);
        for (int i = 0; i < temp.length; i++) {
            sb.append(temp[i]);
            if (i < 3) {
                sb.append('.');
            }
        }
        return sb.toString();
    }

    @Override
    public String getCounter(byte[] data) {
        return String.valueOf(Util.bytesToInt(data));
    }

    @Override
    public String getTimeTicks(byte[] data) {
        return String.valueOf(Util.bytesToInt(data));
    }


}
