package demo.userfront.service.impl;

import demo.userfront.BaseTest;
import demo.userfront.vo.UserVo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static demo.userfront.service.impl.UserServiceImpl.USER_SERVICE_URL;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * UserServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */

public class UserServiceImplTest extends BaseTest {

    @InjectMocks
    @Autowired
    UserServiceImpl userService;

    @Mock
    RestTemplate restTemplate;

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

        when(restTemplate.getForObject(USER_SERVICE_URL+"/{id}",UserVo.class,zs.getId())).thenReturn(zs);
        UserVo user = userService.getUser(zs.getId());
        Assert.assertEquals(zs,user);
        verify(restTemplate).getForObject(USER_SERVICE_URL+"/{id}",UserVo.class,zs.getId());

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
        userService.updateUser(zs);
        verify(restTemplate).put(USER_SERVICE_URL,zs);
    }

    /**
     * Method: createUser(UserVo user)
     */
    @Test
    public void testCreateUser() throws Exception {
        UserVo zs = getUserVo();
        when(restTemplate.postForLocation(USER_SERVICE_URL, zs)).thenReturn(URI.create("/user/"+zs.getId()));
        userService.createUser(zs);
        verify(restTemplate).postForLocation(USER_SERVICE_URL, zs);
    }

    /**
     * Method: getUsers()
     */
    @Test
    public void testGetUsers() throws Exception {
        List<UserVo> mockUsers = new ArrayList<>();
        mockUsers.add(getUserVo());
        mockUsers.add(getUserVo());
        when(restTemplate.getForObject(USER_SERVICE_URL,List.class)).thenReturn(mockUsers);
        List<UserVo> users = userService.getUsers();
        verify(restTemplate).getForObject(USER_SERVICE_URL,List.class);
        //System.out.println(users);
        Assert.assertEquals(mockUsers,users);
    }


} 
