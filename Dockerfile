#docker build -t datax-local:0.1 .
#docker run -it -p 8090:9527 datax-local:0.1 /bin/bash

# 使用 CentOS 8 基础镜像
FROM centos:8

# 1. 替换已失效的 CentOS 8 源（官方源已停止维护）
RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-* && \
    sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*

# 2. 系统时区设置
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /app

COPY python310.tar.gz /app
COPY datax-202309-250522.tar.gz /app
COPY datax-web-2.1.2-250521.tar.gz /app

WORKDIR /app
RUN tar -xvf python310.tar.gz
RUN cp -r python310/* /usr/local/ && rm -rf python310
RUN cd /usr/local/bin && ln -sf python3.10 python && ln -sf python3.10 python310
RUN /usr/local/bin/python3 -m pip install --upgrade pip
# 安装 DataX DataX-Web 依赖
RUN dnf install -y lsof net-tools wget tree unzip mariadb maven java-1.8.0-openjdk && rm -rf /var/lib/apt/lists/*

# 解压 DataX
RUN tar -zxvf datax-202309-250522.tar.gz -C /

# 解压 DataX-Web
RUN tar -zxvf datax-web-2.1.2-250521.tar.gz -C /
RUN mv /datax-web-2.1.2 /datax-web

# 设置环境变量
ENV PATH="/datax/bin:/usr/local/bin/:${PATH}"

# 复制启动脚本
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

# 设置默认命令
CMD ["/app/start.sh"]