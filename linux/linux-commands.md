# 【实用版】Linux快捷命令使用

## 主要内容

- Linux基础文件操作命令
- 实用扩展命令
  - 防火墙命令
  - 查看服务器信息的命令
- docker命令
  - docker目录挂载权限问题
  - docker基本使用流程

## 一 Linux基础文件操作命令

```sh
cd /opt		#进入/opt目录，支持多级目录
cd ..	#切换到上一级目录
cd /	#切换到根目录
cd ~	#切换到用户目录
cd ../..	#向上切换两级目录
ls	#显示当前目录下文件信息
ls -a	#显示当前文件所有文件信息包含隐藏文件
ls -l	#显示长文件信息
ls -al
pwd		#查看当前路径	

mkdir 文件夹名	#创建文件夹
touch 文件名		#创建文件
rm -f  文件名 	#删除文件(最后一级文件，如果存在上一级，则不能删除)-支持表达式
rm -rf  文件名	#删除文件(如果存在下一级目录，则一起删除)-支持表达式（慎用）
mv  老目录 新目录		#移动文件到另一个目录
mv 老文件名 新文件名		#重命名
cp 文件目录 新文件目录		#复制文件到新目录
vim 文件名		#编辑文件（按esc键之后，输入“：wq！”代表保存退出）
file 文件名		#查看文件类型

diff --brief	#显示比较后的结果，判断文件是否相同

diff -c		#命令来描述文件内容具体的不同

man man #帮助命令
```

## 二 实用扩展命令

### 2.1 开机关机命令 ###

**shutdown命令选项  -h表示关闭系统  -r表示重启系统**

```sh
shutdown -h now  #立即关机

shutdown -h +45		#延时关机 定时45分钟后关闭系统

shutdown -r now "system will be reboot now" #关机并发出警告

shutdown -h 01:00	#定时关机 定时在01:00时关机

reboot #重启系统
```

### 2.2 查看并调用历史命令 ###

1. **查看历史命令**

   ```sh
   history		#查看历史命令
   history -c		#清除历史命令
   ```

2. **调用历史命令**

   ```sh
   !6		#运行第六条命令
   !-6		#运行倒数第六条命令
   !!		#运行上一条命令
   
   或者使用上下键查看历史命令
   ```

### 2.3 查看文件内容命令 ###

```sh
cat 文件名  #查询文件内容较少命令
more 文件名 #按照百分比查看文件内容，按空格键或者回车键继续
head -n 10 文件	#查看文件前10行内容
tail -n -200 -f 文件名		#动态的查看文件内容，可查看日志等，持续生成文件 
```

### 2.4 查询进程和查找文件的方法 ###

1. 查询服务或者端口号对应的进程

   ```sh
   ps -ef|grep tomcat/端口号	#查看当前tomcat运行的进程列表
   kill 进程号		#杀死进程
   kill -9 进程号	#强制杀死进程
   ```

2. 查询文件的方法

   ```sh
   find / -name "*.txt"	#查询根目录下所有txt扩展名的文件（*为通配符 表示一个或者多个）
   find . -ctime -20	#查询当前目录及其子目录下最近20天更改过的文件
   ```

### 2.5 查看系统信息的方法 ###

1. 计算机及操作系统相关信息

   ```sh
   uname -a	#显示全部信息
   uname -r	#显示内核版本
   cat /etc/redhat-release # 查看系统版本
   ```

2. 查看和修改主机名（配置文件路径：/etc/hostname）

   ```sh
   hostname	#查看主机名
   hostname "yim"		#暂时修改主机名为yim,重启失效
   hostnamectl set-hostname "yim"		#永久性修改主机名为yim
   ```

3. 查看日历信息

   ```sh
   cal		#显示当前月日历
   cal -3		#显示上中下三个月日历
   cal 2019	#显示2019年日历
   cal 3 2019		#显示2019年3月日历
   cal -jy		#显示今日日历
   ```

4. 查看系统时间

   ```sh
   date
   date "+%Y-%m-%d %H:%M:%S"	#按照“年-月-日 小时:分钟:秒”的格式查看当前系统时间
   ```

5. 查看系统运行时间

   ```sh
   uptime -p	#已运行时间
   uptime -s	#开始运行的时间
   ```

6. 查看系统使用情况

   ```sh
   top		#查看系统性能
   
   top命令执行结果的前5行为系统整体的统计信息，其所代表的含义如下。
   
   #第1行：系统时间 运行时间 登录终端数 系统负载（三个数值分别为1分钟 5分钟 15分钟内的平均值，数值越小意味着负载越低）。
   
   #第2行：进程总数 运行中的进程数 睡眠中的进程数 停止的进程数 僵死的进程数。
   
   #第3行：用户占用资源百分比 系统内核占用资源百分比 改变过优先级的进程资源百分比 空闲的资源百分比等。其中数据均为CPU数据并以百分比格式显示，例如“97.1 id”意味着有97.1%的CPU处理器资源处于空闲。
   
   #第4行：物理内存总量 内存使用量 内存空闲量 作为内核缓存的内存量。
   
   #第5行：虚拟内存总量 虚拟内存使用量 虚拟内存空闲量 已被提前加载的内存量。
   ```

7. 查看用户所在组群和uid信息

   ```sh
   id root		#查看root信息
   ```

8. 查看系统内存使用信息

   ```sh
   free -h 
   ```

9. 查看系统登录记录

   ```sh
   last
   ```

10. 查看ip信息

    ```
    ifconfig # centos7需要安装依赖
    ip
    ip addr
    ip link
    ```

11. 查看系统进程

    ```sh
    ps -a
    
    #Linux系统中时刻运行着许多进程，如果能够合理地管理它们，则可以优化系统的性能。在Linux系统中，有5种常见的进程状态，分别为运行 中断 不可中断 僵死与停止，其各自含义如下所示。
    
    R（运行）：进程正在运行或在运行队列中等待。
    
    S（中断）：进程处于休眠中，当某个条件形成后或者接收到信号时，则脱离该   状态。
    
    D（不可中断）：进程不响应系统异步信号，即便用kill命令也不能将其中断。
    
    Z（僵死）：进程已经终止，但进程描述符依然存在, 直到父进程调用wait4()系统函数后将进程释放。
    
    T（停止）：进程收到停止信号后停止运行。
    ```

12. 查看系统端口情况

    **netstat命令**：需要安装
    
    ```sh
    yum -y install net-tools #需要安装的依赖，可以生成ifconfig命令，netstat命令
    
    netstat
      -a 显示所有socket，包括正在监听的。
      -c 每隔1秒就重新显示一遍，直到用户中断它。
      -i 显示所有网络接口的信息，格式同“ifconfig -e”。
      -n 以网络IP地址代替名称，显示出网络连接情形。
      -r 显示核心路由表，格式同“route -e”。
      -t 显示TCP协议的连接情况。
      -u 显示UDP协议的连接情况。
      -v 显示正在进行的工作。
    
    netstat -tln  #查看运行的TCP连接
    netstat -tuwnpa     #查看所有端口号
    ```
    
    **ss命令**：系统自带：比netstat更快速更高效
    
    ```sh
    -h, --help 帮助
    -V, --version 显示版本号
    -t, --tcp 显示 TCP 协议的 sockets
    -u, --udp 显示 UDP 协议的 sockets
    -x, --unix 显示 unix domain sockets，与 -f 选项相同
    -n, --numeric 不解析服务的名称，如 "22" 端口不会显示成 "ssh"
    -l, --listening 只显示处于监听状态的端口
    -p, --processes 显示监听端口的进程(Ubuntu 上需要 sudo)
    -a, --all 对 TCP 协议来说，既包含监听的端口，也包含建立的连接
    -r, --resolve 把 IP 解释为域名，把端口号解释为协议名称
    
    ss -tnl #查看监听端口的程序名称
    ss -tna #查看建立的 TCP 连接
    ```
    

### 2.6 用户管理命令

1. 用户和群组命令

   ```sh
   useradd 用户名	# 新增一个用户
   groupadd 群组名 #创建一个群组
   
   useradd -g root pp	#创建用户pp，并指定用户群组为root
   userdel -r 用户名	#删除用户 包括用户主目录和邮件存储文件
   userdel -f 用户名	#强制删除
   
   usermod -g wheel pp	#修改用户pp所在的群组为wheel，该群组必须事先存在
   ```

2. 密码管理命令

   ```sh
   passwd		#修改root密码
   passwd pp	#修改pp密码
   passwd -l pp	#锁定pp用户密码
   
   cat /etc/passwd	# 查看用户
   cat /etc/group # 查看组
   ```

### 2.7 设置文件读取权限 ###

1. 设置文件权限

   ```sh
   chmod 777 文件名		#获取文件的所有权限
   chmod -R 777 /opt	#获取opt及其子目录所有文件的所有权限（非root用户获取系统文件所有文件可能会导致系统问题）
   ```

2. 更改文件的所有者权限

   ```sh
   chown pp 文件名		#变更文件所有者为pp
   chown mysql:mysql 文件名 #变更文件所有者为mysql用户组下的mysql用户
   chown -R pp /opt	#变更opt及其子目录文件所有者为pp
   ```

### 2.8 文件压缩解压命令 ###

1. tar后缀文件

   ```sh
   打包：tar -cvf [目标文件名].tar [原文件名/目录名]
   解包：tar -xvf [原文件名].tar
   ```

2. jar后缀文件

   ```sh
   压缩：jar -cvf [目标文件名].jar [原文件名/目录名]
   解压：jar -xvf [原文件名].jar
   ```

3. zip后缀文件

   ```sh
   压缩： zip -r [目标文件名].zip [原文件/目录名]
   解压： unzip [原文件名].zip
   ```

4. tar.xz后缀文件

   ```sh
   打包并压缩： tar -Jcvf [目标文件名].tar.xz [原文件名/目录名]
   解压并解包： tar -Jxvf [原文件名].tar.xz
   ```

### 2.9 文件上传下载命令 ###

```sh
yum install -y lrzsz #安装依赖

rz -be #上传命令

sz -b #下载命令
```

### 2.10 systemctl服务命令 ###

1. **设置开机启动禁用服务**：

   ```sh
   systemctl enable mysqld     #开机启动服务
   systemctl disable firewalld.service     #开机禁用服务
   systemctl status firewalld #查看服务状态
   ```

2. **启动关闭服务命令**：

   ```sh
   systemctl start mysqld  #启动服务        
   systemctl stop mysqld   #关闭mysqld服务
   systemctl restart mysqld #重启mysqld服务
   
   也可以使用service start mysql
   ```

### 2.11 Firewalld防火墙命令 ###

1. 基本操作命令:

   ```sh
   yum install firewalld  #安装firewalld 防火墙
   
   systemctl status firewalld #查看状态
   
   firewall-cmd --reload  #在不改变状态的条件下重新加载防火墙
   ```

2. 启用关闭某个服务

   ```sh
   #临时
   firewall-cmd --zone=public --add-service=https   
   #永久
   firewall-cmd --permanent --zone=public --add-service=https
   
   #关闭已启用的服务
   firewall-cmd --permanent --zone=public --remove-service=https
   ```

3. 开启关闭某个端口

   ```sh
   #临时
   firewall-cmd  --zone=public --add-port=8080-8081/tcp   
   #永久
   firewall-cmd --permanent --zone=public --add-port=8080-8081/tcp
   #关闭已开放的端口
   firewall-cmd --permanent --zone=public --remove-port=8080/tcp
   ```

4. 查看开启的端口和服务

   ```sh
    #服务空格隔开  例如 dhcpv6-client https ss   
   firewall-cmd --permanent --zone=public --list-services
   
    #端口空格隔开  例如  8080-8081/tcp 8388/tcp 80/tcp
   firewall-cmd --permanent --zone=public --list-ports
   
   #查看服务是否生效（例：添加的端口为8080）
   firewall-cmd --zone=public --query-port=3306/tcp
   ```

### 2.12 SSH命令 ###

```sh
ssh root@192.168.231.151 	#--ssh命令以root身份登录192.168.231.151

exit #退出登录
```

### 2.13 网络下载命令 ###

```sh
wget #下载地址
```

### 2.14 Tomcat常用命令 ###

```sh
./startup.sh	#启动tomcat（在bin目录下运行）
./shutdown.sh	#停止tomcat（在bin目录下运行）
./catalina.sh run	#启动tomcat命令，会在命令窗口显示启动日志，关系命令窗口则程序关系
```

### 2.15 dd命令 ###

dd命令用于按照指定大小和个数的数据块来复制文件或转换文件，格式为“dd [参数]”。

dd命令是一个比较重要而且比较有特色的一个命令，它能够让用户按照指定大小和个数的数据块来复制文件的内容。当然如果愿意的话，还可以在复制过程中转换其中的数据。Linux系统中有一个名为/dev/zero的设备文件，每次在课堂上解释它时都充满哲学理论的色彩。因为这个文件不会占用系统存储空间，但却可以提供无穷无尽的数据，因此可以使用它作为dd命令的输入文件，来生成一个指定大小的文件。
```sh
if	#输入的文件名称
of	#输出的文件名称
bs	#设置每个“块”的大小
count	#设置要复制“块”的个数
```

例子：

```sh
dd if=/dev/zero of=560_file count=1 bs=560 		#从/dev/zero设备文件中取出一个大小为560MB的数据块
```

dd命令的功能也绝不仅限于复制文件这么简单。如果您想把光驱设备中的光盘制作成iso格式的镜像文件，在Windows系统中需要借助于第三方软件才能做到，但在Linux系统中可以直接使用dd命令来压制出光盘镜像文件，将它变成一个可立即使用的iso镜像

例子：使用U盘进行镜像文件刻录

```sh
fdisk -l 	#查看U盘名称

dd if=iso文件 of=/dev/sdc
```

### 2.16 定时任务命令

**crontab命令**

系统调度的任务一般存放在/etc/crontab这个文件下，里面存放了一些系统运行的调度程序，通过命令cat crontab查看

## 三 docker常用命令

### 3.1 centos中安装docker

```sh
yum -y install docker 
```

### 3.2 基本使用命令（以MongoDB安装为例）

1. 拉取镜像文件命令

   ```
   docker pull mongo:4.0.3
   ```

2. docker create 命令（创建容器不运行）

   ```sh
   docker create --name mongodb -p 27017:27017 -v /data/mongodb:/data/db --privileged=true mongo:4.0.3
   
   docker create --name percona -v /data/mysql:/var/lib/mysql --privileged=true -p 33306:3306 -e MYSQL_ROOT_PASSWORD=root percona:5.7.23
   
   --name： percona #指定是容器的名称
   -v：   /data/mysql-data:/var/lib/mysql #将主机目录/data/mysql-data挂载到容器的/var/lib/mysql上
   --privileged=true #修改centos安全模块selinux权限
   -p：   33306:3306 #设置端口映射，主机端口是33306，容器内部端口3306
   -e：   MYSQL_ROOT_PASSWORD=root #设置容器参数，设置root用户的密码为root
   percona:5.7.23：#镜像名:版本
   
   #目录挂载易出现权限问题，修改对应文件权限，目录挂载的目的，容器中不要存放需要保留的数据
   
   # 1.添加参数： 
   --privileged=true
   # 2.临时关闭selinux：
   setenforce 0 #临时关闭
   setenforce 1 #开启
   # 3.添加linux规则，把要挂载的目录添加到selinux白名单
   chcon [-R] [-t type] [-u user] [-r role] 文件或者目录
   选顷不参数： 
   -R  ：该目录下的所有目录也同时修改； 
   -t  ：后面接安全性本文的类型字段，例如 httpd_sys_content_t ； 
   -u  ：后面接身份识别，例如 system_u； 
   -r  ：后面街觇色，例如 system_r
   ```

3. docker run 命令（创建容器并运行）

   ```sh
   -d #后台运行
   -it #交互模式运行
   
   docker run --name percona -v /data/mysql:/var/lib/mysql --privileged=true -p 33306:3306 -e MYSQL_ROOT_PASSWORD=root percona:5.7.23
   ```

4. 进入容器

   ```sh
   docker exec -it mongodb /bin/bash
   ```

5. 启动容器

   ```sh
   docker start/restart/stop mongodb
   
   docker start -a mongodb
   ```

6. 查看容器和镜像

   ```sh
   docker ps	查看运行中容器
   
   docker ps -a  查看所有
   
   docker images	查看已下载镜像
   ```

7. 删除容器和镜像（可删除一个或多个）

   ```sh
   docker rm -f  d60e 89d （CONTAINER ID）
   
   docker rmi -rf	d60e（IMAGE ID）
   ```

8. 查看运行中容器

   ```sh
   docker top mongodb
   ```


## 四  性能诊断命令

参看2.5查看系统信息的方法：

- top
- free
- uptime：方便查看系统负载信息

### 4.1 查看CPU

```sh
# 每两秒采样一次攻击采样三次
# vmstat主要用来查看CPU
vmstat -n 2 3

# 查看所有cpu进程数，每两秒采样一次
mpstat -P ALL 2

# 每个进程使用cpu的分解信息，每一秒采样一次
pidstat -u 1 -p <pid> 

# 查看进程占用内存信息
pidstat -p <pid> -r 采样间隔秒数
```

### 4.2 查看磁盘操作

```sh
# 查看磁盘剩余空间数
df -h

# 查看磁盘IO
iostat -xdk 2 3 

# 查看进程磁盘io
pidstat -d 2 -p <pid>

# 查看网络IO
ifstat
```

### 4.3 cpu占用过高，定位和分析

1. 使用top命令查看cpu占比最高的

2. ps -ef或者jps进一步定位，得知是一个怎么样的后台程序

   ```sh
   # jps方式定位
   jps -l
   
   # ps方式定位 
   ps -ef|grep java|grep -v grep
   ```

3. 定位具体线程或者代码

   ```sh
   # 能够查看到具体tid
   # -m 显示所有的线程
   # -p pid进程使用cpu的时间
   # -o 该参数后是用户自定义格式
   ps -mp 进程id -o THREAD，tid，time
   ```

4. 将需要的线程ID转换成16进制格式（英文小写格式）

   ```sh
   # 3中查到有问题的线程id，进行16进制转换
   prinf "%x\n" tid
   ```

5. jstack 进程id | grep tid（16进制线程id小写英文）

   

