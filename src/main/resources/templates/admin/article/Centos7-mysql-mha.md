**参考地址：[mysql高可用架构之MHA，haproxy实现读写分离详解](http://www.cnblogs.com/dannylinux/p/8033318.html)**

###### **实验步骤：**

> **ssh证书户信任（ssh的密钥验证）**

> **ABB架构**

> **安装mha_manager  、 mha_node**

> **测试**

###### **IP地址规划:**

> **Mysql_manager(10.211.55.23)—需要是64位的系统**

> **MySQL_A(10.211.55.20)**

> **MySQL_B1(10.211.55.21)**

> **MySQL_B2(10.211.55.22)**

------

### ssh证书互信脚本---每台机器上都要操作

#### 使用ssh-kegen生成公私钥（每台服务器上），下面是批量执行脚本：
<pre><code>
\#!/bin/bash
  for i in 0 1 2 3
​        do
​                ssh-copy-id -i /root/.ssh/id_rsa.pub 10.211.55.20$i
​        done
</code></pre>

### ABB搭建

#### Mysql 的安装(感觉下面的安装方法比较简单):

  <pre><code>
   	wget http://dev.mysql.com/get/mysql-community-release-el7-5.noarch.rpm
   	rpm -ivh mysql-community-release-el7-5.noarch.rpm
  	yum install mysql-community-server
	service mysqld restart
   	mysql -u root
   	mysql> set password for 'root'@'localhost' =password('youpassword');
   </code></pre>

#### 所有的机器初始化MySQL，设置ABB架构：

> **MYSQL_A(20)上的操作：**
>
> **vim /etc/my.cnf**
>
> **server_id=1       //设置优先级最高**
>
> **log_bin=binlog　　//开启binlog日志**
>
> **log_biin=binlog.index**
>
> **<u>注意：MYSQL_B1、MYSQL_B2上的操作同mysql_a只是注意server_id的不同！</u>**

#### 设置用户

**在msyql_a(20)中：因为前面已经开启二进制日志了，所以其他机器也能学到赋权语句！！故其他机器就不用再赋权了！**

  <pre><code>
   	//给mha_manager用，因为其在failover时需要登陆上来，并且拷贝binlog到所有的slave上去
   	jdata1> grant all on *.* to 'root'@'%' identified by 'youpassword';
   	//复制专用用户
   	jdata1> grant replication slave on *.* to 'sky'@'%' identified by 'youpassword';
   	//刷新权限
   	jdata1> flush privileges;  
   	//看一下binlog日志到第几个文件了　　
   	jdata1> show master status\G 
  </code></pre>

**在mysql_b1(21)中：**

  <pre><code>
      mysql> slave stop;
      mysql> change master to 	      					master_host=”10.211.55.20”,master_user=”sky”,master_passoword=”62661206”,master_log_file=”mysql-bin.000003”;
​      mysql> reset slave; (注意:如果之前因为同步数据的时候sql执行错误，这里要清除掉)
​      mysql> slave start;
​      mysql> show slave status\G        //查看复制线程状态
  </code></pre>

**<u>在mysql_b2（22）做21上同样的操作！</u>**



### 安装配置启动mha_manager、mha_node

**manager机器上（23）：cd /root/zing (mha4mysql-node 和 mha4mysql-manager rpm 文件,要保存这2个文件，这2个东西不好找，下面提供下载地址)**

URL、………………………………..

在管理节点安装MHA管理组件，先安装node再安装manager软件本身有依赖关系

`yum install mha4mysql-node-0.56-0.el6.noarch.rpm`

`yum install mha4mysql-manager-0.56-0.el6.noarch.rpm`

manager 需要一下包的依赖：

在**<u>node节点</u>**上，执行下面的命令：`yum install perl-DBD-MySQL`

在**<u>manager节点</u>**上：

  <pre><code>
    yum install perl-DBD-MySQL
    yum install perl-Config-Tiny
    yum install perl-Log-Dispatch
    yum install perl-Parallel-ForkManager
    yum install -y rrdtool perl-rrdtool rrdtool-devel perl-Params-Validate
  </pre></code>

***坑点1：发现 yum 不能安装perl-Log-Dispatch 和   perl-Parallel-ForkManager:***

解决方案：[CentOS7安装第三方yum源EPEL](https://www.linuxidc.com/Linux/2015-10/124002.htm ) 

下载并安装EPEL

  <pre><code>
   	[root@localhost ~]# wget http://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
   	[root@localhost ~]# rpm -ivh epel-release-latest-7.noarch.rpm
  	[root@localhost ~]# yum repolist      ##检查是否已添加至源列表
   </code></pre>

OK，检查好已添加至源后就可以进行yum安装了。

**最后，将mha4mysql-node包复制到集群中的所有节点：**

  <pre><code>
   	for i in 1 2 3 ;
   	do
  	scp mha4mysql-node-0.56-0.el6.noarch.rpm 10.211.55.2$i:/usr/src;done

   </code></pre>

**mha_node: 安装到其他node(所有其他机器)**

`[root@MHA_240 mha_soft]# yum install -y mha4mysql-node-0.54-0.el6.noarch.rpm`

**mha_manager: (MHA 20上)**

`[root@MHA_240 mha]# cp -pr /usr/src/mha_soft/mha/ /etc/       //mha的配置文件和启动`

**<u>文件（注意：这里我是没有找到mha.cnf 和 mha_start，vi 创建mha.cnf文件）</u>**

配置如下：

  <pre><code>
	[server default]

​	\#mysql admin account and password

​	user=root

​	password=62661206

​	\#mha workdir and worklog

​	manager_workdir=/etc/mha

​	manager_log=/etc/mha/manager.log

​	\#mysql A/B account and pw

​	repl_user=sky

​	repl_password=62661206

​	\#check_mha_node time

​	ping_interval=1

​	\#ssh account

​	ssh_user=root

​	[server1]

​	hostname=10.211.55.20

​	ssh_port=22

​	master_binlog_dir=/var/lib/mysql

​	candidate_master=1

​	[server2]

​	hostname=10.211.55.21

​	ssh_port=22

​	master_binlog_dir=/var/lib/mysql

​	candidate_master=1

​	[server3]

​	hostname=10.211.55.22

​	ssh_port=22

​	master_binlog_dir=/var/lib/mysql

​	candidate_master=1
   </code></pre>

 **检测ssh互信有没有问题**

`[root@localhost src]# masterha_check_ssh --conf=/etc/mha/mha.cnf`

`Tue Jun 13 02:21:31 2017 - [info] All SSH connection tests passed successfully. 有这个表示成功`

**测AB**

   <code><pre>

​	[root@localhost src]# masterha_check_repl --conf=/etc/mha/mha.cnf

​	…

​	MySQL Replication Health is OK.

   </code></pre>