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
