package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.rbac.User;
import indi.eiriksgata.rulateday.api.qo.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
  /**
   * select user
   *
   * @param userQuery user query object
   * @return user
   */
  User selectUser(@Param("userQuery") UserQuery userQuery);

  /**
   * select all users
   *
   * @param userQuery user query object
   * @return user list
   */
  List<User> selectUsers(@Param("userQuery") UserQuery userQuery);

  /**
   * select user-role-permission by user id
   *
   * @param userQuery user query object
   * @return user list
   */
  User selectUrp(@Param("userQuery") UserQuery userQuery);
}
