package common;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by pandechuan on 2018/09/15.
 */
public class BookListProcess {
    public static void main(String[] args) throws IOException {

        /**
         * mobi (1720)epub (1134)azw3 (801)小说 (659)历史 (262)日本 (171)心理学 (169)外国文学
         * (161)推理 (140)随笔 (125)科普 (100)哲学 (97)社会学 (94)中国文学
         * (94)美国 (92)经济 (81)传记 (77)英国 (74)经济学 (74)日本文学 (70)悬疑
         * (64)科幻 (62)商业 (60)思维 (60)文化 (59)散文 (58)管理 (52)心理 (51)金融 (49)中国 (45)
         爱情(874405)	旅行(563272)	生活(521673)	成长(519465)
         心理(395984)	励志(391967)	女性(310285)	摄影(292537)
         职场(209741)	教育(208177)	美食(192814)	游记(151933)
         灵修(123872)	健康(81923)	情感(81320)	两性(43665)
         人际关系(42159)	手工(40634)	养生(36378)	家居(23247)
         自助游(2686)
         经管 · · · · · ·
         经济学(419776)	管理(412818)	经济(340455)	商业(301720)
         金融(276788)	投资(225622)	营销(154031)	理财(111422)
         创业(111205)	广告(65704)	股票(64111)	企业史(21053)
         策划(8536)
         科技 · · · · · ·
         科普(574748)	互联网(236709)	编程(156725)	科学(133098)
         交互设计(68168)	用户体验(54761)	算法(51701)	科技(26172)
         web(21816)	UE(5127)	交互(4942)	通信(4839)
         UCD(3567)	神经网络(2485)	程序(1294)
         标签直达  · · · · · ·
         去其他标签
         */

        //write();
        String bookList = "/Users/pandechuan/Desktop/booklist";
        File file = new File("/Users/pandechuan/Desktop/booklist3");
        List<String> list = FileUtil.readAllLines(bookList);

        //list.stream().distinct().map(e -> wash(e)).filter(e -> e.contains("经济")).forEach(System.out::println);
        String[] economics = new String[]{"经济", "管理", "商业", "投资", "营销", "理财", "股票", "创业", "广告", "策划", "支付", "货币", "财政", "税收"};
        String[] computer = new String[]{"计算机", "网络", "算法", "编程", "Ios", "Android", "Java", "深度学习", "机器学习",
                "PHP", "web", "大数据", "云计算", "docker", "jQuery", "代码", "设计模式", "互联网", "Http", "Git", "spark", "html", "nodejs",
                "Google", "html", "程序员", "Python", "Linux", "Unix", "黑客", "编程", "智能", "c#", "电脑", "Javascript", "C++", "CPU", "CSS"};
        String[] design = new String[]{"数学", "物理", "天文", "宇宙", "地球", "哲学", "科普"};
        String[] think = new String[]{"科幻", "玄幻", "神话", "魔幻", "童话"};
        String[] history = new String[]{"历史"};
        String[] xiaoshuo = new String[]{"小说"};
        String[] statistics = new String[]{"统计", "概率"};
        FileUtil.clear(file);
        list.stream().distinct().filter(e -> match(e, statistics)).forEach(e -> {
            try {
                FileUtil.append(file, e);
                FileUtil.append(file, "\n");
            } catch (IOException e1) {
                e1.printStackTrace();

            }
        });
        // System.out.println(list.size());

        //forEach(System.out::println);
//    private static void write() {
//        String bookList = "/Users/pandechuan/Desktop/booklist2";
//        File file = new File(bookList);
//        FileUtil.readAllLines(bookList)
//                .stream().map(e -> wash(e))
//                .peek(e -> System.out.println("--" + e))
//                .forEach(e -> {
//                    try {
//                        FileUtil.append(file, e);
//                        FileUtil.append(file, "\r\n");
//
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
//                });
//        //System.out.println(list);
//
    }

    private static boolean match(String line, String... keyWord) {
        return Stream.of(keyWord).anyMatch(e -> line.contains(e));
    }

    private static String wash(String oldBook) {
        String bookName = oldBook.trim();
        if (bookName.contains("#")) {
            bookName = bookName.substring(bookName.lastIndexOf("#") + 1, bookName.length());
        }
        if (bookName.contains(".")) {

            bookName = bookName.substring(0, bookName.lastIndexOf("."));
        }
        return bookName;
    }


}
