package com.agjsj.mentality.ui.teachers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.teacher.TeacherInfo;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.utils.PicassoUtils;

import butterknife.Bind;

/**
 * Created by HengXing on 2016/11/29.
 */
public class TeacherItemHolder extends BaseViewHolder {
    @Bind(R.id.iv_icon)
    CircleImageView ivIcon;
    @Bind(R.id.tv_intro)
    TextView tvIntro;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.tv_like)
    TextView tvLike;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_chat)
    TextView tvChat;

    public TeacherItemHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_teachers, listener);
    }

    @Override
    public void bindData(Object o) {
        TeacherInfo teacherInfo = (TeacherInfo) o;

        PicassoUtils.loadResizeImage(teacherInfo.getTeacherIcon(), R.drawable.default_pic, R.drawable.default_pic, 200, 200, ivIcon);

        tvIntro.setText(teacherInfo.getTeacherIntro());
        tvNickname.setText(teacherInfo.getTeacherNickName());
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(getAdapterPosition(), ivIcon.getId());
            }
        });

        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onRecyclerViewListener.onItemClick(getAdapterPosition(), tvComment.getId());

            }
        });

        tvLike.setText("(" + 3 + ")");
        tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(getAdapterPosition(), tvLike.getId());

            }
        });

        tvChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(getAdapterPosition(), tvChat.getId());

            }
        });
    }
}
