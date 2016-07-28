package demo.userservice.controller;

import demo.userservice.model.UserModel;
import demo.userservice.service.UserService;
import demo.userservice.vo.ResponseStatus;
import org.apache.log4j.Logger;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@RestController
@RequestMapping("/user")
public class UserController {//implements InitializingBean {
    @Autowired
    UserService userService;
    private Logger logger = Logger.getLogger(getClass());

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserModel getUserById(@PathVariable int userId){
        return userService.getUserById(userId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseStatus updateUser(@RequestBody UserModel user){
        logger.info("更新用户 :" + user);
        userService.updateUser(user);
        return ResponseStatus.STATUS_OK;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResponseStatus> createUser(@RequestBody UserModel user){
        userService.createUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user/"+user.getId()));
        ResponseEntity<ResponseStatus> resp = new ResponseEntity<ResponseStatus>(ResponseStatus.STATUS_OK,headers,HttpStatus.CREATED);
        logger.info("新增用户 :" + user);
        return resp;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserModel> getUsers(){
        return userService.getUsers();
    }

    //@Override
    public void afterPropertiesSet() throws Exception {

        userService = mock(UserService.class);

        List<UserModel> users = new ArrayList<>();
        UserModel zs = new UserModel();
        zs.setId(1);
        zs.setName("张三");
        zs.setAge(10);
        zs.setSex("男");
        users.add(zs);
        UserModel ls = new UserModel();
        ls.setId(2);
        ls.setName("李四");
        ls.setAge(20);
        ls.setSex("女");
        users.add(ls);
        when(userService.getUsers()).thenReturn(users);
        when(userService.getUserById(1)).thenReturn(zs);
        when(userService.getUserById(2)).thenReturn(ls);

        when(userService.createUser(anyObject())).then(invocation -> {
            UserModel args = invocation.getArgumentAt(0, UserModel.class);
            args.setId(1);
            System.out.println("put:" + args);
            return 1;
        });
        when(userService.updateUser(anyObject())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                UserModel args = invocation.getArgumentAt(0, UserModel.class);
                System.out.println("post:" + args);
                return 1;
            }
        });
    }
}
