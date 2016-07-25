package demo.userservice.service.impl;

import demo.userservice.model.UserModel;
import demo.userservice.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    @Transactional(readOnly = true)
    public UserModel getUserById(String userId) {
        return null;
    }

    @Override
    @Transactional
    public int updateUser(UserModel userModel) {
        return 0;
    }

    @Override
    @Transactional
    public int createUser(UserModel userModel) {
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> getUsers() {
        return null;
    }
}
