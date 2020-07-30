package com.bigdream.dream.controller.login;

import com.bigdream.dream.dto.UserDTO;
import com.bigdream.dream.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-29 16:41
 **/
@RequestMapping("dream/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public boolean register(UserDTO req){
        return userService.register(req);
    }
}
