# 对比点
## 基本数据类型
对于基本数据类型来说，==比较的是值是否相同；基本数据类型不存在equals方法
## 引用数据类型
对于引用数据类型来说，==比较的是计较的对象的地址是否相同。若equals没有重写，则equals的比较方式与==相同，否则以重写后的比较方式来比较

```java
class Test{
    public static void main(String[] args) {
        String a = new String("A"); //在堆
        String b = "A"; //在字符常量池
        String c = a; 
        System.out.println(a == b); // false
        System.out.println(a == c); // true
        System.out.println(b == c); // false
        System.out.println(a.equals(b)); // true
        System.out.println(a.equals(c)); //true
        System.out.println(b.equals(c)); // true
    }    
}
```