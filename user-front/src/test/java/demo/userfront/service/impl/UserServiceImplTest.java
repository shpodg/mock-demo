package demo.userfront.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.userfront.util.HttpUtils;
import demo.userfront.vo.ResponseStatus;
import demo.userfront.vo.UserVo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * UserServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
public class UserServiceImplTest {

    @InjectMocks
    @Autowired
    UserServiceImpl userService;
    @Mock
    HttpUtils httpUtils;
    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: getUser(String userId)
     */
    @Test
    public void testGetUser() throws Exception {
        UserVo zs = getUserVo();

        when(httpUtils.get(userService.USER_SERVICE_URL+"/"+zs.getId())).thenReturn(objectMapper.writeValueAsString(zs));
        UserVo user = userService.getUser(zs.getId());
        Assert.assertEquals(zs,user);
        verify(httpUtils).get(userService.USER_SERVICE_URL+"/"+zs.getId());

    }

    private UserVo getUserVo() {
        UserVo zs = new UserVo();
        zs.setId("1");
        zs.setName("张三");
        zs.setAge(10);
        zs.setSex("男");
        return zs;
    }

    /**
     * Method: updateUser(UserVo user)
     */
    @Test
    public void testUpdateUser() throws Exception {
        UserVo zs = getUserVo();
        when(httpUtils.post(UserServiceImpl.USER_SERVICE_URL,zs)).thenReturn(objectMapper.writeValueAsString(ResponseStatus.STATUS_OK));
        userService.updateUser(zs);
        verify(httpUtils).post(UserServiceImpl.USER_SERVICE_URL,zs);
    }

    /**
     * Method: createUser(UserVo user)
     */
    @Test
    public void testCreateUser() throws Exception {
        UserVo zs = getUserVo();
        when(httpUtils.put(UserServiceImpl.USER_SERVICE_URL,zs)).thenReturn(objectMapper.writeValueAsString(ResponseStatus.STATUS_OK));
        userService.createUser(zs);
        verify(httpUtils).put(UserServiceImpl.USER_SERVICE_URL,zs);
    }

    /**
     * Method: getUsers()
     */
    @Test
    public void testGetUsers() throws Exception {
        List<UserVo> mockUsers = new ArrayList<>();
        mockUsers.add(getUserVo());
        mockUsers.add(getUserVo());
        when(httpUtils.get(userService.USER_SERVICE_URL)).thenReturn(objectMapper.writeValueAsString(mockUsers));
        List<UserVo> users = userService.getUsers();
        verify(httpUtils).get(userService.USER_SERVICE_URL);
        //System.out.println(users);
        Assert.assertEquals(mockUsers,users);
    }


} 
