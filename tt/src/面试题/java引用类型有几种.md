```java
import java.lang.ref.WeakReference
```
# Strong Reference 强引用
new出来的对象
# SoftReference 软引用
SoftReference 于 WeakReference 的特性基本一致， 最大的区别在于 SoftReference 会尽可能长的保留引用直到 JVM 内存不足时才会被回收(虚拟机保证), 这一特性使得 SoftReference 非常适合缓存应用
# WeakReference 弱引用
当所引用的对象在 JVM 内不再有强引用时, GC 后 weak reference 将会被自动回收
# PhantomReference 虚引用
PhantomReference 的唯一作用就是跟踪 referent 何时被 enqueue 到 ReferenceQueue 中