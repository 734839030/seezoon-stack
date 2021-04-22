#!/bin/bash
set -e
ROOT=$(
  cd $(dirname $0)/..
  pwd
)
cd ${ROOT}

APP_NAME="${ROOT##*/}"

# spring 扩展配置文件优先级高
CONF_OPTS="--spring.config.additional-location=./conf/"
pid=$(ps -ef | grep ${ROOT}/bin/${APP_NAME} | grep -v grep | awk '{print $2}')
pid=${pid:=0}

if [ $pid -gt 0 ]; then
  echo "${APP_NAME} is already running"
  exit 1
fi

# conf/setenv.sh
# JAVA_HOME=""
# JAVA_OPTS=""
# SERVER_OTPS=""

if [ -r conf/setenv.sh ]; then
  source conf/setenv.sh
fi

if [ ! -d logs ]; then
  mkdir logs
fi

RUN_JAVA=java
if [ -n "${JAVA_HOME}" ]; then
  RUN_JAVA=${JAVA_HOME}/bin/java
  echo "JAVA_HOME=${JAVA_HOME}"
fi
if [ -n "${JAVA_OPTS}" ]; then
  echo "JAVA_OPTS=${JAVA_OPTS}"
fi
echo "***      Print Application Start Parameters      ***"

if [ -n "${SERVER_OTPS}" ]; then
  SERVER_OTPS="${SERVER_OTPS} ${CONF_OPTS}"
else
  SERVER_OTPS="${CONF_OPTS}"
fi

echo "SERVER_OTPS=${SERVER_OTPS}"

if [ "${IN_CONTAINER}" = true ]; then
    $RUN_JAVA -jar ${JAVA_OPTS} ${ROOT}/bin/${APP_NAME}*.jar ${SERVER_OTPS} >logs/catalina.out 2>&1
else
    nohup $RUN_JAVA -jar ${JAVA_OPTS} ${ROOT}/bin/${APP_NAME}*.jar ${SERVER_OTPS} >logs/catalina.out 2>&1 &
fi
sleep 10
./bin/check.sh
tail -n 20 logs/seezoon.log
echo "Application Started"
