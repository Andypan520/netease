package common;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by pandechuan on 2018/1/6.
 */
public class OptionalUtil {
    /**
     * 防止取值NPE方法
     */
    public static <T> Optional<T> resolve(Supplier<T> resolver) {
        try {
            T result = resolver.get();
            return Optional.of(result);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        Integer a = null;
        Optional<Integer> integerOptional = OptionalUtil.resolve(() -> a);
        System.out.println(integerOptional.isPresent());
        System.out.println(integerOptional.toString());
    }
}
