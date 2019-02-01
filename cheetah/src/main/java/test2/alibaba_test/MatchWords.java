package test2.alibaba_test;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author pandechuan 2019/01/28
 * <p>
 * (30分钟内完成)有一个字符串它的构成是词+空格的组合，如“北京 杭州 杭州 北京”， 要求输入一个匹配模式（简单的以字符来写），
 * * 比如 aabb, 来判断该字符串是否符合该模式， 举个例子：
 * pattern = "abba", str="北京 杭州 杭州 北京" 返回 true ab 北京杭州
 * pattern = "aabb", str="北京 杭州 杭州 北京" 返回 false
 * pattern = "baab", str="北京 杭州 杭州 北京" 返回 true
 */
public class MatchWords {

    /**
     * content match
     *
     * @param source
     * @param pattern
     * @return
     */
    public static int match(String source, String pattern) {
        int index = -1;
        boolean match = true;
        for (int i = 0, len = source.length() - pattern.length(); i <= len; i++) {
            match = true;
            for (int j = 0; j < pattern.length(); j++) {
                if (source.charAt(i + j) != pattern.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * mapping match
     *
     * @param source
     * @param pattern
     * @return
     */
    public static boolean match2(String source, String pattern) {
        String[] wordArray = source.split(" ");
        String[] patternArr = pattern.split("");

        int wordLen = wordArray.length;
        int patternLen = pattern.length();
        if (wordLen != patternLen) {
            return false;
        }
        int sourceDifferentWordNum = Stream.of(wordArray).collect(Collectors.toSet()).size();
        int patternDifferentWordNum = Stream.of(patternArr).collect(Collectors.toSet()).size();
        if (sourceDifferentWordNum != patternDifferentWordNum) {
            return false;
        }
        Map<String, String> contentPatternMap = Maps.newLinkedHashMap();

        for (int i = 0; i < wordArray.length; i++) {
            if (contentPatternMap.containsKey(wordArray[i]) || contentPatternMap.containsValue(patternArr[i])) {
                continue;
            }
            contentPatternMap.put(wordArray[i], patternArr[i]);
        }
        StringBuilder sourceBuilder = new StringBuilder();
        for (int i = 0; i < wordArray.length; i++) {
            sourceBuilder.append(contentPatternMap.getOrDefault(wordArray[i], ""));
        }
        String sourceStr = sourceBuilder.toString();

        return pattern.equals(sourceStr);

    }

    public static void main(String[] args) {
        String pattern = "baab";
        String source = "北京 杭州 杭州 北京";
        System.out.println(match2(source, pattern));
    }

}
