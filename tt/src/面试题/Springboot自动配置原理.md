```text
1.自动配置类由各个starter提供，使用@Configuration+@Bean定义的配置类，放在META-INF/spring.factories下
2.使用Spring spi扫描META-INF/spring.factories下的配置类（@EnableAutoConfiguration->@Import(AutoConfigurationImportSelector.class)->selectImports方法中的getAutoConfigurationEntry）
3.使用@Import导入自动配置类
```

```text

自动装配就是让应用程序上下文为你找出依赖项的过程。说的通俗一点，就是Spring会在上下文中自动查找，并自动给bean装配与其关联的属性！

spring中实现自动装配的方式有两种，一种是通过xml文件、另一种是通过注解。

Spring自动装配解决了Spring时代项目配置繁琐的问题，简化配置。

2.自动装配实现原理：
1.当启动Springboot应用程序的时候，会先创建SpringApplication对象，在对象的构造方法中会 进行某些参数的初始化工作，最主要的是判断当前应用程序的类型以及初始化器和监听器，在这个过程中会加载整个应用程序中的Spring.factories文件，将文件内容放到缓存对象中，方便后续获取。

2.SpringApplication对象创建完成之后，开始执行run方法，来完成整个启动，启动过程中最主要的有两个方法，第一个叫做prepareContext，第二个叫做refreshContext,在这两个关键步骤中完成了自动装配的核心功能，前面的处理逻辑包含了上下文对象的创建，banner的打印，异常报告期的准备等各个准备工作，方便后续进行调用。

3.在prepareContext方法中主要完成的是对上下文对象的初始化操作，包括了属性值的设置，比如环境对象，在整个过程中有一个非常重要的方法，叫做load，load主要完成一件事，将当前启动类作为一个beanDefinition注册到registry中，方便后续在进行BeanFactoryPostProcessor调用执行的时候，找到对应的主类，来完成@SpringbootApplication，@EnableAutoConfiguration等注解的解析工作。

4.在refreshContext方法中会进行整个容器的刷新过程，会调用Spring中的refresh方法，refresh中有13个非常关键的方法，来完成整个Spring应用程序的启动，在自动装配过程中，会调用invokeBeanFactoryPostProcessor方法，在此方法中主要是对ConfigurationClassPostProcessor类的处理，这是BFPP的子类也是BDRPP的子类，在调用的时候会先调用BDRPP中的postProcessBeanDefinitionRegistry方法，然后调用postProcessBeanFactory方法，在执行postProcessBeanDefinitionRegistry的时候会解析处理各种注解，包含@PropertySource，@ComponentScans，@Bean，@Import等注解，最主要的是@Import注解的解析。

5.在解析@Import注解的时候，会有一个getImports的方法，从主类开始递归解析注解，把所有包含@Import的注解都解析到，然后在processImport方法中对Import的类进行分类，此处主要识别的是AutoConfigurationImportSelect归属于ImportSelect的子类，在后续过程中会调用deferredImportSelectHandler中的process方法，来完整EnableAutoConfiguration的加载。
```

- spring中把java.awt.headless设置为true
- 