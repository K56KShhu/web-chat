package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.UserService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 该Servlet用于处理更新用户状态的请求
 */
@WebServlet(
        name = "UserManageServlet",
        urlPatterns = {"/user_manage.do"}
)
public class UserManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId"); //用户ID
        String status = request.getParameter("status"); //请求更新的用户状态
        //保存此时的搜索状态, 用于重定向
        String statusSearch = request.getParameter("statusSearch"); //查询用户状态
        String search = request.getParameter("search"); //查询内容
        String order = request.getParameter("order"); //排序依据
        String page = request.getParameter("page"); //请求页数
        String isReverse = request.getParameter("isReverse"); //是否倒序

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //判断请求更新的状态是否合法
        if (userService.isValidStatus(status)) {
            //判断用户是否存在
            if (userService.isValidUserId(userId) && userService.isUserExisted(Integer.valueOf(userId))) {
                int uStatus = Integer.valueOf(status);
                int id = Integer.valueOf(userId);

                //代码层防止管理员对自己的账号进行操作
                Access access = (Access) request.getSession().getAttribute("access");
                if (access.getUserId() == id) {
                    response.sendRedirect("admin_index.jsp");
                    return;
                }

                //search = new String(search.getBytes("UTF-8"), "ISO-8859-1");
                search = URLEncoder.encode(search, "UTF-8");
                String url = "user_manage_info.do?search=" + search + "&order=" + order + "&page=" + page
                        + "&isReverse=" + isReverse + "&statusSearch=" + statusSearch;
                //判断请求更新的状态类型
                if (uStatus == UserService.STATUS_NORMAL) {
                    userService.updateStatus(id, UserService.STATUS_NORMAL);
                    response.sendRedirect(url);
                    return;
                } else if (uStatus == UserService.STATUS_NOT_APPROVED) {
                    userService.updateStatus(id, UserService.STATUS_NOT_APPROVED);
                    response.sendRedirect(url);
                    return;
                } else if (uStatus == UserService.STATUS_FORBIDDEN) {
                    userService.updateStatus(id, UserService.STATUS_FORBIDDEN);
                    response.sendRedirect(url);
                    return;
                } else {
                    response.sendRedirect("admin_index.jsp");
                    return;
                }
            }
        }
        response.sendRedirect("admin_index.jsp");
    }
}
