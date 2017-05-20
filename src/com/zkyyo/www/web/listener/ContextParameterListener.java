package com.zkyyo.www.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 该监听器用于在web应用上设置通用属性
 */
@WebListener()
public class ContextParameterListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    public ContextParameterListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        String loginCookieName = context.getInitParameter("LOGIN_COOKIE_NAME");
        int stayLoggedTime = Integer.valueOf(context.getInitParameter("STAY_LOGGED_TIME"));
        String topicDir = context.getInitParameter("TOPIC_DIR");
        String imageDir = context.getInitParameter("IMAGE_DIR");
        long cleanInterval = Long.valueOf(context.getInitParameter("CLEAN_INTERVAL"));
        int maxLastReplyDay = Integer.valueOf(context.getInitParameter("MAX_LAST_REPLY_DAY"));
        String cleanTime = context.getInitParameter("CLEAN_TIME");

        context.setAttribute("loginCookieName", loginCookieName);
        context.setAttribute("stayLoggedTime", stayLoggedTime);
        context.setAttribute("topicDir", topicDir);
        context.setAttribute("imageDir", imageDir);
        context.setAttribute("cleanInterval", cleanInterval);
        context.setAttribute("maxLastReplyDay", maxLastReplyDay);
        context.setAttribute("cleanTime", cleanTime);
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
