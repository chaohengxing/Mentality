package com.agjsj.mentality.adapter.appoint;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.agjsj.mentality.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/4.
 */

public class AppointPagerAdapter extends PagerAdapter {
    private ArrayList<View> mViewList;
    private ArrayList<String> mTabList;
    private Context context;

    String[] spinnetData = new String[]{"综合排序", "时间从低到高", "时间从高到底"};

    public AppointPagerAdapter(Context context, ArrayList<View> mViewList, ArrayList<String> mTabList) {
        this.context = context;
        this.mViewList = mViewList;
        this.mTabList = mTabList;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return mViewList.get(position);
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position);
    }


    class ViewHolder {
        Spinner spinner;
        Button btn_discuss;
        Button btn_popular;
    }
}
