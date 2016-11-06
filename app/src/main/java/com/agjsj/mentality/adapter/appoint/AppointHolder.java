package com.agjsj.mentality.adapter.appoint;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.AppointInfo;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.myview.MySwipeHorizontalMenuLayout;
import com.agjsj.mentality.ui.appoint.StudentAppointFragment;
import com.orhanobut.logger.Logger;
import com.tubb.smrv.SwipeHorizontalMenuLayout;
import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SwipeFractionListener;
import com.tubb.smrv.listener.SwipeSwitchListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MyPC on 2016/11/6.
 */

public class AppointHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.sml)
    MySwipeHorizontalMenuLayout swipeMenuLayout;
    @Bind(R.id.tv_name_appointInfo_item)
    TextView tv_name;
    @Bind(R.id.tv_time_appointInfo_item)
    TextView tv_time;
    @Bind(R.id.tv_major_appointInfo_item)
    TextView tv_major;
    @Bind(R.id.iv_icon_appointInfo_itme)
    CircleImageView iv_icon;
    @Bind(R.id.ll_appointInfo_item)
    LinearLayout ll_appointInfo;
    @Bind(R.id.btn_right_appoint_memu)
    Button btn_appoint;


    public AppointHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        initListen();
    }


    private void initListen() {

        swipeMenuLayout.setSwipeListener(new SwipeSwitchListener() {
            @Override
            public void beginMenuClosed(SwipeMenuLayout swipeMenuLayout) {
//                Logger.e("", "left menu closed");
            }

            @Override
            public void beginMenuOpened(SwipeMenuLayout swipeMenuLayout) {

            }

            @Override
            public void endMenuClosed(SwipeMenuLayout swipeMenuLayout) {
//                Logger.e("", "right menu closed");
            }

            @Override
            public void endMenuOpened(SwipeMenuLayout swipeMenuLayout) {
//                Logger.e("", "right menu opened");
            }
        });

        btn_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.i("Appoint Button Click");
            }
        });
    }


    public void doRecoverSwipeMenu() {
        if (swipeMenuLayout != null) {
            if (swipeMenuLayout.isCurrentSwipeExit()) {
                swipeMenuLayout.smoothCloseMenu(200);
            }
//            swipeMenuLayout.computeScroll();
//            swipeMenuLayout.init();
        }
    }


//        swipeMenuLayout.setSwipeFractionListener(new SwipeFractionListener() {
//            @Override
//            public void beginMenuSwipeFraction(SwipeMenuLayout swipeMenuLayout, float fraction) {
//                Logger.e("", "top menu swipe fraction:"+fraction);
//
//            }
//
//            @Override
//            public void endMenuSwipeFraction(SwipeMenuLayout swipeMenuLayout, float fraction) {
//                Logger.e("","bottom menu swipe fraction:"+fraction);
//            }
//        });






}
