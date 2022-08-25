解决报错 Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException

使用springboot2.6.0后，配置swagger，不论是2.9.2还是3.0.0都报错

解决方法：配置文件中加入 application.yml
```yaml
spring:
    mvc:
        pathmatch: 
          matching-strategy: ant_path_matcher
```

# swagger默认地址
```text
http://localhost:端口/swagger-ui.html
```