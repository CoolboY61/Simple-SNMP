package com.liu.snmp;

import com.liu.ber.Decoder;
import com.liu.ber.impl.DecoderImpl;
import com.liu.pdu.SnmpMessage;
import com.liu.util.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Trap服务
 *
 * @author : LiuYi
 * @version : 1.4
 * @date : 2022/5/7 22:36
 */
public class TrapServer implements Runnable{
    DatagramSocket socket;
    byte[] data = new byte[1472];
    DatagramPacket dp;
    Decoder decoder = new DecoderImpl();

    public TrapServer() {
        try {
            this.socket = new DatagramSocket(162);
            this.dp = new DatagramPacket(data, data.length);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        byte[] snmpData;
        byte[] snmpDataTemp;
        while (true) {
            try {
                socket.receive(dp);
                snmpData = new byte[dp.getLength()];
                snmpDataTemp = dp.getData();
                System.arraycopy(snmpDataTemp, 0, snmpData, 0, dp.getLength());
                System.out.println("Receive SNMP Trap:");
                SnmpMessage response = decoder.getSnmpMessage(snmpData);
                System.out.println(response);
                Util.showPacket(snmpData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
