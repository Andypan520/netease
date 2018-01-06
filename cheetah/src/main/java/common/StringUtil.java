package common;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by pandechuan on 2018/1/6.
 */
public class StringUtil {
    public static void main(String[] args) {
//        testJoin();
      String s =  removeRepWords("foobar:foo:bar");
      System.out.println(s);
//        testPatternPredicate();
//        testPatternSplit();
    }

    //a way to remove repetitive words
    public static String removeRepWords(String input) {
       return  input.chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    /**
     * 按照指定的模式切分字符串并且用指定的连接符拼接
     * @param input
     * @param pattern
     * @param delimiter
     * @return
     */
    private static String patternSplit(String input,String pattern,CharSequence delimiter) {
        return  Pattern.compile(pattern)
                .splitAsStream(input)
                .filter(s -> s.contains("bar"))
                .sorted()
                .collect(Collectors.joining(delimiter));
    }


    private static void testJoin() {

        String string = String.join(":", "foobar", "foo", "bar");
        System.out.println(string);
        System.out.println(String.join(",", "bob@gmail.com\", \"alice@hotmail.com".split("")));
    }
}
