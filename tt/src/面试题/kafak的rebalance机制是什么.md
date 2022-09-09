# 综述
rebalance就是说如果消费组里的消费者数量有变化或消费的分区数有变化，kafka会重新分配消费者消费分区的关系。
比如consumer group中某个消费者挂了，此时会自动把分配给他的分区交给其他的消费者，如果他又重启了，那么又会把一些分区重新交还给他。

2、消费者Rebalance分区分配策略
主要有三种rebalance的策略：range()、round-robin(轮询)、sticky(粘性)。
Kafka 提供了消费者客户端参数partition.assignment.strategy 来设置消费者与订阅主题之间的分区分配策略。默认情况为range分配策略。
假设一个主题有10个分区(0-9)，现在有三个consumer消费：
（1）range策略就是按照分区序号排序(范围分配)，假设 n＝分区数／消费者数量 = 3， m＝分区数%消费者数量 = 1，那么前 m 个消费者每个分配 n+1 个分区，后面的（消费者数量－m ）个消费者每个分配 n 个分区。

比如分区0~3给一个consumer，分区4~6给一个consumer，分区7~9给一个consumer。

（2）round-robin策略就是轮询分配

比如分区0、3、6、9给一个consumer，分区1、4、7给一个consumer，分区2、5、8给一个consumer

（3）sticky策略初始时分配策略与round-robin类似，但是在rebalance的时候，需要保证如下两个原则。

分区的分配要尽可能均匀 。
分区的分配尽可能与上次分配的保持相同。
当两者发生冲突时，第一个目标优先于第二个目标 。这样可以最大程度维持原来的分区分配的策略。比如对于第一种range情况的分配，如果第三个consumer挂了，那么重新用sticky策略分配的结果如下：

consumer1除了原有的0~3，会再分配一个7
consumer2除了原有的4~6，会再分配8和9

