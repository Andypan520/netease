package evolution.controller;

import com.google.common.collect.Lists;
import evolution.DTO.User;
import evolution.DTO.UserInputDTO;
import evolution.DTO.UserOutputDTO;
import evolution.service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;

/**
 * Created by pandechuan on 2018/11/27.
 */

public class UserController {


    //request api
    public void addUser(UserInputDTO userInputDTO) {

        UserService userService = new UserService();
        User user = userInputDTO.convertToUser();
        userService.addUser(user);
    }

    //reponse api
    public UserOutputDTO getUser() {
        UserService userService = new UserService();
        return UserOutputDTO.of().convertTODTO(userService.getUser());
    }

    @Test
    public void testValExample() {
        val hello = "Hello World!";
        val list = Lists.newArrayList();
        list.add(1);
        list.add("aaa");
        //hello = "a";
        System.out.println(hello);
        System.out.println(list);
    }
}
