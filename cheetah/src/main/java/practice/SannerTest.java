package practice;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by pandechuan on 2018/11/25.
 */
public class SannerTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
        /* nextLine()是扫描器执行当前行，并返回跳过的输入信息，特别需要注意！！！
            如果没有该行，则执行第一个in.nextLine()命令时的返回值是int n = in.nextInt()的值*/
            in.nextLine();
            HashSet<String> set = new HashSet<>();
            for (int i = 0; i < n; i++) {
                String line = in.nextLine();
                String[] arr = line.split(" ");
                for (int j = 0; j < arr.length; j++) {
                    set.add(arr[j]);
                }
            }
            System.out.println("sum:" + set.size());

        }
        System.out.println("end");
    }
}
