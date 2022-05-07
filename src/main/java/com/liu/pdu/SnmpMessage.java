package com.liu.pdu;

import com.liu.pdu.type.Type;

import java.util.Objects;

/**
 * SNMP报文
 *
 * @author : LiuYi
 * @version : 2.0
 * @date : 2022/4/30 13:27
 *
 */
public class SnmpMessage {
    /**
     * 版本(Version)：SNMP 版本号。取值 0 表示 SNMPv1，取值 1 则表示 SNMPv2。
     */
    private String version;
    /**
     * 团体名(Community)：用于管理站和代理之间的认证。
     */
    private String community;
    /**
     * SNMP_PDU：SNMP的PDU
     */
    private Object snmpPdu;

    @Override
    public String toString() {
        return "Simple Network Management Protocol" +
                "\n version : " + version +
                "\n community : " + community +
                "\n data : " + snmpPdu;
    }

    public SnmpMessage() {
    }

    public SnmpMessage(int version, String community, Object snmpPdu) {
        this.version = Type.VERSION[version];
        this.community = community;
        this.snmpPdu = snmpPdu;
    }

    public String getVersion() {
        return version;
    }

    public int getVersionId() {
        for (int i = 0; i < Type.VERSION.length; i++) {
            if (Type.VERSION[i].equals(version)) {
                return i;
            }
        }
        return 0;
    }

    public void setVersion(int version) {
        this.version = Type.VERSION[version];
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Object getSnmpPdu() {
        return snmpPdu;
    }

    public void setSnmpPdu(Object snmpPdu) {
        this.snmpPdu = snmpPdu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SnmpMessage that = (SnmpMessage) o;
        return Objects.equals(version, that.version) && Objects.equals(community, that.community) && Objects.equals(snmpPdu, that.snmpPdu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, community, snmpPdu);
    }
}
