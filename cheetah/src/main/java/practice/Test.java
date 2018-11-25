package practice;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pandechuan on 2018/11/20.
 */
public class Test implements Runnable {
    ReentrantLock lock = new ReentrantLock();

    public void get() {
        lock.lock();
        System.out.println(Thread.currentThread().getId());
        set();
        lock.unlock();
    }

    public void set() {
        lock.lock();
        System.out.println(Thread.currentThread().getId());
        lock.unlock();
    }

    @Override
    public void run() {
        get();
    }

    public static void main(String[] args) {
        Test ss = new Test();
        new Thread(ss).start();
        new Thread(ss).start();
        new Thread(ss).start();
    }
}