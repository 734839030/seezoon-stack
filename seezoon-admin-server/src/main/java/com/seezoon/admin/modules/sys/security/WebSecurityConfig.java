package com.seezoon.admin.modules.sys.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.seezoon.admin.modules.sys.security.handler.AdminAccessDeniedHandler;
import com.seezoon.admin.modules.sys.security.handler.AjaxAuthenticationFailureHandler;
import com.seezoon.admin.modules.sys.security.handler.AjaxAuthenticationSuccessHandler;
import com.seezoon.admin.modules.sys.security.handler.AjaxLogoutSuccessHandler;

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
// @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@ControllerAdvice
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String[] STATIC_RESOURCES =
        {"/**/*.html", "/**/*.js", "/**/*.css", "/**/*.ico", "/**/*.png", "/**/*.jpg"};
    public static final String[] DOC_API = {"/swagger-resources/**", "/**/api-docs"};
    private static final String DEFAULT_REMEMBER_ME_NAME = "rememberMe";
    private static final String PUBLIC_ANT_PATH = "/public/**";
    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_OUT_URL = "/logout";
    private static final String REMEMBER_KEY = "C02tlRRi8JNsT6Bsp2liSE1paa5naDNY";

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
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false);
        http.rememberMe().rememberMeParameter(DEFAULT_REMEMBER_ME_NAME).key(REMEMBER_KEY).useSecureCookie(true)
            .tokenValiditySeconds(7 * 24 * 60 * 60).userDetailsService(adminUserDetailsService());
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
        web.ignoring().antMatchers(STATIC_RESOURCES).antMatchers(DOC_API);
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
        auth.userDetailsService(adminUserDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
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
