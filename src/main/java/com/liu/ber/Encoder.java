package com.liu.ber;

import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;

/**
 * BER编码
 *
 * @author : LiuYi
 * @version : 2.0
 * @date : 2022/4/30 18:11
 */
public interface Encoder {

    /**
     * 编码SNMP数据包
     *
     * @param snmpMessage SNMP数据包
     * @return BER编码后的数据
     */
    byte[] getSnmpCoding(SnmpMessage snmpMessage);

    /**
     * 获取VariableBindings区的BER编码
     *
     * @param var 未编码Var数据
     * @return Var的编码
     */
    byte[] getVarCoding(VariableBindings var);

    /**
     * 获取PDU区的BER编码
     *
     * @param var var的编码数据
     * @param pdu 未编码的PDU数据
     * @return PDU整体的编码
     */
    byte[] getPduCoding(byte[] var, PDU pdu);

    /**
     * 获取SNMP区的BER代码
     *
     * @param pdu pdu的编码数据
     * @param snmp 未编码的SNMP数据
     * @return SNMP整体的编码
     */
    byte[] getSnmpMessageCoding(byte[] pdu, SnmpMessage snmp);


    /**
     * 编码ValueType为BOOLEAN的数据
     *
     * @param var VariableBindings数据
     * @return 返回BOOLEAN类型的数据编码
     */
    byte[] getBooleanCoding(VariableBindings var);

    /**
     * 编码ValueType为INTEGER的数据
     *
     * @param var VariableBindings数据
     * @return 返回INTEGER类型的数据编码
     */
    byte[] getIntegerCoding(VariableBindings var);

    /**
     * 编码ValueType为OCTET_STRING的数据
     *
     * @param var VariableBindings数据
     * @return 返回OCTET_STRING类型的数据编码
     */
    byte[] getStringCoding(VariableBindings var);

    /**
     * 编码ValueType为NULL的数据
     *
     * @return 返回NULL类型的数据编码
     */
    byte[] getNullCoding();

    /**
     * 编码ValueType为OBJECT_IDENTIFIER的数据
     *
     * @param var VariableBindings数据
     * @return 返回OBJECT_IDENTIFIER类型的数据编码
     */
    byte[] getOIDCoding(VariableBindings var);

    /**
     * 编码ValueType为IPADDRESS的数据
     *
     * @param var VariableBindings数据
     * @return 返回IPADDRESS类型的数据编码
     */
    byte[] getIpAddressCoding(VariableBindings var);

    /**
     * 编码ValueType为COUNTER的数据
     *
     * @param var VariableBindings数据
     * @return 返回COUNTER类型的数据编码
     */
    byte[] getCounterCoding(VariableBindings var);

    /**
     * 编码ValueType为TIMETICKS的数据
     *
     * @param var VariableBindings数据
     * @return 返回TIMETICKS类型的数据编码
     */
    byte[] getTimeTicksCoding(VariableBindings var);


}
