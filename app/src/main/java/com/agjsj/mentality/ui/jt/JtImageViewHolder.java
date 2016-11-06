package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.utils.PicassoUtils;

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
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
//        BitmapFactory.decode
        //获得原图的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        PicassoUtils.loadImage(imageUrl, R.drawable.logo, R.drawable.logo, ivImage);
    }

    @OnClick(R.id.iv_image)
    public void onClick() {
    }
}
