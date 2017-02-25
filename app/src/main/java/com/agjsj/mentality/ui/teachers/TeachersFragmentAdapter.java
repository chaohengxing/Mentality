package com.agjsj.mentality.ui.teachers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.teacher.TeacherInfo;

import java.util.List;

/**
 * Created by HengXing on 2016/11/29.
 */
public class TeachersFragmentAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<TeacherInfo> teacherInfos;

    public TeachersFragmentAdapter(Context context, List<TeacherInfo> teacherInfos) {
        this.context = context;
        this.teacherInfos = teacherInfos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeacherItemHolder(context, parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((BaseViewHolder) holder).bindData(teacherInfos.get(position));

    }

    @Override
    public int getItemCount() {
        return teacherInfos.size();
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
}
