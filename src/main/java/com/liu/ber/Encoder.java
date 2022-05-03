package com.liu.ber;

import com.liu.pdu.GGSPdu;
import com.liu.pdu.ResponsePdu;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;

/**
 * BER编码
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 18:11
 *
 */
public interface Encoder {
    /**
     * 对VariableBindings部分进行编码
     *
     * @param var VariableBindings
     * @return 返回编码后的二进制数组
     */
    byte[] getVariableBindingsCoding(VariableBindings var);

    /**
     * 对GGSPdu部分进行编码
     *
     * @param pdu     GGSPdu
     * @param varByte var部分的编码
     * @return 返回编码后的二进制数组
     */
    byte[] getGgsPduCoding(GGSPdu pdu, byte[] varByte);

    /**
     * 对ResponsePdu部分进行编码
     *
     * @param pdu     ResponsePDU
     * @param varByte var部分的编码
     * @return 返回编码后的二进制数组
     */
    byte[] getResponsePduCoding(ResponsePdu pdu, byte[] varByte);

    /**
     * 对整体SNMP message进行编码
     *
     * @param snmpMessage SNMP message
     * @param pduByte     PDU部分的编码
     * @return 返回编码后的二进制数组
     */
    byte[] getSnmpMessageCoding(SnmpMessage snmpMessage, byte[] pduByte);

    /**
     * 根据请求类型，获得整体的编码
     *
     * @param snmpMessage SNMP数据包
     * @param type        SNMP数据包的类型
     * @return 返回整体编码
     */
    byte[] getCoding(SnmpMessage snmpMessage, String type);
}
