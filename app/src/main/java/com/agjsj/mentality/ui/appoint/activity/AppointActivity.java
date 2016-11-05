package com.agjsj.mentality.ui.appoint.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.TabHost;

import com.agjsj.mentality.R;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;

import butterknife.Bind;

/**
 * Created by YH on 2016/11/4.
 */

public class AppointActivity extends ParentWithNaviActivity {


    @Override
    protected String title() {
        return "预约";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

    }


}
