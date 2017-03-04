package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.bean.appoint.TimeStatus;
import com.agjsj.mentality.bean.appoint.TimeTemplate;
import com.agjsj.mentality.dialog.ShowMegDialog;
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
    private List<TimeTemplate> timeTemplates;

    private List<FreeTime> freeTimes;

    public List<FreeTime> getFreeTimes() {
        return freeTimes;
    }

    private String currentDate;

    public TimeAdapter(Context context, String currentDate, List<TimeTemplate> timeTemplates, List<FreeTime> freeTimes) {
        this.context = context;
        this.timeTemplates = timeTemplates;
        this.currentDate = currentDate;
        mInflate = LayoutInflater.from(context);
        this.freeTimes = freeTimes;
    }

    @Override
    public int getCount() {
        return timeTemplates.size();
    }

    @Override
    public Object getItem(int i) {
        return timeTemplates.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mInflate.inflate(R.layout.textview_showtime_tea, null);
            holder = new ViewHolder();
            holder.tv_time = (TextView) view.findViewById(R.id.tv_showtime_tea);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //Logger.i("TimeAdapter" + "timeStatus.get(i).getId()：" + timeStatus.get(i).getId() + "\n" + ConstatParam.timeTemplateMap.get(timeStatus.get(i).getId()).getTimeName() + "");
        holder.tv_time.setText(timeTemplates.get(i).getTimeStart() + "--" + timeTemplates.get(i).getTimeEnd());
        holder.tv_time.setBackgroundColor(Color.WHITE);

        for (int j = 0; j < freeTimes.size(); j++) {
            if (currentDate.equals(freeTimes.get(j).getTimeDate())
                    && timeTemplates.get(i).getId().equals(freeTimes.get(j).getTimeId())) {
                holder.tv_time.setBackgroundColor(context.getResources().getColor(R.color.text_gray));
            }

        }


        return view;
    }

    class ViewHolder {
        TextView tv_time;
    }
}
