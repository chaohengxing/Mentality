package com.agjsj.mentality.ui.jt;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.network.JtNetWork;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SayInfoActivity extends ParentWithNaviActivity implements OnRecyclerViewListener, OnRecyclerViewImageListener {

    @Bind(R.id.btn_chat_voice)
    Button btnChatVoice;
    @Bind(R.id.btn_chat_keyboard)
    Button btnChatKeyboard;
    @Bind(R.id.edit_msg)
    EditText editMsg;
    @Bind(R.id.btn_speak)
    Button btnSpeak;
    @Bind(R.id.btn_chat_send)
    Button btnChatSend;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    private JtBean jtBean;
    private LinearLayoutManager layoutManager;
    private SayInfoAdapter adapter;
    private Object data;

    private int id;

    @Override
    protected String title() {
        return "详情";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_say_info);

        ButterKnife.bind(this);

        jtBean = new JtBean();

        Bundle bundle = getBundle();
        id = bundle.getInt("id");

        initNaviView();

        initBottomBar();


        initRecyclerView();

        getData();


    }


    private void initRecyclerView() {

        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new SayInfoAdapter(this, jtBean);
        recyclerview.setAdapter(adapter);


        adapter.setOnRecyclerViewListener(this);
        adapter.setOnRecyclerViewImageListener(this);
    }

    private void initBottomBar() {
        btnChatVoice.setVisibility(View.GONE);
        btnChatKeyboard.setVisibility(View.GONE);
        btnSpeak.setVisibility(View.GONE);
        editMsg.setVisibility(View.VISIBLE);
        btnChatSend.setVisibility(View.VISIBLE);
    }

    /**
     * 根据说说的Id来获取说说的详细内容,包括评论和回复
     */
    private void getData() {

        jtBean = JtNetWork.getInstance().getJtBeanWithJtDiscussAndReplay(id);

        Logger.e("chx", jtBean.toString());
        adapter.setJtBean(jtBean);

        adapter.notifyDataSetChanged();


    }

    /**
     * 发送评论
     */
    @OnClick(R.id.btn_chat_send)
    public void onClick() {
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onImageItemClick(int itemPosition, int imagePosition) {
        toast("显示第" + imagePosition + "张图片的大图");
    }
}
