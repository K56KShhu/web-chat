package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.bean.vo.UserVo;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 该Servlet用于获取角色为admin的用户信息
 */
@WebServlet(
        name = "AdminManageInfoServlet",
        urlPatterns = {"/admin_manage_info.do"}
)
public class AdminManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<UserPo> userPos = userService.queryUsersByRole("admin");
        List<UserVo> userVos = new ArrayList<>();
        for (UserPo userPo : userPos) {
            //将userPo转化为userVo
            userVos.add(BeanUtil.userPoToVo(userPo));
        }
        request.setAttribute("users", userVos);
        request.getRequestDispatcher("admin_manage.jsp").forward(request, response);
    }
}
