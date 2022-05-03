package com.liu.snmp;

import com.liu.pdu.GGSPdu;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.snmp.impl.SnmpUtilImpl;
import org.junit.Test;

/**
 * @author : LiuYi
 * @version :
 * @date : 2022/5/2 19:45
 */
public class SnmpUtilTest {

    @Test
    public void sendGetRequestTest() {
        VariableBindings var = new VariableBindings("2.1.11.1.0", 5, null);
        GGSPdu ggsPdu = new GGSPdu(0, "1", var);
        SnmpMessage snmpMessage = new SnmpMessage(0, "xust", ggsPdu);
        SnmpUtil snmpUtil = new SnmpUtilImpl();
        snmpUtil.sendGetRequest(snmpMessage, "127.0.0.1");
    }

    @Test
    public void sendGetNextRequestTest() {
        VariableBindings var = new VariableBindings("2.6.1.11", 5, null);
        GGSPdu ggsPdu = new GGSPdu(1, "2", var);
        SnmpMessage snmpMessage = new SnmpMessage(0, "xust", ggsPdu);
        SnmpUtil snmpUtil = new SnmpUtilImpl();
        snmpUtil.sendGetNextRequest(snmpMessage, "10.100.68.231");
    }

    @Test
    public void sendSetRequestTest() {
        VariableBindings var = new VariableBindings("2.1.11.31", 4, "aaa");
        GGSPdu ggsPdu = new GGSPdu(3, "3", var);
        SnmpMessage snmpMessage = new SnmpMessage(0, "xust", ggsPdu);
        SnmpUtil snmpUtil = new SnmpUtilImpl();
        snmpUtil.sendSetRequest(snmpMessage, "10.100.68.231");
    }

    @Test
    public void receiveSnmpResponseTest() {
        SnmpUtil snmpUtil = new SnmpUtilImpl();
        SnmpMessage snmpMessage = snmpUtil.receiveSnmpResponse();
    }
}
