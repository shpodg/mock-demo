package demo.userfront.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.userfront.exception.BusinessException;
import demo.userfront.service.UserService;
import demo.userfront.util.HttpUtils;
import demo.userfront.vo.ResponseStatus;
import demo.userfront.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Service
public class UserServiceImpl implements UserService {
    public static final String USER_SERVICE_URL="http://localhost:8081/user";
    @Autowired
    HttpUtils httpUtils;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public UserVo getUser(String userId) {
        String result = httpUtils.get(USER_SERVICE_URL+"/"+userId);
        try {
            UserVo userVo = objectMapper.readValue(result, UserVo.class);
            return userVo;
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public int updateUser(UserVo user) {
        String result = httpUtils.post(USER_SERVICE_URL,user);
        try {
            ResponseStatus responseStatus = objectMapper.readValue(result,ResponseStatus.class);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
        return 0;
    }

    @Override
    public int createUser(UserVo user) {
        String result = httpUtils.put(USER_SERVICE_URL,user);
        try {
            ResponseStatus responseStatus = objectMapper.readValue(result,ResponseStatus.class);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<UserVo> getUsers() {
        String result = httpUtils.get(USER_SERVICE_URL);
        try {
            List<UserVo> users = objectMapper.readValue(result,new TypeReference<List<UserVo>>(){});
            return users;
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
