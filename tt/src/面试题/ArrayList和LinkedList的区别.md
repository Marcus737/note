# 底层数据结构不同
ArrayList基于数组实现，LinkedList基于链表实现
# 使用场景不同
ArrayList更适合随机查找（O1），Linked适合删除和添加(O1)
# 额外实现的接口
LinkedList额外实现了Deque接口，所以LinkedList可以当作队列使用
# 扩容机制
## ArrayList
- 空参初始化指向一个空数组
- 第一次添加时指定初始容量为10
- 后续每次扩容后容量为原来的1.5倍（newCapacity = oldCapacity + (oldCapacity >> 1)）
