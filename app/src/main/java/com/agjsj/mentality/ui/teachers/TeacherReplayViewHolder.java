package com.agjsj.mentality.ui.teachers;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.teacher.ReplayDiscussTeacher;
import com.agjsj.mentality.bean.user.UserType;

import butterknife.Bind;

/**
 * Created by HengXing on 2016/11/8.
 */
public class TeacherReplayViewHolder extends BaseViewHolder {
    @Bind(R.id.tv_replayUserNickName)
    TextView tvReplayUserNickName;
    @Bind(R.id.tv_passiveUserNickName)
    TextView tvPassiveUserNickName;
    @Bind(R.id.tv_content)
    TextView tvContent;

    public TeacherReplayViewHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_jt_replay, listener);
    }

    @Override
    public void bindData(Object o) {
//        JtDiscussReplay replay = (JtDiscussReplay) o;
        ReplayDiscussTeacher replay = (ReplayDiscussTeacher) o;

        if (UserType.StudentType == replay.getReplayUserType()) {
            tvReplayUserNickName.setText(replay.getReplayStudentInfo().getStuNickName());

        } else if (UserType.TeacherType == replay.getReplayUserType()) {
            tvReplayUserNickName.setText(replay.getReplayTeacherInfo().getTeacherNickName());

        }
        if (UserType.StudentType == replay.getPassiveUserType()) {
            tvPassiveUserNickName.setText(replay.getPassiveStudentInfo().getStuNickName());

        } else if (UserType.TeacherType == replay.getPassiveUserType()) {
            tvPassiveUserNickName.setText(replay.getPassiveTeacherInfo().getTeacherNickName());
        }
        tvContent.setText(":" + replay.getRepalyInfo());

    }
}
