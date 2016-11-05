package com.agjsj.mentality.bean.appoint;

import java.io.Serializable;

/**
 * Created by YH on 2016/11/5.
 */

public class TimeStatus implements Serializable {
    private int id;
    private boolean status;

    public TimeStatus() {
    }

    public TimeStatus(int id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TimeStatus{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
