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

