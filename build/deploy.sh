#!/bin/bash
# 流水线部署脚本,以seezoon-admin-server 为例子
set -e
APP_NAME="seezoon-admin-server"
mkdir -p /data/${APP_NAME}
if [ -d /data/${APP_NAME}/bin ]; then
  rm -rf bin
fi
# 产出不的bin目录复制到运行目录,conf 目录不要覆盖
tar -zxvf /home/admin/app/${APP_NAME}.tgz bin -C /data/${APP_NAME}
sh /data/${APP_NAME}/bin/restart.sh
