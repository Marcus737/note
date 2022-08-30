# 锁池
所有需要竞争同步锁的线程都会放在锁池中
# 等待池
调用wait（）方法，线程放到等待池，与锁池不同的是，等待池的线程不会主动竞争锁池。notify（）是随机从等待池选出一个线程放到锁池，notifyAll（）是等待池的所有放到锁池中

# Sleep
- sleep是Thread类的静态方法，sleep不会释放其所拥有的锁
- 不依赖于synchronized
- sleep一般用于线程休眠，或轮询暂停操作

# wait
- wait是Object的方法，wait会释放其所拥有的锁，且加入等待池
- 依赖于synchronized
- wait一般用于多线程之间的通信

# yield（）
立即进入就绪状态，马上释放cpu的执行权

# join（）
线程的方法，调用后进入阻塞状态。线程B调用线程A.join()方法，线程B立即进入阻塞，直到A执行完成A中断线程

