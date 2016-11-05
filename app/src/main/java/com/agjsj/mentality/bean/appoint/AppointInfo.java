package com.agjsj.mentality.bean.appoint;

import java.io.Serializable;

/**
 * Created by MyPC on 2016/11/5.
 */

public class AppointInfo implements Serializable {
    //当天日期
    private String date;

    private String teacherName;
    //教师头像
    private String teacherIcon;
    //时间段
    private String time;
    //特长
    private String marjor;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherIcon() {
        return teacherIcon;
    }

    public void setTeacherIcon(String teacherIcon) {
        this.teacherIcon = teacherIcon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarjor() {
        return marjor;
    }

    public void setMarjor(String marjor) {
        this.marjor = marjor;
    }
}
