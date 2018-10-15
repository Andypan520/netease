package common;

import com.google.common.base.Ticker;
import com.google.common.cache.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by pandechuan on 2018/1/24.
 */
public class CacheUtil {
    private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);

    public static <K, V> Cache<K, V> callableCached(int maxSize, long expireTime, TimeUnit timeUnit) throws Exception {
        Cache<K, V> cache = CacheBuilder.newBuilder().maximumSize(maxSize).expireAfterWrite(expireTime, timeUnit)
                .weakKeys().softValues().ticker(Ticker.systemTicker()).recordStats().recordStats()
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        logger.info("Remove a map entry which key is {},value is {},cause is {}.", notification.getKey(),
                                notification.getValue(), notification.getCause().name());
                    }
                }).build();
        return cache;
    }

    /**
     * CacheLoader way，offer a Unified method to load value。
     */
    public static <K, V> LoadingCache<K, V> loaderCached(CacheLoader<K, V> cacheLoader, int maxSize, long expireTime,
                                                         TimeUnit timeUnit) {
        LoadingCache<K, V> cache = CacheBuilder.newBuilder().maximumSize(maxSize).weakKeys().softValues()
                .expireAfterWrite(expireTime, timeUnit).build(cacheLoader);
        return cache;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<Integer, Integer> cache = loaderCached(new CacheLoader<Integer, Integer>() {
            @Override
            public Integer load(Integer key) throws Exception {
                System.out.println("from db..");
                return key;
            }
        }, 10, 3, TimeUnit.SECONDS);
// callableCached(10, 3, timeUnit)
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
        Thread.sleep(1000);
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
        System.out.println(cache.getAll(Lists.newArrayList(1, 2, 3)));
    }
}
