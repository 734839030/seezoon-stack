package com.seezoon.framework.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * event listener 通过spring.factories 加载
 *
 * @author hdf
 */
@Configuration
@EnableConfigurationProperties(FrameworkProperties.class)
@PropertySource({"classpath:default-framework.properties"})
@ServletComponentScan("com.seezoon.framework.servlet")
// 扫描advice 也可以使用@import
@ComponentScan("com.seezoon.framework.web.advice")
@EnableOpenApi
public class SeezoonFrameworkAutoConfiguration {

    @Autowired
    private FrameworkProperties frameworkProperties;

    /**
     * @see <a>http://127.0.0.1:8080/swagger-ui/index.html</a>
     * @return
     */
    @Bean
    public Docket openApiStore() {
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(new ApiInfoBuilder().title(frameworkProperties.getName())
                .description(frameworkProperties.getDescription())
                .contact(new Contact(frameworkProperties.getName(), frameworkProperties.getUrl(),
                    frameworkProperties.getAuthor()))
                .version(frameworkProperties.getVersion()).build())
            .select().paths(PathSelectors.any()).build().ignoredParameterTypes(ApiIgnore.class)
            .enableUrlTemplating(true);
    }

}
