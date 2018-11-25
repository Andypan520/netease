package practice;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by pandechuan on 2018/11/24.
 */
public class ThreadLocalTest {
    /*定义了1个ThreadLocal<Integer>对象，必须overwrite initalValue()，并复写它的initialValue方法，初始值是3*/
    private ThreadLocal<Integer> thradLocalA = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 3;
        }
    };

    private ThreadLocal<Integer> threadLocalB = ThreadLocal.withInitial(() -> 5);

    /*设置一个信号量，许可数为1，让三个线程顺序执行*/
    Semaphore semaphore = new Semaphore(1);
    private Random rnd = new Random();

    /*Worker定义为内部类实现了Runnable接口,tlA定义在外部类中，
每个线程中调用这个对象的get方法，再调用一个set方法设置一个随机值*/
    public class Worker implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(rnd.nextInt(1000)); /*随机延时1s以内的时间*/
                // semaphore.acquire();/*获取许可*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int valA = thradLocalA.get(); // 3
            System.out.println(Thread.currentThread().getName() + " thradLocalA initial val : " + valA);
            valA = rnd.nextInt();
            thradLocalA.set(valA); // random
            System.out.println(Thread.currentThread().getName() + " thradLocalA  new     val: " + valA);
//
//            int valB = threadLocalB.get();
//            System.out.println(Thread.currentThread().getName() + " threadLocalB initial val : " + valB);
//            valB = rnd.nextInt();
//            threadLocalB.set(valB);
//            System.out.println(Thread.currentThread().getName() + " threadLocalB 2    new val: " + valB);
            semaphore.release();
            thradLocalA.remove(); // 3
        }
    }

    /*创建三个线程，每个线程都会对ThreadLocal对象tlA进行操作*/
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        es.execute(threadLocalTest.new Worker());
        es.execute(threadLocalTest.new Worker());
        es.execute(threadLocalTest.new Worker());
        es.shutdown();
    }
}
