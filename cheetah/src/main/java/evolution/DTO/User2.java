package evolution.DTO;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Synchronized;

/**
 * Created by pandechuan on 2018/11/27.
 */
@AllArgsConstructor
//@Getter(lazy=true)
public class User2 {

    @NotNull
    private String name;
    private int age;
    private long idCard;

    @SneakyThrows
    public void method() {
        Thread.sleep(1000L);
    }

    @Synchronized
    public void method2() {

    }
//
//   // public static void main(String[] args) {
//        System.out.println(new User2("a", 2, 222L));
//    }
//

}
