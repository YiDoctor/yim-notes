# Linux下搭建mysql主从服务器 #

2018/12/9 20:47:54 

## 一 配置信息 ##

### Mysql主服务器： ###

    系统: centOS-7
    版本: mysql-5.7.24
    IP地址: 192.168.192.168
    port: 3306

### Mysql从服务器 ###

    系统: centOS-7
    版本: mysql-5.7.24
    IP地址: 192.168.192.168
    port: 3306

### 准备工作 ###

Linux系统 
SSH工具
安装MySQL数据库
开放3306端口号
创建需要同步的库

## 二 搭建步骤 ##

1 登录主服务器，执行如下命令

    mysql -u root -p

    提示密码安全策略问题：
    set global validate_password_policy=0;

    grant replication slave on *.* TO 'yimu'@'192.168.192.200' identified by 'Zhou+5201314';//加入从服务器信息
    flush privileges;


2 编辑主服务器的数据库配置文件信息my.cnf

    vi /etc/my.cnf
    
    添加如下信息：
		server-id=168 #IP地址最后几位
		log_bin=/var/log/mysql/mysql-bin.log #同步日志文件名
		read-only=0 #只读权限 读写分离
		binlog-do-db=test #同步指定的库
		binlog-ignore-db=mysql #忽略掉mysql库

3 登录从服务器，执行如下命令

    编辑从服务器的数据库配置文件信息：my.cnf
    
    vi /etc/my.cnf
    
	server-id=227
	log_bin=/var/log/mysql/mysql-bin.log
    
4 重启主服务器

    service mysqld restart
    
    提示信息如下

![](https://i.imgur.com/0yMcOIU.png)

    修改

主从服务器下

    进入/var/log/文件夹下，新建文件mysql，进入mysql目录，新建文件mysql-bin.log文件，并赋予读写权限(mysql和mysql-bin.log)

5 登录主服务器，查看master状态

    show master status\G

![](https://i.imgur.com/JJMnBxg.png)

6 登录从服务器，设置主从关系

    stop slave;

    从服务器写入主服务器相关信息,注意其主从一致性
    
    change master to master_host='192.168.192.168',master_user='backup',master_password='Zhou+5201314',master_log_file='mysql-bin.000177',master_log_pos=154;

    start slave;

第一次配置可能出错信息:可能未能正常初始化

    ERROR 1794 (HY000): Slave is not configured or failed to initialize properly. You must at least set --server-id to enable either a master or a slave. Additional error messages can be found in the MySQL error log.

解决

    删表
    use mysql
    drop table slave_master_info;
    drop table slave_relay_log_info;
    drop table slave_worker_info;
    drop table innodb_index_stats;
    drop table innodb_table_stats;
    
    重新导入
    source /usr/share/mysql/mysql_system_tables.sql //该sql文件所在位置不同系统可能不同

    重启数据库 service mysqld restart
    再次登录

7 查看从服务器的主从关系状态

    show slave status\G

	其中
	    Slave_IO_Running: Yes  \\表示从服务器IO线程运行
	    Slave_SQL_Running: Yes	\\表示从服务器SQL线程运行
	
	![](https://i.imgur.com/gfdYBvj.png)

如果都为yes则主从搭建成功

8 主从服务器下执行

    /sbin/iptables -I INPUT -p tcp --dport 3306 -j ACCEPT #开放3306端口、

	firewall-cmd --permanent --zone=public --add-port=3306/tcp #centos7更换防火墙设置

9 如果主从库中不存在test库，则需要重新建库，然后重启，重新构建主从关系
    

## 主从知识点 ##

### 用途 ###

    时时备灾,用于故障切换
    读写分离,提供查询服务
    备份,避免影响业务(备可容错性 可用性)
    负载均衡

### 必要条件 ###

    主库开启binlog日志(设置bin-log参数)
    主从server-id不同
    从库服务器能连通主库

### 原理 ###
![](https://i.imgur.com/e0YmkFS.png)

- 从库生成两个线程 一个SQL线程 一个I/O线程;
- I/O线程会去请求主库的binlog 并得到binlog日志写到relay log(中继日志)文件中;
- 主库会生成一个log dump线程 用来给从库I/O线程传binlog;
- SQL线程 会读取relay log文件中的日志 并解析成具体的操作,来实现主从的操作一致,最终数据一致;


### 关系 ###

![](https://i.imgur.com/7wQj3Mf.png)