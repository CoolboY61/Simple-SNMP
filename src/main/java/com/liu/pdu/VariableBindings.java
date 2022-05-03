package com.liu.pdu;

import java.util.Objects;

/**
 * 变量绑定列表
 *
 * @author : LiuYi
 * @version :
 * @date : 2022/4/30 15:21
 *
 */
public class VariableBindings {
    /**
     * SNMP对象标识符(Object Name)：OID
     */
    private String objectId = "1.3.6.1.";
    /**
     * 值类型(valueType)：值的类型
     */
    private String valueType;
    /**
     * 值(Value)：值
     */
    private String value;

    @Override
    public String toString() {
        return "\n          Object Name : " + objectId +
                "\n          Value (" + valueType + ") : " + value;
    }

    public VariableBindings() {
    }

    public VariableBindings(String objectId, int valueType, String value) {
        this.objectId = this.objectId + objectId;
        if (valueType == 2) {
            this.valueType = "INTEGER";
        } else if (valueType == 4) {
            this.valueType = "OCTET STRING";
        } else if (valueType == 6) {
            this.valueType = "OBJECT IDENTIFIER";
        } else if (valueType == 5) {
            this.valueType = "NULL";
        }
        this.value = value;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        if (valueType == 2) {
            this.valueType = "INTEGER";
        } else if (valueType == 4) {
            this.valueType = "OCTET STRING";
        } else if (valueType == 6) {
            this.valueType = "OBJECT IDENTIFIER";
        } else if (valueType == 5) {
            this.valueType = "NULL";
        }
    }

    public void setObjectId(String objectId) {
        this.objectId = this.objectId + objectId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VariableBindings that = (VariableBindings) o;
        return Objects.equals(objectId, that.objectId) && Objects.equals(valueType, that.valueType) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, valueType, value);
    }
}
