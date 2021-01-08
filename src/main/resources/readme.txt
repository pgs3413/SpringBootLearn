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