package com.agjsj.mentality.utils;

/**
 * Created by HengXing on 2017/2/11.
 */

import com.agjsj.mentality.application.MyApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2016/11/28
 * 功能描述:
 */
public class HttpUtils {
    public static final String BASE_URL = "http://192.168.1.109:8080/xlyy/";
    private Retrofit retrofit;
    private static final int NO_NET_MAX = 60 * 60 * 24 * 7; //7天 无网超时时间
    private static final int NET_MAX = 30; //30秒  有网超时时间

    private HttpUtils() {
        Interceptor mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.networkIsAvailable(MyApplication.INSTANCE())) {
                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "private, only-if-cached, max-stale=" + NO_NET_MAX)
                            .build();
                } else {
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .removeHeader("Pragma")
                            .header("Cache-Control", "private, max-age=" + NET_MAX)//添加缓存请求头
                            .header("Content-Type","application/json;UTF-8")
                            .build();
                }

                return chain.proceed(request);
            }
        };

        OkHttpClient mClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(mInterceptor)
                .cache(new Cache(new File(MyApplication.INSTANCE().getCacheDir() + "http")
                        , 1024 * 1024 * 10))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient)
                .build();
    }

    //单例设计模式
//    private static class singRetrofit {
//        private static final HttpUtils instance = new HttpUtils();
//    }

    private static HttpUtils instance;


    public static HttpUtils getInstance() {
        if (instance == null) {
            instance = new HttpUtils();
        }
        return instance;
    }

    public static <T> T createService(Class<T> t) {
        //GetWeatherService getWeatherService = getInstance().retrofit.create(GetWeatherService.class);
        return getInstance().retrofit.create(t);
    }


}
