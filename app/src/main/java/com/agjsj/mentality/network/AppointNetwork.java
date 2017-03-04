package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.BaseEntity;
import com.agjsj.mentality.bean.appoint.Appoint;
import com.agjsj.mentality.bean.appoint.AppointInfo;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.bean.appoint.TeacherTimePlan;
import com.agjsj.mentality.bean.appoint.TimeStatus;
import com.agjsj.mentality.bean.appoint.TimeTemplate;
import com.agjsj.mentality.bean.teacher.TeacherInfo;
import com.agjsj.mentality.utils.HttpUtils;
import com.agjsj.mentality.utils.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MyPC on 2016/11/6.
 */

public class AppointNetwork {
    private AppointNetworkService appointNetworkService;

    private static AppointNetwork instance = new AppointNetwork();

    public static AppointNetwork getInstance() {
        return instance;
    }

    private AppointNetwork() {
        appointNetworkService = HttpUtils.createService(AppointNetworkService.class);
    }

    /**
     * 查询教师空闲时间状态接口回调函数
     */
    public static final int TEACHER_GETFREETIMES_YES = 1;//登陆成功返回码
    public static final int TEACHER_GETFREETIMES_NO = 0;//登陆失败返回码,有待详细补充

    public interface TeacherGetFreeTimesCallBack {
        public void response(int code, TeacherTimePlan teacherTimePlan);
    }

    /**
     * 教师获取可排挡的时间列表，以及那些已经排挡过
     */
    public void teacherGetFreeTimes(String teacherId, List<String> timeDates, final TeacherGetFreeTimesCallBack callBack) {
        appointNetworkService.teacherGetFreeTimesService(teacherId, new Gson().toJson(timeDates))
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(TEACHER_GETFREETIMES_NO, null);

                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {

                        try {
                            JSONObject jsonObject = new JSONObject(baseEntity.getData());
                            List<TimeTemplate> timeTemplates = new Gson().fromJson(jsonObject.getString("templates"), new TypeToken<ArrayList<TimeTemplate>>() {
                            }.getType());
                            List<FreeTime> freeTimes = new Gson().fromJson(jsonObject.getString("freeTimes"), new TypeToken<ArrayList<FreeTime>>() {
                            }.getType());

                            TeacherTimePlan teacherTimePlan = new TeacherTimePlan(timeTemplates, freeTimes);

                            callBack.response(TEACHER_GETFREETIMES_YES, teacherTimePlan);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    /**
     * 教师排挡 及 发布空闲时间
     */
    public static final int PLAN_FREETIME_YES = 1;//登陆成功返回码
    public static final int PLAN_FREETIME_NO = 0;//登陆失败返回码,有待详细补充

    public interface PlanFreeTimeCallBack {
        public void response(int code);
    }

    public void planFreeTime(FreeTime freeTime, final PlanFreeTimeCallBack callBack) {
        appointNetworkService.planFreeTime(new Gson().toJson(freeTime))
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(PLAN_FREETIME_NO);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (200 == baseEntity.getCode()) {
                            callBack.response(PLAN_FREETIME_YES);

                        } else {

                            callBack.response(PLAN_FREETIME_YES);
                        }
                    }
                });
    }


    //------------------ 获取从今天开始 五天内 可预约列表 包括teacherInfo、时间段信息、以及我是否预约-------
    public static final int GetFreeTimesWithMyAppoint_YES = 1;//登陆成功返回码
    public static final int GetFreeTimesWithMyAppoint_NO = 0;//登陆失败返回码,有待详细补充

    public interface GetFreeTimesWithMyAppointCallback {
        public void response(int code, List<FreeTime> freeTimes);
    }

    /**
     * 获取从今天开始 五天内 可预约列表 包括teacherInfo、时间段信息、以及我是否预约
     */
    public void getFreeTimesWithMyAppoint(String stuId, List<String> timeDates, final GetFreeTimesWithMyAppointCallback callback) {
        appointNetworkService.getFreeTimesWithMyAppointService(stuId, new Gson().toJson(timeDates))
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.response(GetFreeTimesWithMyAppoint_NO, null);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {

                        List<FreeTime> freeTimes = new Gson().fromJson(baseEntity.getData(), new TypeToken<ArrayList<FreeTime>>() {
                        }.getType());

                        try {
                            JSONArray jsonArray = new JSONArray(baseEntity.getData());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                List<Appoint> appoints = new Gson().fromJson(jsonArray.getJSONObject(i).getString("appoint"), new TypeToken<ArrayList<Appoint>>() {
                                }.getType());
                                freeTimes.get(i).setAppoint(appoints);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.response(GetFreeTimesWithMyAppoint_YES, freeTimes);
                    }
                });

    }

    //------------------ 学生用户发布预约-------
    public static final int SEND_APPOINT_YES = 1;//登陆成功返回码
    public static final int SEND_APPOINT_NO = 0;//登陆失败返回码,有待详细补充

    public interface SendAppointCallback {
        public void response(int code);
    }

    /**
     * 获取从今天开始 五天内 可预约列表 包括teacherInfo、时间段信息、以及我是否预约
     */
    public void sendAppoint(Appoint appoint, final SendAppointCallback callback) {
        appointNetworkService.sendAppointService(new Gson().toJson(appoint))
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.response(SEND_APPOINT_NO);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {

                        if (200 == baseEntity.getCode()) {
                            callback.response(SEND_APPOINT_YES);
                        } else {
                            callback.response(SEND_APPOINT_NO);
                        }

                    }
                });
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
