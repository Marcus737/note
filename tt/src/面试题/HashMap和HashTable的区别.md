# 底层实现
* HashMap使用数组+链表+红黑树实现
jdk8开始从链表高度到8，数组长度超过64，链表转为红黑树，元素内部类以N哦的
节点存在。
  * 计算key的hash值，二次hash然后对数组长度取模，对应到数组下标
  * 如果没有hash冲突，则直接创建Node存入数组
  * 如果产生冲突，先进行equals比较，相同则替换该元素；不同，则判断链表高度插入链表。若链表高度达到8，并且数组长度到64则转为红黑树，链表长度低于6转回链表
  * key为null，存在下标为0的位置
  
* HashTable 使用数组和链表

# 线程安全
HashMap线程不安全，HashTable线程安全

# 扩容机制

