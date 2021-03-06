#!/bin/bash
# 云效2020页面流水线部署脚本
set -e
APP_NAME="seezoon-admin-web"
mkdir -p /data/${APP_NAME}

tar -zxvf /home/admin/app/${APP_NAME}.tgz -C /data/${APP_NAME}
