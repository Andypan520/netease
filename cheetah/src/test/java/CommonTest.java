import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import common.DateUtil;
import common.OptionalUtil;
import common.ZipUtil;
import org.junit.Test;
import sun.net.www.content.image.png;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pandechuan on 2018/1/8.
 */
public class CommonTest {

    private static final LinkedHashMap<String, String> UNION_TABINFO_MAP = Maps.newLinkedHashMap();

    static {
        UNION_TABINFO_MAP.put("company", "经纪公司认证信息");
        UNION_TABINFO_MAP.put("bank", "开户许可证银行卡");
        UNION_TABINFO_MAP.put("unionRoom", "直播间实景");
        UNION_TABINFO_MAP.put("contact", "联系人信息");
    }

    public static void main(String[] args) {
//        Instant inst1 = Instant.now();A
//        IntStream.range(1,10000000).forEach(e ->System.out.println(e));
//        Instant inst2 = Instant.now();
//        System.out.println("Inst2 : " + inst2);
//        System.out.println("Difference in milliseconds : " + Duration.between(inst1, inst2).getSeconds());
//        List<String> list = Lists.newArrayList("综艺,幽默,明星,,", "生活,手工,日常,,", "生活,萌宠,狗狗,,");
//        Set<String> set = list.stream().flatMap(e -> Arrays.stream(e.split(","))).collect(Collectors.toSet());
//        List<String> list2 = list.stream().flatMap(e -> Arrays.stream(e.split(","))).distinct().collect(Collectors.toList());
//        System.out.println(set.size());
//        System.out.println(set);
//        System.out.println(list2.size());
//        System.out.println(list2);

        //List<Integer> list = Lists.newArrayList(1, 3, 5);
//            list.addAll(Lists.newArrayList(2, 4, 1));
//
//        list.add(10,3);
//            System.out.println(list);

       /* Person person = new Person("a", 12);
        Person person2 = new Person("a", 13);
        Person person3 = new Person("c", 13);
        Person person4 = new Person("d", 15);
        Person person5 = new Person("d", 12);
        Person person6 = new Person("e", 14);
        Person person7 = new Person("g", 12);

        List<Person> list = Lists.newArrayList(person, person2, person3, person4, person5, person6, person7);

        //(a,b) -> b 用后者替换前者
        // Map<Integer, Person> map = list.stream().collect(Collectors.toMap(e -> e.getAge(), e -> e,(a,b) -> b));

        //(a,b) -> b 后者被忽略
        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(Person::getAge, e -> e, (a, b) -> a));


        Map<String, Integer> map2 = list.stream().collect(Collectors.toMap(Person::getName, Person::getAge, (a, b) -> a));
*/

        // System.out.println(map2);
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 100000000; i++) {
            list.add(i);
        }
        long start = System.currentTimeMillis();
        list.stream().limit(10);
        long end = System.currentTimeMillis() - start;

        System.out.println(end);

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
        // Arrays.stream("综艺,幽默,明星,,".split(",")).forEach(e -> System.out.println(e + " " + 1));
        LocalDateTime todayTime = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth()); // 2014-12-01    }
        LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth()); // 2014-12-01    }

        System.out.println(firstDayOfThisMonth.toString());
        System.out.println(lastDay.toString());

        System.out.println(Instant.ofEpochMilli(1530497127796L).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime().toLocalDate());
        System.out.println(LocalTime.MIN);
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(System.currentTimeMillis());

        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        Map<Boolean, List<Integer>> Map = list.stream().collect(Collectors.groupingBy(e -> e > 2));
        System.out.println(Map.get(Boolean.TRUE));
    }

    @Test
    public void test2() {
        Optional<Integer> optionalInteger = OptionalUtil.get(() -> null);
        optionalInteger.orElseThrow(() -> {
            System.out.println(23);
            return new IllegalArgumentException("IllegalArgumentException lalala");
        });
    }

    @Test
    public void test3() {
        Map<String, String> pictureIdsMap = Maps.newHashMap();
        pictureIdsMap.put("a", "0");
        pictureIdsMap.put("b", "123");
        pictureIdsMap.forEach((k, v) -> {
            if (!v.trim().equalsIgnoreCase("0")) {
                pictureIdsMap.put(k, "345");
            } else {

                //java.util.ConcurrentModificationException
                pictureIdsMap.remove(k);
            }
        });

        System.out.println(pictureIdsMap);
    }

    @Test
    public void test4() {

        UNION_TABINFO_MAP.forEach((k, v) -> {
            System.out.println(k);
        });
        System.out.println(UNION_TABINFO_MAP.keySet());
    }

    @Test
    public void test5() {
        System.out.println(1);
        Lists.newArrayList(1).parallelStream().forEach(e -> {
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println(2);
        });
        System.out.println(1);
    }

    @Test
    public void test6() {
        System.out.println(1);
        List<Integer> list1 = Lists.newArrayList(1, 2, 3);
        //简单类型的深拷贝
        List<Integer> list2 = Lists.newArrayList(list1);
        list2.remove(2);
        System.out.println(list1);
        System.out.println(list2);

    }

    @Test
    public void test7() {
        Person person = new Person("a", 12);
        Person person3 = new Person("c", 13);
        Person person4 = new Person("d", 15);
        Person person6 = new Person("e", 14);
        Person person7 = new Person("g", 12);

        List<Person> list1 = Lists.newArrayList(person, person3, person4, person6, person7);

        System.out.println(list1.size());
        List<Person> list2 = Lists.newArrayList(list1);
        //修改list2
        list2.forEach(e -> e.setAge(20));
        list2.remove(2);
        System.out.println(list2.size());
        System.out.println("list1:" + list1);
    }

    @Test
    public void test8() {
        LocalDate localDate = LocalDate.now();
        String yearMonth = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate lastMonthLastDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate curMonthLastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(yearMonth);
        System.out.println(DateUtil.format(lastMonthLastDay, DateUtil.yyyyMMdd));
        System.out.println(DateUtil.format(curMonthLastDay, DateUtil.yyyyMMdd));

        LocalDate localDate1 = LocalDate.parse("2018-10-29", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(localDate1);
        System.out.println(localDate1.isAfter(LocalDate.now()));
        System.out.println(localDate1.isEqual(LocalDate.now()));

        System.out.println(DateUtil.localDate2Millis(localDate1) > System.currentTimeMillis());
    }

    @Test
    public void test9() {
        BigDecimal bigDecimal = new BigDecimal(10003);
        BigDecimal result = bigDecimal.divide(new BigDecimal(100), RoundingMode.HALF_DOWN);

        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(result);
        String str2 = df1.format(new BigDecimal("1.345"));
        System.out.println(str);  //13.15
        System.out.println(str2);  //13.15


    }

    @Test
    public void test10() {
        String initialReference = "123456789";

        AtomicReference<String> atomicStringReference = new AtomicReference<>(initialReference);

        String newReference = "new value referenced";
        boolean exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
        System.out.println("exchanged: " + exchanged);
        System.out.println(atomicStringReference.get());


    }

    @Test
    public void test11() {
        Exception e = new NullPointerException("ddd");
        System.out.println(e.getMessage());
        System.out.println(e.getLocalizedMessage());
        //e.printStackTrace();
        //System.out.println(e.getLocalizedMessage());


    }

    @Test
    public void test12() {
        String objectKey = "livefiles/LTE4NTAwNw==/8a04d16573f6eb93eb3be469cf7366fc.zip";
        String a = objectKey.substring(objectKey.indexOf('/') + 1);
        System.out.println(a);

    }

    @Test
    public void test13() throws Exception {
        // File file = new File("./webfile");
//        File file = new File("./webfile");
        // File file = new File("./" + UUID.randomUUID().toString());
        File file = new File("./" + "tmp");
        System.out.println(file.getName());
        System.out.println(file.length());

        if (!file.exists()) {
            file.createNewFile();
            ZipUtil.unZipFiles(file, "./dest/");
        }


    }

    @Test
    public void test14() throws Exception {
        List<String> list = Lists.newArrayList(null, null);
        System.out.println(list.contains(null));
    }

    @Test
    public void test15() throws Exception {
        String a = "files/liveimgs_1540435587216_img_10.png";
        //String b = a.substring(a.lastIndexOf('_'));
        a = a.replace("files"+'/', "");
        System.out.println(a);
    }

    @Test
    public void test16() throws Exception {
      Integer a = 12;
      Integer b = 12;
      System.out.println(a == b);
      System.out.println(a.equals(b));

    }


}
