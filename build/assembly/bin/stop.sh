#!/bin/bash
set -e
ROOT=$(
  cd $(dirname $0)/..
  pwd
)
cd ${ROOT}

APP_NAME="${ROOT##*/}"
pid=0
#优雅关机等待时间
waitSeconds=10
checkpid() {
  pid=$(ps -ef | grep ${ROOT}/bin/${APP_NAME} | grep -v grep | awk '{print $2}')
  pid=${pid:=0}
}

stop() {
  checkpid
  if [ $pid -eq 0 ]; then
    echo "${APP_NAME} is not running"
  else
    echo "${APP_NAME} stop..."
    kill $pid
    while [ $waitSeconds -gt 0 ]; do
      checkpid
      if [ $pid -gt 0 ]; then
        sleep 1
        waitSeconds=$(($waitSeconds - 1))
      else
        echo "${APP_NAME} stoped "
        exit 0
      fi
    done

    checkpid
    if [ $pid -gt 0 ]; then
      kill -9 $pid
      echo "${APP_NAME} force stoped"
    else
      echo "${APP_NAME} stoped"
    fi
  fi
}
stop
