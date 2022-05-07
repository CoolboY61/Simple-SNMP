package com.liu.snmp;

import com.liu.pdu.SnmpMessage;

/**
 * SNMP操作类
 *
 * @author : LiuYi
 * @version : 1.4
 * @date : 2022/5/2 18:57
 */
public interface SnmpUtil {
    /**
     * 发送SNMP 请求
     *
     * @param snmp 需发送的SNMP请求
     * @param iP 目的IP
     */
    void startSnmpService(SnmpMessage snmp, String iP);
}
