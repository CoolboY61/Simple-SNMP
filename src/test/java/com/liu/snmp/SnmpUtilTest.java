package com.liu.snmp;

import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.snmp.impl.SnmpUtilImpl;
import com.liu.util.Util;
import org.junit.Test;

import java.util.Arrays;


/**
 * @author : LiuYi
 * @version :
 * @date : 2022/5/2 19:45
 */
public class SnmpUtilTest {

    @Test
    public void sendRequestTest() {
        SnmpUtil snmpUtil = new SnmpUtilImpl();
        VariableBindings var1 = new VariableBindings("11.1.0", 2, "1");
        PDU pdu = new PDU(0,"11",var1);
        SnmpMessage snmp = new SnmpMessage(0,"xust",pdu);
        snmpUtil.sendRequest(snmp,"127.0.0.1");
    }
}
