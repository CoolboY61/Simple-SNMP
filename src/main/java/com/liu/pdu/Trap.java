package com.liu.pdu;

import com.liu.pdu.type.Type;

import java.util.Objects;

/**
 * Trap类型的PDU
 *
 * @author : LiuYi
 * @version : 2.0
 * @date : 2022/5/7 14:36
 */
public class Trap {

    /**
     * PDU类型(PDU type)：区分PDU的类型。
     */
    private final String pduType;
    /**
     * 制造商 ID(enterprise)：表示设备制造商的标识，
     * 与 MIB-2 对象 sysObjectID 的值相同。
     */
    private String enterprise;
    /**
     * 代理地址(Agent addr)：产生 Trap 的代理的 IP 地址。
     */
    private String agentAddr;
    /**
     * 一般陷入(Generic trap)：通用 Trap 类型，
     * 包括：
     * coldStart(0)、warmStart(1)、linkDown(2)、
     * linkUp(3)、authenticationFailure(4)、
     * egpNeighborLoss(5)、enterpriseSpecific(6)共 7 类
     */
    private String genericTrap;
    /**
     * 特殊陷入(Specific trap)：企业私有 Trap 信息。
     */
    private String specificTrap;
    /**
     * 时间戳(Time stamp)：代理发出陷入的时间，
     * 即系统最后一次重新初始化和产生Trap 之间所持续的时间，与 MIB-2 中的对象 sysUpTime 的值相同。
     */
    private String timeStamp;
    /**
     * 变量绑定表(Variable bindings)：变量绑定列表，由变量名和变量值对组成。
     * 在检索请求报文中，变量的值应为 0。
     */
    private VariableBindings variableBindings;

    @Override
    public String toString() {
        return pduType +
                "\n   " + pduType +
                "\n     enterprise : " + enterprise +
                "\n     agent-addr : " + agentAddr +
                "\n     generic-trap : " + genericTrap +
                "\n     specific-trap : " + specificTrap +
                "\n     time-stamp : " + timeStamp +
                "\n     variable-bindings : " + variableBindings;
    }

    public Trap() {
        this.pduType = Type.PDUTYPE[4];
    }

    public Trap(String enterprise, String agentAddr, int genericTrap, String specificTrap, String timeStamp, VariableBindings variableBindings) {
        this.pduType = Type.PDUTYPE[4];
        this.enterprise = enterprise;
        this.agentAddr = agentAddr;
        this.genericTrap = Type.GENERIC_TRAP[genericTrap];
        this.specificTrap = specificTrap;
        this.timeStamp = timeStamp;
        this.variableBindings = variableBindings;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getAgentAddr() {
        return agentAddr;
    }

    public void setAgentAddr(String agentAddr) {
        this.agentAddr = agentAddr;
    }

    public String getGenericTrap() {
        return genericTrap;
    }

    public void setGenericTrap(int genericTrap) {
        this.genericTrap = Type.GENERIC_TRAP[genericTrap];
    }

    public String getSpecificTrap() {
        return specificTrap;
    }

    public void setSpecificTrap(String specificTrap) {
        this.specificTrap = specificTrap;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public VariableBindings getVariableBindings() {
        return variableBindings;
    }

    public void setVariableBindings(VariableBindings variableBindings) {
        this.variableBindings = variableBindings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trap trap = (Trap) o;
        return Objects.equals(pduType, trap.pduType) && Objects.equals(enterprise, trap.enterprise) && Objects.equals(agentAddr, trap.agentAddr) && Objects.equals(genericTrap, trap.genericTrap) && Objects.equals(specificTrap, trap.specificTrap) && Objects.equals(timeStamp, trap.timeStamp) && Objects.equals(variableBindings, trap.variableBindings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pduType, enterprise, agentAddr, genericTrap, specificTrap, timeStamp, variableBindings);
    }
}
