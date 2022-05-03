package com.liu.pdu;

import java.util.Arrays;
import java.util.Objects;

/**
 * Response类型的PDU
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 14:41
 */
public class ResponsePdu {

    private final String[] tempStatus = {"noError (0)", "tooBig (1)", "noSuchName (2)", "badValue (3)", "readOnly (4)", "genError (5)"};

    /**
     * PDU类型(PDU type)：区分PDU的类型。
     */
    private final String pduType = "get-response";
    /**
     * 请求标识(Request ID)：SNMP 给每个请求分配全局唯一的 ID，用于匹配请求和
     * 响应。请求标识的另一个作用是检测由不可靠的传输服务产生的重复报文。
     */
    private String requestId;
    /**
     * 错误状态(Error status)：用于表示在处理请求时出现的状况，共有 6 种错误状态：
     * noError(0)、tooBig(1)、noSuchName(2)、badValue(3)、readOnly(4)、genError(5)
     */
    private String errorStatus;
    /**
     * 错误索引(Error index)：当错误状态非 0 时指向出错的变量。
     */
    private String errorIndex;
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

    public ResponsePdu() {
    }

    public ResponsePdu(String requestId, int errorStatus, String errorIndex, Object variableBindings) {
        this.requestId = requestId;
        this.errorStatus = tempStatus[errorStatus];
        this.errorIndex = errorIndex;
        this.variableBindings = variableBindings;
    }

    public String getPduType() {
        return pduType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorStatusId() {
        if ("noError (0)".equals(errorStatus)) {
            return "0";
        } else if ("tooBig (1)".equals(errorStatus)) {
            return "1";
        } else if ("noSuchName (2)".equals(errorStatus)) {
            return "2";
        } else if ("badValue (3)".equals(errorStatus)) {
            return "3";
        } else if ("readOnly (4)".equals(errorStatus)) {
            return "4";
        } else if ("genError (5)".equals(errorStatus)) {
            return "5";
        }
        return null;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = tempStatus[errorStatus];
    }

    public String getErrorIndex() {
        return errorIndex;
    }

    public void setErrorIndex(String errorIndex) {
        this.errorIndex = errorIndex;
    }

    public Object getVariableBindings() {
        return variableBindings;
    }

    public void setVariableBindings(Object variableBindings) {
        this.variableBindings = variableBindings;
    }

    public String[] getTempStatus() {
        return tempStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponsePdu that = (ResponsePdu) o;
        return Objects.equals(pduType, that.pduType) && Objects.equals(requestId, that.requestId) && Objects.equals(errorStatus, that.errorStatus) && Objects.equals(errorIndex, that.errorIndex) && Objects.equals(variableBindings, that.variableBindings) && Arrays.equals(tempStatus, that.tempStatus);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(pduType, requestId, errorStatus, errorIndex, variableBindings);
        result = 31 * result + Arrays.hashCode(tempStatus);
        return result;
    }
}
