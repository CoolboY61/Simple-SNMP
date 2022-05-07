package com.liu.ber;

import com.liu.ber.impl.EncoderImpl;
import com.liu.pdu.PDU;
import com.liu.pdu.SnmpMessage;
import com.liu.pdu.VariableBindings;
import com.liu.util.Util;
import org.junit.Test;


/**
 * BER编码测试
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 17:06
 */
public class EncodeTest {
    public Encoder encoder = new EncoderImpl();

    @Test
    public void getSnmpMessageCodingTest() {
        VariableBindings var1 = new VariableBindings("11.31.0", 2, "1");
        PDU pdu = new PDU(0,"10",var1);
        SnmpMessage snmp = new SnmpMessage(0,"xust",pdu);
        byte[] temp = encoder.getSnmpCoding(snmp);
        Util.showPacket(temp);


        var1 = new VariableBindings("11.1.0", 5, null);
        pdu = new PDU(1,"11",var1);
        snmp = new SnmpMessage(0,"xust",pdu);
        temp = encoder.getSnmpCoding(snmp);
        Util.showPacket(temp);

        var1 = new VariableBindings("11.1.0", 4, "aa");
        pdu = new PDU(3,"11",var1);
        snmp = new SnmpMessage(0,"xust",pdu);
        temp = encoder.getSnmpCoding(snmp);
        Util.showPacket(temp);
    }
}
