package com.Basic;

/**
 * 设计模式之: 单例模式
 * 思想: 此类仅支持创建一个实例.
 * 关于单例模式: Java的实现与相关知识点:https://blog.csdn.net/fd2025/article/details/79711198
 * 三种基本实现思路, 包括三种线程安全的具体实现
 * 单例模式的破坏:
 * 1. 反射: 通过反射调用类构造函数创建实例,越过private关键字对构造函数的访问限制.
 * 2. 序列化: 通过序列化得到
 * 3. 调用clone(): 重写clone()方法,并且直接调用父类的clone(), super.clone(),这样创建的对象是一个新实例,破坏了单例模式.
 * 破坏的解决方案:
 * 1. 破坏反射: 添加一个静态变量记录实例是否已经创建, 在构造函数里判断,若已经创建,抛出运行时异常
 * 2. 破坏序列化: 添加readResolve(), 方法体内直接返回实例.
 * 3. 破坏克隆,重写clone(), 方法体内直接返回实例.
 */


/**
 * 实现1: 懒汉式
 * 思路: 使用时才创建,线程不安全
 * 特点: 使用时创建,线程不安全
 */
class Singleton1{
    private static Singleton1 singleton = null;

    private Singleton1() {
    }

    private static Singleton1 getSingleton() {
        if (singleton == null) {
            singleton = new Singleton1();
        }
        return singleton;
    }
}

/**
 * 实现2: 线程安全
 * 特点: 使用时创建,同步锁实现线程安全
 */
class Singleton2{
    private static Singleton2 instance = null;
    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        if (instance == null) {
            synchronized (Singleton2.class) {
                if (instance == null) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }
}

/**
 * 实现3: 饿汉式(时间换空间)
 * 思路: 类加载时就创建实例对象.
 * 特点: 加载时创建,线程安全
 */
class Singleton3 {
    private static Singleton3 singleton1 = new Singleton3();

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return singleton1;
    }
}

/**
 * 实现4: 静态内部类实现(线程安全)
 * 原理: 饿汉式和懒汉式结合, 内部类在使用时才会被加载, 而且实例是静态字段,所以只会被虚拟机初始化一次, 不存在多线程下被多次创建的情况.
 * 特点: 使用时创建,线程安全. 推荐
 */

class Singleton4{
    private static class SingletonHolder {
        private static  Singleton4 instance = new Singleton4();
    }

    private Singleton4() {

    }

    public static Singleton4 getInstance() {
        return SingletonHolder.instance;
    }
}

