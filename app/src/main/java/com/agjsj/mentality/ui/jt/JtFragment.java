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
import com.agjsj.mentality.network.JtNetWork;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviFragment;
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
    SwipeRefreshLayout refreshlayout;


    private List<JtBean> jtBeens;

    private LinearLayoutManager layoutManager;
    private JtFragmentAdapter adapter;


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

        toast("显示第" + itemPosition + "个说说的第" + imagePosition + "张图片的大图");


    }

    @Override
    public void onItemClick(int position) {

        if (jtBeens.get(position).getJtType() == JtFragmentAdapter.TYPE_SAY) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", jtBeens.get(position).getId());
            startActivity(SayInfoActivity.class, bundle);


        } else if (jtBeens.get(position).getJtType() == JtFragmentAdapter.TYPE_SHARE) {

            Bundle bundle = new Bundle();
            bundle.putString("from", jtBeens.get(position).getFrom());
            bundle.putString("shareUrl", jtBeens.get(position).getShareUrl());
            startActivity(ShareInfoActivity.class, bundle);
        }
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    /**
     * 从网络加载数据
     */
    private void getData() {

        //1.加载数据
        refreshlayout.setRefreshing(true);

        jtBeens.addAll(JtNetWork.getInstance().getJtBeans(1, 20));


        //2.刷新页面
        adapter.notifyDataSetChanged();
        refreshlayout.setRefreshing(false);


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
