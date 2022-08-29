# hashcode的作用是什么？
返回对象的散列值，根据该散列值来快速区分一个对象。一般来说，对象不同，其散列值也会不同，
但也会存在相同的散列值却两个对象不同

# 为什么重写equals方法也要重写hashcode方法
在Map容器中，put一个对象和值，再通过get获取值。
在get方法里，循环每个元素，先判断元素的hashcode与传进来的hashcode是否相同，再判断equals方法是否返回true
若不重写hashcode，get方法拿不到对应的值

```