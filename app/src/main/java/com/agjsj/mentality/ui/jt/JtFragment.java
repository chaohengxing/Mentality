package com.agjsj.mentality.ui.jt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.bean.user.UserType;
import com.agjsj.mentality.network.JtNetWork;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviFragment;
import com.agjsj.mentality.ui.photo.ViewPagerActivity;
import com.agjsj.mentality.utils.PicassoUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HengXing on 2016/10/29.
 */
public class JtFragment extends ParentWithNaviFragment implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewListener, OnRecyclerViewImageListener {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swiprefreshlayout)
   protected SwipeRefreshLayout refreshlayout;


    protected List<JtBean> jtBeens;

    private LinearLayoutManager layoutManager;
    protected JtFragmentAdapter adapter;

    private JtMenuPopup jtMenuPopup;

    @Override
    protected String title() {
        return "鸡汤";
    }

    @Override
    public Object left() {
        return R.drawable.logo;
    }

    @Override
    public Object right() {
        return R.drawable.plus_icon;
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

                jtMenuPopup.showPopupWindow(tv_right);

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_jt, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        refreshlayout.setOnRefreshListener(this);
        initRecycleView();
        jtMenuPopup = new JtMenuPopup(getActivity());
        getData();


        return rootView;
    }

    private void initRecycleView() {
        jtBeens = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        adapter = new JtFragmentAdapter(getActivity(), jtBeens);
        adapter.setOnRecyclerViewListener(this);
        adapter.setOnRecyclerViewImageListener(this);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void onImageItemClick(int itemPosition, int imagePosition) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("imageUrls", (ArrayList) jtBeens.get(itemPosition).getJtImgUrlList());
        bundle.putInt("position", imagePosition);

        startActivity(ViewPagerActivity.class, bundle);


    }

    @Override
    public void onItemClick(int position) {

        if (jtBeens.get(position).getJtType() == JtFragmentAdapter.TYPE_SAY) {
            Bundle bundle = new Bundle();
            bundle.putString("id", jtBeens.get(position).getId());
            startActivity(SayInfoActivity.class, bundle);


        } else if (jtBeens.get(position).getJtType() == JtFragmentAdapter.TYPE_SHARE) {

            Bundle bundle = new Bundle();
            bundle.putString("from", jtBeens.get(position).getShareFrom());
            bundle.putString("shareUrl", jtBeens.get(position).getShareUrl());
            startActivity(ShareInfoActivity.class, bundle);
        }
    }

    @Override

    public void onItemClick(int position, int id) {

        if (R.id.tv_like == id) {
            toast("点赞");
        } else if (R.id.iv_user_icon == id) {
            Bundle bundle = new Bundle();
            ArrayList<String> images = new ArrayList<>();
            if (UserType.StudentType == jtBeens.get(position).getUserType()) {
                images.add(jtBeens.get(position).getStudentInfo().getStuIcon());
            } else if (UserType.TeacherType == jtBeens.get(position).getUserType()) {
                images.add(jtBeens.get(position).getTeacherInfo().getTeacherIcon());
            }
            bundle.putSerializable("imageUrls", images);
            bundle.putInt("position", 0);
            startActivity(ViewPagerActivity.class, bundle);

        }

    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    /**
     * 从网络加载数据
     */
    protected void getData() {

        //1.加载数据
        refreshlayout.setRefreshing(true);

        JtNetWork.getInstance().getJtBeans(1, 20, new JtNetWork.GetJtBeansCallBack() {
            @Override
            public void response(int responseCode, List<JtBean> jtBeenList) {
                if (JtNetWork.GET_JTS_YES == responseCode) {
                    jtBeens.clear();
                    jtBeens.addAll(jtBeenList);
                } else if (JtNetWork.GET_JTS_NO == responseCode) {

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


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

        getData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
