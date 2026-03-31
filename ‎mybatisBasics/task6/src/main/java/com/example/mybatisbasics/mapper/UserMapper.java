package com.example.mybatisbasics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mybatisbasics.entity.User;

@Mapper
public interface UserMapper {
    
    List<User> findAll();
    
    User findById(Long id);
    
    int insert(User user);
    
    int update(User user);
    
    int deleteById(Long id);
    
    List<User> findByCondition(@Param("name") String name, @Param("email") String email);
}
