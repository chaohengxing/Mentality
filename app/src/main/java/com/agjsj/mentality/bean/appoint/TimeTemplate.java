package com.agjsj.mentality.bean.appoint;

import java.io.Serializable;

/**
 * 时间模板，本地只能有一份，当服务器更新时要保持更新
 * Created by YH on 2016/11/5.
 */

public class TimeTemplate implements Serializable {
    private int timeId;
    private String timeVersion;
    private String timeName;
    private String timeStart;
    private String timeEnd;

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public String getTimeVersion() {
        return timeVersion;
    }

    public void setTimeVersion(String timeVersion) {
        this.timeVersion = timeVersion;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return "TimeTemplate{" +
                "timeId=" + timeId +
                ", timeVersion='" + timeVersion + '\'' +
                ", timeName='" + timeName + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                '}';
    }
}
