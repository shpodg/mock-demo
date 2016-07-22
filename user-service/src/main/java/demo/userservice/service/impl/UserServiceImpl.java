package demo.userservice.service.impl;

import demo.userservice.model.UserModel;
import demo.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserModel getUserById(String userId) {
        return null;
    }

    @Override
    public int updateUser(UserModel userModel) {
        return 0;
    }

    @Override
    public int createUser(UserModel userModel) {
        return 0;
    }

    @Override
    public List<UserModel> getUsers() {
        return null;
    }
}
