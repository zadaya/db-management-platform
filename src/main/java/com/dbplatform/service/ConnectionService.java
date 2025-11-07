package com.dbplatform.service;

import com.dbplatform.entity.DbConnection;

import java.util.List;
import java.util.Map;

public interface ConnectionService {
    List<DbConnection> getAllConnections();
    List<DbConnection> getConnectionsByUserId(Long userId);
    List<DbConnection> getConnectionsByCondition(Map<String, Object> params);
    List<DbConnection> getConnectionsByConditionWithPagination(Map<String, Object> params);
    Long getConnectionsCountByCondition(Map<String, Object> params);
    DbConnection getConnectionById(Long id);
    void addConnection(DbConnection connection);
    void updateConnection(DbConnection connection);
    void deleteConnection(Long id);
    void deleteConnectionBatch(List<Long> ids);
    Map<String, Object> testConnection(DbConnection connection);
    DbConnection copyConnection(Long id);
    Long getTotalConnectionCount();
    Long getConnectionCountByUser(Long userId);
}