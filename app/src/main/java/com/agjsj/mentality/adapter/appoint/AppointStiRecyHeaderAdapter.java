package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.utils.PicassoUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YH on 2016/11/5.
 */

public class AppointStiRecyHeaderAdapter extends BaseAppointAdapter<AppointHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context context;

    public AppointStiRecyHeaderAdapter() {
    }

    public AppointStiRecyHeaderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AppointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appoint_stu, parent, false);
        return new AppointHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointHolder holder, int position) {
        if (position == 0) {
            holder.ll_appointInfo.setVisibility(View.GONE);
        } else {
            holder.ll_appointInfo.setVisibility(View.VISIBLE);
            holder.tv_name.setText(getItem(position).getTeacherName() + "");
            holder.tv_time.setText(getItem(position).getTime() + "");
            holder.tv_major.setText(getItem(position).getMarjor() + "");
            PicassoUtils.loadResourceImage(R.drawable.ic_launcher, 80, 80, holder.iv_icon);
        }


    }

    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return -1;
        } else {
            return getItem(position).getDate().hashCode();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_header_appoint, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TitleViewHolder mHolder = (TitleViewHolder) holder;
        mHolder.tv_time.setText(getItem(position).getDate());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name_appointInfo_item)
        TextView tv_name;
        @Bind(R.id.tv_time_appointInfo_item)
        TextView tv_time;
        @Bind(R.id.tv_major_appointInfo_item)
        TextView tv_major;
        @Bind(R.id.iv_icon_appointInfo_itme)
        CircleImageView iv_icon;
        @Bind(R.id.ll_appointInfo_item)
        LinearLayout ll_appointInfo;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_time_view_appoint_stu)
        TextView tv_time;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
