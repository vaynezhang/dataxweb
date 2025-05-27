#!/bin/bash
# 启动 DataX-Web
#export DB_HOST=192.168.80.215
#export DB_PORT=3306
#export DB_USERNAME=root
#export DB_PASSWORD=root_pswd
#export DB_DATABASE=dataxweb

cd /datax-web/bin
sh install.sh -f
sh start-all.sh
# 保持容器运行
tail -f /dev/null