package demo.userfront.service.impl;

import demo.userfront.service.UserService;
import demo.userfront.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Service
public class UserServiceImpl implements UserService {
    public static final String USER_SERVICE_URL="http://localhost:8081/user";
    @Autowired
    RestTemplate restTemplate;
    @Override
    public UserVo getUser(String userId) {
        return restTemplate.getForObject(USER_SERVICE_URL+"/{id}",UserVo.class,userId);
    }

    @Override
    public int updateUser(UserVo user) {
        restTemplate.put(USER_SERVICE_URL,user);
        return 0;
    }

    @Override
    public String createUser(UserVo user) {
        URI location = restTemplate.postForLocation(USER_SERVICE_URL, user);
        return location.toString();
    }

    @Override
    public List<UserVo> getUsers() {
        return restTemplate.getForObject(USER_SERVICE_URL,List.class);
    }
}
