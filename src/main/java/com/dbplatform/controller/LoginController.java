/**
 * 登录控制器
 * 处理用户登录、登出等相关请求
 */
package com.dbplatform.controller;

import com.dbplatform.entity.User;
import com.dbplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     * @return 登录页面的视图名称
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 处理用户登录请求
     * @param username 用户名
     * @param password 密码
     * @param session 会话对象
     * @param model 模型对象
     * @return 登录成功则跳转到仪表盘，否则返回登录页面
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("currentUser", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }

    /**
     * 处理用户登出请求
     * @param session 会话对象
     * @return 跳转到登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String dashboard(HttpSession session, Model model) {
        // 模拟登录用户
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setRealName("管理员");
        user.setEmail("admin@example.com");
        // user.setRole("admin");
        session.setAttribute("currentUser", user);
        model.addAttribute("user", user);
        return "dashboard";
    }
}