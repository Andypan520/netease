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
public class UserInputDTO {
    @NotNull
    private String name;
    private int age;
    //输入专有信息
    private String inputInfo;


    public User convertToUser() {
        UserInputDTOConvert userInputDTOConvert = UserInputDTOConvert.of();
        User convert = userInputDTOConvert.convert(this);
        return convert;
    }

    public UserInputDTO convertToDTO(User user) {
        UserInputDTOConvert userInputDTOConvert = UserInputDTOConvert.of();
        User convert = userInputDTOConvert.convert(this);
        //reverse： A doForward(B b)
        UserInputDTO userInputDTO = userInputDTOConvert.reverse().convert(user);
        return userInputDTO;
    }

    @RequiredArgsConstructor(staticName = "of")
    private static class UserInputDTOConvert extends Converter<UserInputDTO, User> {
        @Override
        protected User doForward(UserInputDTO userInputDTO) {
            User user = User.of();
            BeanUtils.copyProperties(userInputDTO, user);
            //special process
            return user;
        }

        @Override
        protected UserInputDTO doBackward(User user) {
            UserInputDTO userInputDTO = new UserInputDTO();
            BeanUtils.copyProperties(user, userInputDTO);
            return userInputDTO;

            //述只是表明了转化方向的正向或逆向，很多业务需求的出参和入参的DTO对象是不同的，那么你需要更明显的告诉程序：逆向是无法调用的:
            // throw new AssertionError("不支持逆向转化方法!");
        }
    }

}
