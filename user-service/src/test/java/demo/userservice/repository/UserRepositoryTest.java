package demo.userservice.repository;

import demo.userservice.model.UserModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * UserRepository Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>07/28/2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
@FixMethodOrder(MethodSorters.DEFAULT)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    UserModel record = new UserModel();
    UserModel record2 = new UserModel();
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: insert(UserModel record)
     */
    @Test
    public void testInsert() throws Exception {

        record.setName("王五");
        record.setAge(30);
        record.setSex("男");
        userRepository.insert(record);
        Assert.assertNotNull(record.getId());
    }

    /**
     * Method: insertSelective(UserModel record)
     */
    @Test
    public void testInsertSelective() throws Exception {
        record2.setName("11");
        userRepository.insertSelective(record2);
        Assert.assertNotNull(record2.getId());
    }

    /**
     * Method: selectByPrimaryKey(Integer id)
     */
    @Test
    public void testSelectByPrimaryKey() throws Exception {
        UserModel user = userRepository.selectByPrimaryKey(record.getId());
        System.out.println(user);
    }

    /**
     * Method: updateByPrimaryKeySelective(UserModel record)
     */
    @Test
    public void testUpdateByPrimaryKeySelective() throws Exception {
        record2.setAge(1);
        userRepository.updateByPrimaryKeySelective(record2);
    }

    /**
     * Method: updateByPrimaryKey(UserModel record)
     */
    @Test
    public void testUpdateByPrimaryKey() throws Exception {
        record.setName("2222");
        record.setSex(null);
        userRepository.updateByPrimaryKey(record);

    }

    /**
     * Method: deleteByPrimaryKey(Integer id)
     */
    @Test
    public void testDeleteByPrimaryKey() throws Exception {
        userRepository.deleteByPrimaryKey(record.getId());
        userRepository.deleteByPrimaryKey(record2.getId());

    }

    @Test
    public void testSelectList(){
        List<UserModel> users = userRepository.selectList();
        System.out.println(users);
    }


} 
