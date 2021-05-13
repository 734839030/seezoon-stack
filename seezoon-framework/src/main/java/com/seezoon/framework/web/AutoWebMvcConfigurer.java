package com.seezoon.framework.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.seezoon.framework.properties.SeezoonProperties;
import com.seezoon.framework.web.servlet.RequestFilter;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@Configuration
@RequiredArgsConstructor
public class AutoWebMvcConfigurer implements WebMvcConfigurer {

    private final SeezoonProperties seezoonProperties;

    /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 两个文档的支持 ，实际测试加和不加都一样，先按文档配置
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        // webjars 支持，文档也需要
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/").setViewName("forward:" + "/swagger-ui/index.html");
    }*/

    @Bean
    public FilterRegistrationBean<RequestFilter> traceFilter() {
        FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<RequestFilter>();
        registration.setFilter(new RequestFilter());
        registration.setName(RequestFilter.class.getSimpleName());
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }

    /**
     *
     * 跨域很常见，默认框架参数开启，如果想更安全，请设置allowedOrigins，如https://www.seezoon.com
     *
     * 如果使用spring security 必须配置{@code http.cors()} 否则以下配置无效
     *
     * 实际spring boot 的处理类{@link org.springframework.web.cors.reactive.CorsWebFilter}
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        SeezoonProperties.CorsProperties cors = seezoonProperties.getCors();
        if (cors.isEnable()) {
            registry.addMapping(cors.getMapping()).allowedOrigins(cors.getAllowedOrigins())
                .allowedHeaders(cors.getAllowedHeaders()).allowedMethods(cors.getAllowedMethods())
                .allowCredentials(cors.isAllowCredentials()).maxAge(cors.getMaxAge());
        }
    }

    /**
     * 默认Spring session SameSite 默认为Lax ,无法跨域使用
     * 
     * @return
     */
    @Bean
    public DefaultCookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setSameSite(null);
        return defaultCookieSerializer;
    }
}
