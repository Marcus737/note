# 三大特征
## 封装
封装啥？类的属性和方法
封装属性，防止外部随意更改属性，只能通过方法有限制的调整属性
封装方法，把执行逻辑封装起来，使得使用者不必了解其内部实现，使用方便，便于增强
## 继承
在运用上：继承主要是增加代码的复用性
继承本质是is-a的关系，既特殊到一般。类使用extend关键字实现继承，被继承的类叫父类，继承父类的类叫子类
## 多态
存在继承，存在方法重写，存在父类引用指向子类
在实际调用时就会调用子类的方法
是类和类的关系
两个类之间存在继承关系，故而在调用时可以父类引用指向子类对象