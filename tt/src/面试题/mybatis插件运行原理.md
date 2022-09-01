# 支持4种接口
- ParameterHandler 参数处理
- ResultSetHandler 结果集处理
- StatementHandler sql处理
- Executor 执行器

# 编写插件
实现mybatis的interceptor接口并实现intercept（）方法。然后再给插件编写注解，指定要拦截哪个接口的哪些方法