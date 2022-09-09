# RabbitMQ是AMQP（Advanced Message Queuing Protocol）的实现
# Broker
通常是服务器。
维护一条从生产者到消费者的路线，保证数据能按指定的方式进行传输
# Exchange
消息交换机。 指定消息路由到哪个队列。
- Direct Exchange: 直接匹配，通过Exchange名称+RoutingKey来发送与接收消息
- Fanout Exchange: 广播订阅，向所有消费者发布消息，但只有消费者将队列绑定到指定路由器才收到消息。忽略Routing key
- Topic Exchange：主题匹配订阅，主题指的是Routing Key，Routing key符合匹配才能收到消息， 可以使用通配符
- Headers Exchange：消息头订阅。消息携带的请求头类似http的请求头，接受者也需要有一样的请求头才接受到消息

# Queue
消息的载体
# Binding
绑定。把队列和消息交换机按路由绑定起来
# Routing key
路由关键字。exchange根据这个进行消息投递
# vhost
虚拟主机。一个broker可以有多个vhost，用作不同用户的权限分离
# Producer
消息生产者
# Consumer
消息消费者
# Channel
消息通道，每个客户端可以有多个channel
