package com.gpingguo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gpingguo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * @Author gpingguo
     * 通过账户查询密码
     * @Date 2023/8/16
     * @param account:
     * @return String
     */
    @Select("Select password FROM user WHERE account = #{account}")
    @ResultType(String.class)
    String selectByAccount(@Param("account") String account);

}
