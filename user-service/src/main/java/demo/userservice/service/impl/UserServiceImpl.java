package demo.userservice.service.impl;

import demo.userservice.model.UserModel;
import demo.userservice.repository.UserRepository;
import demo.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public UserModel getUserById(int userId) {
        return userRepository.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional
    public int updateUser(UserModel userModel) {
        return userRepository.updateByPrimaryKeySelective(userModel);
    }

    @Override
    @Transactional
    public int createUser(UserModel userModel) {
        userRepository.insertSelective(userModel);
        return userModel.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> getUsers() {
        return userRepository.selectList();
    }
}
