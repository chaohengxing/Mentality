package com.agjsj.mentality.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.user.LoginActivity;


import java.lang.ref.WeakReference;

/**
 * Description:欢迎页面
 * author: chaohx
 * Date: 2016/10/12 16:04
 */
public class SplashActivity extends AppCompatActivity {

    private MyHandler handler = new MyHandler(this);

    //防止handler造成内存泄露
    private static class MyHandler extends Handler {
        WeakReference<SplashActivity> mActivity;

        MyHandler(SplashActivity mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //判断是否登陆了
            if (UserNetwork.getInstance().getCurrentUser() == null) {
                mActivity.get().toLoginActivity();
            } else {
                mActivity.get().toMainActivity();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //因此状态栏,全屏显示
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //不加载布局,通过主题直接显示欢迎logo
//      setContentView(R.layout.activity_splash);
        //先展示logo3秒
        handler.sendEmptyMessageDelayed(0, 2000);

    }


    /**
     * 开启主页面
     */
    private void toMainActivity() {

        startActivity(new Intent(this, MainActivity.class));

        finish();
    }

    /**
     * 开启登陆页面
     */
    private void toLoginActivity() {

        startActivity(new Intent(this, LoginActivity.class));

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
