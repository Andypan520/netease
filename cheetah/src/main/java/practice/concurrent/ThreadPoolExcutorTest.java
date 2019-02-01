package practice.concurrent;

import lombok.Data;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pandechuan on 2018/11/30.
 */
@Data
public class ThreadPoolExcutorTest implements Runnable {


    public String name;

    public ThreadPoolExcutorTest(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1,
                2,
                1L,
                TimeUnit.SECONDS,
                workQueue,
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        String threadName = "Task" + atomicInteger.addAndGet(1);
                        Thread thread = new Thread(r);
                        thread.setName(threadName);
                        return thread;
                    }
                },
//                new RejectedExecutionHandler() {
//                    @Override
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                        System.out.println(r+"==="+executor);
//                    }
//                }
                (r, executor) -> {
                    //  System.out.println(r + "===" + executor);
                    if (!executor.isShutdown()) {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        threadPool.execute(new ThreadPoolExcutorTest("任务1"));
        threadPool.execute(new ThreadPoolExcutorTest("任务2"));
        threadPool.execute(new ThreadPoolExcutorTest("任务3"));
        threadPool.execute(new ThreadPoolExcutorTest("任务4"));
        threadPool.execute(new ThreadPoolExcutorTest("任务5"));
        threadPool.execute(new ThreadPoolExcutorTest("任务6"));

        threadPool.shutdown();

    }

    @Override
    public void run() {
        System.out.println(name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
