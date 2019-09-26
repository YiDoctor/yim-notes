# Spring5

## 主要内容

- Spring
- Bean
- IOC
- AOP

## 一 Spring概述

Spring是一个轻量级的开源框架，以 IOC（Inverse Of Control：反转控制）和 AOP（Aspect Oriented Programming：面向切面编程）为核心。

二 Spring IOC
---

### 2.1 IOC概述

IoC（Inverse Of Control：反转控制），是指将程序中对象获取方式发生反转，由最初new的方式创建，转交给Spring容器进行创建，降低了对象之间的耦合程度。

**在实际开发中，IOC配置实现的方式有两种**

- 基于XML文件配置的方式
  - 适合场景：Bean来自第三方
- 基于注解的方式
  - 适合场景：Bean的实现类由工作人员自己开发和维护

**IOC中用到的Jar包**：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-expression</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>commons-logging</groupId>
        <artifactId>commons-logging</artifactId>
        <version>1.2</version>
    </dependency>
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.16</version>
    </dependency>
</dependencies>
```

### 2.2 Spring容器

BeanFactory 是 Spring 容器中的顶层接口。

ApplicationContext 是BeanFactory 的子接口。

BeanFactory 和 ApplicationContext 的区别：创建对象的时间点不一样。

- ApplicationContext：只要一读取配置文件，默认情况下就会创建对象。
  - 因此更适用单例情况
- BeanFactory：什么使用什么时候创建对象。
  - 多例情况

**ApplicationContext  接口实现类：**

- ClassPathXmlApplicationContext ：它是从类的根路径下加载配置文件 推荐使用这种
- FileSystemXmlApplicationContext ：它是从磁盘路径上加载配置文件，配置文件可以在磁盘的任意位置。
- AnnotationConfigApplicationContext：当我们使用注解配置容器对象时，需要使用此类来创建 spring 容器。它用来读取注解。

### 2.3 Bean标签中对象管理

#### 2.3.1 bean 标签

**作用：**

用于配置对象让 spring 来创建的。
默认情况下它调用的是类中的无参构造函数。如果没有无参构造函数则不能创建成功。

**属性：**

- id：给对象在容器中提供一个唯一标识。用于获取对象。
- class：指定类的全限定类名。用于反射创建对象。默认情况下调用无参构造函数。
- **scope：指定对象的作用范围。**
  
  - singleton :默认值，单例的.
  * prototype :多例的.
  - request  :WEB 项目中,Spring 创建一个 Bean 的对象,将对象存入到 request 域中.
  - session  :WEB 项目中,Spring 创建一个 Bean 的对象,将对象存入到 session 域中.
  - global session  :WEB 项目中,应用在 Portlet 环境.如果没有 Portlet 环境那么
    globalSession 相当于 session.

```java
<bean id="accountService" class="com.yim.service.impl.AccountServiceImpl" scope="prototype"/>
```

#### 2.3.2  bean生命周期

**Bean对象生命周期：初始化、延迟实例化、销毁**

- 指定Spring容器中创建Bean对象初始化方法

  ```xml
  <beans default-init-method=“初始化方法名">    
  	<bean id="标识符" class="类“/>
  </beans>
  或者
  <beans>
  	<bean id="标识符" class="类“ init-method =“初始化方法名"/>  
  </beans> 
  ```

- 指定Spring容器中创建Bean对象销毁方法

  ```xml
  <beans default-destroy-method=“销毁方法名">
  	<bean id="标识符" class="类“/>  
  </beans>
  或者
  <beans>
  	<bean id="标识符" class="类“ destroy-method =“销毁方法名"/>
  </beans>
  ```

- 指定Bean对象的延迟实例化

  - 默认情况下，容器对象创建时Bean对象就会创建，可以指定实例化延迟

    ```xml
    <beans default-lazy-init=“true">
    	<bean id="标识符" class="类“/>  
    </beans>
    或者
    <beans>
    	<bean id="标识符" class="类”  lazy-init=“true"/>
    </beans>
    ```

1. 根据 Bean 的配置实例化一个 Bean 对象。
2. 根据 Spring 上下文对实例化的 bean 进行依赖注入，即对 bean 的属性进行初始化。
3. 如果实现了 BeanNameAware 接口，将会调用它的 setBeanName 方法，此方法传递的是 Bean 标签中的 id。
4. 如果 Bean 实现了 BeanFactoryAware 接口，将会调用它的 setBeanFactory 方法，此方法传递的是当前Spring 工厂实例的引用。
5. 如果 Bean 实现了 ApplicaitonContextAware 接口，那么他将会调用 setApplicaitonContext 方式，此方法传递的是 ApplicationContext 实例的引用。
6. 如果 Bean 关联了 BeanPostProcess 将会调用初始化方法 postProccessBeforeInitialization(Object obj,String s) 对 Bean 进行操作。
7. 如果 Bean 实现了 InitializingBean接口，将会调用 afterPropertiesSet方法。
8. 如果 Bean 在 Spring 的配置文件中配置了 init-method 属性将会自动调用其配置的初始化方法。
9. 如果 Bean 关联了 BeanPostProcesser 接口，将会调用 postProcessAfterInitialization(Object obj,String s)方法，由于是在 Bean 初始化结束时调用 After 方法，也可用于内存或者缓存技术。

>  注意：以上工作完成就可以使用该 Bean，由于该 Bean 的作用域是 Singletion，所以调用的是同一个 Bean 的实例

10. 当 Bean 不需要时就会进入销毁阶段，如果 Bean 实现了 DisposableBean 接口，则调用其实现的 destory方法将 spring 中的 Bean 进行销毁
11. 如果配置文件中通过 destory-method 属性指定了 Bean 的销毁方法，将调用起配置的销毁方法进行销毁。

#### 2.3.3 Bean对象的创建三种方式 ####

1. 使用默认无参构造函数，创建对象，如果类中没有无参构造函数，则会报异常

   ```xml
   <!--构造器配置  Bean组件定义格式-->
   <bean id=“标识符" class=“包名.类名"/> 
   ```

2. spring  管理静态工厂- 使用静态工厂的方法创建对象

   ```xml
   <!--静态工厂方法配置 Bean组件定义格式-->
   <bean id="标识符" class="包名.类名" factory-method="静态方法名" /> 
   ```

   ```java
   public class StaticFactory { 
       public static IAccountService staticMethod(){
       	return new AccountServiceImpl();
       }
   }
   
   <bean id="accountService" class="cn.yim.spring.factory.StaticFactory" factory-method="staticMethod"></bean>
   ```

3. 使用----------------实例工厂方法实例化

   ```xml
   <!--动态工厂方法配置 Bean组件定义格式-->
   <bean id="标识符"  factory-bean="对象id"   factory-method="方法名" />
   ```

   ```java
   public class InstanceFactory {
       public IAccountService instanceFactory(){
           return new AccountServiceImpl();
       }
   }
   
   <bean id="instanceFactory" class="cn.yim.spring.factory.InstanceFactory"></bean>
       <bean id="accountService" factory-bean="instanceFactory" factory-method="instanceFactory"></bean>
   ```

### 2.4 DI（Dependency Injection）依赖注入

依赖注入：Dependency Injection。它是 spring 框架核心 ioc 的具体实现。

我们的程序在编写时，通过`IOC`，把对象的创建交给了 spring，但是代码中不可能出现没有依赖的情况。
ioc 解耦只是降低他们的依赖关系，但不会消除。例如：我们的业务层仍会调用持久层的方法。

那这种业务层和持久层的依赖关系，在使用 spring 之后，就让 spring 使用`DI`来进行依赖关系的管理。

**依赖注入**：

- 能注入的数据：有三类
  - 基本类型和String
  -  bean类型（在配置文件中或者注解配置过的bean）
  -  类型/集合类型    
- 注入的方式有三种
  1. 使用构造函数提供
  2. 使用set方法提供
  3. 使用注解的方式提供

#### 2.4.1 构造器注入

- 使用的标签:constructor-arg
- 标签出现的位置：bean标签的内部
- 标签中的属性
  - type：用于指定要注入的数据的数据类型，该数据类型也是构造函数中某个或某些参数的类型
  - index：用于指定要注入的数据给构造函数中指定索引位置的参数赋值。索引的位置是从0开始
  - name：用于指定给构造函数中指定名称的参数赋值                                        
    =============以上三个用于指定给构造函数中哪个参数赋值===============================
  - value：用于提供基本类型和String类型的数据
  - ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象

优势：在获取bean对象时，注入数据是必须的操作，否则对象无法创建成功。
弊端：改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。

**示例如下：**

```java
public class User {
    
    private String name;
    private Integer age;
    private Date birthday;

    public User(String name, Integer age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }
    
    public User() {
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
```

```xml
<bean id="user" class="cn.yim.spring.entity.User">
    <constructor-arg name="name" value="贾克斯"></constructor-arg>
    <constructor-arg name="age" value="26"></constructor-arg>
    <constructor-arg name="birthday" ref="date"></constructor-arg>
</bean>

<!-- 配置一个日期对象 -->
<bean id="date" class="java.util.Date"></bean>
```

#### 2.4.2 set方法注入

- 涉及的标签：property
- 出现的位置：bean标签的内部
- 标签的属性
  - name：用于指定注入时所调用的set方法名称    
  - value：用于提供基本类型和String类型的数据
- ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象

优势：创建对象时没有明确的限制，可以直接使用默认构造函数
弊端：如果有某个成员必须有值，则获取对象是有可能set方法没有执行。

**示例如下1：**

```xml
<bean id="user2" class="cn.yim.spring.entity.User">
    <property name="name" value="金克斯" ></property>
    <property name="age" value="18"></property>
    <property name="birthday" ref="date"></property>
</bean>
```

**示例如下2：**使用`p:属性名`的方式注入，本质还是set注入

```xml
<!--需要在配置文件中加入-->
<beans xmlns:p="http://www.springframework.org/schema/p"     
```

```xml
<bean id="user3" class="cn.yim.spring.entity.User" p:name="test" p:age="21" p:birthday-ref="date" />
```

#### 2.4.3 集合注入

通过<array/>、<list/>、<set/>、<map/>及<props/> 元素可以定义和设置与Java类型中对应Array、List、 Set、Map及Properties的属性值。

```java
public class DemoBean {

    private String[] myStrs;
    private List<String> myList;
    private Set<String> mySet;
    private Map<String,String> myMap;
    private Properties myProps;

    public void setMyStrs(String[] myStrs) {
        this.myStrs = myStrs;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }

    public void setMySet(Set<String> mySet) {
        this.mySet = mySet;
    }

    public void setMyMap(Map<String, String> myMap) {
        this.myMap = myMap;
    }

    public void setMyProps(Properties myProps) {
        this.myProps = myProps;
    }
}
```

```xml
<bean id="demoBean" class="cn.yim.spring.entity.DemoBean">
        <property name="myStrs">
            <array>
                <value>aaa</value>
                <value>bbb</value>
                <value>ccc</value>
            </array>
        </property>
        <property name="myList">
            <list>
                <value>aaa</value>
                <value>bbb</value>
                <value>ccc</value>
            </list>
        </property>
        <property name="mySet">
            <set>
                <value>aaa</value>
                <value>bbb</value>
                <value>ccc</value>
            </set>
        </property>
        <property name="myMap">
            <map>
                <entry key="11" value="abc"></entry>
                <entry key="12"><value>123abc</value></entry>
            </map>
        </property>
        <property name="myProps">
            <props>
                <prop key="user">root</prop>
                <prop key="password">123</prop>
            </props>
        </property>
    </bean>

    <util:list id="list">
        <value>aaa</value>
        <value>bbb</value>
        <value>ccc</value>
    </util:list>
    <util:map id="map">
        <entry key="11" value="abc"></entry>
        <entry key="12"><value>123abc</value></entry>
    </util:map>
    <util:properties id="application" location="classpath:application.properties"/>
    <util:properties id="properties" location="classpath:application.properties">
        <prop key="username">#{username}</prop>
        <prop key="password">#{password}</prop><!--表达式注入-->
    </util:properties>
    <bean id="demoBean2" class="cn.yim.spring.entity.DemoBean">
        <property name="myProps" ref="application"/>
        <property name="myStrs" ref="list"/>
        <property name="myMap" ref="map"/>
    </bean>
```

> 相同格式的标签，可以混合使用

### 2.5 基于注解IOC配置

注解配置和 xml 配置要实现的功能都是一样的，都是要降低程序间的耦合。只是配置的形式不一样。

#### 2.5.1 常用注解

(一) **用于创建对象的**  

- 相当于：<bean id="" class="">

- **@Component**
  - **作用**：把资源让 spring 来管理。相当于在 xml 中配置一个 bean。
  - **属性**：
    - value：指定 bean 的 id。如果不指定 value 属性，默认 bean 的 id 是当前类的类名。首字母小写。
- **@Controller  @Service  @Repository**
  - 他们三个注解都是针对一个的衍生注解，他们的作用及属性都是一模一样的
  - 他们只不过是提供了更加明确的语义化
    - @Controller ：一般用于表现层的注解。
    - @Service ：一般用于业务层的注解。
    - @Repository ：一般用于持久层的注解。

（二）**用于注入数据的** 

- 相当于：

  - ```xml
    <property name="" ref="">
    <property name="" value="">
    ```

- **@Autowired**
  
  - **作用**：自动按照类型注入。当使用注解注入属性时，set方法可以省略。它只能注入其他 bean 类型。当有多个类型匹配时，使用要注入的对象变量名称作为 bean 的 id，在 spring 容器查找，找到了也可以注入成功。找不到就报错。
- **@Qualifier**
  - **作用**：在自动按照类型注入的基础之上，再按照 Bean 的 id 注入。它在给字段注入时不能独立使用，必须和@Autowire 一起使用；但是给方法参数注入时，可以独立使用
  - **属性**：
    - value：指定 bean 的 id。
- **@Resource**
  - **作用**：直接按照 Bean 的 id 注入。它也只能注入其他 bean 类型。
  - **属性**：
    - name：指定 bean 的 id。
- **@Value**
  - **作用**：注入基本数据类型和 String 类型数据的
  - **属性**：
    - value：用于指定值

（三）**用于改变作用范围的**

- **@Scope**
  - **作用**：指定 bean 的作用范围。
  - **属性**：
    value：指定范围的值。取值：singleton prototype request session globalsession

（四）和生命周期相关的（了解）

- 相当于：<bean id="" class="" init-method="" destroy-method="" />
- @PostConstruct ：用于指定初始化方法。
- @PreDestroy ：用于指定销毁方法。

#### 2.5.2  新注解说明

- **@Configuration**
  - **作用**：用于指定当前类是一个 spring 配置类，当创建容器时会从该类上加载注解。获取容器时需要使用
    AnnotationApplicationContext(有@Configuration 注解的类.class)。
  - **属性**：value:用于指定配置类的字节码
- **@ComponentScan**
  - **作用**：用于指定 spring 在初始化容器时要扫描的包。作用和在 spring 的 xml 配置文件中的：
    <context:component-scan base-package="com.itheima"/>是一样的。
  - **属性**：basePackages：用于指定要扫描的包。和该注解中的 value 属性作用一样。
- **@Bean**
  - **作用**：该注解只能写在方法上，表明使用此方法创建一个对象，并且放入 spring 容器。
  - **属性**：name：给当前@Bean 注解方法创建的对象指定一个名称(即 bean 的 id）。
- **@PropertySource**
  - **作用**：用于加载.properties 文件中的配置。例如我们配置数据源时，可以把连接数据库的信息写到
    properties 配置文件中，就可以使用此注解指定 properties 配置文件的位置。
  - **属性**：value[]：用于指定 properties 文件位置。如果是在类路径下，需要写上 classpath:
- **@Import**
  - **作用**：用于导入其他配置类，在引入其他配置类时，可以不用再写@Configuration 注解。当然，写上也没问题。
  - **属性**：value[]：用于指定其他配置类的字节码。

**通过注解获取容器**

```java
ApplicationContext ac = new 	AnnotationConfigApplicationContext(SpringConfiguration.class);
```

三 Spring AOP
---

### 3.1 AOP概述

 AOP（Aspect Oriented Programming）：面向切面编程，抽取程序中重复的代码，在需要执行的时候，运用动态代理技术，在不改变原有代码的基础上，实现代码增强或功能的增加。

优势：

- 减少重复代码
- 提高开发效率
- 维护方便

**AOP中所用到的Jar包如下**：

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>${spring.version}</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>5.0.5.RELEASE</version>
</dependency>
<dependency>
    <groupId>aopalliance</groupId>
    <artifactId>aopalliance</artifactId>
    <version>1.0</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.13</version>
</dependency>
```

### 3.2 AOP相关术语

- Joinpoint( 连接点):
  - 所谓连接点是指那些被拦截到的点。在 spring 中,这些点指的是方法,因为 spring 只支持方法类型的
    连接点。
- Pointcut( 切入点):
  - 所谓切入点是指我们要对哪些 Joinpoint 进行拦截的定义。
- Advice( 通知/ 增强):
  - 所谓通知是指拦截到 Joinpoint 之后所要做的事情就是通知。
  - 通知的类型：前置通知,后置通知,异常通知,最终通知,环绕通知。
- Introduction( 引介):
  - 引介是一种特殊的通知在不修改类代码的前提下, Introduction 可以在运行期为类动态地添加一些方
    法或 Field。
- Target( 目标对象):
  - 代理的目标对象。
- Weaving( 织入):
  - 是指把增强应用到目标对象来创建新的代理对象的过程。
  - spring 采用动态代理织入，而 AspectJ 采用编译期织入和类装载期织入。
- Proxy （代理）:
  - 一个类被 AOP 织入增强后，就产生一个结果代理类。
- Aspect( 切面):
  - 是切入点和通知（引介）的结合。

### 3.3 基于XML的AOP配置

- **aop:config**

  - **作用**：用于声明开始 aop 的配置

- **aop:aspect**

  - **作用**：用于配置切面。

  - **属性**：id：给切面提供一个唯一标识。
    ref：引用配置好的通知类 bean 的 id。

  - ```xml
    <aop:aspect id="txAdvice" ref="txManager">
    	<!--配置通知的类型要写在此处-->
    </aop:aspect>
    ```

- **aop:pointcut**

  - **作用**：用于配置切入点表达式。就是指定对哪些类的哪些方法进行增强。
  - **属性**：expression：用于定义切入点表达式。
    id：用于给切入点表达式提供一个唯一标识

#### 3.3.1 通知的五大类型

- **aop:before**

  - **作用**：用于配置前置通知。指定增强的方法在切入点方法之前执行

  - **属性**：

    - method:用于指定通知类中的增强方法名称
    - ponitcut-ref：用于指定切入点的表达式的引用
    - poinitcut：用于指定切入点表达式 

  - 执行时间点：切入点方法执行之前执行

  - ```xml
    <aop:before method="beginTransaction" pointcut-ref="pt1"/>
    ```

- **aop:after-returning**

  - **作用**：用于配置后置通知

  - **属性**：

    - method ：指定通知中方法的名称。
    - pointct ：定义切入点表达式
    - pointcut-ref ：指定切入点表达式的引用

  - 执行时间点：切入点方法正常执行之后。它和异常通知只能有一个执行

  - ```xml
    <aop:after-returning method="commit" pointcut-ref="pt1"/>
    ```

- **aop:after-throwing**

  - **作用**：用于配置异常通知

  - **属性**：

    - method ：指定通知中方法的名称。
    - pointct ：定义切入点表达式
    - pointcut-ref ：指定切入点表达式的引用

  - 执行时间点：切入点方法执行产生异常后执行。它和后置通知只能执行一个

  - ```xml
    <aop:after-throwing method="rollback" pointcut-ref="pt1"/>
    ```

- **aop:after**

  - **作用**：用于配置最终通知

  - **属性**：

    - method ：指定通知中方法的名称。
    - pointct ：定义切入点表达式
    - pointcut-ref ：指定切入点表达式的引用

  - 执行时间点：无论切入点方法执行时是否有异常，它都会在其后面执行。

  - ```xml
    <aop:after method="release" pointcut-ref="pt1"/>
    ```

- **aop:around** 

  - **作用**：用于配置环绕通知
  - **属性**：
    - method：指定通知中方法的名称。
    - pointct：定义切入点表达式
    - pointcut-ref：指定切入点表达式的引用
  - **说明**：它是 spring 框架为我们提供的一种可以在代码中手动控制增强代码什么时候执行的方式。
  - **注意**：通常情况下，环绕通知都是独立使用的
  - spring 框架为我们提供了一个接口：**ProceedingJoinPoint**，它可以作为环绕通知的方法参数。在环绕通知执行时，spring 框架会为我们提供该接口的实现类对象，我们直接使用就行。

  ```xml
  <aop:config>
  	<aop:pointcut  expression="execution(*  com.itheima.service.impl.*.*(..))"
  	id="pt1"/>
  		<aop:aspect id="txAdvice" ref="txManager">
  			<!-- 配置环绕通知 -->
  		<aop:around method="transactionAround" pointcut-ref="pt1"/>
  	</aop:aspect>
  </aop:config>
  ```

#### 3.3.2 切点表达式

> 通常情况下，我们都是对业务层的方法进行增强，所以切入点表达式都是切到业务层实现类。

- **类型限定表达式**

  - ```java
    within(包名.类名)
    within(com.yim.Service.XdlAccountService)限定一个具体的类
    within(com.yim.Service.XdlAccount*)支持模糊限定
    within(com.yim.Service.*)代表Service包下所有类型切入切面逻辑
    within(com.yim.*.*)多个包的模糊匹配,xdl下的直接子包下类型
    within(com.yim..*)xdl下所有包下类型都切入逻辑
    ```

- **方法限定表达式**

  - **表达式语法**：execution([修饰符] 返回值类型 包名.类名.方法名(参数))

  - ```java
    expression="execution(*  com.yim.service.impl.*.*(..))
    
    修饰符可以不写，其他都可以使用*进行匹配查询
    ```

### 3.4 基于注解的AOP配置

```xml
<!-- 告知spring，在创建容器时要扫描的包 -->
<context:component-scan base-package="com.yim"></context:component-scan>
```

```xml
<!-- 开启 spring 对注解 AOP 的支持 -->
<aop:aspectj-autoproxy/>
```

- **@Before**  前置通知
- **@AfterReturning**	后置通知 抛出异常不会执行
- **@After**	最终通知 抛出异常也会执行
- **@AfterThrowing**	异常通知 抛出异常才执行
- **@Around**	环绕通知
- **@Pointcut**  切入点表达式配置

> value属性配置切点表达式

四 Spring JDBC
---

### 4.1 Spring JDBC概述

- DAO(Data Access Object)数据访问对象  
- Spring JDBC为整合JDBC提供了封装，简化了DAO组件的编写 
- Spring JDBC提供了AOP模式的事务处理 
- Spring JDBC提供了统一的异常处理层次，它包装的异常类型DataAccessException 继承自RuntimeException无须显式的捕 获 

### 4.2 使用Spring JDBC步骤

1. **使用JDBC所需jar包**

   ```xml
   <dependency>
       <groupId>org.springframework</groupId>
       	<artifactId>spring-jdbc</artifactId>
       	<version>${spring.version}</version>
       </dependency>
       <dependency>
       	<groupId>org.springframework</groupId>
       	<artifactId>spring-tx</artifactId>
       <version>${spring.version}</version>
   </dependency>
   ```

2. **在 spring  配置文件中配置 JdbcTemplate和数据源信息**

   ```xml
   <!-- 配置一个数据库的操作模板：JdbcTemplate -->
   <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
   	<property name="dataSource" ref="dataSource"></property>
   </bean>
   <!-- 配置数据源 -->
   <bean  id="dataSource"
          class="org.springframework.jdbc.datasource.DriverManagerDataSource">
       <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
       <property name="url" value="jjdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8"></property>
       <property name="username" value="root"></property>
       <property name="password" value="1234"></property>
   </bean>
   ```

3. **编写demo测试**

   ```java
   public class JdbcTemplateDemo {
   
       public static void main(String[] args) {
   
           // 1、获取spring容器
           ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-base.xml");
   
           // 2、加载JDBCTemplate
           JdbcTemplate jdbcTemplate = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
   
           // 3、编写sql执行数据库操作
           List<Account> query = jdbcTemplate.query("select * from account", new RowMapper<Account>() {
               @Override
               public Account mapRow(ResultSet resultSet, int i) throws SQLException {
                   Account account = new Account();
                   account.setId(resultSet.getInt("id"));
                   account.setName(resultSet.getString("name"));
                   account.setMoney(resultSet.getDouble("money"));
                   return account;
               }
           });
           for (Account account : query) {
               System.out.println(account.toString());
           }
       }
   }
   ```

### 4.3 Spring中的事务控制

1. JavaEE 体系进行分层开发，事务处理位于业务层，Spring 提供了分层设计 业务层的事务处理解决方案。
2. spring 框架为我们提供了一组事务控制的接口。
3. spring 的事务控制都是基于 AOP 的，它既可以使用编程的方式实现，也可以使用配置的方式实现。

#### 4.3.1 Spring 事务控制中的API

- **PlatformTransactionManager**：该接口提供了操作事务的方法

  ```java
  public interface PlatformTransactionManager {
  	// 获取事务状态信息
      TransactionStatus getTransaction(@Nullable TransactionDefinition var1) throws TransactionException;
  	// 事务提交
      void commit(TransactionStatus var1) throws TransactionException;
  	// 事务回滚
      void rollback(TransactionStatus var1) throws TransactionException;
  }
  ```

  - **DataSourceTransactionManager**：实现类，使用 SpringJDBC 或 MyBatis 进行持久化数据时使用
  - **HibernateTransactionManager**：使用 Hibernate  版本进行持久化数据时使用

- **TransactionDefinition**：对事物进行解释定义的接口

  ```java
  	// 获取事务传播行为
  	int getPropagationBehavior();
  	// 获取事务隔离级别
      int getIsolationLevel();
  	// 获取事务超时时间 默认值是-1，没有超时限制。如果有，以秒为单位进行设置。
      int getTimeout();
  	// 获取事务是否只读 建议查询时设置为只读。
      boolean isReadOnly();
  	// 获取事务名称
      @Nullable
      String getName();
  ```

- **TransactionStatus**：描述某个时间点上事务对象状态信息

  ```java
  public interface TransactionStatus extends SavepointManager, Flushable {
      // 获取事务是否为新的事务
      boolean isNewTransaction();
  	// 获取事务是否存在储存点
      boolean hasSavepoint();
  	// 获取事务设置事务回滚
      void setRollbackOnly();
  	// 获取事务是否回滚
      boolean isRollbackOnly();
  	// 刷新事务
      void flush();
  	// 获取事务是否完成
      boolean isCompleted();
  }
  ```

#### 4.3.2 事务中的概念知识

- **事务的隔离级别**：
  - Serializable：可避免脏读、不可重复读、虚读情况的发生。（串行化）
  - Repeatable read（默认值）：可避免脏读、不可重复读情况的发生。（可 重复读） 
  - Read committed：可避免脏读情况发生（读已提交）。 
  - Read uncommitted：最低级别，以上情况均无法保证。(读未提交)
- 如果不考虑隔离性，可能会引发如下问题：
  - 脏读：指一个事务读取了另外一个事务未提交的数据。 
  - 不可重复读：在一个事务内读取表中的某一行数据，多次读取结果不同。 
  - 虚读(幻读)：是指在一个事务内读取到了别的事务插入的数据，导致前后读 取不一致。
- **事务的传播行为**
  - REQUIRED:如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。一般的选
    择（默认值）
  - SUPPORTS:支持当前事务，如果当前没有事务，就以非事务方式执行（没有事务）
  - MANDATORY：使用当前的事务，如果当前没有事务，就抛出异常
  - REQUERS_NEW:新建事务，如果当前在事务中，把当前事务挂起。
  - NOT_SUPPORTED:以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
  - NEVER:以非事务方式运行，如果当前存在事务，抛出异常
  - NESTED:如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行 REQUIRED 类似的操作。

### 4.4 基于XML的事务控制配置

编写一套简单完整spring项目流程，不使用注解

#### 4.4.1 第一步：配置事务管理器

```xml
<!-- 配置一个事务管理器 -->
<bean  id="transactionManager"
       class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <!-- 注入 DataSource -->
    <property name="dataSource" ref="dataSource"></property>
</bean>
```

#### 4.4.2 第二步：配置事务的通知引用事务管理器

```xml
<!-- 事务的配置 -->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
</tx:advice>
```

#### 4.4.3 第三步：配置事务的属性

```xml
<!-- 事务的配置 -->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <!--在 在 tx:advice 标签内部 配置事务的属性 -->
    <tx:attributes>
        <!-- 指定方法名称：是业务核心方法
        read-only：是否是只读事务。默认 false，不只读。
        isolation：指定事务的隔离级别。默认值是使用数据库的默认隔离级别。
        propagation：指定事务的传播行为。
        timeout：指定超时时间。默认值为：-1。永不超时。
        rollback-for：用于指定一个异常，当执行产生该异常时，事务回滚。产生其他异常，事务不回滚。
        没有默认值，任何异常都回滚。
        no-rollback-for：用于指定一个异常，当产生该异常时，事务不回滚，产生其他异常时，事务回
        滚。没有默认值，任何异常都回滚。
        -->
        <tx:method name="*" read-only="false" propagation="REQUIRED"/>
        <tx:method name="find*" read-only="true" propagation="SUPPORTS"/>
    </tx:attributes>
</tx:advice>
```

#### 4.4.4 第四步：配置 AOP 

```xml
<!-- 配置 aop -->
<aop:config>
    <!-- 配置切入点表达式 -->
    <aop:pointcut  expression="execution(*  cn.yim.spring.service.impl.*.*(..))" id="pt1"/>
</aop:config>
```

#### 4.4.5 第五步：配置切入点表达式和事务通知的对应关系

```xml
<!-- 在 在 aop:config  标签内部：建立事务的通知和切入点表达式的关系 -->
    <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"/>
```

### 4.5 基于注解的事务控制配置

编写一套简单完整spring项目流程，使用注解

#### 4.5.1 第一步：配置事务管理器 并注入数据源

```xml
<!-- 配置事务管理器 -->
<bean  id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="dataSource"></property>
</bean>
```

#### 4.5.2 第二步：在业务层使用@Transactional  注解

```java
@Service("accountService")
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
public class AccountServiceImpl implements IAccountService {}
```

**@Transactional注解说明**

- 该注解的属性和 xml 中的属性含义一致。该注解可以出现在接口上，类上和方法上。
- 出现接口上，表示该接口的所有实现类都有事务支持。
- 出现在类上，表示类中所有方法有事务支持
- 出现在方法上，表示方法有事务支持。
- 以上三个位置的优先级：方法>类>接口

#### 4.5.3 第三步：在配置文件中开启 spring  对注解事务的支持

```xml
<!-- 开启 spring 对注解事务的支持 -->
<tx:annotation-driven transaction-manager="transactionManager"/>
```

#### 4.5.4 创建配置类

```java
@Configuration
@EnableTransactionManagement
public class SpringTxConfiguration {
	//里面配置数据源，配置 JdbcTemplate,配置事务管理器。
}
```

