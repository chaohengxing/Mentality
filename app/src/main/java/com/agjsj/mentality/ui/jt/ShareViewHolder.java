package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.bean.user.UserType;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.utils.PicassoUtils;
import com.agjsj.mentality.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import butterknife.Bind;

/**
 * 分享的Viewholder
 * Created by HengXing on 2016/11/6.
 */
public class ShareViewHolder extends BaseViewHolder {
    @Bind(R.id.iv_user_icon)
    CircleImageView ivUserIcon;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.iv_preview)
    ImageView ivPreview;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_from)
    TextView tvFrom;
    @Bind(R.id.tv_like)
    TextView tvLike;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.include_layout)
    RelativeLayout includeLayout;

    public ShareViewHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_jt_share, listener);
    }

    @Override
    public void bindData(Object o) {

        JtBean jtBean = (JtBean) o;
        if (UserType.StudentType == jtBean.getUserType()) {
            PicassoUtils.loadResizeImage(jtBean.getStudentInfo().getStuIcon(), R.drawable.default_pic, R.drawable.default_pic, 100, 100, ivUserIcon);
            tvNickname.setText(jtBean.getStudentInfo().getStuNickName());
        } else if (UserType.TeacherType == jtBean.getUserType()) {
            PicassoUtils.loadResizeImage(jtBean.getTeacherInfo().getTeacherIcon(), R.drawable.default_pic, R.drawable.default_pic, 100, 100, ivUserIcon);
            tvNickname.setText(jtBean.getTeacherInfo().getTeacherNickName());

        }
//
        tvTime.setText(TimeUtil.getChatTime(false, Long.parseLong(jtBean.getJtTime())));
        tvContent.setText(jtBean.getJtInfo());
//        tvLike.setText("(" + jtBean.getLikes() + ")");

        if (TextUtils.isEmpty(jtBean.getPreImage())) {
            ivPreview.setVisibility(View.GONE);
        } else {
            ivPreview.setVisibility(View.VISIBLE);
            PicassoUtils.loadResizeImage(jtBean.getPreImage(), R.drawable.default_pic, R.drawable.logo, 300, 300, ivPreview);

        }

        tvTitle.setText(TextUtils.isEmpty(jtBean.getShareTitle()) ? "" : jtBean.getShareTitle());
        tvFrom.setText("来自" + (TextUtils.isEmpty(jtBean.getShareFrom()) ? "第三方" : jtBean.getShareFrom()));

        tvComment.setVisibility(View.GONE);

        includeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(getAdapterPosition());
            }
        });
    }
}
