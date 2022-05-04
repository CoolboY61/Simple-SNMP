package com.liu.pdu.type;

/**
 * Value数据类型
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/5/4 16:14
 */
public class ValueType {
    /**
     * INTEGER 类型
     */
    public static final String INTEGER = "Integer";
    /**
     * OCTET STRING 类型
     */
    public static final String OCTET_STRING = "Octet String";
    /**
     * NULL 类型
     */
    public static final String NULL = "Null";

    /**
     * COUNTER 类型
     */
    public static final String COUNTER = "Counter";

    /**
     * IPADDRESS 类型
     */
    public static final String IPADDRESS = "IpAddress";

    /**2
     * 获取每种类型对应的Byte
     *
     * @param type 类型
     * @return 类型对应的Byte
     */
    public static byte getTypeByte(String type) {
        if (INTEGER.equals(type)) {
            return (byte) 2;
        } else if (OCTET_STRING.equals(type)) {
            return (byte) 4;
        } else if (NULL.equals(type)) {
            return (byte) 5;
        } else if (IPADDRESS.equals(type)) {
            return (byte) 64;
        } else if (COUNTER.equals(type)) {
            return (byte) 65;
        }
        return (byte) 5;
    }
}
