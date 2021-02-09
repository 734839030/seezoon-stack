#!/bin/bash
set -e
daemon=false
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
