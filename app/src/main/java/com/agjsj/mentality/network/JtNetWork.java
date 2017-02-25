package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.BaseEntity;
import com.agjsj.mentality.bean.jt.DiscussJt;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.bean.teacher.TeacherInfo;
import com.agjsj.mentality.bean.user.MyUser;
import com.agjsj.mentality.utils.HttpUtils;
import com.agjsj.mentality.utils.PicassoUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HengXing on 2016/11/6.
 */
public class JtNetWork {


    JtNetworkService jtNetworkService;

    private static JtNetWork instance;

    public static JtNetWork getInstance() {
        if (instance == null) {
            instance = new JtNetWork();
        }
        return instance;
    }

    private JtNetWork() {
        jtNetworkService = HttpUtils.createService(JtNetworkService.class);
    }

    //---------------------------------------从网络中加载Jt内容
    public static final int GET_JTS_YES = 1;//登陆成功返回码
    public static final int GET_JTS_NO = 0;//登陆失败返回码,有待详细补充

    public interface GetJtBeansCallBack {
        public void response(int responseCode, List<JtBean> jtBeens);
    }

    /**
     * 分页加载
     * 请求参数  pageIndex  pageSize
     * <p/>
     * 数据端返回接口
     * Json字符串需要包含的内容:
     * jt的基本信息
     * 发布者的UserInfo
     */
    public void getJtBeans(int pageIndex, int pageSize, final GetJtBeansCallBack callBack) {
        jtNetworkService.getJtBeansService(pageIndex, pageSize)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(GET_JTS_NO, null);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (200 == baseEntity.getCode()) {
                            List<JtBean> jtBeens = new Gson().fromJson(baseEntity.getData(), new TypeToken<ArrayList<JtBean>>() {
                            }.getType());
                            callBack.response(GET_JTS_YES, jtBeens);
                        } else {
                            callBack.response(GET_JTS_NO, null);
                        }

                    }
                });
    }


    public static final int GET_JTINFO_WITH_DISCUSS_YES = 1;//登陆成功返回码
    public static final int GET_JTINFO_WITH_DISCUSS_NO = 0;//登陆失败返回码,有待详细补充

    public interface GetJtBeanWithJtDiscussAndReplayCallBack {
        public void response(int responseCode, JtBean jtBean);
    }

    /**
     * 获取带有评论以及评论的回复  的  JtBean
     *
     * @param jtId
     * @return
     */
    public void getJtBeanWithJtDiscussAndReplay(String jtId, final GetJtBeanWithJtDiscussAndReplayCallBack callBack) {

        jtNetworkService.getJtBeanWithJtDiscussAndReplayService(jtId)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(GET_JTINFO_WITH_DISCUSS_NO, null);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        Logger.e("chx", baseEntity.toString());
                        if (200 == baseEntity.getCode()) {
                            JtBean jtBean = new Gson().fromJson(baseEntity.getData(), JtBean.class);
                            Logger.e("chx", jtBean.toString());
                            Logger.e("chx", "评论数：" + jtBean.getDiscussJts().size());
                            callBack.response(GET_JTINFO_WITH_DISCUSS_YES, jtBean);
                        } else {
                            callBack.response(GET_JTINFO_WITH_DISCUSS_NO, null);
                        }

                    }
                });

    }

    //---------------------发布鸡汤---------------
    public static final int SEND_JTBEAN_YES = 1;//登陆成功返回码
    public static final int SEND_JTBEAN_NO = 0;//登陆失败返回码,有待详细补充

    public interface SendJtBeanReplayCallBack {
        public void response(int responseCode);
    }

    public void sendJtBean(JtBean jtBean, final SendJtBeanReplayCallBack callBack) {

        jtNetworkService.sendJtBeanService(new Gson().toJson(jtBean))
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(SEND_JTBEAN_NO);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        callBack.response(SEND_JTBEAN_YES);
                    }
                });
    }

    //---------------------发布鸡汤评论---------------
    public static final int SEND_DISCUSS_JT_YES = 1;//登陆成功返回码
    public static final int SEND_DISCUSS_JT_NO = 0;//登陆失败返回码,有待详细补充

    public interface SendDiscussJtCallBack {
        public void response(int responseCode);
    }

    public void sendDiscussJt(DiscussJt discussJt, final SendDiscussJtCallBack callBack) {

        jtNetworkService.sendDiscussJtService(new Gson().toJson(discussJt))
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(SEND_DISCUSS_JT_NO);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {

                        callBack.response(SEND_DISCUSS_JT_YES);
                    }
                });


    }


}
