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

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

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
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        // 模拟登录用户
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setRealName("管理员");
        user.setEmail("admin@example.com");
        // user.setRole("admin");
        session.setAttribute("currentUser", user);
        
        model.addAttribute("user", user);

        return "index";
    }
}