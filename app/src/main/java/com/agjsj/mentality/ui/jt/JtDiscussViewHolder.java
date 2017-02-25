package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.DiscussJt;
import com.agjsj.mentality.bean.user.UserType;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.utils.PicassoUtils;
import com.agjsj.mentality.utils.TimeUtil;

import butterknife.Bind;

/**
 * Created by HengXing on 2016/11/8.
 */
public class JtDiscussViewHolder extends BaseViewHolder {
    @Bind(R.id.iv_user_icon)
    CircleImageView ivUserIcon;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_replay)
    TextView tvReplay;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    public JtDiscussViewHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_jt_discuss, listener);
    }

    @Override
    public void bindData(Object o) {
        DiscussJt jtDiscuss = (DiscussJt) o;
        if (UserType.StudentType == jtDiscuss.getUserType()) {
            PicassoUtils.loadResizeImage(jtDiscuss.getStudentInfo().getStuIcon(), R.drawable.logo, R.drawable.logo, 50, 50, ivUserIcon);
            tvNickname.setText(jtDiscuss.getStudentInfo().getStuNickName());

        } else if (UserType.TeacherType == jtDiscuss.getUserType()) {
            PicassoUtils.loadResizeImage(jtDiscuss.getTeacherInfo().getTeacherIcon(), R.drawable.logo, R.drawable.logo, 50, 50, ivUserIcon);
            tvNickname.setText(jtDiscuss.getTeacherInfo().getTeacherNickName());

        }
        tvTime.setText(TimeUtil.getChatTime(false, Long.parseLong(jtDiscuss.getDiscussTime())));
        tvContent.setText(jtDiscuss.getDiscussInfo());

        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        JtReplayAdapter adapter = new JtReplayAdapter(context, jtDiscuss.getReplayDiscussJts());
        recyclerview.setAdapter(adapter);

    }
}
