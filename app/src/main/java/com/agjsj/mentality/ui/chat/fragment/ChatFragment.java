package com.agjsj.mentality.ui.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.ChatFragmentAdapter;
import com.agjsj.mentality.adapter.base.IMutlipleItem;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.Conversation;
import com.agjsj.mentality.bean.PrivateConversation;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviFragment;
import com.agjsj.mentality.ui.chat.event.RefreshEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;

/**
 * Created by HengXing on 2016/10/29.
 */
public class ChatFragment extends ParentWithNaviFragment {
    @Bind(R.id.rc_view)
    RecyclerView rcView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;

    private ChatFragmentAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected String title() {
        return "会话";
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
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chat, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);

//单一布局
        IMutlipleItem<Conversation> mutlipleItem = new IMutlipleItem<Conversation>() {

            @Override
            public int getItemViewType(int postion, Conversation c) {
                return 0;
            }

            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_chat_fragment;
            }

            @Override
            public int getItemCount(List<Conversation> list) {
                return list.size();
            }
        };
        adapter = new ChatFragmentAdapter(getActivity(), mutlipleItem, null);
        rcView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rcView.setLayoutManager(linearLayoutManager);
        swRefresh.setEnabled(true);
        setListener();
        return rootView;
    }

    private void setListener() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swRefresh.setRefreshing(true);
                query();
            }
        });
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                adapter.getItem(position).onClick(getActivity());
            }

            @Override
            public void onItemClick(int position, int id) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                adapter.getItem(position).onLongClick(getActivity());
                adapter.remove(position);
                return true;
            }
        });

    }

    /**
     * 查询本地会话
     */
    public void query() {
//        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
        swRefresh.setRefreshing(false);
    }

    /**
     * 获取会话列表的数据：增加新朋友会话
     *
     * @return
     */
    private List<Conversation> getConversations() {
        //添加会话
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if (list != null && list.size() > 0) {
            for (BmobIMConversation item : list) {
                switch (item.getConversationType()) {
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
        return conversationList;
    }

    @Override
    public void onResume() {
        super.onResume();
        swRefresh.setRefreshing(true);
        query();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        log("---会话页接收到自定义消息---");
        //因为新增`新朋友`这种会话类型
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        //重新刷新列表
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
    }

    /**
     * 注册消息接收事件
     *
     * @param event 1、与用户相关的由开发者自己维护，SDK内部只存储用户信息
     *              2、开发者获取到信息后，可调用SDK内部提供的方法更新会话
     */
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        //重新获取本地消息并刷新列表
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
    }
}
