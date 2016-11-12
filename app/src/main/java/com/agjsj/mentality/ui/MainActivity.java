package com.agjsj.mentality.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.MyUser;
import com.agjsj.mentality.bean.UserType;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.appoint.AppointFragment;
import com.agjsj.mentality.ui.appoint.StudentAppointFragment;
import com.agjsj.mentality.ui.appoint.TeacherAppointFragment;
import com.agjsj.mentality.ui.base.BaseActivity;
import com.agjsj.mentality.ui.chat.event.RefreshEvent;
import com.agjsj.mentality.ui.chat.fragment.ChatFragment;
import com.agjsj.mentality.ui.jt.JtFragment;
import com.agjsj.mentality.ui.teachers.TeachersFragment;
import com.agjsj.mentality.utils.IMMLeaks;
import com.orhanobut.logger.Logger;


import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends BaseActivity {


    @Bind(R.id.btn_jt)
    Button btnJt;
    @Bind(R.id.btn_teachers)
    Button btnTeachers;
    @Bind(R.id.btn_chat)
    Button btnChat;
    @Bind(R.id.iv_chat_tips)
    ImageView ivChatTips;
    @Bind(R.id.btn_appoint)
    Button btnAppoint;
    @Bind(R.id.main_bottom)
    LinearLayout mainBottom;
    @Bind(R.id.main_content)
    RelativeLayout mainContent;
    @Bind(R.id.main_drawerlayout)
    DrawerLayout mainDrawerlayout;


    private Button[] mTabs;
    private JtFragment jtFragment;
    private TeachersFragment teachersFragment;
    private ChatFragment chatFragment;
    private AppointFragment appointFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initIm();


    }

    /**
     * 初始化即时通信工具
     */
    private void initIm() {
        //connect server

        MyUser user = UserNetwork.getInstance().getCurrentUser();

        BmobIM.connect(String.valueOf(user.getId()), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Logger.i("connect success");
                    //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
                    EventBus.getDefault().post(new RefreshEvent());
                } else {
                    Logger.e(e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
        //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                toast("" + status.getMsg());
            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }


    @Override
    protected void initView() {
        super.initView();
        mTabs = new Button[4];
        mTabs[0] = btnJt;
        mTabs[1] = btnTeachers;
        mTabs[2] = btnChat;
        mTabs[3] = btnAppoint;
        mTabs[0].setSelected(true);
        initTab();
    }


    private void initTab() {
        jtFragment = new JtFragment();
        teachersFragment = new TeachersFragment();
        chatFragment = new ChatFragment();

        int userType = 2;
        if (userType == UserType.StudentType) {
            appointFragment = new StudentAppointFragment();
        } else if (userType == UserType.TeacherType) {
            appointFragment = new TeacherAppointFragment();
        }

        fragments = new Fragment[]{jtFragment, teachersFragment, chatFragment, appointFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content, jtFragment).
                add(R.id.main_content, teachersFragment)
                .add(R.id.main_content, chatFragment)
                .add(R.id.main_content, appointFragment)
                .hide(teachersFragment).hide(chatFragment).hide(appointFragment)
                .show(jtFragment).commit();
    }

    public void onTabSelect(View view) {
        switch (view.getId()) {
            case R.id.btn_jt:
                index = 0;
                break;
            case R.id.btn_teachers:
                index = 1;
                break;
            case R.id.btn_chat:
                index = 2;
                break;
            case R.id.btn_appoint:
                index = 3;
                break;
        }
        onTabIndex(index);
    }

    private void onTabIndex(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.main_content, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    /**
     * 打开或者关闭菜单
     */
    public void switchMenu() {
        if (mainDrawerlayout.isDrawerOpen(Gravity.LEFT)) {
            mainDrawerlayout.closeDrawer(Gravity.LEFT);
        } else {
            mainDrawerlayout.openDrawer(Gravity.LEFT);
        }

    }


}
