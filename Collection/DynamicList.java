package com.Collection;

import java.util.*;

/**
 * 动态list结构，分为ArrayList和LinkedList
 */

public class DynamicList {
    public static void main(String ...args){
        /**
         * 关于ArrayList，底层数据结构为Object数组
         * 1. 关于static类型的EMPTY_ELEMENTDATA
         * 这是为了节约内存，让所有空的实例都引用同一个空数组，当增加元素时再申请新的空间
         *
         * 2. elementData为何用transient修饰
         * 首先：transient用来表示一个域不是该对象序行化的一部分，当一个对象被序行化的时候，
         * transient修饰的变量的值是不包括在序行化的表示中的。但是ArrayList又是可序行化的类，
         * elementData是ArrayList具体存放元素的成员，用transient来修饰elementData，岂不是反序列化后的ArrayList丢失了原先的元素？
         *
         * 原因：ArrayList中有WriteObject和ReadObject方法，ArrayList在序列化的时候会调用writeObject，直接将size和element写入ObjectOutputStream；
         * 反序列化时调用readObject，从ObjectInputStream获取size和element，再恢复到elementData。
         * 为什么不直接用elementData来序列化，而采用上诉的方式来实现序列化呢？
         * 原因在于elementData是一个缓存数组，它通常会预留一些容量，等容量不足时再扩充容量，那么有些空间可能就没有实际存储元素，
         * 采用上诉的方式来实现序列化时，就可以保证只序列化实际存储的那些元素，而不是整个数组，从而节省空间和时间。
         */
        ArrayList<Integer> intList = new ArrayList<>();

        Iterator<Integer> it = intList.iterator();
        /**
         * 底层数据结构为双向链表，结合迭代器Iterator进行数据访问
         * LinkedList有get(index)方法，但是是通过遍历链表实现的，效率低下。
         * 需要随机访问时，因避免使用LinkedList，因为LinkedList是为了方便插入和删除
         * 随机方法应该使用ArrayList
         */
        LinkedList<Integer> intLinkedList = new LinkedList<>();
        //这两种方式是一样的，iterator()也是返回的listIterator实例
        Iterator<Integer> it2 = intLinkedList.iterator();
        intLinkedList.add(1);
        //调用的就是该集合的迭代器
        for(int i:intLinkedList){
            System.out.println(i);
        }
    }
}
