package evolution;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by pandechuan on 2018/11/28.
 */
@Slf4j
public class CompletableFutureTest {

    public Future<Double> getPriceAsync(final String product) {
        final CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePrice(product);
            //完成后使用complete方法，设置future的返回值
            futurePrice.complete(price);
        }).start();

        return futurePrice;
    }

    //简化版
    public Future<Double> getPriceAsync2(final String product) {
        Future<Double> price = CompletableFuture.supplyAsync(() -> calculatePrice(product));
        return price;
    }


    /**
     * CompletableFuture可以组合多个Future，
     * 不管是Future之间有依赖的，还是没有依赖的。
     * <p>
     * 如果第二个请求依赖于第一个请求的结果，那么可以使用thenCompose方法来组合两个Future.
     * <p>
     * supplyAsync方法第二个参数接受一个自定义的Executor。
     * 首先使用CompletableFuture执行一个任务，调用getPrice方法，得到一个Future，之后使用thenApply方法，将Future的结果应用parse方法，
     * 之后再使用执行完parse之后的结果作为参数再执行一个applyCount方法，然后收集成一个CompletableFuture<String>的List，
     * 最后再使用一个流，调用CompletableFuture的join方法，这是为了等待所有的异步任务执行完毕，获得最后的结果。
     * <p>
     * 注意，这里必须使用两个流，如果在一个流里调用join方法，那么由于Stream的延迟特性，所有的操作还是会串行的执行，并不是异步的。
     *
     * @param product
     * @return
     */
//
//    public List<String> findPriceAsync(String product) {
//        List<CompletableFuture<String>> priceFutures = tasks.stream()
//                .map(task -> CompletableFuture.supplyAsync(() -> task.getPrice(product), executor))
//                .map(future -> future.thenApply(Work::parse))
//                .map(future -> future.thenCompose(work -> CompletableFuture.supplyAsync(() -> Count.applyCount(work), executor)))
//                .collect(Collectors.toList());
//
//        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
//    }
    private double calculatePrice(String product) {

        try {
            Thread.sleep(2000L);
        } catch (Exception e) {
            log.error("", e);
        }
        return 9.45;
    }
}

