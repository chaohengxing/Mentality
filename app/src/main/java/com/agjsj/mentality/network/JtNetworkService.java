package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.BaseEntity;

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

    @POST("jt/sendJtBean.action")
    Observable<BaseEntity> sendJtBeanService(@Query("jtBeanJson") String json);

    @POST("jt/sendDiscussForJt.action")
    Observable<BaseEntity> sendDiscussJtService(@Query("discussJtJson") String discussJtJson);
}
