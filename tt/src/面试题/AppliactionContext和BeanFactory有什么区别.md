# 继承
ApplicationContext继承BeanFactory。BeanFactory可以生成，维护bean，ApplicationContext也有此种功能。
但还有诸如EnvironmentCapable，messageSource等接口，实现了获取系统环境变量，国际化等功能

# 综述
BeanFactory提供基本的IOC和DI功能，而ApplicationContext提供高级功能，BeanFactory可用与测试和非生产使用。
ApplicationContext时更为丰富的容器实现，应该优先于BeanFactory
