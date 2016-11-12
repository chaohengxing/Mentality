package com.agjsj.mentality.bean.jt;

import com.agjsj.mentality.bean.MyUser;

/**
 * Created by HengXing on 2016/11/7.
 */
public class JtDiscussReplay {

    private int id;
    private int discussId;
    private int replayStuId;//评论人的id
    private int replayTeacherId;//评论人的Id
    private MyUser replayUserInfo;//评论人的个人信息

    private int passiveStuId;//被评论人的Id
    private int passiveTeacherId;//被评论人的Id
    private MyUser passiverUserInfo;//被评论人的个人信息
    private String replayInfo;
    private long replayTime;

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "id=" + id +
                ", discussId=" + discussId +
                ", replayStuId=" + replayStuId +
                ", replayTeacherId=" + replayTeacherId +
                ", replayUser=" + replayUserInfo +
                ", passiveStuId=" + passiveStuId +
                ", passiveTeacherId=" + passiveTeacherId +
                ", passiverUser=" + passiverUserInfo +
                ", replayInfo='" + replayInfo + '\'' +
                ", replayTime=" + replayTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscussId() {
        return discussId;
    }

    public void setDiscussId(int discussId) {
        this.discussId = discussId;
    }

    public int getReplayStuId() {
        return replayStuId;
    }

    public void setReplayStuId(int replayStuId) {
        this.replayStuId = replayStuId;
    }

    public int getReplayTeacherId() {
        return replayTeacherId;
    }

    public void setReplayTeacherId(int replayTeacherId) {
        this.replayTeacherId = replayTeacherId;
    }

    public MyUser getReplayUser() {
        return replayUserInfo;
    }

    public void setReplayUser(MyUser replayUser) {
        this.replayUserInfo = replayUser;
    }

    public int getPassiveStuId() {
        return passiveStuId;
    }

    public void setPassiveStuId(int passiveStuId) {
        this.passiveStuId = passiveStuId;
    }

    public int getPassiveTeacherId() {
        return passiveTeacherId;
    }

    public void setPassiveTeacherId(int passiveTeacherId) {
        this.passiveTeacherId = passiveTeacherId;
    }

    public MyUser getPassiverUser() {
        return passiverUserInfo;
    }

    public void setPassiverUser(MyUser passiverUser) {
        this.passiverUserInfo = passiverUser;
    }

    public String getReplayInfo() {
        return replayInfo;
    }

    public void setReplayInfo(String replayInfo) {
        this.replayInfo = replayInfo;
    }

    public long getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(long replayTime) {
        this.replayTime = replayTime;
    }
}
