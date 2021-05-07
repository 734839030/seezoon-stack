# Nacos 配置中心

这里使用阿里云[ACM](https://www.aliyun.com/product/acm) 应用配置管理。

> 没错你猜对了，这个是也是**免费**的，没有任何附加条件，也是流行Nacos 实现，可以配置比对，版本管理，灰度推送，推送轨迹等便捷功能。

上面的后台部署需要手工处理配置文件比较麻烦，这个章节看项目需要，可以彻底剥离配置文件，大项目一般是有配置中心的，可以自己搭建Nacos，这里有免费的当然优先选择现有的，何况代码中没有直接依赖。

<img :src="$withBase('/images/image-20210423150358035.png')" />

该项目已经集成`nacos-config-spring-boot-starter`。静默开启和关闭，加上Nacos 配置则远程优先本地，不加则还是传统方式。

这里为了**降低依赖**没有使用Nacos的注解，所以不具备自动刷新能力，发完配置需要重启生效。

## 配置说明
```
# 在这个文件中补全Nacos 的配置
build/assembly/conf/application.properties

# 免费阿里云配置中心nacos https://acmnext.console.aliyun.com/
# 只需添加了下列配置，就可以读取配置中心变量，默认spring自带注解不刷新，发布后应用需要重启，如需自动刷新，请用Nacos自带注解
# 容器中使用把可变参数值做成环境变量即可
nacos.config.bootstrap.enable=true
nacos.config.remote-first=true
nacos.config.bootstrap.log-enable=true
nacos.config.auto-refresh=true
nacos.config.type=properties
nacos.config.data-id=com.seezoon
nacos.config.group=seezoon-admin-server
nacos.config.endpoint=acm.aliyun.com
# 很奇葩是这uuid ，不能写名字
nacos.config.namespace=555dabb9-1d2a-4ecd-9069-c377f7823236
# 推荐使用 RAM 用户的 accessKey 和 secretKey
nacos.config.access-key=xxx
nacos.config.secret-key=xxx
```