# 继承
ApplicationContext继承BeanFactory。BeanFactory可以生成，维护bean，ApplicationContext也有此种功能。
但还有诸如EnvironmentCapable，messageSource等接口，实现了获取系统环境变量，国际化等功能

