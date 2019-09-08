# redis #

## 主要内容

- 关系型数据库和非关系型数据
- Nosql分类区别
- redis安装、集群
- redis主要知识点
- 应用场景

## 一 NoSQL和关系型数据库

NoSQL指的是非关系型的数据库。其实，NoSQL概念最早出现在1998年，当时的含义是反SQL技术革命运动，但并未引起太多的关注。直到2009年，NoSQL概念被来自Rackspace的EricEvans再次提出，这时的NoSQL已经不是单纯的反SQL运动，指的主要是非关系型的分布式数据库，并且不支持原子性(Atomicity)、一致性(Consistency)、隔离性(Isolation)、持久性(Durability)的数据库设计模式。在亚特兰大举行的有关NoSQL的讨论会上，学者给出了NoSQL较为普遍的解释，即“非关系型的”。

并且指出**Key-Value存储和文档数据库**是NoSQL的主要特点。

 **特点:**

1：key-value存储

2：最终一致性

3：可拓展

### 1.1 NoSQL和关系型数据库 ###

关系型数据库

	优势:
	    1.擅长小数据量的处理
	    2.擅长复杂的SQL操作,可以进行Join等复杂查询
	    3.可以方便的生成各种数据对象，利用存储的数据建立窗体和报表，可视性好
	
	劣势：
	    1.很难进行分布式应用和大量数据的写入处理
	    2.为有数据更新的表做索引和结构变更
	    3.字段不固定的应用
	    4.对简单查询需要快速返回结果的处理

NoSQL数据库

	优势:
	    1.擅长大量数据的写入和读取
	    2.快速的查询响应,灵活的数据模型
	    3.数据结构变更或更新非常方便，不需要更改已有数据的数据结构
	    4.击碎了性能瓶颈，可以使执行速度变的更快
	
	劣势:
	    1.不提供复杂的API接口
	    2.一般仅提供key索引
	    3.不适合小数据的处理
	    4.现有产品的不够成熟,大多数产品都还处于初创期
### 1.2 NoSQL 数据库分类 ###
![](https://i.imgur.com/Vc0PwkN.png)

## 二 Redis ##

### **2.1 简介:**

Redis（REmote DIctionary Server）是一个开源的使用ANSI C语言编写、是一个由Salvatore Sanfilippo写的key-value存储系统，支持网络、可基于内存亦可持久化的日志型、并提供多种语言的API。

Redis是一个key-value存储系统。和Memcached类似，它支持存储的value类型相对更多，包括string(字符串)、list(链表)、set(集合)、zset(sorted set --有序集合)和hash（哈希类型）。

与memcached一样，为了保证效率，数据都是缓存在内存中。区别的是redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。

### 2.2 Redis优点 ###

- 性能极高 – Redis能读的速度是110000次/s,写的速度是81000次/s 。
- 丰富的数据类型 – Redis支持二进制案例的 Strings, Lists, Hashes, Sets 及 Ordered Sets 数据类型操作。
- 原子 – Redis的所有操作都是原子性的，意思就是要么成功执行要么失败完全不执行。单个操作是原子性的。多个操作也支持事务，即原子性，通过MULTI和EXEC指令包起来。
- 丰富的特性 – Redis还支持 publish/subscribe, 通知, key 过期等等特性。
- Redis支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
- Redis支持数据的备份，即master-slave模式的数据备份。

### 2.3 Redis数据类型 ###

#### 2.3.1 string(字符串)

string是redis最基本的类型，而且string类型是二进制安全的。意思是redis的string可以包含任何数据。比如jpg图片或者序列化的对象

从内部实现来看其实string可以看作byte数组，最大上限是1G字节
string类型的值也可视为integer，从而可以让“incr”命令族操作，这种情况下，该integer的值限制在64位有符号数
在list、set和zset中包含的独立的元素类型都是string类型

应用场景：String是最常用的一种数据类型,普通的key/value存储.

#### 2.3.2 list(双向链表)

redis的list类型其实就是一个每个子元素都是string类型的双向链表，所以[lr]push和[lr]pop命令的算法时间复杂度都是O(1)，另外list会记录链表的长度，所以llen操作也是O(1).

可以通过push,pop操作从链表的头部或者尾部添加删除元素。这使得list既可以用作栈，也可以用作队列
list的最大长度是2^32-1个元素

应用场景：Redis list应用场景非常多,也是Redis最重要的数据结构之一,比如twitter的关注列表,粉丝列表等都可以用Redis的list结构来实现.

#### 2.3.3 set(无序集合)

set就是redis string的无序集合，不允许有重复元素
set的最大元素数是2^32-1

对set的操作还有交集、并集、差集等

应用场景：Set对外提供的功能与list类似,当你需要存储一个列表数据,又不希望出现重复数据时,set 是一个很好的选择,并且set提供了判断某个成员是否在一个set集合内的接口,这个也是list所不能提供的

#### 2.3.4 Sorted set(有序集合)-- zset

zset是set的一个升级版本，在set的基础上增加了一个顺序属性，这一属性在添加修改元素时可以指定，每次指定后zset会自动安装指定值重新调整顺序。可以理解为一张表，一列存value，一列存顺序。操作中的key理解为zset的名字。

zset的最大元素数是2^32-1。

对于已经有序的zset，仍然可以使用sort命令，通过指定asc|desc参数对其进行排序。

应用场景：Sorted set的使用场景与set类似,区别是set不是自动有序的,而sorted set可以通过用户额外提供一个优先级(score)的参数来为成员排序,并且是插入有序的,即自动排序.当你需要一个有序的并且不重复的集合列表,那么可以选择sorted set数据结构

#### 2.3.5 hash(hash表) 

redis Hash类型对数据域和值提供了映射，这一结构很方便表示对象

在Hash中可以只保存有限的几个“域”，而不是将所有的“域”作为key，这可以节省内存

### 2.4 常见命令 ###

#### 2.4.1 Value操作命令

```sh
exists(key)：#确认一个key是否存在
del(key)：#删除一个key
type(key)：#返回值的类型
keys(pattern)：#返回满足给定pattern的所有key
randomkey：#随机返回key空间的一个
keyrename(oldname, newname)：#重命名key
dbsize：#返回当前数据库中key的数目
expire：#设定一个key的活动时间（s）
ttl：#获得一个key的活动时间
select(index)：#按索引查询
move(key, dbindex)：#移动当前数据库中的key到dbindex数据库
flushdb：#删除当前选择数据库中的所有key
flushall：#删除所有数据库中的所有key
```

#### 2.4.2 string操作的命令

```sh
set(key, value)：#给数据库中名称为key的string赋予值value
get(key)：#返回数据库中名称为key的string的value
getset(key, value)：#给名称为key的string赋予上一次的value
mget(key1, key2,…, key N)：#返回库中多个string的value
setnx(key, value)：#添加string，名称为key，值为value
setex(key, time, value)：#向库中添加string，设定过期时间time
mset(key N, value N)：#批量设置多个string的值
msetnx(key N, value N)：#如果所有名称为key i的string都不存在
incr(key)：#名称为key的string增1操作
incrby(key, integer)：#名称为key的string增加integer
decr(key)：#名称为key的string减1操作
decrby(key, integer)：#名称为key的string减少integer
append(key, value)：#名称为key的string的值附加value
substr(key, start, end)：#返回名称为key的string的value的子串
```

#### 2.4.3 对List操作的命令

```sh
rpush(key, value)：#在名称为key的list尾添加一个值为value的元素
lpush(key, value)：#在名称为key的list头添加一个值为value的 元素
llen(key)：#返回名称为key的list的长度
lrange(key, start, end)：#返回名称为key的list中start至end之间的元素
ltrim(key, start, end)：#截取名称为key的list
lindex(key, index)：#返回名称为key的list中index位置的元素
lset(key, index, value)：#给名称为key的list中index位置的元素赋值
lrem(key, count, value)：#删除count个key的list中值为value的元素
lpop(key)：#返回并删除名称为key的list中的首元素
rpop(key)：#返回并删除名称为key的list中的尾元素
blpop(key1, key2,… key N, timeout)：#lpop命令的block版本。
brpop(key1, key2,… key N, timeout)：#rpop的block版本。
rpoplpush(srckey, dstkey)：#返回并删除名称为srckey的list的尾元素，并将该元素添加到名称为dstkey的list的头部
```

#### 2.4.4 对Set操作的命令

```sh
sadd(key, member)：#向名称为key的set中添加元素member
srem(key, member) ：#删除名称为key的set中的元素member
spop(key) ：#随机返回并删除名称为key的set中一个元素
smove(srckey, dstkey, member) ：#移到集合元素
scard(key) ：#返回名称为key的set的基数
sismember(key, member) ：#member是否是名称为key的set的元素
sinter(key1, key2,…key N) ：#求交集
sinterstore(dstkey, (keys)) ：#求交集并将交集保存到dstkey的集合
sunion(key1, (keys)) ：#求并集
sunionstore(dstkey, (keys)) ：#求并集并将并集保存到dstkey的集合
sdiff(key1, (keys)) ：#求差集
sdiffstore(dstkey, (keys)) ：#求差集并将差集保存到dstkey的集合
smembers(key) ：#返回名称为key的set的所有元素
srandmember(key) ：#随机返回名称为key的set的一个元素
```

#### 2.4.5 对Hash操作的命令

```sh
hset(key, field, value)：#向名称为key的hash中添加元素field
hget(key, field)：#返回名称为key的hash中field对应的value
hmget(key, (fields))：#返回名称为key的hash中field i对应的value
hmset(key, (fields))：#向名称为key的hash中添加元素field 
hincrby(key, field, integer)：#将名称为key的hash中field的value增加integer
hexists(key, field)：#名称为key的hash中是否存在键为field的域
hdel(key, field)：#删除名称为key的hash中键为field的域
hlen(key)：#返回名称为key的hash中元素个数
hkeys(key)：#返回名称为key的hash中所有键
hvals(key)：#返回名称为key的hash中所有键对应的value
hgetall(key)：#返回名称为key的hash中所有的键（field）及其对应的value
```

### 2.5 Redis功能 ###

1 Redis发布/订阅 

Redis的发布/订阅(Publish/Subscribe)功能类似于传统的消息路由功能,发布者发布消息,订阅者接收消息,沟通发布者和订阅者之间的桥梁是订阅的Channel或者Pattern.订阅者和发布者之间的关系是松耦合的,发布者不指定哪个订阅者才能接收消息,订阅者不只接收特定发布者的消息.

2 Redis事务支持

Redis目前对事务支持还比较简单,也即支持一些简单的组合型的命令,只能保证一个client发起的事务中的命令可以连续的执行,而中间不会插入其他client的命令. 由于Redis是单线程来处理所有client的请求的所以做到这点是很容易的.事务的执行过程中,如果redis意外的挂了,这时候事务可能只被执行了一半,可以用redis-check-aof 工具进行修复

### 2.6 Redis持久化 ### 

由于Redis是内存数据库，它将自己的数据库状态存储在内存里面，所以如果不想办法将储存在内存中的数据库状态保存到磁盘里面，那么一旦服务器退出，服务器中的数据库状态也会消失不见。为了解决这个问题，Redis提供了RDB、AOF持久化方式，将内存中的数据保存到磁盘中，避免数据意外丢失

（1）RDB是 Snapshotting（快照）也是默认方式： 快照是默认的持久化方式。这种方式将内存中数据以快照的方式写入到二进制文件中,默认的文件名为dump.rdb。可以配置自动做快照持久 化的方式。我们可以配置redis在n秒内如果超过m个key被修改就自动做快照
RDB持久化通过将服务器某个时间点上的数据库状态（非空数据库以及相关键值对）保存到一个RDB文件中，Redis服务器可以用它来还原数据库状态。 SAVE命令会阻塞Redis服务器进程。而BGSAVE会派生出一个子进程，然后由子进程负责创建RDB文件，服务器父进程继续处理命令请求。还可以SAVE命令设置自动间隔保存， 例如SAVE 60 10000 服务器在60秒之内，对数据库进行了至少10000次修改，自动执行BGSAVE命令。RDB文件是一个经过压缩的二进制文件。

（2）AOF(Append-only file)： AOF持久化通过保存Redis服务器所执行的写命令来记录数据库状态的。被写入AOF文件的所有命令都是以Redis的命令请求协议格式保存的，Redis的命令请求协议保存为纯文本格式。AOF持久化功能的实现分为命令追加、文件写入、文件同步三个步骤：当AOF持久化处于打开状态时，服务器在执行完一个写命令后，会以协议格式将被执行的写命令（如SET、SADD、RPUSH）追加到服务器状态的aofbuf缓冲区的末尾。服务器在每次结束一个事件循环之前，它都会调用flushAppendOnlyFile函数，考虑是否需要将aof_buf缓冲区中的内容写入和保存到AOF文件里面。flushAppendOnlyFile函数的行为由服务器配置的appendfsync选项的值（ always 、 everysec（默认） 、 no ）来决定

### 2.7 RDB与AOF比较  ###
RDB 

- **优点**：RDB 是一个非常紧凑的文件，它保存了 Redis 在某个时间点上的数据集。这种文件非常适合用于进行备份。
- **缺点**：如果你需要尽量避免在服务器故障时丢失数据，那么 RDB 不适合你。 虽然 Redis 允许你设置不同的保存点（save point）来控制保存 RDB 文件的频率， 但是， 因为RDB 文件需要保存整个数据集的状态，所以它并不是一个轻松的操作。因此你可能会至少 5 分钟才保存一次 RDB 文件。 在这种情况下， 一旦发生故障停机， 你就可能会丢失好几分钟的数据。

AOF 

- **优点**：使用 AOF 持久化会让 Redis 变得非常耐久：你可以设置不同的 fsync 策略，比如无 fsync ，每秒钟一次 fsync ，或者每次执行写入命令时 fsync 。 AOF 的默认策略为每秒钟 fsync 一次，在这种配置下，Redis 仍然可以保持良好的性能，并且就算发生故障停机，也最多只会丢失一秒钟的数据（ fsync 会在后台线程执行，所以主线程可以继续努力地处理命令请求）。
- **缺点**：对于相同的数据集来说，AOF 文件的体积通常要大于 RDB 文件的体积。根据所使用的 fsync 策略，AOF 的速度可能会慢于 RDB 。

## 三 redis的安装和集群操作

### 3.1 centos中安装redis

#### 3.1.1 下载安装

```sh
wget http://download.redis.io/releases/redis-5.0.2.tar.gz

yum install gcc tcl -y # 安装依赖

tar -zvxf redis-5.0.2.tar.gz

mv redis-5.0.2 redis

mkdir /usr/redis # 创建bin目录地址

#配置环境变量
export REDIS_HOME=/usr/redis
export PATH=$PATH:$REDIS_HOME/bin

make # 编译

make PREFIX=/usr/redis/ install # 编译安装

/opt/redis/src/redis-server # 运行reids

# 连接redis数据库
redis-cli
```

#### 3.1.2 构造redis服务

```sh
# 构建redis服务
/opt/redis/utils/install_server.sh 

#更改服务名称
cd /etc/init.d/
mv redis_6379 redisd

#先kill redis进程 然后使用service服务启动
service redisd start
service redisd status
service redisd enable
```

#### 3.1.3 配置外网连接

```sh
# 修改配置文件信息
vim redis/redis.conf

bind 127.0.0.1 # 修改绑定地址为:bind 0.0.0.0 可以任意IP访问
protected-mode yes # 关闭保护模式,yes-->no
daemonize no  # 开启守护no-->yes

# 重新加载配置文件
redis-server /opt/redis/redis.conf
```

#### 3.1.4 卸载redis

```sh
ps -ef | grep -i redis # 查看进程,并杀死

rm -rf /usr/redis
rm -rf /opt/redis
rm -rf /etc/init.d/redisd
```

### 3.2 Redis主从复制(集群)

#### 3.2.1 集群配置

集群的作用：读写分离,数据备份,灾难恢复等

```sh
#Master/Slave配置:

Master IP:175.41.209.118

Master Redis Server Port:6379

#Slave配置很简单,只需要在slave服务器的redis.conf加入:

slaveof 175.41.209.118 6379

#启动master和slave,然后写入数据到master,读取slave,可以看到数据被复制到slave了.
```

#### 3.2.2 Redis主从复制过程: 

1. 配置好slave后,slave与master建立连接,然后发送sync命令. 
2. 无论是第一次连接还是重新连接,master都会启动一个后台进程,将数据库快照保存到文件中,同时master主进程会开始收集新的写命令并缓存. 
3. 后台进程完成写文件后,master就发送文件给slave,slave将文件保存到硬盘上,再加载到内存中。
4. 接着master就会把缓存的命令转发给slave,后续master将收到的写命令发送给slave. 
5. 如果master同时收到多个slave发来的同步连接命令,master只会启动一个进程来写数据库镜像, 然后发送给所有的slave

#### 3.2.3 Redis主从复制特点

1. master可以拥有多个slave.
2. 多个slave可以连接同一个master外,还可以连接到其他slave.
3. 主从复制不会阻塞master,在同步数据时,master可以继续处理client请求.
4. 可以在master禁用数据持久化,注释掉master配置文件中的所有save配置,只需在slave上配置数据持久化.
5. 提高系统的伸缩性
6. **Redis主从复制速度：**官方提供了一个数据, lave在21秒即完成了对Amazon网站 10G key set的复制.

### 

