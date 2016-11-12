package com.agjsj.mentality.bean.jt;

import com.agjsj.mentality.bean.MyUser;

import java.util.List;

/**
 * Created by HengXing on 2016/11/7.
 */
public class JtDiscuss {


    private int id;
    private int jtId;
    private String commentInfo;
    private int commentUserId;
    private MyUser commentUserInfo;
    private long commmentTime;

    private List<JtDiscussReplay> replays;

    @Override
    public String toString() {
        return getClass().getName()+"{" +
                "id=" + id +
                ", jtId=" + jtId +
                ", commentInfo='" + commentInfo + '\'' +
                ", commentUserId=" + commentUserId +
                ", commentUserInfo=" + commentUserInfo +
                ", commmentTime=" + commmentTime +
                ", replays=" + replays +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJtId() {
        return jtId;
    }

    public void setJtId(int jtId) {
        this.jtId = jtId;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public MyUser getCommentUserInfo() {
        return commentUserInfo;
    }

    public void setCommentUserInfo(MyUser commentUserInfo) {
        this.commentUserInfo = commentUserInfo;
    }

    public long getCommmentTime() {
        return commmentTime;
    }

    public void setCommmentTime(long commmentTime) {
        this.commmentTime = commmentTime;
    }

    public List<JtDiscussReplay> getReplays() {
        return replays;
    }

    public void setReplays(List<JtDiscussReplay> replays) {
        this.replays = replays;
    }
}
