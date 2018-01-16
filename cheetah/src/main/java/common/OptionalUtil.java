package common;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by pandechuan on 2018/1/5.
 */
public class OptionalUtil {
    /**
     * 防止取值NPE方法
     */
    public static <T> Optional<T> get(Supplier<T> supplier) {
        try {
            T result = supplier.get();
            return Optional.of(result);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Long> stringToLong(String s) {

        try {
            return Optional.of(Long.parseLong(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }


    public static void main(String[] args) {
        Integer a = null;
        Optional<Integer> integerOptional = OptionalUtil.get(() -> a);
        System.out.println(integerOptional.isPresent());
        System.out.println(integerOptional.toString());

        List<String> list = Lists.newArrayList("1265", "f", " 5 ", "89");
        List<Integer> intList = list.stream().map(OptionalUtil::stringToInt).filter(e -> e.isPresent()).map(e -> e.get()).collect(Collectors.toList());
        intList.forEach(System.out::println);
        OptionalUtil.get(() -> 23).orElseThrow(() -> new IllegalStateException()); // not throw exception
        OptionalUtil.get(() -> null).orElseThrow(() -> new IllegalArgumentException()); //throw exception
    }
}
