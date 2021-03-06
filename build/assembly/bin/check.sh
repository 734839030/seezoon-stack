#!/bin/bash
set -e
ROOT=$(
  cd $(dirname $0)/..
  pwd
)
cd $ROOT
APP_NAME="${ROOT##*/}"
pid=0
checkpid() {
  pid=$(ps -ef | grep ${ROOT}/bin/${APP_NAME} | grep -v grep | awk '{print $2}')
  pid=${pid:=0}
}
checkpid
if [ $pid -eq 0 ]; then
  echo "${APP_NAME} not running"
  exit 1
else
  echo "${APP_NAME} is running,pid is $pid"
fi
