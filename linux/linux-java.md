# Centos搭建Java环境 #

1 查看当前系统版本

	lsb_release -a
	
	cat /proc/version

2 查看java版本

    java -version   

3 查看当前系统是否存在JDK安装包

    rpm -qa | grep java    
    
    删除：
    rpm -e --nodeps java-1.7.0-openjdk-1.7.0.99-2.6.5.1.el6.x86_64
    rpm -e --nodeps java-1.6.0-openjdk-devel-1.6.0.38-1.13.10.4.el6.x86_64
    rpm -e --nodeps java-1.6.0-openjdk-javadoc-1.6.0.38-1.13.10.4.el6.x86_64
    rpm -e --nodeps java-1.5.0-gcj-1.5.0.0-29.1.el6.x86_64
    rpm -e --nodeps java-1.6.0-openjdk-1.6.0.38-1.13.10.4.el6.x86_64
    rpm -e --nodeps java-1.7.0-openjdk-devel-1.7.0.99-2.6.5.1.el6.x86_64

4 cd到安装目录

    cd /opt   

5 wget命令在线下载(需安装wget依赖  yum install wget -y)

    wget  https://download.oracle.com/otn-pub/java/jdk/8u201-b09/42970487e3af4f5aa5bca3f542482c60/jdk-8u201-linux-x64.tar.gz?AuthParam=1554344933_daa07180539ea5251353417db717a099  

下载完成后改一下名称

6 解压命令

    tar -zvxf jdk-8u191-linux-x64.tar.gz
    tar -czvf ***.tar.gz   

7 配置环境变量 

    vim ~/.bash_profile
    
    输入如下内容：
    export JAVA_HOME=/opt/jdk1.8
    export CLASSPATH=$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
    export PATH=$PATH:$JAVA_HOME/bin

![](https://i.imgur.com/K5kASfP.png)

点击键盘【i】键，修改并增加如下内容信息，完成之后，点击键盘【Esc】键，输入【:wq!】,点击回车键，保存退出

8 使配置生效 

```sh
source ~/.bash_profile 
```

9 检验安装结果 

```sh
java -version
```

环境变量:

```sh
export JAVA_HOME=/opt/jdk1.8
export CLASSPATH=$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin

export MAVEN_HOME=/opt/apache-maven-3.6.0
export PATH=$MAVEN_HOME/bin:$PATH

export ERLANG_HOME=/opt/erlang
export PATH=$ERLANG_HOME/bin:$PATH
```

