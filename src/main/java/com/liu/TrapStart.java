package com.liu;

import com.liu.snmp.TrapServer;

/**
 * Trap服务启动器
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/5/7 23:21
 */
public class TrapStart {
    public static void main(String[] args) {
        TrapServer trapServer = new TrapServer();
        Thread trap = new Thread(trapServer);
        trap.start();
    }
}
