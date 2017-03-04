package com.agjsj.mentality.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.agjsj.mentality.application.MyApplication;
import com.agjsj.mentality.bean.BaseEntity;
import com.agjsj.mentality.bean.user.MyUser;
import com.agjsj.mentality.bean.user.UserType;
import com.agjsj.mentality.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.exception.BmobException;
import retrofit2.http.Query;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by HengXing on 2016/10/29.
 */
public class UserNetwork {


    private UserNetworkService userNetworkService;

    private static UserNetwork instance = new UserNetwork();

    public static UserNetwork getInstance() {
        return instance;
    }

    private UserNetwork() {
        userNetworkService = HttpUtils.createService(UserNetworkService.class);
    }


    //-------------------------------------用户登陆----------------------------
    public static final int LOGIN_YES = 1;//登陆成功返回码
    public static final int LOGIN_NO = 0;//登陆失败返回码,有待详细补充

    public interface LoginCallBack {
        public void loginResponse(int responseCode);
    }

    public void login(String username, String password, final LoginCallBack loginCallBack) {

        userNetworkService.loginService(username, password)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginCallBack.loginResponse(LOGIN_NO);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (200 == baseEntity.getCode()) {
                            MyUser myUser = new Gson().fromJson(baseEntity.getData(), MyUser.class);
                            updateCurrentUser(myUser);
                            //返回登录结果
                            loginCallBack.loginResponse(LOGIN_YES);
                        } else {
                            //登录失败
                            loginCallBack.loginResponse(LOGIN_NO);
                        }

                    }
                });
    }


    //-------------------------------------更新学生用户信息----------------------------
    public static final int UPDATE_STUDENTINFO_YES = 1;//登陆成功返回码
    public static final int UPDATE_STUDENTINFO_NO = 0;//登陆失败返回码,有待详细补充

    public interface updateStudentInfoCallBack {
        public void response(int responseCode);
    }

    public void updateStudentInfo(String id,
                                  String stuIcon,
                                  String stuNickName,
                                  String sex,
                                  String userClass, String userMajor, final updateStudentInfoCallBack callBack) {
        userNetworkService.updateStudentInfoService(id, stuIcon, stuNickName, sex, userClass, userMajor)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(UPDATE_STUDENTINFO_NO);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (200 == baseEntity.getCode()) {
                            MyUser myUser = new Gson().fromJson(baseEntity.getData(), MyUser.class);
                            updateCurrentUser(myUser);
                            callBack.response(UPDATE_STUDENTINFO_YES);
                        } else {
                            callBack.response(UPDATE_STUDENTINFO_NO);
                        }

                    }
                });
    }

    //-------------------------------------更新学生用户信息----------------------------
    public static final int UPDATE_TEACHERINFO_YES = 1;//登陆成功返回码
    public static final int UPDATE_TEACHERINFO_NO = 0;//登陆失败返回码,有待详细补充

    public interface updateTeacherInfoCallBack {
        public void response(int responseCode);
    }

    public void updateTeacherInfo(String id,
                                  String teacherIcon,
                                  String teacherNickName,
                                  String sex,
                                  String teacherIntro, final updateTeacherInfoCallBack callBack) {
        userNetworkService.updateTeacherInfoService(id, teacherIcon, teacherNickName, sex, teacherIntro)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<BaseEntity>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.response(UPDATE_TEACHERINFO_NO);
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (200 == baseEntity.getCode()) {
                            MyUser myUser = new Gson().fromJson(baseEntity.getData(), MyUser.class);
                            updateCurrentUser(myUser);
                            callBack.response(UPDATE_TEACHERINFO_YES);
                        } else {
                            callBack.response(UPDATE_TEACHERINFO_NO);
                        }

                    }
                });
    }

    //-------------------------------------获取当前用户------------------------------
    public MyUser getCurrentUser() {
        SharedPreferences sharedPreferences = MyApplication.INSTANCE().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(sharedPreferences.getString("id", ""))) {
            return null;
        } else if (new Date().getTime() - sharedPreferences.getLong("lastLoginTime", -1) >= 3600 * 24 * 30) {
            //登录超时，删除本地用户信息
            deleteCurrentUser();
            return null;
        } else {
            //获取用户，并返回
            MyUser myUser = new MyUser();
            myUser.setId(sharedPreferences.getString("id", ""));
            myUser.setUserType(sharedPreferences.getInt("userType", -1));
            myUser.setSex(sharedPreferences.getString("sex", ""));
            myUser.setUserClass(sharedPreferences.getString("userClass", ""));
            myUser.setUserMajor(sharedPreferences.getString("userMajor", ""));
            myUser.setRegisterTime(sharedPreferences.getString("registerTime", ""));
            myUser.setStuIcon(sharedPreferences.getString("stuIcon", ""));
            myUser.setStuStatus(sharedPreferences.getInt("stuStatus", -1));
            myUser.setStuNickName(sharedPreferences.getString("stuNickName", ""));
            myUser.setTeacherNickName(sharedPreferences.getString("teacherNickName", ""));
            myUser.setTeacherIntro(sharedPreferences.getString("teacherIntro", ""));
            myUser.setTeacherIcon(sharedPreferences.getString("teacherIcon", ""));
            return myUser;
        }
    }

    //------------------------------------更新当前用户信息----------------------------
    public void updateCurrentUser(MyUser myUser) {
        SharedPreferences sharedPreferences = MyApplication.INSTANCE().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", myUser.getId());
        editor.putInt("userType", myUser.getUserType());
        editor.putString("sex", myUser.getSex());
        editor.putString("userClass", myUser.getUserClass());
        editor.putString("userMajor", myUser.getUserMajor());
        editor.putString("registerTime", myUser.getRegisterTime());
        editor.putString("stuIcon", myUser.getStuIcon());
        editor.putInt("stuStatus", myUser.getStuStatus());
        editor.putString("stuNickName", myUser.getNickName());
        editor.putString("teacherNickName", myUser.getTeacherNickName());
        editor.putString("teacherIntro", myUser.getTeacherIntro());
        editor.putString("teacherIcon", myUser.getTeacherIcon());
        editor.putLong("lastLoginTime", new Date().getTime());
        editor.commit();

    }

    //------------------------------------退出登录--------------------------------
    public void deleteCurrentUser() {
        SharedPreferences sharedPreferences = MyApplication.INSTANCE().getSharedPreferences("currentUser", Context.MODE_PRIVATE);

        sharedPreferences.edit().clear().commit();
    }

    //-------------------------------------查询用户信息---------------------------


    /**
     * 查询用户信息
     *
     * @param objectId
     * @param listener
     */
    public void queryUserInfo(String objectId, final QueryUserListener listener) {
        //从网络中查该人的信息
        if (true) {
            MyUser myUser = new MyUser();
//            myUser.setId(Integer.valueOf(objectId));
//            myUser.setUserName("测试");
//            myUser.setUserIcon("http://images.china.cn/attachement/png/site1000/20150930/ac9e178530e11775d4363d.png");
            listener.done(myUser, null);
        } else {
            listener.internalDone(new BmobException(000, "查无此人"));
        }
    }

    //-------------------------------------更新用户资料和会话资料

    /**
     * 更新用户资料和会话资料
     *
     * @param event
     * @param listener
     */
    public void updateUserInfo(MessageEvent event, final UpdateCacheListener listener) {
        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo info = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();
        String username = info.getName();
        String title = conversation.getConversationTitle();
        Logger.i("" + username + "," + title);
        //sdk内部，将新会话的会话标题用objectId表示，因此需要比对用户名和会话标题--单聊，后续会根据会话类型进行判断
        if (!username.equals(title)) {
            UserNetwork.getInstance().queryUserInfo(info.getUserId(), new QueryUserListener() {
                @Override
                public void done(MyUser s, BmobException e) {
                    if (e == null) {
                        String name = null;
                        String avatar = null;
                        if (UserType.StudentType == s.getUserType()) {
                            name = s.getStuNickName();
                            avatar = s.getStuIcon();
                        } else if (UserType.TeacherType == s.getUserType()) {
                            name = s.getTeacherNickName();
                            avatar = s.getTeacherIcon();
                        }
                        Logger.i("query success：" + name + "," + avatar);
                        conversation.setConversationIcon(avatar);
                        conversation.setConversationTitle(name);

                        info.setName(name);
                        info.setAvatar(avatar);
                        //更新用户资料
                        BmobIM.getInstance().updateUserInfo(info);
                        //更新会话资料-如果消息是暂态消息，则不更新会话资料
                        if (!msg.isTransient()) {
                            BmobIM.getInstance().updateConversation(conversation);
                        }
                    } else {
                        Logger.e(e);
                    }
                    listener.done(null);
                }
            });
        } else {
            listener.internalDone(null);
        }
    }
}
