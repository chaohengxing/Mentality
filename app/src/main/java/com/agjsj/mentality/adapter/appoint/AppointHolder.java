package com.agjsj.mentality.adapter.appoint;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.AppointInfo;
import com.agjsj.mentality.myview.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MyPC on 2016/11/6.
 */

public class AppointHolder extends RecyclerView.ViewHolder {

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

    public AppointHolder(View itemView) {
        super(itemView);
    }

//    public AppointHolder(View v) {
//        super(v);
//        ButterKnife.bind(this, itemView);
//    }


}
