package com.liu.ber;

import com.liu.ber.impl.EncoderImpl;
import com.liu.pdu.GGSPdu;
import com.liu.pdu.ResponsePdu;
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
    public void getVariableBindingsCodingTest() {
        VariableBindings var1 = new VariableBindings("2.1.11.1", 2, "10");
        byte[] temp = encoder.getVariableBindingsCoding(var1);
        Util.showPacket(temp);

        VariableBindings var2 = new VariableBindings("2.1.11.2.0.1", 4, "刘艺");
        temp = encoder.getVariableBindingsCoding(var2);
        Util.showPacket(temp);

        VariableBindings var3 = new VariableBindings("2.1.11.12.3.1", 5, null);
        temp = encoder.getVariableBindingsCoding(var3);
        Util.showPacket(temp);
    }

    @Test
    public void getGgsPduCodingTest() {

        VariableBindings var = new VariableBindings("2.1.11.1", 2, "10");
        byte[] temp1 = encoder.getVariableBindingsCoding(var);
        GGSPdu ggsPdu = new GGSPdu(0, "1", var);
        byte[] temp2 = encoder.getGgsPduCoding(ggsPdu, temp1);
        Util.showPacket(temp2);

        var = new VariableBindings("3.25.74.0", 2, "100000");
        temp1 = encoder.getVariableBindingsCoding(var);
        ggsPdu = new GGSPdu(1, "2", var);
        temp2 = encoder.getGgsPduCoding(ggsPdu, temp1);
        Util.showPacket(temp2);

        var = new VariableBindings("7.8.9.10", 4, "xxxx");
        temp1 = encoder.getVariableBindingsCoding(var);
        ggsPdu = new GGSPdu(3, "3", var);
        temp2 = encoder.getGgsPduCoding(ggsPdu, temp1);
        Util.showPacket(temp2);

    }

    @Test
    public void getResponsePduCodingTest() {
        VariableBindings var = new VariableBindings("2.1.11.2.0", 2, "71");
        byte[] temp1 = encoder.getVariableBindingsCoding(var);
        ResponsePdu responsePdu = new ResponsePdu("9", 0, "0", var);
        byte[] temp2 = encoder.getResponsePduCoding(responsePdu, temp1);
        Util.showPacket(temp2);
    }

    @Test
    public void getSnmpMessageCodingTest() {
        VariableBindings var = new VariableBindings("2.1.11.2.0", 2, "71");
        byte[] temp1 = encoder.getVariableBindingsCoding(var);
        ResponsePdu responsePdu = new ResponsePdu("9", 0, "0", var);
        byte[] temp2 = encoder.getResponsePduCoding(responsePdu, temp1);
        SnmpMessage snmpMessage = new SnmpMessage(0, "xust", responsePdu);
        byte[] temp3 = encoder.getSnmpMessageCoding(snmpMessage, temp2);
        Util.showPacket(temp3);

        var = new VariableBindings("2.1.11.31.0", 2, "1");
        temp1 = encoder.getVariableBindingsCoding(var);
        responsePdu = new ResponsePdu("10", 2, "1", var);
        temp2 = encoder.getResponsePduCoding(responsePdu, temp1);
        snmpMessage = new SnmpMessage(0, "xust", responsePdu);
        temp3 = encoder.getSnmpMessageCoding(snmpMessage, temp2);
        Util.showPacket(temp3);

    }

    @Test
    public void getCodingTest() {
        VariableBindings var = new VariableBindings("2.1.11.2.0", 2, "71");
        GGSPdu ggsPdu = new GGSPdu(3, "1", var);
        SnmpMessage snmpMessage = new SnmpMessage(0, "xust", ggsPdu);
        byte[] temp = encoder.getCoding(snmpMessage, "Set-Request");
        Util.showPacket(temp);
    }
}
