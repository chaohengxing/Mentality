package com.agjsj.mentality.network;


import com.agjsj.mentality.bean.BaseEntity;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HengXing on 2017/2/11.
 */
public interface UserNetworkService {

    @POST("user/login.action")
    Observable<BaseEntity> loginService(@Query("userName") String userName, @Query("password") String password);

}
