# 什么是自动装配
就是依赖注入的过程，选出一个最符合条件的注入
# 方式
- byName 根据bean名字进行匹配
- byType 根据bean类型进行匹配
- constructor 根据构造器的参数类型进行装配
- autodetect 如果有默认的构造器，则通过constructor进行自动装配，否则使用byTYpe方式进行装配
- 