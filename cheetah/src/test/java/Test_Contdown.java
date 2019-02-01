import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author pandechuan 2019/01/28
 */
public class Test_Contdown {
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
//        Test_Contdown test = new Test_Contdown();
//        new Thread(() -> {
//            test.method1("method1");
//        }).start();
//        new Thread(() -> {
//            test.method2("method2");
//        }).start();
//        new Thread(() -> {
//            test.method1("method3");
//        }).start();


        final CountDownLatch begin = new CountDownLatch(1);

        // 结束的倒数锁
        final CountDownLatch end = new CountDownLatch(5);

        // 十个线程相当于十个选手
        final ExecutorService exec = Executors.newFixedThreadPool(10);

        for (int index = 0; index < 5; index++) {
            final int NO = index + 1;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 如果当前计数为零，则此方法立即返回。
                        begin.await();
                        Thread.sleep((long) (Math.random() * 100));
                        System.out.println("No." + NO + " arrived");
                    } catch (InterruptedException e) {
                    } finally {
                        // 每个选手到达终点时，end就减一
                        end.countDown();
                    }
                }
            };
            exec.submit(run);
        }
        System.out.println("Game Start……");
        // begin减一，开始游戏
        begin.countDown();
        // 等待end变为0，即所有选手到达终点
        end.await();
        System.out.println("Game Ove……");
        exec.shutdown();
    }

    public void method1(String name) {
        try {
            //countDownLatch.await();  //阻塞线程,到countDownLatch对象的count等于0时唤醒
            countDownLatch.await(); //阻塞线程,如果在10秒后还没有被唤醒,将自动唤醒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + " - " + name + " 被唤醒");
    }

    public void method2(String name) {
        System.out.println(Thread.currentThread().getName() + "  " + name);
        try {
            TimeUnit.SECONDS.sleep(5);
            countDownLatch.countDown(); // 将count值减1
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
