package com.dbplatform.service;

import com.dbplatform.entity.SqlExecution;

import java.util.List;

public interface SqlExecutionService {
    void addExecution(SqlExecution execution);
    List<SqlExecution> getExecutionsByUserId(Long userId);
    List<SqlExecution> getRecentExecutions(int limit);
    Long getTotalExecutionCount();
    Long getExecutionCountByUser(Long userId);
    Long getTotalTableCount();
    Long getRecentSqlExecutionCount();
}