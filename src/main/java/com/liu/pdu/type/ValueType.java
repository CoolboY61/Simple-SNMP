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
    public static final String INTEGER = "INTEGER";
    /**
     * OCTET STRING 类型
     */
    public static final String OCTET_STRING = "OCTET STRING";
    /**
     * NULL 类型
     */
    public static final String NULL = "NULL";



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
        }
        return (byte) 5;
    }
}
