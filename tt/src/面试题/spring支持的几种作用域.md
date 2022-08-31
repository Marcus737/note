# singleton 
单例模式，是默认的模式。
每个容器只有一个bean的实例
该对象的生命周期是与Spring IOC容器一致的

# prototype
原型模式，每次都会创建一个新的对象

# request
每个http请求复用一个实例

# session
每个session复用一个实例

# application
ServletContext的生命周期复用一个实例

# websocket 
websocket生命周期复用一个实例