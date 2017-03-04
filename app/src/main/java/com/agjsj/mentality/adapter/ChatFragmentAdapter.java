package com.agjsj.mentality.adapter;

import android.content.Context;
import android.view.View;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.BaseRecyclerAdapter;
import com.agjsj.mentality.adapter.base.BaseRecyclerHolder;
import com.agjsj.mentality.adapter.base.IMutlipleItem;
import com.agjsj.mentality.bean.Conversation;
import com.agjsj.mentality.utils.TimeUtil;

import java.util.Collection;


/**
 * 使用进一步封装的Conversation,教大家怎么自定义会话列表
 *
 * @author smile
 */
public class ChatFragmentAdapter extends BaseRecyclerAdapter<Conversation> {

    public ChatFragmentAdapter(Context context, IMutlipleItem<Conversation> items, Collection<Conversation> datas) {
        super(context, items, datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, Conversation conversation, int position) {

        holder.setText(R.id.tv_msg, conversation.getLastMessageContent());
        holder.setText(R.id.tv_time, TimeUtil.getChatTime(false, conversation.getLastMessageTime()));
        //会话图标
        Object obj = conversation.getAvatar();
        if (obj instanceof String) {
            String avatar = (String) obj;
            holder.setImageView(avatar, R.drawable.default_pic, R.id.iv_user_icon);
        } else {
            int defaultRes = (int) obj;
            holder.setImageView(null, defaultRes, R.id.iv_user_icon);
        }
        //会话标题
        holder.setText(R.id.tv_user_name, conversation.getcName());
        //查询指定未读消息数
        long unread = conversation.getUnReadCount();
        if (unread > 0) {
            holder.setVisible(R.id.tv_unread, View.VISIBLE);
            holder.setText(R.id.tv_unread, String.valueOf(unread));
        } else {
            holder.setVisible(R.id.tv_unread, View.GONE);
        }
    }
}