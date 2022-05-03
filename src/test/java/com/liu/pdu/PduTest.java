package com.liu.pdu;

import org.junit.Test;

/**
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 14:59
 * 创建PDU进行测试
 */
public class PduTest {

    @Test
    public void CreatGetPdu() {
        VariableBindings variableBindings = new VariableBindings("2.1.11.1", 2, "13");
        GGSPdu GGSPdu = new GGSPdu(0, "1", variableBindings);
        SnmpMessage snmpMessage = new SnmpMessage(0, "xust", GGSPdu);
        System.out.println(snmpMessage);
    }

    @Test
    public void CreatGetNextPdu() {
        VariableBindings variableBindings = new VariableBindings("2.1.13.2", 4, "aaaa");
        GGSPdu GGSPdu = new GGSPdu(1, "2", variableBindings);
        SnmpMessage snmpMessage = new SnmpMessage(0, "student", GGSPdu);
        System.out.println(snmpMessage);
    }

    @Test
    public void CreatSetPdu() {
        VariableBindings variableBindings = new VariableBindings("2.4.17.50", 2, "100");
        GGSPdu GGSPdu = new GGSPdu(3, "3", variableBindings);
        SnmpMessage snmpMessage = new SnmpMessage(0, "public", GGSPdu);
        System.out.println(snmpMessage);
    }

    @Test
    public void CreatResponsePdu() {
        VariableBindings variableBindings = new VariableBindings("4.10.17.112", 5, null);
        ResponsePdu responsePdu = new ResponsePdu("4", 0, "0", variableBindings);
        SnmpMessage snmpMessage = new SnmpMessage(0, "310", responsePdu);
        System.out.println(snmpMessage);
    }

}
