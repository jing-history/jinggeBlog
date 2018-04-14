**_默认Nginx（基于 nginx-1.12.2.tar.gz） 安装方法_**

1. 从Nginx 官网下载安装文档:http://nginx.org/en/download.html

2. 将下载好的Nginx上传到 centos 指定目录进行解压：
     `tar -zxvf nginx-1.12.2.tar.gz` 
     `cd nginx-1.12.2`
    
3. **注意**安装依赖的组件：
     `yum install -y gcc`
     `yum install -y pcre pcre-devel`
     `yum install -y zlib zlib-devel`
     `yum install -y openssl openssl-devel`
      
5. 进行Nginx 安装：
     `./configure `
     `make`
     `make install`

6. Nginx 常用命令：

     `./nginx -t  用于检查Nginx 配置是否正确`
     `./nginx -s start/stop   启动或者停止`
     `./nginx -s reload 重新加载配置  `
   
Docker 方式启动运行Nginx：
-------------     

1. 安装Docker；

2. 从Docker Hub 官方仓库下载应用（注意：这个是本人觉得比较好用的Nginx 应用）：
     `docker pull dockerfile/nginx`

     `docker build -t="dockerfile/nginx" github.com/dockerfile/nginx 重命名`

3. 运行容器：
    
     `docker run -d -p 80:80 dockerfile/nginx` 
    或者你希望读取Docker 容器外面的Nginx 配置文件：
    <pre><code>
    docker run -d -p 80:80 -v <sites-enabled-dir>:/etc/nginx/conf.d -v <certs-dir>:/etc/nginx/certs -v <log-dir>:/var/log/nginx -v <html-dir>:/var/www/html dockerfile/nginx
    </code></pre>
 
4. 最后，你通过 `http://<host>`  去访问你Nginx 首页。  