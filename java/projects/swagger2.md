## Swagger2

OpenAPI规范（OpenAPI Specification 简称OAS）是Linux基金会的一个项目，试图通过定义一种用来描述API格
式或API定义的语言，来规范RESTful服务开发过程，

目前版本是V3.0，并且已经发布并开源在github上：（https://github.com/OAI/OpenAPI-Specification）

Swagger是全球最大的OpenAPI规范（OAS）API开发工具框架，支持从设计和文档到测试和部署的整个API生命周期的开发。 (https://swagger.io/)

Spring Boot 可以集成Swagger，生成Swagger接口，Spring Boot是Java领域的神器，它是Spring项目下快速构建
项目的框架。

### 1.1 Swagger常用注解	

在Java类中添加Swagger的注解即可生成Swagger接口，常用Swagger注解如下：

- @Api：修饰整个类，描述Controller的作用 
- @ApiOperation：描述一个类的一个方法，或者说一个接口
- @ApiParam：单个参数描述 
- @ApiModel：用对象来接收参数 
- @ApiModelProperty：用对象接收参数时，描述对象的一个字段 
- @ApiResponse：HTTP响应其中1个描述 
- @ApiResponses：HTTP响应整体描述 @ApiIgnore：使用该注解忽略这个API @ApiError ：发生错误返回的信息 
- @ApiImplicitParam：一个请求参数
- @ApiImplicitParams：多个请求参数

### 1.2 Swagger接口定义

```java
@Api("cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
        @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
    @ApiOperation("新增页面")
    public CmsPageResult add(CmsPage cmsPage);
    //根据页面id查询页面信息
    @ApiOperation("根据页面id查询页面信息")
    public CmsPage findById(String id);

}
```

### 1.4 Swagger2基本使用示例

依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
</dependency>
```

配置类：

```java
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.service"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学成网api文档")
                .description("学成网api文档")
                .version("1.0")
                .build();
    }
}
```

### 1.3 Swagger接口测试

Swagger接口生成工作原理：

1. 系统启动，扫描到api工程中的Swagger2Configuration类
2. 在此类中指定了包路径com.xuecheng，找到在此包下及子包下标记有@RestController注解的controller类
3. 根据controller类中的Swagger注解生成接口文档。

启动cms服务工程，查看接口文档，请求：http://localhost:31001/swagger-ui.html