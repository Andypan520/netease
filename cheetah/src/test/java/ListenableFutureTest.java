import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by pandechuan on 2018/10/24.
 */
public class ListenableFutureTest {

    @Test
    public void ListenableTest() {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        final List<Long> value = new ArrayList<Long>();
        List<ListenableFuture<Long>> futures = new ArrayList<ListenableFuture<Long>>();
        for (long i = 1; i <= 3; i++) {
            // 处理线程逻辑
            final ListenableFuture<Long> listenableFuture = executorService.submit(() -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return 3L;

            });
            // 回调方法
            Futures.addCallback(listenableFuture, new FutureCallback<Long>() {
                @Override
                public void onSuccess(Long result) {
                    value.add(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
            futures.add(listenableFuture);
        }
        // 阻塞三个线程执行完成
        Futures.allAsList(futures);
        long result = 0;
        for (int i = 0, n = value.size(); i < n; i++) {
            result += value.get(i);
        }
        System.out.println("sum:" + result);
        executorService.shutdownNow();
    }
}

