package demo.userfront.controller;

import demo.userfront.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * IndexController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>07/29/2016</pre>
 */

public class IndexControllerTest{

    @InjectMocks
    IndexController indexController;
    @Mock
    UserService userService;

    MockMvc mockMvc;

    /**
     * standaloneSetup 方式创建单独的Controller环境，
     * 首先自己创建相应的控制器，注入相应的依赖
     * spring github 官方示例
     * https://github.com/spring-projects/spring-framework/tree/master/spring-test/src/test/java/org/springframework/test/web/servlet/samples
     * @throws Exception
     */
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: index()
     */
    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("所有用户")));
    }



} 
