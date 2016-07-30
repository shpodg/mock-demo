package demo.userfront.controller;

import demo.userfront.service.UserService;
import demo.userfront.vo.UserVo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;

/**
 * UserController Tester.
 * mock隔离测试
 * @author <Authors name>
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:/testApplicationContext.xml"),
        @ContextConfiguration(name = "child",locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")
})
public class UserControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    UserService userService;
    private MockMvc mockMvc;

    /**
     * 完整模拟springMVC环境
     * 1、测试类需要指定 @WebAppConfiguration 默认web root 为 src/main/webapp
     * 2、@ContextHierarchy：指定容器层次
     * 3、通过@Autowired WebApplicationContext ：注入web环境的ApplicationContext容器
     * 4、然后通过MockMvcBuilders.webAppContextSetup(wac).build()创建一个MockMvc进行测试
     * @throws Exception
     */
    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void after() throws Exception {
    }

    public UserVo getUser(){
        UserVo zs = new UserVo();
        zs.setId("1");
        zs.setName("张三");
        zs.setAge(10);
        zs.setSex("男");
        return zs;
    }

    /**
     * Method: getUsers(ModelMap model)
     */
    @Test
    public void testGetUsers() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: getUser(@PathVariable String userId, ModelMap model)
     */
    @Test
    public void testGetUser() throws Exception {
        UserVo zs = getUser();
        when(userService.getUser("1")).thenReturn(zs);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(MockMvcResultMatchers.view().name("userDtail"))
                .andExpect(MockMvcResultMatchers.model().attribute("user",zs))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * Method: newUser()
     */
    @Test
    public void testNewUser() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: editUser(@PathVariable String userId, ModelMap model)
     */
    @Test
    public void testEditUser() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateUser(UserVo user)
     */
    @Test
    public void testUpdateUser() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: createUser(UserVo user)
     */
    @Test
    public void testCreateUser() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: afterPropertiesSet()
     */
    @Test
    public void testAfterPropertiesSet() throws Exception {
//TODO: Test goes here... 
    }


} 
