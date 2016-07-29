package demo.userservice.service;

import demo.userservice.model.UserModel;

import java.util.List;

/**
 * Created by oneday on 2016/7/22 0022.
 */
public interface UserService {
    /**
     * 根据userId获取用户model
     * @param userId
     * @return
     */
    UserModel getUserById(int userId);

    /**
     * 根据userId更新用户model
     * @param userModel
     * @return
     */
    int updateUser(UserModel userModel);

    /**
     * 创建用户
     * @param userModel
     * @return
     */
    int createUser(UserModel userModel);

    /**
     * 获取所有用户
     * @return
     */
    List<UserModel> getUsers();
}
