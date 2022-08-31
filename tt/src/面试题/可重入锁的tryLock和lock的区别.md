# lock
lock()获取到锁之前会阻塞当前线程
# tryLock
不会阻塞，立马返回。false表示没获取到锁
应用场景是CAS，while(!tryLock()){}