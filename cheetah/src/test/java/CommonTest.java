import com.google.common.collect.Lists;

import common.OptionalUtil;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by pandechuan on 2018/1/8.
 */
public class CommonTest {
    public static void main(String[] args) {
//        Instant inst1 = Instant.now();
//        IntStream.range(1,10000000).forEach(e ->System.out.println(e));
//        Instant inst2 = Instant.now();
//        System.out.println("Inst2 : " + inst2);
//        System.out.println("Difference in milliseconds : " + Duration.between(inst1, inst2).getSeconds());
        List<String> list = Lists.newArrayList("综艺,幽默,明星,,", "生活,手工,日常,,", "生活,萌宠,狗狗,,");
        Set<String> set = list.stream().flatMap(e -> Arrays.stream(e.split(","))).collect(Collectors.toSet());
        List<String> list2 = list.stream().flatMap(e -> Arrays.stream(e.split(","))).distinct().collect(Collectors.toList());
        System.out.println(set.size());
        System.out.println(set);
        System.out.println(list2.size());
        System.out.println(list2);

    }

    @Test
    public void streamTest() {
        try (Stream<String> lines = Files.lines(Paths.get("D:\\CrackCaptcha.log"), Charset.defaultCharset())) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test() {
        Arrays.stream("综艺,幽默,明星,,".split(",")).forEach(e -> System.out.println(e+" "+1));
    }

  @Test
    public void test2() {
      Optional<Integer> optionalInteger =  OptionalUtil.get(() -> null);
      optionalInteger.orElseThrow(() -> {
          System.out.println(23);
         return  new IllegalArgumentException("IllegalArgumentException lalala");
      });
    }


}
