# SpringCloud #

**理解什么是SpringCloud？**

Spring Cloud是一个基于Spring Boot实现 ，Spring Cloud关注全局的服务治理框架 。

**主要框架？**

- 服务发现——Netflix Eureka
- 服务调用——Netflix Feign
- 熔断器——Netflix Hystrix
- 服务网关——Netflix Zuul
- 分布式配置——Spring Cloud Config
- 消息总线 —— Spring Cloud Bus 

**Spring Cloud和Dubbo对比 ?**

Dubbo只是实现了服务治理，而Spring Cloud下面有17个子项目（可能还会新增）分别覆盖了微服务架构下的方
方面面，服务治理只是其中的一个方面，一定程度来说，Dubbo只是Spring Cloud Netflix中的一个子集。 

## 一 服务发现——Netflix Eureka

Eureka是Netflix开发的服务发现框架，SpringCloud将它集成在自己的子项目 spring-cloud-netflix 中，实现SpringCloud的服务发现功能。Eureka包含两个组件：Eureka Server和Eureka Client。 

Eureka Server之间通过复制的方式完成数据的同步，Eureka还提供了客户端缓存机制，即使所有的Eureka Server都挂掉，客户端依然可以利用缓存中的信息消费其他服务的API。综上，Eureka通过心跳检查、客户端缓存等机制，确保了系统的高可用性、灵活性和可伸缩性。 

### 1.1 Eureka服务端开发

#### 1.1.1 在父工程定义 SpringCloud版本

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>Finchley.SR1</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

#### 1.1.2 在Eureka项目中引入服务依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

#### 1.1.3 配置文件application.yml 添加配置内容

```yml
server:
  port: ${PORT:50101} #服务端口
spring:
  application:
    name: xc-govern-center #指定服务名
eureka:
  client:
    registerWithEureka: true #服务注册，是否将自己注册到Eureka服务中
    fetchRegistry: true #服务发现，是否从Eureka中获取注册信息
    serviceUrl: #Eureka客户端与Eureka服务端的交互地址，高可用状态配置对方的地址，单机状态配置自己（如果不配置则默认本机8761端口）
      defaultZone: ${EUREKA_SERVER:http://eureka02:50102/eureka/}
  server:
    enable-self-preservation: false #是否开启自我保护模式
    eviction-interval-timer-in-ms: 60000 #服务注册表清理间隔（单位毫秒，默认是60*1000）
  instance:
    hostname: ${EUREKA_DOMAIN:eureka01}
```

#### 1.1.4 启动类添加注解，启动并访问服务地址

```
@EnableEurekaServer
```

### 1.2 微服务客户端开发

#### 1.2.1 添加客户端依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐starter‐netflix‐eureka‐
        client</artifactId>
</dependency>
```

#### 1.2.2 配置配置文件内容

```yml
eureka:
  client:
    service‐url:
      defaultZone: http://localhost:6868/eureka
  instance:
    prefer‐ip‐address: true
```

#### 1.2.3 启动类添加客户端注解

```
@EnableEurekaClient
```

### 1.3 保护模式

​        Eureka Server在运行期间，会统计心跳失败的比例在15分钟之内是否低于85%，如果出现低于的情况（在单机调试的时候很容易满足，实际在生产环境上通常是由于网络不稳定导致），Eureka Server会将当前的实例注册信息保护起来，同时提示警告。保护模式主要用于一组客户端和Eureka Server之间存在网络分区场景下的保护。一旦进入保护模式，Eureka Server将会尝试保护其服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。 

## 二 Feign实现服务间的调用 

​		Feign是简化Java HTTP客户端开发的工具（java-to-httpclient-binder），它的灵感来自于Retrofit、JAXRS-2.0和WebSocket。Feign的初衷是降低统一绑定Denominator到HTTP API的复杂度，不区分是否为restful。 

### 2.1 基本使用

- 导入依赖

  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring‐cloud‐starter‐openfeign</artifactId>
  </dependency>
  ```

- 添加启动类注解

  ```
  @EnableDiscoveryClient
  @EnableFeignClients
  ```

- 添加接口

  ```java
  @FeignClient("tensquare‐base")
  public interface LabelClient {
      @RequestMapping(value="/label/{id}", method = RequestMethod.GET)
      public Result findById(@PathVariable("id") String id);
  }
  ```

  @FeignClient注解用于指定从哪个服务中调用功能 ，注意 里面的名称与被调用的服务名保持一致，并且不能包含下划线。
  @RequestMapping注解用于对被调用的微服务进行地址映射。注意 @PathVariable注解一定要指定参数名称，否则出错 

**注意点:**

SpringCloud对Feign进行了增强兼容了SpringMVC的注解 ，我们在使用SpringMVC的注解时需要注意：

1. feignClient接口 有参数在参数必须加@PathVariable("XXX")和@RequestParam("XXX")feignClient返回值为复杂对象时其类型必须有无参构造函数。 
2. feignClient返回值为复杂对象时其类型必须有无参构造函数。 

### 2.2 负载均衡

Ribbon是Netflix公司开源的一个负载均衡的项目（https://github.com/Netflix/ribbon），它是一个基于HTTP、
TCP的客户端负载均衡器。 

Spring Cloud引入Ribbon配合 restTemplate 实现客户端负载均衡。Java中远程调用的技术有很多，如：
webservice、socket、rmi、Apache HttpClient、OkHttp等，互联网项目使用基于http的客户端较多，本项目使
用OkHttp。 

**可开启多个 tensquare‐base 服务进行测试负载均衡测试。**

#### 2.2.1 配置Ribbon参数

```properties
ribbon:
MaxAutoRetries: 2 #最大重试次数，当Eureka中可以找到服务，但是服务连不上时将会重试
MaxAutoRetriesNextServer: 3 #切换实例的重试次数
OkToRetryOnAllOperations: false #对所有操作请求都进行重试，如果是get则可以，如果是post，put等操作
没有实现幂等的情况下是很危险的,所以设置为false
ConnectTimeout: 5000 #请求连接的超时时间
ReadTimeout: 6000 #请求处理的超时时间
```

## 三 熔断器Hystrix 

### 3.1 为什么要使用熔断器 ?

在微服务架构中通常会有多个服务层调用，基础服务的故障可能会导致级联故障，进而造成整个系统不可用的情况，这种现象被称为服务雪崩效应。服务雪崩效应是一种因“服务提供者”的不可用导致“服务消费者”的不可用,并将不可用逐渐放大的过程。 

Hystrix 能使你的系统在出现依赖服务失效的时候，通过隔离系统所依赖的服务，防止服务级联失败，同时提供失败回退机制，更优雅地应对失效，并使你的系统能更快地从异常中恢复。 

### 3.2 快速使用

Feign 本身支持Hystrix，不需要额外引入依赖。 

1. 配置文件中开启：

```yml
feign:
  hystrix:
    enabled: true
```

2. 上面定义了`LabelClient`接口 这儿进行实现

```java
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"熔断器启动了");
    }
}
```

3. 修改接口注解如下

```
@FeignClient(value="tensquare‐base",fallback = LabelClientImpl.class)
```

4. 重新运行查看是否启动

## 四 Zuul微服务网关

### 4.1 简介

Zuul是Netflix开源的微服务网关，他可以和Eureka,Ribbon,Hystrix等组件配合使用。Zuul组件的核心是一系列的过滤器，这些过滤器可以完成以下功能： 

身份认证和安全: 识别每一个资源的验证要求，并拒绝那些不符的请求

审查与监控：

动态路由：动态将请求路由到不同后端集群

压力测试：逐渐增加指向集群的流量，以了解性能

负载分配：为每一种负载类型分配对应容量，并弃用超出限定值的请求

静态响应处理：边缘位置进行响应，避免转发到内部集群

多区域弹性：跨域AWS Region进行请求路由，旨在实现ELB(ElasticLoad Balancing)使用多样化 

## 4.2 基本使用

#### 4.2.1 新建网关项目，导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐starter‐netflix‐eureka‐
        client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐starter‐netflix‐zuul</artifactId>
</dependency>
```

#### 4.2.2 配置文件 (配置多个微服务)

```yml
zuul:
  routes:
    manage‐course: #路由名称，名称任意，保持所有路由名称唯一
      path: /course/**
      serviceId: xc‐service‐manage‐course #指定服务id，从Eureka中找到服务的ip和端口
      #url: http://localhost:31200 #也可指定url
      strip‐prefix: false #true：代理转发时去掉前缀，false:代理转发时不去掉前缀
      sensitiveHeaders: #默认zuul会屏蔽cookie，cookie不会传到下游服务，这里设置为空则取消默认的黑名单，如果设置了具体的头信息则不会传到下游服务
    manage‐course:
      path: /course/**
      serviceId: xc‐service‐manage‐course
      strip‐prefix: false
      sensitiveHeaders:
```

#### 4.2.3 添加启动类注解

```
@EnableZuulProxy
```

### 4.3 过滤器

Zuul的核心就是过虑器，通过过虑器实现请求过虑，身份校验等。

#### 4.3.1 ZuulFilter

自定义过虑器需要继承 ZuulFilter，ZuulFilter是一个抽象类，需要覆盖它的四个方法，如下：

1. shouldFilter：返回一个Boolean值，判断该过滤器是否需要执行。返回true表示要执行此过虑器，否则不执行。 
2. run：过滤器的业务逻辑。 
3.  filterType：返回字符串代表过滤器的类型，如下 pre：请求在被路由之前执行 routing：在路由请求时调用 post：在routing和errror过滤器之后调用 error：处理请求时发生错误调用
4.  filterOrder：此方法返回整型数值，通过此数值来定义过滤器的执行顺序，数字越小优先级越高。 

## 五 集中配置组件SpringCloudConfig 

### 5.1 Spring Cloud Config简介 

​		由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloudconfig ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是config server，二是configclient。

​		Config Server是一个可横向扩展、集中式的配置服务器，它用于集中管理应用程序各个环境下的配置，默认使用Git存储配置文件内容，也可以使用SVN存储，或者是本地文件存储。 

### 5.2 客户端配置

依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐config‐server</artifactId>
</dependency>
```

启动类注解：

```
@EnableConfigServer //开启配置服务
```

配置文件：将配置文件更名为base-dev.yml上传到码云上，下面的url对应的就是码云上面的项目地址

```yml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://gitee.com/chuanzhiliubei/tensquare‐config.git
```

### 5.3 服务单配置

依赖:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐starter‐config</artifactId>
</dependency>
```

创建bootstrap.yml添加如下内容，并删除application.yml

```yml
spring:
  cloud:
    config:
      name: base
      profile: dev
      label: master
      uri: http://127.0.0.1:12000
```

## 六 SpringCloudBus 消息总线

如果我们更新码云中的配置文件，那客户端工程是否可以及时接受新的配置信息呢？我们现在来做有一个测试，修改一下码云中的配置文件中mysql的端口 ，然后测试http://localhost:9001/label 数据依然可以查询出来，证明修改服务器中的配置并没有更新立刻到工程，只有重新启动程序才会读取配置。 那我们如果想在不重启微服务的情况下更新配置如何来实现呢? 我们使用SpringCloudBus来实现配置的自动更新。 

### 6.1 配置中心服务端设置

依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐bus</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐stream‐binder‐rabbit</artifactId>
</dependency>
```

修改application,yml配置

```yml
management: #暴露触发消息总线的地址
  endpoints:
    web:
      exposure:
        include: bus‐refresh
```

### 6.3 客户端设置

依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐bus</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring‐cloud‐stream‐binder‐rabbit</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring‐boot‐starter‐actuator</artifactId>
</dependency>
```

