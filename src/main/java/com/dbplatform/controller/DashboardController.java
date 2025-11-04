/**
 * 仪表盘控制器
 * 处理仪表盘页面的相关请求
 */
package com.dbplatform.controller;

import com.dbplatform.entity.User;
import com.dbplatform.service.DbConnectionService;
import com.dbplatform.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private DbConnectionService dbConnectionService;

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 跳转到仪表盘页面
     * @param session 会话对象
     * @param model 模型对象
     * @return 仪表盘页面的视图名称
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }

        // 统计信息
        long totalConnections = dbConnectionService.getTotalConnectionCount();
        long userConnections = dbConnectionService.getConnectionCountByUser(user.getId());
        long totalLogs = operationLogService.getTotalLogCount();
        long userLogs = operationLogService.getLogCountByUser(user.getId());

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

        return "dashboard";
    }
}