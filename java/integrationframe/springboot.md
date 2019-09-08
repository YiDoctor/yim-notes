# SpringBoot基础

## 主要内容

- SpringBoot特点
- 原理分析
- 配置文件yml
- 注解
- 技术整合

## 一  SpringBoot入门

### 1.1 SpringBoot概述

#### 1.1.1 Spring缺点分析

- 虽然Spring的组件代码是轻量级的，但它的配置却是重量级的。一开始，Spring用XML配置，而且是很多XML配置。Spring 2.5引入了基于注解的组件扫描，这消除了大量针对应用程序自身组件的显式XML配置。Spring 3.0引入了基于Java的配置，这是一种类型安全的可重构配置方式，可以代替XML。所有这些配置都代表了开发时的损耗。
- 项目的依赖管理也是一件耗时耗力的事情。在环境搭建时，需要分析要导入哪些库的坐标，而且还需要
  分析导入与之有依赖关系的其他库的坐标，一旦选错了依赖的版本，随之而来的不兼容问题就会严重阻碍项目的开发进度。

#### 1.1.2 SpringBoot解决上述Spring的缺点

SpringBoot对上述Spring的缺点进行的改善和优化，基于约定优于配置的思想，可以让开发人员不必在配置与逻辑业务之间进行思维的切换，全身心的投入到逻辑业务的代码编写中，从而大大提高了开发的效率，一定程度上缩短了项目周期。

#### 1.1.3 SpringBoot的特点

- 为基于Spring的开发提供更快的入门体验
- 开箱即用，没有代码生成，也无需XML配置。同时也可以修改默认值来满足特定的需求
- 提供了一些大型项目中常见的非功能性特性，如嵌入式服务器、安全、指标，健康检测、外部配置等
- SpringBoot不是对Spring功能上的增强，而是提供了一种快速使用Spring的方式

### 1.2 SpringBoot的核心功能

- 起步依赖
  - 起步依赖本质上是一个Maven项目对象模型（Project Object Model，POM），定义了对其他库的传递依
    赖，这些东西加在一起即支持某项功能。
  - 简单的说，起步依赖就是将具备某种功能的坐标打包到一起，并提供一些默认的功能。
- 自动配置
  - Spring Boot的自动配置是一个运行时（更准确地说，是应用程序启动时）的过程，考虑了众多因素，才决定	Spring配置应该用哪个，不该用哪个。该过程是Spring自动完成的。

### 1.3 入门项目

1. 创建一个Maven项目

2. 添加起步依赖

   ```xml
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>2.0.1.RELEASE</version>
   </parent>
   ```

3. 编写SpringBoot启动类

4. 编写Controller进行测试

### 1.4 SpringBoot热部署实现

```xml
<!--热部署配置-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

idea下需要在|Settings|Compiler|下勾选自动构建项目选项

然后 Shift+Ctrl+Alt+/，选择Registry，勾选如下内容

![springboot01](images/springboot01.png)

### 1.5 自动配置注解原理解析

注解@SpringBootApplication的源码

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
    @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
    @Filter(type = FilterType.CUSTOM, classes =
            AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
    /**
* Exclude specific auto-configuration classes such that they will never be
applied.
* @return the classes to exclude
*/
    @AliasFor(annotation = EnableAutoConfiguration.class)
    Class<?>[] exclude() default {};
    ... ... ...
}
```

- @SpringBootConfiguration：等同与@Configuration，既标注该类是Spring的一个配置类
- @EnableAutoConfiguration：SpringBoot自动配置功能开启

### 1.6 小技巧

```
java -jar myapp.jar --debug 开启控制台日志输出
```

```shell
java -jar   打包运行
mvn spring-boot：run 使用Maven插件运行
```

## 二 SpringBoot配置文件

SpringBoot是基于约定的，所以很多配置都有默认值，但如果想使用自己的配置替换默认配置的话，就可以使用
application.properties或者application.yml（application.yaml）进行配置。

### 2.1 application.yml

YML文件格式是YAML (YAML Aint Markup Language)编写的文件格式，YAML是一种直观的能够被电脑识别的的数据数据序列化格式，并且容易被人类阅读，容易和脚本语言交互的，可以被支持YAML库的不同的编程语言程序导入，比如： C/C++, Ruby, Python, Java, Perl, C#, PHP等。YML文件是以数据为核心的，比传统的xml方式更加简洁。

1. 配置普通数据

   ```yml
   name: haohao
   ```

   - 注意：value之前有一个空格

2. 配置对象数据

   ```yml
   person:
     name: haohao
     age: 31
     addr: beijing
   #或者
   person: {name: haohao,age: 31,addr: beijing}
   ```

   - 注意：key1前面的空格个数不限定，在yml语法中，相同缩进代表同一个级别

3.  配置数组（List、Set）数据

   ```yml
   city:
    - beijing
    - tianjin
    - shanghai
    - chongqing
   #或者
   city: [beijing,tianjin,shanghai,chongqing]
   #集合中的元素是对象形式
   student:
    - name: zhangsan
     age: 18
     score: 100
    - name: lisi
     age: 28
     score: 88
    - name: wangwu
     age: 38
     score: 90
   ```

   - 注意：value1与之间的 - 之间存在一个空格

### 2.2 配置文件与配置类的属性映射方式

- @**Value**：我们可以通过@Value注解将配置文件中的值映射到一个Spring管理的Bean的字段上

- **@ConfigurationProperties**：通过注解@ConfigurationProperties(prefix="配置文件中的key的前缀")可以将配置文件中的配置自动与实体进行映射

  ```yml
  person:
    name: zhangsan
    age: 18
  ```

  ```java
  @Controller
  @ConfigurationProperties(prefix = "person")
  public class QuickStartController {
      private String name;
      private Integer age;
      @RequestMapping("/quick")
      @ResponseBody
      public String quick(){
          return "springboot 访问成功! name="+name+",age="+age;
      }
      public void setName(String name) {
          this.name = name;
      }
      public void setAge(Integer age) {
          this.age = age;
      }
  }
  ```

> 注意：使用@ConfigurationProperties方式可以进行配置文件与实体字段的自动映射，但需要字段必须提供set方法才可以，而使用@Value注解修饰的字段不需要提供set方法

## 三 SpringBoot整合其他技术

### 3.1 SpringBoot整合MyBatis

```xml
<!--mybatis起步依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
<!-- MySQL连接驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

### 3.2 SpringBoot整合Junit

```xml
<!--测试的起步依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

注解：

- **@RunWith(SpringRunner.class)**
  - SpringRunner继承自SpringJUnit4ClassRunner，使用哪一个Spring提供的测试测试引擎都可以
- **@SpringBootTest(classes = MySpringBootApplication.class)**
  - @SpringBootTest的属性指定的是引导类的字节码对象

### 3.3 SpringBoot整合Spring Data JPA

1. 依赖

   ```xml
   <!-- springBoot JPA的起步依赖 -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   
   <!-- MySQL连接驱动 -->
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
   </dependency>
   ```

2. application.preperties中的配置

   ```properties
   #DB Configuration:
   spring.datasource.driverClassName=com.mysql.jdbc.Driver
   spring.datasource.url=jdbc:mysql://127.0.0.1:3306/test?
   useUnicode=true&characterEncoding=utf8
   spring.datasource.username=root
   spring.datasource.password=root
   #JPA Configuration:
   spring.jpa.database=MySQL
   spring.jpa.show-sql=true
   spring.jpa.generate-ddl=true
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.hibernate.naming_strategy=org.hibernate.cfg.ImprovedNamingStrategy
   ```

3. 编写UserRepository

   ```java
   public interface UserRepository extends JpaRepository<User,Long>{
       public List<User> findAll();
   }
   ```

### 3.4 SpringBoot整合Redis

1. 依赖

   ```xml
   <!-- 配置使用redis启动器 -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
   ```

2. application.properties中的配置

   ```properties
   #Redis
   spring.redis.host=127.0.0.1
   spring.redis.port=6379
   ```

3. 使用RedisTemplate测试

