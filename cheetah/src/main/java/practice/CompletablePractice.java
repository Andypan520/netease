package practice;


import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * Created by pandechuan on 2018/11/28.
 */
public class CompletablePractice {

    static ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
        int count = 1;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "custom-executor-" + count++);
        }
    });

    @Test
    //创建完整的 CompletableFuture
    public void completedFutureExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");

        Assert.isTrue(cf.isDone());

        System.out.println(cf.getNow("a"));
    }

    @Test
    //运行简单的异步场景
    public void runAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            //使用 ForkJoinPool 实现异步执行，这种方式使用了 daemon 线程执行 Runnable 任务
            Assert.isTrue(Thread.currentThread().isDaemon());
            randomSleep();
        });
        Assert.isTrue(!cf.isDone());
        sleepEnough();
        Assert.isTrue(cf.isDone());
    }

    @Test
    //同步执行动作示例
    public void thenApplyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            Assert.isTrue(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        Assert.isTrue("MESSAGE".equals(cf.getNow(null)));
    }


    //使用固定的线程池完成异步执行动作示例

    @Test
        //异步执行动作示例，以下代码实现了异步方式，仅仅是在上面的代码里的多个方法增加"Async"这样的关键字。
    void thenApplyAsyncExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            randomSleep();
            return s.toUpperCase();
        });
        Assert.isNull(cf.getNow(null));
        //Returns the result value when complete
        System.out.println("1");
        Assert.isTrue("MESSAGE".equals(cf.join()));
        System.out.println("2");
    }

    @Test
    public void thenApplyAsyncWithExecutorExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            Assert.isTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
            Assert.isTrue(!Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        }, executor);
        Assert.isNull(cf.getNow(null));
        Assert.isTrue("MESSAGE".equals(cf.join()));
    }

    // 作为消费者消费计算结果示例
    //生产者（前一次计算）-消费者（本次计算）模式
    @Test
    public void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> result.append(s));
        Assert.isTrue(result.length() > 0);
    }


    //异步消费示例
    @Test
    public void thenAcceptAsyncExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(s -> result.append(s));
        cf.join();
        Assert.isTrue(result.length() > 0);
    }


    //运行两个阶段后执行
    @Test
    public void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase)
                .runAfterBoth( //两个阶段都完成之后，执行runnable
                        CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                        () -> result.append("done"));
        Assert.isTrue(result.length() > 0, "Result was empty");
    }

    @Test
    public void thenAcceptBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase)
                .thenAcceptBoth(//biConsumer 消费两阶段结果
                        CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                        (s1, s2) -> result.append(s1 + s2));
        Assert.isTrue("MESSAGEmessage".equals(result.toString()));
    }

    //整合两个计算结果
    @Test
    public void thenCombineExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(String::toUpperCase)
                .thenCombine(//biFunction  与上面的区别之处
                        CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                        (s1, s2) -> s1 + s2);
        Assert.isTrue(cf.getNow(null).equals("MESSAGEmessage"));
    }


    //多stage 的链式整合
    @Test
    public void thenCombineAsyncExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApplyAsync(String::toLowerCase)
                .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(String::toUpperCase),
                        (s1, s2) -> s1 + s2);

        //join()的作用是："等待该线程终止
        Assert.isTrue("MESSAGEmessage".equals(cf.join()));
    }

    //除了 thenCombine()方法以外，还有另外一种方法-thenCompose()，这个方法也会实现两个方法执行后的返回结果的连接
    @Test
    public void thenComposeExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(String::toUpperCase)
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(String::toLowerCase)
                        .thenApply(s -> upper + s));
        Assert.isTrue("MESSAGEmessage".equals(cf.join()));
    }


    //使用 anyOf()方法后返回的任何一个值都会立即触发 CompletableFuture。
    // 然后我们使用 whenComplete(BiConsumer<? super Object, ? super Throwable> action)方法处理结果。
    @Test
    public void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                //同步
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(String::toUpperCase))
                .collect(Collectors.toList());

        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if (th == null) {
                System.out.println(res);
                result.append(res);
            }
        });
        System.out.println(result);
    }


    //当所有的 CompletableFuture 完成后创建 CompletableFuture
    @Test
    public void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                //同步
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(String::toUpperCase))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> System.out.println(cf.getNow(null)));
            result.append("done");
        });
    }

    //当所有的 CompletableFuture 完成后创建 CompletableFuture
    @Test
    public void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                //同步
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(String::toUpperCase))
                .collect(Collectors.toList());

        CompletableFuture allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> System.out.println(cf.getNow(null)));
            result.append("done");
        });
        System.out.println("=======");
        allOf.join();
        System.out.println(result);

    }


    void randomSleep() {

        Random rd = new Random(10);

        try {
//            Thread.sleep(rd.nextInt());
            Thread.sleep(500L);
        } catch (Exception e) {

        }
    }

    void sleepEnough() {
        Random rd = new Random(100);

        try {
            // Thread.sleep(rd.nextInt());
            Thread.sleep(1000L);
        } catch (Exception e) {

        }
    }
}
