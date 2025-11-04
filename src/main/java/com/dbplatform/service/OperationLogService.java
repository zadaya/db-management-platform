package com.dbplatform.service;

import com.dbplatform.entity.OperationLog;
import com.dbplatform.mapper.OperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface OperationLogService {
    void addLog(String operationType, String operationContent, Long userId, HttpServletRequest request);
    List<OperationLog> getLogsByUserId(Long userId);
    List<OperationLog> getLogsByType(String type);
    List<OperationLog> getAllLogs();
    List<OperationLog> getRecentLogs(int limit);
    Long getTotalLogCount();
    Long getLogCountByUser(Long userId);
}

@Service
class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void addLog(String operationType, String operationContent, Long userId, HttpServletRequest request) {
        OperationLog log = new OperationLog();
        log.setUserId(userId);
        log.setOperationType(operationType);
        log.setOperationContent(operationContent);
        log.setIpAddress(getIpAddress(request));
        log.setOperationTime(new Date());
        operationLogMapper.insert(log);
    }

    @Override
    public List<OperationLog> getLogsByUserId(Long userId) {
        return operationLogMapper.findByUserId(userId);
    }

    @Override
    public List<OperationLog> getLogsByType(String type) {
        return operationLogMapper.findByType(type);
    }

    @Override
    public List<OperationLog> getAllLogs() {
        return operationLogMapper.findAll();
    }

    @Override
    public List<OperationLog> getRecentLogs(int limit) {
        return operationLogMapper.findRecentLogs(limit);
    }

    @Override
    public Long getTotalLogCount() {
        return operationLogMapper.countTotalLogs();
    }

    @Override
    public Long getLogCountByUser(Long userId) {
        return operationLogMapper.countLogsByUser(userId);
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip != null ? ip.split(",")[0] : "";
    }
}