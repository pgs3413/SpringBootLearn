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

