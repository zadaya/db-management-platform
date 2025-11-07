package com.dbplatform.mapper;

import com.dbplatform.entity.DbConnection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ConnectionMapper {
    List<DbConnection> findAll();
    List<DbConnection> findByUserId(Long userId);
    List<DbConnection> findByCondition(Map<String, Object> params);
    List<DbConnection> findByConditionWithPagination(Map<String, Object> params);
    Long countByCondition(Map<String, Object> params);
    DbConnection findById(Long id);
    void insert(DbConnection connection);
    void update(DbConnection connection);
    void deleteById(Long id);
    void deleteBatch(@Param("ids") List<Long> ids);
    Long countTotalConnections();
    Long countConnectionsByUser(Long userId);
}