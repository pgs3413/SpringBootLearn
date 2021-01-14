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

4.SpringMVC常用注解
@RequestMapping("/test03/{name}"} @GetMapping("xx")
@RequestParam("xx")   @RequestParam("xx") @RequestHeader("User-Agent") @RequestBody
@CookieValue("xx") @RequestAttribute("xx") @PathVariable("name") 放在形参前
@MatrixVariable("jsessionid") /abc;jsessionid=xx;xx=xx,xx,xx 开启需要配置UrlPathHelper

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

8.单文件与多文件的上传
html书写：
<form action="" method="post" enctype="multipart/form-data">
    <input type="file" name="single"><br>
    <input type="file" name="multi" multiple>
</form>
FileController

9.docker
安装 yum install docker 启动:systemctl start/enable docker 查询：docker search mysql
拉取：docker pull mysql:5.5 查看镜像：docker images 删除镜像：docker rmi image-id
运行镜像：docker run --name container-name -d image-name -p xxxx:xxxx -name：自定义容器名 -d:后台运行 -p 端口映射 主机端口:docker端口
查看运行：docker ps -a 停止运行：docker stop container-id 开始运行：docker start container-id 删除容器：docker rm container-id
查看日志：docker logs container-id
启动mysql:docker run -p 3306:3306 --name some-mysql --restart always -e MYSQL_ROOT_PASSWORD=my-secret-pw -v 主机配置文件：docker配置文件 -d mysql:tag
   参数设定：--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
进入容器内：docker exec -it container-id bash


10.数据访问
SpringBoot默认支持的数据源：
默认启动执行 initialization-mode: always
建表sql文件:schema.sql 指定路径 schema: -classpath:xx.sql 普通sql文件:data.sql
自动配置了JdbcTemplate
SpringBoot1.x:
使用druid:需要自己注册一个DataSource Configuration+Bean ConfigurationProperties(prefix="spring.datasource")
         注册一个StatViewServlet
SpringBoot2.x:可在配置文件中配置

10.整合MyBatis
注解版：
    不需要在配置文件中额外配置 自定义MyBatis的配置规则，给容器中添加一个ConfigurationCustomizer
    @MapperScan(value="com.pan.SpringBootLearn.mapper") 可加在启动类上
配置文件版：
    接口+xml文件，接口要加@Mapper 在配置文件中指定xml路径

11.Spring Data JPA (类似MyBatisPlus，不用写基本的sql语句）
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

redis数据结构：
key-string 最常用的，一般用于存储一个值, key-hash 存储一个对象数据, key-list 使用list结构实现栈和队列结构,
key-set 交集，差集和并集的操作, key-zset 排行榜，积分存储操作

string常用命令：
1.set key value
2.get key
3.mset key value [key value...]
4.mset key [key..]
5.自增命令 incr key
6.自减命令 decr key
7.自增或自减指定数量：incrby/decrby key increment
8.设置值的同时，指定生存时间 setex key second value
9.设置值，如果当前key不存在的话 setnx key value
10.在key对应的value后，追加内容 append key value
11.查看value字符串的长度 strlen key

hash结构常用命令：
1.存储数据 hset key field value           hset person name pan , hset person age 18
2.获取数据 hset key field                 hget person name
3.批量操作 hmset key field value [field value..] hmget key field [field ..]        hmset person name pan age 18 ,  hmget person name age
4.自增指定的值，可负数：hincrby key fieldincrement
5.设置值，如果当前key不存在的话 hsetnx key field value
6.检查field是否存在：hexists key field
7.删除key对应的某一个field：hdel key field [field..]
8.获取当前hash结构中的全部field和value：hgetall key
9.获取当前hash结构中的全部field:hkeys key
10.获取当前hash结构中的全部value：hvals key
11.获取当前hash结构中field的数量：hlen key

list结构命令：
1.存储数据 从左侧插入数据 : lpush key value [value ..] 从右侧插入数据:rpush key value [value ..]
    如果key存在且为list:lpushx key value , rpushx key value  指定索引位置(覆盖原来位置的数据) ：lset key index value
2.获取数据  弹栈方式获取数据：左侧 lpop key 右侧 rpop key 获取指定索引范围的数据(start从0开始，stop输入-1,代表最后一个，-2倒数第二个): lrange key start stop
            获取指定索引位置的数据 lindex key index 获取整个列表的长度 llen key
3.删除数据 删除列表中count个value值 count>0 从左删 count=0 删除全部 count<0 从右删 lrem key count value
            保留指定访问内的数据 ltrim key start stop
            将一个列表中最后一个数据插入到另外一个列表的头部位置 rpoplpush list1 list2

set结构命令：
1.存储数据 sadd key member [member ..]
2.获取全部数据 smembers key
3.随机获取一个数据 spop key [count] 这个数据也会移除这个数据
4.交集 sinter set1 set2 ..
5.并集 sunion set1 set2 ..
6.差集 sdiff set1 set2 ..
7.删除指定的数据 srem key member [menber..]
8.查看当前的set集合中是否包含指定值 sismember key member

zset结构命令 (sorted set):
1.添加数据 zadd key score menber [score menber..] score为数值 member不允许重复
2.修改member的score： zincrby key increment member
3.查看member的score：zscore key member
4.获取zset中数据的数量：zcard key
5.根据score的范围查询member数量 zcount key min max
6.删除zset中的成员：zrem key member [member..]
7.根据分数从小到大排序，获取指定范围内的数据：zrange key start stop [withscores]
8.根据分数从大到小排序，获取指定范围内的数据：zrevrange key start stop [withscores]
9.根据分数的范围获取数据：zrangebyscore key min max [withscores] [limit offset count]
10.zrangebyscore key max min [withscores] [limit offset count] +inf最大值 -inf最小值

key常用命令：
1.查看redis中的全部的key  keys pattern(*,xxx*)
2.查看某一个key是否存在 exists key
3.删除key del key [key..]
4.设置key的生存时间 秒 expire key second 毫秒 pexpire key milliseconds 指定时间 expireat key timestamp , pexpireat key milliseconds
5.查看key的剩余生存时间 ttl key , pttl key (返回-2 key不存在 -1 当前key没有设置生存时间)
6.移除key的生存时间 persist key
7.选择操作的库 select 0~15
8.移动key到另外一个库中 move key db

db库的常用命令：
1.清空当前所在的库 flushdb
2.清空全部数据库 flushall
3.查看当前数据库中有多少个key dbsize
4.查看最后一次操作的时间 lastsave
5.实时监控redis服务接受到的目录 monitor

java连接redis : jedis , Lettuce

Jedis连接redis
1.连接redis  Jedis jedis = new Jedis(ip , port)
2.操作redis  调用jedis的方法
3.释放资源  jedis.clos()

jedis如何存储一个对象到redis中
以byte[]形式存储到redis中 ：使用Spring中的SerializationUtils.serialize()序列化key和value
以字节数组的形式获得对象 byte[] value=jedis.get(byte[] key) 反序列化 Object obj=SerializationUtils.deserialize(value)
以String的形式存储对象 ：将对象转换为json对象

jedis连接池
1.创建连接池 JedisPool pool=new JedisPool(ip,6379)
    配置连接池信息 GenericObjectConfig类 以构造方式传入JedisPool
2.通过连接池获得jedis对象 Jedis jedis=pool.getResource()
3.操作
4.释放资源

redis管道操作 执行大量命令
获得管道 Pipeline p=jedis.pipelined() 调用p的方法,将命令放到管道中 p.incr(xx) 执行方法 p.syncAndReturnAll()

AUTH : 配置文件 requirepass 密码 ， 命令行 Config set requirepass 密码， auth 密码
jedis : jedis.auth(xx) ， 在new JedisPool时指定密码

redis事务：开启事务 multi 执行事务 exec 取消事务 discard 监听 watch key [key..]

redis持久化机制 允许，推荐同时开启 优先选择AOF文件
    RDB : 写进配置文件 默认开启
        save 900 1 # 900秒之内，有一个key改变了，就进行RDB持久化
        rdbcompression # 开启RDB持久化压缩
        dbfilename redis.rdb # RDB持久化文件的名称
    AOF : 写进配置文件 默认关闭 更安全
        appendonly yes #代表开启AOF持久化
        appendfilename "redis.aof" #AOF文件的名称
        appendfsync always(everysec , no)  #AOF持久化执行的时机

redis的主从架构 哨兵模式 集群

Springboot整合redis
默认jdk序列化机制
引入redis的starter，容器中保存的是RedisCacheManager。RedisCacheManager帮我们创建RedisCache来作为缓存组件，RedisCache通过操作redis来缓存数据
springboot1.x中：底层使用redisTemplate
springboot2.x中：key采用StringRedisSerializer序列化，value采用jdk序列化，所以自定义RedisTemplate是没有效果的，需要配置RedisCacheConfiguration才行

二、SpringBoot与消息

大多应用中，可通过消息服务中间件来提升系统异步通信，扩展解耦能力
消息服务中两个重要概念：消息代理(message broker)和目的地(destination)，当消息发送者发送消息以后，将由消息代理接管，消息代理保证消息传递到指定目的地
消息队列主要有两种形式的目的地：
    1.队列(queue):点对点消息通信
    2.主题(topic):发布(publish)/订阅(subscribe) 消息通信
JMS , AMQP

RabbitMQ (基于AMQP):
核心概念：
    1.message:消息，消息是不具名的，它由消息头和消息体组成。消息体是不透明的，而消息头则有一系列的可选属性组成，包括：routing-key,priority,delivery-mode
    2.publisher:消息的生产者，也是一个向交换器发布消息的客户端应用程序
    3.exchage:交换器，用来接受生产者发送的消息并将这些消息路由给服务器中的队列。exchange有4种类型：direct(单播),fanout(广播),topic(组播),headers(不用)
    4.queue:消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息可投入一个或多个队列。消息一直在队列里面，等待消费者连接到这个队列将其取走
    5.binding:绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。exchange和queue的绑定可以是多对多的关系
    6.connection:网络连接，比如一个TCP连接
    7.channel:信道，多路复用连接中的一条独立的双向数据流管道。一条TCP连接有多个信道。AMQP命令都是通过信道发出来的。
    8.consumer:消息的消费者，表示一个从消息队列中取得消息的客户端应用程序。
    9.virtual host:虚拟主机，表示一批交换器，消息队列和相关对象。每个vhost本质上就是一个mini版的rabbitMQ服务器。必须在连接时指定，默认为/
    10.broker: 表示消息队列服务器实体

自动配置：
    1.RabbitAutoConfiguration
    2.自动配置了连接工厂ConnectionFactory
    3.RabbitProperties 封装了RabbitMQ的配置
    4.RabbitTemplate 给RabbitMQ发送和接受消息
    5.AmqpAdmin RabbitMQ系统管理功能组件

1.rabbitTemplate.send(exchange,routeKey,Message) Message需要自己构造一个，定义消息体内容和消息头
2.rabbitTemplate.convertAndSend(exchange,routeKey,object) 自动序列化

启动类上加@EnableRabbit
Service层方法加@RabbitListener(queues = "xx") 监听消息队列
可将消息封装成Message类，可得到消息头

AmqpAdmin:创建和删除Queue , Exchange

三、Springboot与检索

ElasticSearch
    全文搜索引擎，可以快速的存储，搜索和分析海量的数据；
    分布式搜索服务，提供Restful API,底层基于Lucene

保存数据：
    发起PUT请求 http://192.168.1.247:9200/pan/_doc/1
    body：
        {
            "name":"pan",
            "age":18,
            "about":"I love to go rock climbing", #后加
            "interests":["sports","music"]
        }

    response:
        {
            "_index": "pan",
            "_type": "_doc",
            "_id": "1",
            "_version": 1,
            "result": "created",
            "_shards": {
                "total": 2,
                "successful": 1,
                "failed": 0
            },
            "_seq_no": 0,
            "_primary_term": 1
        }

检索文档：
    发起GET请求 http://192.168.1.247:9200/pan/_doc/1
    response:
        {
            "_index": "pan",
            "_type": "_doc",
            "_id": "1",
            "_version": 1,
            "_seq_no": 0,
            "_primary_term": 1,
            "found": true,
            "_source": {
                "name": "pan",
                "age": 18,
                "about": "I love to go rock climbing",
                "interests": [
                    "sports",
                    "music"
                ]
            }
        }

删除数据：发起DELETE请求 http://192.168.1.247:9200/pan/_doc/1

检查数据是否存在：发起HEAD请求 http://192.168.1.247:9200/pan/_doc/1
    200 存在 404 不存在

查询所有数据：发起GET请求 http://192.168.1.247:9200/pan/_doc/_search
复杂查询：发起GET请求
        1.http://192.168.1.247:9200/pan/_doc/_search?q=name:pan
        2.POST http://192.168.1.247:9200/pan/_doc/_search
          body:
            {
            "query":{
                "match":{
                "name":"pan"
                }
            }
            }

        关键字查询：(全文检索)
            body:
                {
                    "query":{
                         "match":{
                              "about":"rock climbing"
                                }
                            }
                }
        完整搜索：使用"match_phrase"

springboot整合elasticsearch
默认两种技术来和ES交互：
    1.Jest（默认不生效）springboot2.x弃用
    2.SpringData ElasticSearch
        1) 提供了 Client类 节点信息 clusterNodes , clusterName
        2) 提供了 ElasticsearchTemplate
        3) 提供了 ElasticsearchRepository接口 使用该接口的子接口操作ES

四、SpringBoot与任务

1.异步任务
在Service层方法上加@Async 在启动类上加@EnableAsync

2.定时任务
在定时方法上加@Scheduled(cron = "*秒 *分 *时 *日 *月 *周") 启动类上加@EnableScheduling
cron写法：, 枚举 - 区间 * 任意 / 步长 ? 日、周冲突匹配

3.邮件任务

五、SpringBoot与安全

应用程序的两个主要区域是 认证 和 授权
认证(authentication):是建立一个他声明的主体的过程
授权(authorization):指确定一个主体是否允许在你的应用程序执行一个动作的过程

Spring Security 在配置类里配置

六、SpringBoot 与 分布式

zookeeper+dubbo
zookeeper:是一个为分布式应用提供一致性服务的软件 （注册中心）
dubbo:分布式服务框架
1.将服务注册到注册中心
    1）引入dubbo和zkclient相关依赖
    2）配置dubbo的扫描包和注册中心地址
    3）使用@Service发布服务
2)消费者使用服务
    1）引入dubbo和zkclient相关依赖
    2）配置dubbo注册中心地址
    3）引用服务 @Reference

SpringBoot+SpringCloud

SpringCloud是一个分布式的整体解决方案。为开发者提供了在分布式系统（配置管理，服务发现，熔断，路由，微代理，控制总线，
一次性token，全局锁，leader选举，分布式Session，集群状态）中快速构建的工具，开发者可以快速的启动服务或构建应用，同时能够
快速和云平台资源进行对接。

SpringCloud分布式开发五大组件：
1.服务发现--Netflix Eureka (注册中心)
2.客服端负载均衡--Netflix Ribbon
3.断路器--Netflix Hystrix
4.服务网关--Netflix Zuul
5.分布式配置--Spring Cloud Config

七、SprintBoot 与 监控
1.引入spring-boot-starter-actuator
2.management.endpoints.web.exposure.include=*
3.http访问 /health /auditevents /beans /info /dump /autoconfig /trace /headdump /mappings /metrics /env /loggers /configprops
POST请求  /shutdown 需配置：management.endpoints.shutdown.enabled=true