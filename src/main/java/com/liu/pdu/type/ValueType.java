package com.liu.pdu.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Value数据类型
 *
 * @author : LiuYi
 * @version : 1.4
 * @date : 2022/5/4 16:14
 */
public class ValueType {
    /**
     * 存放 数据类型 与其 字节 的映射
     */
    public static Map<String,Byte> typeValue = new HashMap<>();
    /**
     * 存放 数据类型 与其 序号 的映射
     */
    public static Map<Integer,String> type = new HashMap<>();
    /**
     * BOOLEAN 布尔类型
     */
    public static final String BOOLEAN = "Boolean";
    /**
     * INTEGER 整数类型
     */
    public static final String INTEGER = "Integer";
    /**
     * OCTET STRING 字符串类型
     */
    public static final String OCTET_STRING = "Octet String";
    /**
     * NULL 空类型
     */
    public static final String NULL = "Null";
    /**
     * OBJECT_IDENTIFIER 对象标识符类型
     */
    public static final String OBJECT_IDENTIFIER = "Object Identifier";
    /**
     * SEQUENCE 序列类型
     */
    public static final String SEQUENCE = "Sequence";
    /**
     * COUNTER 计数器类型
     */
    public static final String COUNTER = "Counter";
    /**
     * IPADDRESS IP地址类型
     */
    public static final String IPADDRESS = "IpAddress";

    public static final String TIMETICKS = "TimeTicks";

    /**
     * BOOLEAN类型的True
     */
    public static final String TRUE = "True";
    /**
     * BOOLEAN类型的False
     */
    public static final String FALSE = "False";

    static {
        typeValue.put(BOOLEAN,(byte) 1);
        typeValue.put(INTEGER,(byte) 2);
        typeValue.put(OCTET_STRING,(byte) 4);
        typeValue.put(NULL,(byte) 5);
        typeValue.put(OBJECT_IDENTIFIER,(byte) 6);
        typeValue.put(SEQUENCE,(byte) 48);
        typeValue.put(IPADDRESS,(byte) 64);
        typeValue.put(COUNTER,(byte) 65);
        typeValue.put(TIMETICKS,(byte) 67);

        type.put(1,BOOLEAN);
        type.put(2,INTEGER);
        type.put(4,OCTET_STRING);
        type.put(5,NULL);
        type.put(6,OBJECT_IDENTIFIER);
        type.put(48,SEQUENCE);
        type.put(64,IPADDRESS);
        type.put(65,COUNTER);
        type.put(67,TIMETICKS);
    }

    /**
     * 获取每种类型对应的Byte
     *
     * @param type 类型
     * @return 类型对应的Byte
     */
    public static byte getTypeByte(String type) {
        return typeValue.get(type);
    }

    /**
     * 根据类型序号获取对应的Type
     *
     * @param num 类型序号
     * @return 序号对应的类型
     */
    public static String getTypeByNum(int num) {
        return type.get(num);
    }
}
