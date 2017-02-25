package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.BaseEntity;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by HengXing on 2017/2/12.
 */
public interface FileNetworkService {
    @Multipart
    @POST("file/sendfile.action")
    Observable<BaseEntity> sendOneImageFilesService(@Part() MultipartBody.Part part);

    @Multipart
    @POST("file/sendfiles.action")
    Observable<BaseEntity> sendImageFilesService(@Part() MultipartBody.Part[] files);

}
