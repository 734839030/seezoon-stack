#!/bin/bash
BUILD_PATH=$(
  cd $(dirname $0)/..
  pwd
)
echo "build path is ${BUILD_PATH}"
cd ${BUILD_PATH}
mvn -s build/maven-settings.xml clean package
