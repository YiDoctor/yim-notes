description: 内容提取自工作笔记
-----

## 账户信息 ##

深信Easyconnect下载地址:

[https://vpn.travelsky.com/com/installClient.html](https://vpn.travelsky.com/com/installClient.html)

## SVN的使用 ##
## MyEclipse2018.8 ##

MyEclipse2018.8破解参照我的网盘文件

破解之后JavaWeb项目无法创建

    安装目录\plugins\com.genuitec.eclipse.j2eedt.ui_13.0.0.me201501140726
    
    删除之前，你可以先把这两个文件备份，防止你的删了改了还不行的，我的就是这样解决的
    把j2eedtcore.jar删除，j2eedtcore.jar.bakXXXXXXX改为j2eedtcore.jar即可。

## 前后台时间显示问题 ##

**润乾报表开发流程,参照润乾报表开发文档**

查询当日,不显示数据问题

    date格式:yyyy-mm-dd
    前端js定义数据库显示时间的工具类
    Java定义时间显示格式的工具类
    
    var date = new Date();
    var a = date.getDate();
    console.log("===getDate===:"+a);
    var b = date.getDay();
    console.log("===getDay===:"+b);
    var c = date.getFullYear();
    console.log("===getFullYear===:"+c);
    var d = date.getHours();
    console.log("===getHours===:"+d);
    var e = date.getMilliseconds();
    console.log("===getMilliseconds===:"+e);
    var f = date.getMinutes();
    console.log("===getMinutes===:"+f);
    var g = date.getMonth();
    console.log("===getMonth===:"+g);
    var h = date.getSeconds();
    console.log("===getSeconds===:"+h);
    var i = date.getTime();
    console.log("===getTime===:"+i);
    var j = date.getTimezoneOffset();
    console.log("===getTimezoneOffset===:"+j);
    var k = date.getUTCDate();
    console.log("===getUTCDate===:"+k);
    var l = date.getUTCDay();
    console.log("===getUTCDay===:"+l);
    var m = date.getUTCFullYear();
    console.log("===getUTCFullYear===:"+m);
    var n = date.getYear();
    console.log("===getYear===:"+n);
    var o = date.toDateString();
    console.log("===toDateString===:"+o);
    var p = date.toISOString();
    console.log("===toISOString===:"+p);
    var q = date.toJSON();
    console.log("===toJSON===:"+q);
    var r = date.toLocaleDateString();
    console.log("===toLocaleDateString===:"+r);
    var s = date.toLocaleString();
    console.log("===toLocaleString===:"+s);
    var t = date.toLocaleTimeString();
    console.log("===toLocaleTimeString===:"+t);
    var u = date.toTimeString();
    console.log("===toTimeString===:"+u);

## 环境配置 Cordova打包H5 ##

参照:
    前端软件安装.md

## 项目现场培训总结 ##

1.准备工作

- 业务准备：对enesys系统，移动系统的数量掌握程度，必要条件

- 测试准备：系统环境的准备，必要条件

- 现场准备：提前到场，测试机械等硬件，-启动好环境，等待培训人员到场

2.讲解环节

- 事先准备好培训资料：按照资料大纲，开始执行操作流程

- 讲解大纲：讲解模块（顺序，繁简）

- 讲解流程：分模块讲解，按照业务流程，由宏-->微

- 阶段性问答: 模块讲解完成后的，交流回答

3.收尾环节

- 培训总结回顾
- 培训资料，联系方式给学员

## enesys项目中增加Viewbar的方式 ##

菜单列表显示关系表:ObjectRaletion BusinessType OBJECTDETAIL

然后Debug模式下查看btkey ContextManager


## Oracle数据库 时间格式 ##

to_data("",yyyy-mm-dd hh24:mi:ss)

like和eq,使用like作为查询条件当条件为空时可以获取所有数据

## 原有表新增字段值alter语句 ##

    sql语句 : 
    新增列: alter table 表名 add 新增字段 数据类型 default '默认值'
    
            alter table YONGQIDIZHI add yqdz_tsyh varchar2(1) default '1'
    
    删除列: ALTER TABLE 表名  DROP COLUMN  字段名 

## 项目实现热部署的方式 ##

方式一: 使用Jrebel插件 功能强大!最近使用中总觉的没起到作用!
方式二: SpringBoot项目使用 spring-boot-devtools依赖 只能运用于SpringBoot项目中
方式三: springLoaded.jar包 对Spring项目支持良好
IDEA自带: 在服务器配置中,on frame deactivation栏设置热启动

# HTTP : 400错误-请求无效 ##

在ajax请求后台数据时有时会报 HTTP 400 错误 - 请求无效 (Bad request);**出现这个请求无效报错说明请求没有进入到后台服务里；**

## 逻辑运算符 || 和 && ##

位运算符
位移运算符

## 移动项目H5前端JS分析 ##

自定义 serializeJSON()方法和Jquery自带的serialize()方法

找到插件serializeJSON插件，转换json格式

javascript: 是一个伪协议,使用可以防止页面跳转,后面跟着的是一段要执行的js代码!

### 获取radio的值 ###

1.获取选中值，三种方法都可以：
$('input:radio:checked').val()；
$("input[type='radio']:checked").val();
$("input[name='rd']:checked").val();

2.设置第一个Radio为选中值：
$('input:radio:first').attr('checked', 'checked');
或者
$('input:radio:first').attr('checked', 'true');
注：attr("checked",'checked')= attr("checked", 'true')= attr("checked", true)
3.设置最后一个Radio为选中值：
$('input:radio:last').attr('checked', 'checked');
或者
$('input:radio:last').attr('checked', 'true');
4.根据索引值设置任意一个radio为选中值：
$('input:radio').eq(索引值).attr('checked', 'true');索引值=0,1,2....
    
## 使用MyEclipse导入Enesys项目遇到的坑 ##

建议直接在项目在创建

    只有src和webroot目录的情况下
创建webProject项目，项目名为MainSource

## IE浏览器设置兼容性视图以及ActiveX控件 ##

## 数据库随机查询 ##

从mysql中随机获取十条数据
SELECT * FROM address WHERE id >= (SELECT floor(RAND() * (SELECT MAX(id) FROM address))) ORDER BY id LIMIT 0,10

Oracle中

    SELECT * FROM (SELECT * FROM T_USER ORDER BY DBMS_RANDOM.RANDOM()) WHERE RONUM <= 10

Mysql中

    SELECT * FROM T_USER  ORDER BY  RAND() LIMIT 10

## 项目启动无法远程连接数据库的问题 ##

eclipse有时在联网时会将ipv4转换为ipv6使用，导致报错：The Network Adapter could not establish the connection。

如下图所示，打开菜单windows–>preferences,在引用java时，添加参数

    -Djava.net.preferIPv4Stack=true 

即可解决问题。

![](https://i.imgur.com/bMeLhMK.png)

## JNDI配置数据源 ##

看网上有的content.xml中，但本公司非已此种方式

JNDI配置数据源的方式

在Tomcat中server.xml文件中配置

    <Context path="" docBase="项目绝对路径到WebRoot下" debug="0" sessionCookieName="enesyssessionCookie">
    	<Resource name="jdbc/dbpool" auth="Container" type="javax.sql.DataSource" driverClassName="oracle.jdbc.driver.OracleDriver" 
    			maxIdle="50" maxWait="100" 
    			username="用户名" password="密码" url="url地址配具体的库"
    			maxActive="50"/>
    </Context>

对比JDBC的方式 Connection Statement PreparedStatement ResultSet 

	1 配置连接池信息
	2 获取Properties对象
	3 获取getConnection连接
	4 编写sql
	5 获取结果
	6 释放资源

## 拷贝效率 ##
    数据类型转换小类型会自动转换成大的类型
    效率大小:System.arraycopy>clone>Arrays.copyOf>for
    System.arraycopy(浅拷贝),底层使用c或者c++实现

## 引用 ##

基本数据类型的值传递(call by value) 不能改变原有变量的内容和地址
引用数据类型的值传递(call by reference) 不能改变原变量的地址,但是可以改变内容

线程安全和线程非安全
    
    线程安全就是在多线程的环境下也不会出现数据不一致,而线程非安全就有可能出现数据不一致的情况,线程安全的因为做了许多资源控制的限制,所以效率上比较低于线程非安全的

@JsonIgnore

    在json序列化时,将Java bean中的一些属性忽略掉,序列化和反序列化都受影响


## Java中自定义注解的方式 ##

Java注解@Target @Retention @Doumented

    @Target说明了Annotation所修饰对象的范围
        
    @Retention定义Annotation被保留的时间长短
        取值（RetentionPoicy）有：
    　　　　1.SOURCE:在源文件中有效（即源文件保留）
    　　　　2.CLASS:在class文件中有效（即class保留）
    　　　　3.RUNTIME:在运行时有效（即运行时保留）
    @Documented 表明这个注释是由JavaDoc记录的

格式:

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface 注解名称{}

是由@interface自定义注解时,自动继承了Java.lang.annotation.Annotation接口
返回值类型就是参数的类型(返回值累就基本数据类型 String calss enum Annotation 以及以上所有类型的数组)

只能由public default这两个访问控制符

## JSON数据转换错误 ##

在enesys项目中,前端传入json数据出错,显示为类型转换异常的错误
错误原因是,Bean类中使用List<Bean> bean的参数

    Map<String,Object>() className =new HashMap<String,Object>();
    className.put("前台传入的参数集合名",对应的Bean.class);
    JSONObject josn = JSONObject.formObject();
    JSONObject.toBean(json,Bean.class,className);


## 最近在做报表遇到的一些问题 ##


1、UNION 和 UNION ALL 的使用

	用于合并结果集，在使用上存在细微差距，UNION ALL

2、case '字段' when '对应值' then '对应值显示结果' else '其他情况' end

	用于某一一段不同结果值，显示不同对应内容

3、查询结果添加序号的方式

    select
           rank() over(order by bm.BM_MC) 序号1,
           row_number() over(order by bm.BM_MC) 序号2,
           dense_rank() over(order by bm.BM_MC) 序号3,
           bm.BM_MC 子公司,
           khlx.DES 居民,
           sum(FYJL.FYJL_SL) 累计欠气,
           sum(fyjl.FYJL_YJE) 累计欠费
    from
         FEIYONG_JILU_YS fyjl
         left join bumen bm on bm.bm_id = substr(fyjl.FYJL_YYT,0,6)
         left join KHTYPE khlx on khlx.ID = fyjl.KH_LX
    where 1=1
    and fyjl.FYJL_ZT = '4'
    group by bm.BM_MC,khlx.DES;

4、获取一段时间内最新的那条数据

    1、使用max() min() 函数对数值，字符串，日期等类型的数据获取最大/最小值
    
    2、rownum的方式 limit

5、数据查询用到少见的组函数
	
    ceil(n) 取大于等于数值n的最小整数；
    
    floor(n)取小于等于数值n的最大整数

6、子查询值过多问题

	使用子查询时作为显示字段或者作为where条件查询字段，默认值查询一个最终结果值，如果存在多个，则会报值过多的错误

7、查询结果去重 DISTINCT（此去重方式将关键字放在select之后，去除完全重复的值）

    SELECT DISTINCT 列名称 FROM 表名称

## 分页查询 ##

oracle分页查询关键字rownum

	select * from ( select rownum row, * from table_name where row <= pageNum*pageSize) where row >= (pageNum-1)*pageSize

mssql分页查询关键字limit

## 使用UNION ALL ##

使用UNION ALL关联两个order by子句的查询会报错误，sql未正常结束。 