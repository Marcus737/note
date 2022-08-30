# 原理
```text
每个线程都有一个threadLocalMap
当调用ThreadLocal的set方法时，会先获取当前的threadLocalMap，再向这个threadLocalMap设置值，key为当前ThreadLocal对象，val为传进来的值
取值的时候需要传入对应的ThreadLocal对象，即调用ThreadLocal.get()，对象就是本身
```
# 注意
使用完ThreadLocal后，需要手动调用remove方法，不然会造成内存泄露

# 内存泄露原因
```text
在线程池中使用ThreadLocal要手动remove
线程本身的ThreadLocalMap指向一个Entry，Entry的key指向ThreadLocal这是个虚引用，Entry的value指向使用的对象，这是个强引用
在线程池中，因为线程复用的原因，线程不会被回收。Entry本应该被回收，但Entry有强引用value，所以也不会被回收，这就造成了内存泄露。
解决方法时调用remove将Entry的value这个强引用去掉，那么Entry就可以被正常回收
```


