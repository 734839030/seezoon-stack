# 介绍

[Seezoon-Stack](https://github.com/734839030/seezoon-stack)以**快速开发**为目的，在开发速度和代码结果上做出一定取啥，无论如何，你将看到**非常地道**的 Java 常用开发框架使用，`Seezoon-Statck`采用主流开发框架，无论打包、编译、部署都按着大公司的标准完成并不断逐步完善。

[[toc]]

## 初衷

作为一个IT程序员，日常难免会帮朋友、亲戚做一些小项目，或者基于一个持续学习的目的，所以一个稳定，高效的开发平台很有必要，目前开源社区也有很多类似优秀的全家桶框架，大多太庞大，功能太繁多，学习成本巨高，比如一些基于`dubbo`、`spring-cloud`等搭建的，可能并不太适合快速开发及日常项目使用，在早期创业公司也收益颇多，所以决定再次手写一款使用最新技术的快速开发框架，以便日常所需，并长期升级。

>  在此框架之前，在`spring boot`还未普及的情况下，之前开源并给到朋友公司使用的，基于纯`spring`的快速开发平台[seezoon-framework-all](https://gitee.com/huangdf/seezoon-framework-all)也获得一些好评。

## 目标

**可以快速开始中小项目开发**

根据日常经验，大部分项目都会基于微信体系来完成，尤其是小程序，该项目C端功能主要会侧重于微信体系的基础封装，如公众号，小程序登录、支付，消息订阅与推送。

> 基于微信体系有一点需要留意，如果程序被恶意举报很有可能会短暂封禁，根据经验，为了保障业务连续性，通常会采用[Uniapp](https://uniapp.dcloud.io/) 编写，可以一套代码多端运行，在遇到封禁时候可以采用H5，或者APP方式继续使用。



**学习目的**

快速平台功能完善稳定后，初步计划完成两个专题的知识代码及文档整理：

- 亿级用户业务平台的设计，如同/跨城容灾，分库分表、单元化架构等。
- 云原生架构相关知识，如`k8s + service mesh`上部署，容灾，服务治理，灰度发布等。

## 原则

- 简洁实用：只提供最基本、中小项目必须的功能，依赖降到最低。
- 不二次封装：针对使用的开源技术，非必要场景不二次封装，保持开源组件的原汁原味。
- 安全高效：参数配置，安全、部署等方面也尽量提供最优的方案。

## 技术选型

> 有基础的前后端开发经验基本可以对着已有模块完成一个CRUD例子，全面系统学习一下知识，更容易扩展，也可以提高自己。

**Seezoon Stack** 采用当下最前沿前后端(Spring Boot + Vue3 + Antdv2 + Vite)技术栈完成。

后台**主要**框架：

| 框架名称        | 框架地址                                                     | 说明           |
| --------------- | ------------------------------------------------------------ | -------------- |
| Spring Boot     | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) | 自动装配       |
| Spring Session  | [https://spring.io/projects/spring-session-data-redis](https://spring.io/projects/spring-session-data-redis) | 登录态管理     |
| Spring Security | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security) | 登录及权限控制 |
| Mybatis         | [https://mybatis.org/mybatis-3/zh/index.html](https://mybatis.org/mybatis-3/zh/index.html) | 持久层         |
| Springfox       | [https://github.com/springfox/springfox](https://github.com/springfox/springfox) | openAPI 3 文档 |

前台**主要**框架：

> 前端主体框架采用  [Vue-Vben-Admin](https://github.com/anncwb/vue-vben-admin)，对原作者表示感谢🙏。

| 框架名称         | 框架地址                                                     | 说明            |
| ---------------- | ------------------------------------------------------------ | --------------- |
| Vue 3            | [https://v3.cn.vuejs.org/guide/introduction.html](https://v3.cn.vuejs.org/guide/introduction.html) | 双向绑定        |
| Vite             | [https://cn.vitejs.dev/](https://cn.vitejs.dev/)             | 构建工具        |
| Ant Design Vue 2 | [https://2x.antdv.com/components/overview-cn/](https://2x.antdv.com/components/overview-cn/) | 组件            |
| TypeScript 4     | [https://www.typescriptlang.org/zh/](https://www.typescriptlang.org/zh/) | JavaScript 超集 |
| Windi Css        | [https://windicss.org/guide/](https://windicss.org/guide/)   | css 辅助类      |
| Iconify          | [https://iconify.design/](https://iconify.design/)           | 图标库          |

## 功能特性

> 可直接预览网站。

- **已完成功能：**

  - 登录及权限控制
  - 个人中心
  - 部门管理
  - 用户管理
  - 角色管理
  - 菜单管理
  - 系统参数
  - 数据字典
  - 文件管理
  - 登录日志
  - 👍代码生成
  - 👍自动文档
  - 👍数据权限

- **TODO功能：**

  - 微信体系功能

  

## 浏览器支持

本地开发推荐使用`Chrome 80+`浏览器,在火狐浏览器进行开发相对卡顿。

支持现代浏览器, IE 不支持

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt=" Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt=" Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|                         not support                          |                       last 2 versions                        |                       last 2 versions                        |                       last 2 versions                        |                       last 2 versions                        |

更多浏览器可以查看 [Can I Use Es Module](https://caniuse.com/?search=ES%20Module)

