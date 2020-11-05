#!/bin/bash
ROOT=$(
  cd $(dirname $0)/..
  pwd
)
cd ${ROOT}
./bin/stop.sh
sleep 1
./bin/start.sh
