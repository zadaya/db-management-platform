package com.dbplatform.mapper;

import com.dbplatform.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    void insert(OperationLog log);
    List<OperationLog> findByUserId(Long userId);
    List<OperationLog> findByType(String type);
    List<OperationLog> findAll();
    List<OperationLog> findRecentLogs(@Param("limit") int limit);
    Long countTotalLogs();
    Long countLogsByUser(Long userId);
}