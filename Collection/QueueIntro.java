package com.Collection;

import com.sun.source.tree.SynchronizedTree;

import java.util.*;
import java.util.concurrent.*;

/**
 * 普通队列的接口分为Queue和Deque（双端队列），Deque继承自Queue
 * LinkedList和ArrayDeque实现了Deque接口。
 * 除此之外还有个优先队列 PriorityQueue
 */
public class QueueIntro {
    public static void main(String ...args){
        //双端队列的链表实现
        Deque<Integer> deque1 = new LinkedList<>();
        //双端队列的数组实现
        Deque<Integer> deque2 = new ArrayDeque<>();

        /**
         * 优先队列是通过最小堆实现的，具体使用数组实现的最小堆，所以使用迭代器访问的顺序是层次遍历小顶堆的顺序
         * 队列首部总是最小值。
         */
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        /**
         * 线程安全阻塞型队列,这些类都实现的BlockingQueue接口，内部使用ReentrantLock锁和Condition条件实现线程安全。
         */
        //线程安全的队列，链表实现
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>();
        //线程安全的双端队列，双向链表实现
        LinkedBlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<>();
        //线程安全的队列，数组实现。需要指定长度。可设置为公平锁
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        //线程安全的优先队列。
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        //
        LinkedTransferQueue<Integer> linkedTransferQueue = new LinkedTransferQueue<>();

        /**
         * 以LinkedBlockingQueue举例说明这几个方法的区别
         * 与之对应的remove()/poll()/take()是类似的
         */

        // 底层调用的还是offer方法，不过在调用前会先检查满了没，满了直接抛出队满异常
        linkedBlockingQueue.add(1);
        // 先检查队满没，队满直接返回false，否则获得锁然后再次检查队满没，队满返回false，否则插入然后释放锁，返回true。
        linkedBlockingQueue.offer(1);
        // 阻塞方法，先获得锁，然后检查是否队满，队满就执行await()。
        // 入队成功后，检查是否还存在一个空位，有的话执行signal()方法
        try{
            linkedBlockingQueue.put(1);
        }catch (InterruptedException e){
        }

        /**
         * 线程安全的非阻塞型队列，内部没有使用锁机制，而是采用CAS和volatile关键实现线程安全
         */
        //线程安全的非阻塞型队列，链表实现
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        //线程安全的非阻塞型双端队列，双向链表实现
        ConcurrentLinkedDeque<Integer> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
        //add方法底层调用的offer
        concurrentLinkedQueue.add(1);
        // 利用VarHandle类提供的方法和循环CAS实现线程安全的入队
        concurrentLinkedQueue.offer(1);
        concurrentLinkedQueue.poll();
//        Runnable r1 = ()->{
//            for(int i=0;i<10;i=i+2){
//                concurrentLinkedQueue.offer(i);
//            }
//        };
//        Runnable r2 = ()->{
//            for(int i=1;i<10;i=i+2){
//                concurrentLinkedQueue.offer(i);
//            }
//        };
//        Thread t1 = new Thread(r1);
//        t1.start();
//        Thread t2 =new Thread(r2);
//        t2.start();
//        out:
//        {
//            while(true){
//                while(t1.getState()==Thread.State.TERMINATED && t2.getState()==Thread.State.TERMINATED){
//                    System.out.println(Arrays.toString(concurrentLinkedQueue.toArray()));
//                    break out;
//                }
//            }
//        }
    }
}
