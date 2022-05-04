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
        VariableBindings variableBindings = new VariableBindings("2.1.11.1", 5, null);
        PDU PDU = new PDU(0, "1", variableBindings);
        SnmpMessage snmpMessage = new SnmpMessage(0, "xust", PDU);
        System.out.println(snmpMessage);
    }

    @Test
    public void CreatGetNextPdu() {
        VariableBindings variableBindings = new VariableBindings("2.1.13.2", 4, "aaaa");
        PDU PDU = new PDU(1, "2", variableBindings);
        SnmpMessage snmpMessage = new SnmpMessage(0, "student", PDU);
        System.out.println(snmpMessage);
    }

    @Test
    public void CreatSetPdu() {
        VariableBindings variableBindings = new VariableBindings("2.4.17.50", 2, "100");
        PDU PDU = new PDU(3, "3", variableBindings);
        SnmpMessage snmpMessage = new SnmpMessage(0, "public", PDU);
        System.out.println(snmpMessage);
    }

    @Test
    public void CreatResponsePdu() {
        VariableBindings variableBindings = new VariableBindings("4.10.17.112", 5, null);
        PDU pdu = new PDU(2, "10", 5, "100", variableBindings);
        SnmpMessage snmpMessage = new SnmpMessage(0, "310", pdu);
        System.out.println(snmpMessage);
    }

}
