package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.user.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobListener;

/**
 * @author :smile
 * @project:QueryUserListener
 * @date :2016-02-01-16:23
 */
public abstract class QueryUserListener extends BmobListener<MyUser> {

    public abstract void done(MyUser s, BmobException e);

    @Override
    protected void postDone(MyUser o, BmobException e) {
        done(o, e);
    }
}
