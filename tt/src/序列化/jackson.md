## 配置
https://blog.csdn.net/Seky_fei/article/details/109960178
```java
//忽略未知字段
om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//属性为NULL不被序列化
om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
```
