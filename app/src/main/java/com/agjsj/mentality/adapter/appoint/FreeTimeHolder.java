package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.orhanobut.logger.Logger;

import butterknife.Bind;

/**
 * Created by YH on 2016/11/5.
 */

public class FreeTimeHolder extends BaseViewHolder<FreeTime> {
    @Bind(R.id.gv_time_appoint_tea)
    GridView mGridView;
    @Bind(R.id.tv_date_appoint_tea)
    TextView tv_date;

    public FreeTimeHolder(Context context, ViewGroup root, int layoutRes, OnRecyclerViewListener listener) {
        super(context, root, layoutRes, listener);
    }

    public FreeTimeHolder(Context context, ViewGroup root, int layoutRes) {
        super(context, root, layoutRes, null);
    }


    @Override
    public void bindData(FreeTime freeTime) {
//        Logger.i("freeTime.getDate()" + freeTime.getDate());
//        tv_date.setText(freeTime.getDate());
//        TimeAdapter mAdapter = new TimeAdapter(context, freeTime.getTimeStatus());
//        mGridView.setAdapter(mAdapter);
    }
}
