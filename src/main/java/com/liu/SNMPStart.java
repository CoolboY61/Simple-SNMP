package com.liu;

import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.snmp.GGSServer;
import com.liu.util.Util;

import java.util.Scanner;

/**
 * SNMP服务启动器
 *
 * @author : LiuYi
 * @version : 2.0
 * @date : 2022/5/3 10:15
 */
public class SNMPStart {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean state = true;
        System.out.println("请填写SNMP Message相关配置：");
        System.out.print("请求的目的IP地址：      ");
        String iP = sc.nextLine();
        System.out.print("团体名(Community)：    ");
        String community = sc.nextLine();
        while (state) {
            state = SNMPStart.view(iP, community);
        }
    }

    public static boolean view(String iP, String community) {

        VariableBindings var = new VariableBindings();
        PDU pdu = new PDU();
        SnmpMessage snmp = new SnmpMessage();


        System.out.println("-------------------- SNMP配置选项 --------------------");
        System.out.println("Version：    0：version-1");
        System.out.println("PDU Type：   0：get-request         1：get-next-request  3：set-request");
        System.out.println("Value Type： 1：BOOLEAN             2：INTEGER       4：OCTET STRING    5：NULL\n" +
                "             6：OBJECT_IDENTIFIER  64：IPADDRESS    65：COUNTER        67：TIMETICKS");
        System.out.println("请按照提示填写，选择SNMP Message相关配置：");

        System.out.println("版本号(Version)：      0");
        snmp.setVersion(0);
        snmp.setCommunity(community);

        System.out.print("请求类型(ResponsePdu type)：   ");
        int type = sc.nextInt();
        pdu.setPduType(type);
        String num = Util.getNum();
        System.out.print("请求标识(Request ID)： " + num + "\n");
        pdu.setRequestId(num);
        sc.nextLine();
        System.out.print("OID：         1.3.6.1.");
        var.setObjectId(sc.nextLine());
        if (type == 3) {
            System.out.print("Value Type：          ");
            int valueType = sc.nextInt();
            var.setValueType(valueType);
            if (valueType == 1) {
                System.out.println("BOOLEAN：    1：True         2：False");
                System.out.print("Value：               ");
                var.setValue(1 == sc.nextInt() ? "True" : "False");
            } else if (valueType != 5) {
                sc.nextLine();
                System.out.print("Value：               ");
                var.setValue(sc.nextLine());
            } else {
                System.out.println("Value：               NULL");
            }
        } else {
            System.out.println("Value Type：          NULL");
            System.out.println("Value：               NULL");
        }
        pdu.setVariableBindings(var);
        snmp.setSnmpPdu(pdu);
        GGSServer snmpServer = new GGSServer(snmp, iP);
        snmpServer.run();

        System.out.println("提示： 1-继续   0-退出");
        if (sc.nextInt() == 0) {
            sc.nextLine();
            System.out.println("\n\n\n");
            return false;
        }
        sc.nextLine();
        System.out.println("\n\n\n");
        return true;
    }
}
