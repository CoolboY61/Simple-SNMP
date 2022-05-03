package com.liu.snmp;

import com.liu.pdu.SnmpMessage;

/**
 * SNMP操作类
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/5/2 18:57
 */
public interface SnmpUtil {
    /**
     * 发送SNMP Get请求
     *
     * @param snmpMessage 需要发送的SNMP Message
     * @param iP          目标IP地址
     * @return 返回接收到的响应
     */
    byte[] sendGetRequest(SnmpMessage snmpMessage, String iP);

    /**
     * 发送SNMP Get-Next请求
     *
     * @param snmpMessage 需要发送的SNMP Message
     * @param iP          目标IP地址
     * @return 返回接收到的响应
     */
    byte[] sendGetNextRequest(SnmpMessage snmpMessage, String iP);

    /**
     * 发送SNMP Set请求
     *
     * @param snmpMessage 需要发送的SNMP Message
     * @param iP          目标IP地址
     * @return 返回接收到的响应
     */
    byte[] sendSetRequest(SnmpMessage snmpMessage, String iP);

    /**
     * 接收SNMP Response响应并解析
     *
     * @return 返回接收到的SNMP数据包
     */
    SnmpMessage receiveSnmpResponse();
}
