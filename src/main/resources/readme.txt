1.引入配置文件中的属性
ConfigurationProperties(prefix = "xx")
PropertySource(value="classpath:xx.properties")
@Value(${name})

2.lombok
@Data
@NoArgsConstructor
@AllArgsConstructor

3.validation
在对象的属性上加 @NotNull(message="xx") @Min ..
在Controller的方法的形参中加@Valid 信息保存在BindingResult中

4.
@RequestMapping("/test03/{name}"}
@PathVariable("name") 放在形参前

5.
restful风格
URI:/资源名称/资源标识 HTTP请求方式区分对资源CRUD操作
GET --> 查询  POST-->添加  /{id}---PUT --> 修改  /{id}--DELETE-->删除
GetMapping PostMapping PutMapping DeleteMapping
发送PUT请求 DELETE请求
①SpringMvc中配置HiddenHttpMethodFilter spring.mvc.hiddenmethod.filter.enabled=true
②页面创建一个post表单
③ 创建一个input name="_method" ,value="put(delete)"

6.定制错误页面和错误数据
定制错误页面：
有模板引擎的情况下；error/状态码；将错误页面命名为 错误状态码.html 放在模板引擎文件夹下的error文件夹 4xx 5xx
错误信息：timestamp status error exception message errors
没有模板引擎的情况下：在其他文件夹 error/
都没有，返回一个默认页面
定制错误数据：


7.注册 Servlet Filter Listener
ServletRegistrationBean FilterRegistrationBean ServletListenerRegistrationBean

8.docker
安装 yum install docker 启动:systemctl start/enable docker 查询：docker search mysql
拉取：docker pull mysql:5.5 查看镜像：docker images 删除镜像：docker rmi image-id
运行镜像：docker run --name container-name -d image-name -p xxxx:xxxx -name：自定义容器名 -d:后台运行 -p 端口映射 主机端口:docker端口
查看运行：docker ps -a 停止运行：docker stop container-id 开始运行：docker start container-id 删除容器：docker rm container-id
查看日志：docker logs container-id
启动mysql:docker run -p 3306:3306 --name some-mysql --restart always -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
   参数设定：--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

8.数据访问
SpringBoot默认支持的数据源：
默认启动执行 initialization-mode: always
建表sql文件:schema.sql 指定路径 schema: -classpath:xx.sql 普通sql文件:data.sql
自动配置了JdbcTemplate
SpringBoot1.x:
使用druid:需要自己注册一个DataSource Configuration+Bean ConfigurationProperties(prefix="spring.datasource")
         注册一个StatViewServlet
SpringBoot2.x:可在配置文件中配置

9.整合MyBatis
注解版：
    不需要在配置文件中额外配置 自定义MyBatis的配置规则，给容器中添加一个ConfigurationCustomizer
    @MapperScan(value="com.pan.SpringBootLearn.mapper") 可加在启动类上
配置文件版：
    接口+xml文件，接口要加@Mapper 在配置文件中指定xml路径

10.Spring Data JPA (类似MyBatisPlus，不用写基本的sql语句）
实体类相关注解：ORM
 类上 @Entity @Table(name="xxx") 省略默认表名为类名
 属性上 @Id @GeneratedValue(strategy=GenerationType=IDENTITY) @Column(name ="xxx") name可省略
Dao接口：extends JPARepository<实体类，主键的类型>
spring.jpa.hibernate.ddl-auto=update 更新或者创建表结构
spring.jpa.show-sql=true 控制台输出sql语句

SpringBoot 高级技术

一、SpringBoot与缓存 SpringBoot缓存默认使用ConcurrentHashMap

Application > CachingProvider > CacheManager(redis) > Cache > Entry<k,v>-->Expiry
CacheManager管理多个Cache组件，对缓存的CRUD操作在Cache组件中，每一个缓存组件有自己唯一的一个名字

@EnableCaching加在启动类上

方法注解：@Cacheable 将方法的运行结果进行缓存，，以后再要相同的缓存，直接从缓存中获取，不再调用方法
        执行方法之前执行，一般加在查询的Service方法上
        cacheNames/value:指定缓存组件的名字 key：指定key，默认是使用方法参数的值 key="#id" / "#root.args[0]"
        keyGenerator:key的生成器 cacheManager:指定缓存管理器 condition:指定符合条件的情况下才缓存 condition="#id>0"
        unless:否定缓存 满足条件不缓存，可以获得结果再判断 unless=“#result==null" sync:是否使用异步模式

        @CacheEvict 缓存清除
        先调用目标方法，一般加在删除的Service方法上，缓存名要一致，key要一致,key="#result.id"，删除原本的缓存
        allEntries=true 把所有缓存删除 beforeInvocation=true 在方法执行之前清空缓存

        @CachePut 既调用方法，又更新缓存数据 修改了数据库的某个数据，同时更新缓存
        先调用目标方法，将目标方法的结果缓存起来，一般加在更新的Service方法上，缓存名要一致，key要一致,key="#result.id"，代替原本的缓存

        @Caching 可同时写多个缓存注解

Service类上： @CacheConfig 抽取缓存的公共配置 @CacheConfig(cacheNames="xx")

开发中使用缓存中间件：redis