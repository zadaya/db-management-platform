package com.dbplatform.mapper;

import com.dbplatform.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    User findById(Long id);
    void insert(User user);
    void update(User user);
    void updatePassword(@Param("id") Long id, @Param("password") String password);
}