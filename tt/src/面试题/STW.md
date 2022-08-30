STW: Stop-The-World，是在垃圾回收算法的执行过程中，需要将JVM内存冻结。在STW状态下，JAVA的所有线程都是停止的-GC线程除外，native方法可以执行，但是不能与JVM交互。
