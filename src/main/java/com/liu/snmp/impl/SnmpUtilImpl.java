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
    public byte[] sendGetRequest(SnmpMessage snmpMessage, String iP) {
        Encoder encoder = new EncoderImpl();
        Decoder decoder = new DecoderImpl();
        byte[] snmpData = encoder.getCoding(snmpMessage, "Get-Request");
        System.out.println("Send SNMP Message:");
        System.out.println(snmpMessage);
        Util.showPacket(snmpData);
        byte[] responseData = SocketUtil.snmpServe(snmpData, iP);
        System.out.println("Receive SNMP Response:");
        SnmpMessage response = decoder.getSnmpMessage(responseData);
        System.out.println(response);
        Util.showPacket(responseData);
        return responseData;
    }

    @Override
    public byte[] sendGetNextRequest(SnmpMessage snmpMessage, String iP) {
        Encoder encoder = new EncoderImpl();
        Decoder decoder = new DecoderImpl();
        byte[] snmpData = encoder.getCoding(snmpMessage, "Get-Next-Request");
        System.out.println("Send SNMP Message:");
        System.out.println(snmpMessage);
        Util.showPacket(snmpData);
        byte[] responseData = SocketUtil.snmpServe(snmpData, iP);
        System.out.println("Receive SNMP Response:");
        SnmpMessage response = decoder.getSnmpMessage(responseData);
        System.out.println(response);
        Util.showPacket(responseData);
        return responseData;

    }

    @Override
    public byte[] sendSetRequest(SnmpMessage snmpMessage, String iP) {
        Encoder encoder = new EncoderImpl();
        Decoder decoder = new DecoderImpl();
        byte[] snmpData = encoder.getCoding(snmpMessage, "Set-Request");
        System.out.println("Send SNMP Message:");
        System.out.println(snmpMessage);
        Util.showPacket(snmpData);
        byte[] responseData = SocketUtil.snmpServe(snmpData, iP);
        System.out.println("Receive SNMP Response:");
        SnmpMessage response = decoder.getSnmpMessage(responseData);
        System.out.println(response);
        Util.showPacket(responseData);
        return responseData;
    }

    @Override
    public SnmpMessage receiveSnmpResponse() {
        Decoder decoder = new DecoderImpl();
        byte[] snmpData = SocketUtil.receiveSnmp();
        Util.showPacket(snmpData);
        SnmpMessage snmpMessage = decoder.getSnmpMessage(snmpData);
        return snmpMessage;
    }
}
