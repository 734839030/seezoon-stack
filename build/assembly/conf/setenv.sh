# 如果不设置，默认使用系统环境变量中的java命令
#JAVA_HOME="/usr/local/jdk"
# 远程调试功能for > jdk1.5
# -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
# for jdk version >= 11
JAVA_OPTS="-Xmx512m -Xms128m -XX:+UseG1GC -verbose:gc -Xlog:gc*,safepoint:./logs/gc.log:time,uptime:filecount=10,filesize=10M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./logs/dump"
# for 1.7 < jdk version < 11
#JAVA_OPTS="-Xmx512m -Xms128m -XX:+UseG1GC -verbose:gc -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=10M  -Xloggc:./logs/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./logs/dump"
SERVER_OTPS="--server.port=8080 --server.tomcat.basedir=. --server.tomcat.connection-timeout=10 --server.tomcat.max-connections=2000 --server.tomcat.accept-count=1000 --server.tomcat.threads.min-spare=10 --server.tomcat.threads.max=2000 --server.shutdown=graceful --spring.lifecycle.timeout-per-shutdown-phase=10s"
