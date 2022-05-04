package com.liu.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/**
 * UDPSocket操作类
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/5/2 20:01
 *
 */
public class SocketUtil {

    /**
     * UDP Socket
     */
    public static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用UDP协议发送SNMP请求，同时接收SNMP响应
     *
     * @param snmpData1 需发送的snmp数据
     * @param iP        目的IP地址
     * @return 返回接收到的响应
     */
    public static byte[] snmpServe(byte[] snmpData1, String iP) {
        byte[] snmpData2 = new byte[0];
        byte[] snmpDataTemp;
        try {
            int port = 161;
            InetAddress address = InetAddress.getByName(iP);
            DatagramPacket packet = new DatagramPacket(snmpData1, snmpData1.length, address, port);
            socket.setReuseAddress(true);
            socket.send(packet);
            byte[] bytes = new byte[1472];
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
            socket.setSoTimeout(1000);
            socket.receive(dp);
            snmpData2 = new byte[dp.getLength()];
            snmpDataTemp = dp.getData();
            System.arraycopy(snmpDataTemp, 0, snmpData2, 0, dp.getLength());
        } catch (Exception e) {
            System.out.println("\n等待响应超时！！！\n");
            e.printStackTrace();
        }
        return snmpData2;
    }
}
