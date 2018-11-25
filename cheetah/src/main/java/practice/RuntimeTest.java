package practice;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Created by pandechuan on 2018/11/22.
 */
public class RuntimeTest {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("close client");
                } catch (Throwable e) {
                } finally {
                    System.out.println("##  client is down.");
                }
            }

        });
        System.out.println("before stop jvm");
        System.exit(0);
    }
}
