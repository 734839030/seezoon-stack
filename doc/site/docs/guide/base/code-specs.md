# 代码规范

::: tip
**建议都配置**，可以实现Java 和前端代码自动格式化，统一编码标准。
:::

这里以IDEA 配置举例，在IDEA 插件市场中分别以下安装插件。

## Lombok（必须）

[Lombok](https://projectlombok.org/features/all) 是一种 Java™ 实用工具，可用来帮助开发人员消除 Java 的冗长，尤其是对于简单的 Java 对象（POJO）。它通过注解实现这一目的。

> JDK 14 以后版本中有有类似功能，Record关键字。

安装完毕即可。

## Save Actions（可选）

保存代码自动格式化，配置如下图：

<img :src="$withBase('/images/image-20210422201011887.png')" />

## Eclipse Code Formatter（可选）

 使用阿里[P3C](https://github.com/alibaba/p3c) 格式化规范，已放置在`build/eclipse-codestyle.xml`，可以自行更新最新，配置如下图：

<img :src="$withBase('/images/image-20210422201421446.png')" />

## EsLint （IDEA 内置)

前端格式化，配置如下图：

<img :src="$withBase('/images/image-20210422201755954.png')" />

