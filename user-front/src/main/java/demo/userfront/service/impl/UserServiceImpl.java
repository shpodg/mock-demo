package demo.userfront.service.impl;

import demo.userfront.model.UserModel;
import demo.userfront.service.UserService;
import demo.userfront.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserVo getUser(String userId) {
        return null;
    }

    @Override
    public int updateUser(String userId) {
        return 0;
    }

    @Override
    public int createUser(UserModel user) {
        return 0;
    }

    @Override
    public List<UserVo> getUsers() {
        return null;
    }
}
