# 跨域

前后端分离项目跨域很常见，所以跨域功能也很有必要。

## 实现

已经实现配置化，具体参考配置SeezoonProperties中cors即可，开启跨域后建议详细配置允许跨域的域名allowedOrigins，保障安全。

```java
     @Override
    public void addCorsMappings(CorsRegistry registry) {
        SeezoonProperties.CorsProperties cors = seezoonProperties.getCors();
        if (cors.isEnable()) {
            registry.addMapping(cors.getMapping()).allowedOrigins(cors.getAllowedOrigins())
                .allowedHeaders(cors.getAllowedHeaders()).allowedMethods(cors.getAllowedMethods())
                .allowCredentials(cors.isAllowCredentials()).maxAge(cors.getMaxAge());
        }
    }
```

> 使用Spring Security 需要在其配置中加上跨域代码`http.cors()`才可以，框架已处理。

## Cookie SameSite 

跨域目前受这个影响，Cookie 的`SameSite`属性用来限制第三方 Cookie，从而减少安全风险。它可以设置三个值：

- Strict

  `Strict`最为严格，完全禁止第三方 Cookie，跨站点时，任何情况下都不会发送 Cookie。换言之，只有当前网页的 URL 与请求目标一致，才会带上 Cookie。

- Lax

  `Lax`规则稍稍放宽，大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外。

- None

  Chrome 计划将`Lax`变为默认设置。这时，网站可以选择显式关闭`SameSite`属性，将其设为`None`。不过，前提是必须同时设置`Secure`属性（Cookie 只能通过 HTTPS 协议发送），否则无效。

  

为了方便，我们覆写了DefaultCookieSerializer，将samesite 设置为null。

```java
    @Bean
    public DefaultCookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setSameSite(null);
        return defaultCookieSerializer;
    }
```



