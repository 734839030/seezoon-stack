#!/bin/bash
set -e

ROOT=$(
  cd $(dirname $0)
  pwd
)
cd ${ROOT}
case $1 in
"start")
  source start.sh
  ;;
"stop")
  source stop.sh
  ;;
"restart")
  source restart.sh
  ;;
*)
  exec "$@"
  ;;
esac
