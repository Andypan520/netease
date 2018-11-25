package practice;

/**
 * Created by pandechuan on 2018/11/23.
 */
public class ArgsTest {

    public static void main(String[] args) {
        int i = 0;
        if (args != null & args.length > 0) {
            for (String s : args) {
                System.out.println("args[" + i++ + "]=" + s);
            }
        }
    }

}
