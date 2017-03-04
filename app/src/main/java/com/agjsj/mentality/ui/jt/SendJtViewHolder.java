package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.utils.PicassoUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by HengXing on 2016/11/21.
 */
public class SendJtViewHolder extends BaseViewHolder {
    @Bind(R.id.iv_image)
    ImageView ivImage;

    public SendJtViewHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_send_jt, listener);
    }

    @Override
    public void bindData(Object o) {

        String url = (String) o;

        if (TextUtils.isEmpty(url)) {
            //最后一张，显示图标
            ivImage.setImageResource(R.drawable.white_add);
        } else {
            //不是最后一张显示图片内容
//            PicassoUtils.loadResizeImage(url, R.drawable.logo, R.drawable.logo, 200, 200, ivImage);
            ivImage.setImageBitmap(BitmapFactory.decodeFile(url));
        }

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(getAdapterPosition());
            }
        });

    }
}
