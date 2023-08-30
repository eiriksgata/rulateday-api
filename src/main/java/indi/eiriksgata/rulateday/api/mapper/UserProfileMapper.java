package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.rbac.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfile> {
  /**
   * select user profile by user id
   *
   * @param userId user id
   * @return user profile
   */
  UserProfile selectByUserId(@Param("userId") Long userId);

  /**
   * delete user profile by user id
   *
   * @param userId user id
   * @return true or false
   */
  Long deleteByUserId(@Param("userId") Long userId);
}
