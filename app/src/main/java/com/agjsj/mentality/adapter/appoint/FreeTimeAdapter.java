package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.bean.appoint.TeacherTimePlan;
import com.agjsj.mentality.bean.appoint.TimeTemplate;
import com.agjsj.mentality.global.MyConfig;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YH on 2016/11/5.
 */

public class FreeTimeAdapter extends RecyclerView.Adapter<FreeTimeHolder> {

    private Context context;

    private List<String> timedates;

    private TeacherTimePlan teacherTimePlan;

    public List<String> getTimedates() {
        return timedates;
    }

    private FreeTimeHolder.OnTeacherPlanFreeTimeListener onTeacherPlanFreeTimeListener;

    public void setOnTeacherPlanFreeTimeListener(FreeTimeHolder.OnTeacherPlanFreeTimeListener onTeacherPlanFreeTimeListener) {
        this.onTeacherPlanFreeTimeListener = onTeacherPlanFreeTimeListener;
    }

    public FreeTimeAdapter(Context context) {
        this.context = context;
        timedates = MyConfig.getTimeDates();
        this.teacherTimePlan = new TeacherTimePlan(new ArrayList<TimeTemplate>(), new ArrayList<FreeTime>());

    }

    public TeacherTimePlan getTeacherTimePlan() {
        return teacherTimePlan;

    }

    public void setTeacherTimePlan(TeacherTimePlan teacherTimePlan) {
        this.teacherTimePlan = teacherTimePlan;
    }

    @Override
    public FreeTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreeTimeHolder(context, parent, R.layout.item_appoint_teacher, teacherTimePlan,onTeacherPlanFreeTimeListener);
    }

    @Override
    public void onBindViewHolder(FreeTimeHolder holder, int position) {
        holder.bindData(timedates.get(position));
    }




    @Override
    public int getItemCount() {
        return timedates.size();
    }
}
