package com.liu.snmp;

import com.liu.ber.Decoder;
import com.liu.ber.Encoder;
import com.liu.ber.impl.DecoderImpl;
import com.liu.ber.impl.EncoderImpl;
import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.Trap;
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
    public void sendRequestTest() {
        SnmpUtil snmpUtil = new SnmpUtilImpl();
        VariableBindings var1 = new VariableBindings("11.1.0", 2, "1");
        PDU pdu = new PDU(0, "11", var1);
        SnmpMessage snmp = new SnmpMessage(0, "xust", pdu);
        snmpUtil.startSnmpService(snmp, "127.0.0.1");
    }

    @Test
    public void test() {
        Decoder decoder = new DecoderImpl();
        byte[] data = {(byte) 0xa4, 0x21, 0x06, 0x0c, 0x2b
                , 0x06, 0x01, 0x04, 0x01, (byte) 0x82, 0x37, 0x01, 0x01, 0x03, 0x01, 0x01, 0x40, 0x04, 0x7f, 0x00, 0x00
                , 0x01, 0x02, 0x01, 0x04, 0x02, 0x01, 0x00, 0x43, 0x03, 0x04, 0x0a, (byte) 0xdc, 0x30, 0x00};
        Trap trap = new Trap();
        decoder.getTrap(data, trap);
        System.out.println(trap);
    }
}

