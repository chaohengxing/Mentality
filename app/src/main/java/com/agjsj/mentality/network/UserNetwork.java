package com.agjsj.mentality.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.agjsj.mentality.application.MyApplication;
import com.agjsj.mentality.bean.MyUser;
import com.orhanobut.logger.Logger;


import java.util.Date;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobListener;


/**
 * Created by HengXing on 2016/10/29.
 */
public class UserNetwork {


    private static UserNetwork instance = new UserNetwork();

    public static UserNetwork getInstance() {
        return instance;
    }

    private UserNetwork() {
    }


    //-------------------------------------用户登陆----------------------------
    public static final int LOGIN_YES = 1;//登陆成功返回码
    public static final int LOGIN_NO = 0;//登陆失败返回码,有待详细补充

    public interface LoginCallBack {
        public void loginResponse(int responseCode);
    }

    public void login(String username, String password, LoginCallBack loginCallBack) {

        //请求网络
        if (true) {
            //登录成功，拿到服务端返回的用户信息，登录成功后将用户信息存入本地

            MyUser myUser = new MyUser();
            myUser.setId(99);
            myUser.setOpenId("123456789");
            myUser.setUserIcon("http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");
            myUser.setUserName("测试用户名");
            myUser.setNickName("测试昵称");
            myUser.setPassword("123456");
            myUser.setUserType(1);
            myUser.setLastAlterTime(new Date().getTime());
            myUser.setLastLoginTime(new Date().getTime());
            updateCurrentUser(myUser);
            //返回登录结果
            loginCallBack.loginResponse(LOGIN_YES);
        } else {
            //登录失败
            loginCallBack.loginResponse(LOGIN_NO);
        }


    }

    //-------------------------------------获取当前用户------------------------------
    public MyUser getCurrentUser() {
        SharedPreferences sharedPreferences = MyApplication.INSTANCE().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("_id", -1) == -1) {
            return null;
        } else if (new Date().getTime() - sharedPreferences.getLong("lastLoginTime", -1) >= 3600 * 24 * 30) {
            //登录超时，删除本地用户信息
            deleteCurrentUser();
            return null;
        } else {
            //获取用户，并返回
            MyUser myUser = new MyUser();
            myUser.setId(sharedPreferences.getInt("_id", -1));
            myUser.setOpenId(sharedPreferences.getString("openId", ""));
            myUser.setUserIcon(sharedPreferences.getString("userIcon", ""));
            myUser.setUserName(sharedPreferences.getString("userName", ""));
            myUser.setNickName(sharedPreferences.getString("nickName", ""));
            myUser.setPassword(sharedPreferences.getString("password", ""));
            myUser.setUserType(sharedPreferences.getInt("userType", -1));
            myUser.setLastAlterTime(sharedPreferences.getLong("lastAlterTime", -1));
            myUser.setLastLoginTime(sharedPreferences.getLong("lastLoginTime", -1));
            return myUser;
        }
    }

    //------------------------------------更新当前用户信息----------------------------
    public void updateCurrentUser(MyUser myUser) {
        SharedPreferences sharedPreferences = MyApplication.INSTANCE().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("_id", myUser.getId());
        editor.putString("openId", myUser.getOpenId());
        editor.putString("userIcon", myUser.getUserIcon());
        editor.putString("userName", myUser.getUserName());
        editor.putString("nickName", myUser.getNickName());
        editor.putString("password", myUser.getPassword());
        editor.putInt("userType", myUser.getUserType());
        editor.putLong("lastAlterTime", myUser.getLastAlterTime());
        editor.putLong("lastLoginTime", myUser.getLastLoginTime());

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
            myUser.setId(Integer.valueOf(objectId));
            myUser.setUserName("测试");
            myUser.setUserIcon("http://images.china.cn/attachement/png/site1000/20150930/ac9e178530e11775d4363d.png");
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
                        String name = s.getUserName();
                        String avatar = s.getUserIcon();
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
