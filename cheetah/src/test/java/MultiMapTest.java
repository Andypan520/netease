import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.primitives.Ints;

import org.junit.Test;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by pandechuan on 2018/1/9.
 */
public class MultiMapTest {
    // Multimaps
// Multimaps提供了若干值得单独说明的通用工具方法
// index
// 作为Maps.uniqueIndex的兄弟方法，Multimaps.index(Iterable,
// Function)通常针对的场景是：有一组对象，它们有共同的特定属性，我们希望按照这个属性的值查询对象，但属性值不一定是独一无二的。
// 比方说，我们想把字符串按长度分组。

    @Test
    public void test1() {
        ImmutableSet digits = ImmutableSet.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
                "nine");

        ImmutableListMultimap<Integer, String> digitsByLength = Multimaps.index(digits, (String s) -> s.length());

        System.out.println(digitsByLength);
// {4=[zero, four, five, nine], 3=[one, two, six], 5=[three, seven,
// eight]}
    }


    @Test
    public void test2() {
// invertFrom
// 鉴于Multimap可以把多个键映射到同一个值也可以把一个键映射到多个值，反转Multimap也会很有用。Guava
// 提供了invertFrom(Multimap toInvert,
// Multimap dest)做这个操作，并且你可以自由选择反转后的Multimap实现。
// 注：如果你使用的是ImmutableMultimap，考虑改用ImmutableMultimap.inverse()做反转。
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));


        TreeMultimap<Integer, String> inverse = Multimaps.invertFrom(multimap, TreeMultimap.create());
// {1=[a], 2=[a, b, c], 3=[c], 4=[a, b], 5=[c], 6=[b]}
        System.out.println(inverse);


    }


    @Test
    public void test3() {
// 想在Map对象上使用Multimap的方法吗？forMap(Map)把Map包装成SetMultimap。
//这个方法特别有用，例如，与Multimaps.invertFrom结合使用，可以把多对一的Map反转为一对多的Multimap。
        Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
        SetMultimap<String, Integer> multimap = Multimaps.forMap(map);
        System.out.println(multimap);
// multimap：{a=[1], b=[1], c=[2]}
        Multimap<Integer, String> inverse = Multimaps.invertFrom(multimap, HashMultimap.create());
        System.out.println(inverse);
// {1=[a, b], 2=[c]}

    }

}
