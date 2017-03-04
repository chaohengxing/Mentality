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
import com.agjsj.mentality.adapter.appoint.FreeTimeHolder;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.bean.appoint.TeacherTimePlan;
import com.agjsj.mentality.bean.appoint.TimeStatus;
import com.agjsj.mentality.bean.appoint.TimeTemplate;
import com.agjsj.mentality.global.MyConfig;
import com.agjsj.mentality.network.AppointNetwork;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.chat.Config;
import com.agjsj.mentality.utils.DialogFactory;
import com.agjsj.mentality.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HengXing on 2016/10/29.
 */
public class TeacherAppointFragment extends AppointFragment implements FreeTimeHolder.OnTeacherPlanFreeTimeListener {
    @Bind(R.id.recyc_appoint_teacher)
    RecyclerView mRecyclerView;

    private FreeTimeAdapter mAdapter;
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
        return null;
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

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_appoint_teacher, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        initView();

        initData();


        return rootView;
    }

    private void initView() {
        mAdapter = new FreeTimeAdapter(getActivity());
        mAdapter.setOnTeacherPlanFreeTimeListener(this);
        mRecyclerView.setAdapter(mAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        AppointNetwork.getInstance().teacherGetFreeTimes(UserNetwork.getInstance().getCurrentUser().getId(), MyConfig.getTimeDates(), new AppointNetwork.TeacherGetFreeTimesCallBack() {

            @Override
            public void response(int code, TeacherTimePlan timePlan) {

                Logger.e(timePlan.getTemplates().size() + "////" + timePlan.getFreeTimes().size());

                if (AppointNetwork.TEACHER_GETFREETIMES_YES == code) {
                    mAdapter.setTeacherTimePlan(timePlan);
                    mAdapter.notifyDataSetChanged();
                } else if (AppointNetwork.TEACHER_GETFREETIMES_NO == code) {
                    toast("获取数据失败!");
                }

            }
        });
    }

    private boolean getRadomBool() {
        if (((int) (Math.random() * 100)) % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 教师点击该时间段  排挡
     *
     * @param datePosition
     * @param timeTemplatePosition
     */
    @Override
    public void onItemClick(final int datePosition, final int timeTemplatePosition) {

        //先判断是否已经排挡
        for (int i = 0; i < mAdapter.getTeacherTimePlan().getFreeTimes().size(); i++) {
            if (mAdapter.getTimedates().get(datePosition).equals(mAdapter.getTeacherTimePlan().getFreeTimes().get(i).getTimeDate())
                    && mAdapter.getTeacherTimePlan().getTemplates().get(timeTemplatePosition).getId().equals(mAdapter.getTeacherTimePlan().getFreeTimes().get(i).getTimeId())) {

                new DialogFactory().getDialog(getContext(), "您已在该时段排挡，请不要重复排挡!", "确定", null).show();
                return;
            }
        }

        new DialogFactory().getDialog(getContext(), "您确定要在此时间段排挡吗?", "取消", "确定", new DialogFactory.MDialogListener() {
            @Override
            public void leftbt(int key) {

            }

            @Override
            public void rightbt(int key) {
                //在此发布排挡
                FreeTime freeTime = new FreeTime(UserNetwork.getInstance().getCurrentUser().getId(),
                        mAdapter.getTimedates().get(datePosition),
                        mAdapter.getTeacherTimePlan().getTemplates().get(timeTemplatePosition).getId());
                AppointNetwork.getInstance().planFreeTime(freeTime, new AppointNetwork.PlanFreeTimeCallBack() {
                    @Override
                    public void response(int code) {
                        if (AppointNetwork.PLAN_FREETIME_YES == code) {
                            toast("排挡成功!");
                            initData();
                        } else if (AppointNetwork.PLAN_FREETIME_NO == code) {
                            toast("排挡失败!");
                        }
                    }
                });
            }
        });


    }
}
