package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
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

@WebServlet(
        name = "UserManageInfoServlet",
        urlPatterns = {"/user_manage_info.do"}
)
public class UserManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String search = request.getParameter("search");
        String orderStr = request.getParameter("order");
        String statusStr = request.getParameter("statusSearch");
        boolean isReverse = "true".equals(request.getParameter("isReverse"));

        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        int order;
        if ("sex".equals(orderStr)) {
            order = UserService.ORDER_BY_SEX;
        } else if ("created".equals(orderStr)) {
            order = UserService.ORDER_BY_CREATED;
        } else {
            order = UserService.ORDER_BY_CREATED;
            isReverse = true;
        }
        int status;
        if ("all".equals(statusStr)) {
            status = UserService.STATUS_ALL;
        } else if ("normal".equals(statusStr)) {
            status = UserService.STATUS_NORMAL;
        } else if ("audit".equals(statusStr)) {
            status = UserService.STATUS_AUDIT;
        } else if ("notApproved".equals(statusStr)) {
            status = UserService.STATUS_NOT_APPROVED;
        } else if ("forbidden".equals(statusStr)) {
            status = UserService.STATUS_FORBIDDEN;
        } else {
            status = UserService.STATUS_ALL;
        }

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        PageBean<UserPo> pageBeanPo;
        if (search != null && search.trim().length() > 0) {
            pageBeanPo = userService.queryUsers(status, currentPage, search);
        } else {
            pageBeanPo = userService.queryUsers(status, currentPage, order, isReverse);
        }

        List<UserPo> userPos = pageBeanPo.getList();
        List<UserVo> userVos = new ArrayList<>();
        for (UserPo userPo : userPos) {
            userVos.add(BeanUtil.userPoToVo(userPo));
        }
        PageBean<UserVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, userVos);


        request.setAttribute("statusSearch", statusStr);
        request.setAttribute("search", search);
        request.setAttribute("order", order);
        request.setAttribute("isReverse", isReverse);
        request.setAttribute("pageBean", pageBeanVo);
        request.getRequestDispatcher("user_manage.jsp").forward(request, response);
    }
}
