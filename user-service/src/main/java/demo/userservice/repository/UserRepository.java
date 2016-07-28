package demo.userservice.repository;

import demo.userservice.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** * Created by oneday on 2016/7/26 0026.
 */
@Repository
public interface UserRepository {

    int deleteByPrimaryKey(@Param("id") Integer id);

    void insert(UserModel record);

    void insertSelective(UserModel record);

    UserModel selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);

    List<UserModel> selectList();
}
