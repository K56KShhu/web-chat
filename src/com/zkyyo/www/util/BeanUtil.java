package com.zkyyo.www.util;

import com.zkyyo.www.po.ReplyPo;
import com.zkyyo.www.po.UserPo;
import com.zkyyo.www.service.ReplyService;
import com.zkyyo.www.vo.ReplyVo;

import java.sql.Timestamp;

public class BeanUtil {

//    private int replyId;
//    private int topicId;
//    private int userId;
//    private String username; //vo
//    private String content;
//    private int contentType; // 0文本 1图片
//    private String contentType; // 0文本 1图片
//    private Timestamp created;

    public static ReplyVo ReplyPoToVo(ReplyPo replyPo, UserPo userPo) {
        int replyId = replyPo.getReplyId();
        int topicId = replyPo.getTopicId();
        int userId = replyPo.getUserId();
        String username = userPo.getUsername();
        String content = replyPo.getContent();
        int contentType = replyPo.getContentType();
        Timestamp created = replyPo.getCreated();
        String contentTypeStr;
        if (contentType == ReplyService.CONTENT_TEXT) {
            contentTypeStr = "聊天文本";
        } else if (contentType == ReplyService.CONTENT_IMAGE) {
            contentTypeStr = "聊天图片";
        } else {
            contentTypeStr = "unknown";
        }
        return new ReplyVo(replyId, topicId, userId, username, content, contentType, contentTypeStr, created);
    }
}
