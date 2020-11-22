

# 1、介绍

## 1.1、背景

该项目以快速开发为目的，在开发速度和代码结果上做出一定取啥，无论如何，你将看到**最地道**的 Java 常用开发框架使用。`seezoon-statck`采用主流开发框架，无论打包、编译、部署都按着大公司的标准完成并不断逐步完善，在此框架之前，在`spring boot`还未普及的情况下，之前开源并给到朋友公司使用的，基于纯`spring`的快速开发平台[seezoon-framework-all](https://gitee.com/huangdf/seezoon-framework-all)也获得一些好评。作为一个IT程序员，日常难免会帮朋友、亲戚做一些小项目，或者基于一个持续学习的目的，所以一个稳定，高效的开发平台很有必要，目前开源社区也有很多类似优秀的全家桶框架，大多太庞大，功能太繁多，学习成本巨高，比如一些基于`dubbo`、`spring-cloud`等搭建的，可能并不太适合快速开发及日常项目使用，早期非常喜欢`jeesite`，在早期创业公司也收益颇多，所以决定再次手写一款使用最新技术的快速开发框架，以便日常所需，并长期升级。

## 1.2、功能规划

1. 管理端：包含数据字段，权限控制，用户管理，角色管理，数据权限等；
2. C端：小程序及公众号、H5的登录，微信支付；
3. 代码生成：生成前后端代码，支持权限控制，丰富的生成字段，如图像，富文本等，自动表单验证等；

## 1.3、一些取舍

1. 在快速开发的前提下，无法像阿里P3C要求一样，比如对象的分层级转换，这里基本会使用DB对象完成CRUD,其余业务功能可以自己在使用`Query`,`VO`，`DTO`等保持良好的习惯；

2. `maven`也没拆得特别碎，如果需要的可以fork后自己拆分，比如全局依赖可以在建一个`module`,`service` 可以单独再建`module`;

# 2、框架介绍

## 2.1、框架结构

先简单介绍框架层次职能，后续对每层的要点会详细介绍。

```ASN.1
.
└── seezoon-stack   parent工程
    ├── build 构建所需文件
    │   ├── assembly    生成产出物结构
    │   │   ├── bin     项目脚本
    │   │   ├── conf    配置目录
    │   │   ├── logs    日志目录
    │   │   └── assembly.xml     maven assembly 打包插件描述文件(不打包到产出物)
    │   ├── build.sh    构建脚本
    │   ├── eclipse-codestyle.xml  P3C代码格式,可以自行更新
    │   ├── maven-settings.xml     maven的setting配置(可选)
    │   └── mybatis-generator-config.xml  原生mybatis-generator 描述文件(可选)
    ├── doc 项目文档及资料维护目录
    ├── seezoon-admin-server  管理端服务
    ├── seezoon-dao    DAO层，方便管理端和C端引用
    ├── seezoon-framework  管理端和C端通用框架层(基础中间件,参数验证，安全控制)
    ├── seezoon-generator  通用代码生成器，可以单独使用也可以供其他组件继承使用
    └── seezoon-user-server  C端服务端
```

## 2.2、使用准备

>  代码格式及代码检查已[P3C](https://github.com/alibaba/p3c) 为标准。

- `Eclipse`中直接使用`build/eclipse-codestyle.xml`

- `Idea` 中安装`Eclipse Code Formatter`导入`build/eclipse-codestyle.xml`,同时`Save Actions`插件，便于在自动格式化.

代码格式检查，可以再提交时候自动检查，安装`Alibaba Java Coding Guidelines`插件，也可以使用`PMD maven`插件检查，在开发阶段的自我约束.

- 自行安装`lombok`插件



## 2.3、build/assembly说明

在`conf/setenv.sh`,我们将一些`spring boot `的参数抽出来，比如端口、连接池、超时控制、优雅关机、GC参数；

在`conf/application.properties`中指定`logging.config`，让`logback-spring.xml`外置到`conf`，这样可以利用`logback`自动刷新能力，动态调整日志配置，方便稳定定位。

在`bin/start.sh`中我们让`application.properties`外置到`conf`目录,可以配置内部一些配置参数,**同时我们约定项目部署根目录一定是执行jar的前缀**，jar有版本号，没有关系，具体可以看脚本内部，可以根据自己情况调整；

## 2.4、`seezoon-stack` 说明

该工程为`maven pom`，作为其他模块的父工程，使用的是`plugins`配置插件会被子模块直接继承使用，如果不想被子模块继承可以使用`pluginManagement`,和`dependencies` 和 `dependencyManagement`非常类似;重要`properties`在该`pom维护`。

**全局使用到的maven 插件介绍：**

1. **`versions-maven-plugin`**

   一键更新子模块的父版本号版本号，执行如下`maven`命令即可.

   ```
   mvn versions:set -DnewVersion=1.0.1-SNAPSHOT
   ```

   提示：如果generateBackupPoms设置为true,则会生成备份`pom`,需要执行`commit`

   ```
   # 更改版本号
   mvn versions:set -DnewVersion=1.0.1-SNAPSHOT
   mvn versions:commit
   
   #更改过程中想回退则使用
   mvn versions:set -DnewVersion=1.0.1-SNAPSHOT
   mvn versions:revert
   
   ```

2. **`maven-javadoc-plugin`**

   `Java doc`写的标准则可以生成`java doc`文档.

3. **`maven-source-plugin`**

   生成源代码包。

4. **`jacoco-maven-plugin`**

   生成测试覆盖率，执行如下命令,报告将生成在各模块的`target/site/jacoco`

   ```
    mvn clean test && mvn jacoco:report
   ```

## 2.5 、`seezoon-framework`说明

默认参数`default-framework.properties`配置再这里(该文件名不可为`application.properties`)，可以被外部属性覆盖，这里默认参数皆为一些最佳实践，可以根据使用情况调整，尽量减少外部模块依赖时候的参数配置。

改模块主要完成：

1. 通用API返回结果统一,错误码约定;

2. 基于`jsr303`参数验证结果处理；

3. 请求埋点，返回头添加`X-Trace-Id`,通过`MDC`在日志中埋入线程ID;

4. Debug模式下出入参数打印；

5. 通用组件改写；

   - Redis 序列化方式
   - RestTemplate 线程池及默认属性约定 TODO
   - Spring Boot 异步线程调整 TODO

6. 启动Banner；

   启动banner,通过ascii码放置在`banner.txt`中，可以在[txt2ascii](https://www.bootschool.net/ascii)及[image2ascii](http://www.makepic.net/tool/image2ascii.html)网站生成，比如来个性感美女图都挺好😁。

7. 文档生成；

   基于`springfox3.0,openAPI3 `的方式，文档开关，可以在生成环境配置关闭；

   ```
   springfox.documentation.enabled=false
   springfox.documentation.auto-startup=false
   ```

   文档地址，该文档静态采用webjar的方式，在权限拦截，如`spring Security`中可以考虑放行，或者不放行也可以，根据自己需求。

   ```
   UI美化过的:http://127.0.0.1:8080/doc.html
   原生swagger:http://127.0.0.1:8080/swagger-ui/index.html
   ```

## 2.6、`seezoon-dao`说明

- 公共DAO层，基本CRUD皆为代码生成，引用该层的模块自动开启事务。

- DB默认参数在`default-datasource.properties`,同理内部属性值可以被外部覆盖。

- 以前一直使用`Druid`连接池，但新版本`spring boot`直接默认`HikariDataSource`,未来可能成为主流，因为`Druid`的优点在于监控及自定义插件，我们目前没有这些需求,监控可以用云厂商DB，或者开启mysql的慢日志查询来定位。
- `HikariDataSource`启动不会默认初始化池子(也没有参数控制)，这个很不好，未来可能会变，目前我们在该组件自动配置时候会初始化池子，并采用`retry`的模型一致重试。
- 通用数据权限会基于mybatis拦截器插件改写sql TODO

**关键代码解释：**

**通用DB实体父类，框架默认一些如下字段，如果不需要则建表不加这些字段即可。**

```
/**
 * 数据库实体基类，框架默认字段，已实际表为准
 *
 * @author hdf
 */
@Data
public class BaseEntity<PK> {

    /**
     * 主键只支持单一主键，数值或者字符串，数据库表必须存在名为ID的主键列
     */
    @ApiModelProperty("*内置*-主键")
    private PK id;
    /**
     * 数据状态 一般表建议不要这个字段，设计上没有软删除场景，如果有就用
     *
     * {@link com.seezoon.dao.framework.constants.EntityStatus}
     *
     * 使用Integer 写代码更方便
     *
     * 保存方法自动处理
     * </pre>
     */
    @ApiModelProperty("*内置*-状态")
    @Max(127)
    @Min(-128)
    private Integer status;
    /**
     * 保存方法自动处理
     */
    @ApiModelProperty("*内置*-创建人")
    private Integer createBy;
    /**
     * 保存方法自动处理
     */
    @ApiModelProperty("*内置*-创建时间")
    private Date createTime;
    /**
     * 更新方法自动处理
     */
    @ApiModelProperty("*内置*-更新人")
    private Integer updateBy;
    /**
     * 更新方法自动处理
     */
    @ApiModelProperty("*内置*-更新时间")
    private Date updateTime;

    @ApiModelProperty("*内置*-备注")
    @Size(max = 255)
    private String remarks;
}

```

**通用DAO,会有一层基于`jsr303`基本的校验，不用担心上层参数漏传造成全表扫描；在添加和更新场景，只简单的验证空，因为上层会自动验证实体中对象，这里约定不在冗余校验，因为DB自身还有一层保护，不会参数数据错乱.**

```
public interface CrudDao<T extends BaseEntity<PK>, PK> extends BaseDao {

    /**
     * 通用删除，实际线上一般不给删除权限，无意义，可以根据项目情况注释掉
     *
     * @param ids
     * @return
     */
    int deleteByPrimaryKey(@NotEmpty PK... ids);

    /**
     * 插入
     *
     * @param records
     * @return
     */
    int insert(@NotEmpty T... records);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(@NotNull PK id);

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    List<T> selectByCondition(QueryCondition condition);

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(@NotNull T record);

    /**
     * 主键全字段更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(@NotNull T record);
}
```

## 2.7、seezoon-generator说明

默认集成了`mybatis-generator-maven-plugin`插件，可以修改`build/mybatis-generator-config.xml`然后执行如下命令：

```
mvn mybatis-generator:generate
```

生成文件在`target/generated-sources`下。

