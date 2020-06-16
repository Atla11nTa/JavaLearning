package com.Collection;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class Set {
    public static void main(String ...args){
        /**
         * 基于哈希的思想,但底层并不是通过数组和链表实现，而是通过HashMap，当然HashMap的普通实现也是通过数组和链表
         * 其中元素作为HashMap的Key，而value统一引用的一个静态成员变量PRESENT
         */
        HashSet<Integer> hashSet = new HashSet<>(10);
        /**
         * 底层数据结构为NavigableMap，是一种有序表，排序基于红黑树,为了排序，所以其范型类必须实现了Comparable接口或者声明TreeSet时提供一个Comparator
         * 每次添加元素都加入到合适的位置上，所以迭代器访问都是有序的
         */
        TreeSet<Integer> treeSet = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return Integer.compare(integer, t1);
            }
        });

        /**
         * 直接通过LinkedHashMap实现的，在桶中对象按加入顺序连接起来，所以迭代器访问顺序和加入顺序一致
         */
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();

        /**
         * 线程安全的Set
         */
        // 有序的线程安全的Set，底层基于ConcurrentSkipListMap实现
        ConcurrentSkipListSet<Integer> concurrentSkipListSet = new ConcurrentSkipListSet<>();
        java.util.Set<Integer> synchronizedSet = Collections.synchronizedSet(hashSet);

    }
}
