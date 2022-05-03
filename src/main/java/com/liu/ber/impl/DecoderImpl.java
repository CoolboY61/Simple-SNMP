package com.liu.ber.impl;

import com.liu.ber.Decoder;
import com.liu.pdu.ResponsePdu;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.util.Util;


/**
 * BER解码
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 17:05
 *
 */
public class DecoderImpl implements Decoder {

    @Override
    public SnmpMessage getSnmpMessage(byte[] snmpData) {
        SnmpMessage snmpMessage = new SnmpMessage();
        ResponsePdu responsePdu = new ResponsePdu();
        VariableBindings var = new VariableBindings();


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


        //  二、第二步解码PDU的Request ID、Error status、Error index
        //  1、获取Request ID
        int requestId = pduData[4] & 0xff;
        responsePdu.setRequestId(String.valueOf(requestId));

        //  2、获取Error status
        int errorStatus = pduData[7] & 0xff;
        responsePdu.setErrorStatus(errorStatus);

        //  3、获取Error index
        int errorIndex = pduData[10] & 0xff;
        responsePdu.setErrorIndex(String.valueOf(errorIndex));

        //  截取Variable bindings部分数据，继续解码
        byte[] varData = new byte[pduData.length - 11];
        System.arraycopy(pduData, 11, varData, 0, varData.length);

        //  三、第三步解码Variable bindings的Name、Value
        byte[] varDataTemp = new byte[varData.length];
        System.arraycopy(varData, 0, varDataTemp, 0, varData.length);
        varData = new byte[varDataTemp.length - 4];
        System.arraycopy(varDataTemp, 4, varData, 0, varData.length);

        //  1、获取Name
        int nameLength = varData[1] & 0xff;
        byte[] nameData = new byte[nameLength - 3];
        System.arraycopy(varData, 5, nameData, 0, nameData.length);
        StringBuilder sb = new StringBuilder();
        //  拼接OID
        for (int i = 0; i < nameData.length; i++) {
            sb.append(nameData[i] & 0xff);
            if (i != (nameData.length - 1)) {
                sb.append('.');
            }
        }
        var.setObjectId(sb.toString());

        //  2、获取Value
        int valueLength = varData.length - (2 + nameLength);
        byte[] valueData = new byte[valueLength];
        System.arraycopy(varData, (2 + nameLength), valueData, 0, valueLength);

        int type = valueData[0] & 0xff;
        var.setValueType(type);
        int dataLength = valueData[1] & 0xff;
        byte[] data = new byte[dataLength];
        System.arraycopy(valueData, 2, data, 0, dataLength);

        if (type == 2) {
            //  value为"INTEGER"型
            int value = Util.bytesToInt(data);
            var.setValue(String.valueOf(value));
        } else if (type == 4) {
            //  value为"OCTET STRING"型
            char[] temp = Util.byteToChar(data);
            sb = new StringBuilder();
            for (char c : temp) {
                sb.append(temp);
            }
            var.setValue(sb.toString());
        } else if (type == 5) {
            //  value为"NULL"型
            var.setValueType(5);
            var.setValue(null);
        }


        //  合并
        responsePdu.setVariableBindings(var);
        snmpMessage.setSnmpPdu(responsePdu);
        return snmpMessage;
    }
}
