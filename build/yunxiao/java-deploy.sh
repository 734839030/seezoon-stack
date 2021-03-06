#!/bin/bash
# 云效2020流水线部署脚本,以seezoon-admin-server 为例子
set -e
APP_NAME="seezoon-admin-server"
mkdir -p /data/${APP_NAME}
if [ -d /data/${APP_NAME}/bin ]; then
  rm -rf bin
fi

# 目录不存在则为首次部署
if [ ! -d /data/${APP_NAME}/conf ]; then
  tar -zxvf /home/admin/app/${APP_NAME}.tgz -C /data/${APP_NAME}
else
  # 产出物的bin目录复制到运行目录,conf 目录不要覆盖
  tar -zxvf /home/admin/app/${APP_NAME}.tgz -C /data/${APP_NAME} ./bin
fi
sh /data/${APP_NAME}/bin/restart.sh
