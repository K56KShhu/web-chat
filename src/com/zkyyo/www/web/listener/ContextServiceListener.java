package com.zkyyo.www.web.listener;

import com.zkyyo.www.dao.impl.*;
import com.zkyyo.www.service.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.sql.DataSource;

@WebListener()
public class ContextServiceListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    public ContextServiceListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/temp");
            ServletContext context = sce.getServletContext();
            context.setAttribute("userService", new UserService(new UserDaoJdbcImpl(dataSource)));
            context.setAttribute("topicService", new TopicService(new TopicDaoJdbcImpl(dataSource)));
            context.setAttribute("replyService", new ReplyService(new ReplyDaoJdbcImpl(dataSource)));
            context.setAttribute("fileService", new FileService(new FileDaoJdbcImpl(dataSource)));
            context.setAttribute("reportService", new ReportService(new ReportDaoJdbcImpl(dataSource)));
            context.setAttribute("groupService", new GroupService(new GroupDaoJdbcImpl(dataSource)));
            context.setAttribute("rememberService", new RememberService(new RememberDaoJdbcImpl(dataSource)));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    }

    public void attributeAdded(HttpSessionBindingEvent sbe) {
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
    }
}
