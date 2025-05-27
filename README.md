# dataxweb
datax、datax-web优化项目，支持Mongo6、ES8，支持容器化部署和启动


datax改动：
1、升级core/src/main/bin/中python脚本，支持python3.10
2、新增ES8.x reader驱动支持，支持ES读取
3、新增mongo-driver-sync-5.5.0驱动支持mongo6.x
4、mongodb驱动和工具兼容性修改
5、hdfsreader、oceanbasev10writer等等编译错误

datax-web改动：
1、初始化SQL调整，避免重新部署清空数据库bin/db/datax_web.sql
2、安装脚本优化，支持通过环境变量导入数据库初始化信息bin/install.sh
3、datax-admin脚本优化，支持通过环境变量导入数据库初始化信息datax-admin/src/main/bin/configure.sh、datax-admin/src/main/bin/datax-admin.sh
4、datax-executor脚本优化，解决同步任务报错，无法执行python脚本问题datax-executor/src/main/bin/env.properties、datax-executor/src/main/bin/datax-executor.sh
5、修正datax-executor脚本路径，关闭脚本自动删除
6、新增mongo-driver-sync-5.5.0驱动支持mongo6.x
7、datax-admin/pom.xml依赖优化，解决mongodb-driver-core\bson自动依赖版本过旧问题
8、放开cors().and().csrf根路径支持，url访问直接打开首页
9、mongodb驱动和工具兼容性修改
10、dockerfile、docker-compose、start.sh支持


基于以下开源项目进行适配改进：
datax：https://github.com/alibaba/DataX
datax-web：https://github.com/WeiYe-Jing/datax-web
elasticsearchreader8x：https://github.com/ysy2025/datax-elasticsearchreader8x