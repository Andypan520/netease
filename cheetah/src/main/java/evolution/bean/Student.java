package evolution.bean;

import java.time.Clock;

/**
 * Created by pandechuan on 2018/11/27.
 */
public class Student {
    private String name;
    private int age;

    public static void main(String[] args) {
        //  Student student = Student.builder().name("zs").age(24).build();
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());
    }

}
