# 代码生成

项目已**表结构**为生成材料，我们只需要维护好表结构，就可以生成完整的CRUD代码，菜单，前后端权限控制，自动接口文档等。

> 代码生成在日常开发中很常用，可以深度学习下`seezoon-generator`模块，打造自己的生成器。

## 基本要求

- 表名至少包含一个`_`，如`sys_param`，`sys`为模块名，`param`为功能名。

- 一定要有主键，联合主键代码只会取第一个为主键，**日常不建议联合主键**。

- 字段类型如下，基本够用，如需要添加可以扩展该枚举类。

  ```java
  /**
   * 默认都用包装类型
   *
   * 常用数据库字段类型->mybatis 类型-> java 类型，这里生成避免sql过长，不加入mybatis type
   * <p>
   * mybatis 类型可以不用，mybatis自动推导的，这里预留。
   *
   * @author hdf
   */
  public enum ColumnDataType {
      // @formatter:off
      VARCHAR("varchar", "VARCHAR", String.class.getSimpleName()),
      CHAR("char", "CHAR", String.class.getSimpleName()),
      TINYINT("tinyint", "TINYINT", Integer.class.getSimpleName()),
      INT("int", "INTEGER", Integer.class.getSimpleName()),
      INTEGER("integer", "INTEGER", Integer.class.getSimpleName()),
      BIGINT("bigint", "BIGINT", Long.class.getSimpleName()),
      DOUBLE("double", "DOUBLE", Double.class.getSimpleName()),
      FLOAT("float", "REAL", Float.class.getSimpleName()),
      // 导入时间
      DATETIME("datetime", "TIMESTAMP", Date.class.getSimpleName()),
      DATE("date", "DATE", Date.class.getSimpleName()),
      TIME("time", "TIME", Date.class.getSimpleName()),
      TIMESTAMP("timestamp", "TIMESTAMP", Date.class.getSimpleName()),
      // 导入BigDecimal
      DECIMAL("decimal", "DECIMAL", BigDecimal.class.getSimpleName()),
      NUMERIC("numeric", "DECIMAL", BigDecimal.class.getSimpleName()),
      TEXT("text", "LONGVARCHAR", String.class.getSimpleName()),
      TINYTEXT("tinytext", "VARCHAR", String.class.getSimpleName()),
      MEDIUMTEXT("mediumtext", "LONGVARCHAR", String.class.getSimpleName()),
      LONGTEXT("longtext", "LONGVARCHAR", String.class.getSimpleName()),
      ;
      ....
  }
  ```

## 关键字段说明

- 主键

  主键名可以为id，也可以定义为其他名字，代码如果不是id，生成代码会自动添加`setId`、`getId`，不是自增也可以的。

- 索引

  默认方案会有查询，针对唯一索引会生成唯一性**校验**。

- 建议默认字段，都是可选的，默认这些字段在插入和修改会自动维护。

  ```
    `status` tinyint NOT NULL COMMENT '状态1.启用,0.停用',
    `create_by` int NOT NULL COMMENT '创建者',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by` int NOT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息'
  ```

- DB 约束

  长度和非空都会生成前后端验证。

- 字段名及备注

  字段名为采用驼峰命名法生成java字段名，备注会在界面上展示。

## 前端组件

前端主要单选、复选框多选、下拉多选、整形、小数（默认2位小数）、图片上传、文件上传、富文本、分页表格、参数验证。

> 复选框多选目前只生成代码，需要手动维护，因为接受的数组，插入和回显因项目而异，如果是简单多选，可以使用下拉多选，项目默认是逗号分隔多选。

## 生成器

`seezoon-generator`集成了mybaits-generator maven 插件，配置文件见：`build/mybatis-generator-config.xml`，修改配置执行插件命令即可生成原生代码。

这里主要介绍自定义生成逻辑：

- 独立打包后使用`seezoon-generator-xxxx-exec.jar`, 平级（或平级config下）维护如下application.properties，执行java -jar seezoon-generator-xxxx-exec.jar 

  ```properties
  #打成jar后会排除，用jar生成application.properties 放在和jar平级或者平级的config 目录即可
  spring.datasource.url=jdbc:mysql://127.0.0.1:3306/seezoon-stack?useUnicode=true&&characterEncoding=utf8&serverTimezone=GMT%2B8&connectTimeout=1000
  spring.datasource.username=root
  spring.datasource.password=
  ```

- 集成到`seezoon-admin-server`中使用，页面上的生成器逻辑实际由该模块提供。

> 生成路径在`target/seezoon-generated`, 一个模块要独立执行，又要可以被别的模块引入，需要添加标识符，不然别的模块依赖的代码不正确。

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <configuration>
    <!-- 可以执行jar不能被其他组件依赖,加上单独生成可执行jar 后缀加上exec-->
    <classifier>exec</classifier>
  </configuration>
</plugin>
```

