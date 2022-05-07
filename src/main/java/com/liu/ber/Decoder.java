package com.liu.ber;

import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.Trap;
import com.liu.pdu.VariableBindings;

/**
 * BER解码
 *
 * @author : LiuYi
 * @version : 2.0
 * @date : 2022/4/30 18:12
 */
public interface Decoder {

    /**
     * 解码数据包，得到SNMP整体
     *
     * @param data 数据包
     * @return 解码得到的SNMP
     *
     */
    SnmpMessage getSnmpMessage(byte[] data);

    /**
     * 解码数据包，得到SNMP头部
     *
     * @param snmpData 数据包
     * @param snmpMessage SNMP
     * @return 剩余未解码数据
     */
    byte[] getSnmp(byte[] snmpData, SnmpMessage snmpMessage);

    /**
     * 解码数据包，得到PDU头部
     *
     * @param pduData PDU数据包
     * @param pdu PDU
     * @return 剩余未解码数据
     */
    byte[] getPDU(byte[] pduData, PDU pdu);

    /**
     * 解码数据包，得到Trap
     *
     * @param trapData Trap数据包
     * @param trap Trap
     * @return 剩余未解码数据
     */
    void getTrap(byte[] trapData, Trap trap);

    /**
     * 解码数据包，得到Var数据
     *
     * @param varData VariableBindings数据
     * @param var VariableBindings
     */
    void getVar(byte[] varData, VariableBindings var);


    /**
     * 解码ValueType为BOOLEAN的数据
     *
     * @param data 数据
     * @return 返回解码后的BOOLEAN类型数据
     */
    String getBoolean(byte[] data);

    /**
     * 解码ValueType为INTEGER的数据
     *
     * @param data 数据
     * @return 返回解码后的INTEGER类型数据
     */
    String getInteger(byte[] data);

    /**
     * 解码ValueType为OCTET_STRING的数据
     *
     * @param data 数据
     * @return 返回解码后的OCTET_STRING类型数据
     */
    String getString(byte[] data);

    /**
     * 解码ValueType为NULL的数据
     *
     * @param data 数据
     * @return 返回解码后的NULL类型数据
     */
    String getNull(byte[] data);

    /**
     * 解码ValueType为OBJECT_IDENTIFIER的数据
     *
     * @param data 数据
     * @return 返回解码后的OBJECT_IDENTIFIER类型数据
     */
    String getOID(byte[] data);

    /**
     * 解码ValueType为SEQUENCE的数据
     *
     * @param data 数据
     * @return 返回解码后的SEQUENCE类型数据
     */
    String getSequence(byte[] data);

    /**
     * 解码ValueType为IPADDRESS的数据
     *
     * @param data 数据
     * @return 返回解码后的IPADDRESS类型数据
     */
    String getIpAddress(byte[] data);

    /**
     * 解码ValueType为COUNTER的数据
     *
     * @param data 数据
     * @return 返回解码后的COUNTER类型数据
     */
    String getCounter(byte[] data);

    /**
     * 解码ValueType为TIMETICKS的数据
     *
     * @param data 数据
     * @return 返回解码后的TIMETICKS类型数据
     */
    String getTimeTicks(byte[] data);




}
