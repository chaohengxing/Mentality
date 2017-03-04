package com.agjsj.mentality.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.user.MyUser;
import com.agjsj.mentality.bean.user.UserType;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.appoint.AppointFragment;
import com.agjsj.mentality.ui.appoint.StudentAppointFragment;
import com.agjsj.mentality.ui.appoint.TeacherAppointFragment;
import com.agjsj.mentality.ui.base.BaseActivity;
import com.agjsj.mentality.ui.chat.event.RefreshEvent;
import com.agjsj.mentality.ui.chat.fragment.ChatFragment;
import com.agjsj.mentality.ui.jt.JtFragment;
import com.agjsj.mentality.ui.menu.MyAppointActivity;
import com.agjsj.mentality.ui.menu.MyJtActivity;
import com.agjsj.mentality.ui.menu.StudentInfoActivity;
import com.agjsj.mentality.ui.menu.TeacherDatumActivity;
import com.agjsj.mentality.ui.photo.ViewPagerActivity;
import com.agjsj.mentality.ui.teachers.TeacherInfoActivity;
import com.agjsj.mentality.ui.teachers.TeachersFragment;
import com.agjsj.mentality.ui.user.LoginActivity;
import com.agjsj.mentality.utils.IMMLeaks;
import com.agjsj.mentality.utils.PicassoUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    //侧滑菜单的一些控件
    @Bind(R.id.iv_user_icon)
    CircleImageView ivUserIcon;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_sex)
    TextView tvUserSex;
    @Bind(R.id.ll_my_send)
    LinearLayout llMySend;
    @Bind(R.id.ll_my_appoint)
    LinearLayout llMyAppoint;
    @Bind(R.id.ll_my_info)
    LinearLayout llMyInfo;
    @Bind(R.id.ll_logout)
    LinearLayout llLogout;
    @Bind(R.id.ll_menu)
    LinearLayout llMenu;


    private Button[] mTabs;
    private JtFragment jtFragment;
    private TeachersFragment teachersFragment;
    private ChatFragment chatFragment;
    private AppointFragment appointFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    private MyUser currentUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initIm();


    }


    /**
     * 初始化即时通信工具
     */
    private void initIm() {
        //connect server

        MyUser user = UserNetwork.getInstance().getCurrentUser();

        BmobIM.connect(user.getId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
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

        if (UserType.StudentType == UserNetwork.getInstance().getCurrentUser().getUserType()) {
            appointFragment = new StudentAppointFragment();
        } else if (UserType.TeacherType == UserNetwork.getInstance().getCurrentUser().getUserType()) {
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

    @Override
    protected void onResume() {
        super.onResume();
        currentUserInfo = UserNetwork.getInstance().getCurrentUser();
        updateMenu();
    }

    /**
     * 更新菜单里的用户信息
     */
    private void updateMenu() {

        if (TextUtils.isEmpty(currentUserInfo.getUserIcon())) {
            ivUserIcon.setImageResource(R.drawable.default_pic);
        } else {
            PicassoUtils.loadResizeImage(currentUserInfo.getUserIcon(), R.drawable.default_pic, R.drawable.default_pic, 100, 100, ivUserIcon);

        }
        tvUserName.setText(TextUtils.isEmpty(currentUserInfo.getNickName()) ? currentUserInfo.getAccount() : currentUserInfo.getNickName());
        tvUserSex.setText(TextUtils.isEmpty(currentUserInfo.getSex()) ? "" : currentUserInfo.getSex());
    }

    @OnClick({R.id.iv_user_icon, R.id.ll_my_send, R.id.ll_my_appoint, R.id.ll_my_info, R.id.ll_logout, R.id.ll_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:

                Bundle bundle = new Bundle();
                ArrayList<String> images = new ArrayList<>();
                images.add(UserNetwork.getInstance().getCurrentUser().getUserIcon());
                bundle.putSerializable("imageUrls", images);
                bundle.putInt("position", 0);

                startActivity(ViewPagerActivity.class, bundle, false);

                break;
            case R.id.ll_my_send:
                startActivity(MyJtActivity.class, null, false);

                break;
            case R.id.ll_my_appoint:
                if (UserType.StudentType == UserNetwork.getInstance().getCurrentUser().getUserType()) {
                    //进入学生用户的资料
                    startActivity(MyAppointActivity.class, null, false);
                } else if (UserType.TeacherType == UserNetwork.getInstance().getCurrentUser().getUserType()) {
                    toast("该功能只为学生用户开放!");
                }
                //

                break;
            case R.id.ll_my_info:

                if (UserType.StudentType == UserNetwork.getInstance().getCurrentUser().getUserType()) {
                    //进入学生用户的资料
                    startActivity(StudentInfoActivity.class, null, false);
                } else if (UserType.TeacherType == UserNetwork.getInstance().getCurrentUser().getUserType()) {
                    //进入教室用户的资料
                    startActivity(TeacherDatumActivity.class, null, false);

                }


                break;
            case R.id.ll_logout:
                UserNetwork.getInstance().deleteCurrentUser();
                startActivity(LoginActivity.class, null, true);
                break;
            case R.id.ll_menu:
                //不作处理，防止点击事件传到下面的页面
                break;
        }
    }
}
