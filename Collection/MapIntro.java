package com.Collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapIntro {
    public static void main(String[] args) {

        /**
         * HashMap底层采用Node类型的table数组做存储，Node类实现了Entry接口，包含key,value,next三个字段
         * entrySet只是一个视图集，用于查看
         * 通常HashMap的实际结构是数组+链表，但是当某个桶的链表长度超过8,就把链表改为红黑树
         * get(key)方法根据key的hash值找到其在数组中的索引位置，然后再遍历next，找到具体的Node节点，关于hash计算索引在下面HashTable处有详细理解
         * replace()方法可以更新值,方式类似get()方法
         * 为何要采用红黑树？既然红黑树这么好，为什么不直接采用红黑树？
         * 首先，Hash结构是为了快速查找，红黑树查找O(logn)，而链表查找通过遍历O(n)，使用红黑树肯定是优于链表的
         * 但是红黑树数节点占用2倍空间，而且插入新节点需要进行左旋右旋操作，所以消耗比较大，当节点数较小时，红黑树的查找优势并不大。
         * 对于为什么选择8这个数，因为从概率论的角度分析，只要哈希函数符合泊松分布，链长大于8的概率是极小的。
         */
        HashMap<Integer,Integer> hashMap = new HashMap<>();
//        hashMap.replace(1,2);
//        hashMap.forEach((k,v)->{
//            System.out.println(k+v);
//        });

        /**
         * 这种HashMap是为了配合垃圾回收器回收无任何引用的空间
         * 通过引用队列ReferenceQueue实现
         */
        WeakHashMap<Integer,Integer> weakHashMap = new WeakHashMap<>();

        /**
         * 在HashMap的基础上改变了基本数据单元，其内部静态类Entry继承了HashMap的Node，新增before和after两个指针，分别指向上一个和下一个单元
         * Linked增加了head和tail指针，结合每个数据单元的before和after指针，使整个结构按照加入顺序组成一个双向链表
         * 由此改变了迭代访问的方式，现在是根据双向链表访问。
         * 当初始化时accessOrder字段设置为true时，每次get和put时，包括replace，但迭代器访问不影响，都会把受影响的单元移到双向链表的尾部，注意在桶中的位置并不改变.
         * 根据这个特性可以快速的删除不经常访问的元素
         */
        LinkedHashMap<Integer,Integer> linkedHashMap = new LinkedHashMap<>(10,0.75F,true);
        linkedHashMap.put(1,1);
        linkedHashMap.put(2,2);
        linkedHashMap.put(3,3);
        //通过迭代器访问不会影响
//        var it = linkedHashMap.entrySet().iterator();
//        it.next();
//        linkedHashMap.forEach((k,v)->{
//            System.out.println(k);
//        });
        //不可更改的视图
        Map<Integer,Integer> linkedHashMap2 = Collections.unmodifiableMap(linkedHashMap);

        /**
         * 线程安全的Map类
         */

        /**
         * 使用synchronized关键字实现线程安全
         * 对比HashMap，没有红黑树的设计，其次在hash计算索引上也有所区别。
         * HashTable直接通过key.hashcode与数组长度-1求余得到在数组中的索引值。
         * 因为求余操作速度很慢，所以HashMap进行了改进，改成了hash值&数组长度-1，数组长度总是pow(2,n)，所以索引值实际是hash值的低n-1位
         * 但是仅取hash值的n-1位作为索引，就很容易产生冲突，为了解决这种缺陷，进行了二次哈希，将key.hashcode的高16位与低16位求异或得hash
         */
        Hashtable<Integer,Integer> hashtable = new Hashtable<>();
        // 数据结构与HashMap一致，利用CAS+Synchronized关键字实现线程安全, get()方法仅通过CAS实现，这也是查找速度快的原因
        ConcurrentHashMap<Integer,Integer> concurrentHashMap = new ConcurrentHashMap<>();
        // 键值有序的线程安全的映射表。在高并发环境下，具有更快的存取速度
        ConcurrentSkipListMap<Integer,Integer> concurrentSkipListMap = new ConcurrentSkipListMap<>();

        //除此之外还有Collections集合下的线程安全的映射表，都是通过synchronized关键字实现
        Map<Integer,Integer> map = new HashMap<>();
        Map<Integer,Integer> synchronizedMap = Collections.synchronizedMap(map);
    }
}
