package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.bean.appoint.TeacherTimePlan;
import com.agjsj.mentality.bean.appoint.TimeTemplate;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;

/**
 * Created by YH on 2016/11/5.
 */

public class FreeTimeHolder extends BaseViewHolder<String> {
    @Bind(R.id.gv_time_appoint_tea)
    GridView mGridView;
    @Bind(R.id.tv_date_appoint_tea)
    TextView tv_date;

    private TeacherTimePlan teacherTimePlan;

    private OnTeacherPlanFreeTimeListener onTeacherPlanFreeTimeListener;

    public interface OnTeacherPlanFreeTimeListener {
        public void onItemClick(int datePosition, int timeTemplatePosition);
    }

    public void setTimeTemplates(TeacherTimePlan teacherTimePlan) {
        this.teacherTimePlan = teacherTimePlan;
    }

    public FreeTimeHolder(Context context, ViewGroup root, int layoutRes, TeacherTimePlan teacherTimePlan, OnTeacherPlanFreeTimeListener listener) {
        super(context, root, layoutRes, null);
        this.teacherTimePlan = teacherTimePlan;
        this.onTeacherPlanFreeTimeListener = listener;
    }

    public FreeTimeHolder(Context context, ViewGroup root, int layoutRes, TeacherTimePlan teacherTimePlan) {
        super(context, root, layoutRes, null);
        this.teacherTimePlan = teacherTimePlan;
    }


    @Override
    public void bindData(String string) {

        tv_date.setText(string);
        TimeAdapter mAdapter = new TimeAdapter(context, string, teacherTimePlan.getTemplates(), teacherTimePlan.getFreeTimes());
        mGridView.setAdapter(mAdapter);
        mGridView.deferNotifyDataSetChanged();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onTeacherPlanFreeTimeListener.onItemClick(getAdapterPosition(), position);
            }
        });
    }
}
