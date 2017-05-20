package com.zkyyo.www.web.listener; /**
 * Created by xu on 5/20/17.
 */

import com.zkyyo.www.web.CleanerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * 该监听器用于设置讨论区的清理任务
 */
@WebListener()
public class ContextCleanerListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {
    private Timer timer;

    public ContextCleanerListener() {

    }

    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer(true);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String cleanTime = (String) sce.getServletContext().getAttribute("cleanTime");
        String[] hourMinSec = cleanTime.split(":");
        int hourOfDay = Integer.valueOf(hourMinSec[0]);
        int minute = Integer.valueOf(hourMinSec[1]);
        int second = Integer.valueOf(hourMinSec[2]);
        calendar.set(year, month, day, hourOfDay, minute, second);
        Date date = calendar.getTime();

        long cleanInterval = (long) sce.getServletContext().getAttribute("cleanInterval"); //删除时间间隔
        timer.schedule(new CleanerTask(sce.getServletContext()), date, cleanInterval);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
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
