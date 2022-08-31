# CountDownLatch
- await() 阻塞当前线程，当计数器为0时唤醒
- countDown() 计数器减1
- 计数器为0会将等待线程以此唤醒（使用AQS排队）
- 常用于有先后顺序的线程之间

# Semaphore
- acquire() 获取一个令牌，若没获取到则阻塞
- release() 释放一个令牌，会唤醒等待队列的第一个线程（使用AQS排队）