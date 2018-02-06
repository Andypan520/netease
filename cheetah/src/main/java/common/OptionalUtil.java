package common;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
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
            T result = supplier.get();
            return Optional.ofNullable(result);
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
//        Integer a = null;
//        Optional<Integer> integerOptional = OptionalUtil.get(() -> a);
//        System.out.println(integerOptional.isPresent());
//        System.out.println(integerOptional.toString());
//
//        List<String> list = Lists.newArrayList("1265", "f", " 5 ", "89");
//        List<Integer> intList = list.stream().map(OptionalUtil::stringToInt).filter(e -> e.isPresent()).map(e -> e.get()).collect(Collectors.toList());
//        intList.forEach(System.out::println);
//        OptionalUtil.get(() -> 23).orElseThrow(() -> new IllegalStateException()); // not throw exception
//        OptionalUtil.get(() -> null).orElseThrow(() -> new IllegalArgumentException()); //throw exception
//
//        String s = Arrays.toString(Lists.newArrayList(1, 2, 3, 4).toArray());
//        System.out.println(s);
//        s = Joiner.on(",").join(Lists.newArrayList(1, 2, 3, 4));
//        System.out.println(s);
        //  String  s1 = Collectors.joining(",", "(", ")");
        // Lists.newArrayList(1, 2, 3, 4).stream().collect(Collectors.joining(",", "(", ")"));
        System.out.println();
//        new StringJoiner()

//        int[] a = new int[]{1,2,3};
//        Sets.newHashSet(a).remove(2);

        Optional<String> someNull = Optional.ofNullable(null);
        System.out.println(someNull.isPresent());
    }
}
