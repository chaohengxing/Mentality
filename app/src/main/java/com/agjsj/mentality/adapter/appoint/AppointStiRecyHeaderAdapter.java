package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.dialog.ShowMegDialog;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.utils.PicassoUtils;
import com.orhanobut.logger.Logger;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.listener.SwipeSwitchListener;

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
    public void onBindViewHolder(final AppointHolder holder, final int position) {
        if (position == 0) {
            holder.swipeMenuLayout.setVisibility(View.GONE);
        } else {
            holder.swipeMenuLayout.setVisibility(View.VISIBLE);
            holder.tv_name.setText(getItem(position).getTeacherName() + "");
            holder.tv_time.setText(getItem(position).getTime() + "");
            holder.tv_major.setText(getItem(position).getMarjor() + "");
            PicassoUtils.loadResourceImage(R.drawable.logo, 80, 80, holder.iv_icon);
        }
        //左右滑动监听时间
        holder.swipeMenuLayout.setSwipeListener(new SwipeSwitchListener() {
            @Override
            public void beginMenuClosed(SwipeMenuLayout swipeMenuLayout) {
//                Logger.e("", "left menu closed");
            }

            @Override
            public void beginMenuOpened(SwipeMenuLayout swipeMenuLayout) {

            }

            @Override
            public void endMenuClosed(SwipeMenuLayout swipeMenuLayout) {
//                Logger.e("", "right menu closed");

            }

            @Override
            public void endMenuOpened(SwipeMenuLayout swipeMenuLayout) {
//                Logger.e("", "right menu opened");
                for (int i = 0; i < holders.size(); i++) {
                    if (i != position && holders.get(i).swipeMenuLayout.isCurrentSwipeExit()) {
                        holders.get(i).doRecoverSwipeMenu();
                    }
                }
            }
        });

        //预约点击监听事件
        holder.btn_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Logger.i("Appoint Button Click");
                ShowMegDialog dialog = new ShowMegDialog(context, "提示", "确定预约此时间段:\n" + getItem(position).getTime() + "\n"
                        + getItem(position).getTeacherName() + "？");
                dialog.show();
                dialog.setOnResultListener(new ShowMegDialog.OnResultListener() {
                    @Override
                    public void onOk() {
                        Logger.i("OnOk");
                    }

                    @Override
                    public void onCancel() {
                        Logger.i("onCancel");
                    }
                });

            }
        });

    }


    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return -1;
        } else {
//            Logger.i(getItem(position).getDate()+":"+getItem(position).getDate().hashCode());
            return Math.abs(getItem(position).getDate().hashCode());
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
        Logger.i("getItem(position).getDate():" + getItem(position).getDate());
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
