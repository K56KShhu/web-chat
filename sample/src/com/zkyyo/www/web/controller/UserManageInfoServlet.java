package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.bean.vo.UserVo;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;
import com.zkyyo.www.util.CheckUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 该Servlet用于处理查询用户的请求
 */
@WebServlet(
        name = "UserManageInfoServlet",
        urlPatterns = {"/user_manage_info.do"}
)
public class UserManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page"); //请求页数
        String search = request.getParameter("search"); //查询内容
        String orderStr = request.getParameter("order"); //排序依据
        String statusStr = request.getParameter("statusSearch"); //用户状态
        boolean isReverse = "true".equals(request.getParameter("isReverse")); //是否降序 true降序, false升序

        //处理分页
        int currentPage = 1;
        if (CheckUtil.isValidInteger(CheckUtil.NUMBER_POSITIVE, page, 10)) {
            currentPage = Integer.valueOf(page);
        }
        //判断排序依据
        int order;
        if ("sex".equals(orderStr)) {
            order = UserService.ORDER_BY_SEX;
        } else if ("created".equals(orderStr)) {
            order = UserService.ORDER_BY_CREATED;
        } else {
            order = UserService.ORDER_BY_CREATED;
            isReverse = true;
        }
        //判断用户状态
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
        //判断查询方式
        if (search != null && search.trim().length() > 0) {
            //通过用户名模糊查询, 无法进行排序
            pageBeanPo = userService.queryUsers(status, currentPage, search);
        } else {
            //通过排序进行查询
            pageBeanPo = userService.queryUsers(status, currentPage, order, isReverse);
        }

        //将UserPo转换为UserVo
        List<UserPo> userPos = pageBeanPo.getList();
        List<UserVo> userVos = new ArrayList<>();
        for (UserPo userPo : userPos) {
            userVos.add(BeanUtil.userPoToVo(userPo));
        }
        //重构分页对象
        PageBean<UserVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, userVos);

        request.setAttribute("statusSearch", statusStr);
        request.setAttribute("search", search);
        request.setAttribute("order", order);
        request.setAttribute("isReverse", isReverse);
        request.setAttribute("pageBean", pageBeanVo);
        request.getRequestDispatcher("user_manage.jsp").forward(request, response);
    }
}
