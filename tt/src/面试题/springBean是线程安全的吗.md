Spring本身没有对bean做线程安全的处理
- 如果bean是无状态的，那么bean是线程安全
- 如果bean是有状态，则bean不是线程安全的
