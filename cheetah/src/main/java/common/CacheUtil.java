package common;

import com.google.common.base.Ticker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by pandechuan on 2018/1/24.
 */
public class CacheUtil {
    /**
     * this is a  more flexible method，caller is able to give a Callable when call get(),which allow us use diffenent Strategy to load value
     *
     * @param maxSize the max size of cache item
     */
    public static <K, V> Cache<K, V> callableCached(int maxSize, long expireTime, TimeUnit timeUnit) throws Exception {
        Cache<K, V> cache = CacheBuilder.newBuilder().maximumSize(maxSize).expireAfterWrite(expireTime, timeUnit)
                .weakKeys().softValues().ticker(Ticker.systemTicker()).recordStats().build();
        return cache;
    }


    /**
     * CacheLoader way，offer a Unified method to load value。
     */
    public static <K, V> LoadingCache<K, V> loaderCached(CacheLoader<K, V> cacheLoader, int maxSize, long expireTime,
                                                         TimeUnit timeUnit) {
        LoadingCache<K, V> cache = CacheBuilder.newBuilder().maximumSize(maxSize).weakKeys().softValues()
                .expireAfterWrite(expireTime, timeUnit).build(cacheLoader);
        /*
         * .removalListener(new RemovalListener<K, V>() {
         *
         * @Override public void onRemoval(RemovalNotification<K, V> rn) {
         * System.out.println(rn.getKey() + "was removed!");
         *
         * } })
         */
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
        Thread.sleep(2000);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.getAll(Lists.newArrayList(1, 2, 3)));
    }
}
