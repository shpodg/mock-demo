package demo.userfront.controller;

import demo.userfront.service.UserService;
import demo.userfront.vo.UserVo;
import org.apache.log4j.Logger;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Controller
@RequestMapping("/user")
public class UserController{// implements InitializingBean{
    @Autowired
    UserService userService;
    private Logger logger = Logger.getLogger(getClass());

    /**
     * 获取用户列表
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getUsers(ModelMap model){
        List<UserVo> users = userService.getUsers();
        model.put("users",users);
        return "userList";
    }

    /**
     * 获取单个用户详细页面
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(path = "/{userId}",method = RequestMethod.GET)
    public String getUser(@PathVariable String userId, ModelMap model){
        UserVo user = userService.getUser(userId);
        model.put("user", user);
        return "userDtail";
    }

    /**
     * 创建新用户页面
     * @return
     */
    @RequestMapping(path = "/new")
    public String newUser(){
        return "userUpdateOrCreate";
    }

    /**
     * 编辑某个用户页面
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(path = "/{userId}/edit")
    public String editUser(@PathVariable String userId,ModelMap model){
        UserVo user = userService.getUser(userId);
        model.put("user",user);
        return "userUpdateOrCreate";
    }

    /**
     * 提交更新用户信息跳转用户详细信息页面
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String updateUser(@Valid UserVo user){
        logger.info("updateUser "+user);
        userService.updateUser(user);
        return "redirect:user/"+user.getId();
    }

    /**
     * 提交创建用户跳转用户详细信息页面
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String createUser(@Valid UserVo user){
        logger.info("createUser" + user);
        String location = userService.createUser(user);
        return "redirect:"+location;
    }

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
