/**
 * 仪表盘控制器
 * 处理仪表盘页面的相关请求
 */
package com.dbplatform.controller;

import com.dbplatform.entity.User;
import com.dbplatform.service.ConnectionService;
import com.dbplatform.service.OperationLogService;
import com.dbplatform.service.SqlExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ConnectionService dbConnectionService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private SqlExecutionService sqlExecutionService;

    /**
     * 跳转到仪表盘页面
     * @param session 会话对象
     * @param model 模型对象
     * @return 仪表盘页面的视图名称
     */
    @GetMapping
    public String page(HttpSession session, Model model) {
        // 模拟登录用户
        User user = new User();
        user.setId(1L);

        // 统计信息
        long totalConnections = dbConnectionService.getTotalConnectionCount();
        long userConnections = dbConnectionService.getConnectionCountByUser(user.getId());
        long totalLogs = operationLogService.getTotalLogCount();
        long userLogs = operationLogService.getLogCountByUser(user.getId());
        long totalTables = sqlExecutionService.getTotalTableCount();
        long recentSqlExecutions = sqlExecutionService.getRecentSqlExecutionCount();

        // 最近日志
        model.addAttribute("recentLogs", operationLogService.getRecentLogs(10));

        // 系统状态
        model.addAttribute("systemStatus", "正常");
        model.addAttribute("statusColor", "success");

        // 传递数据到视图
        model.addAttribute("user", user);
        model.addAttribute("totalConnections", totalConnections);
        model.addAttribute("userConnections", userConnections);
        model.addAttribute("totalLogs", totalLogs);
        model.addAttribute("userLogs", userLogs);
        model.addAttribute("totalTables", totalTables);
        model.addAttribute("recentSqlExecutions", recentSqlExecutions);

        return "dashboard";
    }
}