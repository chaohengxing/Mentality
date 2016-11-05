package com.agjsj.mentality.bean.appoint;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by MyPC on 2016/11/5.
 */

public class FreeTime implements Serializable {

    private String date;
    //时间段状态
    private List<TimeStatus> timeStatus;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TimeStatus> getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(List<TimeStatus> timeStatus) {
        this.timeStatus = timeStatus;
    }

    @Override
    public String toString() {
        return "FreeTime{" +
                "date='" + date + '\'' +
                ", timeStatus=" + timeStatus +
                '}';
    }
}
