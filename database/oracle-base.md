# 数据库基础（Oracle） #

## 主要内容

- 数据库简介
- 数据库操作语言
- 函数
- 数据库对象
- 事务和JDBC

## 一 了解关系型数据库 ##

### 1.1 数据库简介

**数据库**(Database)：是按照数据结构来组织、存储和管理数据的建立在计算机存储设备上的仓库。
简单来说是本身可视为电子化的文件柜——存储电子文件的处所，用户可以对文件中的数据进行新增、截取、更新、删除等操作。

**关系型数据库**

商业型代表:Oracle DB2 SqlServer
非商业型:Mysql

- MySQL：开源免费的数据库，小型的数据库，已经被 Oracle 收购了。
- Oracle：收费的大型数据库，Oracle 公司的产品。
- DB2 ：IBM 公司的数据库产品,收费的。常应用在银行系统中。
- SQL Server：MicroSoft 公司收费的中型的数据库。C#、.net 等语言常使用。
- SQLite: 嵌入式的小型数据库，应用在手机端，如：Android。

### 1.2 表的组成

**表的组成**：表头、字段、行、列、字段值

### 1.3 Oracle 用户和权限

Oracle中每一个用户对应着一个数据库；

#### 1.3.1 数据库角色（role）

- CONNECT 角色，主要应用在临时用户，特别是那些不需要建表的用户，通常只赋予
  他们 CONNECT role。CONNECT 是使用 Oracle 的简单权限，拥有 CONNECT 角色的用
  户，可以与服务器建立连接会话（session，客户端对服务器连接，称为会话）。
- RESOURCE 角色 ，更可靠和正式的数据库用户可以授予 RESOURCE role。RESOURCE
  提供给用户另外的权限以创建他们自己的表、序列、过程（procedure）、触发器
  （trigger）、索引（index）等。
- DBA 角色，DBA role 拥有所有的系统权限----包括无限制的空间限额和给其他用户授
  予各种权限的能力。用户 SYSTEM 拥有 DBA 角色。

一般情况下，一个普通的用户（如 SCOTT），拥有 CONNECT 和 RESOURCE 两个角色即可
进行常规的数据库开发工作。

#### 1.3.2 创建用户

```sql
CREATE USER 用户名 IDENTIFIED BY 口令; -- 创建新用户

GRANT connect, resource to 用户名; --给与用户连接和操作资源权限

ALTER USER 用户名 IDENTIFIED BY 新密码; --修改用户命令
```

## 1.4 Oracle 数据类型

- **CHAR(length)**  ：存储固定长度的字符串。参数 length 指定了长度，如果存储的字符串长
  度小于 length，用空格填充。默认长度是 1，最长不超过 2000 字节。
- **VARCHAR2(length)** ：存储可变长度的字符串。length 指定了该字符串的最大长度。默认长度
  是 1，最长不超过 4000 字符。
- **NUMBER(p，s)**：既可以存储浮点数，也可以存储整数，p 表示数字的最大位数（如果是
  小数包括整数部分和小数部分和小数点，p 默认是 38 为），s 是指小数位
  数。
- **DATE**  ：存储日期和时间，存储纪元、4 位年、月、日、时、分、秒，存储时间
- **TIMESTAMP**  ：不但存储日期的年月日，时分秒，以及秒后 6 位，同时包含时区。
- **CLOB**  ：存储大的文本，比如存储非结构化的 XML 文档
- **BLOB**  ：存储二进制对象，如图形、视频、声音等。

## 二 数据库操作语言 ##

### 2.1 数据定义语言（DDL） ###

#### 2.1.1 表增删

```sql
create table table_name();	--建表操作

drop table table_name;	--删表操作
```

#### 2.1.2 表字段增删


```sql
ALTER TABLE 表名 add 新增字段名 数据类型 default '默认值';  --新增列

ALTER TABLE 表名  DROP COLUMN  字段名 ; --删除列
```

#### 2.1.3 根据结果集创建表

```
CREATE TABLE 表名 AS SELECT 语句
```

#### 2.1.4 约束

约束是保证数据库数据的完整性和一致性的手段。约束的形式：表级约束 列级约束

- **主键约束：** primary key/一个表只能有一个主键约束，值唯一，非空

- **唯一约束：**unique/值不可重复
- **非空约束：** not null/非空约束，只能设置列级约束
- **检查约束：**  check(被检查列检查条件)/用于排除一个不需要数据的插入
- **外键约束：** foreign key references/

### 2.2 数据操纵语言（DML）

**insert语句**

```sql
--插入部分字段值
insert into table_name(col1,col2,...,colN) values(col_value1,col_value2,...,col_valueN);
-- 插入所有字段值
insert into table_name values(col_value1,col_value2,...,col_valueN);
```

**delete语句**

```sql
delete from table_name where col = ？
```

**update语句**

```sql
update table_name set col1 = col_value,...,colN = col_value where col = ?
```

**truncate语句**：如果用户确定要删除表中的所有数据记录，则使用truncate语句，使用该语句不产生回滚，因此操作不能被撤销

```sql
TRUNCATE TABLE 表名
```

### 2.3 事务控制语言（TCL）

- COMMIT： 事务提交
- ROLLBACK：事务回滚
- SAVEPOINT：保存点

### 2.4 数据控制语言（DCL）

- GRANT（授权）命令
- REVOKE（撤销）命令

### 2.5 数据查询语言（DQL）

**简单查询**：

```sql
select col1,col2,...,colN from table_name
```

#### 2.5.1 where子句

1. 关系运算符

   ```sql
   A=B --等于
   A>B >=  --大于 大于等于
   A<B <=  --小于 小于等于
   A!=B或A<>B --不等于
   ```

2. 逻辑运算符：AND、OR、NOT

3. 字符串连接操作符（||）

4. 模糊查询LIKE：通配符：“_”代表任意字符 “%”代表任意个数的任意字符

5. NULL操作

   ```sql
   IS NULL --空值判断
   IS NOT NULL  --非空判断
   ```

6. BETWEEN…AND…操作

7. IN操作：类似于枚举结果查询

   ```sql
   IN
   NOT IN
   ```

8. EXISTS查询操作：主要用于判断子查询的结果是否存在

   ```sql
   EXISTS()
   NOT EXISTS()
   ```

#### 2.5.2 order by子句

```SQL
order by  --默认对字段进行ASC升序
order by  --一定放在句尾
DESC -- 降序
```

#### 2.5.3 group by子句

- 对查询的结果集进行分组操作
- 默认对group by分组的列进行升序排列
- 查询字段中的组函数需要在group by后作为分组依据

#### 2.5.4 having子句

having子句通常和group by子句结合使用，对分组之后的数据集进行进一步的筛选

#### 2.5.5 子查询

**子查询类型**：

1. 单行子查询：不向外部返回结果，或者只返回一行结果。
2. 多行子查询：向外部返回零行、一行或者多行结果。

**子查询执行顺序**：

1. 一般子查询的的执行顺序，是先执行括号内的子查询，再执行括号外的查询
2. 关联子查询，执行顺，是先查询括号外的查询，再执行括号内的子查询

**注意事项**：

- 在使用比较运算符连接子查询时，要确保子查询返回的结果集只包含一个列的值

#### 2.5.6 分页查询

**使用关键字**：rownum

```sql
-- 分页查询的一般实现
select * from (select rownum row,column_name1,column_name2,...,column_name_N from table_name where rounum <= (pageNum)*pageSize) as tt where tt.row > (pageNum-1)*pageSize;
```

#### 2.5.7 连接查询

简单查询：笛卡尔乘积

- 内连接（inner join）: 只返回左右表之间关联内容
- 左连接（left join）: 返回左表所有内容
- 右连接（right join）: 返回右表所有内容
- 全连接（full join）: 返回左右表中所有的行

#### 2.5.8 集合操作

1. UNIOM：对多个select语句查询的结果集进行并集操作，并且消除掉重复的行
2. UNION ALL：对多个select语句查询的结果集进行并集操作，不消除掉重复的行
3. INTELSECT：对多个select语句查询的结果集进行交集操作
4. MINUS：MINUS 集合运算符可以找到两个结果集之间的差集，即返回第一个集合中存在，第二个集合中不存在的数据内容

#### 2.5.9 Oracle 中的伪列

在 Oracle 的表的使用过程中，实际表中还有一些附加的列，称为伪列。伪列就像表中的列一样，但是在表中并不存储。伪列只能查询，不能进行增删改操作。

- ROWID：ROWID 伪列返回的就是该行的物理地址。使用 ROWID 可以快速的定位表中的某一行。
- ROWNUM：ROWNUM 为结果集中每一行标识一个行号，第一行返回 1，第二行返回 2，以此类推。

#### 2.5.9 Oracle查询中的去重操作

**去重的方式**：

1. DISTINCT：方法查询字段前面，对字段结果去重操作
2. 使用group by分组进行去重操作

#### 2.5.10 select语句的执行顺序

1. from子句组装来自不同数据源的数据；
2. where子句基于指定的条件对记录行进行筛选；
3. group by子句将数据划分为多个分组；
4. 使用聚集函数进行计算；
5. 使用having子句筛选分组；
6. 计算所有的表达式；
7. select 的字段；
8. 使用order by对结果集进行排序。

## 三 Oracle函数

Oracle SQL提供了用于执行特定操作的专用函数。这些函数大大增强了 SQL 语言的功能。函数可以接受零个或者多个输入参数，并返回一个输出结果。Oracle 数据库中主要使用两种类型的函数：

1.  单行函数：对每一个函数应用在表的记录中时，只能输入一行结果，返回一个结果，比如：MOD(x,y)返回 x 除以 y 的余数（x 和 y 可以是两个整数，也可以是表中的整数列）。常用的单行函数有：
    - 字符函数：对字符串操作。
    - 数字函数：对数字进行计算，返回一个数字。
    - 转换函数：可以将一种数据类型转换为另外一种数据类型。
    - 日期函数：对日期和时间进行处理。
2.  聚合函数：聚合函数同时可以对多行数据进行操作，并返回一个结果。比如 SUM(x)返回结果集中 x 列的总合。

### 3.1 字符函数

- ASCII(x) ：返回字符 x 的 ASCII 码。
- CONCAT(x,y) ：连接字符串 x 和 y。
- INSTR(x, str [,start] [,n)：在 x 中查找 str，可以指定从 start 开始，也可以指定从第 n 次开始。
- LENGTH(x)：  返回 x 的长度。
- LOWER(x) ： x 转换为小写。
- UPPER(x)：  x 转换为大写。
- LTRIM(x[,trim_str])：  把 x 的左边截去 trim_str 字符串，缺省截去空格。
- RTRIM(x[,trim_str])：  把 x 的右边截去 trim_str 字符串，缺省截去空格。
- TRIM([trim_str FROM] x)：  把 x 的两边截去 trim_str 字符串，缺省截去空格。
- REPLACE(x,old,new)  在 x 中查找 old，并替换为 new。
- SUBSTR(x,start[,length])：返回 x 的字串，从 staart 处开始，截取 length 个字符，
  缺省 length，默认到结尾。

### 3.2 数字函数

| 函数 | 说明 | 示例 |
| ---- | ---- | ---- |
|ABS(x) | x 绝对值 | ABS(-3)=3|
|ACOS(x) | x 的反余弦 | ACOS(1)=0|
|COS(x)  |余弦  |COS(1)=1.57079633|
|CEIL(x) | 大于或等于 x 的最小值 | CEIL(5.4)=6|
|FLOOR(x)|  小于或等于 x 的最大值  |FLOOR(5.8)=5|
|LOG(x,y)|  x 为底 y 的对数  |LOG(2,4)=2|
|MOD(x,y) | x 除以 y 的余数  |MOD(8,3)=2|
|POWER(x,y) | x 的 y 次幂  |POWER(2,3)=8|
|ROUND(x[,y])|  x 在第 y 位四舍五入 | ROUND(3.456,2)=3.46|
|SQRT(x) | x 的平方根 | SQRT(4)=2|
|TRUNC(x[,y]) | x 在第 y 位截断 | TRUNC(3.456,2)=3.45	|

### 3.3 日期函数

1. ADD_MONTHS(d,n)，在某一个日期 d 上，加上指定的月数 n，返回计算后的新日期。d 表示日期，n 表示要加的月数。

2. LAST_DAY(d)，返回指定日期当月的最后一天。

3.  ROUND(d[,fmt])，返回一个以 fmt 为格式的四舍五入日期值，d 是日期，fmt 是格式
    模型。默认 fmt 为 DDD，即月中的某一天。

    - 如果 fmt 为“YEAR”则舍入到某年的 1 月 1 日，即前半年舍去，后半年作为下一年。
    - 如果 fmt 为“MONTH”则舍入到某月的 1 日，即前月舍去，后半月作为下一月。
    - 默认为“DDD”，即月中的某一天，最靠近的天，前半天舍去，后半天作为第二天。
    - 如果 fmt 为“DAY”则舍入到最近的周的周日，即上半周舍去，下半周作为下一周周日。

    与 ROUND 对应的函数时 TRUNC(d[,fmt])对日期的操作，TRUNC 与 ROUND 非常相似，只
    是不对日期进行舍入，直接截取到对应格式的第一天。

4.  EXTRACT(fmt FROM d)，提取日期中的特定部分。

### 3.4 转换函数

1. TO_CHAR(d|n[,fmt])：把日期和数字转换为制定格式的字符串
2. TO_DATE(x [,fmt])：把一个字符串以 fmt 格式转换为一个日期类型
3. TO_NUMBER(x[,fmt])：把一个字符串以 fmt 格式转换为一个数字

### 3.5 其他单行函数

1. NVL(x,value)：如果 x 为空，返回 value，否则返回 x。
2. NVL2(x,value1,value2)：如果 x 非空，返回 value1，否则返回 value2。

### 3.6 聚合函数

聚合函数同时对一组数据进行操作，返回一行结果，比如计算一组数据的总和，平均值等。

| 名称 | 作用 | 语法 |
| ---- | ---- | ---- |
|   AVG   |  平均值     |   AVG(表达式)   |
|SUM  |求和 | SUM(表达式)|
|MIN、MAX  |最小值、最大值 | MIN(表达式)、MAX(表达式)|
|COUNT  |数据统计  |COUNT(表达式)|

## 四 数据库对象

数据库对象是数据库的组成部分，常常用 CREATE 命令进行创建，可以使用 ALTER 命令修改，用 DROP 执行删除操作。前面已经接触过的数据库对象有表、用户等。

- 同义词：就是给数据库对象一个别名。
- 序列：Oracle 中实现增长的对象。
- 视图：预定义的查询，作为表一样的查询使用，是一张虚拟表。
- 索引：对数据库表中的某些列进行排序，便于提高查询效率。

### 4.1 同义词

同义词（Synonym）是数据库对象的一个别名，Oracle 可以为表、视图、序列、过程、函数、程序包等指定一个别名。同义词有两种类型：

- 私有同义词：拥有 CREATE SYNONYM 权限的用户（包括非管理员用户）即可创建私
  有同义词，创建的私有同义词只能由当前用户使用。
- 公有同义词：系统管理员可以创建公有同义词，公有同义词可以被所有用户访问。

语法：

```sql
-- 创建同义词 中括号内可不写
CREATE [OR REPLACE] [PUBLIC] SYSNONYM [schema.]synonym_name FOR [schema.]object_name;
--删除同义词
DROP [PUBLIC] SYNONYM [schema.]sysnonym_name;
```

### 4.2 序列

序列(Sequence)是用来生成连续的整数数据的对象。序列常常用来作为主键中增长列，序列中的可以升序生成，也可以降序生成。创建序列的语法是：

**语法**：

```sql
-- 创建序列	
CREATE SEQUENCE sequence_name
[START WITH num]
[INCREMENT BY increment]
[MAXVALUE num|NOMAXVALUE]
[MINVALUE num|NOMINVALUE]
[CYCLE|NOCYCLE]
[CACHE num|NOCACHE]
```

1. START WITH：从某一个整数开始，升序默认值是 1，降序默认值是-1。
2. INCREMENT BY：增长数。如果是正数则升序生成，如果是负数则降序生成。升序默认值是 1，降序默认值是-1。
3. MAXVALUE：指最大值。
4. NOMAXVALUE：这是最大值的默认选项，升序的最大值是：10^27 ，降序默认值是-1。
5. MINVALUE：指最小值。
6. NOMINVALUE：这是默认值选项，升序默认值是 1，降序默认值是-10^26 。
7. CYCLE：表示如果升序达到最大值后，从最小值重新开始；如果是降序序列，达到最小值后，从最大值重新开始。
8. NOCYCLE：表示不重新开始，序列升序达到最大值、降序达到最小值后就报错。默认 NOCYCLE。
9. CACHE：使用 CACHE 选项时，该序列会根据序列规则预生成一组序列号。

示例如下;

```sql
-- 创建一个序列 从最小值1开始，升序每次+1，默认最大值，预创建30
SQL> CREATE SEQUENCE MYSEQ
2 MINVALUE 1
3 START WITH 1
4 NOMAXVALUE
5 INCREMENT BY 1
6 NOCYCLE
7 CACHE 30
8 /
-- 删除序列
DROP SEQUENCE MYSEQ;
```

### 4.3 视图

视图（View）实际上是一张或者多张表上的预定义查询，这些表称为基表。从视图中查询信息与从表中查询信息的方法完全相同。只需要简单的 SELECT…FROM 即可。

视图具有以下优点：
1. 可以限制用户只能通过视图检索数据。这样就可以对最终用户屏蔽建表时底层的基
表。
2. 可以将复杂的查询保存为视图。可以对最终用户屏蔽一定的复杂性。
3. 限制某个视图只能访问基表中的部分列或者部分行的特定数据。这样可以实现一定
的安全性。
4. 从多张基表中按一定的业务逻辑抽出用户关心的部分，形成一张虚拟表。

**语法**：

```sql
CREATE [OR REPLACE] [{FORCE|NOFORCE}] VIEW view_name
AS
SELECT查询
[WITH READ ONLY CONSTRAINT]

--1. OR REPLACE：如果视图已经存在，则替换旧视图。
--2. FORCE：即使基表不存在，也可以创建该视图，但是该视图不能正常使用，当基表创建成功后，视图才能正常使用。
--3. NOFORCE：如果基表不存在，无法创建视图，该项是默认选项。
--4. WITH READ ONLY：默认可以通过视图对基表执行增删改操作，但是有很多在基表上的限制（比如：基表中某列不能为空，但是该列没有出现在视图中，则不能通过视图执行 insert 操作），WITH READ ONLY 说明视图是只读视图，不能通过该视图进行增删改操作。现实开发中，基本上不通过视图对表中的数据进行增删改操作。

DROP VIEW 视图名称;
```

### 4.4 索引

```
CREATE [UNIQUE] INDEX index_name ON table_name(column_name[,column_name…])
```

1. UNIQUE:指定索引列上的值必须是唯一的。称为唯一索引。
2. index_name：指定索引名。
3. tabl_name：指定要为哪个表创建索引。
4. column_name：指定要对哪个列创建索引。我们也可以对多列创建索引；这种索引称为组合索引。

```sql
alter table table_name drop index index_name --删除索引

select * from user_indexes where table_name=upper('表名');  --查找已创建的索引
```

### 4.5 表空间

表空间是一个逻辑概念,有文件组成。常见的表空间如下：

- 系统表空间：存放系统数据，系统表空间在数据库创建时创建。表空间名称为
  SYSTEM。存放数据字典和视图以及数据库结构等重要系统数据信息，在运行时如
  果 SYSTEM 空间不足，对数据库影响会比较大，虽然在系统运行过程中可以通过命
  令扩充空间，但还是会影响数据库的性能，因此有必要在创建数据库时适当的把数
  据文件设置大一些。
-  TMEP 表空间：临时表空间，安装数据库时创建，可以在运行时通过命令增大临时
  表空间。临时表空间的重要作用是数据排序。比如当用户执行了诸如 Order by 等
  命令后，服务器需要对所选取数据进行排序，如果数据很大，内存的排序区可能装
  不下太大数据，就需要把一些中间的排序结果写在硬盘的临时表空间中。
- 用户表自定义空间：用户可以通过 CREATE TABLESPACE 命令创建表空间。

## 五 数据库其他知识点

### 5.1 事务

#### 5.1.1 事务的特性

- 原子性（Atomicity） – 原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生， 要么都不发生。 
- 一致性（Consistency） – 事务必须使数据库从一个一致性状态变换到另外一个一致性状态。 
- 隔离性（Isolation） – 事务的隔离性是多个用户并发访问数据库时，数据库为每一个用户开启的 事务，不能被其他事务的操作数据所干扰，多个并发事务之间要相互隔离 
- 持久性（Durability） – 持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的 ，接下来即使数据库发生故障也不应该对其有任何影响。

#### 5.1.2 事务的隔离级别

多个线程开启各自事务操作数据库中数据时，数据库系统要负责隔 离操作，以保证各个线程在获取数据时的准确性

如果不考虑隔离性，可能会引发如下问题：

- 脏读：指一个事务读取了另外一个事务未提交的数据。 
- 不可重复读：在一个事务内读取表中的某一行数据，多次读取结果不同。 
- 虚读(幻读)：是指在一个事务内读取到了别的事务插入的数据，导致前后读 取不一致。

数据库共定义了四种隔离级别： 

- Serializable：可避免脏读、不可重复读、虚读情况的发生。（串行化）
- Repeatable read（默认值）：可避免脏读、不可重复读情况的发生。（可 重复读） 
- Read committed：可避免脏读情况发生（读已提交）。 
- Read uncommitted：最低级别，以上情况均无法保证。(读未提交)

### 5.2 数据库三范式

好的数据库设计对数据的存储性能和后期的程序开发，都会产生重要的影响。建立科学的，规范的数据库就需要满足一些规则来优化数据的设计和存储，这些规则就称为范式。

#### 5.2.1 1NF

数据库表的每一列都是不可分割的原子数据项，不能是集合、数组等非原子数据项。即表中的某个列有多个值时，必须拆分为不同的列。简而言之，**第一范式每一列不可再拆分，称为原子性**。

#### 5.2.2 2NF

在满足第一范式的前提下，表中的每一个字段都完全依赖于主键。所谓完全依赖是指不能存在仅依赖主键一部分的列。简而言之，第二范式就是在第一范式的基础上所有列完全依赖于主键列。当存在一个复合主键包含多个主键列的时候，才会发生不符合第二范式的情况。比如有一个主键有两个列，不能存在这样的属性，它只依赖于其中一个列，这就是不符合第二范式。

**第二范式的特点**：

1. 一张表只描述一件事情。
2. 表中的每一列都完全依赖于主键

#### 5.2. 3NF

在满足第二范式的前提下，表中的每一列都直接依赖于主键，而不是通过其它的列来间接依赖于主键。

简而言之，第三范式就是所有列不依赖于其它非主键列，**也就是在满足 2NF 的基础上，任何非主列不得传递**
**依赖于主键**。所谓传递依赖，指的是如果存在"A → B → C"的决定关系，则 C 传递依赖于 A。因此，满足第三范
式的数据库表应该不存在如下依赖关系：主键列 → 非主键列 x → 非主键列 y

## 六  Oracle 数据的导入导出

ORACLE 数据库的逻辑备份分为四种模式：表空间备份(tablespace)、表备份(table)、用户备份(user)和完全备份(full)。Oracle 的逻辑备份是使用 IMP&EXP 命令进行数据导入导出的操作。使用 EXP 命令导出或者使用 IMP 命令导入时，需要 Create Session 系统权限，但是如果要导出其他的表，必须拥有权限：EXP_FULL_DATABASE。

调用导入导出命令时，首先要估计所需的空间。EXP 命令导出的文件是二进制文件
（*.dmp）只能由对应的 IMP 命令进行读取恢复。导入导出的用途是：

- 备份与恢复
- Oracle 平台更换：可以在相同版本之间进行备份与恢复，Oracle 较低版本的 export
  数据文件可以 import 到高版本的 Oracle 数据库中，但是 Oracle 的版本只能是相邻
  的，不能垮版本

### 6.1 导出

#### 6.1.1 交互环境下

导出示例:

```sql
exp scott/tiger@my_orcl ①
Export: Release 10.2.0.3.0 - Production on 星期一 10月 19 17:04:14 2009
Copyright (c) 1982, 2005, Oracle. All rights reserved.
连接到: Oracle Database 10g Enterprise Edition Release 10.2.0.3.0 - Production
With the Partitioning, OLAP and Data Mining options
输入数组提取缓冲区大小: 4096 > ②
导出文件: EXPDAT.DMP > scott.dmp ③
(2)U(用户), 或 (3)T(表): (2)U > 2 ④
导出权限 (yes/no): yes > yes ⑤
导出表数据 (yes/no): yes > yes ⑥
压缩区 (yes/no): yes > no ⑦
已导出 ZHS16GBK 字符集和 AL16UTF16 NCHAR 字符集
. 正在导出 pre-schema 过程对象和操作
```

#### 6.1.2 非交互环境

```
exp scott/tiger file=employee.dmp tables=(emp,dept)
```

### 6.2 导出

命令示例：

```sql
imp system/manager file=employee.dmp fromuser=scott touser=employee
commit=y
```

### 6.3 常见问题

- 数据库对象已经存在
  - 一般情况, 导入数据前应该彻底删除目标数据下的表，序列，函数/过程,触发器等。
  - 数据库对象已经存在, 按缺省的 imp 参数，则会导入失败。
  - 如果用了参数 ignore=y，会把 exp 文件内的数据内容导入。
  - 如果表有唯一关键字的约束条件，不合条件将不被导入。
  - 如果表没有唯一关键字的约束条件，将引起记录重复。
- 数据库对象有主外键约束
  - 不符合主外键约束时，数据会导入失败。
  - 解决办法: 先导入主表，再导入依存表。
    disable 目标导入对象的主外键约束，导入数据后，再 enable 它们。
- 权限不够
  - 如果要把 A 用户的数据导入 B 用户下， A 用户需要有 imp_full_database 权限。
- 导入大表( 大于 80M ) 时，存储分配失败
  - 默认的 EXP 时，compress = Y，也就是把所有的数据压缩在一个数据块上。
  - 导入时，如果不存在连续一个大数据块，则会导入失败。
  - 导出 80M 以上的大表时，记得 compress= N，则不会引起这种错误。
- Imp 和 Exp 使用的字符集不同
  - 如果字符集不同，导入会失败，可以改变 unix 环境变量或者 NT 注册表里 NLS_LANG 相
    关信息。
- Imp 和 Exp 版本不能往上兼容
  - Imp 可以成功导入低版本 Exp 生成的文件, 不能导入高版本 Exp 生成的文件根据情况我
    们可以用。

## 七 JDBC(Java Data Base Connectivity)

### 7.1 相关API

- DriverManager：用于加载驱动，并创建与数据库的链接
- Connection：创建数据库连接
- Statement：用于向数据库发送sql
- ResultSet：sql执行结果集

### 7.2 释放资源的重要性

- 需要释放资源对象有：Connection、Statement、ResultSet
- Connection
  - 稀缺资源，用完马上释放
  - Connection不能及时、正确的关闭，极易导致系统宕机
  - 尽量晚创建，尽量早的释放
- finally语句中编写释放资源代码，为了确保代码执行

### 7.3 数据库连接步骤

```java
//1 加载驱动
Class.forName("数据库驱动"); 
//2 创建连接
DriverManager.getConnection("","","");
//3 创建语句
conn.Preparestament(sql);
//4 执行语句
ps.executeQuery();
//5 处理结果
while(rs.next){}
//6 释放资源
finally{
	conn.close();
	rs.close();
}
```

### 7.4 Statement 和 PreparedStatement ？

1.  PreparedStatement 接口继承 Statement， PreparedStatement 实例包含已编译的 SQL 语句，所以其执行速度要快于 Statement 对象。
2. 在 JDBC 应用中,在任何时候都不要使用 Statement，原因如下：
   1. Statement 需要不断地拼接sql,PreparedStatement 不需要
   2. DB 有缓存机制，预编译的SQL再次调用无需编译
   3. PreparedStatement 有效的防止了SQL注入
