package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.BaseEntity;
import com.agjsj.mentality.bean.teacher.DiscussTeacher;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HengXing on 2017/2/11.
 */
public interface TeacherNetworkService {
    @POST("user/getTeacherInfoList.action")
    Observable<BaseEntity> getTeacherInfosService();

    @POST("teacher/getTeacherInfoAndDiscussWithReplay.action")
    Observable<BaseEntity> getTeacherInfoWithJtDiscussAndReplayService(@Query("id")String id);

    @POST("teacher/sendDiscussForTeacher.action")
    Observable<BaseEntity> sendDiscussTeacherService(@Query("discussTeacherJson")String discussTeacherJson);
}
