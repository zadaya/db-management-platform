package com.dbplatform.mapper;

import com.dbplatform.entity.DbConnection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DbConnectionMapper {
    List<DbConnection> findAll();
    List<DbConnection> findByUserId(Long userId);
    DbConnection findById(Long id);
    void insert(DbConnection connection);
    void update(DbConnection connection);
    void deleteById(Long id);
    Long countTotalConnections();
    Long countConnectionsByUser(Long userId);
}