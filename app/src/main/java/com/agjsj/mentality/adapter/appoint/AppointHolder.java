package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.AppointInfo;
import com.agjsj.mentality.dialog.ShowMegDialog;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.myview.MySwipeHorizontalMenuLayout;
import com.agjsj.mentality.ui.appoint.StudentAppointFragment;
import com.orhanobut.logger.Logger;
import com.tubb.smrv.SwipeHorizontalMenuLayout;
import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SwipeFractionListener;
import com.tubb.smrv.listener.SwipeSwitchListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MyPC on 2016/11/6.
 */

public class AppointHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.rl_item_appoint_stu)
    RelativeLayout rl_item;
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

    private Context context;

    public AppointHolder(View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }





    public void doRecoverSwipeMenu() {
        if (swipeMenuLayout != null) {
            if (swipeMenuLayout.isCurrentSwipeExit() && swipeMenuLayout.isMenuOpen()) {
//                Logger.i("smoothCloseMenu");
                swipeMenuLayout.smoothCloseEndMenu();
            }
        }
    }

}
