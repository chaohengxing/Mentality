package com.agjsj.mentality.ui.jt;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseViewHolder;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.JtDiscussReplay;

import butterknife.Bind;

/**
 * Created by HengXing on 2016/11/8.
 */
public class JtReplayViewHolder extends BaseViewHolder {
    @Bind(R.id.tv_replayUserNickName)
    TextView tvReplayUserNickName;
    @Bind(R.id.tv_passiveUserNickName)
    TextView tvPassiveUserNickName;
    @Bind(R.id.tv_content)
    TextView tvContent;

    public JtReplayViewHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_jt_replay, listener);
    }

    @Override
    public void bindData(Object o) {
        JtDiscussReplay replay = (JtDiscussReplay) o;
        tvReplayUserNickName.setText(replay.getReplayUser().getNickName());
        tvPassiveUserNickName.setText(replay.getPassiverUser().getNickName());
        tvContent.setText(":" + replay.getReplayInfo());

    }
}
