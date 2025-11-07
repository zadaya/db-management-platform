/**
 * 数据库连接控制器
 * 处理数据库连接管理的相关请求
 */
package com.dbplatform.controller;

import com.dbplatform.entity.DbConnection;
import com.dbplatform.entity.User;
import com.dbplatform.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/connections")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    /**
     * 获取连接管理页面片段
     * 
     * @param session 会话对象
     * @param model   模型对象
     * @return 连接管理页面的视图片段
     */
    @GetMapping
    public String connections(HttpSession session, Model model) {
        // 模拟登录用户
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setRealName("管理员");
        user.setEmail("admin@example.com");
        // user.setRole("admin");
        session.setAttribute("currentUser", user);

        // 获取所有连接
        List<DbConnection> connections = connectionService.getAllConnections();
        model.addAttribute("connections", connections);
        model.addAttribute("user", user);

        return "connections";
    }

    /**
     * 根据条件查询连接
     * 
     * @param params 查询参数
     * @return 连接列表
     */
    @PostMapping("/search")
    @ResponseBody
    public List<DbConnection> searchConnections(@RequestBody Map<String, Object> params) {
        return connectionService.getConnectionsByCondition(params);
    }

    /**
     * 获取连接新增页面片段
     * 
     * @param model 模型对象
     * @return 连接新增页面的视图片段
     */
    @GetMapping("/add")
    public String addConnection(Model model) {
        model.addAttribute("connection", new DbConnection());
        return "connection-form :: connection-form";
    }

    /**
     * 保存新连接
     * 
     * @param connection 连接对象
     * @param session    会话对象
     * @return 重定向到连接管理页面
     */
    @PostMapping("/save")
    public String saveConnection(DbConnection connection, HttpSession session) {
        // 模拟登录用户
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setRealName("管理员");
        user.setEmail("admin@example.com");
        // user.setRole("admin");
        session.setAttribute("currentUser", user);

        connection.setCreateUser(user.getId());
        connectionService.addConnection(connection);

        return "redirect:/connections";
    }

    /**
     * 获取连接编辑页面片段
     * 
     * @param id    连接ID
     * @param model 模型对象
     * @return 连接编辑页面的视图片段
     */
    @GetMapping("/edit/{id}")
    public String editConnection(@PathVariable Long id, Model model) {
        DbConnection connection = connectionService.getConnectionById(id);
        // 编辑时密码字段清空，需要重新输入
        connection.setPassword("");
        model.addAttribute("connection", connection);
        return "connection-form :: connection-form";
    }

    /**
     * 更新连接
     * 
     * @param connection 连接对象
     * @return 重定向到连接管理页面
     */
    @PostMapping("/update")
    public String updateConnection(DbConnection connection) {
        connectionService.updateConnection(connection);
        return "redirect:/connections";
    }

    /**
     * 删除单个连接
     * 
     * @param id 连接ID
     * @return 操作结果
     */
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteConnection(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            connectionService.deleteConnection(id);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 批量删除连接
     * 
     * @param ids 连接ID列表
     * @return 操作结果
     */
    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, Object> deleteConnectionBatch(@RequestBody List<Long> ids) {
        Map<String, Object> result = new HashMap<>();
        try {
            connectionService.deleteConnectionBatch(ids);
            result.put("success", true);
            result.put("message", "批量删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "批量删除失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 测试连接
     * 
     * @param connection 连接对象
     * @return 测试结果
     */
    @PostMapping("/test")
    @ResponseBody
    public Map<String, Object> testConnection(@RequestBody DbConnection connection) {
        return connectionService.testConnection(connection);
    }

    /**
     * 获取连接复制页面片段
     * 
     * @param id    连接ID
     * @param model 模型对象
     * @return 连接复制页面的视图片段
     */
    @GetMapping("/copy/{id}")
    public String copyConnection(@PathVariable Long id, Model model) {
        DbConnection copiedConnection = connectionService.copyConnection(id);
        // 复制时密码字段清空，需要重新输入
        copiedConnection.setPassword("");
        model.addAttribute("connection", copiedConnection);
        return "connection-form :: connection-form";
    }
}