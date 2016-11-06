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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YH on 2016/11/5.
 */

public class AppointStiRecyHeaderAdapter extends BaseAppointAdapter<AppointHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<AppointHolder> holders = new ArrayList<>();

    public AppointStiRecyHeaderAdapter() {
    }

    public List<AppointHolder> getHolders() {
        return holders;
    }

    public AppointStiRecyHeaderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AppointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appoint_stu, parent, false);
        AppointHolder holder = new AppointHolder(view);
        holders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(AppointHolder holder, int position) {
        if (position == 0) {
            holder.swipeMenuLayout.setVisibility(View.GONE);
        } else {
            holder.swipeMenuLayout.setVisibility(View.VISIBLE);
            holder.tv_name.setText(getItem(position).getTeacherName() + "");
            holder.tv_time.setText(getItem(position).getTime() + "");
            holder.tv_major.setText(getItem(position).getMarjor() + "");
            PicassoUtils.loadResourceImage(R.drawable.logo, 80, 80, holder.iv_icon);
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


    class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_time_view_appoint_stu)
        TextView tv_time;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
