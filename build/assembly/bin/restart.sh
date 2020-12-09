#!/bin/bash
set -e
ROOT=$(
  cd $(dirname $0)/..
  pwd
)
cd ${ROOT}
./bin/stop.sh
sleep 2
./bin/start.sh
