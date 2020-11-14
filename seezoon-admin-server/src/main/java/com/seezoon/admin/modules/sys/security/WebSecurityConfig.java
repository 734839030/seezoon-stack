package com.seezoon.admin.modules.sys.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.seezoon.admin.modules.sys.security.handler.AjaxAuthenticationFailureHandler;
import com.seezoon.admin.modules.sys.security.handler.AjaxAuthenticationSuccessHandler;
import com.seezoon.admin.modules.sys.security.handler.AjaxLogoutSuccessHandler;

/**
 * @author hdf
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String PUBLIC_ANT_PATH = "/public/**";
    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_OUT_URL = "/logout";

    // @formatter:off

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  需要认证和授权的请求配置
        http.authorizeRequests().antMatchers(PUBLIC_ANT_PATH).permitAll()
                .anyRequest().authenticated();

        // 自带username filter provider 处理机制
        http.formLogin().loginProcessingUrl(LOGIN_URL).successHandler(ajaxAuthenticationSuccessHandler())
        .failureHandler(ajaxAuthenticationFailureHandler());

        // http.authenticationProvider()
        //http.userDetailsService(adminUserDetailsService());
        // 以下为公共逻辑 如果要扩展登录方式，只需要添加类似UsernamePasswordAuthenticationFilter-> DaoAuthenticationProvider 这种整套逻辑
        //登出处理
        http.logout().logoutUrl(LOGIN_OUT_URL).logoutSuccessHandler(ajaxLogoutSuccessHandler());
        // 默认认证过程中的异常处理，accessDeniedHandler 默认为也是返回403
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl())
                // 到认证环节的入口逻辑,默认是跳页
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        // 安全头设置
        http.headers().defaultsDisabled()// 关闭默认
                // 浏览器根据respone content type 格式解析资源
                .contentTypeOptions()
                // xss 攻击，限制有限，还是需要通过过滤请求参数，该框架已做
                .and().xssProtection()
                // 同域名可以通过frame
                .and().frameOptions().sameOrigin()
                // CSRF 攻击
                .and().csrf().ignoringAntMatchers(PUBLIC_ANT_PATH,LOGIN_URL).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 默认 DaoAuthenticationProvider的userDetailsService，自定义其他登录方式还得在provider中设置
        auth.userDetailsService(adminUserDetailsService()).passwordEncoder(null);
    }
    // @formatter:on

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
    public AdminUserDetailsService adminUserDetailsService() {
        return new AdminUserDetailsService();
    }
}
