package com.seezoon.framework.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.swagger.annotations.Api;
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
 *
 * 通过{@code @ComponentScan }扫描advice 也可以使用@import
 *
 * @author hdf
 */
@Configuration
@EnableConfigurationProperties(FrameworkProperties.class)
@PropertySource({"classpath:default-framework.properties"})
@ServletComponentScan("com.seezoon.framework.servlet")
@ComponentScan("com.seezoon.framework.web.advice")
@EnableOpenApi
public class SeezoonFrameworkAutoConfiguration {

    @Autowired
    private FrameworkProperties frameworkProperties;

    /**
     * @see <a>http://127.0.0.1:8080/swagger-ui/index.html</a>
     * @see <a>http://127.0.0.1:8080/doc.html</a>
     *
     * @see <a>http://springfox.github.io/springfox/docs/current/</a>
     * @return
     */
    @Bean
    public Docket openApiStore() {
        ApiInfo apiInfo = new ApiInfoBuilder().title(frameworkProperties.getName())
            .description(frameworkProperties.getDescription()).contact(new Contact(frameworkProperties.getName(),
                frameworkProperties.getUrl(), frameworkProperties.getAuthor()))
            .version(frameworkProperties.getVersion()).build();

        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo).select()
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
            .enableUrlTemplating(true);
    }
}
