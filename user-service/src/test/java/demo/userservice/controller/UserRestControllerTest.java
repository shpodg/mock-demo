package demo.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.userservice.model.UserModel;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserRestController Tester.
 *
 * @author shpodg
 * @version 1.0
 * @since <pre>07/29/2016</pre>
 */
public class UserRestControllerTest extends AbstractWebApplicationTest {

    ObjectMapper objectMapper = new ObjectMapper(); //用来把对象转换json

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())  //配置默认输出处理结果
                .alwaysExpect(status().is2xxSuccessful())  //默认返回成功
                .alwaysExpect(content().contentType(APPLICATION_JSON_UTF8)) //
                .build();
    }

    UserModel getUserModel(){
        UserModel zs = new UserModel();
        zs.setName("张三test");
        zs.setSex("男");
        zs.setAge(33);
        return zs;
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getUserById(@PathVariable int userId)
     */
    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/user/{userId}",1))
                .andExpect(content().string(CoreMatchers.containsString("name")));
    }

    /**
     * Method: updateUser(@RequestBody UserModel user)
     */
    @Test
    public void testUpdateUser() throws Exception {
        UserModel user = getUserModel();
        user.setId(1);
        mockMvc.perform(put("/user").contentType(APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(user)))
                .andExpect(content().string("{\"status\":\"ok\"}"));
    }

    /**
     * Method: createUser(@RequestBody UserModel user)
     */
    @Test
    public void testCreateUser() throws Exception {
        UserModel user = getUserModel();
        MvcResult result = mockMvc.perform(post("/user").contentType(APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(user))) //content("{\"name\":\"张三test\",\"age\":33,\"sex\":\"男\"}"))
                .andExpect(content().string("{\"status\":\"ok\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println("Location:" + result.getResponse().getHeader("Location"));
    }

    /**
     * Method: getUsers()
     */
    @Test
    public void testGetUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/user"))
                .andExpect(content().string(CoreMatchers.containsString("[")))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }



} 
