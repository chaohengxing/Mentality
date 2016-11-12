package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by HengXing on 2016/11/6.
 */
public class JtImageViewHolder extends BaseViewHolder {
    @Bind(R.id.iv_image)
    ImageView ivImage;

    public JtImageViewHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_jt_image, listener);
    }

    @Override
    public void bindData(Object o) {

        String imageUrl = (String) o;


        PicassoUtils.loadImage(imageUrl, R.drawable.logo, R.drawable.logo, ivImage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(getAdapterPosition());
            }
        });


    }

    @OnClick(R.id.iv_image)
    public void onClick() {
    }
}
