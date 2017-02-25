package com.agjsj.mentality.adapter.appoint;

import android.support.v7.widget.RecyclerView;

import com.agjsj.mentality.bean.appoint.AppointInfo;
import com.agjsj.mentality.bean.appoint.FreeTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by YH on 2016/11/5.
 */

public abstract class BaseAppointAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private ArrayList<FreeTime> items = new ArrayList<FreeTime>();


    public void add(FreeTime object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, FreeTime object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends FreeTime> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(FreeTime... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public FreeTime getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
