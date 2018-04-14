Docker 环境的安装(yum源)
================

***机器环境：Linux Centos 7*** 

1. 首选需要一个64位操作系统和3.10或者更版本的内核
   `uname -r`
   
2. 安装yum-utils
   `yum install -y yum-config-manager`
   
3. 使用普通用户sudo或者root登录到你的服务器，更新yum，确保你的软件都是最新的
   `sudo yum update`
   
4. 添加Docker的yum源
    `sudo tee /etc/yum.repos.d/docker.repo <<-'EOF'`
    `[dockerrepo]`
    `name=Docker Repository`
    `baseurl=https://yum.dockerproject.org/repo/main/centos/7/`
    `enabled=1`
    `gpgcheck=1`
    `gpgkey=https://yum.dockerproject.org/gpg`
    `EOF`
    
5. 安装Docker软件包
   `sudo yum install docker-engine`
   
6. 设置Docker服务开机自启
   `sudo systemctl enable docker.service`
   
7. 启动Docker服务
   `sudo systemctl start docker`
   
8. 验证Docker是否安装成功
   `sudo docker run --rm hello-world`
   
---------------------------------------

**_Docker 卸载_**

1. 查询安装过的包
   `yum list installed | grep docker`
   [![30F98875-CF84-4E7E-B8BA-16D020571B38.md.png](http://s1.wailian.download/2017/12/25/30F98875-CF84-4E7E-B8BA-16D020571B38.md.png)](http://www.wailian.work/image/MgHAZL)
   
2. 删除安装的软件包
   `yum -y remove docker-engine.x86_64`
   
3. 删除镜像/容器等
   `rm -rf /var/lib/docker`

---------------------------------------

**_Docker 重启_**

`systemctl daemon-reload`

`service docker restart`