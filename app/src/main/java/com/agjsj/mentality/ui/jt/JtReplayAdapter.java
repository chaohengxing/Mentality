package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.ReplayDiscussJt;

import java.util.List;

/**
 * Created by HengXing on 2016/11/8.
 */
public class JtReplayAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ReplayDiscussJt> replays;

    public JtReplayAdapter(Context context, List<ReplayDiscussJt> replays) {
        this.context = context;
        this.replays = replays;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JtReplayViewHolder(context, parent, onRecyclerViewListener);
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
