# 部署

## 目录结构

```
/data/
├── cert
├── seezoon-admin-server  # 后台产出物
│   ├── bin
│   ├── conf
│   ├── logs
│   └── work
├── seezoon-admin-web    # 前端产出物
│   ├── assets
│   └── resource
└── upload-server  文件上传目录，默认使用磁盘文件，使用OSS则不需要.
```

## 手动部署

### 后台

采用[maven-assembly-plugin](http://maven.apache.org/plugins/maven-assembly-plugin/)生成构建物，可以直接生成生产部署的目录结构，方便DevOps 集成。

```
cd seezoon-admin-server
mvn clean package
```

**产出物目录**

`seezoon-admin-server/target/seezoon-admin-server`

**只需要维护产出物`conf `目录的`application.properties `即可**，然后就可以发布了。

>  配置文件与环境分离，`maven-jar-plugin` 打包产出物`jar`会排出`resources`目录如下文件：

```
application-local.properties
logback-spring.xml
```

>  如果不介意各个环境配置都在工程中那就很方便，使用spring boot 提供的`--spring.profiles.active`，在启动命令中选择配置文件。

<img :src="$withBase('/images/image-20210423121842460.png')" />

### 前台

```
cd seezoon-admin-web
yarn build
```

**产出物**

`seezoon-admin-web/dist` 中文件发布到线上nginx 目录即可，该工程nginx 配置如下，仅供参考。

```
upstream seezoon-admin-server {
    server 127.0.0.1:8080 max_fails=3 fail_timeout=10s;
}

server {
    listen       80;
    server_name  stack.seezoon.com;
    rewrite ^(.*)$  https://$host$1 permanent;
}

server {
    listen       443;
    server_name  stack.seezoon.com;
    ssl on;
    ssl_certificate   /data/cert/stack.seezoon.com.pem; 
    ssl_certificate_key  /data/cert/stack.seezoon.com.key;
    ssl_session_timeout 5m;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;

    # api
    location ^~ /api/ {
        proxy_redirect off;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Real-PORT $remote_port;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass http://seezoon-admin-server/;
    }
    # 文件服务
    location ^~ /file/ {
        access_log off;
        alias /data/upload-server/;
    }

    # 静态资源
    location / {
        access_log off;
        root /data/seezoon-admin-web/;
        index index.html index.htm;
    }

}
```

> 可选静态资源压缩配置，放在`nginx.conf http` 节点下。

```
# 打开gzip 效果更佳
gzip on;
gzip_min_length 1k;
gzip_buffers 4 16k;
gzip_http_version 1.0;
gzip_comp_level 6;
gzip_types text/plain application/javascript application/x-javascript text/css application/xml text/javascript application/x-httpd-php image/jpeg image/gif image/png;
gzip_vary off;
gzip_disable "MSIE [1-6]\.";
```

## [云效2020](https://www.aliyun.com/product/yunxiao/public) 部署

> 不是给阿里打广告，是真的好用，个人使用基本免费，可以使用完整DevOps功能，需求看板，测试计划、灰度发布，回退等。

### 后台

后台最短路径 **流水线**配置，添加源代码->构建->主机部署(不要求是阿里主机).

<img :src="$withBase('/images/image-20210423130133351.png')" />

主机部署，部署脚本见`build/yunxiao/java-deploy.sh`，主机如果conf目录存在了，则不会覆盖，后面会讲用配置中心的方式(小项目依赖多了划不来，可以当学习使用)。

<img :src="$withBase('/images/image-20210423130548116.png')" />

### 前台

前台最短路径 **流水线**配置：

<img :src="$withBase('/images/image-20210423144532907.png')" />

部署脚本见`build/yunxiao/page-deploy.sh`

<img :src="$withBase('/images/image-20210423144643042.png')" />

最终的效果就是动动小手👌就部署了，也可以使用git hooks，基于代码和分支变动自动触发，非常酸爽。

<img :src="$withBase('/images/image-20210423174851689.png')" />

