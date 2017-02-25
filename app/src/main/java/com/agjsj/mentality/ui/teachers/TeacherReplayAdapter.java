package com.agjsj.mentality.ui.teachers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.teacher.ReplayDiscussTeacher;

import java.util.List;

/**
 * Created by HengXing on 2016/12/4.
 */
public class TeacherReplayAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ReplayDiscussTeacher> replays;

    public TeacherReplayAdapter(Context context, List<ReplayDiscussTeacher> replays) {
        this.context = context;
        this.replays = replays;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeacherReplayViewHolder(context, parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((BaseViewHolder) holder).bindData(replays.get(position));
    }

    @Override
    public int getItemCount() {
        return replays.size();
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

}
