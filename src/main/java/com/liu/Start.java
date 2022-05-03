package com.liu;

import com.liu.pdu.GGSPdu;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.snmp.SnmpUtil;
import com.liu.snmp.impl.SnmpUtilImpl;
import com.liu.util.Util;

import java.util.Scanner;

/**
 * @author : LiuYi
 * @version :
 * @date : 2022/5/3 10:15
 *
 */
public class Start {
    public static Scanner sc = new Scanner(System.in);
    public static SnmpUtil snmpUtil = new SnmpUtilImpl();

    public static void main(String[] args) {
        boolean state = true;
        while (state) {
            state = Start.view();
        }
    }

    public static boolean view() {
        VariableBindings var = new VariableBindings();
        GGSPdu pdu = new GGSPdu();
        SnmpMessage snmp = new SnmpMessage();

        System.out.println("-------------------- SNMP配置选项 --------------------");
        System.out.println("Version：    0：version-1");
        System.out.println("PDU Type：   0：get-request  1：get-next-request  3：set-request");
        System.out.println("Value Type： 2：INTEGER      4：OCTET STRING      5：NULL");
        System.out.println("请按照提示填写，选择SNMP Message相关配置：");

        System.out.print("请求的目的IP地址：      ");
        String iP = sc.nextLine();

        System.out.println("版本号(Version)：      0");
        snmp.setVersion(0);
        System.out.print("团体名(Community)：    ");
        snmp.setCommunity(sc.nextLine());

        System.out.print("请求类型(PDU type)：   ");
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
            var.setValueType(sc.nextInt());
            System.out.print("Value：               ");
            var.setValue(sc.nextLine());
            pdu.setVariableBindings(var);
            snmp.setSnmpPdu(pdu);
            snmpUtil.sendSetRequest(snmp, iP);
        } else if (type == 0) {
            System.out.println("Value Type：          NULL");
            System.out.println("Value：               NULL");
            var.setValueType(5);
            var.setValue(null);
            pdu.setVariableBindings(var);
            snmp.setSnmpPdu(pdu);
            snmpUtil.sendGetRequest(snmp, iP);
        } else if (type == 1) {
            System.out.println("Value Type：          NULL");
            System.out.println("Value：               NULL");
            var.setValueType(5);
            var.setValue(null);
            pdu.setVariableBindings(var);
            snmp.setSnmpPdu(pdu);
            snmpUtil.sendGetNextRequest(snmp, iP);
        }

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
