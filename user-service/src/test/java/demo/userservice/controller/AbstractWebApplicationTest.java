package demo.userservice.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by oneday on 2016/7/29 0029.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(value = {
        @ContextConfiguration(name = "parent", locations = "classpath:/testApplicationContext.xml"),
        @ContextConfiguration(name = "child", locations = "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")
})
public abstract class AbstractWebApplicationTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;


}
