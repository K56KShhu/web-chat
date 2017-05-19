package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.ReportPo;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.ReportService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;
import com.zkyyo.www.bean.vo.ReportVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 该Servlet用于处理获取举报信息的请求
 */
@WebServlet(
        name = "ReportManageInfoServlet",
        urlPatterns = {"/report_manage_info.do"}
)
public class ReportManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page"); //请求页数
        String orderStr = request.getParameter("order"); //排序依据
        boolean isReverse = "true".equals(request.getParameter("isReverse")); //是否降序 true降序, false升序

        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        //判断排序依据
        int order;
        if ("contentType".equals(orderStr)) {
            order = ReportService.ORDER_BY_CONTENT_TYPE;
        } else if ("created".equals(orderStr)) {
            order = ReportService.ORDER_BY_CREATED;
        } else {
            order = ReportService.ORDER_BY_CREATED;
            isReverse = true;
        }
        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        //获取分页对象
        PageBean<ReportPo> pageBeanPo = reportService.queryReports(currentPage, order, isReverse);

        //将ReportPo转换为ReportVo
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<ReportPo> reportPos = pageBeanPo.getList();
        List<ReportVo> reportVos = new ArrayList<>();
        for (ReportPo reportPo : reportPos) {
            UserPo userPo = userService.findUser(reportPo.getUserId());
            reportVos.add(BeanUtil.reportPoToVo(reportPo, userPo));
        }
        //重新构建类型为ReportVo的分页对象
        PageBean<ReportVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, reportVos);

        request.setAttribute("pageBean", pageBeanVo);
        request.setAttribute("orderStr", orderStr);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher("report_manage.jsp").forward(request, response);
    }
}
