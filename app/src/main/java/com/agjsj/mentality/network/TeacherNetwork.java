package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.BaseEntity;
import com.agjsj.mentality.bean.teacher.DiscussTeacher;
import com.agjsj.mentality.bean.teacher.TeacherInfo;
import com.agjsj.mentality.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HengXing on 2016/11/29.
 */
public class TeacherNetwork {

    private TeacherNetworkService teacherNetworkService;

    private static TeacherNetwork instance = new TeacherNetwork();

    public static TeacherNetwork getInstance() {
        return instance;
    }

    private TeacherNetwork() {
        teacherNetworkService = HttpUtils.createService(TeacherNetworkService.class);

    }

    //---------------------------获取TeacherInfo列表-------------------------
    public static final int GET_TEACHERINFOS_YES = 1;//登陆成功返回码
    public static final int GET_TEACHERINFOS_NO = 0;//登陆失败返回码,有待详细补充

    public interface GetTeacherInfosCallBack {
        public void response(int responseCode, List<TeacherInfo> teacherInfos);
    }

    public void getTeacherInfos(final GetTeacherInfosCallBack callBack) {
        teacherNetworkService.getTeacherInfosService()
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(GET_TEACHERINFOS_NO, null);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        List<TeacherInfo> teacherInfos = new Gson().fromJson(baseEntity.getData(), new TypeToken<ArrayList<TeacherInfo>>() {
                        }.getType());
                        callBack.response(GET_TEACHERINFOS_YES, teacherInfos);

                    }
                });
    }

    public static final int GET_TEACHERINFO_WITH_DISCUSS_YES = 1;//登陆成功返回码
    public static final int GET_TEACHERINFO_WITH_DISCUSS_NO = 0;//登陆失败返回码,有待详细补充

    public interface GetTeacherInfoWithJtDiscussAndReplayCallBack {
        public void response(int responseCode, TeacherInfo teacherInfo);
    }

    /**
     * 根据心理咨询师的Id来获取其详情  包括评论和评论的回复
     *
     * @param id
     * @return
     */
    public void getTeacherInfoWithJtDiscussAndReplay(String id, final GetTeacherInfoWithJtDiscussAndReplayCallBack callBack) {

        teacherNetworkService.getTeacherInfoWithJtDiscussAndReplayService(id)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(GET_TEACHERINFO_WITH_DISCUSS_NO, null);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        callBack.response(GET_TEACHERINFO_WITH_DISCUSS_YES,
                                new Gson().fromJson(baseEntity.getData(), TeacherInfo.class));
                    }
                });
    }

    public static final int SEND_DISCUSS_TEACHER_YES = 1;//登陆成功返回码
    public static final int SEND_DISCUSS_TEACHER_NO = 0;//登陆失败返回码,有待详细补充

    public interface SendDiscussTeacherCallBack {
        public void response(int responseCode);
    }

    /**
     * 发送评论
     *
     * @return
     */
    public void sendDiscussTeacher(DiscussTeacher discussTeacher, final SendDiscussTeacherCallBack callBack) {
        teacherNetworkService.sendDiscussTeacherService(new Gson().toJson(discussTeacher))
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(SEND_DISCUSS_TEACHER_NO);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {

                        if (200 == baseEntity.getCode()) {
                            callBack.response(SEND_DISCUSS_TEACHER_YES);
                        }
                    }
                });


    }
}
