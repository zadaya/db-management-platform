package com.dbplatform.service;

import com.dbplatform.entity.SqlExecution;
import com.dbplatform.mapper.SqlExecutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SqlExecutionServiceImpl implements SqlExecutionService {

    @Autowired
    private SqlExecutionMapper sqlExecutionMapper;

    @Override
    public void addExecution(SqlExecution execution) {
        execution.setExecutionTime(new Date());
        sqlExecutionMapper.insert(execution);
    }

    @Override
    public List<SqlExecution> getExecutionsByUserId(Long userId) {
        return sqlExecutionMapper.findByUserId(userId);
    }

    @Override
    public List<SqlExecution> getRecentExecutions(int limit) {
        return sqlExecutionMapper.findRecent(limit);
    }

    @Override
    public Long getTotalExecutionCount() {
        return sqlExecutionMapper.countTotal();
    }

    @Override
    public Long getExecutionCountByUser(Long userId) {
        return sqlExecutionMapper.countByUser(userId);
    }

    @Override
    public Long getTotalTableCount() {
        // 这里需要根据实际情况实现，暂时返回模拟数据
        return 123L;
    }

    @Override
    public Long getRecentSqlExecutionCount() {
        // 这里需要根据实际情况实现，暂时返回模拟数据
        return 45L;
    }
}