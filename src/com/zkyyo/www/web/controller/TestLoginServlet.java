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
import java.util.*;

@WebServlet(
        name = "TestLoginServlet",
        urlPatterns = {"/test_login.do"}
)
public class TestLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean remember = "true".equals(request.getParameter("remember"));

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        RememberService rememberService = (RememberService) getServletContext().getAttribute("rememberService");
        UserPo user = new UserPo(username, password);

        if (userService.checkLogin(user)) {
            Access access = userService.getAccess(username);
            request.getSession().setAttribute("access", access);
            if (remember) {
                String uuid = UUID.randomUUID().toString();
                rememberService.save(uuid, username);
                CookieUtil.addCookie(response, "user", uuid, 30 * 60);
            } else {
                rememberService.delete(username);
                CookieUtil.removeCookie(response, "user");
            }
        } else {
            response.sendRedirect("test_login.jsp");
        }
        */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        /*
        //储存<店铺ID, 该店铺的商品对象列表>
        Map<Integer, List<Product>> order_prod = new HashMap<>();
        String[] arrayId = request.getParameterValues("checkou");
        //获得所有不同的商店
        Set<Integer> stores = new HashSet<>();
        //用for获得不同的商店ID
        for (String proid_id : arrayId) {
            //pass
            int id = Integer.parseInt(proid_id)
            int store_id = productService.findStoreId(id);
            stores.add(store_id);
        }
        //找到每一个商店ID的所有商品
        for (int store : stores) {
            List<Product> list = new ArrayList<>();
            for (String proid_id : arrayId) {
                //pass
                int id = Integer.parseInt(proid_id)
                int store_id = productService.findStoreId(id);
                Product product = new Product();
                if (store_id == store) {
                    list.add(product);
                }
            }
            //放进去
            order_prod.put(store, list);
        }
        */
    }
}
