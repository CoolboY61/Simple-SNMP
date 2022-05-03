package com.liu.pdu;

import java.util.Arrays;
import java.util.Objects;

/**
 * Get类型的PDU
 *
 * @author : LiuYi
 * @version :1.0
 * @date : 2022/4/30 14:29
 *
 */
public class GGSPdu {

    private final String[] tempStatus = {"noError (0)", "tooBig (1)", "noSuchName (2)", "badValue (3)", "readOnly (4)", "genError (5)"};
    private final String[] AllPduType = {"get-request", "get-next-request", "", "set-request"};

    /**
     * PDU类型(PDU type)：区分PDU的类型。
     */
    private String pduType;
    /**
     * 请求标识(Request ID)：SNMP 给每个请求分配全局唯一的 ID，用于匹配请求和
     * 响应。请求标识的另一个作用是检测由不可靠的传输服务产生的重复报文。
     */
    private String requestId;
    /**
     * 错误状态(Error status)：用于表示在处理请求时出现的状况，共有 6 种错误状态：
     * noError(0)、tooBig(1)、noSuchName(2)、badValue(3)、readOnly(4)、genError(5)
     */
    private final String errorStatus = tempStatus[0];
    /**
     * 错误索引(Error index)：当错误状态非 0 时指向出错的变量。
     */
    private final String errorIndex = "0";
    /**
     * 变量绑定表(Variable bindings)：变量绑定列表，由变量名和变量值对组成。
     * 在检索请求报文中，变量的值应为 0。
     */
    private Object variableBindings;

    @Override
    public String toString() {
        return pduType +
                "\n   " + pduType +
                "\n     request-id : " + requestId +
                "\n     error-status : " + errorStatus +
                "\n     error-index : " + errorIndex +
                "\n     variable-bindings : " + variableBindings;
    }

    public GGSPdu() {
    }

    public GGSPdu(int pduType, String requestId, Object variableBindings) {
        this.pduType = AllPduType[pduType];
        this.requestId = requestId;
        this.variableBindings = variableBindings;
    }

    public String getPduType() {
        return pduType;
    }

    public void setPduType(int pduType) {
        this.pduType = AllPduType[pduType];
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getVariableBindings() {
        return variableBindings;
    }

    public void setVariableBindings(Object variableBindings) {
        this.variableBindings = variableBindings;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public String getErrorIndex() {
        return errorIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GGSPdu GGSPdu = (GGSPdu) o;
        return Arrays.equals(tempStatus, GGSPdu.tempStatus) && Objects.equals(pduType, GGSPdu.pduType) && Objects.equals(requestId, GGSPdu.requestId) && Objects.equals(errorStatus, GGSPdu.errorStatus) && Objects.equals(errorIndex, GGSPdu.errorIndex) && Objects.equals(variableBindings, GGSPdu.variableBindings);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(pduType, requestId, errorStatus, errorIndex, variableBindings);
        result = 31 * result + Arrays.hashCode(tempStatus);
        return result;
    }
}
