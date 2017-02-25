package com.agjsj.mentality.ui.teachers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.teacher.TeacherInfo;
import com.agjsj.mentality.network.TeacherNetwork;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviFragment;
import com.agjsj.mentality.ui.chat.activity.ChatActivity;
import com.agjsj.mentality.ui.photo.ViewPagerActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by HengXing on 2016/10/29.
 */
public class TeachersFragment extends ParentWithNaviFragment implements OnRecyclerViewListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private LinearLayoutManager layoutManager;

    private List<TeacherInfo> teacherInfos;
    private TeachersFragmentAdapter adapter;

    @Override
    protected String title() {
        return "师资";
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

    /**
     * 发起一个会话
     */
    private void startChat(String userid) {

        //发起聊天
        //构造聊天方的用户信息:传入用户id、用户名和用户头像三个参数
//        BmobIMUserInfo info = new BmobIMUserInfo("0", "测试用户名", "http://images.china.cn/attachement/png/site1000/20150930/ac9e178530e11775d4363d.png");
        BmobIMUserInfo info = new BmobIMUserInfo(userid, "测试用户名", "http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");

        //启动一个会话，设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, false, null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("c", c);
        Logger.e("chx", "发起会话");
        startActivity(ChatActivity.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_teachers, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);


        initRecyclerView();


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 从网络获取教师列表
     */
    private void getData() {
        TeacherNetwork.getInstance().getTeacherInfos(new TeacherNetwork.GetTeacherInfosCallBack() {
            @Override
            public void response(int responseCode, List<TeacherInfo> teacherInfoList) {
                if (TeacherNetwork.GET_TEACHERINFOS_YES == responseCode) {
                    teacherInfos.clear();
                    teacherInfos.addAll(teacherInfoList);
                    adapter.notifyDataSetChanged();

                } else if (TeacherNetwork.GET_TEACHERINFOS_NO == responseCode) {
                    toast("获取师资列表失败!");
                } else {
                    toast("获取师资列表失败!");
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private void initRecyclerView() {


        teacherInfos = new ArrayList<>();

        refreshLayout.setOnRefreshListener(this);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        adapter = new TeachersFragmentAdapter(getActivity(), teacherInfos);
        adapter.setOnRecyclerViewListener(this);
        recyclerview.setAdapter(adapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(int position) {

        toTeacherInfoActivity(position);
    }


    @Override
    public void onItemClick(int position, int id) {
        if (R.id.tv_chat == id) {
            startChat(teacherInfos.get(position).getId());
        } else if (R.id.tv_comment == id) {
            toTeacherInfoActivity(position);
        } else if (R.id.tv_like == id) {
            toast("点赞");
        } else if (R.id.iv_icon == id) {
            Bundle bundle = new Bundle();
            ArrayList<String> images = new ArrayList<>();
            images.add(teacherInfos.get(position).getTeacherIcon());
            bundle.putSerializable("imageUrls", images);
            bundle.putInt("position", 0);
            startActivity(ViewPagerActivity.class, bundle);

        }
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void toTeacherInfoActivity(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("id", teacherInfos.get(position).getId());
        startActivity(TeacherInfoActivity.class, bundle);
    }
}


