package practice;

import java.util.WeakHashMap;

/**
 * Created by pandechuan on 2018/11/24.
 */
public class WeakHashMapTest {

    public static void main(String[] args) {
        WeakHashMap<String, byte[]> whm = new WeakHashMap<String, byte[]>();
        String s1 = new String("s1");
        String s2 = new String("s2");
        String s3 = new String("s3");

        whm.put(s1, new byte[100]);
        whm.put(s2, new byte[100]);
        whm.put(s3, new byte[100]);

        s2 = null;
        s3 = null;

        /*此时可能还未执行gc,所以可能还可以通过仅有弱引用的key找到value*/
        System.out.println(whm.get("s1"));
        System.out.println(whm.get("s2"));
        System.out.println(whm.get("s3"));

        System.out.println("-------------------");

        /*执行gc,导致仅有弱引用的key对应的entry(包括value)全部被回收*/
        System.gc();
        System.out.println(whm.get("s1"));
        System.out.println(whm.get("s2"));
        System.out.println(whm.get("s3"));


    }

}
