package com.leoleo.film.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.leoleo.film.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {
    User getUserByName(String name);
    List<User> getUserByPage(@Param("pageNo")int pageNo, @Param("pageSize")int pageSize);
    int insertUser(@Param("name")String name, @Param("password") String password, @Param("role")String role);
    int updateUser(@Param("name")String name,@Param("password")String password);
    int updateBalance(@Param("name")String name,@Param("balance")BigDecimal balance);
}
