package com.liu.pdu.type;

/**
 * SNMP数据包中，一些固定值的字段，将其取值定义在Type中
 *
 * @author : LiuYi
 * @version : 2.0
 * @date : 2022/5/6 22:57
 */
public class Type {
    /**
     * PDU数据包的类型
     *
     * get-request 类型
     * get-next-request 类型
     * set-request 类型
     * get-response 类型
     * trap 类型
     * 5种PDU
     */
    public static final String[] PDUTYPE = {
            "get-request", "get-next-request", "get-response", "set-request", "trap"
    };

    /**
     * SNMP的Version的版本
     *
     * "version-1 (0)", "version-2 (1)", "version-3 (2)"
     * 3个版本
     */
    public static final String[] VERSION = {
            "version-1 (0)", "version-2 (1)", "version-3 (2)"
    };

    /**
     * Response中的ErrorStatus的类型
     *
     * "noError (0)"    ：正常
     * "tooBig (1)"     ：应答太大无法装入一个 SNMP 报文
     * "noSuchName (2)" ：操作指明了一个不存在的变量
     * "badValue (3)"   ：Set 操作结果是一个无效值或无效语法
     * "readOnly (4)"   ：管理站试图修改一个只读变量
     * "genError (5)"   ：其他差错
     * 6种错误状态
     */
    public static final String[] STATUS = {
            "noError (0)", "tooBig (1)", "noSuchName (2)",
            "badValue (3)", "readOnly (4)", "genError (5)"
    };

    /**
     * Trap中Generic trap的种类
     * "coldStart(0)"               ：冷启动，常常由于崩溃或重大故障引起意外重启
     * "warmStart(1)"               ：热启动，即重新初始化，但不修改代理的配置或协议实例的实现
     * "linkDown(2)"                ：某个接口从工作状态变为故障状态
     * "linkUp(3)"                  ：某个接口从故障状态变为工作状态
     * "authenticationFailure(4)"   ：接收到团体名无效的报文，即认证失败
     * "egpNeighborLoss(5)"         ：某个相邻 EGP 路由器变为故障状态
     * "enterpriseSpecific(6)"      ：代理自定义事件，需结合特定代码具体指明
     *
     */
    public static final String[] GENERIC_TRAP = {
            "coldStart(0)", "warmStart(1)", "linkDown(2)", "linkUp(3)",
            "authenticationFailure(4)", "egpNeighborLoss(5)", "enterpriseSpecific(6)"
    };
}
