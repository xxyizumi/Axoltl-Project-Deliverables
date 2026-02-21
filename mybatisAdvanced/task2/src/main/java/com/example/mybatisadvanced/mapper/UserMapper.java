package com.example.mybatisadvanced.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.mybatisadvanced.entity.User;

@Mapper
public interface UserMapper {

    List<User> findAll();

    User findByIdWithOrders(Long id);

    List<User> findAllWithOrders();

    List<User> findAllWithOrdersAndOrderDetails();

    List<User> findAllForLazyWithOrders();
}