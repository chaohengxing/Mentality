package com.agjsj.mentality.ui.menu;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.network.JtNetWork;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.jt.JtFragment;

import java.util.List;

/**
 * Created by HengXing on 2017/2/26.
 */
public class MyJtFragment extends JtFragment {

    @Override
    protected String title() {
        return "我的发布";
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
                ((MyJtActivity) getActivity()).finish();
            }

            @Override
            public void clickRight() {


            }
        };
    }
    @Override
    protected void getData() {
        //1.加载数据
        refreshlayout.setRefreshing(true);

        JtNetWork.getInstance().getMyJtBeans(UserNetwork.getInstance().getCurrentUser().getId(),
                new JtNetWork.GetMyJtBeansCallBack() {
                    @Override
                    public void response(int responseCode, List<JtBean> jtBeenList) {
                        if (JtNetWork.GET_MYJTS_YES == responseCode) {
                            jtBeens.clear();
                            jtBeens.addAll(jtBeenList);
                        } else if (JtNetWork.GET_MYJTS_NO == responseCode) {

                            toast("获取鸡汤列表失败!");
                        } else {
                            toast("获取鸡汤列表失败!");
                        }
                        //2.刷新页面
                        adapter.notifyDataSetChanged();
                        refreshlayout.setRefreshing(false);
                    }
                });


    }
}
