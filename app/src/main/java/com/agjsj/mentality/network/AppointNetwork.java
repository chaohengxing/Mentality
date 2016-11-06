package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.bean.appoint.TimeStatus;
import com.agjsj.mentality.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 2016/11/6.
 */

public class AppointNetwork {
    private static AppointNetwork instance = new AppointNetwork();

    private AppointNetwork() {
    }

    public static AppointNetwork getInstance() {
        return instance;
    }

    /**
     * 查询教师空闲时间状态接口回调函数
     */
    public interface QueryTeaFreeTimeCallable {
        public void postResult(List<FreeTime> lists);
    }

    public void queryTeacherFreeTime(String timeVersion, QueryTeaFreeTimeCallable callable) {
        List<FreeTime> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FreeTime time = new FreeTime();
            time.setDate(TimeUtil.getFormatToday(TimeUtil.FORMAT_DATE + "_" + i));
            List<TimeStatus> timeStatus = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                TimeStatus status = new TimeStatus();
                status.setId(j);
                status.setStatus(getRadomBool());
                timeStatus.add(status);
            }
            time.setTimeStatus(timeStatus);
            lists.add(time);
        }


        callable.postResult(lists);
    }

    /**
     * 随机获取true 或 fasle
     *
     * @return
     */
    private boolean getRadomBool() {
        if (((int) (Math.random() * 100)) % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

}
