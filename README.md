# 基于Java实现SNMPV1 Manager的基本服务
## 1. 实现PDU数据类型：
+ get-request
+ get-next-request
+ set-request
+ get-response
## 2. Value支持数据类型：
+ INTEGER
+ OCTET STRING
+ NULL
+ COUNTER
+ IPADDRESS

只实现了简单的SNMP Manager服务，在VariableBindings中默认只含有一对（Name-Value）
