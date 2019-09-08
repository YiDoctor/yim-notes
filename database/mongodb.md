# MongoDB #

# 主要内容：

- MongoDB概述
- Linux及docker下安装
- docker基本使用
- SpringBoot项目整合

## 一   MongoDB安装

### 1.1 Linux系统下安装

1. 使用命令下或下载好上传到Linux上

   ```shell
   wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-4.0.4.tgz
   ```

2. tar 命令解压

   ```sh
   tar -zvxf mongodb-linux-x86_64-4.0.4.tgz
   ```

3. mongdb目录下创建data目录和logs文件

4. 配置环境变量

   ```sh
   PATH=$PATH:$HOME/bin:/opt/mongodb/bin
   ```

5. 启动

   ```sh
   mongod --dbpath=/opt/mongodb/data --fork --logpath=/opt/mongodb/logs
   
   mongo #进入MongoDB数据库
   ```

6. 允许外部ip访问

   ```sh
   vim  /opt/mongodb/bin/mongodb.conf	#创建配置文件
   ```

   配置文件内容：yml格式

   ```yml
   systemLog:
     destination: file
     logAppend: true
     path: /opt/mongodb/logs #日志路径 
   storage:
     dbPath: /opt/mongodb/data #数据库路径 
     journal:
       enabled: true
   
   processManagement:
     fork: true 
   #  pidFilePath: /var/run/mongodb/mongod.pid  
   #  timeZoneInfo: /usr/share/zoneinfo
   
   net:
     port: 27017
     bindIp: 0.0.0.0  #配置外部可访问
   #security:
   #  authorization: enabled
   ```

   配置文件启动：

   ```sh
   mongod -f mongodb.conf	#配置文件启动
   ```

### 1.2 docker下安装

```sh
#拉取镜像
docker pull mongo:4.0.3
#创建容器
docker create --name mongodb -p 27017:27017 -v /data/mongodb:/data/db mongo:4.0.3
#启动容器
docker start mongodb
#进入容器
docker exec -it mongodb /bin/bash
#使用MongoDB客户端进行操作
mongo
> show dbs  #查询所有的数据库
```

## 二   MongoDB知识点扫盲

### 2.1 概述

MongoDB是一个基于分布式文件存储的数据库。由C++语言编写。旨在为WEB应用提供可扩展的高性能数据存储
解决方案。
MongoDB是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库
的，它支持的数据结构非常松散，是类似json的bson格式，因此可以存储比较复杂的数据类型。

MongoDB最大的特点是它支持的查询语言非常强大，其语法有点类似于面向对象的查询语言，几乎可以实现类似
关系数据库单表查询的绝大部分功能，而且还支持对数据建立索引。

官网：https://www.mongodb.com

### 2.2 特点

1. 面向集合（Collenction-Orented）

意思是数据被分组存储在数据集中，被称为一个集合（Collenction)。每个集合在数据库中都有一个唯一的标识名，并且可以包含无限数目的文档。集合的概念类似关系型数据库（RDBMS）里的表（table），不同的是它不需要定义任何模式（schema)

2. 模式自由（schema-free)

3. 文档型: 

存储的数据是键-值对的集合,键是字符串,值可以是数据类型集合里的任意类型,包括数组和文档. 我们把这个数据格式称作 “BSON” 即 “Binary Serialized dOcument Notation.”

4. 支持复制和故障恢复
5. 使用高效的二进制数据存储，包括大型对象（如视频等）
6. 自动处理碎片，以支持云计算层次的扩展性

### 2.3 体系结构

- 数据逻辑结构

  MongoDB 的逻辑结构是一种层次结构。主要由：文档(document)、集合(collection)、数据库(database)这三部分组成的。逻辑结构是面向用户的，用户使用 MongoDB 开发应用程序使用的就是逻辑结构。
     1： MongoDB 的文档（document），相当于关系数据库中的一行记录
     2：多个文档组成一个集合（collection），相当于关系数据库的表
     3：多个集合（collection），逻辑上组织在一起，就是数据库（database）
     4：一个 MongoDB 实例支持多个数据库（database）

- 数据存储结构

  MongoDB 的默认数据目录是/data/db，它负责存储所有的 MongoDB 的数据文件。在 MongoDB内部，每个数据库都包含一个.ns 文件和一些数据文件，而且这些数据文件会随

![](https://i.imgur.com/d9guB4A.png)

### 2.4 常用命令

```sh
show dbs #显示数据库列表 
show collections #显示当前数据库中的集合（类似关系数据库中的表） 
show users #显示用户
use <db name> #切换当前数据库，这和MS-SQL里面的意思一样 
db.help() #显示数据库操作命令，里面有很多的命令 
db.foo.help() #显示集合操作命令，同样有很多的命令，foo指的是当前数据库下，一个叫foo的集合，并非真正意义上的命令 
db.repairDatabase(); # 修复当前数据库   
# 查看当前使用的数据库  
    db.getName()
     db
db.stats() #显示当前db状态
db.version() #当前db版本  
db.g3 etMongo() #查看当前db的链接机器地址  
```

### 2.5 基本操作

1. 新增数据

   ```sh
   db.users.insert({ 
       “_id”:ObjectId("52c3c518498a9646a48133a2"),
       “name”:“likang”, 
       “email”:“likang@qq.com”
   }); 
   db.users.save({ 
       “_id”:ObjectId("52c3c518498a9646a48133a2"),
       “name”:“likang2”, 
       “email”:“likang2@qq.com”
   }); 
   
   #insert 当_id存在时报错
   #save 当_id存在时覆盖更新
   ```

2. 删除操作

   ```sh
   #删除全部
   db.user.remove(); 
   #删除指定记录
   db.user.remove({“name”:“likang”}); 
   
   #删除user集合
   db.user.drop();
   ```

3. 更新数据

   ```sh
   > db.user.find()
   { "_id" : ObjectId("5c08c0024b318926e0c1f6dc"), "id" : 1, "username" : "zhangsan",
   "age" : 20 }
   { "_id" : ObjectId("5c08c0134b318926e0c1f6dd"), "id" : 2, "username" : "lisi",
   "age" : 25 }
   > db.user.update({id:1},{$set:{age:22}}) #更新数据
   WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
   
   #更新不存在的字段，会新增字段
   > db.user.update({id:2},{$set:{sex:1}}) #更新数据
   > db.user.find()
   { "_id" : ObjectId("5c08c0024b318926e0c1f6dc"), "age" : 25 }
   { "_id" : ObjectId("5c08c0134b318926e0c1f6dd"), "id" : 2, "username" : "lisi",
   "age" : 25, "sex" : 1 }
   
   #如果设置第一个参数为true，就是新增数据
   > db.user.update({id:3},{$set:{sex:1}},true)
   ```

4. 查询数据

   ```sh
   #插入测试数据
   db.user.insert({id:1,username:'zhangsan',age:20})
   db.user.insert({id:2,username:'lisi',age:21})
   db.user.insert({id:3,username:'wangwu',age:22})
   db.user.insert({id:4,username:'zhaoliu',age:22})
   db.user.find()  #查询全部数据
   db.user.find({},{id:1,username:1})  #只查询id与username字段
   db.user.find().count()  #查询数据条数
   db.user.find({id:1}) #查询id为1的数据
   db.user.find({age:{$lte:21}}) #查询小于等于21的数据
   db.user.find({age:{$lte:21}, id:{$gte:2}}) #and查询，age小于等于21并且id大于等于2
   db.user.find({$or:[{id:1},{id:2}]}) #查询id=1 or id=2
   #分页查询：Skip()跳过几条，limit()查询条数
   db.user.find().limit(2).skip(1)  #跳过1条数据，查询2条数据
   db.user.find().sort({id:-1}) #按照age倒序排序，-1为倒序，1为正序
   ```

### 2.6 索引

索引通常能够极大的提高查询的效率，如果没有索引，MongoDB在读取数据时必须扫描集合中的每个文件并选取
那些符合查询条件的记录。

这种扫描全集合的查询效率是非常低的，特别在处理大量的数据时，查询可以要花费几十秒甚至几分钟，这对网站
的性能是非常致命的。

索引是特殊的数据结构，索引存储在一个易于遍历读取的数据集合中，索引是对数据库表中一列或多列的值进行排
序的一种结构

```sh
#查看索引
> db.user.getIndexes()
[
	{
    "v" : 2,
    "key" : {
    	"_id" : 1
    },
    "name" : "_id_",
    "ns" : "testdb.user"
	}
]
#说明：1表示升序创建索引，-1表示降序创建索引。

#创建索引
> db.user.createIndex({'age':1})

#删除索引
db.user.dropIndex("age_1")
#或者，删除除了_id之外的索引
db.user.dropIndexes()

#创建联合索引
db.user.createIndex({'age':1, 'id':-1})

#查看索引大小，单位：字节
db.user.totalIndexSize()
```

### 2.7 执行计划

MongoDB 查询分析可以确保我们建议的索引是否有效，是查询语句性能分析的重要工具。

```sh
#插入1000条数据
for(var i=1;i<1000;i++)db.user.insert({id:100+i,username:'name_'+i,age:10+i})

#查看执行计划
> db.user.find({age:{$gt:100},id:{$lt:200}}).explain()

{
        "queryPlanner" : {
                "plannerVersion" : 1,
                "namespace" : "test.user",
                "indexFilterSet" : false,
                "parsedQuery" : {
                        "$and" : [
                                {
                                        "id" : {
                                                "$lt" : 200
                                        }
                                },
                                {
                                        "age" : {
                                                "$gt" : 100
                                        }
                                }
                        ]
                },
                "winningPlan" : {#最佳执行计划
                        "stage" : "COLLSCAN",#查询方式，常见的有COLLSCAN/全表扫描、IXSCAN/索引扫描、FETCH/根据索引去检索文档、SHARD_MERGE/合并分片结果、IDHACK/针对_id进行查询
                        "filter" : {
                                "$and" : [
                                        {
                                                "id" : {
                                                        "$lt" : 200
                                                }
                                        },
                                        {
                                                "age" : {
                                                        "$gt" : 100
                                                }
                                        }
                                ]
                        },
                        "direction" : "forward"
                },
                "rejectedPlans" : [ ]
        },
        "serverInfo" : {
                "host" : "fab0162962d2",
                "port" : 27017,
                "version" : "4.0.3",
                "gitVersion" : "7ea530946fa7880364d88c8d8b6026bbc9ffa48c"
        },
        "ok" : 1
}

#测试没有使用索引
> db.user.find({username:'zhangsan'}).explain()
```

### 2.8 UI客户端工具

studio3t是mongodb优秀的客户端工具。官方地址在[https://studio3t.com/

## 三 JavaAPI操作MongoDB

### 3.1 创建maven项目导入依赖

```xml
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>3.9.1</version>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.4</version>
</dependency>
```

### 3.2 编写MongoDBDemo

该demo中演示了，如何连接到MongoDB，如何选择数据库、表，进行查询的操作。

```java
public class MongoDBDemo {

    public static void main(String[] args) {
        // 建立连接
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.231.160:27017");
        // 选择数据库
        MongoDatabase testdb = mongoClient.getDatabase("testdb");
        // 获取collection表
        MongoCollection<Document> user = testdb.getCollection("user");

        // 查询遍历
        FindIterable<Document> documents = user.find();
        documents.forEach((Consumer<? super Document>)
                document -> {
                    System.out.println(document.toJson());
                });
        // 关闭连接
        mongoClient.close();
    }
}
```

### 3.3 编写TestMongoDB实现CURD操作

```java
public class TestMongoDB {

    MongoCollection<Document> mongoCollection;

    @Before
    public void init(){
        //建立连接
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.231.160:27017");
        //选择数据库
        MongoDatabase testdb = mongoClient.getDatabase("testdb");
        //获取表连接
        mongoCollection = testdb.getCollection("user");
    }
    // 查询age<=22并且id>=1的两条用户信息，并且按照id倒序排序，只返回id，age字段，不返回_id字段
    @Test
    public void queryTest(){
        mongoCollection.find(
            and(
                    lte("age",22),
                    gte("id",1)
            )
        ).limit(5).sort(descending("id")).projection(fields(
                include("id","age"),excludeId()
        )).forEach((Consumer<? super Document>) document ->{
            System.out.println(document.toJson());
        });
    }
    //插入一条数据，并查看
    @Test
    public void insertTest(){
        Document document = new Document();
        document.put("id",10);
        document.put("name","赵一");
        document.put("age",28);
        mongoCollection.insertOne(document);
        System.out.println("插入成功");

        mongoCollection.find(eq("id",10)).forEach((Consumer<? super Document>) doc->{
            System.out.println(doc.toJson());
        });
    }

    //更新一条数据，并查看
    @Test
    public void updateTest(){
        UpdateResult updateResult = mongoCollection.updateOne(
                eq("id", 10), Updates.set("age", 27));
        System.out.println(updateResult);
        mongoCollection.find(eq("id",10)).forEach((Consumer<? super Document>) doc->{
            System.out.println(doc.toJson());
        });
    }
    //删除一条数据，返回删除结果
    @Test
    public void deleteTest(){
        DeleteResult deleteResult = mongoCollection.deleteOne(eq("id", 1));
        System.out.println(deleteResult);
    }
}
```

### 3.4 面向对象操作

前面对MongoDB的操作都是基于Document对象操作，操作略显繁琐，下面我们通过面向对象的方式进行操作。

创建Person、Address对象：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String city;
    private String zip;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private ObjectId id;
    private String name;
    private int age;
    private Address address;
}
```

测试操作：

```java
public class TestPerson {

    MongoCollection<Person> personCollection;

    @Before
    public void init(){
        //定义对象的解码注册器
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true).build()
                ));
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.231.160");
        // 选择数据库 并且 注册解码器
        MongoDatabase testdb = mongoClient.getDatabase("testdb").withCodecRegistry(codecRegistry);
        //选择操作表
        personCollection = testdb.getCollection("person", Person.class);
    }

    @Test
    public void insertTest(){
        Person person = new Person(ObjectId.get(), "钱二", 24,
                new Address("闵行区","shanghai","233611"));
        personCollection.insertOne(person);
        System.out.println("单条写入数据成功");

        List<Person> personList = Arrays.asList(
            new Person(ObjectId.get(), "张三", 20, new Address("人民路", "上海市", "666666")),
            new Person(ObjectId.get(), "李四", 21, new Address("北京西路", "上海市", "666666")),
            new Person(ObjectId.get(), "王五", 22, new Address("南京东路", "上海市", "666666")),
            new Person(ObjectId.get(), "赵六", 23, new Address("陕西南路", "上海市", "666666")),
            new Person(ObjectId.get(), "孙七", 24, new Address("南京西路", "上海市", "666666"))
        );
        personCollection.insertMany(personList);
        System.out.println("多条写入数据成功");
    }

    @Test
    public void updateTest(){
        UpdateResult updateResult = personCollection.updateMany(Filters.eq("name", "张三"), Updates.set("age", 23));
        System.out.println(updateResult);
    }

    @Test
    public void deleteTest(){
        DeleteResult deleteResult = personCollection.deleteOne(Filters.eq("name", "张三"));
        System.out.println(deleteResult);
    }
    @Test
    public void queryTest(){
        personCollection.find().forEach((Consumer<? super Person>) person ->{
            System.out.println(person);
        });
    }
}
```

## 四 SpringBoot整合MongoDB

spring-data对MongoDB做了支持，使用spring-data-mongodb可以简化MongoDB的操作。
地址：https://spring.io/projects/spring-data-mongodb

### 4.1 引入依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.0.RELEASE</version>
</parent>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 4.2 配置文件增加

```properties
# Spring boot application
spring.application.name = mongodb-demo

spring.data.mongodb.uri = mongodb://192.168.231.160:27017/testdb

spring.data.mongodb.uri = mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
```

- mongodb:// 固定前缀
- username：账号，可不填
- password：密码，可不填
- host：主机名或ip地址，只有host主机名为必填项。
- port：端口，可不填，默认27017
- /database：连接某一个数据库
- ?options：连接参数，key/value对

### 4.3 在上面实体类基础上编写PersonDao

```java
@Component
public class PersonDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Person savePerson(Person person){
        return this.mongoTemplate.save(person);
    }

    public List<Person> queryPersonListByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));
        return this.mongoTemplate.find(query, Person.class);
    }

    public List<Person> queryPagePersonList(Integer page, Integer rows) {
        Query query = new Query().limit(rows).skip((page - 1) * rows);
        return this.mongoTemplate.find(query, Person.class);
    }

    public UpdateResult update(Person person) {
        Query query = Query.query(Criteria.where("id").is(person.getId()));
        Update update = Update.update("age", person.getAge());
        return this.mongoTemplate.updateFirst(query, update, Person.class);
    }

    public DeleteResult deleteById(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return this.mongoTemplate.remove(query, Person.class);
    }
}
```

### 4.3 编写启动类，编写单元测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringPerson {

    @Autowired
    private PersonDAO personDAO;

    @Test
    public void testSave(){
        Person person = new Person(ObjectId.get(), "刘德华", 50,new Address("人民路", "香港市", "666666"));
        this.personDAO.savePerson(person);
    }

    @Test
    public void testQueryPersonListByName(){
        List<Person> list = this.personDAO.queryPersonListByName("李四");
        for (Person person : list) {
            System.out.println(person);
        }
    }

    @Test
    public void testQueryPagePersonList(){
        List<Person> list = this.personDAO.queryPagePersonList(2, 2);
        for (Person person : list) {
            System.out.println(person);
        }
    }

    @Test
    public void testUpdatae(){
        Person person = new Person();
        person.setId(new ObjectId("5c0c8a19235e194494ae65cc"));
        person.setAge(23);
        UpdateResult update = this.personDAO.update(person);
        System.out.println(update);
    }

    @Test
    public void testDelete(){
        DeleteResult deleteResult = this.personDAO.deleteById("5c0c9c1d235e1936645533ad");
        System.out.println(deleteResult);
    }

}
```

