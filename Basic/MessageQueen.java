package com.Basic;

import java.sql.Struct;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消息队列实现
 * Uploader类往消息队列中上传消息
 * User类订阅了消息队列
 * 当有新消息时MessageQueen告知每个用户。
 */
public class MessageQueen {
    private ReentrantLock lock = new ReentrantLock();
    private Condition hasNewMessageCondition = lock.newCondition();
    private static Queue<String> messageQueen = new LinkedList<>();
    private static List<User> users = new ArrayList<>();
    //往消息队列中插入消息
    public void InsertMessage(String str){
        lock.lock();
        try {
            messageQueen.offer(str);
            //告诉在等待的线程，有新消息到了
            hasNewMessageCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }
    //分发消息
    public void SendMessage(){
        lock.lock();
        try {
            while(messageQueen.size() == 0)
                hasNewMessageCondition.await();
            for (var user:users){
                user.setMessage(messageQueen.peek());
            }
            messageQueen.poll();
            hasNewMessageCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    //注册订阅者
    public void addUser(User user){
        users.add(user);
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        MessageQueen messageQueen = new MessageQueen();
        User user1 = new User();
        User user2 = new User();
        messageQueen.addUser(user1);
        messageQueen.addUser(user2);
        Uploader up = new Uploader(messageQueen);

        //线程1不停的分发消息
        threadPool.submit(()->{
            while(true){
                messageQueen.SendMessage();
            }
        });

        //user1不停的读取
        threadPool.submit(()->{
            while (true){
                System.out.println("user1读取到消息："+user1.getMessage());
            }
        });

        threadPool.submit(()->{
            while (true){
                System.out.println("user2读取到消息："+user2.getMessage());
            }
        });

        threadPool.submit(()->{
            int i=1;
            while (true){
                up.UpMessage("这是第"+ i +"条消息");
                System.out.println("已经上传消息：这是第"+i+"条消息");
                i++;
                Thread.sleep(2000);
            }
        });
    }

}

//用户订阅消息
class User{
    private volatile String message;
    private volatile boolean messageUpdate = false;
    private ReentrantLock lock = new ReentrantLock();
    private Condition hasNewMessage = lock.newCondition();
    public String getMessage(){
        lock.lock();
        try {
            while(!messageUpdate)
                hasNewMessage.await();
            //更新状态
            messageUpdate = false;
            hasNewMessage.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return message;
    }
    public void setMessage(String message){
        lock.lock();
        try {
            this.message = message;
            messageUpdate = true;
            hasNewMessage.signalAll();
        }finally {
            lock.unlock();
        }
    }
}

class Uploader{
    private MessageQueen messageQueen;
    public Uploader(MessageQueen messageQueen){
        this.messageQueen = messageQueen;
    }
    public void UpMessage(String str){
        messageQueen.InsertMessage(str);
    }
}