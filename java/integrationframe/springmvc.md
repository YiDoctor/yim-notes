# SpringMVC

## 主要内容

- MVC结构模型
- 五大组件
- web三大组件
- 常用注解
- SSM项目整合

## 一 SpringMVC概述

### 1.1 三层架构和MVC

#### 1.1.1 三层结构

1. 咱们开发服务器端程序，一般都基于两种形式，一种C/S架构程序，一种B/S架构程序
2. 使用Java语言基本上都是开发B/S架构的程序，B/S架构又分成了三层架构
3. 三层架构
   1. 表现层：WEB层，用来和客户端进行数据交互的。表现层一般会采用MVC的设计模型
   2. 业务层：处理公司具体的业务逻辑的
   3. 持久层：用来操作数据库的

#### 1.1.2 MVC

1. MVC全名是Model View Controller 模型视图控制器，每个部分各司其职。
2. Model：数据模型，JavaBean的类，用来进行数据封装。
3. View：指JSP、HTML用来展示数据给用户
4. Controller：用来接收用户的请求，整个流程的控制器。用来进行数据校验等。

### 1.2 SpringMVC概述

#### 1.2.1 SpringMVC ？

SpringMVC 是一种基于 Java 的实现 MVC 设计模型的请求驱动类型的轻量级 Web 框架，属于 Spring FrameWork 的后续产品，已经融合在 Spring Web Flow 里面。Spring 框架提供了构建 Web 应用程序的全功能 MVC 模块。使用 Spring 可插入的 MVC 架构，从而在使用 Spring 进行 WEB 开发时，可以选择使用 Spring的 Spring MVC 框架或集成其他 MVC 开发框架，如 Struts1(现在一般不用)，Struts2 等。SpringMVC 已经成为目前最主流的 MVC 框架之一，并且随着 Spring3.0 的发布，全面超越 Struts2，成为最优秀的 MVC 框架。
它通过一套注解，让一个简单的 Java 类成为处理请求的控制器，而无须实现任何接口。同时它还支持RESTful 编程风格的请求。

#### 1.2.2 SpringMVC  的优势

```
1、清晰的角色划分：
    前端控制器（DispatcherServlet）
    请求到处理器映射（HandlerMapping）
    处理器适配器（HandlerAdapter）
    视图解析器（ViewResolver）
    处理器或页面控制器（Controller）
    验证器（ Validator）
    命令对象（Command 请求参数绑定到的对象就叫命令对象）
    表单对象（Form Object 提供给表单展示和提交到的对象就叫表单对象）。
2、分工明确，而且扩展点相当灵活，可以很容易扩展，虽然几乎不需要。
3、由于命令对象就是一个 POJO，无需继承框架特定 API，可以使用命令对象直接作为业务对象。
4、和 Spring 其他框架无缝集成，是其它 Web 框架所不具备的。
5、可适配，通过 HandlerAdapter 可以支持任意的类作为处理器。
6、可定制性，HandlerMapping、ViewResolver 等能够非常简单的定制。
7、功能强大的数据验证、格式化、绑定机制。
8、利用 Spring 提供的 Mock 对象能够非常简单的进行 Web 层单元测试。
9、本地化、主题的解析的支持，使我们更容易进行国际化和主题的切换。
10、强大的 JSP 标签库，使 JSP 编写更容易。
………………还有比如RESTful风格的支持、简单的文件上传、约定大于配置的契约式编程支持、基于注解的零配
置支持等等。
```

#### 1.2.3 SpringMVC 和 和 Struts2  的优略分析

- 共同点：
  - 它们都是表现层框架，都是基于 MVC 模型编写的。
  - 它们的底层都离不开原始 ServletAPI。
  - 它们处理请求的机制都是一个核心控制器。
- 区别：
  - Spring MVC 的入口是 Servlet, 而 Struts2 是 Filter
  - Spring MVC 是基于方法设计的，而 Struts2 是基于类，Struts2 每次执行都会创建一个动作类。所
    以 Spring MVC 会稍微比 Struts2 快些。
  - Spring MVC 使用更加简洁,同时还支持 JSR303, 处理 ajax 的请求更方便
  - (JSR303 是一套 JavaBean 参数校验的标准，它定义了很多常用的校验注解，我们可以直接将这些注
    解加在我们 JavaBean 的属性上面，就可以在需要校验的时候进行校验了。)
  - Struts2 的 OGNL 表达式使页面的开发效率相比 Spring MVC 更高些，但执行效率并没有比 JSTL 提
    升，尤其是 struts2 的表单标签，远没有 html 执行效率高。

## 二 SpringMVC开发配置及组件

创建web项目，引入需要的jar包

**SpringMVC的jar包**

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>${spring.version}</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>${spring.version}</version>
</dependency>
```

### 2.1 在web.xml中配置核心控制器DispatcherServlet

```xml
<!-- 配置 spring mvc 的核心控制器 -->
    <servlet>
        <servlet-name>SpringMVCDispatcherServlet</servlet-name>
        <servlet-class>
            org.springframework.web.servlet.DispatcherServlet
        </servlet-class>
        <!-- 配置初始化参数，用于读取 SpringMVC 的配置文件 -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <!-- 配置 servlet 的对象的创建时间点：应用加载时创建。
        取值只能是非 0 正整数，表示启动顺序 -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVCDispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

### 2.2 创建 spring mvc  的配置文件

```xml
<!-- 开启注解扫描 -->
<context:component-scan base-package="com.yim"/>

<!-- 视图解析器对象 -->
<bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/pages/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```

### 2.3 Spring MVC中组件和处理流程

- DispatcherServlet：前端控制器
  - dispatcherServlet 是整个流程控制的中心，由它调用其它组件处理用户的请求
- HandlerMapping：处理映射器
  - HandlerMapping 负责根据用户请求找到 Handler 即处理器，SpringMVC 提供了不同的映射器实现不同的映射方式，例如：配置文件方式，实现接口方式，注解方式等。
- handler：处理器
  - 它就是我们开发中要编写的具体业务控制器。由 DispatcherServlet 把用户请求转发到 Handler。由
    Handler 对具体的用户请求进行处理。
- HandlAdapter：处理器适配器
  - 通过 HandlerAdapter 对处理器进行执行
- View Resolver ：视图解析器
  - View Resolver 负责将处理结果生成 View 视图，View Resolver 首先根据逻辑视图名解析成物理视图名
    即具体的页面地址，再生成 View 视图对象，最后对 View 进行渲染将处理结果通过页面展示给用户。
- View：视图
  - SpringMVC 框架提供了很多的 View 视图类型的支持，包括：jstlView、freemarkerView、pdfView
    等。我们最常用的视图就是 jsp。
  - 一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，需要由程序员根据业务需求开发具体的页面。

### 2.4  < mvc:annotation-driven> 说明

```xml
使 用 <mvc:annotation-driven> 自 动加载 RequestMappingHandlerMapping （处理映射器） 和
RequestMappingHandlerAdapter （ 处 理 适 配 器 ） ， 可 用 在 SpringMVC.xml 配 置 文 件 中 使 用<mvc:annotation-driven>替代注解处理器和适配器的配置。
  
<!-- 开启SpringMVC框架注解的支持 -->
<mvc:annotation-driven conversion-service="conversionService"/>
```

### 2.5 SpringMVC工作原理流程

1. 用户发送请求至前端控制器DispatcherServlet。
2. DispatcherServlet收到请求调用HandlerMapping处理器映射器。
3. 处理器映射器根据请求url找到具体的处理器，生成处理器对象及处理器拦截器(如果有则生成)一并返回给DispatcherServlet。
4. DispatcherServlet通过HandlerAdapter处理器适配器调用处理器。
5. 执行处理器(Controller，也叫后端控制器)。
6. Controller执行完成返回ModelAndView。
7. HandlerAdapter将controller执行结果ModelAndView返回给DispatcherServlet。
8. DispatcherServlet将ModelAndView传给ViewReslover视图解析器。
9. ViewReslover解析后返回具体View。
10. DispatcherServlet对View进行渲染视图（即将模型数据填充至视图中）。
11. DispatcherServlet响应用户

## 三 请求，响应参数和结果视图

### 3.1 请求参数绑定

1. **绑定机制**

   我们都知道，表单中请求参数都是基于 key=value 的。SpringMVC 绑定请求参数的过程是通过把表单提交请求参数，作为控制器中方法参数进行绑定的。

2. **支持的数据类型**
   1. 基本类型参数 ：包括基本类型和 String 类型
   2. POJO  类型参数 ：包括实体类，以及关联的实体类
   3. 数组和集合类型参数 ：包括 List 结构和 Map 结构的集合（包括数组）
3. **使用要求**
   1. 如果是基本类型或者 String  类型：要求我们的参数名称必须和控制器中方法的形参名称保持一致。(严格区分大小写)
   2. 如果是 POJO  类型 ，或者 它的关联对象 ：要求表单中参数名称和 POJO 类的属性名称保持一致。并且控制器方法的参数类型是 POJO 类型。
      1. 第一种：要求集合类型的请求参数必须在 POJO 中。在表单中请求参数名称要和 POJO 中集合属性名称相同。给 List 集合中的元素赋值，使用下标。给 Map 集合中的元素赋值，使用键值对。
      2. 第二种：接收的请求参数是 json 格式数据。需要借助一个注解实现。

### 3.2 请求参数乱码解决

（一）**针对POST请求方式**；在web.xml中配置一个过滤器

```xml
<!-- 配置 springMVC 编码过滤器 -->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>
            org.springframework.web.filter.CharacterEncodingFilter
        </filter-class>
        <!-- 设置过滤器中的属性值 -->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <!-- 启动过滤器 -->
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <!-- 过滤所有请求 -->
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

在 springmvc 的配置文件中可以配置，静态资源不过滤：
<!-- location 表示路径，mapping 表示文件，**表示该目录下的文件以及子目录的文件 -->
<mvc:resources location="/css/" mapping="/css/**"/>
<mvc:resources location="/images/" mapping="/images/**"/>
<mvc:resources location="/scripts/" mapping="/javascript/**"/>
```

（二)  **针对GET请求**：有两种方式

1. tomacat 对 GET和 POST 请求处理方式是不同的，GET请求的编码问题，要改 tomcat 的 server.xml配置文件

   ```xml
   <Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"/>
   改为：
   <Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"
   useBodyEncodingForURI="true"/>
   如果遇到 ajax 请求仍然乱码，请把：
   useBodyEncodingForURI="true"改为 URIEncoding="UTF-8"
   ```

2. 对请求参数进行重新编码

   ```java
   String  userName  =  New String(Request.getParameter(“userName”).getBytes(“ISO8859-1”), “utf-8”);
   ```

   

### 3.3 自定义数据类型转换器

除了以上数据类型外，存在特殊情况，如日期类型参数

1. 自定义转换类实现converter接口

   ```java
   public class StringToDateConverter implements Converter<String , Date> {
   
       /**
        * 用于把 String 类型转成日期类型
        */
       @Override
       public Date convert(String source) {
           DateFormat format = null;
           try {
               if(StringUtils.isEmpty(source)) {
                   throw new NullPointerException("请输入要转换的日期");
               }
               format = new SimpleDateFormat("yyyy-MM-dd");
               Date date = format.parse(source);
               return date;
           } catch (Exception e) {
               throw new RuntimeException("输入日期有误");
           }
       }
   }
   ```

2. spring配置文件中引入转换器

   ```xml
   <!-- 配置类型转换器工厂 -->
   <bean id="converterService"
   	class="org.springframework.context.support.ConversionServiceFactoryBean">
   	<!-- 给工厂注入一个新的类型转换器 -->
   	<property name="converters">
   		<array>
   		<!-- 配置自定义类型转换器 -->
   		<bean class="com.yim.utils.StringToDateConverter"></bean>
   		</array>
   	</property>
   </bean>
   ```

3. 在 annotation-driven  标签中引用配置的类型转换服务

   ```xml
   <!-- 开启SpringMVC框架注解的支持 -->
   <mvc:annotation-driven conversion-service="conversionService"/>
   ```

### 3.4 SpringMVC 对原生 ServletAPI 对象的支持

支持原始 ServletAPI 对象有：

- HttpServletRequest
- HttpServletResponse
- HttpSession
- java.security.Principal
- Locale
- InputStream
- OutputStream
- Reader
- Writer

我们可以把上述对象，直接写在控制的方法参数中使用。

### 3.5 常用注解

- **@RequestMapping**
  - **作用**：用于建立请求 URL 和处理请求方法之间的对应关系。
  - **属性**：
    - value：用于指定请求的 URL。它和 path 属性的作用是一样的。
    - method：用于指定请求的方式。
    - params：用于指定限制请求参数的条件。它支持简单的表达式。要求请求参数的 key 和 value 必须和配置的一模一样。
  - **出现**位置：
    - 类上：可以让URL 按照模块化管理，请求 URL 的第一级访问目录。
    - 方法上：请求 URL 的第二级访问目录。
- **@RequestParam**
  - **作用**：把请求中指定名称的参数给控制器中的形参赋值。
  - **属性**：value：请求参数中的名称。required：请求参数中是否必须提供此参数。默认值：true。表示必须提供，如果不提供将报错。
- **@RequestBody**
  - **作用**：用于获取请求体内容。直接使用得到是 key=value&key=value...结构的数据。get 请求方式不适用。
  - **属性**：required：是否必须有请求体。默认值是:true。当取值为 true 时,get 请求方式会报错。如果取值
    为 false，get 请求得到是 null。
- **@ResponseBody**
  - 作用：该注解用于将 Controller 的方法返回的对象，通过 HttpMessageConverter 接口转换为指定格式的数据如：json,xml 等，通过 Response 响应给客户端
- **@PathVaribale**
  - **作用**：用于绑定 url 中的占位符。例如：请求 url 中 /delete/{id}，这个{id}就是 url 占位符。
    url 支持占位符是 spring3.0 之后加入的。是 springmvc 支持 **rest 风格** URL 的一个重要标志。
  - **属性**：value：用于指定 url 中占位符名称。required：是否必须提供占位符。
- @RequestHeader
  - 作用：用于获取请求消息头。
  - 属性：value：提供消息头名称
    required：是否必须有此消息头
- @CookieValue
  - 作用：用于把指定 cookie 名称的值传入控制器方法参数。
    属性：value：指定 cookie 的名称。required：是否必须有此 cookie。
- **@ModelAttribute**
  - **作用**：该注解是 SpringMVC4.3 版本以后新加入的。它可以用于修饰方法和参数。
    - 出现在方法上，表示当前方法会在控制器的方法执行之前，先执行。它可以修饰没有返回值的方法，也可以修饰有具体返回值的方法。
    - 出现在参数上，获取指定的数据给参数赋值。
  - **属性**：
    value：用于获取数据的 key。key 可以是 POJO 的属性名称，也可以是 map 结构的 key。
  - **应用场景**：当表单提交数据不是完整的实体类数据时，保证没有提交数据的字段使用数据库对象原来的数据。例如：我们在编辑一个用户时，用户有一个创建信息字段，该字段的值是不允许被修改的。在提交表单数据是肯定没有此字段的内容，一旦更新会把该字段内容置为 null，此时就可以使用此注解解决问题。
- **@SessionAttribute**
  - **作用**：用于多次执行控制器方法间的参数共享。
  - **属性**：value：用于指定存入的属性名称。 type：用于指定存入的数据类型。

### 3.6 响应数据

#### 3.6.1 返回值类型

- 字符串：controller 方法返回字符串可以指定逻辑视图名，通过**视图解析器解析为物理视图地址**。

- void： Servlet 原始 API无需返回值情况

  - 使用 request 转向页面

    ```java
    request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,
    response);
    ```

  - 通过 response  页面重定向

    ```java
    response.sendRedirect("testRetrunString")
    ```

  - 通过 response  指定响应结果

    ```java
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write("json串);
    ```

- **ModelAndView**：ModelAndView 是 SpringMVC 为我们提供的一个对象，该对象也可以用作控制器方法的返回值。

  - ModelAndView对象使用如下：

  ```java
  @RequestMapping("/viewPage")
  public ModelAndView viewPage(Model model) {
  	ModelAndView modelView = new ModelAndView();
      // ModelAndView主要作用一：向视图页面传递参数，JSP可用EL表达式接受
  	modelView.addObject("deptTypeEnum", DeptTypeEnum.values());
      // Model接口，也可以向向视图页面传递参数
  	model.addAttribute("PayChannelsEnum", PayChannelsEnum.values());
  	model.addAttribute("province", departmentService.getProvinceCode());
  	model.addAttribute("city", departmentService.getCityCode());
      // 作用二 设置逻辑视图，ViewReslover解析前往视图
  	modelView.setViewName("/dept/deptRight");
  	return modelView;
  }
  
  public ModelAndView addObject(String attributeName, @Nullable Object attributeValue) {
      // ModelAndView和Model传递参数的方式通过ModelMap实现，ModelAndView需要实例化对象，Model
      // 通过实现类ExtendedModelMap对象
      this.getModelMap().addAttribute(attributeName, attributeValue);
      return this;
  }
  ```

#### 3.6.2 SpringMVC的转发和重定向

- 转发：

  ```java
  return "forward:/WEB-INF/pages/success.jsp";
  需要注意的是，如果用了formward：则路径必须写成实际视图 url，不能写逻辑视图。
  ```

- 重定向：

  ```java
  return "redirect:testReturnModelAndView";
  要注意的是，如果是重定向到jsp页面，则jsp页面不能写在WEB-INF目录中，否则无法找到。
  ```

### 3.7 REST

**REST**：即表述性状态传递（英文：Representational State Transfer，简称REST），
对请求的URL做了基于资源的设计 ，
对请求方式做了设置 用 GET   POST  PUT  DELETE 代表对应的操作。

**RESTful**：基于REST规范的应用程序就是RESTful

@PathVariable作用是将URI请求模板中的变量解析出来，映射到处理方法的参数上

## 四 SpringMVC实现文件上传

### 4.1 文件上传的前提条件

- form 表单的 enctype 取值必须是：multipart/form-data(默认值是:application/x-www-form-urlencoded)
  enctype:是表单请求正文的类型
- method 属性取值必须是 Post
- 提供一个文件选择域<input type="file" />

### 4.2 SpringMVC 传统方式的文件上传

传统方式的文件上传，指的是我们上传的文件和访问的应用存在于同一台服务器上。并且上传完成之后，浏览器可能跳转。

1. **引入文件上传依赖包**：

   ```xml
   <dependency>
       <groupId>commons-fileupload</groupId>
       <artifactId>commons-fileupload</artifactId>
       <version>1.3.3</version>
   </dependency>
   <dependency>
       <groupId>commons-io</groupId>
       <artifactId>commons-io</artifactId>
       <version>2.6</version>
   </dependency>
   ```

2. **配置文件上传解析器**

   ```xml
   <!-- 配置文件上传解析器 id固定 -->
   <bean id="multipartResolver"
         class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
   <!-- 设置上传文件的最大尺寸为 5MB -->
       <property name="maxUploadSize">
   	<value>5242880</value>
   	</property>
   </bean>
   ```

   > 注意：文件上传的解析器 id 是固定的，不能起别的名称，否则无法实现请求参数的绑定。（不光是文件，其他字段也将无法绑定）

3. **编写代码实现**

   ```java
   @RequestMapping("/fileUpload")
   public String fileUpload(MultipartFile uploadFile, HttpServletRequest request) throws IOException {
   
       // 1、获取原始文件名,截取扩展名
       String uploadFileName = uploadFile.getOriginalFilename();
       String extendName = uploadFileName.substring(uploadFileName.lastIndexOf("."));
       // 2、生成UUID
       String uuid = UUID.randomUUID().toString().replace("-", "");
       // 3、防止文件名重复，重新组成文件
       String fileName = uuid + extendName;
       // 4、设置文件保存目录
       String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
       String filePath = "D:\\review-demo\\springmvc\\" + datePath;
       File file = new File(filePath);
       if (!file.exists()) {
           file.mkdir();
       }
       // 5、使用 MulitpartFile 接口方法上传
       uploadFile.transferTo(new File(file,fileName));
       return "success";
   }
   ```

### 4.3 跨服务器方式的文件上传

在实际开发中，我们会有很多处理不同功能的服务器。例如：

- 应用服务器：负责部署我们的应用
- 数据库服务器：运行我们的数据库
- 缓存和消息服务器：负责处理大并发访问的缓存和消息
- 文件服务器：负责存储用户上传文件的服务器。

1. **创建另一个web项目用于存放文件**

   ```xml
   <servlet>
       <servlet-name>default</servlet-name>
       <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
       <init-param>
           <param-name>debug</param-name>
           <param-value>0</param-value>
       </init-param>
       <!--在文件服务器Tomcat的conf/web.xml，加以下内容：表示接收文件的目标服务器可以支持写入操作。-->
       <init-param>
           <param-name>readonly</param-name>
           <param-value>false</param-value>
       </init-param>
       <init-param>
           <param-name>listings</param-name>
           <param-value>false</param-value>
       </init-param>
       <load-on-startup>1</load-on-startup>
   </servlet>
   ```

2. **文件上传项目中加入以下jar包**

   ```xml
   <dependency>
       <groupId>commons-fileupload</groupId>
       <artifactId>commons-fileupload</artifactId>
       <version>1.3.3</version>
   </dependency>
   <dependency>
       <groupId>commons-io</groupId>
       <artifactId>commons-io</artifactId>
       <version>2.6</version>
   </dependency>
   <dependency>
       <groupId>com.sun.jersey</groupId>
       <artifactId>jersey-client</artifactId>
       <version>1.19.1</version>
   </dependency>
   <dependency>
       <groupId>com.sun.jersey</groupId>
       <artifactId>jersey-core</artifactId>
       <version>1.19.1</version>
   </dependency>
   ```

3. **同上：配置文件上传解析器**

4. **编写实现代码**

   ```java
   @Controller
   public class FileUploadController {
   
       public static final String FILESERVERURL = "http://localhost:9090/spring_image/uploads/";
   
       @RequestMapping("/fileUpload")
       public String fileUpload(MultipartFile uploadFile, HttpServletRequest request) throws IOException {
   
           // 1、获取原始文件名,截取扩展名
           String uploadFileName = uploadFile.getOriginalFilename();
           String extendName = uploadFileName.substring(uploadFileName.lastIndexOf("."));
           // 2、生成UUID
           String uuid = UUID.randomUUID().toString().replace("-", "");
           // 3、防止文件名重复，重新组成文件
           String fileName = uuid + extendName;
           // 4、创建 sun 公司提供的 jersey 包中的 Client 对象
           Client client = Client.create();
           // 5、指定上传文件的地址，该地址是 web 路径
           WebResource resource = client.resource(FILESERVERURL+fileName);
           // 6、实现上传
           String result = resource.put(String.class,uploadFile.getBytes());
           System.out.println(result);
           return "success";
       }
   }
   ```

## 五 SpringMVC 中的异常处理

**异常处理思路**：系统的 dao、service、controller 出现都通过 throws Exception 向上抛出，最后由 springmvc 前端控制器交由异常处理器进行异常处理

**实现流程**：

1. 自定义异常类和异常页面

   ```java
   public class UserDefinedException extends Exception {
       private String message;
       public UserDefinedException(String message) {
           this.message = message;
       }
       @Override
       public String getMessage() {
           return message;
       }
   }
   ```

2. 自定义异常处理器

   ```java
   public class UserDefinedExceptionResolver implements HandlerExceptionResolver {
       @Override
       public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response
               , Object handler, Exception ex) {
   
           ex.printStackTrace();
           UserDefinedException userDefinedException = null;
   
           //如果抛出的是系统自定义异常则直接转换
           if (ex instanceof UserDefinedException) {
               userDefinedException = (UserDefinedException) ex;
           } else {
               //如果抛出的不是系统自定义异常则重新构造一个系统错误异常。
               userDefinedException = new UserDefinedException("系统错误，请与系统管理 员联系！");
           }
           ModelAndView modelAndView = new ModelAndView();
           modelAndView.addObject("message", userDefinedException.getMessage());
           modelAndView.setViewName("error");
           return modelAndView;
       }
   }
   ```

3. 配置自定异常处理器

   ```xml
   <!-- 配置自定义异常处理器 -->
   <bean id="userDefinedExcceptionResolver" class="com.yim.excption.UserDefinedExcceptionResolver"/>
   ```

## 六 SpringMVC框架中的拦截器

### 6.1 概述

1. SpringMVC框架中的拦截器用于对处理器进行预处理和后处理的技术。

2. 可以定义拦截器链，连接器链就是将拦截器按着一定的顺序结成一条链，在访问被拦截的方法时，拦截器链
中的拦截器会按着定义的顺序执行。
3. 拦截器和过滤器的功能比较类似，有区别
1. 过滤器是Servlet规范的一部分，任何框架都可以使用过滤器技术。
2. 拦截器是SpringMVC框架独有的。
3. 过滤器配置了/*，可以拦截任何资源。
4. 拦截器只会对控制器中的方法进行拦截。
4. 拦截器也是AOP思想的一种实现方式
5. 想要自定义拦截器，需要实现HandlerInterceptor接口。

### 6.2 自定义拦截器

1. **编写实现类实现HandlerInterceptor接口**

2. **配置拦截器**

   ```xml
   <!-- 配置拦截器，可配置拦截范围，可配置多个拦截器 -->
   <mvc:interceptors>
       <mvc:interceptor>
           <mvc:mapping path="/**"/><!--  用于指定对拦截的 url -->	
           <mvc:exclude-mapping path=""/><!-- 用于指定排除的 url-->
           <bean id="userDefinedInterceptor"
                 class="com.yim.interceptor.UserDefinedInterceptor"></bean>
       </mvc:interceptor>
   </mvc:interceptors>
   ```

### 6.3 关于拦截器方法说明

**多个拦截器，调用顺序是按照在Spring配置文件中的配置顺序决定的**

```java
public interface HandlerInterceptor {
    
   /**
    *  如何调用： 按拦截器定义顺序调用
    *  何时调用：只要配置了都会调用
    *  有什么用：
    * 如果程序员决定该拦截器对请求进行拦截处理后还要调用其他的拦截器，或者是业务处理器去
    * 进行处理，则返回 true。如果程序员决定不需要再调用其他的组件去处理请求，则返回 false。
    */
    
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
        return true;
    }
    
    /**
    *  如何调用：按拦截器定义逆序调用
    *  何时调用：在拦截器链（定义了多个拦截器）内所有 preHandle 返成功才调用
    *  有什么用：
    * 在业务处理器处理完请求后，但是 DispatcherServlet 向客户端返回响应前被调用，在该方法中对用户请求 request 进行处理。
    */
    default void postHandle(HttpServletRequest  request,  HttpServletResponse response, Object handler,@Nullable ModelAndView modelAndView) throws Exception {
    }
   
    /**
    *  如何调用：按拦截器定义逆序调用
    *  何时调用：只有（当前）preHandle 返回 true 才调用
    *  有什么用：
    * 在 DispatcherServlet 完全处理完请求后被调用，可以在该方法中进行一些资源清理的操作。
    */
    default void afterCompletion(HttpServletRequest request, HttpServletResponseresponse, Object handler,@Nullable Exception ex) throws Exception {
    }
}
```

**多个拦截器执行顺序测试结果**：

![interceptor01](images/interceptor01.png)

**中断拦截器执行结果：**

![interceptor02](images/interceptor02.png)



