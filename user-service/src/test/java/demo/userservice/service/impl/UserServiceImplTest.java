package demo.userservice.service.impl;

import demo.userservice.model.UserModel;
import demo.userservice.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * UserServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>07/29/2016</pre>
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:/applicationContext.xml")
public class UserServiceImplTest {

    @InjectMocks
    @Autowired
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after() throws Exception {
    }

    UserModel getUserModel(){
        UserModel zs = new UserModel();
        zs.setId(1);
        zs.setName("张三");
        zs.setSex("男");
        return zs;
    }

    /**
     * Method: getUserById(int userId)
     */
    @Test
    public void testGetUserById() throws Exception {
        UserModel zs = getUserModel();
        when(userRepository.selectByPrimaryKey(zs.getId())).thenReturn(zs);
        UserModel user = userService.getUserById(zs.getId());
        Assert.assertEquals(zs,user);
        verify(userRepository).selectByPrimaryKey(zs.getId());

    }

    /**
     * Method: updateUser(UserModel userModel)
     */
    @Test
    public void testUpdateUser() throws Exception {
        UserModel zs = getUserModel();
        when(userRepository.updateByPrimaryKeySelective(zs)).thenReturn(1);
        Assert.assertEquals(1,userService.updateUser(zs));
        verify(userRepository).updateByPrimaryKeySelective(zs);
    }

    /**
     * Method: createUser(UserModel userModel)
     */
    @Test
    public void testCreateUser() throws Exception {
        UserModel zs = getUserModel();
        doNothing().when(userRepository).insertSelective(zs);
        int newId = userService.createUser(zs);
        Assert.assertEquals(zs.getId().intValue(),newId);
        verify(userRepository).insertSelective(zs);
    }

    /**
     * Method: getUsers()
     */
    @Test
    public void testGetUsers() throws Exception {
        List<UserModel> userModels = Arrays.asList(getUserModel(), getUserModel());
        when(userRepository.selectList()).thenReturn(userModels);
        List<UserModel> users = userService.getUsers();
        Assert.assertEquals(userModels,users);
        verify(userRepository).selectList();
    }


} 
