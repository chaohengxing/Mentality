package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by YH on 2016/11/5.
 */

public class FreeTimeAdapter extends RecyclerView.Adapter<FreeTimeHolder> {

    private Context context;
    List<FreeTime> freeTimeList;

    public FreeTimeAdapter(Context context, List<FreeTime> freeTimeList) {
        this.context = context;
        this.freeTimeList = freeTimeList;
    }

    @Override
    public FreeTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreeTimeHolder(context, parent, R.layout.item_appoint_teacher);
    }

    @Override
    public void onBindViewHolder(FreeTimeHolder holder, int position) {
        Logger.i("BindDate at Adapter", freeTimeList.get(position).toString());
        holder.bindData(freeTimeList.get(position));
    }

    @Override
    public int getItemCount() {
        return freeTimeList.size();
    }
}
