# Mysql安装和主从配置

## 主要内容

- Mysql的安装
- Mysql基本操作命令
- Mysql搭建主从配置

## 一 Mysql的安装

### 1.1 centos下卸载mysql

```sh
rpm -qa | grep -i mysql  #1. 查看mysql是否安装

systemctl stop mysqld  #2. 关闭mysqld服务

# 3. 删除依赖 --nodeps强力删除
    rpm -ev mysql-community-libs-5.7.26-1.el7.x86_64 --nodeps
    rpm -ev mysql57-community-release-el7-11.noarch --nodeps
    rpm -ev mysql-community-common-5.7.26-1.el7.x86_64 --nodeps
    rpm -ev mysql-community-client-5.7.26-1.el7.x86_64 --nodeps
    rpm -ev mysql-community-libs-compat-5.7.26-1.el7.x86_64 --nodeps
    rpm -ev mysql-community-server-5.7.26-1.el7.x86_64 --nodeps

find / -name mysql  #4. 查找并删除文件和库

    rm -rf /etc/selinux/targeted/active/modules/100/mysql
    rm -rf /var/lib/mysql
    rm -rf /var/lib/mysql/mysql
    rm -rf /usr/lib64/mysql
    rm -rf /usr/share/mysql
    
	rm -rf /etc/my.cnf  #注意：卸载后/etc/my.cnf不会删除，需要进行手工删除

rpm -qa | grep -i mysql  #5. 再次查看
```

### 1.2 centos下安装

Mysql的下载地址及官方安装文档方法：https://dev.mysql.com/downloads/mysql/

#### 1.2.1 mysql80下载

```sh
# 下载
wget https://dev.mysql.com/get/mysql80-community-release-el7-1.noarch.rpm
# 安装
yum localinstall mysql80-community-release-el7-1.noarch.rpm
# 安装服务
yum install mysql-community-server
```

#### 1.2.2 mysql57下载

```sh
# 下载
wget http://repo.mysql.com/mysql57-community-release-el7-11.noarch.rpm
# 安装
rpm -ivh mysql57-community-release-el7-11.noarch.rpm
# 安装服务
yum install mysql-server
```

#### 1.2.3  登录并修改密码

```sh
systemctl start mysqld #启动服务

cat /var/log/mysqld.log | grep password #查看初始密码

mysql -u root -p #登录命令

# 修改初始化密码（密码一定要大小写字母+数字+符号，如：Aa-123456789）
ALTER USER 'root'@'localhost' IDENTIFIED BY '密码';
```

#### 1.2.4  设置mysql访问权限

```sh
use mysql; # 1. 选择mysql库

update user set host='%' where user='root'; # 2.设置允许远程访问

# 允许任何主机访问数据库
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'WITH GRANT OPTION;

# 允许root用户使用mypassword密码从任何主机连接到mysql服务器
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '密码' WITH GRANT OPTION;

# 允许用户root从ip为192.168.1.6的主机连接到mysql服务器，并使用mypassword作为密码
GRANT ALL PRIVILEGES ON *.* TO 'root'@'192.168.1.3'IDENTIFIED BY '密码' WITH GRANT OPTION;
```

#### 1.2.5 Mysql80连接上的一些变化

```sh
#  驱动地址发生改变，原来：driverClassName: com.mysql.jdbc.Driver
driverClassName: com.mysql.cj.jdbc.Driver

# 需要在url之后配置useSSL和serverTimezone
url: jdbc:mysql://localhost/ovls?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false
```

### 1.3 windows中安装Mysql

windows需要以管理员身份打开cmd窗口

```sh
# mysql/bin 目录下执行初始化命令（data目录需删除），初始化后注意查看初始密码
mysqld --initialize --console

# 进行服务的安装
mysqld -install

# 启动服务
net start mysql

mysql -u root -p

ALTER USER root@localhost IDENTIFIED  BY '123456'; 

# 如果安装中出现错误可，使用命令删除mysql服务,进行重安装
sc delete mysql
```

### 1.4 tar.gz包安装Mysql

#### 1.4.1 创建文件目录并创建Mysql用户

```sh
# Mysql中如果没有指定basedir和datadir这会使用默认路径，指定目录可在/etc/my.cnf中添加，Mysql启动时会首先检索改文件
cd /usr/local
tar -zvxf mysql-5.7.25-linux-glibc2.12-x86_64.tar.gz # 解压
ln -s mysql-5.7.25-linux-glibc2.12-x86_64 mysql # 创建软连接，软连接文件不占用内容，和源文件操作同步
mkdir mysql/data #创建mysql数据目录

groupadd mysql
useradd -r -g mysql -s /bin/false mysql #-r表示系统用户，无法登陆。创建该用户的目的是为了数据的所有权，而不是为了登录

chown mysql:mysql /usr/local/mysql/data  #变更mysql数据目录用户组及用户
```

#### 1.4.2 初始化操作

```sh
# 配置mysql环境变量
PATH=$PATH:$HOME/bin:/usr/local/bin

# 执行初始化操作 -insecure生成空密码，登录时直接回车
# --user=mysql 指定初始化文件的拥有者 没有在默认目录下安装，需要指定安装目录和mysql数据目录。
mysqld --initialize-insecure --user=mysql

# 启动传输加密SSL
mysql_ssl_rsa_setup
# 启动
mysqld_safe --user=mysql
```

#### 1.4.3  添加mysql到服务，作为服务启动

```sh
# 加入服务
cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql.server
# 查看服务列表
chkconfig --list
```

## 二 mysql数据库操作命令		

```sql
show databases; --查看数据库

create database 库名 -- 创建表库
示例：
CREATE DATABASE IF NOT EXISTS `库名` DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 

drop database 库名; --删库

use 库名; -- 选择要使用的库  

show tables; --查看表

describe 表名 -- 查看表描述

source opt/sqlFileName.sql -- 导入数据资源
```

