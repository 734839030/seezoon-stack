package com.seezoon.framework.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "framework")
public class FrameworkProperties {
    private String name;
    private String version;
    private String url;
    private String description;
    private String author;

}
