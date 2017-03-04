package com.agjsj.mentality.network;


import com.agjsj.mentality.bean.BaseEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Completable;
import rx.Observable;

/**
 * Created by HengXing on 2017/2/11.
 */
public interface UserNetworkService {

    @POST("user/login.action")
    Observable<BaseEntity> loginService(@Query("userName") String userName, @Query("password") String password);
    @Headers("Content-type:application/x-www-form-urlencoded;charset=UTF-8")
    @FormUrlEncoded
    @POST("user/updateStudentInfo.action")
    Observable<BaseEntity> updateStudentInfoService(@Field("id") String id,
                                                    @Field("stuIcon") String stuIcon,
                                                    @Field("stuNickName") String stuNickName,
                                                    @Field("sex") String sex,
                                                    @Field("userClass") String userClass,
                                                    @Field("userMajor") String userMajor
    );
    @Headers("Content-type:application/x-www-form-urlencoded;charset=UTF-8")
    @FormUrlEncoded
    @POST("user/updateTeacherInfo.action")
    Observable<BaseEntity>  updateTeacherInfoService(@Field("id") String id,
                                                     @Field("teacherIcon")String teacherIcon,
                                                     @Field("teacherNickName")String teacherNickName,
                                                     @Field("sex")String sex,
                                                     @Field("teacherIntro")String teacherIntro);

}
