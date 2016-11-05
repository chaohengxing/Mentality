package com.agjsj.mentality.ui.appoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agjsj.mentality.adapter.appoint.FreeTimeAdapter;
import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.bean.appoint.TimeStatus;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HengXing on 2016/10/29.
 */
public class TeacherAppointFragment extends AppointFragment {
    @Bind(R.id.recyc_appoint_teacher)
    RecyclerView mRecyclerView;

    private FreeTimeAdapter mAdapter;
    private List<FreeTime> freeTimeList;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected String title() {
        return "发布预约";
    }

    @Override
    public Object left() {
        return R.drawable.logo;
    }

    @Override
    public Object right() {
        return R.drawable.search_icon;
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {
                ((MainActivity) getActivity()).switchMenu();
            }

            @Override
            public void clickRight() {
                startActivity(SearchActivity.class, null);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_appoint_teacher, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);

        initData();

        initView();

        return rootView;
    }

    private void initView() {
        mAdapter = new FreeTimeAdapter(getActivity(), freeTimeList);
        mRecyclerView.setAdapter(mAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        freeTimeList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            FreeTime time = new FreeTime();
            time.setDate(TimeUtil.getFormatToday(TimeUtil.FORMAT_DATE + "_" + i));
            List<TimeStatus> timeStatus = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                TimeStatus status = new TimeStatus();
                status.setId(j);
                status.setStatus(getRadomBool());
                timeStatus.add(status);
            }
            time.setTimeStatus(timeStatus);
            freeTimeList.add(time);
        }
    }

    private boolean getRadomBool() {
        if (((int) (Math.random() * 100)) % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }


}
