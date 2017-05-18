package com.zkyyo.www.web.listener; /**
 * Created by xu on 5/18/17.
 */

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class ContextParameterListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    public ContextParameterListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String loginCookie = context.getInitParameter("LOGIN_COOKIE");
        String topicPath = context.getInitParameter("TOPIC_DIR");
        context.setAttribute("loginCookie", loginCookie);
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
