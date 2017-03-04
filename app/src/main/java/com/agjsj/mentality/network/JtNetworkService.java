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
 * Created by HengXing on 2017/2/12.
 */
public interface JtNetworkService {


    @POST("jt/getJtList.action")
    Observable<BaseEntity> getJtBeansService(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @POST("jt/getJtInfoAndDiscussWithReplay.action")
    Observable<BaseEntity> getJtBeanWithJtDiscussAndReplayService(@Query("id") String jtId);

    @Headers("Content-type:application/x-www-form-urlencoded;charset=UTF-8")
    @FormUrlEncoded
    @POST("jt/sendJtBean.action")
    Observable<BaseEntity> sendJtBeanService(@Field("jtBeanJson") String json);

    @Headers("Content-type:application/x-www-form-urlencoded;charset=UTF-8")
    @FormUrlEncoded
    @POST("jt/sendDiscussForJt.action")
    Observable<BaseEntity> sendDiscussJtService(@Field("discussJtJson") String discussJtJson);

    @POST("jt/getMyJtList.action")
    Observable<BaseEntity> getMyJtBeansService(@Query("userId")String userid);
}
