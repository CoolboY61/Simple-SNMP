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

    public static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用UDP发送SNMP请求
     *
     * @param snmpData SNMP数据包的编码格式
     */
    public static void sendSnmp(byte[] snmpData, String iP) {
        try {
            int port = 161;
            InetAddress address = InetAddress.getByName(iP);
            DatagramPacket packet = new DatagramPacket(snmpData, snmpData.length, address, port);
            socket.setReuseAddress(true);
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用UDP接收SNMP响应
     *
     * @return 返回接收的字节数组（数据包）
     */
    public static byte[] receiveSnmp() {
        byte[] snmpData = new byte[0];
        byte[] snmpDataTemp;
        try {
            byte[] bytes = new byte[1472];
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
            socket.receive(dp);
            socket.close();
            snmpData = new byte[dp.getLength()];
            snmpDataTemp = dp.getData();
            System.arraycopy(snmpDataTemp, 0, snmpData, 0, dp.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(snmpData));
        return snmpData;
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
