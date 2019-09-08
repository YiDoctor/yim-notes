# Quartz #

**Quartz是什么?** 

Quartz是一个任务调度框架,是完全由java开发的一个开源的任务日程管理系统

**Quartz特点**

- 强大的调度功能，例如支持丰富多样的调度方法，可以满足各种常规及特殊需求；
- 灵活的应用方式，例如支持任务和调度的多种组合方式，支持调度数据的多种存储方式；
- 分布式和集群能力。

## Quartz调度核心元素 ##

Scheduler（任务调度器

	实际执行任务调度的控制器。在spring中通过SchedulerFactoryBean封装起来
	一个调度容器中可以注册多个JobDetail和Trigger
Trigger（触发器）

	用于定义任务调度的时间规则(什么时候去调)，有SimpleTrigger,CronTrigger,DateIntervalTrigger和NthIncludedDayTrigger，
	其中CronTrigger用的比较多
	Calendar（特定时间点的集合） 
	
	一个trigger可以包含多个Calendar，以便排除或包含某些时间点
	JobDetail（表示一个具体的可执行的调度程序） 
	在spring中有JobDetailFactoryBean和 MethodInvokingJobDetailFactoryBean两种实现，如果任务调度只需要执行某个类的某个方
	法，就可以通过MethodInvokingJobDetailFactoryBean来调用Job（接口） 
	表示一个工作，要执行的具体内容。此接口中只有一个方法
	void execute(JobExecutionContext context)

## 工作原理 ##

![](https://i.imgur.com/EfUR402.png)

原理流程步骤说明：

	1：Scheduler是一个计划调度器容器（总部），容器里面可以放多个JobDetail和Trigger，当容器启动后，里面的每个JobDetail都会根
	据Trigger按部就班自动去执行。
	2：JobDetail是一个可执行的工作，它本身可能是有状态的。
	3：Trigger代表一个调度参数的配置，什么时候去调。
	4：当JobDetail和Trigger在scheduler容器上注册后，形成了装配好的作业（JobDetail和Trigger所组成的一对儿），就可以伴随容器
	启动而调度执行了
	5：Scheduler是个容器，容器中有一个线程池，用来并行调度执行每个作业，这样可以提高容器效率

## 整合spring步骤：

1：加载quartz的类库jar包

2：添加quartz的配置文件
	
	<?xml version="1.0" encoding="UTF-8"?>
	
	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
	        
	        <!-- 定时  -->
	        <bean id="nextVisitMessageJob" class="com.hjcrm.publics.task.NextVisitMessageJob"></bean>
	        
	        <bean id="nextVisitMessageTask" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
	            <property name="targetObject">
	                <ref bean="nextVisitMessageJob"/>
	            </property>
	            <property name="targetMethod">
	                <value>nextVisitMessage</value>
	            </property>
	        </bean>
	        <bean id="nextVisitMessageJobTrigger" class="org.springframework.scheduling.quartz.CronTriggerBean">
	            <property name="jobDetail">
	                <ref bean="nextVisitMessageTask"/>
	            </property>
	            <property name="cronExpression">
	                <value>0/1 * * * * ?</value>
	            </property>
	        </bean>
	        
	          <!-- 定时  -->
	        <bean id="nextVisitMessageJob1" class="com.hjcrm.publics.task.NextVisitMessageJob1"></bean>
	        
	        <bean id="nextVisitMessageTask1" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
	            <property name="targetObject">
	                <ref bean="nextVisitMessageJob1"/>
	            </property>
	            <property name="targetMethod">
	                <value>demo09Job</value>
	            </property>
	            
	        </bean>
	        <bean id="nextVisitMessageJobTrigger1" class="org.springframework.scheduling.quartz.CronTriggerBean">
	            <property name="jobDetail">
	                <ref bean="nextVisitMessageTask1"/>
	            </property>
	            <property name="cronExpression">
	                <value>0/1 * * * * ?</value>
	            </property>
	        </bean>


​	        
​	        
	        <!-- 总管理类 如果将lazy-init='false'那么容器启动就会执行调度程序  -->
	        <bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
	            <property name="triggers">
	                <list>
	                    <!-- <ref bean="nextVisitMessageJobTrigger"/>  -->
	                     <ref bean="nextVisitMessageJobTrigger1"/> 
	                </list>
	            </property>
	        </bean>
	<!-- 
	关于cronExpression的介绍:
	s m h d m w(?) y(?) ,分别对应: 秒>分>小时>日>月>周>年
	字段 允许值 允许的特殊字符
	秒 	  	0-59 	  	, - * /
	分 	  	0-59 	  	, - * /
	小时 	  	0-23 	  	, - * /
	日期 	  	1-31 	  	, - * ? / L W C
	月份 	  	1-12 或者 JAN-DEC 	  	, - * /
	星期 	  	1-7 或者 SUN-SAT 	  	, - * ? / L C #
	年（可选） 	  	留空, 1970-2099 	  	, - * /
	
	表达式
	 意义
	"0 0/1 * * * ?"     每1分钟触发
	"0 0 12 * * ?" 	  	每天中午12点触发
	"0 15 10 ? * *" 	  	每天上午10:15触发
	"0 15 10 * * ?" 	  	每天上午10:15触发
	"0 15 10 * * ? *" 	  	每天上午10:15触发
	"0 15 10 * * ? 2005" 	  	2005年的每天上午10:15触发
	"0 * 14 * * ?" 	  	在每天下午2点到下午2:59期间的每1分钟触发
	"0 0/5 14 * * ?" 	  	在每天下午2点到下午2:55期间的每5分钟触发
	"0 0/5 14,18 * * ?" 	  	在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
	"0 0-5 14 * * ?" 	  	在每天下午2点到下午2:05期间的每1分钟触发
	"0 10,44 14 ? 3 WED" 	  	每年三月的星期三的下午2:10和2:44触发
	"0 15 10 ? * MON-FRI" 	  	周一至周五的上午10:15触发
	"0 15 10 15 * ?" 	  	每月15日上午10:15触发
	"0 15 10 L * ?" 	  	每月最后一日的上午10:15触发
	"0 15 10 ? * 6L" 	  	每月的最后一个星期五上午10:15触发 
	"0 15 10 ? * 6L 2002-2005" 	  	2002年至2005年的每月的最后一个星期五上午10:15触发
	"0 15 10 ? * 6#3" 	  	每月的第三个星期五上午10:15触发
	每天早上6点
	
	0 6 * * * 
	
	每两个小时
	
	0 */2 * * *
	晚上11点到早上8点之间每两个小时，早上八点
	
	0 23-7/2，8 * * *
	
	每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点
	
	0 11 4 * 1-3
	1月1日早上4点
	
	0 4 1 1 *
	 -->
	</beans>

3：spring配置文件中，引用quartz的配置文件

	<import resource="classpath:applicationContext-quartz.xml"/>