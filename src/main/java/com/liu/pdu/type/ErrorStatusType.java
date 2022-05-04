package com.liu.pdu.type;

/**
 * ErrorStatus 类型
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/5/4 16:47
 */
public class ErrorStatusType {
    /**
     * "noError (0)", "tooBig (1)", "noSuchName (2)",
     * "badValue (3)", "readOnly (4)", "genError (5)"
     * 6种错误状态
     */
    public static final String[] STATUS = {
            "noError (0)", "tooBig (1)", "noSuchName (2)",
            "badValue (3)", "readOnly (4)", "genError (5)"
    };
}
