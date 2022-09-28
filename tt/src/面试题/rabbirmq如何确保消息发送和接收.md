# 消息发送方
### ConfirmCallback方法
ConfirmCallback是一个回调方法。消息发送到broker后触发回调，确认消息是否代打broker服务器，也就是只确认是否正确到达Exchange中
### ReturnCallback方法
交换器路由不到队列时回调。

# 消息接受方
rabbitmq的消息确认机制（ACK）默认是自动确认的，自动确认会在消息发送给消费者后立即确认，但存在消息丢失的可能。
如果消费端抛出异常，即时回滚了也只是保证了数据的一致性，但消息还是丢失了。
### 消息确认模式
- AcknowledgeMode.NONE: 自动确认
- AcknowledgeMode.AUTO: 根据情况确认
- AcknowledgeMode.MANUAL: 手动确认
消费者收到消息后，手动调用Basic.ACK（确认当前消息）或Basic.Nack（否定当前消息）和Basic.Reject（拒绝当前消息）后，Rabbit收到这些消息后。才认为本次投递完成
