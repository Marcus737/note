# Broker
一个服务器就是一个broker
# topic
主题。用于区分消息的类别
# partition
分区，一个topic可以有多个分区。指存放数据的分区。每个broker应该只存topic的一个分区
# Segment
每个partition分成多个segment
# Producer
消息提供者。将消息发布到topic中，可指定partition
# Consumer
消费topic的数据
# Consumer Group
实现广播（发送给所有consumer）和单播（发送给单个consumer）的手段
# Leader
负责数据的读写。
每个topic有多个partition，但只能有一个主partition，这个partition就是leader。
# Follower
除了主partition外其他的partition。主从同步数据
# 
