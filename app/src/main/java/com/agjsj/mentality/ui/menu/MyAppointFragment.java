package com.agjsj.mentality.ui.menu;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.appoint.FreeTime;
import com.agjsj.mentality.global.MyConfig;
import com.agjsj.mentality.network.AppointNetwork;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.appoint.StudentAppointFragment;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HengXing on 2017/2/26.
 */
public class MyAppointFragment extends StudentAppointFragment {
    @Override
    protected String title() {
        return "我的预约";
    }

    @Override
    public Object left() {
        return R.drawable.base_action_bar_back_bg_selector;
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
                getActivity().finish();
            }

            @Override
            public void clickRight() {

            }
        };
    }

    @Override
    protected void initDate() {
        AppointNetwork.getInstance().getFreeTimesWithMyAppoint(UserNetwork.getInstance().getCurrentUser().getId(),
                MyConfig.getTimeDates(), new AppointNetwork.GetFreeTimesWithMyAppointCallback() {
                    @Override
                    public void response(int code, List<FreeTime> freeTimes) {
                        if (AppointNetwork.GetFreeTimesWithMyAppoint_YES == code) {
                            //在此做一个排序,并且筛选掉未预约的
                            List<FreeTime> list = new ArrayList<FreeTime>();
                            List<String> dates = MyConfig.getTimeDates();
                            for (int i = 0; i < dates.size(); i++) {
                                for (int j = 0; j < freeTimes.size(); j++) {
                                    if (dates.get(i).equals(freeTimes.get(j).getTimeDate())) {
                                        if (freeTimes.get(j).getAppoint() == null || freeTimes.get(j).getAppoint().size() == 0) {

                                        } else {

                                            list.add(freeTimes.get(j));
                                        }
                                    }
                                }
                            }


                            adapter.clear();
                            adapter.addAll(list);
                            adapter.notifyDataSetChanged();
                        } else if (AppointNetwork.GetFreeTimesWithMyAppoint_NO == code) {
                            toast("获取数据失败!");
                        }
                    }
                }
        );
    }
}
