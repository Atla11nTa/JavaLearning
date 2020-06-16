package com.Collection;

import java.util.Random;
import java.util.concurrent.*;

public class Concurrency {
    static class Mythread extends Thread{
        int i;
        Mythread(int i){
            this.i = i;
        }
        public void run(){
            System.out.println(i);
        }
    }

    public static void task(){

    }

    public static void main(String[] args) {
        //不推荐的方法
        Mythread t2 = new Mythread(2);
        t2.start();

        //推荐方法，实现Runable接口，而不是继承Thread类，应把运行机制和运行任务解藕。这里是lambda写法
        Runnable r = ()->{
            //task
        };
        Thread t3 = new Thread(r);
        //这里是lambda的函数调用写法
        Thread t4 = new Thread(Concurrency::task);

        /**
         * 线程池使用实例
         */
        // 1.创建一个线程池
        //动态拓展的线程池，空闲60s后回收
        ExecutorService threadPool1 = Executors.newCachedThreadPool();
        // 固定大小的线程池
        ExecutorService threadPool2 = Executors.newFixedThreadPool(2);
        // 单线程的线程池，主要用途是为了比较单线程和多线程下任务执行速度
        ExecutorService threadpool3 = Executors.newSingleThreadExecutor();
        // 用于执行调度任务的线程池，固定大小。调度任务指延迟执行或者周期性执行的任务。
        ScheduledExecutorService threadPool4 = Executors.newScheduledThreadPool(2);
        // 用于执行调度任务的线程池，单线程。
        ScheduledExecutorService threadPool5 = Executors.newSingleThreadScheduledExecutor();

        // 2. 创建一个任务
        Runnable r1 = ()->{ };
        // Callable类型的任务，任务具有返回值
        Callable<Integer> r2 = ()->{
            return 1;
        };

        // 3. 向线程池提交任务，并用Future接口的对象跟踪执行状态和结果
        threadPool1.submit(r1);
        Future<Integer> taskResult = threadPool1.submit(r2);

        // 4. 回收线程池, shutdown()方法不再接收新任务，待执行的任务执行完关闭，shutdownNow是不再接收新任务，并且所有在等待的任务也不执行了。
        threadPool1.shutdown();
        threadPool1.shutdownNow();

    }
}
