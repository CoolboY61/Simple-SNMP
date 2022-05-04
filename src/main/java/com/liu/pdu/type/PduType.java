package com.liu.pdu.type;

/**
 * PDU数据包类型
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/5/4 16:13
 */
public class PduType {
    /**
     * get-request 类型
     * get-next-request 类型
     * set-request 类型
     * get-response 类型
     * 4种PDU
     */
    public static final String[] TYPE = {
            "get-request", "get-next-request", "get-response", "set-request"
    };
}
