package com.seezoon.framework.autoconfigure;

import javax.servlet.ServletRequestListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

import com.seezoon.framework.properties.SeezoonProperties;
import com.seezoon.framework.web.AutoWebMvcConfigurer;

import io.swagger.annotations.Api;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * event listener 通过spring.factories 加载
 * <p>
 * 通过{@code @ComponentScan }扫描advice 也可以使用{@code @import}
 *
 * {@link BeanValidatorPluginsConfiguration} swagger jsr303 使用
 *
 * 开启简易缓存，主要针对字典及基本配置参数，实时性要求不高的业务进行缓存
 *
 * @author hdf
 */
@Configuration
@EnableConfigurationProperties(SeezoonProperties.class)
@PropertySource({"classpath:default-framework.properties"})
@ServletComponentScan(basePackages = "com.seezoon.framework.web.servlet",
    basePackageClasses = ServletRequestListener.class)
@ComponentScan({"com.seezoon.framework.web.advice", "com.seezoon.framework.component"})
@EnableOpenApi
@Import({AutoWebMvcConfigurer.class, BeanValidatorPluginsConfiguration.class})
@EnableCaching
public class SeezoonFrameworkAutoConfiguration {

    @Autowired
    private SeezoonProperties seezoonProperties;

    /**
     * doc 配置
     *
     * @return
     * @see <a>http://127.0.0.1:8080/swagger-ui/index.html</a>
     * @see <a>http://127.0.0.1:8080/doc.html</a>
     * @see <a>http://springfox.github.io/springfox/docs/current/</a>
     */
    @Bean
    public Docket openApiStore() {
        ApiInfo apiInfo = new ApiInfoBuilder().title(seezoonProperties.getDoc().getName())
            .description(seezoonProperties.getDoc().getDescription())
            .contact(new Contact(seezoonProperties.getDoc().getName(), seezoonProperties.getDoc().getUrl(),
                seezoonProperties.getDoc().getAuthor()))
            .version(seezoonProperties.getDoc().getVersion()).build();

        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo).select()
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
            .enableUrlTemplating(true);
    }
}
