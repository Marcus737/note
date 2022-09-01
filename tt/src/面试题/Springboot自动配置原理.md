1.自动配置类由各个starter提供，使用@Configuration+@Bean定义的配置类，放在META-INF/spring.factories下
2.使用Spring spi扫描META-INF/spring.factories下的配置类
3.使用@Import导入自动配置类