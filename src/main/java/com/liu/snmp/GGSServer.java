package com.liu.snmp;

import com.liu.ber.Decoder;
import com.liu.ber.Encoder;
import com.liu.ber.impl.DecoderImpl;
import com.liu.ber.impl.EncoderImpl;
import com.liu.pdu.SnmpMessage;
import com.liu.util.Util;

import java.net.*;

/**
 * SNMP服务
 *
 * @author : LiuYi
 * @version : 1.4
 * @date : 2022/5/2 19:04
 */
public class GGSServer {

    /**
     * UDP Socket
     */
    public static DatagramSocket socket;
    SnmpMessage snmp;
    byte[] bytes = new byte[1472];
    DatagramPacket dp;
    InetAddress address;
    Encoder encoder = new EncoderImpl();
    Decoder decoder = new DecoderImpl();

    static {
        try {
            socket = new DatagramSocket(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public GGSServer(SnmpMessage snmp, String iP) {
        try {
            this.address = InetAddress.getByName(iP);
            this.snmp = snmp;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        byte[] snmpData2 = null;
        byte[] snmpDataTemp;
        try {

            byte[] snmpData = encoder.getSnmpCoding(snmp);
            System.out.println("Send SNMP Message:");
            System.out.println(snmp);
            Util.showPacket(snmpData);

            dp = new DatagramPacket(snmpData, snmpData.length, address, 161);
            socket.setReuseAddress(true);
            socket.send(dp);

            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
            socket.setSoTimeout(1000);
            socket.receive(dp);
            snmpData2 = new byte[dp.getLength()];
            snmpDataTemp = dp.getData();
            System.arraycopy(snmpDataTemp, 0, snmpData2, 0, dp.getLength());

            System.out.println("Receive SNMP Response:");
            SnmpMessage response = decoder.getSnmpMessage(snmpData2);
            System.out.println(response);
            Util.showPacket(snmpData2);
        } catch (Exception e) {
            System.out.println("\n等待GGS响应超时！！！\n");
        }
    }
}
