# 【4】分布式文件系统 fastDFS #

## 主要内容

- GridFS介绍
- 基本使用

## 一 GridFS介绍

GridFS是MongoDB提供的用于持久化存储文件的模块，CMS使用MongoDB存储数据，使用GridFS可以快速集成
开发。

**它的工作原理是**：

在GridFS存储文件是将文件分块存储，文件会按照256KB的大小分割成多个块进行存储，GridFS使用两个集合
（collection）存储文件，一个集合是chunks, 用于存储文件的二进制数据；一个集合是files，用于存储文件的元数据信息（文件名称、块大小、上传时间等信息）。

从GridFS中读取文件要对文件的各各块进行组装、合并。

详细参考：https://docs.mongodb.com/manual/core/gridfs/

## 二 SpringBoot中使用

### 2.1 SpringBoot起步依赖+如下：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

### 2.2 application.yml中配置MongoDB连接信息：

```yml
spring:
  application:
    name: xc-service-manage-cms
  data:
    mongodb:
      uri:  mongodb://192.168.231.180:27017
      database: xc_cms
```

### 2.3 测试存文件

使用GridFsTemplate存储文件测试代码：向测试程序注入GridFsTemplate。

```java
//存文件
@Test
public void testStore() throws FileNotFoundException {
    //定义file
    File file =new File("c:/course.ftl");
    //定义fileInputStream
    FileInputStream fileInputStream = new FileInputStream(file);
    ObjectId objectId = gridFsTemplate.store(fileInputStream, "course.ftl");
    System.out.println(objectId);
}
```

### 2.4 读取文件

创建配置类：

```java
@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    String db;

    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase(db);
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;
    }
}
```

读取文件测试：

```java
@Autowired
GridFsTemplate gridFsTemplate;

@Autowired
GridFSBucket gridFSBucket;

//取文件
@Test
public void queryFile() throws IOException {
    //根据文件id查询文件
    GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5b9cb02435794805b43b2b04")));

    //打开一个下载流对象
    GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
    //创建GridFsResource对象，获取流
    GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
    //从流中取数据
    String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
    System.out.println(content);
}
```

