package evolution.DTO;

import com.google.common.base.Converter;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * Created by pandechuan on 2018/11/27.
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class UserOutputDTO {
    @NotNull
    private String name;
    private int age;
    //输出专有信息
    private String outputInfo;


    public User convertToUser() {
        UserOutputDTOConvert userOutputDTOConvert = UserOutputDTOConvert.of();
        User user = userOutputDTOConvert.convert(this);
        return user;
    }

    public UserOutputDTO convertTODTO(User user) {
        UserOutputDTOConvert userOutputDTOConvert = UserOutputDTOConvert.of();
        //reverse： A doForward(B b)
        UserOutputDTO userOutputDTO = userOutputDTOConvert.reverse().convert(user);
        return userOutputDTO;
    }

    @RequiredArgsConstructor(staticName = "of")
    private static class UserOutputDTOConvert extends Converter<UserOutputDTO, User> {
        @Override
        protected User doForward(UserOutputDTO userOutputDTO) {
            User user = User.of();
            BeanUtils.copyProperties(userOutputDTO, user);
            //other special process
            return user;
        }

        @Override
        protected UserOutputDTO doBackward(User user) {
            UserOutputDTO userOutputDTO = UserOutputDTO.of();
            BeanUtils.copyProperties(user, userOutputDTO);
            //other special process
            return userOutputDTO;
        }
    }


}
