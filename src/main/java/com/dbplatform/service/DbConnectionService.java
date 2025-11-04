package com.dbplatform.service;

import com.dbplatform.entity.DbConnection;
import com.dbplatform.mapper.DbConnectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface DbConnectionService {
    List<DbConnection> getAllConnections();
    List<DbConnection> getConnectionsByUserId(Long userId);
    DbConnection getConnectionById(Long id);
    void addConnection(DbConnection connection);
    void updateConnection(DbConnection connection);
    void deleteConnection(Long id);
    Long getTotalConnectionCount();
    Long getConnectionCountByUser(Long userId);
}

@Service
class DbConnectionServiceImpl implements DbConnectionService {

    @Autowired
    private DbConnectionMapper dbConnectionMapper;

    @Override
    public List<DbConnection> getAllConnections() {
        return dbConnectionMapper.findAll();
    }

    @Override
    public List<DbConnection> getConnectionsByUserId(Long userId) {
        return dbConnectionMapper.findByUserId(userId);
    }

    @Override
    public DbConnection getConnectionById(Long id) {
        return dbConnectionMapper.findById(id);
    }

    @Override
    public void addConnection(DbConnection connection) {
        connection.setCreateTime(new Date());
        connection.setUpdateTime(new Date());
        dbConnectionMapper.insert(connection);
    }

    @Override
    public void updateConnection(DbConnection connection) {
        connection.setUpdateTime(new Date());
        dbConnectionMapper.update(connection);
    }

    @Override
    public void deleteConnection(Long id) {
        dbConnectionMapper.deleteById(id);
    }

    @Override
    public Long getTotalConnectionCount() {
        return dbConnectionMapper.countTotalConnections();
    }

    @Override
    public Long getConnectionCountByUser(Long userId) {
        return dbConnectionMapper.countConnectionsByUser(userId);
    }
}