package practice;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by pandechuan on 2018/11/23.
 */
public class SystemTest {
    public static void main(String[] args) throws IOException {
//        String log_file = System.getProperty("user.dir");
//        System.out.println(log_file);
//        System.getProperties().list(System.out);


//        System.out.println("livefiles_1542113527026_data.json".length());
//        System.out.println("livefiles_1542113527026_data.json".endsWith("json"));
       // System.out.println(test());


        String a = "http://f2.iplay.126.net/livefiles_1542113527026_data.json";
        String[] sar = "http://f2.iplay.126.net/livefiles_1542113527026_data.json".split("^\\/");
        System.out.println(sar.length);
       // System.out.println(sar[1]);
System.out.println(a.lastIndexOf("/"));
        System.out.println(a.substring(a.lastIndexOf("/")+1));
        //Arrays.stream(sar).forEach(e -> System.out.println(e));

    }

    static Integer test() {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            System.out.println("catch");
            return 1;
        } finally {
            System.out.println("finally");
            return 2;
        }
    }
}
