package com.zkyyo.www.web;

import com.zkyyo.www.service.TopicService;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

/**
 * 该类用于定时清除指定的讨论区资源
 */
public class CleanerTask extends TimerTask {
    /**
     * 上下文对象
     */
    private ServletContext servletContext;

    /**
     * 构造对象
     * @param servletContext 传入的上下文对象
     */
    public CleanerTask(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void run() {
        String topicDir = (String) servletContext.getAttribute("topicDir"); //讨论区根目录
        int maxLastReplyDay = (int) servletContext.getAttribute("maxLastReplyDay"); //讨论区最新回复距离现在的时间

        TopicService topicService = (TopicService) servletContext.getAttribute("topicService");
        List<Integer> topicIds = topicService.queryTopicsBeforeDaysAboutLastReply(TopicService.ACCESS_PRIVATE, maxLastReplyDay);

        try {
            for (int topicId : topicIds) {
                //判断讨论区是否存在
                if (topicService.isExisted(topicId)) {
                    String bathPath = servletContext.getRealPath(topicDir); //存放讨论区文件的根目录
                    String topicPath = bathPath + "/topic#" + topicId; //该讨论区的绝对路径
                    //删除该讨论区在硬盘的一切数据
                    FileUtils.deleteDirectory(new File(topicPath));
                    //删除该讨论区在数据库的数据
                    topicService.deleteTopic(topicId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
