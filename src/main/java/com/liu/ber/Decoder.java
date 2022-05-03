package com.liu.ber;

import com.liu.pdu.SnmpMessage;

/**
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 18:12
 */
public interface Decoder {
    /**
     * 解码数据包，得到Snmp Message头部
     *
     * @param snmpData 数据包
     * @return 返回解码得到的Snmp Message头部
     */
    SnmpMessage getSnmpMessage(byte[] snmpData);

}
