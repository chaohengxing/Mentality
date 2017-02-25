package com.agjsj.mentality.ui.jt;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.jt.DiscussJt;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.bean.user.UserType;
import com.agjsj.mentality.network.JtNetWork;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.photo.ViewPagerActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

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

    private String id;

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
        id = bundle.getString("id");

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
        editMsg.setHint("请输入评论内容");
    }

    /**
     * 根据说说的Id来获取说说的详细内容,包括评论和回复
     */
    private void getData() {


        JtNetWork.getInstance().getJtBeanWithJtDiscussAndReplay(id, new JtNetWork.GetJtBeanWithJtDiscussAndReplayCallBack() {
            @Override
            public void response(int responseCode, JtBean jt) {
                if (JtNetWork.GET_JTINFO_WITH_DISCUSS_YES == responseCode) {
                    jtBean = jt;
                    adapter.setJtBean(jt);
                    adapter.notifyDataSetChanged();
                } else if (JtNetWork.GET_JTINFO_WITH_DISCUSS_NO == responseCode) {

                    toast("获取鸡汤详情失败!");
                } else {
                    toast("获取鸡汤详情失败!");
                }
            }
        });


    }

    /**
     * 发送评论
     */
    @OnClick(R.id.btn_chat_send)
    public void onClick() {
        if (TextUtils.isEmpty(editMsg.getText())) {
            toast("请输入评论内容");
        } else {
            sendDiscussJt();
        }
    }

    private void sendDiscussJt() {

        DiscussJt discussJt = new DiscussJt(id,
                UserNetwork.getInstance().getCurrentUser().getUserType(),
                UserNetwork.getInstance().getCurrentUser().getId(),
                editMsg.getText().toString().trim());
        JtNetWork.getInstance().sendDiscussJt(discussJt, new JtNetWork.SendDiscussJtCallBack() {
            @Override
            public void response(int responseCode) {
                if (JtNetWork.SEND_DISCUSS_JT_YES == responseCode) {
                    toast("发送评论成功!");
                    editMsg.setText("");
                    hideSoftInputView();
                    getData();
                } else if (JtNetWork.SEND_DISCUSS_JT_NO == responseCode) {
                    toast("发送评论失败");
                }
            }
        });

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(int position, int id) {

        if (R.id.tv_like == id) {
            toast("点赞");
        } else if (R.id.tv_comment == id) {
            editMsg.setFocusable(true);
            showSoftInputView();
        } else if (R.id.iv_user_icon == id) {
            Bundle bundle = new Bundle();
            ArrayList<String> images = new ArrayList<>();
            if (UserType.StudentType == jtBean.getUserType()) {
                images.add(jtBean.getStudentInfo().getStuIcon());
            } else if (UserType.TeacherType == jtBean.getUserType()) {
                images.add(jtBean.getTeacherInfo().getTeacherIcon());
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

    @Override
    public void onImageItemClick(int itemPosition, int imagePosition) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("imageUrls", (ArrayList) jtBean.getJtImgUrlList());
        bundle.putInt("position", imagePosition);

        startActivity(ViewPagerActivity.class, bundle);

    }
}
