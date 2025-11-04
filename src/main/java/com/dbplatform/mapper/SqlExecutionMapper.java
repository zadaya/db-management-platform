package com.dbplatform.mapper;

import com.dbplatform.entity.SqlExecution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SqlExecutionMapper {
    void insert(SqlExecution execution);
    List<SqlExecution> findByUserId(Long userId);
    List<SqlExecution> findRecent(@Param("limit") int limit);
    Long countTotal();
    Long countByUser(Long userId);
}