# 架构
## subject
主体，一个用户对象，所有 Subject 都绑定到 SecurityManager
相当于SpringSecurity的authentication对象


## SecurityManager
安全管理器，管理subject，
相当于SpringSecurity的SecurityContextHolder

## Realm
数据层，dao，为认证授权提供数据
相当于SpringSecurity的UserDetailsService

# 操作
## 获取当前用户
```java
Subject currentUser = SecurityUtils.getSubject();
```

## 获取session
```java
Session session = currentUser.getSession();
session.setAttribute( "someKey", "aValue" );
```

## 登录操作
```java
if ( !currentUser.isAuthenticated() ) {

    UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
    token.setRememberMe(true);
    try {
    currentUser.login( token );
    //if no exception, that's it, we're done!
    } catch ( UnknownAccountException uae ) {
    //username wasn't in the system, show them an error message?
    } catch ( IncorrectCredentialsException ice ) {
    //password didn't match, try again?
    } catch ( LockedAccountException lae ) {
    //account for that username is locked - can't login.  Show them a message?
    }
    ... more types exceptions to check if you want ...
    } catch ( AuthenticationException ae ) {
    //unexpected condition - error?
    }
}
```
```java
UsernamePasswordToken token = new UsernamePasswordToken(username, password);
AuthenticationInfo a = getAuthenticationInfo(token);
//若a为空，则抛出异常认证失败
```

## 判断是否有特定角色
```java
//第一种
if ( currentUser.hasRole( "schwartz" ) ) {
    log.info("May the Schwartz be with you!" );
} else {
    log.info( "Hello, mere mortal." );
}
//第二种
@RequiresRoles( "teller" )
public void openAccount( Account acct ) {
//do something in here that only a teller
//should do
}
```
## 判断权限
```java
//第一种
if ( currentUser.isPermitted( "lightsaber:wield" ) ) {
    log.info("You may use a lightsaber ring.  Use it wisely.");
} else {
    log.info("Sorry, lightsaber rings are for schwartz masters only.");
}
//第二种
@RequiresPermissions("account:create")
public void openAccount( Account acct ) {
//create the account
}
```

## 注销
```java
currentUser.logout(); //removes all identifying information and invalidates their session too.
```

# springboot集成shiro
## 登录授权类
```java
/**
 * 负责认证用户身份和对用户进行授权
 */
publicclass UserRealm extends AuthorizingRealm {

@Autowired
private UserService userService;

@Autowired
private RoleService roleService;

@Autowired
private PermissionService permissionService;

// 用户授权
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roleList = roleService.findRoleByUserId(user.getId());
        Set<String> roleSet = new HashSet<>();
        List<Integer> roleIds = new ArrayList<>();
for (Role role : roleList) {
            roleSet.add(role.getRole());
            roleIds.add(role.getId());
        }
// 放入角色信息
        authorizationInfo.setRoles(roleSet);
// 放入权限信息
        List<String> permissionList = permissionService.findByRoleId(roleIds);
        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));

return authorizationInfo;
    }

// 用户认证
protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        User user = userService.findByAccount(token.getUsername());
if (user == null) {
returnnull;
        }
returnnew SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
```
## shiro配置类
```java
@Configuration
publicclass ShiroConfig {

@Bean
public UserRealm userRealm() {
returnnew UserRealm();
    }

@Bean
public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
return securityManager;
    }

/**
     * 路径过滤规则
     * @return
     */
@Bean
public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        Map<String, String> map = new LinkedHashMap<>();
// 有先后顺序
        map.put("/login", "anon");      // 允许匿名访问
        map.put("/**", "authc");        // 进行身份认证后才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
return shiroFilterFactoryBean;
    }

/**
     * 开启Shiro注解模式，可以在Controller中的方法上添加注解
     * @param securityManager
     * @return
     */
@Bean
public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
return authorizationAttributeSourceAdvisor;
    }
```
