# Servlet和JSP #

**B/S**

![](https://i.imgur.com/KWbH2So.png)

## Servlet ##

![](https://i.imgur.com/SUV9jW5.png)	
### Servlet编写 ###

	案例: 
	public class Servlet1 extends HttpServlet {
	    protected void service(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        response.setContentType("text/html;charset=utf-8");
	        PrintWriter pw = response.getWriter();
	        pw.append("<h1>我们的第一个servlet</h1>");
	    }
	}
### xml中配置Servlet ###

	步骤: 
	1.  将Servlet 配置到web.xml中, 并起别名
	    <servlet>
	        <servlet-name>别名</servlet-name>
	        <servlet-class>类的全称(包名.类名)</servlet-class>
	    </servlet>
	2.  使用Servlet别名, 映射一个或多个网址
	    <servlet-mapping>
	        <servlet-name>别名</servlet-name>
	        <url-pattern>/映射地址</url-pattern>
	    </servlet-mapping>  
	    .....
	    <servlet-mapping>
	        <servlet-name>别名</servlet-name>
	        <url-pattern>/映射地址2</url-pattern>
	    </servlet-mapping>  
	
	    映射完毕后, 访问此servlet的方式: 
	        http://ip地址:端口号/项目名/映射地址
	案例: 
	    <servlet>
	        <servlet-name>s1</servlet-name>
	        <servlet-class>cn.xdl.demo.Servlet1</servlet-class>
	      </servlet>
	      <servlet-mapping>
	        <servlet-name>s1</servlet-name>
	        <url-pattern>/666.php</url-pattern>
	      </servlet-mapping>
### Servlet生命周期 ###

生命周期 在Servlet中, 存在三个方法的体现:

- init方法  :    当此方法被调用时, 表示对象已创建, 且初始化完成! 我们常在此方法中初始化 一些资源 !  
-  service方法   :   当被调用时, 表示来了一个客户端的请求, 此方法每次都执行在一个新的线程中 !
-  destroy方法:   当此方法被调用时,表示Servlet即将被销毁 ! 我们常在此方法中 释放资源 !

### 乱码问题 ###

**1、请求时乱码**
方式一:可适用于所有问题 引起的乱码

    原理:  将乱码的文字, 重新变为字节数组, 再按照我们支持的编码重新组装 !
    步骤1.    将接收到的文字, 按照ISO-8859-1编码打碎成字节数组
        byte[] b = 乱码文字.getBytes("ISO-8859-1");
    步骤2.    将打碎后的字节数组, 按照UTF-8组装
        String text = new String(b,"UTF-8");
    简化: String text = new String(乱码文字.getBytes("ISO-8859-1"),"UTF-8");
方式二:    只能解决POST乱码问题, 用于在获取数据之前, 设置请求体的编码格式

    request.setCharacterEncoding("UTF-8");
**2、响应时乱码**

在处理响应时数据时, 无论时get/post请求, 方式都一致 !  一定要在获取输出流之前!
方式一:

    设置网页内容类型 , 并设置网页编码 , 不建议使用
    response.setContentType("text/html;charset=utf-8"); 

方式二:

    设置文字编码为UTF-8
    response.setCharacterEncoding("UTF-8");
### 请求转发和重定向 ###

![](https://i.imgur.com/HUiEmll.png)

### Cookie和Session ###

![](https://i.imgur.com/SSwzCtl.png)

1、Cookie

常用方法:

	如何将Cookie设置到响应的消息头: 
	    response.addCookie(Cookie cookie);
	如何从请求的消息头中获取Cookie:
	因为服务器可以给浏览器多个Cookie, 所以得到的是一个Cookie数组
	Cookie[] cookies = request.getCookies();
2、Session

常用方法:
(session是JSP四大域对象之一)
1.  设置属性:   setAttribute(String key,Object value);
2.  获取属性:   Object value = getAttribute(String key);
3.  删除属性:   removeAttribute(String key);
4.  销毁Session:  invalidate();

3、设置Session存活时长:

方式一: 修改所有的session默认时长

    修改tomcat目录下的conf文件夹下的 web.xml
    <session-config>
        <session-timeout>数字类型:  分钟</session-timeout>
    </session-config>

方式二:修改单个session存活时长:

    使用session对象, 调用setMaxInactiveInterval(int 秒);

## JSP ##

![](https://i.imgur.com/NiTYq23.png)

JSP即是Java Server Pages的简称

### JSP三大指令 ###

1、page指令

用来快速的描述JSP页面的信息, 用来对JSP页面进行配置: 
常用属性: 

	<%@page language=“script language”|--   语言 ,一般为java
	    extends=“className”  |--可以指定JSP在生成Java文件时, 文件所继承的Java类
	    *import=“importList”  |--导包列表 ,可以在参数中导入多个包, 多个包之间使用逗号隔开
	    buffer=“none|kb size”|   --none:不缓冲，默认8k
	    session=“true|false” |   --是否可以使用session，默认true
	    autoFlush=“true|false”  --缓冲器是否自动清除，默认true
	    isThreadSafe=“true|false”|--当前JSP代码执行区是否为线程安全的
	    *errorPage=“errorPageUrl” |--指定产生错误后, 处理错误的页面
	    *isErrorPage=“true|false” |--表示当前页面是否为处理错误的页面
	    contentType=“contentTyepInfo”|--描述网页的内容类型 和 编码格式
	%>

导包案例: 

    <%@page import="java.util.HashMap,java.util.ArrayList" %>

错误页面处理: 

    在产生错误的页面中加入page指令, errorPage属性中指定错误处理页面 
    在错误处理的jsp文件中, 指定isErrorPage为true , 可以直接操作一个叫做exception的对象
    
    在操作exception时, 注意有可能为null(当用户直接访问错误页面时)
2、处理项目中的错误码

使用上述的page指令, 无法处理404错误, 只有当程序出现BUG时, 才可以处理异常 !

想要处理404异常, 需要告知tomcat , 当在本项目中用户访问出现404以后, 跳转到指定页面: 

**在项目的web.xml中添加如下节点: **

    <error-page>
        <error-code>404</error-code>
        <location>/error.jsp</location>
    </error-page>
    <error-page>
        <error-code>500</error-code>
        <location>/error.jsp</location>
    </error-page>

3、include指令

用来在一个JSP文件中, 引入另一个JSP程序 或 HTML文件 !

格式: 

    <%@include file="文件路径" %>

JSP文件 在引擎转换时, 会将引入的文件代码复制粘贴到include指令的位置, 然后统一进行转换与编译操作(转换为一个Java文件)!

4、include动作

用于在一个JSP文件中, 引入另一个JSP程序 或 HTML文件 !
格式: 

    <jsp:include page="文件地址" flush="true"/>
include动作引入的JSP文件 , 在转换为.java的时候, 是单独转换的 ,只有在代码执行到include动作时, 才会编译被引入的文件, 动态载入!
5、taglib指令

### JSP内置对象 ###

|隐含对象|类型|作用|
|-------|-------- | ----- |
|out|JSPWriter|用来向客户端进行字符的响应|
|request|HttpServletRequest| 请求对象, 包含Http协议中请求数据|
|response| HttpServletResponse|响应对象|
|session| HttpSession|会话对象, 用来在一次会话中对单个用户 进行数据的共享与会话的跟踪|
|application| ServletContext|Servlet上下文 , 每个应用在启动时会创建且只创建一个对象, 在应用关闭时,对象被释放. 用来在当前项目中Servlet之间进行数据的共享与通信 |
|pageContext | PageContext|页面的上下文, 用于在当前页面进行数据的共享 , 在设计9大内置对象时, 将所有其他内置对象封装在了PageContext中 |
|config| ServletConfig|配置对象|
|page| Object|当前页面本身|
|exception| Throwable|当page指令中 isErrorPage为true时 才会产生此对象 , 用于收集其他页面产生的异常信息|

### 四大域对象 ###

![](https://i.imgur.com/axFwqlb.png)

### EL和JSTL ###

![](https://i.imgur.com/71bHVWK.png)

### Filter&Listener组件 ###

![](https://i.imgur.com/QtFizh9.png)

**在web.xml文件中配置Filter组件和Servlet配置基本一致**

**不使用注解在web.xml中配置Listener**

	<listener >
	     <listener-class>包名 + 类名</listener-class>
	</listener>

AJAX
---

![](https://i.imgur.com/iyYRIHl.png)

### AJAX优点 ###

作用:(1)异步请求和响应处理(2)页面局部刷新处理

优点:

- 减少页面整体刷新，提升用户体验(局部刷新)
- 减少交互的数据量，提升程序性能 
- 以异步模式处理请求，较少用户操作等待时间，增 强了用户的操作连续性(异步交互) 

### AJAX技术应用步骤 ###

- 创建XMLHttpRequest对象 
- 调用open方法，创建请求 
- 设定onreadystatechange事件函数，用于 响应处理 
- 调用send方法发送请求 

		// 发送请求之前的准备 
		对象.open(请求方式,请求路径,是否异步)  如    xhr.open('get','getDate.do',true);
		// 注册请求处理函数 
		xhr.onreadystatechange=function(){
		// 请求响应完成  并且  http 状态 是 200
			if(xhr.readyState == 4 && xhr.status==200){
		// 获取请求响应的数据
				var text = xhr.responseText;
			} 
		};  
		// 发送请求 
		xhr.send(null); 
		
### AJAX+Json应用 ###

**Json**

- JSON （JavaScript   Object  Notation）是 JavaScript语言中的一种对象类型。 
- JSON的好处是易于阅读和JavaScript解析 
- 当客户端和服务器端交互大量数据时，使用JSON 格式传输可以简化客户端和服务器端的编程 

### Jquery和AJAX ###

jQuery对ajax请求的创建、发送和响应都进  行了相应的封装，同时也考虑 了浏览器的兼容性问题 

1、$.ajax()函数

	$.ajax({     
		url ：请求 地址,     
		type ：请求 方式,     
		async ：默认 true  false 表示同步 请求,     
		data ：请求 参数,     
		dataType ：服务器返回的 数据类型,      
		success ： 服务器 正常 处理时执行的 回调函 数,     
		error ： 服务器处理出错时执行的 回调函 数, 
	}); 
2、$.get()函数

	$.get(url，data，function，datatype)  
		url：请求地址  
		data：请求参数  
		dataType：服务器返回的数据类型  
		function：服务器正常处理时执行的回调函数 
3 $.post()函数

 	$.post(url，data，function，datatype)  
	 	url：请求地址  
	 	data：请求参数  
	 	dataType：服务器返回的数据类型  
	 	function：服务器正常处理时执行的回调函数 
4、$.getJSON函数 

	$.getJSON(url，data，function)  
		url：请求地址  
		data：请求参数  
		function：服务器正常处理时执行的回调函数 
5、load()函数 

	$obj.load(url，data，function)  
	url：请求地址  
	data：请求参数  
	function：ajax请求处理完毕后执行的回调函数 功能：利用ajax发送一个url请求，将返回结果填充到$obj元素中 （常用于页面引入)


## 面试考点：XML、JSP ##


1、Servlet和JSP的区别?
	
	1 Servlet是一个运行在服务器上的Java类,依靠服务器支持向浏览器传输数据
	2 JSP本质上是Servlet的一种简易形式,可以简化页面的生成
	3 Servlet的应用逻辑在Java文件中,并且完全从HTML分离而来,JSP则是Java和HTML组合成一个扩展名为.jsp文件
	4 JSP侧重于视图,而Servlet侧重于控制逻辑,在MVC架构模式中,JSP适合充当视图View,Servlet适合充当控制器Controller
2、简述Servlet生命周期
	
	1 init() 初始化
	2 service() 处理客户端请求
	3 destroy() 销毁方法

3、如何现实servlet的单线程模式？

	<%@ page isThreadSafe="false" %>
4、请求转发(forward)和重定向(redirect)的区别?  <!-- more -->
	
	1：效率上
	转发（forward） > 重定向（redirect）
	2：显示上
	重定向（redirect）：显示新的URL
	转发（forward）：地址栏不变
	3：数据上
	转发（forward）：可以共享request里面的数据
	重定向（redirect）：不能
	4：请求次数
	重定向（redirect）是两次
	转发（forward）是一次
5、get请求和post请求的区别?
	
	GET：
	1：从服务器上获取数据，一般不能使用在写操作接口
	2：由URL所限制，GET方式传输的数据大小有所限制，传送的数据量不超过2KB
	3：请求的数据会附加在URL之后，以？分隔URL和传输数据，多个参数用&连接
	4：安全性差
	POST:
	1：向服务器提交数据,一般处理写业务
	2：POST方式传送的数据量比较大，一般被默认为没有限制
	3：安全性高
	4：请的求的数据内容放置在HTML HEADER中
6、JSP的9个内置对象和4个作用域?
	
	（1）9 个内置对象： 
	1 request：封装客户端的请求，其中包含来自 GET 或 POST 请求的参数； 
	2 application：封装服务器运行环境的对象； 
	3 response：封装服务器对客户端的响应；
	4 session：封装用户会话的对象； 
	5 page：JSP 页面本身（相当于 Java 程序中的 this）；
	6 config：Web 应用的配置对象； 
	7 exception：封装页面抛出异常的对象；
	8 pageContext：通过该对象可以获取其他对象；
	9 out：用来传送回应的输出； 
	（2）4 个域对象 
	1 page（当前页面）
	2 request（一次请求） 
	3 session（ 一次会话）
	4 application （整个会话）
7、会话跟踪技术有哪些?
	
	1 由于 HTTP 协议本身是无状态的，服务器为了区分不同的用户，就需要对用户会话进行跟踪，简单的说就是 为用户进行
	登记，为用户分配唯一的 ID，下一次用户在请求中包含此 ID，服务器根据此判断到底是哪一个用户。 
	
	2 URL 重写：在 URL 中添加会话信息作为请求的参数，或者将唯一的会话 ID 添加到 URL 结尾，以表示一个 会话； 
	
	3 设置表单隐藏域：将和会话跟踪相关的字段添加到隐藏域中，这些信息不会在浏览器显示，但是提交表 单时会提交给服务器；
	
	4 cookie：cookie 有两种，一种是基于窗口的，浏览器关闭后，cookie 就没有了；另一种是将信息存储在一 
	个临时文件中，并设置其有效路径和最大存活时间。当用户通过浏览器和服务器建立一次会话后，会话ID就会随相应信息储存
	在基于窗口的 cookie 中，那就意味着只要浏览器没有关闭，会话没有超时，下一次请求时这个会话ID 
	又会提交给服务器，让服务器识别用户身份。在使用 cookie 时要注意几点：首先不要在 cookie 中存放敏 感信息；
	其次 cookie 存储的数据量有限（4k），不能将过多的内容存储 cookie 中；再者浏览器通常只允许一个站点最多存
	放 20 个 cookie。当然，和用户会话相关的其他信息（除了会话 ID）也可以存在 cookie 方便进行会话 跟踪；
	
	5 HttpSession：在所有会话跟踪技术中，HttpSession 对象是最强大也是功能最多的。当一个用户第一次访 问某个
	网站时会自动创建 HttpSession，每个用户可以访问他自己的 HttpSession。可以通过 HttpServletRequest对象的
	getSession 方法获得 HttpSession，通过 HttpSession 的 setAttribute 方法可以将一个值放在 HttpSession
	中，通 过调用 HttpSession 对象的 getAttribute 方法，同时传入属性名就可以获取保存在 HttpSession 中的对
	象。与上面三 种方式不同的是，HttpSession 放在服务器的内存中，因此不要将过大的对象放在里面，即使目前的 
	Servlet 容器 可以在内存将满时将 HttpSession 中的对象移到其他存储设备中，但是这样势必影响性能。添加到 
	HttpSession 中 的值可以是任意 Java 对象，这个对象最好实现了 Serializable 接口，这样 Servlet 容器在必
	要的时候可以将其序列 化到文件中，否则在序列化时就会出现异常。 
8、什么是Web Service?

	1 WebService 就是一个应用程序，它向外界暴露出一个能够通过 Web 进行调用的 API。 
	2 它是基于 HTTP 协议传输数据，这使得运行在不同机上的不同应用程序，无须借助附加的、专门的第三方 软件或硬件，就可以相互交换数据或集成。 
9、request的主要方法?
	
	setAttribute(String name,Object)：设置名字为name的request的参数值
	getAttribute(String name)：返回由name指定的属性值
	getAttributeNames()：返回request对象所有属性的名字集合，结果是一个枚举的实例
	getCookies()：返回客户端的所有Cookie对象，结果是一个Cookie数组
	getCharacterEncoding()：返回请求中的字符编码方式
	getContentLength()：返回请求的Body的长度
	getHeader(String name)：获得HTTP协议定义的文件头信息
	getHeaders(String name)：返回指定名字的request Header的所有值，结果是一个枚举的实例
	getHeaderNames()：返回所以request Header的名字，结果是一个枚举的实例
	getInputStream()：返回请求的输入流，用于获得请求中的数据 getMethod()：获得客户端向服务器端传送数据的方法
	getParameter(String name)：获得客户端传送给服务器端的有name指定的参数值
	getParameterNames()：获得客户端传送给服务器端的所有参数的名字，结果是一个枚举的实例
	getParameterValues(String name)：获得有name指定的参数的所有值
	getProtocol()：获取客户端向服务器端传送数据所依据的协议名称
	getQueryString()：获得查询字符串
	getRequestURI()：获取发出请求字符串的客户端地址
	getRemoteAddr()：获取客户端的IP地址
	getRemoteHost()：获取客户端的名字
	getSession([Boolean create])：返回和请求相关
	Session getServerName()：获取服务器的名字
	getServletPath()：获取客户端所请求的脚本文件的路径
	getServerPort()：获取服务器的端口号
	removeAttribute(String name)：删除请求中的一个属性
10、用户在浏览器中输入URL之后，发什么了什么？写出请求和响应的流程
	1：域名解析
	2：TCP三次握手
	3：浏览器向服务器发送http请求
	4：浏览器发送请求头信息
	5：服务器处理请求
	6：服务器做出应答
	7：服务器发送应答头信息
	8：服务器发送数据
	9：TCP连接关闭

11、Servlet中如何获取用户提交的查询参数或者表单数据
	
	1 HttpServletRequest 的 getParameter（）方法。
	2 HttpServletRequest 的 getParameterValues（）方法。
	3 HttpServletRequest 的 getParameterMap（）方法。