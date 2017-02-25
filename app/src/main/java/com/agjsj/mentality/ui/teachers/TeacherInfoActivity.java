package com.agjsj.mentality.ui.teachers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.bean.teacher.DiscussTeacher;
import com.agjsj.mentality.bean.teacher.TeacherInfo;
import com.agjsj.mentality.bean.user.UserType;
import com.agjsj.mentality.network.JtNetWork;
import com.agjsj.mentality.network.TeacherNetwork;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.chat.activity.ChatActivity;
import com.agjsj.mentality.ui.jt.SayInfoAdapter;
import com.agjsj.mentality.ui.photo.ViewPagerActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * 师资的详情页  包括基本资料 和 评论回复
 *
 * @author chx
 * @date 2016/12/4
 */
public class TeacherInfoActivity extends ParentWithNaviActivity implements OnRecyclerViewListener {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
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

    private TeacherInfo teacherInfo;
    private LinearLayoutManager layoutManager;
    private TeacherInfoAdapter adapter;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        ButterKnife.bind(this);


        teacherInfo = new TeacherInfo();

        Bundle bundle = getBundle();
        id = bundle.getString("id");

        initNaviView();

        initBottomBar();


        initRecyclerView();

        getData();

    }


    @Override
    protected String title() {
        return "师资详情";
    }

    private void initBottomBar() {
        btnChatVoice.setVisibility(View.GONE);
        btnChatKeyboard.setVisibility(View.GONE);
        btnSpeak.setVisibility(View.GONE);
        editMsg.setVisibility(View.VISIBLE);
        btnChatSend.setVisibility(View.VISIBLE);
        editMsg.setHint("请输入评论内容");

    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new TeacherInfoAdapter(this, teacherInfo);
        recyclerview.setAdapter(adapter);


        adapter.setOnRecyclerViewListener(this);

    }

    /**
     * 根据说说的Id来获取该教师的详细内容,包括评论和回复
     */
    private void getData() {
        TeacherNetwork.getInstance().getTeacherInfoWithJtDiscussAndReplay(id, new TeacherNetwork.GetTeacherInfoWithJtDiscussAndReplayCallBack() {
            @Override
            public void response(int responseCode, TeacherInfo teacherInfo) {
                if (TeacherNetwork.GET_TEACHERINFO_WITH_DISCUSS_YES == responseCode) {
                    adapter.setTeacherInfo(teacherInfo);
                    adapter.notifyDataSetChanged();
                } else if (TeacherNetwork.GET_TEACHERINFO_WITH_DISCUSS_NO == responseCode) {
                    toast("获取师资详情失败!");
                } else {
                    toast("获取师资详情失败!");
                }
            }
        });
    }

    /**
     * 发送评论
     */
    @OnClick(R.id.btn_chat_send)
    public void onClick() {
        if (UserType.StudentType == UserNetwork.getInstance().getCurrentUser().getUserType()) {
            if (TextUtils.isEmpty(editMsg.getText())) {
                toast("请输入评论内容");
            } else {

                sendDiscussTeacher();
            }

        } else if (UserType.TeacherType == UserNetwork.getInstance().getCurrentUser().getUserType()) {
            toast("只有学生用户才可评论!");
        }
    }

    private void sendDiscussTeacher() {
        DiscussTeacher discussTeacher = new DiscussTeacher(UserNetwork.getInstance().getCurrentUser().getId(),
                adapter.getTeacherInfo().getId(), editMsg.getText().toString().trim());

        TeacherNetwork.getInstance().sendDiscussTeacher(discussTeacher, new TeacherNetwork.SendDiscussTeacherCallBack() {
            @Override
            public void response(int responseCode) {
                if (TeacherNetwork.SEND_DISCUSS_TEACHER_YES == responseCode) {
                    toast("发送评论成功!");
                    editMsg.setText("");
                    hideSoftInputView();
                    getData();

                } else if (TeacherNetwork.SEND_DISCUSS_TEACHER_NO == responseCode) {
                    toast("发送评论失败!");
                }
            }
        });
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(int position, int id) {

        if (R.id.tv_chat == id) {
            startChat(teacherInfo.getId());
        } else if (R.id.iv_icon == id) {
            Bundle bundle = new Bundle();
            ArrayList<String> images = new ArrayList<>();
            images.add(teacherInfo.getTeacherIcon());
            bundle.putSerializable("imageUrls", images);
            bundle.putInt("position", 0);
            startActivity(ViewPagerActivity.class, bundle);

        } else if (R.id.tv_like == id) {
            toast("点赞");
        } else if (R.id.tv_comment == id) {
            toast("显示软键盘");
            editMsg.setFocusable(true);
            showSoftInputView();
        }

    }

    @Override
    public boolean onItemLongClick(int position) {


        return false;
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

}
