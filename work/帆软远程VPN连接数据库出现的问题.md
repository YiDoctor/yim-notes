title: 帆软远程VPN连接数据库出现的问题

date： 2019/1/21 10:12:32 

------

## 帆软远程VPN连接数据库出现的问题 ##

问题：使用VPN连接远程数据库时出现java.sql.SQLException: Io 异常: The Network Adapter could not establish the connection异常错误，考虑到和之前使用myEclipse遇到的问题相同

eclipse有时在联网时会将ipv4转换为ipv6使用，导致报错：The Network Adapter could not establish the connection，说明都是在ip地址在转换时出错了，帆软报表可通过如下图方式进行解决

1、找到帆软报表参数配置文件，进行编辑
![](https://i.imgur.com/g5GZX9Y.png)

2、添加如下内容，使之只支持IPV4

	-Djava.net.preferIPv4Stack=true 
![](https://i.imgur.com/WO4hoEY.png)




