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



------

### 使用过程中大问题总汇：

> **flume的memeryChannel中transactionCapacity和sink的batchsize**

##### 运行时候报下面错误：

  <pre><code>

16/04/29 09:36:15 ERROR sink.AbstractRpcSink: Rpc Sink avro-sink: Unable to get event from channel memoryChannel. Exception follows.
org.apache.flume.ChannelException: Take list for MemoryTransaction, capacity 10 full, consider committing more frequently, increasing capacity, or increasing thread count
at org.apache.flume.channel.MemoryChannel$MemoryTransaction.doTake(MemoryChannel.java:96)
at org.apache.flume.channel.BasicTransactionSemantics.take(BasicTransactionSemantics.java:113)
at org.apache.flume.channel.BasicChannelSemantics.take(BasicChannelSemantics.java:95)
at org.apache.flume.sink.AbstractRpcSink.process(AbstractRpcSink.java:354)
at org.apache.flume.sink.DefaultSinkProcessor.process(DefaultSinkProcessor.java:68)
at org.apache.flume.SinkRunner$PollingRunner.run(SinkRunner.java:147)
at java.lang.Thread.run(Thread.java:745)

  </pre></code>

**参考的解决方案：**

[transactionCapacity和sink的batchsize需要注意事项](https://blog.csdn.net/joy6331/article/details/51279670)

**原因是：**

**<u>flume的实时日志收集，用flume默认的配置后，发现不是完全实时的，于是看了一下，原来是memeryChannel的transactionCapacity在作怪，因为他默认是100，也就是说收集端的sink会在收集到了100条以后再去提交事务（即发送到下一个目的地）。</u>**



> **flume 收集 java exception 错误日志的问题**

`flume 在收集到java throw Exception 异常日志信息的时候既然不是整条ERROR 异常错误，如下：`

![04.png](http://www.wailian.work/images/2018/04/24/04.png)

`它是将上面的异常每条都传输到后台处理，像这种应该合并成一个Event里面`

**参考的解决方案：**

[Flume<自定义Source和拦截器实现抓取异常多行日志>](https://blog.csdn.net/gpwner/article/details/71275737)

**<u>我采用的是自定义Source 的方式，可以将错误的信息合并后传输：</u>**

**其核心思想是：**

如果当前有两行记录，如果两行记录都是“正常”的日志信息，比如：

  <pre><code>

2016-07-08 09:33:53-[Analysis-WC] INFO [main] FormulaContextUtil.init(68) | into FormulaContextUtil.init method begin ....
2016-07-08 09:33:53-[Analysis-WC] INFO [main] FormulaContextUtil.init(133) | FormulaContextUtil.init method end ....

  </pre></code>

则**单独将每一行当做一个Event，**传递； 
当连续的两条记录诸如这样的形式：

  <pre><code>

2016-07-28 15:49:05-[Analysis-WC] ERROR [http-8080-2] ControllerProxy.afterMethod(43) | java.lang.NullPointerException
java.lang.NullPointerException
    at com.ap.alt.system.web.LoginMgrController.getSumMXYJTC(LoginMgrController.java:304)

  </pre></code>

**则将下一行日志合并到上一个Event中。**