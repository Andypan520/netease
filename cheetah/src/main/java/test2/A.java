package test2;

/**
 * @author pandechuan 2019/01/28
 */
public class A {

    public static void main(String[] args) {
        char charstr = 'a';
        charstr += 1;
        System.out.println(charstr);

        System.out.println(String.valueOf(charstr));

        System.out.println(Character.getNumericValue(charstr));
    }

}
