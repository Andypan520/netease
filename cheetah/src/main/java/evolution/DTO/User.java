package evolution.DTO;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.val;

/**
 * Created by pandechuan on 2018/11/27.
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@ToString(exclude = "idCard")
public class User {
    @NotNull
    private String name;
    private int age;
    private long idCard;

    public static void main(String[] args) {
        val Country = "china";
        User user = User.of().setName("a").setAge(12).setIdCard(999L);
    }

}
