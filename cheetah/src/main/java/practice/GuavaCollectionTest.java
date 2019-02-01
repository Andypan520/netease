package practice;

import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pandechuan on 2018/11/28.
 */
public class GuavaCollectionTest {

    public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
            "red",
            "orange",
            "yellow",
            "green",
            "blue",
            "purple");

    public static void main(String[] args) {

        //比想象中更智能的copyOf
        Set<String> bars = ImmutableSet.copyOf(COLOR_NAMES);
        System.out.println(bars);
    }

    @Test
    public void multisetTest() {
        Multiset multiset = HashMultiset.create();
        for (int i = 0; i < 20; ++i) {
            multiset.add(i);
        }
        for (int i = 0; i < 20; i += 2) {
            multiset.add(i);
        }
        for (Object element : multiset.elementSet()) {
            System.out.printf("\n" + (Integer) element + ":" + multiset.count(element));
        }
    }

    @Test
    public void biMapTest() {
        BiMap<Integer, String> biMap = HashBiMap.create();
        biMap.put(1, "num1");
        biMap.put(2, "num1");

        //java.lang.IllegalArgumentException: value already present: num1
        BiMap<String, Integer> biMap1 = biMap.inverse();
    }

    @Test
    public void tableTest() {
        HashBasedTable<String, Integer, Integer> hashBasedTable = HashBasedTable.create();
        hashBasedTable.put("Bob", 18, 69);
        hashBasedTable.put("Arby", 17, 90);
        Set<Table.Cell<String, Integer, Integer>> cells = hashBasedTable.cellSet();
        for (Table.Cell<String, Integer, Integer> temp : cells) {
            System.out.println(temp.getRowKey() + ":" + temp.getColumnKey() + ":" + temp.getValue());
        }

    }

    @Test
    public void listsTest() {
        List<String> list = Lists.newArrayList("a", "a", "a", "a", "a", "a", "a", "a", "a", "a");
        List<List<String>> subLists = Lists.partition(list, 3);
        subLists.forEach(e -> System.out.println(e.size())); //3,3,3,1

    }

    @Test
    public void setsTest() {
//      Sets.SetView<Integer> setView =  Sets.symmetricDifference(Sets.newHashSet(1, 2, 3), Sets.newHashSet(5, 2, 3,4));
//       setView.stream().forEach(e -> System.out.println(e));

//      Set<Integer>  set = Sets.newHashSet(1,2,3);
//       Sets.powerSet(set).stream().forEach(e -> System.out.println(e));

        Set<List<Integer>> cartesian = Sets.cartesianProduct(Lists.newArrayList(Sets.newHashSet(1, 2),
                Sets.newHashSet(3, 4), Sets.newHashSet(5, 6, 7)));

        cartesian.stream().forEach(e -> System.out.println(e)); // 2 * 2 * 3 = 12

    }


    @Test
    public void mapTest() {
        List<String> strings = Lists.newArrayList("a", "ab", "abc");

        ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(strings, e -> e.length());


//        ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(strings,
//                new Function<String, Integer>() {
//                    @Override
//                    public Integer apply(String string) {
//                        return string.length();
//                    }
//                });

        stringsByIndex.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }

    @Test
    public void mapTest2() {
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("a", 1, "b", 2, "c", 4);
        MapDifference<String, Integer> diff = Maps.difference(left, right);

        diff.entriesInCommon().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        diff.entriesDiffering().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        diff.entriesOnlyOnLeft().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        diff.entriesOnlyOnRight().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }


}

