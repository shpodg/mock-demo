package demo.userservice.service;

import demo.userservice.model.UserModel;

import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
public interface UserService {
    UserModel getUserById(String userId);
    int updateUser(UserModel userModel);
    int createUser(UserModel userModel);
    List<UserModel> getUsers();
}
