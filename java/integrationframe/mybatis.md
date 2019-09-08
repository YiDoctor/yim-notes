# Mybatis

## 主要内容

- Mybatis基本配置和使用
- Mybatis基本知识点

## 一 基本配置和使用（入门案例）

**创建demo项目进行一下操作:**

```xml
<!-- 引入相关依赖 -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.5</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.8</version>
</dependency>
```

### 1.1 基于xml的使用demo

1. 创建Account

   ```java
   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   @ToString
   public class Account {
   
       private Integer account_id;
   
       private String account_name;
   
       private Double balance;
   }
   ```

2. 创建DAO层接口

   ```java
   public interface IAccountDao {
       List<Account> findAll();
   }
   ```

3. 编写Mybatis主配置文件SqlMapConfig.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <environments default="mysql">
           <environment id="mysql">
               <transactionManager type="JDBC"></transactionManager>
               <dataSource type="POOLED">
                   <property name="driver" value="com.mysql.jdbc.Driver"/>
                   <property name="url" value="jdbc:mysql://192.168.225.220:3306/spring_demo?characterEncoding=utf8"></property>
                   <property name="username" value="root"></property>
                   <property name="password" value="123465"></property>
               </dataSource>
           </environment>
       </environments>
       <mappers>
           <mapper resource="mapper/IAccountDao.xml"/>
       </mappers>
   </configuration>
   ```

4. 编写Mapper映射文件IAccountDao.xml

   ```xml
   <?xml version="1.0" encoding="utf-8" ?>
   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.yim.dao.IAccountDao">
       <select id="findAll" resultType="com.yim.entity.Account">
           select * from account
       </select>
   </mapper>
   ```

5. 编写测试类

   ```java
   public class MybatisTest {
       public static void main(String[] args) throws IOException {
           // 1. 加载配置文件
           InputStream mapConfig = Resources.getResourceAsStream("SqlMapConfig.xml");
           // 2. 创建session工厂构造器
           SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
           // 3. 构造session工厂
           SqlSessionFactory factory = builder.build(mapConfig);
           // 4. 构建session会话
           SqlSession sqlSession = factory.openSession();
           // 5. 动态代理实现关系映射
           IAccountDao accountDao = sqlSession.getMapper(IAccountDao.class);
           List<Account> accounts = accountDao.findAll();
           accounts.forEach(System.out::println);
           System.out.println(accounts.get(0).getAccount_name());
           // 6. 关闭连接
           sqlSession.close();
           mapConfig.close();
       }
   }
   ```

### 1.2 使用注解的方式

在上面案例的基础上进行如下修改：

```java
public interface IAccountDao {

    @Select("select * from account")
    List<Account> findAll();
}
```

```xml
<mappers>
    <mapper class="com.yim.dao.IAccountDao"/>
</mappers>
```

## 二 了解Mybatis的基本原理

通过上面的MybatisTest类，我们基本知道了，Mybatis工作的基本流程:

那么我们想要更加深刻的了解的话，我们需要从设计者的角度去思考度量。从`MybatisTest`中的代码，我们大致可以看出用的设计模式有工厂设计模式，构建者模式，代理模式，用到的技术点动态搭理、反射、自定义注解、以及对XML文件的解析。

根据入门案例：自定义mybatis框架实现上面的查询结果

思考步骤（参照源码内容）：

1. 创建Resources类加载xml文件的方法
2. 创建SqlSessionFactoryBuilder构建者，提供build方法，build方法中包含对获取的xml流文件进行解析，使用dom4j对xml文件解析，遍历xml节点，获取数据库连接信息，获取mapper元素是resource还是class进行判断使用的是注解的方式还是xml方式编写sql的。若是xml方式：此时仍需进一步解析xml，获取sql等信息，若是注解需要编写一个注解，使用反射动态获取注解内容。
3. 构建者需要构建出session工厂，此时我们要编写一个session工厂接口，及其实现类来生产具体的SqlSession，就需要创建SqlSession产品及其实现的具体产品内容。
4. 此时SqlSeesion通过动态代理技术，通过拿到的数据库连接和sql语句信息等，进行数据库连接，得到查询结果，通过List<E>泛型返回结果值。

由此可见：主要代码内容在2和4部分

以下内容：我将贴出案例内容，以方便查看

### 2.1 创建mybatis项目并添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>dom4j</groupId>
        <artifactId>dom4j</artifactId>
        <version>1.6.1</version>
    </dependency>
    <!-- dom4j依赖包jaxen -->
    <dependency>
        <groupId>jaxen</groupId>
        <artifactId>jaxen</artifactId>
        <version>1.1.6</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.47</version>
    </dependency>
</dependencies>
```

### 2.2 编写思考步骤中1 2内容代码

```java
/**
 * 1. 使用类加载器读取配置文件的类
 * @author yim create time 2019-8-17
 */
public class Resources {

    public static InputStream getResourceAsStream(String filePath){
        return Resources.class.getClassLoader().getResourceAsStream(filePath);
    }
}
```

```java
/**
 * @author yim create time 2019-8-17
 * 2. 用于创建SqlSeessionFactory
 */
public class SqlSessionFactoryBuilder {
    /**
     * 根据参数的字节输入流创建一个SqlSessionFactory工厂
     * @param config
     * @return com.yim.mybatis.sqlsession.SqlSessionFactory
     */
    public SqlSessionFactory build(InputStream config){
        Configuration configuration = XMLConfigBuilder.loadConfiguration(config);
        return new DefaultSqlSessionFactory(configuration);
    }
}
```

```java
/**
 * 用于解析配置文件
 * @author yim create time 2019-8-17
 */
public class XMLConfigBuilder {

    /**
     * 解析主配置文件，把里面的内容填充到DefaultSqlSession所需要的地方
     * 使用的技术：
     * dom4j+xpath
     */
    public static Configuration loadConfiguration(InputStream config) {
        try {
            //定义封装连接信息的配置对象（mybatis的配置对象）
            Configuration cfg = new Configuration();

            //1.获取SAXReader对象
            SAXReader reader = new SAXReader();
            //2.根据字节输入流获取Document对象
            Document document = reader.read(config);
            //3.获取根节点
            Element root = document.getRootElement();
            //4.使用xpath中选择指定节点的方式，获取所有property节点
            List<Element> propertyElements = root.selectNodes("//property");
            //5.遍历节点
            for (Element propertyElement : propertyElements) {
                //判断节点是连接数据库的哪部分信息
                //取出name属性的值
                String name = propertyElement.attributeValue("name");
                if ("driver".equals(name)) {
                    //表示驱动
                    //获取property标签value属性的值
                    String driver = propertyElement.attributeValue("value");
                    cfg.setDriver(driver);
                }
                if ("url".equals(name)) {
                    //表示连接字符串
                    //获取property标签value属性的值
                    String url = propertyElement.attributeValue("value");
                    cfg.setUrl(url);
                }
                if ("username".equals(name)) {
                    //表示用户名
                    //获取property标签value属性的值
                    String username = propertyElement.attributeValue("value");
                    cfg.setUsername(username);
                }
                if ("password".equals(name)) {
                    //表示密码
                    //获取property标签value属性的值
                    String password = propertyElement.attributeValue("value");
                    cfg.setPassword(password);
                }
            }
            //取出mappers中的所有mapper标签，判断他们使用了resource还是class属性
            List<Element> mapperElements = root.selectNodes("//mappers/mapper");
            //遍历集合
            for (Element mapperElement : mapperElements) {
                //判断mapperElement使用的是哪个属性
                Attribute attribute = mapperElement.attribute("resource");
                if (attribute != null) {
                    System.out.println("使用的是XML");
                    //表示有resource属性，用的是XML
                    //取出属性的值
                    String mapperPath = attribute.getValue();
                    //把映射配置文件的内容获取出来，封装成一个map
                    Map<String, Mapper> mappers = loadMapperConfiguration(mapperPath);
                    //给configuration中的mappers赋值
                    cfg.setMappers(mappers);
                }else{
                    System.out.println("使用的是注解");
                    //表示没有resource属性，用的是注解
                    //获取class属性的值
                    String daoClassPath = mapperElement.attributeValue("class");
                    //根据daoClassPath获取封装的必要信息
                    Map<String,Mapper> mappers = loadMapperAnnotation(daoClassPath);
                    //给configuration中的mappers赋值
                    cfg.setMappers(mappers);
                }
            }
            //返回Configuration
            return cfg;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                config.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 根据传入的参数，解析XML，并且封装到Map中
     *
     * @param mapperPath 映射配置文件的位置
     * @return map中包含了获取的唯一标识（key是由dao的全限定类名和方法名组成）
     * 以及执行所需的必要信息（value是一个Mapper对象，里面存放的是执行的SQL语句和要封装的实体类全限定类名）
     */
    private static Map<String, Mapper> loadMapperConfiguration(String mapperPath) throws IOException {
        InputStream in = null;
        try {
            //定义返回值对象
            Map<String, Mapper> mappers = new HashMap<String, Mapper>();
            //1.根据路径获取字节输入流
            in = Resources.getResourceAsStream(mapperPath);
            //2.根据字节输入流获取Document对象
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            //3.获取根节点
            Element root = document.getRootElement();
            //4.获取根节点的namespace属性取值
            String namespace = root.attributeValue("namespace");//是组成map中key的部分
            //5.获取所有的select节点
            List<Element> selectElements = root.selectNodes("//select");
            //6.遍历select节点集合
            for (Element selectElement : selectElements) {
                //取出id属性的值      组成map中key的部分
                String id = selectElement.attributeValue("id");
                //取出resultType属性的值  组成map中value的部分
                String resultType = selectElement.attributeValue("resultType");
                //取出文本内容            组成map中value的部分
                String queryString = selectElement.getText();
                //创建Key
                String key = namespace + "." + id;
                //创建Value
                Mapper mapper = new Mapper();
                mapper.setQueryString(queryString);
                mapper.setResultType(resultType);
                //把key和value存入mappers中
                mappers.put(key, mapper);
            }
            return mappers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
        }
    }

    /**
     * 根据传入的参数，得到dao中所有被select注解标注的方法。
     * 根据方法名称和类名，以及方法上注解value属性的值，组成Mapper的必要信息
     *
     * @param daoClassPath
     * @return
     */
    private static Map<String, Mapper> loadMapperAnnotation(String daoClassPath) throws Exception {
        //定义返回值对象
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();

        //1.得到dao接口的字节码对象
        Class daoClass = Class.forName(daoClassPath);
        //2.得到dao接口中的方法数组
        Method[] methods = daoClass.getMethods();
        //3.遍历Method数组
        for (Method method : methods) {
            //取出每一个方法，判断是否有select注解
            boolean isAnnotated = method.isAnnotationPresent(Select.class);
            if (isAnnotated) {
                //创建Mapper对象
                Mapper mapper = new Mapper();
                //取出注解的value属性值
                Select selectAnno = method.getAnnotation(Select.class);
                String queryString = selectAnno.value();
                mapper.setQueryString(queryString);
                //获取当前方法的返回值，还要求必须带有泛型信息
                Type type = method.getGenericReturnType();//List<User>
                //判断type是不是参数化的类型
                if (type instanceof ParameterizedType) {
                    //强转
                    ParameterizedType ptype = (ParameterizedType) type;
                    //得到参数化类型中的实际类型参数
                    Type[] types = ptype.getActualTypeArguments();
                    //取出第一个
                    Class domainClass = (Class) types[0];
                    //获取domainClass的类名
                    String resultType = domainClass.getName();
                    //给Mapper赋值
                    mapper.setResultType(resultType);
                }
                //组装key的信息
                //获取方法的名称
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String key = className + "." + methodName;
                //给map赋值
                mappers.put(key, mapper);
            }
        }
        return mappers;
    }

}
```

`**XMLConfigBuilder`中将获取的内容需要具体类来接收用来存储数据，创建`Configuration`和`Mapper`类**

```java
public class Configuration {

    private String driver;
    private String url;
    private String username;
    private String password;

    private Map<String,Mapper> mappers = new HashMap<>();

    public Map<String, Mapper> getMappers() {
        return mappers;
    }

    public void setMappers(Map<String, Mapper> mappers) {
        // 追加的方式
        this.mappers.putAll(mappers);
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

```java
/**
 * 用于封装执行的sql语句和结果的全限定类名
 * @author yim create time 2019-8-17
 */
public class Mapper {
    private String queryString;
    private String resultType;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
```

**创建Select注解类：**

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    
    /**
     * 配置sql语句
     */
    String value();
}
```

### 2.3 编写思考步骤中3内容代码

```java
/**
 * 抽象工厂
 * @author yim create time 2019-8-17
 */
public interface SqlSessionFactory {

    /**
     * 用于打开一个新的SqlSession会话
     */
    SqlSession openSession();
}
```

```java
/**
 * 具体工厂
 * @author yim create time 2019-8-17
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
```

```java
/**
 * mybatis中与数据库交互的核心类,可以代理dao接口
 * @author yim create time 2019-8-17
 */
public interface SqlSession {
    
    /**
     * 根据参数创建一个代理对象
     * @param daoInterfaceClass dao的接口字节码
     * @return T
     */
    <T> T getMapper(Class<T> daoInterfaceClass);

    /**
     * 释放资源
     * @param
     * @return void
     */
    void close();
}
```

```java
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Connection connection;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
        // 构造方法中进行了数据库连接
        connection = DataSourceutil.getConnection(configuration);
    }

    @Override
    public <T> T getMapper(Class<T> daoInterfaceClass) {
        // 调用代理类
        return (T)Proxy.newProxyInstance(daoInterfaceClass.getClassLoader()
                ,new Class[]{daoInterfaceClass},new MapperProxy(configuration.getMappers(),connection));
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### 2.4 编写思考步骤4代码内容

```java
public class DataSourceutil {

    /**
     * 获取一个连接
     * @param configuration
     * @return java.sql.Connection
     */
    public static Connection getConnection(Configuration configuration){
        try {
            Class.forName(configuration.getDriver());
            return DriverManager.getConnection(configuration.getUrl()
                    ,configuration.getUsername(),configuration.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

```java
public class MapperProxy implements InvocationHandler {

    private Map<String,Mapper> mappers;
    private Connection connection;

    public MapperProxy(Map<String,Mapper> mappers,Connection connection){
        this.mappers = mappers;
        this.connection = connection;
    }

    /**
     * invoke用于方法增强
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();

        String key = className + "." + methodName;
        Mapper mapper = mappers.get(key);
        if (mapper == null) {
            throw new IllegalArgumentException("传入参数有误");
        }
        return new Executor().selectList(mapper,connection);
    }
}
```

```java
/**
 * 负责执行SQL语句，并且封装结果集
 * @author yim create time 2019-8-17
 */
public class Executor {

    public <E> List<E> selectList(Mapper mapper, Connection conn) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //1.取出mapper中的数据
            String queryString = mapper.getQueryString();
            String resultType = mapper.getResultType();
            Class domainClass = Class.forName(resultType);
            //2.获取PreparedStatement对象
            pstm = conn.prepareStatement(queryString);
            //3.执行SQL语句，获取结果集
            rs = pstm.executeQuery();
            //4.封装结果集
            //定义返回值
            List<E> list = new ArrayList<E>();
            while(rs.next()) {
                //实例化要封装的实体类对象
                E obj = (E)domainClass.newInstance();

                //取出结果集的元信息：ResultSetMetaData
                ResultSetMetaData rsmd = rs.getMetaData();
                //取出总列数
                int columnCount = rsmd.getColumnCount();
                //遍历总列数
                for (int i = 1; i <= columnCount; i++) {
                    //获取每列的名称，列名的序号是从1开始的
                    String columnName = rsmd.getColumnName(i);
                    //根据得到列名，获取每列的值
                    Object columnValue = rs.getObject(columnName);
                    //给obj赋值：使用Java内省机制（借助PropertyDescriptor实现属性的封装）
                    //要求：实体类的属性和数据库表的列名保持一种
                    PropertyDescriptor pd = new PropertyDescriptor(columnName,domainClass);
                    //获取它的写入方法
                    Method writeMethod = pd.getWriteMethod();
                    //把获取的列的值，给对象赋值
                    writeMethod.invoke(obj,columnValue);
                }
                //把赋好值的对象加入到集合中
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(pstm,rs);
        }
    }

    private void release(PreparedStatement pstm,ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(pstm != null){
            try {
                pstm.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
```

### 2.5 执行代码演示结果

由下图可见，我是创建了一个mybatis项目，在spring-demo把mybatis作为二方库导入使用

![mybatis01](/images/mybatis01.png)

## 三 Mybatis基于dao层的CRUD操作

使用要求：

1. 持久层接口和持久层接口的映射配置必须在相同的包下

2. 持久层映射配置中 mapper 标签的 namespace 属性取值必须是持久层接口的全限定类名

3. ```
   SQL 语句的配置标签<select>,<insert>,<delete>,<update>的 id 属性必须和持久层接口的方法名相同。 
   ```

### 3.1 定义dao层接口

```java
// 定义dao层接口
public interface IAccountDao {
    /**
     * 根据id查询账户
     *
     * @param accountId
     * @return com.yim.entity.Account
     */
    Account findById(Integer accountId);
    /**
     * 保存一个账户
     * @param account
     * @return int
     */
    int save(Account account);
    /**
     * 更新账户
     * @param account
     * @return int
     */
    int update(Account account);
    /**
     * 删除账户根据id
     * @param accountId
     * @return int
     */
    int delete(Integer accountId);
    /**
     * 模糊查询01
     * @param accountName
     * @return com.yim.entity.Account
     */
    Account findByName(String accountName);
    /**
     * 模糊查询02
     * @param accountName
     * @return com.yim.entity.Account
     */
    Account findByName02(String accountName);
}
```

### 3.2 在xml中配置sql

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yim.dao.IAccountDao">

    <resultMap id="accountMap" type="com.yim.entity.Account">
        <id property="accountId" column="account_id"/>
        <result column="account_name" property="accountName"/>
        <result column="balance" property="balance"/>
    </resultMap>

    <select id="findById" resultMap="accountMap">
        select * from account where account_id = #{accountId}
    </select>

    <insert id="save" parameterType="com.yim.entity.Account">
        insert into account(account_id,account_name,balance) values (#{accountId},#{accountName},#{balance})
        <!-- 配置保存时获取插入的 id -->
        <selectKey keyColumn="account_id" keyProperty="accountId" resultType="int">
            select last_insert_id();
        </selectKey>
    </insert>

    <update id="update" parameterType="com.yim.entity.Account">
        update account set account_name = #{accountName},balance = #{balance} where account_id = #{accountId}
    </update>

    <delete id="delete" parameterType="java.lang.Integer">
        delete from account where account_id = #{accountId}
    </delete>

    <!-- 模糊查询的两种方式，第一种需要在Java中写%符号 第二种写法固定${value}-->
    <select id="findByName" resultMap="accountMap" parameterType="String">
        select * from account where account_name like #{accountName}
    </select>
    <select id="findByName02" resultMap="accountMap" parameterType="String">
        select * from account where account_name like '%${value}%'
    </select>
</mapper>
```

**属性示意：**

- parameterType 属性：代表参数的类型，因为我们要传入的是一个类的对象，所以类型就写类的全名称。
- resultType 属性：可以指定结果集的类型，它支持基本类型和实体类类型。 
- resultMap 标签：可以建立查询的列名和实体类的属性名称不一致时建立对应关系。从而实现封装。
  - id 标签：用于指定主键字段
  - result 标签：用于指定非主键字段
  - column 属性：用于指定数据库列名
  - property 属性：用于指定实体类属性名称  

**#{}与${}**

```
#{}表示一个占位符号
通过#{}可以实现 preparedStatement 向占位符中设置值，自动进行 java 类型和 jdbc 类型转换，
#{}可以有效防止 sql 注入。 #{}可以接收简单类型值或 pojo 属性值。 如果 parameterType 传输单个简单类
型值， #{}括号中可以是 value 或其它名称。
${}表示拼接 sql 串
通过${}可以将 parameterType 传入的内容拼接在 sql 中且不进行 jdbc 类型转换， ${}可以接收简
单类型值或 pojo 属性值，如果 parameterType 传输单个简单类型值， ${}括号中只能是 value。
```

### 3.3 编写测试代码测试增删改查

```java
public class CrudTest {

    private InputStream inputStream;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private IAccountDao accountDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = builder.build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        accountDao = sqlSession.getMapper(IAccountDao.class);
    }

    @After
    public void destroy() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void findById() {
        Account account = accountDao.findById(1);
        System.out.println(account);
    }

    @Test
    public void save() {
        Account account = new Account(4, "李四", 60.0);
        int save = accountDao.save(account);
        System.out.println(save);
    }

    @Test
    public void update() {
        Account account = accountDao.findById(3);
        account.setAccountName("李宇");
        account.setBalance(88.0);
        int update = accountDao.update(account);
        System.out.println("返回结果为1则修改成功" + update);
    }

    @Test
    public void delete() {
        int delete = accountDao.delete(4);
        System.out.println(delete);
    }

    @Test
    public void likeSelect() {
        Account account = accountDao.findByName("%李%");
        Account account1 = accountDao.findByName02("张");
        System.out.println(account.toString());
        System.out.println(account1.toString());
    }
}
```

### 3.4 Mybatis动态Sql

- <if> 标签   
- <where> 标签
- <foreach>标签

**综合使用**：传入多个 id 查询用户信息，用下边两个 sql 实现：

1. 定义查询类

   ```java
   public class QueryIds {
   
       private List<Integer> ids;
   
       public List<Integer> getIds() {
           return ids;
       }
   
       public void setIds(List<Integer> ids) {
           this.ids = ids;
       }
   }
   ```

2. 编写dao层查询方法

   ```java
   	/**
        * 根据多个id进行查询
        * @param queryIds
        * @return java.util.List<com.yim.entity.Account>
        */
   List<Account> findByIds(QueryIds queryIds);
   ```

3. 使用动态标签

   ```xml
   <sql id="defaultSql">
       select * from account
   </sql>
   
   <select id="findByIds" resultMap="accountMap" parameterType="com.yim.entity.QueryIds">
       <include refid="defaultSql"></include>
       <where>
           <if test="ids != null and queryIds.size() > 0">
               <foreach collection="ids" open="account_id in (" close=")" item="uid"  separator=",">
                   #{uid}
               </foreach>
           </if>
       </where>
   </select>
   
   <foreach>标签用于遍历集合，它的属性：
   collection:代表要遍历的集合元素，注意编写时不要写#{}
   open:代表语句的开始部分
   close:代表结束部分
   item:代表遍历集合的每个元素，生成的变量名
   sperator:代表分隔符
   ```

4. 进行测试

   ```java
   @Test
   public void tagTest(){
       QueryIds queryIds = new QueryIds();
       ArrayList<Integer> list = new ArrayList<>();
       list.add(1);
       list.add(2);
       list.add(3);
       List<Account> byIds = accountDao.findByIds(queryIds);
       for (Account byId : byIds) {
           System.out.println(byId);
       }
   }
   ```

### 3.5 一对多关系

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private Integer id;
    private String name;
    private Integer sex;

    private List<Account> accounts;
}
```

```xml
<mapper namespace="com.yim.dao.IUserDao">

    <resultMap id="userMap" type="com.yim.entity.User">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="sex" property="sex"/>
        <!-- collection 是用于建立一对多中集合属性的对应关系ofType 用于指定集合元素的数据类型-->
        <collection property="accounts" ofType="com.yim.entity.Account">
            <id property="accountId" column="account_id"/>
            <result column="account_name" property="accountName"/>
            <result column="balance" property="balance"/>
        </collection>
    </resultMap>

    <select id="queryAll" resultMap="userMap">
        select * from user left join account where id = account_id;
    </select>
</mapper>
```

### 3.6 多对多关系

即可以看做是两个一对多关系的合并；

如role表和user表

一个用户可以有多个角色

一个角色也可以包含多个用户

这两者之间的关系构成了多对多的关系，表现在具体的代码上可以通过案例来实现

由于此处内容过于重复，不做演示

## 四 Mybatis进阶内容

### 4.1 Mybatis连接池

查看Mybatis配置文件，我们会发现

```xml
<dataSource type="POOLED">
    <property name="driver" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://192.168.225.220:3306/spring_demo?characterEncoding=utf8"></property>
    <property name="username" value="root"></property>
    <property name="password" value="123456"></property>
</dataSource>
```

Mybatis数据源分为三类：如下

| UNPOOLED | 不使用连接池的数据源   |
| -------- | ---------------------- |
| POOLED   | 使用连接池的数据源     |
| JNDI     | 使用 JNDI 实现的数据源 |

MyBatis 在初始化时， 根据<dataSource>的 type 属性来创建相应类型的的数据源 DataSource，即：

- type=”POOLED”： MyBatis 会创建 PooledDataSource 实例
- type=”UNPOOLED” ： MyBatis 会创建 UnpooledDataSource 实例
- type=”JNDI”： MyBatis 会从 JNDI 服务上查找 DataSource 实例，然后返回使用 

### 4.2 Mybatis事务控制

在 JDBC 中我们可以通过手动方式将事务的提交改为手动方式，通过 setAutoCommit()方法就可以调整。 

```java
// 传入true值，设置成自动提交
sqlSession = sqlSessionFactory.openSession(true);

SqlSession openSession(boolean autoCommit);
```

### 4.3 Mybatis延迟加载

**延迟加载：**就是在需要用到数据时才进行加载，不需要用到数据时就不加载数据。延迟加载也称懒加载.

- 好处： 先从单表查询，需要时再从关联表去关联查询，大大提高数据库性能，因为查询单表要比关联查询多张表速度要快。
- 坏处：因为只有当需要用到数据时，才会进行数据库查询，这样在大批量数据查询时，因为查询工作也要消耗
  时间，所以可能造成用户等待时间变长，造成用户体验下降 

在Mybatis主配置文件SqlMapConfig.xml添加如下内容：开始延迟加载

```xml
<settings>
    <setting name="lazyLoadingEnabled" value="true"/>
    <setting name="aggressiveLazyLoading" value="false"/>
</settings>
```

#### 4.3.1 一对多关系延时加载实现

```xml
<!-- collection 是用于建立一对多中集合属性的对应关系
ofType 用于指定集合元素的数据类型
select 是用于指定查询账户的唯一标识（账户的 dao 全限定类名加上方法名称）
column 是用于指定使用哪个字段的值作为条件查询
-->
<collection property="accounts" ofType="account"
select="com.yim.dao.IAccountDao.findByUid"
column="id">
</collection>
```

### 4.4 Mybatis缓存

Mybatis中的缓存分为一级缓存和二级缓存

一级缓存是 SqlSession 级别的缓存，只要 SqlSession 没有 flush 或 close，它就存在 。

一级缓存是 SqlSession 范围的缓存，当调用 SqlSession 的修改，添加，删除， commit()， close()等 方法时，就会清空一级缓存。 

二级缓存是 mapper 映射级别的缓存，多个 SqlSession 去操作同一个 Mapper 映射的 sql 语句，多个SqlSession 可以共用二级缓存，二级缓存是跨 SqlSession 的。 

#### 4.4.1 二级缓存的开启与关闭

1. 在SqlMapConfig.xml中开启二级缓存

```xml
<settings>
    <!-- 开启二级缓存的支持 -->
    <setting name="cacheEnabled" value="true"/>
</settings>
因为 cacheEnabled 的取值默认就为 true，所以这一步可以省略不配置。为 true 代表开启二级缓存；为
false 代表不开启二级缓存。
```

2. 在映射文件中开启支持

```xml
<!--useCache=”true”代表当前这个 statement 要使用二级缓存，如果不使用二级缓存可以设置为 false。
    注意： 针对每次查询都需要最新的数据 sql，要设置成 useCache=false，禁用二级缓存。-->
<select id="queryAll" resultMap="userMap" useCache="true">
    select * from user left join account where id = account_id;
</select>
<!-- 开启二级缓存的支持 -->
<cache></cache>
```

#### 4.4.2 二级缓存注意事项

当我们在使用二级缓存时，所缓存的类一定要实现 java.io.Serializable 接口，这种就可以使用序列化方式来保存对象。 

## 五 Mybatis注解开发

### 5.1 常用注解说明

- @Insert:实现新增
- @Update:实现更新
- @Delete:实现删除
- @Select:实现查询
- @Result:实现结果集封装
- @Results:可以与@Result 一起使用，封装多个结果集
- @ResultMap:实现引用@Results 定义的封装
- @One:实现一对一结果集封装
- @Many:实现一对多结果集封装
- @SelectProvider: 实现动态 SQL 映射
- @CacheNamespace:实现注解二级缓存的使用 

```
@Results 注解
    代替的是标签<resultMap>
    该注解中可以使用单个@Result 注解，也可以使用@Result 集合
@Results（{@Result（）， @Result（） }）或@Results（@Result（））
@Resutl 注解
    代替了 <id>标签和<result>标签
    @Result 中 属性介绍：
        id 是否是主键字段
        column 数据库的列名
        property 需要装配的属性名
        one 需要使用的@One 注解（@Result（one=@One）（）））
        many 需要使用的@Many 注解（@Result（many=@many）（）））
@One 注解（一对一）
	代替了<assocation>标签，是多表查询的关键，在注解中用来指定子查询返回单一对象。
@One 注解属性介绍：
    select 指定用来多表查询的 sqlmapper
    fetchType 会覆盖全局的配置参数 lazyLoadingEnabled。。
    使用格式：
    @Result(column=" ",property="",one=@One(select=""))
@Many 注解（多对一）
    代替了<Collection>标签,是是多表查询的关键，在注解中用来指定子查询返回对象集合。
    注意：聚集元素用来处理“一对多”的关系。需要指定映射的 Java 实体类的属性，属性的 javaType
    （一般为 ArrayList）但是注解中可以不定义；
    使用格式：
    @Result(property="",column="",many=@Many(select=""))
```

### 5.2 CRUD使用示例

```java
public interface IAccountDao {
    /**
     * 根据id查询账户
     *
     * @param accountId
     * @return com.yim.entity.Account
     */
    @Select("select * from account where account_id = #{accountId}")
    @Results(id = "accountMap",value = {
            @Result(id = true,column = "account_id",property = "accountId"),
            @Result(column = "account_name",property = "accountName"),
            @Result(column = "balance",property = "balance")
    })
    Account findById(Integer accountId);

    /**
     * 保存一个账户
     * @param account
     * @return int
     */
    @Insert("insert into account(account_name,balance) values (#{accountName},#{balance})")
    @SelectKey(keyColumn = "account_id",keyProperty = "accountId",resultType = Integer.class,before = false,statement = {
            "select last_insert_id()"
    })
    int save(Account account);
    /**
     * 更新账户
     * @param account
     * @return int
     */
    @Update("update account set account_name = #{accountName},balance = #{balance} where account_id = #{accountId}")
    int update(Account account);
    /**
     * 删除账户根据id
     * @param accountId
     * @return int
     */
    @Delete("delete from account where account_id = #{accountId}")
    int delete(Integer accountId);

    /**
     * 模糊查询01
     * @param accountName
     * @return com.yim.entity.Account
     */
    @Select("select * from account where account_name like #{accountName}")
    @ResultMap("userMap")
    Account findByName(String accountName);
    /**
     * 模糊查询02
     * @param accountName
     * @return com.yim.entity.Account
     */
    @Select("select * from account where account_name like '%${value}%'")
    @ResultMap("userMap")
    Account findByName02(String accountName);
```

**测试代码：如3.3处相同**

### 5.3 复杂使用示例

```java
@CacheNamespace(blocking=true)
public interface IUserDao {
    /**
     * 查询所有用户
     * @param
     * @return java.util.List<com.yim.entity.User>
     */
    @Select("select * from user")
    @Results(id = "userMap",
        value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "accountId",property = "accounts",many = @Many(
                    select = "com.yim.dao.IAccountDao.findById",
                    // 设置collection加载方式为懒加载
                    fetchType = FetchType.LAZY
            ))
        }
    )
    List<User> queryAll();
}
```