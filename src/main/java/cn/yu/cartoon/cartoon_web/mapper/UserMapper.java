package cn.yu.cartoon.cartoon_web.mapper;

import cn.yu.cartoon.cartoon_web.pojo.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User selectUserByUserName(String userName);

    User selectUserByUserEmail(String userEmail);

    User selectUserByUserPhone(String userPhone);

    User selectUserByUserPopularize(String userPopularize);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}