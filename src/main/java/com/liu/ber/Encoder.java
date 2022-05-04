package com.liu.ber;

import com.liu.pdu.SnmpMessage;

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
     * 编码SNMP数据包
     *
     * @param snmpMessage SNMP数据包
     * @return BER编码后的数据
     */
    byte[] getSnmpMessageCoding(SnmpMessage snmpMessage);
}
