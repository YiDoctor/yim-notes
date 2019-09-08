# HTTP网络协议 #

![](https://i.imgur.com/dqd8AZY.png)

Http协议是基于TCP/IP的超文本传输协议，属于应用层的面向对象的协议，工作与客户端--服务器架构之上

**1、Http协议的特点**

**2、Http的URL，以及URL和URI区别**

URL：统一资源定位符

URI：统一资源标识符

**3、Http的请求（Request）和响应（Response）**

Request请求：请求行（Request Line）、请求头（Header）、请求空行、请求体	

Response响应：响应头、响应体、响应行

**4、Http响应状态码**

	1**：信息性状态码
	2**：成功状态码
	    200：请求正常成功
	    204：指示请求成功但没有返回新信息
	    206：指示服务器已完成对资源的部分 GET 请求
	3**：重定向状态码
	    301：永久性重定向
	    302：临时性重定向
	    304：服务器端允许请求访问资源，但未满足条件
	4**：客户端错误状态码
	    400：请求报文中存在语法错误
	    401：发送的请求需要有通过HTTP认证的认证信息
	    403：对请求资源的访问被服务器拒绝了
	    404：服务器上无法找到请求的资源
	5**：服务器错误状态码
	    500：服务器端在执行请求时发生了错误
	    503：服务器暂时处于超负载或正在进行停机维护，现在无法处理请求

**5、Http的请求方法（了解）**
	
	GET     请求指定的页面信息，并返回实体主体。
	
	HEAD     类似于get请求，只不过返回的响应中没有具体的内容，用于获取报头
	
	POST     向指定资源提交数据进行处理请求（例如提交表单或者上传文件）。
	数据被包含在请求体中。POST请求可能会导致新的资源的建立和/或已有资源的修改。
	
	PUT     从客户端向服务器传送的数据取代指定的文档的内容。
	
	DELETE      请求服务器删除指定的页面。
	
	CONNECT     HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器。
	
	OPTIONS     允许客户端查看服务器的性能。
	
	TRACE     回显服务器收到的请求，主要用于测试或诊断。

**6、GET和POST请求**

（1）GET 请求的数据会附在 URL 之后（就是把数据放置在 HTTP 协议头中），以?分割 URL 和传输数据，参数之间
以&相连

（2）GET请求只能提交少量数据，是因为浏览器。操作系统进行了现在限制。而POST并未存在此种限制

（3）GET是向服务器索取数据的一种请求，而POST是向服务器提交数据的一种请求

（4）GET数据格式为字符串、POST可以是任意数据格式

（5）安全性低于POST，由于GET请求数据内容是放在URL后面，提交的重要数据保存在缓存中，容易被获取。 此外GET提交数据还可能会造成 Cross-site request forgery 攻击

**7、完整的HTTP请求过程**

	1、域名解析
	2、建立连接（TCP三次握手）
	3、浏览器向服务器发送Http请求
	4、浏览器发送请求头信息
	5、服务器处理请求
	6、服务器做出应答
	7、服务器发送应答头信息
	8、服务器发送数据
	9、TCP连接关闭

**8、转发和重定向**

![](https://i.imgur.com/HUiEmll.png)

**9、Cookis和Session**

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

10、HTTPS

HTTP协议传输的数据都是未加密的，也就是明文的，因此使用HTTP协议传输隐私信息非常不安全，为了保证这些隐私数据能加密传输，于是网景公司设计了SSL（Secure Sockets Layer）协议用于对HTTP协议传输的数据进行加密，从而就诞生了HTTPS。简单来说，HTTPS协议是由SSL+HTTP协议构建的可进行加密传输、身份认证的网络协议，要比http协议安全。

　　HTTPS和HTTP的区别主要如下：

　　1、https协议需要到ca申请证书，一般免费证书较少，因而需要一定费用。

　　2、http是超文本传输协议，信息是明文传输，https则是具有安全性的ssl加密传输协议。

　　3、http和https使用的是完全不同的连接方式，用的端口也不一样，前者是80，后者是443。

　　4、http的连接很简单，是无状态的；HTTPS协议是由SSL+HTTP协议构建的可进行加密传输、身份认证的网络协议，比http协议安全。

11、Http2

	新的二进制格式
	多路复用
	Header压缩
	服务器推送
	Stream