package com.Basic;
/**
 * 动态代理基本实现
 * 组成要素1： Subject：抽象主题，包含了一组接口
 * 组成要素2： RealSubject：实现了抽象主题的接口，被代理的类对象，是目标对象
 * 组成要素3： ProxySubject：代理类，除了实现抽象主题定义的接口外，还必须持有被代理类的对象。
 * ！！！注意是对象，代理的目标是一个对象，而不是一个类，通过代理类的对象执行方法，具体是转到调用invoke方法执行
 *
 * 具体实现：
 * 1. 实现InvocationHandler接口， 这个接口包含一个成员字段: 真实对象和invoke方法 （这是最基本的，还可以增强）
 * 2. 生成代理对象，主要通过Proxy.newProxyInstance()方法，代理对象属于运行时定义的一个类，这个类同样会实现抽象主题的接口，只是调用目标对象
 * 的方法时，调用的handler对象的invoke方法，方法体内handler.invoke()。所以newProxyInstance有三个参数。
 *
 * 思考：为什么newProxyInstance()方法的参数中不包含目标对象？
 * 首先，目标对象已经作为成员变量保存在handler中。
 * 其次，newProxyInstance()方法本身就不用知道目标对象是什么，其是为了动态创建一个实现了抽象主题接口的类对象，根据接口的Class对象利用反射就
 * 可以创建，然后每个方法实体里，调用handler.invoke()即可，目标对象方法的具体运行由invoke执行就可以了。
 *
 * 动态代理的应用
 * 1. Spring的AOP: 面向切面编程。
 * 通过动态代理的思路，增强模块的基本功能，在模块基本功能上，增加执行前和执行后的逻辑。
 */

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 抽象主题： 接口类
 */
interface Subject{
    void Hello(String str);
    void  Bye();
}
/**
 * 真实主题： 实现类
 */
class RealSubject implements Subject{

    @Override
    public void Hello(String str) {
        System.out.println("hello:"+str);
    }

    @Override
    public void Bye() {
        System.out.println("bye");
    }
}

/**
 * 代理执行类
 * 实现InvocationHandler接口，主要实现invoker方法
 */

class MyProxyHandler implements InvocationHandler {
    private Object target;
    public MyProxyHandler(Object target){
        this.target = target;
    }
    /**
     * @param proxy: 代理类对象
     * @param method： 被代理类的方法
     * @param args： 方法参数
     * @return obj: 被代理类方法的返回值
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在代理类方法执行前可以添加一些操作
        System.out.println("代理执行...");
        System.out.println("执行类："+proxy.getClass().getName());
        System.out.println("执行方法："+method);
        //执行代理的方法
        Object obj = method.invoke(target,args);
        //在之后完成一些操作
        System.out.println("执行结束...");
        return obj;
    }
}

public class DynamicProxy{
    public static void main(String... args){
        proxyExample1();
    }
    /**
     * 这个示例中，抽象主题是Subject接口，真实主题是RealSubject
     * 当proxy.Hello时就会转到handler里的invoke中执行
     */
    private static void proxyExample1(){
        RealSubject realSubject = new RealSubject();
        MyProxyHandler handler = new MyProxyHandler(realSubject);
        /**
         * 通过Proxy的newProxyInstance创建代理对象
         * param1:classloader类加载器，可以采用系统的类加载器:ClassLoader.getSystemClassLoader()
         * param2:需要代理的类实现的接口数组，注意必须是接口
         * param3:将这个代理对象与我们创建的执行类对象关联。
         */
        Subject proxy = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(),new Class[]{Subject.class},handler);
        printProxyClass(proxy);
        proxy.Hello("test");
    }
    /**
     * 这个示例中，抽象主题是Comparable接口，真实主题是Integer类
     * 当执行二分查找时，需要执行Comparable接口里的compareTo方法时就会转到handler里的invoke中执行
     */
    private static void proxyExample2(){
        var numArray = new Object[1000];
        for(int i = 0; i<numArray.length; i++){
            Integer num = i;
            MyProxyHandler handler = new MyProxyHandler(num);
            Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{
                Comparable.class
            },handler);
            numArray[i] = proxy;
        }
        int result = Arrays.binarySearch(numArray,10);
    }
    private static void printProxyClass(Object proxy){
        Class cl = proxy.getClass();
        Method[] methods = cl.getDeclaredMethods();
        System.out.println("代理类："+cl.getName());
        System.out.print("类方法：");
        for(var m:methods){
            System.out.print("["+m.getName()+"]");
        }
        System.out.println();
    }
}
