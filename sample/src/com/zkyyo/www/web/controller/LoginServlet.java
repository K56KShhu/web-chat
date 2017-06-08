package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.RememberService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.CookieUtil;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 该Servlet用于处理用户登录的请求
 */
@WebServlet(
        name = "Login",
        urlPatterns = {"/login.do"}
)
public class LoginServlet extends HttpServlet {
    /**
     * 自动登录的Cookie Name
     */
    private String LOGIN_COOKIE_NAME;
    /**
     * 自动登录持续时间
     */
    private int STAY_LOGGED_TIME;

    public void init() throws ServletException {
        LOGIN_COOKIE_NAME = (String) getServletContext().getAttribute("loginCookieName");
        STAY_LOGGED_TIME = (int) getServletContext().getAttribute("stayLoggedTime");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); //输入的用户名
        String password = request.getParameter("password"); //输入的密码
        boolean remember = "true".equals(request.getParameter("remember")); //是否自动登录 true自动, false不自动

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        RememberService rememberService = (RememberService) getServletContext().getAttribute("rememberService");
        List<String> errors = new ArrayList<>();
        UserPo user = new UserPo(username, password);

        //判断用户输入用户名, 密码是否正确
        if (userService.checkLogin(user)) {
            Access access = userService.getAccess(username);
            //判断账号状态
            if (access.getStatus() == UserService.STATUS_NORMAL) {
                //状态正常
                //判断是否自动登录
                if (remember) {
                    String uuid = UUID.randomUUID().toString();
                    //向数据库中保存Cookie Value和用户名
                    rememberService.save(uuid, username);
                    //向响应中添加Cookie
                    CookieUtil.addCookie(response, LOGIN_COOKIE_NAME, uuid, STAY_LOGGED_TIME);
                } else {
                    //删除数据库中储存的Cookie Value和用户名
                    rememberService.delete(username);
                    //设置客户机自动登录相关Cookie存活期为内存时间
                    CookieUtil.removeCookie(response, LOGIN_COOKIE_NAME);
                }
                //保存权限
                request.getSession().setAttribute("access", access);
                response.sendRedirect("index.jsp");
                return;
            } else if (access.getStatus() == UserService.STATUS_AUDIT) {
                errors.add("该账号正在审核中");
            } else if (access.getStatus() == UserService.STATUS_NOT_APPROVED) {
                errors.add("该账号审核不通过");
            } else if (access.getStatus() == UserService.STATUS_FORBIDDEN) {
                errors.add("该账号已被封印");
            }
        } else {
            errors.add("用户名或密码输入有误");
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
