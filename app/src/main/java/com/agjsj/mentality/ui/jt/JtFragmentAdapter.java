package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.bean.jt.JtBean;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by HengXing on 2016/11/6.
 */
public class JtFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int TYPE_SAY = 0;
    private final int TYPE_SHARE = 1;


    private Context context;
    private List<JtBean> jtBaens;

    public JtFragmentAdapter(Context context, List<JtBean> jtBaens) {
        this.context = context;
        this.jtBaens = jtBaens;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == TYPE_SAY) {
            //说说的viewholder
            return new SayViewHolder(context, parent, null);
        } else if (viewType == TYPE_SHARE) {
            //分享的viewholder

            return new ShareViewHolder(context, parent, R.layout.item_jt_share, null);
        } else {

            return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((BaseViewHolder) holder).bindData(jtBaens.get(position));
    }

    @Override
    public int getItemViewType(int position) {

        if (jtBaens.get(position).getJtType() == TYPE_SAY) {
            return TYPE_SAY;
        } else if (jtBaens.get(position).getJtType() == TYPE_SHARE) {
            return TYPE_SHARE;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return jtBaens.size();
    }
}
