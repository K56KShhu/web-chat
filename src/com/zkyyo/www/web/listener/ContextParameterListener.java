package com.zkyyo.www.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class ContextParameterListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    public ContextParameterListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String loginCookieName = context.getInitParameter("LOGIN_COOKIE_NAME");
        int stayLoggedTime = Integer.valueOf(context.getInitParameter("STAY_LOGGED_TIME"));
        String topicPath = context.getInitParameter("TOPIC_DIR");
        context.setAttribute("loginCookieName", loginCookieName);
        context.setAttribute("stayLoggedTime", stayLoggedTime);
        context.setAttribute("topicDir", topicPath);
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
