package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * Created by pandechuan on 2018/1/18.
 */
public class ConcurrencyUtil {
    private Logger logger = LoggerFactory.getLogger(ConcurrencyUtil.class);

    public static <U> Future<U> getAsyncFuture(Supplier<U> supplier) {

        return CompletableFuture.supplyAsync(supplier);
    }

    public static <U> Future<U> getAsyncFuture(Supplier<U> supplier, Executor executor) {

        return CompletableFuture.supplyAsync(supplier, executor);
    }


}
