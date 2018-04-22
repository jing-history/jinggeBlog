### 需求是用flume 监控日志文件，然后将文件的内容存放在mysql数据库中。

#### 本文结构（<u>注意：环境都是在centos 7（192.168.5.105）下</u>）

> 1. **flume1.7.0 的安装和配置**
>
> 2. **mysql 表设计**
>
> 3. **MysqlSink插件的编写**
>
> 4. **连接服务器远程调试**
>
> 5. **打包更新到服务器**

------

#### flume1.7.0 的安装和配置

**官网下载flume1.7.0，我这里都放到自己的共享盘里面，方便自己也方面别人：**

[百度网盘flume文件夹下获取](https://pan.baidu.com/s/1-lFvROxmIz2AxahO61qE4Q)

**上传到centos 7 指定目录：**

![01.png](http://www.wailian.work/images/2018/04/20/01.png)

**解压文件：**

`tar -zxvf apache-flume-1.7.0-bin.tar.gz -C /home/common/`

`cd ../common/`

` mv apache-flume-1.7.0-bin/ flume-1.7.0`

`cd flume-1.7.0/conf`

`cp flume-conf.properties.template flume-conf.properties`

`vi flume-conf.properties`

**配置如下:**

  <pre><code>
#配置 Agent
a1.sources = r1
a1.sinks = k1
a1.channels = c1
#配置 Source
a1.sources.r1.type = exec
a1.sources.r1.channels = c1
a1.sources.r1.type = exec
a1.sources.r1.command = tail -F /root/data/XXX/logback.log
a1.sources.r1.deserializer.outputCharset = UTF-8

#配置 Sink
a1.sinks.k1.type = XXX.XXX.logs.sink.XXXXX
a1.sinks.k1.hostname=192.168.5.111
a1.sinks.k1.port=3306
a1.sinks.k1.databaseName=database
a1.sinks.k1.tableName=tables
a1.sinks.k1.user=root
a1.sinks.k1.password=password
a1.sinks.k1.channel = c1

#配置 Channel
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 100
  </pre></code>

> **注意：记得配置flume 运行Java 的环境**

`cp flume-env.sh.template flume-env.sh`

`vi flume-env.sh 修改JAVA_HOME路径（这里写你自己的Java 环境）`

`export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64`

![02.png](http://www.wailian.work/images/2018/04/20/02.png)



> **Java 方面的编写和mysql 表的创建**

[公司项目不方便贴出源码，我也是参考这个来写的](https://blog.csdn.net/u012373815/article/details/54098581)

**<u>注意下这个jar 包放置的点，我是用下面的方式：</u>**

**官方建议在flume的 plugins.d (plugins.d 目录需要自己创建)目录下创建 一个自己定义的目录，在自定义的目录下新建 lib 和 libext 文件夹，lib 文件夹为放自定义组件的jar包，libext 文件夹下放 自定义组件的依赖包。**
  <pre><code>
​   flume-1.7.0/plugins.d/
    flume-1.7.0/plugins.d/project/
    flume-1.7.0/plugins.d/project/lib/XXXXX.jar
    flume-1.7.0/plugins.d/project/libext/mysql-connector-java-6.0.5.jar
  </pre></code>
***坑点1：WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+*** 

  <pre><code>

​	DriverManager.getConnection("jdbc:mysql://localhost:3306/logdb","root","123456");

​	改成

​	DriverManager.getConnection("jdbc:mysql://localhost:3306/logdb？useSSL=false","root","123456");

  </pre></code>

#### 最后，启动flume

  <pre><code>

./flume-ng agent -c /home/common/flume-1.7.0/conf -f /home/common/flume-1.7.0/conf/flume-conf.properties -n agnet1 -Dflume.root.logger=INFO,console

  </pre></code>

![030dfe0.png](http://www.wailian.work/images/2018/04/20/030dfe0.png)
