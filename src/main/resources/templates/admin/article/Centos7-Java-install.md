**_之前在Linux 下面安装Java 环境总感觉操作很多复杂，现在总结安装的方法如下，方便简单：_**

1. 首选需要查看系统是否已经默认安装java 环境：
   `yum list installed |grep java`
   `**注意：**CentOS 6.X 和 7.X 自带有OpenJDK runtime environment (openjdk)。它是一个在linux上实现开源的Java 平台。`
   `具体的卸载方式可以自行网上查询`
   
2. 卸载JDK相关文件输入和卸载tzdata-java输入
   `yum -y remove java-1.6.0-openjdk*`
   `yum -y remove tzdata-java.noarch`
   `当结果显示为Complete！即卸载完毕。
    注：“*”表示卸载掉java 1.6.0的所有openjdk相关文件。`
    
3. yum安装自己需要的Java版本
   `yum -y install java-1.8.0-openjdk* `
   `安装完成之后，安装的目录JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.144-0.b01.el7_4.x86_64`

- - -
**上面使用的yum 的方式默认安装，虽然隐藏了安装细节，但是却不知道安装路径，
因此建议将java 免安装版上传到Linux 对应的目录，然后修改配置文件来直接java 环境。**
   `vi /etc/profile`
   `JAVA_HOME=/root/zing/jdk1.8.0_144`
   `export JAVA_HOME`
   `PATH=$JAVA_HOME/bin:$PATH`
   `export PATH`
   `source /etc/profile`
   `java -version 来显示安装好的Java 环境变量` 
   
   **或者：修改 .bash_profile 环境配置**
>    /etc/profile 系统整体设置 
>    ~/.bash_profile 用户个人设置