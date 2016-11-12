package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.JtBean;

/**
 * Created by HengXing on 2016/11/8.
 */
public class SayInfoAdapter extends RecyclerView.Adapter {

    private final int JT_INFO = 0;
    private final int JT_DISCUSS = 1;


    private Context context;
    private JtBean jtBean;

    public JtBean getJtBean() {
        return jtBean;
    }

    public void setJtBean(JtBean jtBean) {
        this.jtBean = jtBean;
    }

    public SayInfoAdapter(Context context, JtBean jtBean) {
        this.context = context;
        this.jtBean = jtBean;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return JT_INFO;
        } else{
            return JT_DISCUSS;
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == JT_INFO) {
            return new SayViewHolder(context, parent, onRecyclerViewListener, onRecyclerViewImageListener);
        } else if (viewType == JT_DISCUSS) {
            return new JtDiscussViewHolder(context, parent, null);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == JT_INFO) {
            ((BaseViewHolder) holder).bindData(jtBean);
        } else if (getItemViewType(position) == JT_DISCUSS) {
            ((BaseViewHolder) holder).bindData(jtBean.getDiscusses().get(position - 1));
        }

    }

    @Override
    public int getItemCount() {
        return jtBean.getDiscusses() == null ? 1 : jtBean.getDiscusses().size()+1;
    }


    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private OnRecyclerViewImageListener onRecyclerViewImageListener;

    public void setOnRecyclerViewImageListener(OnRecyclerViewImageListener onRecyclerViewImageListener) {
        this.onRecyclerViewImageListener = onRecyclerViewImageListener;
    }

}
