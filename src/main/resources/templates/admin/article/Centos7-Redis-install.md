### 具体的安装方式如下（基于centos 7）：

#### 我自己喜欢的安装方式：

```
# 从网站上下载 redis-3.2.11.tar.gz 安装包；
> cd /zing/tools/
> tar -zxvf redis-3.2.11.tar.gz
> make
> make PREFIX=/zing/trad/redis. install

# 命令脚本
# redis-benchmark   压力测试工具（测试AOF日
# redis-check-aof   检查AOF日志文件
# redis-check-dump  检查RDB快照文件
# redis-cli         客户端
# redis-sentinel    哨兵，实现主从复制的HA（版本：2.4+）
# redis-server      启动和停止Redis Server

# 核心的配置文件：需要从源码中拷贝
> cd /zing/trad/
> cp ../tools/redis-3.0.5/redis.conf conf/
# 相关参数
# daemonize no  是否以后台运行的方式启动Redis，建议yes
# port 6379     端口号

# 启动
> bin/redis-server conf/redis.conf 

# 将redis 启动加入到系统服务中运行
# 将启动脚本复制到/etc/init.d目录下，本例将启动脚本命名为redis 
#（复制 /usr/local/cluster/7000/utils/redis_init_script文件）
> cp /zing/tools/redis-3.2.11/utils/redis_init_script /etc/init.d/redis 
> vi /etc/init.d/redis 
# 下面配置
#!/bin/sh
#
# Simple Redis init.d script conceived to work on Linux systems
# as it does use of the /proc filesystem.
# chkconfig: 2345 90 10
# description: Redis is a persistent key-value database
# 注意：上面增加了chkconfig和description两行 不然会提示 服务 redisd 不支持 chkconfig

# REDISPORT=6379
# EXEC=/zing/trad/redis/bin/redis-server
# CLIEXEC=/zing/trad/redis/bin/redis-cli
# 
# PIDFILE=/var/run/redis_${REDISPORT}.pid
# CONF="/zing/trad/redis/conf/${REDISPORT}.conf"
# 编辑完保存就OK了

# 设置为开机自启动服务器 
> chkconfig redis on 
# 打开服务 
service redis start 
# 关闭服务 
service redis stop
```

> **注意：如果是redis 需要远程连接，一定要设置密码，不然会报入侵，具体如下连接：**
>
> [通过redis入侵服务器](https://my.oschina.net/lenglingx/blog/532185)

```
# 设置redis 密码和远程连接
# Redis默认的初始密码为空。
# 修改Redis配置文件，需要访问密码：
> vim /zing/trad/redis/6379.conf
# 设置密码：
# requirepass 你的密码
# 使用密码登录：
> redis-cli -a  你的密码
# 停止redis：
> redis-cli -a  你的密码 shutdown
# 注意其他服务器连接Redis 配置（不然远程服务器连接不上）：
# 将6379.conf 的参数 bind 127.0.0.1 改成了bind 0.0.0.0

```



#### 也可以使用redis 文件里面的utils/install_server.sh 来默认安装

> **好处就是默认安装成服务启动后台运行启动，不好就是默认安装的路径不好找**

```
# 从网站上下载 redis-3.2.11.tar.gz 安装包；
> cd /zing/tools/
> tar -zxvf redis-3.2.11.tar.gz
> cd redis-3.2.11
> make
# (默认安装到 /usr/local/bin)
> make install	
> cd /utils/
# 默认next 安装
> ./install_server.sh
# 执行结果：查看使用的配置
# Selected config:
# Port           : 6379
# Config file    : /etc/redis/6379.conf
# Log file       : /var/log/redis_6379.log
# Data dir       : /var/lib/redis/6379
# Executable     : /usr/local/bin/redis-server
# Cli Executable : /usr/local/bin/redis-cli
# Is this ok? Then press ENTER to go on or Ctrl-C to abort.
# Copied /tmp/6379.conf => /etc/init.d/redis_6379
# Installing service...
# Successfully added to chkconfig!
# Successfully added to runlevels 345!
# Starting Redis server...
# Installation successful!

# 查看开机启动列表：
> chkconfig  --list
# redis_6379      0:off   1:off   2:on    3:on    4:on    5:on    6:off

# 开启服务 或者启动
> /etc/init.d/redis_6379 start ，也可通过（service redis_6379 start）；
# 关闭服务
> /etc/init.d/redis_6379 stop ，也可通过（service redis_6379 stop）；
```



#### Docker 方式

````
# 拉取 redis 镜像
> docker pull redis
# 运行 redis 容器
> docker run --name myredis -d -p6379:6379 redis
# 执行容器中的 redis-cli，可以直接使用命令行操作 redis
> docker exec -it myredis redis-cli...

https://juejin.im
掘金 — 一个帮助开发者成长的社区
````



#### Github 源码编译方式

```
# 下载源码
> git clone --branch 2.8 --depth 1 git@github.com:antirez/redis.git
> cd redis
# 编译
> make
> cd src
# 运行服务器，daemonize表示在后台运行
> ./redis-server --daemonize yes
# 运行命令行
> ./redis-cli...

https://juejin.im
掘金 — 一个帮助开发者成长的社区
```



#### 直接安装方式

```
# mac
> brew install redis
# ubuntu
> apt-get install redis
# redhat
> yum install redis
# 运行客户端
> redis-cli
```

