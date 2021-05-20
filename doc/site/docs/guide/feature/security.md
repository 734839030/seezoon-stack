# 登录与授权

基于 [Spring Security ](https://spring.io/projects/spring-security)实现登录安全及细粒度的授权访问控制，Spring Security模型比较固定，建议通读其文档便于理解，关键控制参数已经抽取到：

```java
com.seezoon.admin.modules.sys.security.LoginSecurityProperties
```

目前实现一种登录方式即账号密码登录，扩展其他登录方式也比较简单，按文档实现一个Filter。

> 后续考虑实现微信扫码登录，作为例子。

具体实现参考：

```java
package com.seezoon.admin.modules.sys.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.seezoon.admin.modules.sys.security.handler.AdminAccessDeniedHandler;
import com.seezoon.admin.modules.sys.security.handler.AjaxAuthenticationFailureHandler;
import com.seezoon.admin.modules.sys.security.handler.AjaxAuthenticationSuccessHandler;
import com.seezoon.admin.modules.sys.security.handler.AjaxLogoutSuccessHandler;

import lombok.RequiredArgsConstructor;

/**
 * <code>
 *     账号密码登录
 *     url:/login
 *     method:POST
 *     param:
 *          username
 *          password
 *          rememberMe
 *
 *     退出登录
 *     url:/logout
 *     method:POST
 *
 * </code>
 *
 *
 * 安全配置
 *
 * @see <a>https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5</a>
 *
 *      默认的安全设置参考{@link WebSecurityConfigurerAdapter#getHttp}
 *
 * @author hdf
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@ControllerAdvice
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String[] STATIC_RESOURCES =
        {"/**/*.html", "/**/*.js", "/**/*.css", "/**/*.ico", "/**/*.png", "/**/*.jpg"};
    // public static final String[] DOC_API = {"/swagger-resources/**", "/**/api-docs"};
    private static final String DEFAULT_REMEMBER_ME_NAME = "rememberMe";
    private static final String PUBLIC_ANT_PATH = "/public/**";
    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_OUT_URL = "/logout";

    private final LoginSecurityProperties loginSecurityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 需要认证和授权的请求配置
        http.authorizeRequests().antMatchers(PUBLIC_ANT_PATH).permitAll().anyRequest().authenticated();
        // 自带username filter provider 处理机制
        http.formLogin().loginProcessingUrl(LOGIN_URL).successHandler(ajaxAuthenticationSuccessHandler())
            .failureHandler(ajaxAuthenticationFailureHandler());

        // 以下为公共逻辑 如果要扩展登录方式，只需要添加类似UsernamePasswordAuthenticationFilter-> DaoAuthenticationProvider 这种整套逻辑
        // 登出处理
        http.logout().logoutUrl(LOGIN_OUT_URL).logoutSuccessHandler(ajaxLogoutSuccessHandler());
        // 默认认证过程中的异常处理，accessDeniedHandler 默认为也是返回403，spring security
        // 是在filter级别抓住异常回调handler的,所以会被全局拦截器模式@ExceptionHandler 吃掉
        http.exceptionHandling().accessDeniedHandler(new AdminAccessDeniedHandler())
            // 到认证环节的入口逻辑,默认是跳页
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        // seesion 管理 一个账号登录一次，后面的挤掉前面的(spring security 默认的,true 则已登录的优先)
        // remember 采用默认解密前端remember-cookie
        http.sessionManagement().maximumSessions(loginSecurityProperties.getMaximumSessions())
            .maxSessionsPreventsLogin(loginSecurityProperties.isMaxSessionsPreventsLogin());
        http.rememberMe().rememberMeParameter(DEFAULT_REMEMBER_ME_NAME).key(loginSecurityProperties.getRememberKey())
            .useSecureCookie(true).tokenValiditySeconds((int)loginSecurityProperties.getRememberTime().toSeconds())
            .userDetailsService(adminUserDetailsService());
        // 需要添加不然spring boot 的跨域配置无效
        http.cors();
        // 安全头设置
        http.headers().defaultsDisabled()// 关闭默认
            // 浏览器根据respone content type 格式解析资源
            .contentTypeOptions()
            // xss 攻击，限制有限，还是需要通过过滤请求参数，该框架已做
            .and().xssProtection()
            // 同域名可以通过frame
            .and().frameOptions().sameOrigin()
            // CSRF 攻击 开发时候暂时disable
            // respone cookie name XSRF-TOKEN
            // requst param _csrf or below;
            // request head HEADER_NAME = "X-CSRF-TOKEN";
            // CsrfFilter 默认实现类是这个，不拦截get请求
            .and().csrf().ignoringAntMatchers(PUBLIC_ANT_PATH, LOGIN_URL)
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());// .disable();
    }

    /**
     * 忽略静态资源在HttpSecurity前面
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 按需忽略
        web.ignoring().antMatchers(STATIC_RESOURCES);// .antMatchers(DOC_API);
    }

    /**
     * 防止被上层的@ExceptionHandler 范围更大的给拦截住，而无法调用accessDeniedHandler
     *
     * @param e
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) {
        throw e;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 默认 DaoAuthenticationProvider的userDetailsService，自定义其他登录方式还得在provider中设置
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(AdminPasswordEncoder.getEncoder());
        daoAuthenticationProvider.setUserDetailsService(adminUserDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        auth.authenticationProvider(daoAuthenticationProvider);

        // 只因为下面的没有暴露setHideUserNotFoundExceptions 方法，导致UsernameNotFoundException 被内部转换成BadCredentialsException
        // auth.userDetailsService(adminUserDetailsService()).passwordEncoder(AdminPasswordEncoder.getEncoder());
    }

    @Bean
    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return new AjaxLogoutSuccessHandler();
    }

    @Bean
    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AdminUserDetailsServiceImpl adminUserDetailsService() {
        return new AdminUserDetailsServiceImpl();
    }
}

```

## CSRF & XSS

设置代码，框架多特殊字符会自动转义避免XSS 攻击，针对CSRF 登录成功后获取Respone Cookie中 name=XSRF-TOKEN，在请求时候可以统一携带param _csrf=xxx 或者Header：X-CSRF-TOKEN=xxx。

> CSRF 默认不拦截get 请求。

```java
   http.headers().defaultsDisabled()// 关闭默认
            // 浏览器根据respone content type 格式解析资源
            .contentTypeOptions()
            // xss 攻击，限制有限，还是需要通过过滤请求参数，该框架已做
            .and().xssProtection()
            // 同域名可以通过frame
            .and().frameOptions().sameOrigin()
            // CSRF 攻击 
            // respone cookie name XSRF-TOKEN
            // requst param _csrf or below;
            // request head HEADER_NAME = "X-CSRF-TOKEN";
            // CsrfFilter 默认实现类是这个，不拦截get请求
            .and().csrf().ignoringAntMatchers(PUBLIC_ANT_PATH, LOGIN_URL)
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
```

## 单账号登录限制

可以配置一个账号是否可以多人登录，具体设置：

```java
http.sessionManagement().maximumSessions(loginSecurityProperties.getMaximumSessions())
            .maxSessionsPreventsLogin(loginSecurityProperties.isMaxSessionsPreventsLogin());
```

> 需要设定HttpSessionEventPublisher，Spring session 还需指定SessionRegistry。

## RememberMe

记住我实现，可以控制记住时长，以及安全控制盐值，可以定期更换。

```java
http.rememberMe().rememberMeParameter(DEFAULT_REMEMBER_ME_NAME).key(loginSecurityProperties.getRememberKey())
         .useSecureCookie(true).tokenValiditySeconds((int)loginSecurityProperties.getRememberTime().toSeconds())
```

> 修改密码后，RememberMe会自动失效。

## 登录安全

账号错误到一定次数会锁定，IP错误一定次数会锁定IP，见LoginSecurityProperties中配置。

## 授权

采用RBAC（Role-based access control）的授权访问控制，即基于角色的访问控制。登录时候会放入相关授权信息，主要包括可访问菜单、按钮。

```java
package com.seezoon.admin.modules.sys.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.seezoon.admin.framework.file.FileService;
import com.seezoon.admin.modules.sys.dto.UserInfo;
import com.seezoon.admin.modules.sys.security.constant.LockType;
import com.seezoon.admin.modules.sys.service.SysUserService;
import com.seezoon.dao.framework.constants.EntityStatus;
import com.seezoon.dao.modules.sys.entity.SysMenu;
import com.seezoon.dao.modules.sys.entity.SysRole;
import com.seezoon.dao.modules.sys.entity.SysUser;
import com.seezoon.framework.utils.IpUtil;

/**
 * 用户加载逻辑
 *
 * @author hdf
 */
public class AdminUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileService fileService;
    @Autowired
    private LoginSecurityService loginSecurityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request =
            ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String remoteIp = IpUtil.getRemoteIp(request);
        boolean ipLocked = loginSecurityService.getIpLockStrategy().isLocked(remoteIp);
        if (ipLocked) {
            throw new LockedException(LockType.IP.name());
        }

        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("username is empty");
        }

        boolean locked = loginSecurityService.getUsernameLockStrategy().isLocked(username);
        if (locked) {
            throw new LockedException(LockType.USERNAME.name());
        }
        SysUser user = sysUserService.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException(username + "  not found");
        }

        if (EntityStatus.INVALID.status() == user.getStatus()) {
            throw new DisabledException(username + " disabled");
        }

        UserInfo userInfo = new UserInfo(user.getId(), user.getDeptId(), user.getUsername(), user.getName());
        userInfo.setDeptName(user.getDeptName());
        userInfo.setPhoto(fileService.getUrl(user.getPhoto()));
        // 角色及权限信息登录成功后放入
        AdminUserDetails adminUserDetails = new AdminUserDetails(userInfo, username, user.getPassword());
        adminUserDetails.setAuthorities(getAuthorities(user.getId()));
        return adminUserDetails;
    }

    /**
     *
     * @return
     */
    private List<GrantedAuthority> getAuthorities(Integer userId) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色处理
        List<SysRole> userRoles = userService.findRolesByUserId(userId);
        userRoles.stream().filter(v -> StringUtils.isNotBlank(v.getCode())).forEach(v -> {
            authorities.add(new UserGrantedAuthority(v.getCode(), true));
        });
        List<SysMenu> userMenus = userService.findMenusByUserId(userId);
        userMenus.stream().filter(v -> StringUtils.isNotBlank(v.getPermission())).forEach(v -> {
            authorities.add(new UserGrantedAuthority(v.getPermission()));
        });
        return authorities;
    }
}

```

## 细粒度控制

Spring Security 提供了多种方式，[Authorization Architecture](https://docs.spring.io/spring-security/site/docs/5.4.6/reference/html5/#servlet-authorization)。

这里列举常用的使用方式基本够用，更多表达式语法[Expression-Based Access Control](https://docs.spring.io/spring-security/site/docs/5.4.6/reference/html5/#el-access)。

```java
// 只看UserDetails中Authorities是否包含即可
@PreAuthorize("hasAuthority('ROLE_admin')")
// 判断有ROLE_ 前缀的Authority
@PreAuthorize("hasRole('admin')") 
// 不建议使用, 上面的够用了
@Secured({"ROLE_admin"}) 
```

> 如果是角色判断需要GrantedAuthority放入时候需要加上ROLE_前缀，该框架已处理。

## 动态菜单&按钮

根据用户的角色，获取用户菜单和按钮权限。