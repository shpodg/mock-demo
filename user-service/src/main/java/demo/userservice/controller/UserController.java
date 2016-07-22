package demo.userservice.controller;

import demo.userservice.model.UserModel;
import demo.userservice.service.UserService;
import demo.userservice.vo.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/<userId>", method = RequestMethod.GET)
    public UserModel getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseStatus updateUser(UserModel user){
        userService.updateUser(user);
        return ResponseStatus.STATUS_OK;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseStatus createUser(UserModel user){
        userService.createUser(user);
        return ResponseStatus.STATUS_OK;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserModel> getUsers(){
        return userService.getUsers();
    }
}
