package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.TimeStatus;
import com.agjsj.mentality.param.ConstatParam;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by YH on 2016/11/5.
 */

public class TimeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflate;
    //时间段状态
    private List<TimeStatus> timeStatus;

    public TimeAdapter(Context context, List<TimeStatus> timeStatus) {
        this.context = context;
        this.timeStatus = timeStatus;
        mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return timeStatus.size();
    }

    @Override
    public Object getItem(int i) {
        return timeStatus.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mInflate.inflate(R.layout.textview_showtime_tea, null);
            holder = new ViewHolder();
            holder.tv_time = (TextView) view.findViewById(R.id.tv_showtime_tea);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Logger.i("TimeAdapter" + "timeStatus.get(i).getId()：" + timeStatus.get(i).getId() + "\n" + ConstatParam.timeTemplateMap.get(timeStatus.get(i).getId()).getTimeName() + "");
        holder.tv_time.setText(ConstatParam.timeTemplateMap.get(timeStatus.get(i).getId()).getTimeName() + "");
        if (timeStatus.get(i).isStatus()) {
            holder.tv_time.setBackgroundColor(Color.WHITE);
        } else {
            holder.tv_time.setBackgroundColor(Color.GRAY);
        }

        return view;
    }

    class ViewHolder {
        TextView tv_time;
    }
}
