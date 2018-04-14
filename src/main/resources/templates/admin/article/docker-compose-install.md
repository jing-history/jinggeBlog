Centos7 docker-compose 环境的安装
================

在线安装
-------------

1. 使用curl安装
   <pre><code>
      curl -L https://github.com/docker/compose/releases/download/1.1.0/docker-compose-`uname -s`-`uname -m` > 
      /usr/local/bin/docker-compose
      chmod +x /usr/local/bin/docker-compose
   </code></pre>
2. 添加可执行权限：
    `chmod +x /usr/local/bin/docker-compose`
    
3. 最后在命令行执行命令：docker-compose --version，显示如下图片则安装正常：
   ![docker-compose1.png](http://s1.wailian.download/2017/12/26/docker-compose1.png)
    
离线安装
-------------  
 
1. 下载[docker-compose-Linux-x86_64]：
   `https://github.com/docker/compose/releases/download/1.8.1/docker-compose-Linux-x86_64`
   
2. 上传到服务器对应目录(/home/jingzing/tools)，然后重新命名(docker-compose)移动到 /usr/local/bin/docker-compose：
   `mv docker-compose-Linux-x86_64 /usr/local/bin/docker-compose; `
   
3. 添加可执行权限：
    `chmod +x /usr/local/bin/docker-compose`
    
5. 最后在命令行执行命令：docker-compose --version，显示如下图片则安装正常：
   ![docker-compose1.png](http://s1.wailian.download/2017/12/26/docker-compose1.png)
   
