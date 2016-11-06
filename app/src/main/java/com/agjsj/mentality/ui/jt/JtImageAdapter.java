package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by HengXing on 2016/11/6.
 */
public class JtImageAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> images;

    public JtImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JtImageViewHolder(context, parent, null);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).bindData(images.get(position));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
