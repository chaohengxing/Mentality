package com.agjsj.mentality.ui.appoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.appoint.AppointHolder;
import com.agjsj.mentality.adapter.appoint.AppointStiRecyHeaderAdapter;
import com.agjsj.mentality.bean.appoint.AppointInfo;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.utils.TimeUtil;
import com.orhanobut.logger.Logger;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;
import com.tubb.smrv.SwipeHorizontalMenuLayout;
import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.SwipeVerticalMenuLayout;
import com.tubb.smrv.listener.SwipeSwitchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HengXing on 2016/10/29.
 */
public class StudentAppointFragment extends AppointFragment {

    private static final String TAG = "StudentAppoint";
    @Bind(R.id.recyc_appoint_stu)
    RecyclerView mRecyclerView;


    private LayoutInflater mInflater;
    private ArrayList<AppointInfo> mAppointInfoList;
    private ArrayList<String> mTitleList;

    private List<List<AppointInfo>> allAppoints;
    private AppointStiRecyHeaderAdapter adapter;
    private StickyRecyclerHeadersDecoration mHeaderDecor;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected String title() {
        return "预约";
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
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_appoint_stu, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);

        initDate();

        initView();
        setListener();

        return rootView;
    }


    private void initView() {
        adapter = new AppointStiRecyHeaderAdapter(getContext());
        adapter.add(new AppointInfo());
        adapter.addAll(mAppointInfoList);
        mRecyclerView.setAdapter(adapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mHeaderDecor = new StickyRecyclerHeadersDecoration(adapter);
        mRecyclerView.addItemDecoration(mHeaderDecor);

    }

    private void initDate() {
        mTitleList = new ArrayList<>();
        mAppointInfoList = new ArrayList<>();
        allAppoints = new ArrayList<>();


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                AppointInfo a = new AppointInfo();
                a.setTeacherName("李老师" + j);
                a.setTime(j + "--" + (j + 2));
                a.setMarjor("特长：");
                a.setDate(TimeUtil.getFormatToday(TimeUtil.FORMAT_DATE) + "_" + i);
                mAppointInfoList.add(a);
            }
        }


    }

    private void setListener() {
        //监听事件
        StickyRecyclerHeadersTouchListener touchListener = new StickyRecyclerHeadersTouchListener(mRecyclerView, mHeaderDecor);
        touchListener.setOnHeaderClickListener(new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View header, int position, long headerId) {
                toast("Header position: " + position + ", id:" + headerId);
            }
        });
        //The StickyHeaders aren't aware of your adapter so if you must notify them when your data set changes.
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mHeaderDecor.invalidateHeaders();
            }
        });

        mRecyclerView.addOnItemTouchListener(touchListener);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                List<AppointHolder> holders = adapter.getHolders();
                for (AppointHolder holder : holders
                        ) {
                    holder.doRecoverSwipeMenu();
                }
//                Logger.i("onScrolled");
            }
        });

    }


}
