package practice.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by pandechuan on 2018/11/30.
 */
public class CyclicBarrierTest {

    private static final int THREAD_NUM = 5;

    public static void main(String[] args) {

//        CyclicBarrier cb = new CyclicBarrier(THREAD_NUM * 2, () -> System.out.println("Inside Barrier"));
        CyclicBarrier cb = new CyclicBarrier(THREAD_NUM, () -> System.out.println("Inside Barrier"));

        for (int i = 0; i < 10; i++) {
            new Thread(new WorkerThread(cb)).start();
        }
    }

    public static class WorkerThread implements Runnable {

        CyclicBarrier barrier;

        public WorkerThread(CyclicBarrier b) {
            this.barrier = b;
        }

        @Override
        public void run() {
            try {
                System.out.println("ID:" + Thread.currentThread().getId() + " Worker's waiting");
                // 线程在这里等待，直到所有线程都到达barrier。
                barrier.await();
                System.out.println("ID:" + Thread.currentThread().getId() + " Working");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
