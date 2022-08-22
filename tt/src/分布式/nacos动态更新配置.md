## 导入依赖
```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>2021.1</version>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
    <version>3.1.3</version>
</dependency>

```
## 编写bootstrap.properties
这里有个坑，没导入spring-cloud-starter-bootstrap该配置文件不会生效
```properties
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
spring.application.name=userservice
spring.cloud.nacos.config.file-extension=properties
```
## 编写controller

```java
@RestController
@RequestMapping("/config")
@RefreshScope //加上这个注解实现动态刷新
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;
    
    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}
```

## DataId
`${prefix}-${spring.profiles.active}.${file-extension}$`
`prefix默认是应用名`

## 更新
使用api
```
POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=example.properties&group=DEFAULT_GROUP&content=useLocalCache=true"
```
使用网页端

