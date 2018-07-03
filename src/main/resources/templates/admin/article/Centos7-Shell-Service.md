**参考地址：[shell脚本批量/单独启动、停止、重启java独立jar程序](http://www.cnblogs.com/jxrichar/p/5744940.html)**

#### 微服务部署问题：

> **系统微服务之后，会产品很多个子服务，但是多个子服务独立运行程序包越来越多，程序的部署变成了一件非常头痛的问题，写一个shell脚本来批量/单独启动、停止、重启这些独立的java程序，本人没有写过Shell，也是参考上面链接提供的做法修改，谢谢楼主了。**

<p>
	<br />
</p>
<pre class="prettyprint lang-bsh">
<pre class="prettyprint lang-bsh">#!/bin/bash

#程序代码数组
APPS=(config edge platform sso)

#程序名称数组
NAMES=(配置模块 网关模块 平台服务 sso服务)

#jar包数组
JARS=(sf-config-server-1.1.0-SNAPSHOT.jar sf-edge-service-1.1.0-SNAPSHOT.jar sf-platform-service-1.1.0-SNAPSHOT.jar sf-sso-service-1.1.0-SNAPSHOT.jar)

#jar包路径数组
PATHS=(/root/sfdata/ss/)

  start(){
  	local APPNAME=
	local NAME=
	local CLASSNAME=
	local PROJECTDIR=
	local command="sh service.sh start"
	local cmd2="$1"
	local cmd2ok=0
	local cnt=0
	local okcnt=0
	echo "---------------------------开始启动服务---------------------------"
	for(( i=0;i&lt;${#APPS[@]};i++))
	  do
	  	APPNAME=${APPS[$i]}
	  	NAME=${NAMES[$i]}
		CLASSNAME=${JARS[$i]}
		PROJECTDIR=${PATHS}${APPS[$i]}

	  	if [ "$cmd2" == "all" ] || [ "$cmd2" == "$APPNAME" ]; then
	  		cmd2ok=1
	  		C_PID="0"
	  		cnt=0
	  		#ps -ef | grep "$CLASSNAME" | awk '{print $2}' | while read pid
	  		PID=`ps -ef |grep $(echo $CLASSNAME |awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`	
	  		#-n 如果字符长度非零则返回为真，非空为真
	  		if [ -n "$PID" ]
	  			then
	  			echo "$APPNAME---$NAME:己经运行,PID=$PID"
	  		else
	  			cd $PROJECTDIR
	  			rm -f $PROJECTDIR/nohup.out
	  			command="nohup java -Xms256m -Xmx256m -jar $CLASSNAME"
	  			exec $command &gt;&gt; $PROJECTDIR/nohup.out &amp;
				PID=`ps -ef |grep $(echo $CLASSNAME |awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`	
				cnt=0
	  			#-z 如果字符长度为零则返回为真，空为真
	  			while [ -z "$PID" ]
				  do
				    if(($cnt==30))
				      then
				      echo "$APPNAME---$NAME:$cnt秒内未启动，请检查！"
				      break
				    fi
				  cnt=$(($cnt+1))
				  sleep 1s
	              		  PID=`ps -ef |grep $(echo $CLASSNAME |awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`
				  done
			      okcnt=$(($okcnt+1))
			      echo "$APPNAME---$NAME:己经成功启动,PID=$PID"
			      #config 配置模块和启动优先要启动
	          		      if [ "$APPNAME" == "config" ] ;
	             		 then
	             		 echo "$APPNAME---$NAME:启动中..."
	             		 sleep 60s
	          		      fi
	          		      if [ "$APPNAME" == "platform" ] ;
	             		 then
	             		 echo "$APPNAME---$NAME:启动中..."
	             		 sleep 30s
	          			 fi
	         		      if [ "$APPNAME" == "edge" ] ;
	             		 then
	             		 echo "$APPNAME---$NAME:启动中..."
	             		 sleep 20s
	          		      fi
	         		      if [ "$APPNAME" == "sso" ] ;
	             		 then
	             		 echo "$APPNAME---$NAME:启动中..."
	            		 sleep 20s
	          		      fi
			fi
		fi
	  done
	if (($cmd2ok==0))
	   then
	   echo "command2:all|config|platform|edge|sso"
	else
	   echo "---------------------------本次启动:$okcnt个服务"
	fi
  }

  stop(){
       local APPNAME=
       local NAME=
       local CLASSNAME=
       local PROJECTDIR=
       local command="sh service.sh stop"
       local cmd2="$1"
       local cmd2ok=0  
       local okcnt=0
       echo "---------------------------开始停止服务---------------------------"
       for(( i=0;i&lt;${#APPS[@]};i++))
	do
	  APPNAME=${APPS[$i]}
	  NAME=${NAMES[$i]}
	  CLASSNAME=${JARS[$i]}
	  PROJECTDIR=${PATHS}${APPS[$i]}
	  if [ "$cmd2" == "all" ] || [ "$cmd2" == "$APPNAME" ]; then
		cmd2ok=1
		PID=`ps -ef |grep $(echo $CLASSNAME |awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`	
		if [ -n "$PID" ]
		   then
		   echo "$NAME:PID=$PID准备结束"
		   kill $PID
		   PID=`ps -ef |grep $(echo $CLASSNAME |awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`
		   while [ -n "$PID" ]
		     do
			sleep 1s
			PID=`ps -ef |grep $(echo $CLASSNAME |awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`
		     done
		     echo "$NAME:成功结束"
		     okcnt=$(($okcnt+1))
		else
		     echo "$NAME:未运行"
		fi
	   fi
	done
	if (($cmd2ok==0))
	   then
	   echo "command2:all|config|platform|edge|sso"
	else
	   echo "---------------------------本次共停止:$okcnt个服务" 
	fi
  }
case "$1" in 
start) 
start "$2"
exit 1
;; 

stop) 
stop "$2"
;; 

restart) 
stop "$2"
start "$2"
;; 
*) 
echo "command1: start|stop|restart" 
exit 1 
;; 
esac
</pre>
<br />
</pre>
<p>
	<br />
</p>

> **之前独立部署服务的脚本（第一种）：**

<pre class="prettyprint lang-bsh">#!/bin/sh

## service name
APP_NAME=edge

SERVICE_DIR=/sfdata/service/$APP_NAME
SERVICE_NAME=sf-$APP_NAME
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid

cd $SERVICE_DIR

case "$1" in

    start)
        nohup java -Xms256m -Xmx512m -jar $JAR_NAME &gt;/dev/null 2&gt;&amp;1 &amp;
        echo $! &gt; $SERVICE_DIR/$PID
        echo "=== start $SERVICE_NAME"
        ;;
    
    stop)
        kill `cat $SERVICE_DIR/$PID`
        rm -rf $SERVICE_DIR/$PID
        echo "=== stop $SERVICE_NAME"
    
        sleep 5
        P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "=== $SERVICE_NAME process not exists or stop success"
        else
            echo "=== $SERVICE_NAME process pid is:$P_ID"
            echo "=== begin kill $SERVICE_NAME process, pid is:$P_ID"
            kill -9 $P_ID
        fi
        ;;
    
    restart)
        $0 stop
        sleep 2
        $0 start
        echo "=== restart $SERVICE_NAME"
        ;;
    
    *)
        ## restart
        $0 stop
        sleep 2
        $0 start
        ;;
esac
exit 0</pre>

> **第二种：**

<pre class="prettyprint lang-bsh">
<pre class="prettyprint lang-bsh">DATE=$(date +%Y-%m-%d)
export JAVA_HOME PATH CLASSPATH
JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64
PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH
CLASSPATH=.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib:$CLASSPATH
DIR=/root/data/platform
JARFILE=sf-platform-service-1.1.0-SNAPSHOT.jar

if [ ! -d $DIR/backup ];then
   mkdir -p $DIR/backup
fi
cd $DIR

ps -ef | grep $JARFILE | grep -v grep | awk '{print $2}' | xargs kill -9
mv $JARFILE backup/$JARFILE$DATE
mv -f /root/data/jenkins/$JARFILE .

nohup java -Xms256m -Xmx256m -jar $JARFILE &gt; out.log &amp;
if [ $? = 0 ];then
        sleep 30
        tail -n 50 out.log
fi

cd backup/
ls -lt|awk 'NR&gt;5{print $NF}'|xargs rm -rf</pre>
<br />
</pre>