package demo.userfront.controller;

import demo.userfront.vo.UserVo;
import demo.userfront.service.UserService;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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

    @RequestMapping(path = "/new")
    public String newUser(){
        return "userUpdateOrCreate";
    }

    @RequestMapping(path = "/{userId}/edit")
    public String editUser(@PathVariable String userId,ModelMap model){
        UserVo user = userService.getUser(userId);
        model.put("user",user);
        return "userUpdateOrCreate";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateUser(UserVo user){
        userService.updateUser(user);
        return "redirect:user/"+user.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String createUser(UserVo user){
        userService.createUser(user);
        return "redirect:user/"+user.getId();
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

        when(userService.createUser(anyObject())).then(invocation -> {
            UserVo args = invocation.getArgumentAt(0, UserVo.class);
            args.setId("1");
            System.out.println(args);
            return 1;
        });
        when(userService.updateUser(anyObject())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                UserVo args = invocation.getArgumentAt(0, UserVo.class);
                System.out.println(args);
                return 1;
            }
        });
    }
}
