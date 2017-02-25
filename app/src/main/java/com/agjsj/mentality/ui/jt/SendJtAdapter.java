package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;

import java.util.List;

/**
 * Created by HengXing on 2016/11/21.
 */
public class SendJtAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> images;

    public SendJtAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SendJtViewHolder(context, parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == images.size()) {
            //最后一张，显示图标
            ((BaseViewHolder) holder).bindData("");
        } else {
            //不是最后一张
            ((BaseViewHolder) holder).bindData(images.get(position));

        }


    }

    @Override
    public int getItemCount() {
        return images.size() + 1;
    }


    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

}
