# DataXWeb 优化项目

## 项目简介
基于阿里云 DataX 和 DataX-Web 的优化版本，新增对 MongoDB 6.x 和 Elasticsearch 8.x 的支持，并优化了容器化部署方案。

基础项目：
- DataX: https://github.com/alibaba/DataX
- DataX-Web: https://github.com/WeiYe-Jing/datax-web
- Elasticsearch Reader 8.x: https://github.com/ysy2025/datax-elasticsearchreader8x

## DataX 核心改进

### 核心功能升级
- 🐍 升级 Python 脚本支持 3.10 版本 (`core/src/main/bin/`)
- 🔍 新增 Elasticsearch 8.x Reader 驱动
- 🍃 新增 MongoDB 同步驱动 5.5.0 (支持 MongoDB 6.x)

### 兼容性修复
- 🔧 MongoDB 驱动和工具链兼容性优化
- 🛠️ 修复 HDFSReader、OceanBaseV10Writer 等模块的编译错误

## DataX-Web 改进

### 安装部署优化
- 🗃️ 初始化 SQL 调整 (避免重复部署清库) `bin/db/datax_web.sql`
- ⚙️ 安装脚本支持环境变量配置数据库 `bin/install.sh`
- 🔌 Admin/Executor 脚本支持环境变量配置:
  - `datax-admin/src/main/bin/*.sh`
  - `datax-executor/src/main/bin/*.sh`

### 功能增强
- 🐛 修复 Executor 同步任务报错问题 (Python 脚本执行)
- 📁 修正脚本路径管理，禁用自动删除
- 🍃 新增 MongoDB 6.x 驱动支持
- 🔗 解决 MongoDB 驱动版本冲突问题 (`pom.xml` 依赖优化)

### 用户体验改进
- 🌐 放开 CORS 和 CSRF 根路径支持，直接访问首页
- 🐳 完善 Docker 支持:
  - Dockerfile 优化
  - docker-compose 配置
  - 启动脚本 (`start.sh`)

## 部署方案
支持传统部署和容器化部署，提供完整的环境变量配置方案，便于 CI/CD 集成。

> 该项目适用于需要现代化数据同步解决方案的场景，特别适合使用 MongoDB 6+ 和 ES 8+ 的技术栈。
