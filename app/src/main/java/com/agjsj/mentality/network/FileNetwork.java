package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.BaseEntity;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HengXing on 2017/2/12.
 */
public class FileNetwork {


    private FileNetworkService fileNetworkService;

    private static FileNetwork instance = new FileNetwork();

    public static FileNetwork getInstance() {
        return instance;
    }

    private FileNetwork() {
        fileNetworkService = HttpUtils.createService(FileNetworkService.class);
    }

    //-------------------批量上传图片--------------------------
    public static final int SEND_IMAGE_ONE_FILE_YES = 1;//登陆成功返回码
    public static final int SEND_IMAGE_ONE_FILE_NO = 0;//登陆失败返回码,有待详细补充

    public interface SendOneImageFilesCallBack {
        public void response(int responseCode, String imgeUrl);
    }

    public void sendOneImageFiles(String imagePath, final SendOneImageFilesCallBack callBack) {

        File file = new File(imagePath);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);


        fileNetworkService.sendOneImageFilesService(part)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(SEND_IMAGE_ONE_FILE_NO, null);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {

                        if (200 == baseEntity.getCode()) {

                            callBack.response(SEND_IMAGE_FILES_YES, baseEntity.getData());
                        }

                    }
                });

    }


    //-------------------批量上传图片--------------------------
    public static final int SEND_IMAGE_FILES_YES = 1;//登陆成功返回码
    public static final int SEND_IMAGE_FILES_NO = 0;//登陆失败返回码,有待详细补充

    public interface SendImageFilesCallBack {
        public void response(int responseCode, List<String> imgeUrls);
    }

    public void sendImageFiles(List<String> images, final SendImageFilesCallBack callBack) {

        MultipartBody.Part[] part = new MultipartBody.Part[images.size()];
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i));
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            part[i] = MultipartBody.Part.createFormData("files", file.getName(), requestBody);

        }


        fileNetworkService.sendImageFilesService(part)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(SEND_IMAGE_FILES_NO, null);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        List<String> imageUrls = new Gson().fromJson(baseEntity.getData(), new TypeToken<ArrayList<String>>() {
                        }.getType());
                        callBack.response(SEND_IMAGE_FILES_YES, imageUrls);

                    }
                });

    }


}
