package com.liu.ber;

import com.liu.ber.impl.DecoderImpl;
import com.liu.pdu.SnmpMessage;
import org.junit.Test;

/**
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 17:06
 * BER解码测试
 */
public class DecodeTest {
    public Decoder decoder = new DecoderImpl();

    @Test
    public void getSnmpMessageTest() {
        byte[] data = {48, 37, 2, 1, 0, 4, 4, 120, 117, 115, 116, (byte) 162, 26, 2, 1, 9, 2, 1, 0, 2, 1, 0, 48, 15, 48, 13, 6, 8, 43, 6, 1, 2, 1, 11, 2, 0, 2, 1, 71};
        SnmpMessage snmpMessage = decoder.getSnmpMessage(data);
        System.out.println(snmpMessage);
    }
}
