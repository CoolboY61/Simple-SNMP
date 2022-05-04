package com.liu.snmp.impl;

import com.liu.ber.Decoder;
import com.liu.ber.Encoder;
import com.liu.ber.impl.DecoderImpl;
import com.liu.ber.impl.EncoderImpl;
import com.liu.pdu.SnmpMessage;
import com.liu.snmp.SnmpUtil;
import com.liu.util.SocketUtil;
import com.liu.util.Util;

/**
 * @author : LiuYi
 * @version :
 * @date : 2022/5/2 19:04
 */
public class SnmpUtilImpl implements SnmpUtil {
    @Override
    public void sendRequest(SnmpMessage snmp, String iP) {
        Encoder encoder = new EncoderImpl();
        Decoder decoder = new DecoderImpl();
        byte[] snmpData = encoder.getSnmpMessageCoding(snmp);
        System.out.println("Send SNMP Message:");
        System.out.println(snmp);
        Util.showPacket(snmpData);
        byte[] responseData = SocketUtil.snmpServe(snmpData, iP);
        System.out.println("Receive SNMP Response:");
        SnmpMessage response = decoder.getSnmpMessage(responseData);
        System.out.println(response);
        Util.showPacket(responseData);
    }
}
