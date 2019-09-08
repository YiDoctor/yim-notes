# Linux下安装Nginx #

date: 2018/12/9 18:46:39 

## Linux下安装Nginx ##

1 安装wget,用来下载nginx安装包

    yum install wget -y

2 因为Nginx以来与gcc的编译环境，所以，在mini centos中需要安装编译环境来使Nginx能够编译起来

    yum install gcc-c++

3 Nginx的http模块需要使用pcre来解析正则表达式

    yum -y install pcre pcre-devel

4 依赖的解压包

    yum -y install zlib zlib-devel

5 openssl安装

    yum install -y openssl openssl-devel

6 切换到opt目录下

7 官网下载 [http://nginx.org/en/download.html](http://nginx.org/en/download.html) 找到自己需要的版本下载

    wget  http://nginx.org/download/nginx-1.15.7.tar.gz

    tar zxvf nginx-1.15.7.tar.gz

8 编译 安装

    mkdir nginx

    cd nginx-1.15.7 解压目录

    ./configure  --prefix=/opt/nginx  #指定安装目录

9 在/opt/nginx-1.15.7目录下执行编译命令

    make

10 执行安装命令

    make install

11 启动

	cd /

    cd /opt/nginx/sbin/
    
    ./nginx 
    ./nginx -s reload
    ./nginx -s quit:此方式停止步骤是待nginx进程处理任务完毕进行停止。
    ./nginx -s stop:此方式相当于先查出nginx进程id再使用kill命令强制杀掉进程。

12 开放nginx默认端口号80

    /sbin/iptables -I INPUT -p tcp --dport 80 -j ACCEPT

13 远程访问进行安装测试

![](https://i.imgur.com/OZepdGC.png)