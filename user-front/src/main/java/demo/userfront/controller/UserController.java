package demo.userfront.controller;

import demo.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

}
