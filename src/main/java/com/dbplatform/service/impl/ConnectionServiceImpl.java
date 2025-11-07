package com.dbplatform.service.impl;

import com.dbplatform.entity.DbConnection;
import com.dbplatform.mapper.ConnectionMapper;
import com.dbplatform.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionMapper connectionMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<DbConnection> getAllConnections() {
        return connectionMapper.findAll();
    }

    @Override
    public List<DbConnection> getConnectionsByUserId(Long userId) {
        return connectionMapper.findByUserId(userId);
    }

    @Override
    public List<DbConnection> getConnectionsByCondition(Map<String, Object> params) {
        return connectionMapper.findByCondition(params);
    }

    @Override
    public List<DbConnection> getConnectionsByConditionWithPagination(Map<String, Object> params) {
        return connectionMapper.findByConditionWithPagination(params);
    }

    @Override
    public Long getConnectionsCountByCondition(Map<String, Object> params) {
        return connectionMapper.countByCondition(params);
    }

    @Override
    public DbConnection getConnectionById(Long id) {
        return connectionMapper.findById(id);
    }

    @Override
    public void addConnection(DbConnection connection) {
        // 加密密码
        if (connection.getPassword() != null && !connection.getPassword().isEmpty()) {
            connection.setPassword(passwordEncoder.encode(connection.getPassword()));
        }
        connection.setCreateTime(new Date());
        connection.setUpdateTime(new Date());
        connectionMapper.insert(connection);
    }

    @Override
    public void updateConnection(DbConnection connection) {
        // 如果密码不为空，则加密后保存
        if (connection.getPassword() != null && !connection.getPassword().isEmpty()) {
            connection.setPassword(passwordEncoder.encode(connection.getPassword()));
        } else {
            // 如果密码为空，则不更新密码，保持原有密码
            DbConnection existingConnection = connectionMapper.findById(connection.getId());
            if (existingConnection != null) {
                connection.setPassword(existingConnection.getPassword());
            }
        }
        connection.setUpdateTime(new Date());
        connectionMapper.update(connection);
    }

    @Override
    public void deleteConnection(Long id) {
        connectionMapper.deleteById(id);
    }

    @Override
    public void deleteConnectionBatch(List<Long> ids) {
        connectionMapper.deleteBatch(ids);
    }

    @Override
    public Map<String, Object> testConnection(DbConnection connection) {
        Map<String, Object> result = new java.util.HashMap<>();
        java.sql.Connection conn = null;
        try {
            // 根据数据库类型加载驱动
            switch (connection.getDbType()) {
                case "mysql":
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    break;
                case "oracle":
                    Class.forName("oracle.jdbc.OracleDriver");
                    break;
                case "postgresql":
                    Class.forName("org.postgresql.Driver");
                    break;
                case "sqlserver":
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    break;
                default:
                    throw new RuntimeException("不支持的数据库类型: " + connection.getDbType());
            }

            // 构建连接URL
            String url = buildConnectionUrl(connection);

            // 建立连接
            conn = java.sql.DriverManager.getConnection(url, connection.getUsername(), connection.getPassword());
            result.put("success", true);
            result.put("message", "连接成功");
        } catch (ClassNotFoundException e) {
            result.put("success", false);
            result.put("message", "驱动加载失败: " + e.getMessage());
        } catch (java.sql.SQLException e) {
            result.put("success", false);
            result.put("message", "连接失败: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (java.sql.SQLException e) {
                    // 忽略关闭异常
                }
            }
        }
        return result;
    }

    @Override
    public DbConnection copyConnection(Long id) {
        DbConnection original = connectionMapper.findById(id);
        if (original == null) {
            return null;
        }

        DbConnection copy = new DbConnection();
        copy.setName(original.getName() + "_复制");
        copy.setDbType(original.getDbType());
        copy.setHost(original.getHost());
        copy.setPort(original.getPort());
        copy.setDatabaseName(original.getDatabaseName());
        copy.setUsername(original.getUsername());
        copy.setPassword(original.getPassword());
        // 复制时不设置createUser和createTime，这些将在保存时设置

        return copy;
    }

    @Override
    public Long getTotalConnectionCount() {
        return connectionMapper.countTotalConnections();
    }

    @Override
    public Long getConnectionCountByUser(Long userId) {
        return connectionMapper.countConnectionsByUser(userId);
    }

    /**
     * 构建数据库连接URL
     */
    private String buildConnectionUrl(DbConnection connection) {
        switch (connection.getDbType()) {
            case "mysql":
                return String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC",
                        connection.getHost(), connection.getPort(), connection.getDatabaseName());
            case "oracle":
                return String.format("jdbc:oracle:thin:@%s:%d:%s",
                        connection.getHost(), connection.getPort(), connection.getDatabaseName());
            case "postgresql":
                return String.format("jdbc:postgresql://%s:%d/%s",
                        connection.getHost(), connection.getPort(), connection.getDatabaseName());
            case "sqlserver":
                return String.format("jdbc:sqlserver://%s:%d;databaseName=%s",
                        connection.getHost(), connection.getPort(), connection.getDatabaseName());
            default:
                throw new RuntimeException("不支持的数据库类型: " + connection.getDbType());
        }
    }
}