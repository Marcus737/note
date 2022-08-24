4种授权方式

https://blog.51cto.com/u_14299052/4659170
https://blog.csdn.net/mingpingyao/article/details/120102374

# 搭建
## sql语句
```sql
create table clientdetails
(
    appId                  varchar(256)  not null
        primary key,
    resourceIds            varchar(256)  null,
    appSecret              varchar(256)  null,
    scope                  varchar(256)  null,
    grantTypes             varchar(256)  null,
    redirectUrl            varchar(256)  null,
    authorities            varchar(256)  null,
    access_token_validity  int           null,
    refresh_token_validity int           null,
    additionalInformation  varchar(4096) null,
    autoApproveScopes      varchar(256)  null
);

create table oauth_access_token
(
    token_id          varchar(256)     null,
    token             varbinary(10000) null,
    authentication_id varchar(256)     not null
        primary key,
    user_name         varchar(256)     null,
    client_id         varchar(256)     null,
    authentication    varbinary(10000) null,
    refresh_token     varchar(256)     null
);

create table oauth_approvals
(
    userId         varchar(256)                        null,
    clientId       varchar(256)                        null,
    scope          varchar(256)                        null,
    status         varchar(10)                         null,
    expiresAt      timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    lastModifiedAt timestamp default CURRENT_TIMESTAMP not null
);

create table oauth_client_details
(
    client_id               varchar(256)  not null
        primary key,
    resource_ids            varchar(256)  null,
    client_secret           varchar(256)  null,
    scope                   varchar(256)  null,
    authorized_grant_types  varchar(256)  null,
    web_server_redirect_uri varchar(256)  null,
    authorities             varchar(256)  null,
    access_token_validity   int           null,
    refresh_token_validity  int           null,
    additional_information  varchar(4096) null,
    autoapprove             varchar(256)  null
);

create table oauth_client_token
(
    token_id          varchar(256)    null,
    token             varbinary(1000) null,
    authentication_id varchar(256)    not null
        primary key,
    user_name         varchar(256)    null,
    client_id         varchar(256)    null
);

create table oauth_code
(
    code           varchar(256)     null,
    authentication varbinary(10000) null
);

create table oauth_refresh_token
(
    token_id       varchar(256)     null,
    token          varbinary(10000) null,
    authentication varbinary(10000) null
);

create table sys_role
(
    id        int auto_increment
        primary key,
    role_name varchar(255) null,
    role_desc varchar(255) null
);

create table sys_user
(
    id       int auto_increment
        primary key,
    username varchar(255) null,
    password varchar(255) null,
    status   int          null
);

create table sys_user_role
(
    uid int null,
    rid int null
);


```

## 搭建资源服务端
### pom依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>oauth2Test</artifactId>
        <groupId>org.example</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>OAuthResourceApplication</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>
    </dependencies>

</project>
```
### application.yml
```yaml
server:
  port: 9002
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/oauth2test?serverTimezone=UTC
    username: root
    password: root
  main:
    allow-bean-definition-overriding: true
mybatis:
  type-aliases-package: com.wei.entity
  configuration:
    map-underscore-to-camel-case: true
logging:
  level:
    com.example: debug
```

### 配置类
#### OauthResourceConfig
```java
package com.wei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableResourceServer
public class OauthResourceConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("jdbcTokenStore")
    private TokenStore tokenStore;
 
    /**
     * 指定token的持久化策略
     * 其下有   RedisTokenStore保存到redis中，
     * JdbcTokenStore保存到数据库中，
     * InMemoryTokenStore保存到内存中等实现类，
     * 这里我们选择保存在数据库中
     *
     * @return
     */
    @Bean("jdbcTokenStore")
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }
 
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //指定当前资源的id，非常重要！必须写！
        resources.resourceId("product_api")
                //指定保存token的方式
                .tokenStore(tokenStore);
    }
 
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //指定不同请求方式访问资源所需要的权限，一般查询是read，其余是write。
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
                .and()
                .headers().addHeaderWriter((request, response) -> {
            //允许跨域
            response.addHeader("Access-Control-Allow-Origin", "*");
            //如果是跨域的预检请求，则原封不动向下传达请求头信息
            if (request.getMethod().equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            }
        });
    }
}
```
### controller
```java
package com.wei.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping
    public String findAll() {
        return "查询产品列表成功！";
    }
}
```

### 启动类
```java
package com.wei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wei.mapper")
public class OAuthResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthResourceApplication.class, args);
    }
}
```

## 搭建认证授权端
### pom依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>oauth2Test</artifactId>
        <groupId>org.example</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>OauthServerApplication</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>
    </dependencies>

</project>
```
### application.yml
```yaml
server:
  port: 9001
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/oauth2test?serverTimezone=UTC
    username: root
    password: root
  main:
    allow-bean-definition-overriding: true
mybatis:
  type-aliases-package: com.wei.entity
  configuration:
    map-underscore-to-camel-case: true

logging:
  level:
    org.springframework.security: debug
```


### 配置类
#### OauthServerConfig
```java
package com.wei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
 
 
    //从数据库中查询出客户端信息
    @Bean
    public JdbcClientDetailsService clientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }

    //token保存策略
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
 
    //授权信息保存策略
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }
 
    //授权码模式专用对象
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    //指定客户端登录信息来源
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //从数据库取数据
        clients.withClientDetails(clientDetailsService());
 
//         从内存中取数据
//        clients.inMemory()
//                .withClient("baidu")
//                .secret(passwordEncoder.encode("12345"))
//                .resourceIds("product_api")
//                .authorizedGrantTypes(
//                        "authorization_code",
//                        "password",
//                        "client_credentials",
//                        "implicit",
//                        "refresh_token"
//                )// 该client允许的授权类型 authorization_code,password,refresh_token,implicit,client_credentials
//                .scopes("read", "write")// 允许的授权范围
//                .autoApprove(false)
//                //加上验证回调地址
//                .redirectUris("http://www.baidu.com");
    }
 
    //检测token的策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients()    //允许form表单客户端认证,允许客户端使用client_id和client_secret获取token
                .checkTokenAccess("isAuthenticated()")     //通过验证返回token信息
                .tokenKeyAccess("permitAll()")            // 获取token请求不进行拦截
                .passwordEncoder(passwordEncoder);
    }
 
    //OAuth2的主配置信息
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .approvalStore(approvalStore())
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(authorizationCodeServices())
                .tokenStore(tokenStore())
                .userDetailsService(userDetailsService);
    }
}
```
#### WebSecurityConfig
```java
package com.wei.config;

import com.wei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
 
 
    @Bean
    public PasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //所有资源必须授权后访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()//指定认证页面可以匿名访问
                //关闭跨站请求防护
                .and()
                .csrf().disable();
    }
 
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //UserDetailsService类
        auth.userDetailsService(userService)
                //加密策略
                .passwordEncoder(passwordEncoder);
    }
 
    //AuthenticationManager对象在OAuth2认证服务中要使用，提取放入IOC容器中
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
```
### 实体类
#### SysUser
```java
package com.wei.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

public class SysRole implements GrantedAuthority {
    private Integer id;
    private String roleName;
    private String roleDesc;
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getRoleName() {
        return roleName;
    }
 
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
 
    public String getRoleDesc() {
        return roleDesc;
    }
 
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
 
    //标记此属性不做json处理
    @JsonIgnore
    @Override
    public String getAuthority() {
        return roleName;
    }
}
 
```
#### SysRole
```java
package com.wei.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SysUser implements UserDetails {


    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private List<SysRole> roles = new ArrayList<>();
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public Integer getStatus() {
        return status;
    }
 
    public void setStatus(Integer status) {
        this.status = status;
    }
 
    public List<SysRole> getRoles() {
        return roles;
    }
 
    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
 
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
 
    @Override
    public String getUsername() {
        return username;
    }
 
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
```
### mapper
#### RoleMapper
```java
package com.wei.mapper;

import com.wei.entity.SysRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    @Select("select r.id,r.role_name roleName ,r.role_desc roleDesc " +
            "FROM sys_role r,sys_user_role ur " +
            "WHERE r.id=ur.rid AND ur.uid=#{uid}")
    public List<SysRole> findByUid(Integer uid);
}
```
#### UserMapper
```java
package com.wei.mapper;

import com.wei.entity.SysUser;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("select * from sys_user where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roles", column = "id", javaType = List.class,
                    many = @Many(select = "com.wei.mapper.RoleMapper.findByUid"))
    })
    public SysUser findByUsername(String username);
 
}
```
### service
#### UserService
```java
package com.wei.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
 
}
```
#### UserServiceImpl
```java
package com.wei.service;

import com.wei.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.findByUsername(username);
    }
}
```

### 启动类
```java
package com.wei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wei.mapper")
public class OauthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthServerApplication.class, args);
    }
}
```






问题1:
error="invalid_grant", error_description="A redirect_uri can only be used by implicit or authorization_code grant types."
异常定位：DefaultRedirectResolver
错误信息：是因为我们在设置oauth2授权模式的地方没有按照正确的格式
解决方案：
```java
@Override
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // 从数据库oauth_client_details表读取客户端配置
    clients.inMemory().withClient("c1")
    .redirectUris("http://www.baidu.com")
    .secret(new BCryptPasswordEncoder().encode("123456"))
    .autoApprove(true)
    // 重点是这个地方，注意格式，一般我们不会犯这个错误，学习的过程中有可能就会导致这个问题
    .authorizedGrantTypes("authorization_code,refresh_token");
    }
}
```

