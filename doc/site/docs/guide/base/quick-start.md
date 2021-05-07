# 快速开始

快速搭建开发环境。

## 环境准备

本地开发环境需要安装：

- [IDEA](https://www.jetbrains.com/idea/)（😎宇宙第一无敌IDE）
- [JDK](http://jdk.java.net/archive/) (要求1.8 +，推荐11)
- [Node](https://nodejs.org/zh-cn/download/)
- [Yarn](https://yarnpkg.com/getting-started/install) （安装完node 后可以`npm install -g yarn`）
- [Git](https://git-scm.com/downloads)
- [Mysql](https://dev.mysql.com/downloads/mysql/)
- [Redis](https://redis.io/download)

## 代码下载

可以通过 IDEA `File->New->Project From Version Control `导入，也可以通过如下命令下载后导入。

>  建议fork 后到自己仓库后再导入，方便后续同步更新。

```
git clone https://github.com/734839030/seezoon-stack.git	
```



## 工程结构

采用最小分层结构，主要分为框架层级、后台、C端程序，如果觉得复杂可以直接合并。

```
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
    ├── db DB脚本目录
    ├── doc 项目文档及资料维护目录
    ├── seezoon-admin-server  管理端服务
    ├── seezoon-admin-web  后台页面目前fork上游,代码库单独维护稳定后合并
    ├── seezoon-dao    DAO层，方便管理端和C端引用
    ├── seezoon-framework  管理端和C端通用框架层(基础中间件,参数验证，安全控制)
    ├── seezoon-generator  通用代码生成器，可以单独使用也可以供其他组件继承使用
    └── seezoon-user-server  C端服务端TODO
```

## 后台配置

后端主要关注`Mysql` 和 `Redis`的配置即可，先运行起来，框架默认了很多配置，运行起来后，可以再关注细节参数配置，中小型项目不调整也是够用的。

### 数据库初始化

脚本见`db/seezoon-stack.sql`，已包含建库语句。

::: warning

默认提供的是`Mysql 8`的建库建表语句，默认字符集`utf8mb4`，`collation：utf8mb4_0900_ai_ci`。`Mysql 8` 以下，不支持`utf8mb4_0900_ai_ci`，请全局替换为`utf8mb4_general_ci`，否则执行会出现错误`Unknown collation: 'utf8mb4_0900_ai_ci`，强烈推荐使用`Mysql 8`。

:::

### 配置Mysql

 配置文件中配置`Mysql`，配置文件见 `seezoon-admin-server/src/main/resources/application-local.properties`。

```
spring.datasource.url=
spring.datasource.hikari.username=
spring.datasource.hikari.password=
```

### 配置Redis

 配置文件中配置`Redis`，配置文件见 `seezoon-admin-server/src/main/resources/application-local.properties`。

```
spring.redis.host=
spring.redis.port=
spring.redis.password=
```

> 这里开发环境默认使用单机版的redis；点击查看[ Redis Sentinel ](https://docs.spring.io/spring-data/redis/docs/2.5.0/reference/html/#redis:sentinel)配置、点击查看[Redis Cluster](https://docs.spring.io/spring-data/redis/docs/2.5.0/reference/html/#cluster)配置。

### 启动后台

执行如下类中Main方法

```
seezoon-admin-server/src/main/java/com/seezoon/admin/AdminMain.java
```

> 如果报依赖错误，可以执行parent 工程mvn package。

## 前端配置

前端工具不熟也没有关系的，基本是固定操作。

### 安装依赖

首次执行需要，后续`package.json` 中依赖更新后也需要执行下。

```
cd seezoon-stack/seezoon-admin-web
yarn install
```

### 启动前端服务

```
cd seezoon-stack/seezoon-admin-web
yarn serve 
```

**访问地址：** http://localhost:3100/

### 文件服务器(可选)

文件上传功能，需要本地模拟文件服务器。

```
cd seezoon-admin-web/test/server/upload
yarn install
yarn start
```

管理端上传文件后，可以直接访问地址static目录静态文件。

**访问地址：**  http://localhost:3001/static/xxxx





