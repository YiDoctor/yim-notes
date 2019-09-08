# Linux下nginx搭建集群 #

date: 2018/12/11 11:44:29 

前言
----

说明:nginx搭建tomcat集群十分简单,而且windows上和Linux上面的方式是一样的!不同的是Linux系统上面安装nginx要复杂一些,文章的目的实在掌握nginx用法的基础上深入了解其原理,知道其优缺点及性能

## window下安装nginx ##

1 windows下官网直接在下载最新稳定版 地址:[http://nginx.org/en/download.html](http://nginx.org/en/download.html)

2 解压到指定目录,打开解压文件,启动nginx.exe

3 查看启动:[http://127.0.0.1:80](http://127.0.0.1:80)

## 配置conf/nginx.conf配置文件 ##

### 了解配置文件结构 ###

详参nginx中文参考文档: [http://shouce.jb51.net/nginx/left.html](http://shouce.jb51.net/nginx/left.html)

### 配置文件/conf/nginx.conf增改内容 ###

    upstream test{
        server  127.0.0.1:8080;
        server  127.0.0.1:8090;
        server  127.0.0.1:8100;
    }

    location /{
        proxy_pass  http://test;
    }

### 负载均衡策略 ###

- 轮询--默认策略 按顺序逐一分配;
- 最少连接--把请求分配到最少的连接;
- 权重--用weight指定server的访问比率,默认为1;
- ip_hash--每一个请求会按照访问的ip的hash值进行分配,这样同一客户端连续的web请求都会分配到同一server进行处理,可以解决session问题

        upstream test{
            ip_hash;
            least_conn;
            server  127.0.0.1:8080 weight=2;
            server  127.0.0.1:8090 weight=3;
            server  127.0.0.1:8100;
        }


window下kill 需要管理员权限

    taskkill /f /t /im nginx.exe

## 应用集群模式下，Session共享问题解决 ##

- 方式一：tomcat自带session复制功能，可以解决session共享问题

        1：在conf文件夹下，找到server.xml,修改配置文件，将注释内的内容，复制到标签内使用，如下：
        
            <Engine name="Catalina" defaultHost="localhost" jvmRoute="8080">
            <Cluster className="org.apache.catalina.ha.tcp.SimpleTcpCluster"/>
        
        2：将所有tomcat服务器内的配置，都需要修改，并重启即可
        
        问题：如果以上还没有解决共享问题，才启用下面配置。
        
            1：终极解决方案：在web.xml中添加一行代码，代码位置是固定的，必须放到标签<display-name>下一行，如下：
            <distributable/>


- 方式二：使用redis来实现session共享
        
        1：在框架中集成redis之后，用户登录成功后，将session信息存放到redis中
        2：在登录检测拦截器中，从redis中获取当前用户信息，检验用户session
        3：在redis中可以设置session有效期