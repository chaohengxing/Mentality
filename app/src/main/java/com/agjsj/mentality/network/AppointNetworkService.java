package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.BaseEntity;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HengXing on 2017/2/20.
 */
public interface AppointNetworkService {
    @POST("appoint/teacherGetFreeTimes.action")
    Observable<BaseEntity> teacherGetFreeTimesService(@Query("teacherId") String teacherId, @Query("timeDatesJson") String timeDatesJson);

    @POST("appoint/getFreeTimesWithMyAppoint.action")
    Observable<BaseEntity> getFreeTimesWithMyAppointService(@Query("stuId") String stuId, @Query("timeDatesJson") String timeDatesJson);

    @POST("appoint/sendAppoint.action")
    Observable<BaseEntity> sendAppointService(@Query("appointJson") String appointJson);

    @POST("appoint/planFreeTime.action")
    Observable<BaseEntity> planFreeTime(@Query("freeTimeJson") String appointJson);

}
