# API 文档

通常我们会采用SpringFox + Swagger 来自动生成文档，但是Swagger 页面不是太友好，所以这里采用[SpringFox](http://springfox.github.io/springfox/) + [Knife4j](https://gitee.com/xiaoym/knife4j)。
> 通常SpringFox 只提供符合[OpenAPI3.0](https://swagger.io/specification/)的接口，Swagger和Knife4j 都是用来展示的。

## 文档开关

本地开发启动较慢，所以文档默认关，测试环境或者线上想开可以开。

```
springfox.documentation.enabled=true
springfox.documentation.auto-startup=true
```

> 文档接口和页面都是用后端停服务，所以正常访问是通过nginx转发的。

## SpringFox 配置

采用Spring 初始化化Docket 即可。

```java
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
```

## SpringFox + Swagger

本项目不使用这种，这种引入Maven依赖，默认访问地址：http://127.0.0.1:8080/swagger-ui/index.html

```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-boot-starter</artifactId>
  <version>${latest}</version>
</dependency>
```

## SpringFox + Knife4j

添加依赖即可，默认访问地址：http://127.0.0.1:8080/doc.html

```xml
<dependency>
  <groupId>com.github.xiaoymin</groupId>
  <artifactId>knife4j-spring</artifactId>
  <version>${latest}</version>
</dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-boot-starter</artifactId>
  <version>${latest}</version>
  <exclusions>
    <exclusion>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

## 常用注解

这里列举常用的用法即可，不需要弄的太复杂，文档会结合spring mvc的注解，以及JSR-303校验生成详细文档。

::: warning
Spring 接收参数，@RequestParam可以缺省，但是SpringFox，需要读取这个注解才可以生成文档，所以还是加上注解。
:::

### @Api

用于Controller 上，例如：

```java
@Api(tags = "系统参数")
```

### @ApiOperation

用户Controller 中Rest接口上，例如：

```java
@ApiOperation(value = "分页查询")
```

### @ApiModel

作用于入参和响应参对象上，例如：

```java
@ApiModel(value = "参数,一般省略value")
```

### @ApiModelProperty

作用于入参和响应参对象字段上，例如：

```java
@ApiModelProperty(value = "参数名称", required = true)
```

### @ApiParam

作用于rest接口参数上，通常是@RequestParam接收，例如：

```java
@ApiParam(value = "0:ip,1:用户名")
```

## 文档安全

需要登录才可以访问，生产环境也可以通过配置关闭文档。