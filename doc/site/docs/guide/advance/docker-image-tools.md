# 镜像工具

采用容器快速构建环境依赖，这个是**站在工具角度**来使用，针对特定项目还是利用已提供的Dockfile 做镜像发布比较正规。为了方便可以共享主机网络(包括端口)，可以加上--network=host，这样更方便。

> 如果环境是单独的，可以采用全部做成Dockfile，利用DevOps工具配置一站是流程，这里主要避免手动装软件的烦恼。

## Mysql

安装mysql，并初始化：

- 安装

  ```
  # 指定密码使用
  docker run -d -v /var/lib/mysql:/var/lib/mysql  --name mysql -e MYSQL_ROOT_PASSWORD=xxxx mysql:8.0.25
  # 空密码使用
  docker run -d -v /var/lib/mysql:/var/lib/mysql  --name mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes mysql:8.0.25
  ```

  > 支持远程访问加上共享主机网络--network=host，请设置安全密码。

- 初始化数据

  ```
  # 默认localhost访问没有限制，可以去掉-p
  docker exec -i mysql sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD"' < db/seezoon-stack.sql
  ```

常用操作：

```
# 当做mysql 客户端使用
docker run -it --rm mysql mysql -hsome.mysql.host -usome-mysql-user -p
# 进入mysql容器
docker exec -it mysql bash
# 查看日志
docker logs mysql
```

具体文档参见[Mysql Docker](https://hub.docker.com/_/mysql)

## Redis

安装：

```
docker run --name redis -d redis
```

连接：

```
# 默认链接127.0.0.1 6379，连接其他可以添加-h -p 参数来指定host和端口 
docker run -it --rm redis redis-cli -h xxx.xxx
```

具体文档参见[Redisl Docker](https://hub.docker.com/_/redis)

## Nginx

```
mkdir /usr/local/nginx
#准备一个配置文件https://raw.githubusercontent.com/nginx/nginx/master/conf/nginx.conf
docker run --name nginx -v /usr/local/nginx/nginx.conf:/etc/nginx/nginx.conf:ro -v /usr/local/nginx:/usr/local/nginx  -d nginx
```

## Java

```
docker run -d --name seezoon-admin-server -e IN_CONTAINER=true -v /data/seezoon-admin-server:/data/seezoon-admin-server openjdk:11 /data/seezoon-admin-server/bin/docker-entrypoint.sh start
```

