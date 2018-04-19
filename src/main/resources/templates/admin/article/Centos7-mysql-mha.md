> 参考地址：[mysql高可用架构之MHA，haproxy实现读写分离详解](http://www.cnblogs.com/dannylinux/p/8033318.html)

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

---

1. ### **<u>ssh证书互信脚本---每台机器上都要操作</u>**

   使用ssh-kegen生成公私钥（每台服务器上），下面是批量执行脚本：

   <pre><code>

   #!/bin/bash
   for i in 0 1 2 3
           do
                   ssh-copy-id -i /root/.ssh/id_rsa.pub 10.211.55.20$i
           done
   </code></pre>

2. ##### **<u>ABB搭建</u>**

    **2.1 Mysql 的安装**

   <pre><code>

   wget http://dev.mysql.com/get/mysql-community-release-el7-5.noarch.rpm
   rpm -ivh mysql-community-release-el7-5.noarch.rpm
   yum install mysql-community-server

   #	配置mysql
   service mysqld restart

   mysql -u root
   mysql> set password for 'root'@'localhost' =password('youpassword');

   </code></pre>

   ***2.2所有的机器初始化MySQL，设置ABB架构：***

   > MYSQL_A(20)上的操作：
   >
   > **vim /etc/my.cnf**
   >
   > **server_id=1       //设置优先级最高**
   >
   > **log_bin=binlog　　//开启binlog日志**
   >
   > log_biin=binlog.index
   >
   > **注意：MYSQL_B1、MYSQL_B2上的操作同mysql_a只是注意server_id的不同！**









>    登录MYSQL_A(55.20)上的操作：

> *vim /etc/my.cnf**

> **server_id=1       //设置优先级最高**

> **log_bin=binlog　　//开启binlog日志**

> **log_biin=binlog.index**
>
> #### **<u>注意：MYSQL_B1、MYSQL_B2上的操作同mysql_a只是注意server_id的不同！</u>**

***2.3 设置用户***

在msyql_a(20)中：因为前面已经开启二进制日志了，所以其他机器也能学到赋权语句！！故，其他机器就不用再赋权了！

```shell
jdata1> grant all on *.* to 'root'@'%' identified by 'youpassword'; 　　　//给mha_manager用，因为其在failover时需要登陆上来，并且拷贝binlog到所有的slave上去。
jdata1> grant replication slave on *.* to 'sky'@'%' identified by 'youpassword';//复制专用用户
jdata1> flush privileges;  　　//刷新权限
jdata1> show master status\G        //看一下binlog日志到第几个文件了
```

在mysql_b1(21)中：

```mysql
   mysql> slave stop;
   mysql> change master to master_host=”10.211.55.20”,master_user=”sky”,master_passoword=”62661206”,master_log_file=”mysql-bin.000003”;
   mysql> reset slave; (注意:如果之前因为同步数据的时候sql执行错误，这里要清除掉)
   mysql> slave start;
   mysql> show slave status\G        //查看复制线程状态
```

**<u>在mysql_b2（22）做21上同样的操作！</u>**

3. ##### 安装配置启动mha_manager、mha_node

   manager机器上（23）：cd /root/zing (mha4mysql-node 和 mha4mysql-manager rpm 文件,要保存这2个文件，这2个东西不好找，下面提供下载地址)

   URL、………………………………..

   在管理节点安装MHA管理组件，先安装node再安装manager软件本身有依赖关系

   `yum install mha4mysql-node-0.56-0.el6.noarch.rpm`

   `yum install mha4mysql-manager-0.56-0.el6.noarch.rpm`

   manager 需要一下包的依赖：

   在**<u>node节点</u>**上，执行下面的命令：`yum install perl-DBD-MySQL`

   在**<u>manager节点</u>**上：

   ```shell
    yum install perl-DBD-MySQL
    yum install perl-Config-Tiny
    yum install perl-Log-Dispatch
    yum install perl-Parallel-ForkManager
    yum install -y rrdtool perl-rrdtool rrdtool-devel perl-Params-Validate
   ```

   ***坑点1：发现 yum 不能安装perl-Log-Dispatch 和   perl-Parallel-ForkManager:***

   解决方案：[CentOS7安装第三方yum源EPEL](https://www.linuxidc.com/Linux/2015-10/124002.htm ) 

   下载并安装EPEL

   ```shell
   [root@localhost ~]# wget http://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
   [root@localhost ~]# rpm -ivh epel-release-latest-7.noarch.rpm
   [root@localhost ~]# yum repolist      ##检查是否已添加至源列表
   ```

   OK，检查好已添加至源后就可以进行yum安装了。

   最后，将mha4mysql-node包复制到集群中的所有节点：

   `[root@MHA_240 mha_soft]# for i in 1 2 3 ;do scp mha4mysql-node-0.56-0.el6.noarch.rpm 10.211.55.2$i:/usr/src ;done`

   mha_node: 安装到其他node(所有其他机器)

   `[root@MHA_240 mha_soft]# yum install -y mha4mysql-node-0.54-0.el6.noarch.rpm`