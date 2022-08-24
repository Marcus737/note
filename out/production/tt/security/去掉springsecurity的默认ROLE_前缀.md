```java
   @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        //去除角色的ROLE_前缀
        handler.setDefaultRolePrefix("");
        return handler;
    }
```