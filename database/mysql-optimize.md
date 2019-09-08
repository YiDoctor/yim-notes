# Mysql数据库优化 #

## 主要内容

- SQL及索引优化
  - 根据需求写出良好的SQL，并创建有效的索引，实现某一种需求可以多种写法，这时候我们就要选择一种效率最高的写法。这个时候就要了解sql优化
- 数据库表结构优化
  - 根据数据库的范式，设计表结构，表结构设计的好直接关系到写SQL语句。
- 系统配置优化
  - 大多数运行在Linux机器上，如tcp连接数的限制、打开文件数的限制、安全性的限制，因此我们要对这些配置进行相应的优化。
- 硬件配置优化
  - 选择适合数据库服务的cpu，更快的IO，更高的内存；cpu并不是越多越好，某些数据库版本有最大的限制，ＩＯ操作并不是减少阻塞。

## 一 SQL及索引优化

### 1.1 SQL优化建议

1. Max()函数优化

   ```sql
   select max(payment_date) from payment;
   
   --通过创建索引优化
   create index inx_paydate on payment(payment_date);
   ```

2. 函数Count()的优化

   ```sql
   -- 需求：在一条SQL中同时查处2006年和2007年电影的数量
   
   -- 正确方式
   select count(release_year='2006' or null) as '06films',count(release_year='2007' or null) as '07films' from film;
   
   -- Count（id）是不包含null的值，Count（*）是包含null的值
   ```

3. 添加适当的索引，一个表的索引数最好不要超过6个

4.  **where 及 order by 涉及的列，建立索引**。

5. 在正式环境中禁用select * 的方式

6. 尽可能的使用 varchar/nvarchar 代替 char/nchar ，因为首先变长字段存储空间小，可以节省存储空间，其次对于查询来说，在一个相对较小的字段内搜索效率显然要高些

7. 使用关联表查询时，充分利用连接条件！两个表存在多个连接条件时，可以同时写上去

8. 避免在sql中进行复杂的运算

9. **数据量大需要展示时，应避免在展示列使用子查询，可以把一个单独的select语句作为一个left jion的数据集进行查询**

10. 表设计时为每张表设置ID，避免使用varchar类型，整型类型效率最高

11. 针对需要分类的，创建数据库枚举表

12. **sql语句尽可能简单(一条sql只能在一个cpu运算,大语句拆小语句,减少锁时间,一条大sql可以堵死整个库);**

### 1.2 索引

#### 1.2.1 创建索引

在执行CREATE TABLE语句时可以创建索引，也可以单独用CREATE INDEX或ALTER TABLE来为表增加索引。

- ALTER TABLE
  - ALTER TABLE用来创建普通索引、UNIQUE索引或PRIMARY KEY索引。
  - ALTER TABLE table_name ADD INDEX index_name (column_list)
  - ALTER TABLE table_name ADD UNIQUE (column_list)
  - ALTER TABLE table_name ADD PRIMARY KEY (column_list)
- CREATE INDEX
  - CREATE INDEX可对表增加普通索引或UNIQUE索引。
  - CREATE INDEX index_name ON table_name (column_list)
  - CREATE UNIQUE INDEX index_name ON table_name (column_list)

#### 1.2.2 删除索引

```sql
DROP INDEX index_name ON talbe_name
ALTER TABLE table_name DROP INDEX index_name
ALTER TABLE table_name DROP PRIMARY KEY

-- 查看索引
show index from tblname;
show keys from tblname;
```

#### 1.2.3 建立索引

1. 在where从句，group by从句，order by从句，on从句中的列添加索引
2. 索引字段越小越好（因为数据库数据存储单位是以“页”为单位的，数据存储的越多，IO也会越大）
3. 离散度大的列放到联合索引的前面

**联合索引：**

1. 两个或更多个列上的索引被称作联合索引，又被称为是复合索引。
2.  利用索引中的附加列，您可以缩小搜索的范围，但使用一个具有两列的索引 不同于使用两个单独的索引。复合索引的结构与电话簿类似，人名由姓和名构成，电话簿首先按姓氏对进行排序，然后按名字对有相同姓氏的人进行排序。如果您知 道姓，电话簿将非常有用；如果您知道姓和名，电话簿则更为有用，但如果您只知道名不姓，电话簿将没有用处。

#### 1.2.4 索引优化SQL的方法

1、索引的维护及优化（重复及冗余索引）

​       增加索引会有利于查询效率，但会降低insert，update，delete的效率，但实际上往往不是这样的，过多的索引会不但会影响使用效率，同时会影响查询效率，这是由于数据库进行查询分析时，首先要选择使用哪一个索引进行查询，如果索引过多，分析过程就会越慢，这样同样的减少查询的效率，因此我们要知道如何增加，有时候要知道维护和删除不需要的索引

重复索引：重复索引是指相同的列以相同的顺序建立的同类型的索引

#### 1.2.5 使用索引优化注意

记面试中被问答，索引失效的情况。第一反应就是创建索引失败了，脑子里没有索引失效的概念，完全没有往这儿想。其实答案就在这儿。

1. 应避免在 where 子句中使用 or 连接!

2. 应避免在 where 中使用!=或<>操作符

3. in 和 not in 也要慎用!可以使用NOT EXISTS替代

4. 用>=替代>

5. 尽量避免在where子句中对字段进行表达式操作（避免在=号左边进行算术，函数或其他表达式运算）

6. 应尽量避免在 where 子句中对字段进行 null 值判断

7. 使用like 模糊查询不要以通配符开始

   ```sql
   select * from student where name like 'a%';  -- 正确
   ```

8. 应尽量避免在where子句中对字段进行函数操作（避免在=号左边进行算术，函数或其他表达式运算）

## 二 数据库表结构优化

### 2.1 MYSQL数据库设计规范

#### 2.1.1 命名规范

1. 库名、表名、字段名由字母、数字、下划线组成
2. 命名简介，多个单词下划线分割
3. 表表之间关联字段名称尽可能相同
4. 索引命令应该是字段名+index组成

#### 2.1.2 数据库表字段类型规范

- 用尽量少的存储空间来存数一个字段的数据;
  - 例如：能使用int就不要使用varchar、char,能用varchar(16)就不要使用varchar(256);
- IP地址最好使用int类型;
- 固定长度的类型最好使用char,例如：邮编;
- 能使用tinyint就不要使用smallint,int;
- 最好给每个字段一个默认值,最好不能为null;

#### 2.1.3 满足三范式规范

- 第一范式(1NF)：字段值具有原子性,不能再分(所有关系型数据库系统都满足第一范式);
- 第二范式(2NF)：一个表必须有主键,即每行数据都能被唯一的区分;
  - 必须先满足第一范式;
- 第三范式(3NF)：一个表中不能包涵其他相关表中非关键字段的信息,即数据表不能有沉余字段;
  - 必须先满足第二范式;

### 2.2  MYSQL数据库设计原则

#### 2.2.1 核心原则

- 不在数据库做运算;
- cpu计算务必移至业务层;
- 控制列数量(字段少而精,字段数建议在20以内);
- 平衡范式与冗余(效率优先；往往牺牲范式)
- 拒绝3B(拒绝大sql语句：big sql、拒绝大事务：big transaction、拒绝大批量：big batch);

#### 2.2.2 字段类原则

1. 用好数值类型(用合适的字段类型节约空间);
2. 字符转化为数字(能转化的最好转化,同样节约空间、提高查询性能);
3. 避免使用NULL字段(NULL字段很难查询优化、NULL字段的索引需要额外空间、NULL字段的复合索引无效);
4. 少用text类型(尽量使用varchar代替text字段); 

## 三 数据库系统配置优化

linux系统系优化

### 3.1 操作系统的优化

要修改的配置文件：/etc/sysctl.conf

1. 增加tcp支持的队列数

   ```sh
   net.ipv4.tcp_max_syn_backlog = 65535//
   ```

2. 减少断开连接时，资源回收(tcp有连接状态)

   ```sh
   net.ipv4.tcp_max_tw_buckets = 8000 //
   net.ipv4.tcp_tw_reuse = 1
   net.ipv4.tcp_tw_recycle = 1
   net.ipv4.tcp_fin_timeout = 10
   ```

### 3.2 打开文件数的限制

要修改的配置文件：/etc/security/limits.conf

```sh
# mysql用户
mysql Soft nofile 65535
mysql Hard nofile 65535

# 查看命令
ulimit –a
ulimit –H -s #查看最大软连接数
ulimit –H -n #查看最大硬连接数
```

### 3.3 Mysql配置文件优化

Mysql配置文件位置（没有需要手动创建）：etc/my.cnf

### 3.4 mysql语句执行顺序

```ruby
from-->on-->join-->where-->group by-->with(CUBE | ROLLUP)-->having-->select-->distinct-->order by-->limit
```

1. **FORM**: 对FROM的左边的表和右边的表计算笛卡尔积。产生虚表VT1
2.  **ON**: 对虚表VT1进行ON筛选，只有那些符合<join-condition>的行才会被记录在虚表VT2中。

3. **JOIN**： 如果指定了OUTER JOIN（比如left join、 right join），那么保留表中未匹配的行就会作为外部行添加到虚拟表VT2中，产生虚拟表VT3, rug from子句中包含两个以上的表的话，那么就会对上一个join连接产生的结果VT3和下一个表重复执行步骤1~3这三个步骤，一直到处理完所有的表为止。

4. **WHERE**： 对虚拟表VT3进行WHERE条件过滤。只有符合<where-condition>的记录才会被插入到虚拟表VT4中。

5. **GROUP BY**: 根据group by子句中的列，对VT4中的记录进行分组操作，产生VT5.

6. **CUBE | ROLLUP**: 对表VT5进行cube或者rollup操作，产生表VT6.

7. **HAVING**： 对虚拟表VT6应用having过滤，只有符合<having-condition>的记录才会被 插入到虚拟表VT7中。

### 3.5 存储引擎

#### 3.5.1 MyISAM存储引擎

不支持事务、也不支持外键，优势是访问速度快，对事务完整性没有要求或者以select，insert为主的应用基本上可以用这个引擎来创建表

支持3种不同的存储格式，分别是：静态表；动态表；压缩表

#### 3.5.2 InnoDB存储引擎

该存储引擎提供了具有提交、回滚和崩溃恢复能力的事务安全。但是对比MyISAM引擎，写的处理效率会差一些，并且会占用更多的磁盘空间以保留数据和索引。 

InnoDB存储引擎的特点：支持自动增长列，支持外键约束