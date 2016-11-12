package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.utils.PicassoUtils;
import com.agjsj.mentality.utils.TimeUtil;

import butterknife.Bind;

/**
 * 说说的Viewholder
 * Created by HengXing on 2016/11/6.
 */
public class SayViewHolder extends BaseViewHolder {


    @Bind(R.id.iv_user_icon)
    CircleImageView ivUserIcon;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.recyc_imags)
    RecyclerView recycImags;
    @Bind(R.id.tv_like)
    TextView tvLike;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.include_layout)
    RelativeLayout includeLayout;


    private OnRecyclerViewImageListener onRecyclerViewImageListener;


    public SayViewHolder(Context context, ViewGroup root, OnRecyclerViewListener listener, OnRecyclerViewImageListener onRecyclerViewImageListener) {
        super(context, root, R.layout.item_jt_say, listener);
        this.onRecyclerViewImageListener = onRecyclerViewImageListener;
    }

    @Override
    public void bindData(Object o) {

        JtBean jtBean = (JtBean) o;

        PicassoUtils.loadResizeImage(jtBean.getUserInfo().getUserIcon(), R.drawable.logo, R.drawable.logo, 100, 100, ivUserIcon);

        tvNickname.setText(TextUtils.isEmpty(jtBean.getUserInfo().getNickName()) ? jtBean.getUserInfo().getUserName() : jtBean.getUserInfo().getNickName());
        tvTime.setText(TimeUtil.getChatTime(false, jtBean.getCreateDate()));
        tvContent.setText(jtBean.getContent());
        tvLike.setText("(" + jtBean.getLikes() + ")");

        if (jtBean.getImages() == null || jtBean.getImages().size() == 0) {
            recycImags.setVisibility(View.GONE);
        } else {
            recycImags.setVisibility(View.VISIBLE);
            //设置图片内容
            recycImags.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            JtImageAdapter imageAdapter = new JtImageAdapter(context, jtBean.getImages());

            imageAdapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
                @Override
                public void onItemClick(int position) {

                    onRecyclerViewImageListener.onImageItemClick(getAdapterPosition(), position);

                }

                @Override
                public boolean onItemLongClick(int position) {
                    return false;
                }
            });
            recycImags.setAdapter(imageAdapter);
        }

        includeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(getAdapterPosition());
            }
        });

    }
}
