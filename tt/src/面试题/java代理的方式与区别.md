# 静态代理
代理类和被代理类都实现同一个接口。
原理是自己手写代理类进行代理，优点是实现简单，缺点是不能很好的扩展。
实现过程，声明一个带call方法的Phone接口，实现类SmartPhone实现了Phone。
写一个PhoneInvocationHandler实现jdk的InvocationHandler，在invoke里调用methon传入真实对象和调用方法的参数。
最后通过Proxy类的静态方法newProxyInstance传入类加载器，真实对象实现的接口和前面的InvocationHandler
## 代码
```java
public class StaticProxy {
    public static void main(String[] args) {
        SmartPhone smartPhone = new SmartPhone();
        PhoneProxy phoneProxy = new PhoneProxy(smartPhone);
        phoneProxy.call();
    }
}

interface Phone {
    void call();
}

class SmartPhone implements Phone{

    @Override
    public void call() {
        System.out.println("打电话");
    }
}

class PhoneProxy implements Phone{

    //真实对象
    Phone phone;

    public PhoneProxy(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void call() {
        System.out.println("打电话前执行的语句");
        phone.call();
        System.out.println("打电话后执行的语句");
    }
}

```
# 动态代理
## JDK动态代理
缺点很明显，就是代理类和被代理的对象需要实现同一个接口
jdk动态代理需要2个对象，真实对象，invocationHandler。
```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKP {
    public static void main(String[] args) {
        SmartPhone smartPhone = new SmartPhone();
        SmartPhoneInvocationHandler handler = new SmartPhoneInvocationHandler(smartPhone);
        /**
         * loader – the class loader to define the proxy class 加载该类的类加载器
         * interfaces – the list of interfaces for the proxy class to implement 代理类实现的接口列表
         * h – the invocation handler to dispatch method invocations to 拦截方法的的处理器
         */
        Phone phoneProxy = (Phone)Proxy.newProxyInstance(smartPhone.getClass().getClassLoader(), new Class[]{Phone.class}, handler);
        phoneProxy.call();
    }
}

interface Phone{
    public void call();
}

class SmartPhone implements Phone {

    @Override
    public void call() {
        System.out.println("打电话");
    }
}

class SmartPhoneInvocationHandler implements InvocationHandler{

    Phone phone;

    public SmartPhoneInvocationHandler(Phone phone) {
        this.phone = phone;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object res = null;
        //根据方法名拦截方法
        if (method.getName().equals("call")){
            System.out.println("方法调用前");
            res = method.invoke(phone, args);
            System.out.println("方法调用后");
        }
        return res;
    }
}
```
## cglib代理
最大的好处是解决了代理类和被代理的对象需要实现同一个接口的问题