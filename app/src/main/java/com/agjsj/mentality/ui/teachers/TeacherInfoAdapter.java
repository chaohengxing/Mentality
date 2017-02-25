package com.agjsj.mentality.ui.teachers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.teacher.TeacherInfo;
import com.agjsj.mentality.ui.jt.JtDiscussViewHolder;
import com.agjsj.mentality.ui.jt.SayViewHolder;

/**
 * Created by HengXing on 2016/12/4.
 */
public class TeacherInfoAdapter extends RecyclerView.Adapter {
    private final int TEACHER_INFO = 0;
    private final int TEACHER_DISCUSS = 1;


    private Context context;
    private TeacherInfo teacherInfo;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    public TeacherInfoAdapter(Context context, TeacherInfo teacherInfo) {
        this.context = context;
        this.teacherInfo = teacherInfo;
    }


    @Override
    public int getItemViewType(int position) {


        if (position == 0) {
            return TEACHER_INFO;
        } else {
            return TEACHER_DISCUSS;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TEACHER_INFO) {
            return new TeacherItemHolder(context, parent, onRecyclerViewListener);
        } else if (viewType == TEACHER_DISCUSS) {
            return new TeacherDiscussViewHolder(context, parent, onRecyclerViewListener);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TEACHER_INFO) {
            ((BaseViewHolder) holder).bindData(teacherInfo);
        } else if (getItemViewType(position) == TEACHER_DISCUSS) {
            ((BaseViewHolder) holder).bindData(teacherInfo.getDiscussTeachers().get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return teacherInfo.getDiscussTeachers() == null ? 1 : teacherInfo.getDiscussTeachers().size() + 1;
    }


    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
}
