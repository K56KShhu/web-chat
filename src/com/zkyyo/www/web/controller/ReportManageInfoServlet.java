package com.zkyyo.www.web.controller;

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

@WebServlet(
        name = "ReportManageInfoServlet",
        urlPatterns = {"/report_manage_info.do"}
)
public class ReportManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<ReportPo> reportPos = reportService.findReports();
        List<ReportVo> reportVos = new ArrayList<>();
        for (ReportPo reportPo : reportPos) {
            UserPo userPo = userService.getUser(reportPo.getUserId());
            reportVos.add(BeanUtil.reportPoToVo(reportPo, userPo));
        }
        request.setAttribute("reports", reportVos);
        request.getRequestDispatcher("report_manage.jsp").forward(request, response);
    }
}
