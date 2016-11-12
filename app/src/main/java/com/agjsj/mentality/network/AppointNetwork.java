package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.appoint.AppointInfo;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.bean.appoint.TimeStatus;
import com.agjsj.mentality.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
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

    /**
     * 用于教师查看自己的可发布空闲时间
     *
     * @param timeVersion
     * @param callable
     */
    public void queryTeacherFreeTime(String timeVersion, QueryTeaFreeTimeCallable callable) {
        List<FreeTime> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FreeTime time = new FreeTime();
            time.setDate(getDateTime(i));
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
     * 查询教师空闲时间状态接口回调函数
     */
    public interface QueryStundetCanAppointTimeCallable {
        public void postResult(List<AppointInfo> lists);
    }

    /**
     * 用于学生查询自己可预约的时间
     */
    public void queryStundetCanAppointTime(QueryStundetCanAppointTimeCallable callable) {
        ArrayList<AppointInfo> mAppointInfoList = new ArrayList<>();
        mAppointInfoList.add(new AppointInfo());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                AppointInfo a = new AppointInfo();
                a.setTeacherName("李老师" + j);
                a.setTime(j + "--" + (j + 2));
                a.setMarjor("特长：");
                a.setDate(getDateTime(i));
                mAppointInfoList.add(a);
            }
        }
        callable.postResult(mAppointInfoList);

    }

    private String getDateTime(int i) {
        Calendar calendar = Calendar.getInstance();
        String[] dates = TimeUtil.getFormatToday(TimeUtil.FORMAT_DATE).split("-");
        calendar.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]) + i);
        return TimeUtil.dateToString(calendar.getTime(), TimeUtil.FORMAT_DATE);
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
