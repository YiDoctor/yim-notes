# SSM企业权限管理系统

## 项目内容

- Oracle
- Spring项目整合
- Spring Security
- 整合MyBatis
- pageHelper使用

## 一 前期准备

Oracle 为每个项目创建单独user，oracle数据表存放在表空间下，每个用户有独立表空间

1. Oracle创建用户：

   ```sql
   语法[创建用户]： create user 用户名 identified by 口令[即密码]；
   例子： create user test identified by test;
   ```

2. 授权：

   ```sql
   语法： grant connect, resource to 用户名;
   例子： grant connect, resource to test;
   ```

3. PL\SQL基本使用
   1. 安装需要配置环境变量
   2. 如未安装数据库，仍需要安装oracle客户端
   3. SQL窗口
   4. 命令窗口
   5. 配置数据库连接地址：product\11.2.0\dbhome_1\NETWORK\ADMIN\tnsnames.ora
4. 创建表：项目资源
5. 创建Maven项目dao层，service层，entity层，web层，utils层分开创建项目
6. 编写实体类，业务层接口，持久层接口

## 二 SSM基本整合

### 2.1 Spring环境搭建

1. 引用jar包

2. 创建applicationContext.xml配置文件

3. 开启注解扫描

   ```xml
   <!-- 开启注解扫描，管理service和dao -->
   <context:component-scan base-package="com.yim.service,com.yim.dao">
   </context:component-scan>
   ```

### 2.2 SpringMVC环境搭建

1. **创建springmvc.xml配置文件**

   1. 配置视图解析器InternalResourceViewResolver

   2. 开启对SpringMVC注解支持：

      ```xml
      <mvc:annotation-driven />
      ```

   3. 设置静态资源不过滤

   4. 设置AOP注解的支持

      ```xml
      <aop:aspectj-autoproxy proxy-target-class="true"/>
      ```

2. **配置web.xml文件**

   1. 创建核心控制器：DispatcherServlet
   2. 配置编码过滤器：CharacterEncodingFilter

3. **Spring与SpringMVC的**

   ```xml
   <!-- 配置加载类路径的配置文件 -->
   <context-param>
       <param-name>contextConfigLocation</param-name>
       <param-value>classpath*:applicationContext.xml</param-value>
   </context-param>
   <!-- 配置监听器 -->
   <listener>
       <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
   </listener>
   ```

### 2.3 Spring整合MyBatis

1. 整合思路：把 mybatis 配置文件（mybatis.xml）中内容配置到 spring 配置文件中 同时，把 mybatis 配置文件的内容清掉。

2. Spring接管mybatis的Session工厂

   1. **创建属性文件：db.properties**

   2. **applicationContext.xml中添加配置**

      ```xml
      <context:property-placeholder location="classpath:db.properties"/>
      <!-- 配置连接池 -->
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="${jdbc.driver}" />
          <property name="jdbcUrl" value="${jdbc.url}" />
          <property name="user" value="${jdbc.username}" />
          <property name="password" value="${jdbc.password}" />
      </bean>
      <!-- 把交给IOC管理 SqlSessionFactory -->
      <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
          <property name="dataSource" ref="dataSource" />
      </bean>
      ```

   3. **自动扫描所有Mapper接口和文件**

      ```xml
      <!-- 扫描dao接口 -->
      <bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
          <property name="basePackage" value="com.yim.dao"/>
      </bean>
      ```

   4. **配置Spring事务**

      ```xml
      <!-- 配置Spring的声明式事务管理 -->
      <!-- 配置事务管理器 -->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <tx:annotation-driven transaction-manager="transactionManager"/>
      ```

### 2.4 整合AdminLTE进行页面创建

AdminLTE是一款建立在bootstrap和jquery之上的开源的模板主题工具，它提供了一系列响应的、可重复使用的组件，并内置了多个模板页面；同时自适应多种屏幕分辨率，兼容PC和移动端。通过AdminLTE，我们可以快速的创建一个响应式的Html5网站.

资源地址： https://github.com/ColorlibHQ/AdminLTE

黑马-定制版：https://github.com/itheima2017/adminlte2-itheima

由于AdminLTE2-IT黑马-定制版是基于FIS3进行开发，在目录结构中assets、modules、pages、
plugins都是前端开发时所使用到的，最终发布的就是release。所以对于我们使用AdminLTE2-IT黑
马-定制版来说，我们只需要关注release目录下的结构就可以。

## 四 PageHelper分页插件

### 4.1 概述

PageHelper是国内非常优秀的一款开源的mybatis分页插件，它支持基本主流与常用的数据库，例如mysql、
oracle、mariaDB、DB2、SQLite、Hsqldb等。

github 的项目地址：https://github.com/pagehelper/Mybatis-PageHelper

### 4.2 如何使用？

1. 导入依赖

   ```xml
   <dependency>
       <groupId>com.github.pagehelper</groupId>
       <artifactId>pagehelper</artifactId>
       <version>5.1.2</version>
   </dependency>
   ```

2. 在MyBatis的会话工厂中传入插件

   ```xml
   <!-- 把交给IOC管理 SqlSessionFactory -->
   <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
       <property name="dataSource" ref="dataSource"/>
       <!-- 传入PageHelper的插件 -->
       <property name="plugins">
           <array>
               <!-- 传入插件的对象 -->
               <bean class="com.github.pagehelper.PageInterceptor">                      
               </bean>
           </array>
       </property>
   </bean>
   ```

3. 在 Spring 配置文件中配置拦截器插件属性

   ```xml
   <!-- 传入插件的对象 -->
   <bean class="com.github.pagehelper.PageInterceptor">
       <property name="properties">
           <!-- 插件属性 -->
           <props>
               <prop key="helperDialect">oracle</prop>
               <prop key="reasonable">true</prop>
           </props>
       </property>
   </bean>
   ```

4. PageHelper.startPage 静态方法调用（重点）

   1. 这种方式是我们要掌握的 在你需要进行分页的 MyBatis 查询方法前调用PageHelper.startPage 静态方法即可，紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页。

   2. ```java
      /获取第1页，10条内容，默认查询总数count
      PageHelper.startPage(1, 10);
      //紧跟着的第一个select方法会被分页
      List<Country> list = countryMapper.selectIf(1);
      ```

### 4.3 插件属性介绍

- **helperDialect**：

  - 分页插件会自动检测当前的数据库链接，自动选择合适的分页方式。 你可以配置
    helperDialect 属性来指定分页插件使用哪种方言。配置时，可以使用下面的缩写值：oracle , mysql , mariadb , sqlite , hsqldb , postgresql , db2 , sqlserver , informix , h2 , sqlserver201
    2 , derby
  - **特别注意**：使用 SqlServer2012 数据库时，需要手动指定为  sqlserver2012 ，否则会使用SqlServer2005 的方式进行分页。你也可以实现  AbstractHelperDialect ，然后配置该属性为实现类的全限定名称即可使用自定义的实现方法。

- offsetAsPageNum：默认值为  false ，该参数对使用  RowBounds 作为分页参数时有效。 当该参数设置为
  true 时，会将  RowBounds 中的  offset 参数当成  pageNum 使用，可以用页码和页面大小两个参数进行分
  页。

-  rowBoundsWithCount ：默认值为 false ，该参数对使用  RowBounds 作为分页参数时有效。 当该参数设置
  为 true 时，使用  RowBounds 分页会进行 count 查询。

-  pageSizeZero ：默认值为  false ，当该参数设置为  true 时，如果  pageSize=0 或者  RowBounds.limit =
  0 就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是  Page 类型）。

- reasonable ：分页合理化参数，默认值为 false 。当该参数设置为  true 时， pageNum<=0 时会查询第一
  页， pageNum>pages （超过总数时），会查询最后一页。默认 false 时，直接根据参数进行查询。

- params ：为了支持 startPage(Object params) 方法，增加了该参数来配置参数映射，用于从对象中根据属
  性名取值， 可以配置  pageNum,pageSize,count,pageSizeZero,reasonable ，不配置映射的用默认值， 默认
  值为

  ```sql
  pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero
  ```

-  supportMethodsArguments ：支持通过 Mapper 接口参数来传递分页参数，默认值 false ，分页插件会从查询方法的参数值中，自动根据上面  params 配置的字段中取值，查找到合适的值时就会自动分页。 

- autoRuntimeDialect ：默认值为  false 。设置为  true 时，允许在运行时根据多数据源自动识别对应方言
  的分页 （不支持自动选择 sqlserver2012 ，只能使用 sqlserver ）

-  closeConn ：默认值为  true 。当使用运行时动态数据源或没有设置  helperDialect 属性自动获取数据库类
  型时，会自动获取一个数据库连接， 通过该属性来设置是否关闭获取的这个连接，默认 true 关闭，设置为
  false 后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定。

## 五 Spring Security

### 5.1 Spring Security概述

pring Security 的前身是 Acegi Security ，是 Spring 项目组中用来提供安全认证服务的框架。
(https://projects.spring.io/spring-security/) Spring Security 为基于J2EE企业应用软件提供了全面安全服务。特别
是使用领先的J2EE解决方案-Spring框架开发的企业软件项目。人们使用Spring Security有很多种原因，不过通常吸
引他们的是在J2EE Servlet规范或EJB规范中找不到典型企业应用场景的解决方案。 特别要指出的是他们不能再
WAR 或 EAR 级别进行移植。这样，如果你更换服务器环境，就要，在新的目标环境进行大量的工作，对你的应用
系统进行重新配 置安全。使用Spring Security 解决了这些问题，也为你提供很多有用的，完全可以指定的其他安
全特性。 安全包括两个主要操作。

- “认证”，是为用户建立一个他所声明的主体。主题一般式指用户，设备或可以在你系 统中执行动作的其他系
  统。
- “授权”指的是一个用户能否在你的应用中执行某个操作，在到达授权判断之前，身份的主题已经由 身份验证
  过程建立了。

Spring Security 目前支持认证一体化如下认证技术：

-  HTTP BASIC authentication headers (一个基于IEFT RFC 的标准)
-  HTTP Digest authentication headers (一个基于IEFT RFC 的标准) 
- HTTP X.509 client certificate exchange(一个基于IEFT RFC 的标准) 
- LDAP (一个非常常见的跨平台认证需要做法，特别是在大环境)
-  Form-based authentication (提供简单用户接口的需求) 
- OpenID authentication Computer Associates Siteminder JA-SIG Central Authentication Service (CAS，这是一个流行的开源单点登录系统) 
- Transparent authentication contextpropagation for Remote Method Invocation and HttpInvoker (一个Spring远程调用协议)

### 5.2 Spring Security入门使用

1. **maven依赖**：

   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.security</groupId>
           <artifactId>spring-security-web</artifactId>
           <version>5.0.1.RELEASE</version>
       </dependency>
       <dependency>
           <groupId>org.springframework.security</groupId>
           <artifactId>spring-security-config</artifactId>
           <version>5.0.1.RELEASE</version>
       </dependency>
   </dependencies>
   ```

2. **web.xml中配置**

   ```xml
   <!-- 配置加载类路径的配置文件 -->
   <context-param>
       <param-name>contextConfigLocation</param-name>
       <param-value>classpath:spring-security.xml</param-value>
   </context-param>
   <!-- 配置监听器，监听request域对象的创建和销毁的 -->	
   <listener>
       <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
   </listener>
   <!-- 委派过滤器 -->
   <filter>
       <filter-name>springSecurityFilterChain</filter-name>
       <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
   </filter>
   <filter-mapping>
       <filter-name>springSecurityFilterChain</filter-name>
       <url-pattern>/*</url-pattern>
   </filter-mapping>
   ```

3. **创建spring-security配置入门文件**

   ```xml
   <!-- 配置不拦截的资源 -->
       <security:http pattern="/login.jsp" security="none"/>
       <security:http pattern="/failer.jsp" security="none"/>
       <security:http pattern="/css/**" security="none"/>
       <security:http pattern="/img/**" security="none"/>
       <security:http pattern="/plugins/**" security="none"/>
   <!-- 配置具体的规则 auto-config="true"	不用自己编写登录的页面，框架提供默认登录页面
        use-expressions="false"	是否使用SPEL表达式（没学习过）-->
   <security:http auto-config="true" use-expressions="false">
       <!-- 配置具体的拦截的规则 pattern="请求路径的规则" access="访问系统的人，必须有ROLE_USER的角色" -->
       <security:intercept-url pattern="/**" access="ROLE_USER"/>
   </security:http>
   
   <!-- 提供了入门的方式，在内存中存入用户名和密码 -->
   <security:authentication-manager>
       <security:authentication-provider>
           <security:user-service>
               <security:user name="admin" password="{noop}admin" authorities="ROLE_USER"/>
           </security:user-service>
       </security:authentication-provider>
   </security:authentication-manager>
   ```

4. **测试，在项目webapp下创建index.html编写任意内容，启动项目访问发现会跳转如下页面：**

   ![security01](images/security01.png)

我们没有建立下面的登录页面，为什么Spring Security会跳到上面的登录页面呢？这是我们设置http的**auto-config=”true”时Spring Security自动为我们生成的**。

5. **自定义页面**

   ```xml
   <security:http auto-config="true" use-expressions="false">
       <security:intercept-url pattern="/**" access="ROLE_USER"/>
       <!-- 自定义登陆页面-->
       <security:form-login login-page="/login.html" login-processing-url="/login" 	 	 username-parameter="username" password-parameter="password" authentication-		failure- 	url="/failer.html" default-target-url="/success.html"/>
       <!-- 登出 -->
       <security:logout invalidate-session="true" logout-url="/logout" logout-success-		url="/login.jsp"/>
       <!-- 关闭CSRF,默认是开启的 -->
       <security:csrf disabled="true"/>
   </security:http>
   ```

   1. **自定义登陆页面**：
      - login-page 自定义登陆页面 
      - authentication-failure-url 用户权限校验失败之后才会跳转到这个页面，如果数据库中没有这个用户则不会跳转到这个页面。
      - default-target-url 登陆成功后跳转的页面。 
      - 注：登陆页面用户名固定 username，密码password，action:login
   2. **登出**：
      - invalidate-session 是否删除session 
      - logout-url：登出处理链接
      -  logout-success- url：登出成功页面
      - 注：登出操作 只需要链接到 logout即可登出当前用户

6. **Spring Security定义数据库认证**

   在Spring Security中如果想要使用数据进行认证操作，有很多种操作方式，这里我们介绍使用UserDetails、
   UserDetailsService来完成操作。

   - **UserDetails**

     ```java
     public interface UserDetails extends Serializable {
         Collection<? extends GrantedAuthority> getAuthorities();
         String getPassword();
         String getUsername();
         boolean isAccountNonExpired();
         boolean isAccountNonLocked();
         boolean isCredentialsNonExpired();
         boolean isEnabled();
     }
     ```

     UserDetails是一个接口，我们可以认为UserDetails作用是于**封装当前进行认证的用户信息**，但由于其是一个接口，所以我们可以对其进行实现，也可以使用Spring Security提供的一个UserDetails的实现类User来完成操作

     以下是User类的部分代码：

     ```java
     public class User implements UserDetails, CredentialsContainer {
         private String password;
         private final String username;
         private final Set<GrantedAuthority> authorities;
         private final boolean accountNonExpired; //帐户是否过期
         private final boolean accountNonLocked; //帐户是否锁定
         private final boolean credentialsNonExpired; //认证是否过期
         private final boolean enabled; //帐户是否可用
     ｝
     ```

   - UserDetailsService

     ```java
     public interface UserDetailsService {
         UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
     }
     ```

### 5.3 结合项目实现用户登录

1. spring security的配置

   ```xml
   <!-- 切换成数据库中的用户名和密码 -->
   <security:authentication-manager>
       <security:authentication-provider user-service-ref="userService">
           <!-- 配置加密的方式 -->
           <security:password-encoder ref="passwordEncoder"/>
       </security:authentication-provider>
   </security:authentication-manager>
   
   <!-- 配置加密类 -->
   <bean id="passwordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"/>
   ```

2. 编写`IUserService`接口继承`UserDetailsService`接口

3. 实现类`IUserServiceImpl`重写方法

   ```java
   //作用就是返回一个List集合，集合中装入的是角色描述
   public List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
       List<SimpleGrantedAuthority> list = new ArrayList<>();
       for (Role role : roles) {
           list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
       }
       return list;
   }
   ```

### 5.4 服务器端方法级权限控制

在服务器端我们可以通过Spring security提供的注解对方法来进行权限控制。Spring Security在方法的权限控制上
支持三种类型的注解，**JSR-250注解**、**@Secured注解**和**支持表达式的注解**，这三种注解默认都是没有启用的，需要单独通过global-method-security元素的对应属性进行启用

#### 5.4.1 开启注解使用	

1. 配置文件：

   ```xml
   <security:global-method-security jsr250-annotations="enabled"/>
   <security:global-method-security secured-annotations="enabled"/>
   <security:global-method-security pre-post-annotations="disabled"/>
   ```

2. 注解开启：

   ```
   @EnableGlobalMethodSecurity ：Spring Security默认是禁用注解的，要想开启注解，需要在继承
   WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，并在该类中将
   AuthenticationManager定义为Bean
   ```

#### 5.4.2 相关注解

- **JSR-250注解**

  - @RolesAllowed：表示访问对应方法时所应该具有的角色

    ```
    @RolesAllowed({"USER", "ADMIN"}) 该方法只要具有"USER", "ADMIN"任意一种权限就可以访问。这里可以省略前缀ROLE_，实际的权限可能是ROLE_ADMIN
    ```

  - @PermitAll：表示允许所有的角色进行访问，也就是说不进行权限控制

  - @DenyAll：是和PermitAll相反的，表示无论什么角色都不能访问

- **支持表达式的注解**

  - @PreAuthorize： 在方法调用之前,基于表达式的计算结果来限制对方法的访问
  - @PostAuthorize： 允许方法调用,但是如果表达式计算结果为false,将抛出一个安全性异常
  - @PostFilter： 允许方法调用,但必须按照表达式来过滤方法的结果
  - @PreFilter： 允许方法调用,但必须在进入方法之前过滤输入值

- **@Secured注解**

  - @Secured：注解标注的方法进行权限控制的支持，其值默认为disabled。

#### 5.4.3 页面端标签控制权限

在jsp页面中我们可以使用spring security提供的权限标签来进行权限控制

1. 依赖：

   ```xml
   <dependency>
       <groupId>org.springframework.security</groupId>
       <artifactId>spring-security-taglibs</artifactId>
       <version>version</version>
   </dependency>
   ```

2. 页面导入

   ```jsp
   <%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
   ```

#### 5.4.4 常用标签

- **authentication**：authentication代表的是当前认证对象，可以获取当前认证对象信息，例
  如用户名

  ```jsp
  <security:authentication property="" htmlEscape="" scope="" var=""/>
  ```

  - property： 只允许指定Authentication所拥有的属性，可以进行属性的级联获取，如“principle.username”，不允许直接通过方法进行调用
  - htmlEscape：表示是否需要将html进行转义。默认为true。
  - scope：与var属性一起使用，用于指定存放获取的结果的属性名的作用范围，默认我pageContext。Jsp中拥有的作用范围都进行进行指定
  - var： 用于指定一个属性名，这样当获取到了authentication的相关信息后会将其以var指定的属性名进行存放，默认是存放在pageConext中

- **authorize**：authorize是用来判断普通权限的，通过判断用户是否具有对应的权限而控制其所包含内容的显示

  ```jsp
  <security:authorize access="" method="" url="" var=""></security:authorize>
  ```

  - access： 需要使用表达式来判断权限，当表达式的返回结果为true时表示拥有对应的权限
  - method：method属性是配合url属性一起使用的，表示用户应当具有指定url指定method访问的权限，
  - method的默认值为GET，可选值为http请求的7种方法
  - url：url表示如果用户拥有访问指定url的权限即表示可以显示authorize标签包含的内容
  - var：用于指定将权限鉴定的结果存放在pageContext的哪个属性**中**

- **accesscontrollist**：accesscontrollist 标签是用于鉴定ACL权限的。其一共定义了三个属性：hasPermission、domainObject和var，其中前两个是必须指定的

  ```jsp
  <security:accesscontrollist hasPermission="" domainObject="" var=""></security:accesscontrollist>
  ```

  - hasPermission：hasPermission属性用于指定以逗号分隔的权限列表
  - domainObject：domainObject用于指定对应的域对象
  - var：var则是用以将鉴定的结果以指定的属性名存入pageContext中，以供同一页面的其它地方使用

## 六 AOP日志

使用AOP编程记录日志信息

需要记录到日志中的信息：

```java
@Component
@Aspect
public class SystemLogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService logService;

    /**
    * 访问开始时间
    */
    private Date startTime;
    /**
    * 访问的类
    * */
    private Class executionClass;
    /**
     * 访问的方法
     * */
    private Method executionMethod;

    /**
    * 前置通过:主要获取属性值
    * @params: [joinPoint]
    * @return: void
    */
    @Before("execution(* com.yim.controller.*.*(*))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        startTime = new Date();
        executionClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        // 获取访问方法参数哦
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            // 没有参数，获取无参方法
            executionMethod = executionClass.getMethod(methodName);
        }else {
            // 有参数 遍历数组获取对应Class，装入Class[]
            Class[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i].getClass();
            }
            executionMethod = executionClass.getMethod(methodName,classes);
        }
    }
    /**
    * 最终通知：主要获取日志中其它信息，时长、ip、url...
    * @params: [joinPoint]
    * @return: void
    */
    @After("execution(* com.yim.controller.*.*(*))")
    public void doAfter(JoinPoint joinPoint) throws Exception {
        // 获取类上的@RequestMapping对象
        if (executionClass != SysLogController.class) {
            RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                // 获取方法上的@RequestMapping对象
                RequestMapping methodAnnotation = executionMethod.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    // 它的值应该是类上的@RequestMapping的value+方法上的@RequestMapping的value
                    String url = classAnnotation.value()[0] + methodAnnotation.value()[0];
                    SysLog sysLog = new SysLog();
                    // 获取访问时长
                    Long executionTime = System.currentTimeMillis() - startTime.getTime();
                    // 将sysLog对象属性封装
                    sysLog.setExecutionTime(executionTime);
                    sysLog.setUrl(url);
                    // 获取ip
                    String ip = request.getRemoteAddr();
                    sysLog.setIp(ip);
                    // 可以通过securityContext获取，也可以从request.getSession中获取
                    SecurityContext context = SecurityContextHolder.getContext();
                    String username = ((User) (context.getAuthentication().getPrincipal())).getUsername();
                    sysLog.setUsername(username);
                    sysLog.setMethod("[类名]" + executionClass.getName() + "[方法名]" + executionMethod.getName());
                    sysLog.setVisitTime(startTime);
                    // 调用Service，调用dao将sysLog insert数据库
                    logService.save(sysLog);
                }
            }
        }

    }
}
```

在切面类中我们需要获取登录用户的username，还需要获取ip地址，我们怎么处理？

- username获取
- SecurityContextHolder获取
- ip地址获取
  - ip地址的获取我们可以通过request.getRemoteAddr()方法获取到。
  - 在Spring中可以通过RequestContextListener来获取request或session对象。