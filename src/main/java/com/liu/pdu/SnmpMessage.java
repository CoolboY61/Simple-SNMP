package com.liu.pdu;

import java.util.Objects;

/**
 * SNMP报文
 *
 * @author : LiuYi
 * @version :1.0
 * @date : 2022/4/30 13:27
 *
 */
public class SnmpMessage {

    private final String[] versionType = {"version-1 (0)", "version-2 (1)", "version-3 (2)"};

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
        this.version = versionType[version];
        this.community = community;
        this.snmpPdu = snmpPdu;
    }

    public String getVersion() {
        return version;
    }

    public String getVersionId() {
        if ("version-1 (0)".equals(version)) {
            return "0";
        } else if ("version-2 (1)".equals(version)) {
            return "1";
        } else if ("version-3 (2)".equals(version)) {
            return "3";
        }
        return null;
    }

    public void setVersion(int version) {
        this.version = versionType[version];
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
        return version.equals(that.version) && community.equals(that.community) && snmpPdu.equals(that.snmpPdu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, community, snmpPdu);
    }
}
