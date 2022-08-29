# 可变性
String时不可变的（每次操作都会创建一个字符串）
StringBuilder和StringBuffer都是可变的
# 线程安全
StringBuilder线程不安全，效率高
StringBuffer线程安全（synchronized），适合多线程环境下使用
