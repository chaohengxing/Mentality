package com.agjsj.mentality.ui.teachers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.R;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviFragment;
import com.agjsj.mentality.ui.chat.activity.ChatActivity;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by HengXing on 2016/10/29.
 */
public class TeachersFragment extends ParentWithNaviFragment {
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
//                startActivity(SearchActivity.class, null);

                startChat();

            }
        };
    }

    /**
     * 发起一个会话
     */
    private void startChat() {

        //发起聊天
        //构造聊天方的用户信息:传入用户id、用户名和用户头像三个参数
//        BmobIMUserInfo info = new BmobIMUserInfo("0", "测试用户名", "http://images.china.cn/attachement/png/site1000/20150930/ac9e178530e11775d4363d.png");
        BmobIMUserInfo info = new BmobIMUserInfo("0", "测试用户名", "http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");

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


        return rootView;
    }


}


