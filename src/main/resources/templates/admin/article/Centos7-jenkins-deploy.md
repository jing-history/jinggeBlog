#### Jenkins是什么？

> 1. Jenkins是一个开源软件项目，旨在提供一个开放易用的软件平台，使软件的持续集成变成可能；
> 2. Jenkins是基于Java开发的一种持续集成工具，用于监控持续重复的工作；
> 3. 续的软件版本发布/测试项目；
> 4. 监控外部调用执行的工作。

#### Jenkins安装和部署(通过yum来安装):
<pre><code>
	##安装 添加yum repos，然后安装
	sudo wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins.repo
	sudo rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
	sudo yum install jenkins
</pre></code>

`sudo service jenkins start/stop/restart`

`sudo chkconfig jenkins on`

> **注意：如果没有java环境的话要安装java**

#### 一般这么安装都能启动成功（默认的启动占用的端口是8080）：

**如果想要修改Jenkins 默认启动端口号（我自己使用7777端口）：**

`vi /etc/sysconfig/jenkins`

[![01.png](http://www.wailian.work/images/2018/05/04/01.png)](http://www.wailian.work/image/eW92QT)



**<u>通过链接访问Jenkins 主页（这里我忽略第一次访问要密码，创建账号和初初始化插件，插件默认推荐安装）：</u>**

[![02.png](http://www.wailian.work/images/2018/05/04/02.png)](http://www.wailian.work/image/eW9CvV)

------



#### 首先，全局工具配置：

[![03.png](http://www.wailian.work/images/2018/05/04/03.png)](http://www.wailian.work/image/eWCw1p)

> **主要是JDK，Git和maven 配置：**

[![04.png](http://www.wailian.work/images/2018/05/04/04.png)](http://www.wailian.work/image/eWlI7V)

[![05.png](http://www.wailian.work/images/2018/05/04/05.png)](http://www.wailian.work/image/eWl635)



**要是你不知道可以执行命令安装位置，可以试下用命令查找：**

`find / -name git`

------

> **然后接着就是创建可以访问github（我项目部署在上面）Credentials 账号：**

[![06.png](http://www.wailian.work/images/2018/05/05/06.png)](http://www.wailian.work/image/elOjdN)



**使用默认的global 账号进入到配置页面：**

[![07.png](http://www.wailian.work/images/2018/05/05/07.png)](http://www.wailian.work/image/elOUm0)

> **注意：这里就要设计到SSH免登陆设置（在Linux 服务器上面输入下面命令）：**

`ssh-keygen -t rsa`

**然后一直空格到结束，下图生成的秘钥位置和文件：**

[![09.png](http://www.wailian.work/images/2018/05/05/09.png)](http://www.wailian.work/image/elOc7V)

> **注意：这里是需要把秘钥（id_rsa）里面的内容全部拷贝到Jenkins private key 的内容，Username 文本框可以任意填**



#### 然后我们创建自己的任务

[![10.png](http://www.wailian.work/images/2018/05/05/10.png)](http://www.wailian.work/image/elWe35)

> 注意：这里我的项目是基于maven 来搭建的，可能默认安装的插件是不会安装maven 插件的

![11.png](http://www.wailian.work/images/2018/05/05/11.png)

**这个时候我们就要去Jenkins 系统管理面安装插件**

[![12.png](http://www.wailian.work/images/2018/05/05/12.png)](http://www.wailian.work/image/elWT98)

**在可选插件里面查找maven（Maven Integration plugin）插件，这里我已经安装了**

[![13.png](http://www.wailian.work/images/2018/05/05/13.png)](http://www.wailian.work/image/elWrIo)

**安装完成之后就会有上面的那个maven 选项可以选择。**

------

#### 最后配置我们自己项目：

> **Github源码的配置（注意：这里就是使用我们上面配置的Credentials 账号）：**

[![14.png](http://www.wailian.work/images/2018/05/05/14.png)](http://www.wailian.work/image/elW0pS)

**<u>差点忘记，我们还需要到我们github 账号上面去添加我的公钥，Jenkins 才能check out 代码：</u>**

[![15.png](http://www.wailian.work/images/2018/05/05/15.png)](http://www.wailian.work/image/elWNrR)

> **编译maven 项目的配置：**

[![16.png](http://www.wailian.work/images/2018/05/05/16.png)](http://www.wailian.work/image/elWIbl)

`命令：clean package -e -X -DskipTests`



> **Jenkins 编译打包通过SSH 远程登录部署服务器**

**在构建后操作选择：**

[![17.png](http://www.wailian.work/images/2018/05/05/17.png)](http://www.wailian.work/image/elWyqb)

**<u>注意：上面的SSH Publishers 也是需要插件(Publish Over SSH)安装才能有的选项</u>**

[![19.png](http://www.wailian.work/images/2018/05/05/19.png)](http://www.wailian.work/image/ellvtB)

**然后需要到系统管理-->>系统设置里面去设置连接的账号：**

![20.png](http://www.wailian.work/images/2018/05/05/20.png)

[![21.png](http://www.wailian.work/images/2018/05/05/21.png)](http://www.wailian.work/image/ellXAL)**<u>这里还要注意一点就是要在Jenkins 服务上面配置可以SSH 登录远程服务器</u>**

**<u>这里还要注意一点就是要在Jenkins 服务上面配置可以SSH 登录远程服务器*</u>**

**`ssh-copy-id root@服务器IP`** 

`ssh 服务器IP (登录试下是否成功)`

> **上面都配置完后，填写打包生成的Jar 文件和远程服务器运行的脚本**

[![18.png](http://www.wailian.work/images/2018/05/05/18.png)](http://www.wailian.work/image/elW7ND)



#### **远程服务器上面编写的脚本(jingge-blog.sh)：**

`#!/bin/sh`
`DATE=$(date +%Y-%m-%d)`
`export JAVA_HOME PATH CLASSPATH`
`JAVA_HOME=/root/program/jdk1.8.0_161`
`PATH=$JAVA_HOME/bin:​$JAVA_HOME/jre/bin:$PATH`
`CLASSPATH=.:$JAVA_HOME/lib:​$JAVA_HOME/jre/lib:$CLASSPATH`
`DIR=/home/jingzing/service/blog`
`JARFILE=jinggeBlog-1.0.0-SNAPSHOT.jar`
`cd $DIR`
`ps -ef | grep $JARFILE | grep -v grep | awk '{print $2}' | xargs kill -9`
`mv -f /root/data/jenkins/$JARFILE .`
`nohup java -Xms256m -Xmx256m -jar $JARFILE --spring.profiles.active=production > /dev/null 2>&1 &`
`if [ $? = 0 ];then`
        `sleep 30`
        `tail -n 30 logback.log`
`fi`
**最后保存。**

------

#### 项目构建：

[![22.png](http://www.wailian.work/images/2018/05/05/22.png)](http://www.wailian.work/image/ellZM1)

**构建成功状态是蓝色，说明的项目部署成功，如果是红色，就是构建的有问题，具体的问题可以查看控制台输出的错误来定位错误。**

[![23.png](http://www.wailian.work/images/2018/05/05/23.png)](http://www.wailian.work/image/ellry4)

**下面是控制台的位置：**

[![24.png](http://www.wailian.work/images/2018/05/05/24.png)](http://www.wailian.work/image/ell0Ek)

**这样基本的部署就完成了。**