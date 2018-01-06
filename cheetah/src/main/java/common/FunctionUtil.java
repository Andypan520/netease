package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by pandechuan on 2018/1/6.
 */
public class FunctionUtil {

    private static final Logger logger = LoggerFactory.getLogger(FunctionUtil.class);

    @FunctionalInterface
    public interface CheckedConsumer<T> {
        void accept(T input) throws Exception;
    }

    @FunctionalInterface
    public interface CheckedPredicate<T> {
        boolean test(T input) throws Exception;
    }

    @FunctionalInterface
    public interface CheckedFunction<F, T> {
        T apply(F input) throws Exception;
    }

    /**
     * Return a function which rethrows possible checked exceptions as runtime exception.
     */
    public static <F, T> Function<F, T> function(CheckedFunction<F, T> function) {
        return input -> {
            try {
                return function.apply(input);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Return a predicate which rethrows possible checked exceptions as runtime exception.
     */
    public static <T> Predicate<T> predicate(CheckedPredicate<T> predicate) {
        return input -> {
            try {
                return predicate.test(input);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Return a consumer which rethrows possible checked exceptions as runtime exception.
     */
    public static <T> Consumer<T> consumer(CheckedConsumer<T> consumer) {
        return input -> {
            try {
                consumer.accept(input);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * 方法重试
     *
     * @param retryTime 重试次数
     */
    public static <T> T retry(int retryTime, Supplier<T> supplier) {
        int retry = 0;
        while (retry < retryTime - 1) {
            retry++;
            try {
                return supplier.get();
            } catch (Exception e) {
                logger.warn("方法执行失败，进行第" + retry + "次重试!", e);
            }
        }
        return supplier.get();
    }

    public static void main(String[] args) {
        Consumer<String> consumer = FunctionUtil.consumer(Integer::parseInt);
        Consumer<String> consumer2 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                Integer.parseInt(s);
            }
        };
        consumer2.accept("23a");
        Function<Integer, String> function = FunctionUtil.function(T -> String.valueOf(T));
        String str = function.apply(69);
        System.out.println(str);

        Predicate<String> predicate = FunctionUtil.predicate(T -> Boolean.valueOf(T));
        System.out.println(predicate.test("1"));

    }
}
