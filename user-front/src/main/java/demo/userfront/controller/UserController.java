package demo.userfront.controller;

import demo.userfront.service.UserService;
import demo.userfront.vo.UserVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean{
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String getUsers(ModelMap model){
        List<UserVo> users = userService.getUsers();
        model.put("users",users);
        return "userList";
    }

    @RequestMapping(path = "/{userId}",method = RequestMethod.GET)
    public String getUser(@PathVariable String userId, ModelMap model){
        UserVo user = userService.getUser(userId);
        model.put("user", user);
        return "userDtail";
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        userService = mock(UserService.class);

        List<UserVo> users = new ArrayList<>();
        UserVo zs = new UserVo();
        zs.setId("1");
        zs.setName("张三");
        zs.setAge(10);
        zs.setSex("男");
        users.add(zs);
        UserVo ls = new UserVo();
        ls.setId("2");
        ls.setName("李四");
        ls.setAge(20);
        ls.setSex("女");
        users.add(ls);
        when(userService.getUsers()).thenReturn(users);
        when(userService.getUser("1")).thenReturn(zs);
        when(userService.getUser("2")).thenReturn(ls);
    }
}
