# Linux配置网卡信息  #

date: 2018/12/7 16:13:52

## Linux配置网卡信息 ##

第1步：首先切换到/etc/sysconfig/network-scripts目录中（存放着网卡的配置文件）。

第2步：使用Vim编辑器修改网卡文件ifcfg-ens33，逐项写入下面的配置参数并保存退出。由于每台设备的硬件及架构是不一样的，因此请读者使用ip addr命令自行确认各自网卡的默认名称

	设备类型：TYPE=Ethernet
	
	地址分配模式：BOOTPROTO=static
	
	网卡名称：NAME=eno16777736
	
	是否启动：ONBOOT=yes
	
	IP地址：IPADDR=192.168.10.10
	
	子网掩码：NETMASK=255.255.255.0
	
	网关地址：GATEWAY=192.168.10.1
	
	DNS地址：DNS1=192.168.10.1

第3步：重启网络服务并测试网络是否联通。

## 我在VMware配置网络环境的实操 ##

### 1、查看Vmware虚拟机可配置的IP地址信息 ###

	1 点击虚拟机左侧，选中安装的系统，右键，设置，查看网络连接信息,【硬件】页签，选择【网络适配器】，网络连接选择【NAT...】，点击【确定】按钮 
	
	2 在虚拟机中，选中安装的系统，点击工具条中的【编辑】-选择【虚拟网络编辑器】
	
	3 选中【VMnet8】，点击【NAT设置】，查看本地IP网段
	
	4 点击【DHCP设置】，查看IP起始段，方便设置IP地址 

![](https://i.imgur.com/AFOaO9g.png)

### 2、进入命令窗口，cd / 切换到root的根目录，输入命令：vim /etc/sysconfig/network-scripts/ifcfg-ens33  ###

	TYPE=Ethernet
	PROXY_METHOD=none
	BROWSER_ONLY=no
	BOOTPROTO=static（修改成静态ip地址）
	DEFROUTE=yes
	IPV4_FAILURE_FATAL=no
	IPV6INIT=yes
	IPV6_AUTOCONF=yes
	IPV6_DEFROUTE=yes
	IPV6_FAILURE_FATAL=no
	IPV6_ADDR_GEN_MODE=stable-privacy
	NAME=ens33
	UUID=0264c4d6-a7db-4f88-abe1-04235edb6b32
	DEVICE=ens33
	ONBOOT=yes（改为yes，以下内容为配置的网络信息）
	IPADDR=192.168.192.168（上一步第四步查看ip段位）
	GATEWAY=192.168.192.2
	NETMASK=255.255.255.0
	DNS1=114.114.114.114

点击键盘【i】键，修改并增加如下内容信息，完成之后，点击键盘【Esc】键，输入【:wq!】,点击回车键，保存退出 


### 3、重启network服务 ping测试 ###

	service network restart 或者
	systemctl restart network

使用ping命令验证网络

    ping www.baidu.com

由于在Linux系统中ping命令不会自动终止，因此需要手动按下Ctrl-c键来强行结束进程。

### 4.重启服务时，出现错误 ###

 查看VMware服务是否启动
