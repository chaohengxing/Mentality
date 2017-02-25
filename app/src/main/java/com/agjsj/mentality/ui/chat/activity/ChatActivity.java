package com.agjsj.mentality.ui.chat.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.ChatActivityAdapter;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.utils.Util;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMAudioMessage;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.BmobRecordManager;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.newim.listener.OnRecordChangeListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;

public class ChatActivity extends ParentWithNaviActivity implements MessageListHandler {


    @Bind(R.id.rc_view)
    RecyclerView rcView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    @Bind(R.id.iv_record)
    ImageView ivRecord;
    @Bind(R.id.tv_voice_tips)
    TextView tvVoiceTips;
    @Bind(R.id.layout_record)
    RelativeLayout layoutRecord;
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
    @Bind(R.id.ll_chat)
    LinearLayout llChat;
    private Drawable[] drawable_Anims;// 话筒动画
    BmobRecordManager recordManager;


    private ChatActivityAdapter adapter;
    protected LinearLayoutManager layoutManager;
    BmobIMConversation c;


    @Override
    protected String title() {
        return c.getConversationTitle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        c = BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) getBundle().getSerializable("c"));
        initNaviView();
        initSwipeLayout();
        initVoiceView();
        initBottomView();
    }

    private void initSwipeLayout() {
        swRefresh.setEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        rcView.setLayoutManager(layoutManager);
        adapter = new ChatActivityAdapter(this, c);
        rcView.setAdapter(adapter);
        llChat.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llChat.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swRefresh.setRefreshing(true);
                //自动刷新
                queryMessages(null);
            }
        });
        //下拉加载
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BmobIMMessage msg = adapter.getFirstMessage();
                queryMessages(msg);
            }
        });
        //设置RecyclerView的点击事件
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Logger.i("" + position);
            }

            @Override
            public void onItemClick(int position, int id) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                //这里省了个懒，直接长按就删除了该消息
                c.deleteMessage(adapter.getItem(position));
                adapter.remove(position);
                return true;
            }
        });
    }

    /**
     * 初始化语音布局
     *
     * @param
     * @return void
     */
    private void initVoiceView() {
        btnSpeak.setOnTouchListener(new VoiceTouchListener());
        initVoiceAnimRes();
        initRecordManager();
    }

    /**
     * 长按说话
     *
     * @author smile
     * @date 2014-7-1 下午6:10:16
     */
    class VoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!Util.checkSdCard()) {
                        toast("发送语音需要sdcard支持！");
                        return false;
                    }
                    try {
                        v.setPressed(true);
                        layoutRecord.setVisibility(View.VISIBLE);
                        tvVoiceTips.setText(getString(R.string.voice_cancel_tips));
                        // 开始录音
                        recordManager.startRecording(c.getConversationId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                case MotionEvent.ACTION_MOVE: {
                    if (event.getY() < 0) {
                        tvVoiceTips.setText(getString(R.string.voice_cancel_tips));
                        tvVoiceTips.setTextColor(Color.RED);
                    } else {
                        tvVoiceTips.setText(getString(R.string.voice_up_tips));
                        tvVoiceTips.setTextColor(Color.WHITE);
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
                    layoutRecord.setVisibility(View.INVISIBLE);
                    try {
                        if (event.getY() < 0) {// 放弃录音
                            recordManager.cancelRecording();
                            Logger.i("voice", "放弃发送语音");
                        } else {
                            int recordTime = recordManager.stopRecording();
                            if (recordTime > 1) {
                                // 发送语音文件
                                sendVoiceMessage(recordManager.getRecordFilePath(c.getConversationId()), recordTime);
                            } else {// 录音时间过短，则提示录音过短的提示
                                layoutRecord.setVisibility(View.GONE);
                                showShortToast().show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                default:
                    return false;
            }
        }
    }


    /**
     * 初始化语音动画资源
     *
     * @param
     * @return void
     * @Title: initVoiceAnimRes
     */
    private void initVoiceAnimRes() {
        drawable_Anims = new Drawable[]{
                getResources().getDrawable(R.mipmap.chat_icon_voice2),
                getResources().getDrawable(R.mipmap.chat_icon_voice3),
                getResources().getDrawable(R.mipmap.chat_icon_voice4),
                getResources().getDrawable(R.mipmap.chat_icon_voice5),
                getResources().getDrawable(R.mipmap.chat_icon_voice6)};
    }

    private void initRecordManager() {
        // 语音相关管理器
        recordManager = BmobRecordManager.getInstance(this);
        // 设置音量大小监听--在这里开发者可以自己实现：当剩余10秒情况下的给用户的提示，类似微信的语音那样
        recordManager.setOnRecordChangeListener(new OnRecordChangeListener() {

            @Override
            public void onVolumnChanged(int value) {
                ivRecord.setImageDrawable(drawable_Anims[value]);
            }

            @Override
            public void onTimeChanged(int recordTime, String localPath) {
                Logger.i("voice", "已录音长度:" + recordTime);
                if (recordTime >= BmobRecordManager.MAX_RECORD_TIME) {// 1分钟结束，发送消息
                    // 需要重置按钮
                    btnSpeak.setPressed(false);
                    btnSpeak.setClickable(false);
                    // 取消录音框
                    layoutRecord.setVisibility(View.INVISIBLE);
                    // 发送语音消息
                    sendVoiceMessage(localPath, recordTime);
                    //是为了防止过了录音时间后，会多发一条语音出去的情况。
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            btnSpeak.setClickable(true);
                        }
                    }, 1000);
                }
            }
        });
    }

    Toast toast;

    /**
     * 显示录音时间过短的Toast
     *
     * @return void
     * @Title: showShortToast
     */
    private Toast showShortToast() {
        if (toast == null) {
            toast = new Toast(this);
        }
        View view = LayoutInflater.from(this).inflate(
                R.layout.include_chat_voice_short, null);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        return toast;
    }


    private void initBottomView() {
//        editMsg.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
//                    scrollToBottom();
//                }
//                return false;
//            }
//        });
//        editMsg.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                scrollToBottom();
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!TextUtils.isEmpty(s)) {
//                    btnChatSend.setVisibility(View.VISIBLE);
//                    btnChatKeyboard.setVisibility(View.GONE);
//                    btnChatVoice.setVisibility(View.GONE);
//                } else {
//                    if (btnChatVoice.getVisibility() != View.VISIBLE) {
//                        btnChatVoice.setVisibility(View.VISIBLE);
//                        btnChatSend.setVisibility(View.GONE);
//                        btnChatKeyboard.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
    }


    @OnClick(R.id.btn_chat_voice)
    public void onVoiceClick(View view) {
        editMsg.setVisibility(View.GONE);
        btnChatVoice.setVisibility(View.GONE);
        btnChatKeyboard.setVisibility(View.VISIBLE);
        btnSpeak.setVisibility(View.VISIBLE);
        hideSoftInputView();
    }

    @OnClick(R.id.btn_chat_keyboard)
    public void onKeyClick(View view) {
        showEditState(false);
    }


    @OnClick(R.id.btn_chat_send)
    public void onSendClick(View view) {
        sendMessage();
    }

    /**
     * 根据是否点击笑脸来显示文本输入框的状态
     *
     * @param isEmo 用于区分文字和表情
     * @return void
     */
    private void showEditState(boolean isEmo) {
        editMsg.setVisibility(View.VISIBLE);
        btnChatKeyboard.setVisibility(View.GONE);
        btnChatVoice.setVisibility(View.VISIBLE);
        btnSpeak.setVisibility(View.GONE);
        editMsg.requestFocus();
        showSoftInputView();
    }

    /**
     * 显示软键盘
     */
    public void showSoftInputView() {
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .showSoftInput(editMsg, 0);
        }
    }


    /**
     * 首次加载，可设置msg为null，下拉刷新的时候，默认取消息表的第一个msg作为刷新的起始时间点，默认按照消息时间的降序排列
     *
     * @param msg
     */
    public void queryMessages(BmobIMMessage msg) {
        c.queryMessages(msg, 10, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                swRefresh.setRefreshing(false);
                if (e == null) {
                    if (null != list && list.size() > 0) {
                        adapter.addMessages(list);
                        layoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
                    }
                } else {
                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
            }
        });
    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
    }

    /**
     * 消息发送监听器
     */
    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
            //文件类型的消息才有进度值
            Logger.i("onProgress：" + value);
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
            adapter.addMessage(msg);
            editMsg.setText("");
            scrollToBottom();
        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
            adapter.notifyDataSetChanged();
            editMsg.setText("");
            scrollToBottom();
            if (e != null) {
                toast(e.getMessage());
            }
        }
    };

    /**
     * 发送文本消息
     */
    private void sendMessage() {
        String text = editMsg.getText().toString();
        if (TextUtils.isEmpty(text.trim())) {
            toast("请输入内容");
            return;
        }

        BmobIMTextMessage msg = new BmobIMTextMessage();
        msg.setContent(text);
        //可设置额外信息
        Map<String, Object> map = new HashMap<>();
        map.put("level", "1");//随意增加信息
        msg.setExtraMap(map);
        c.sendMessage(msg, listener);
    }


    /**
     * 发送语音消息
     *
     * @param local
     * @param length
     * @return void
     * @Title: sendVoiceMessage
     */
    private void sendVoiceMessage(String local, int length) {
        BmobIMAudioMessage audio = new BmobIMAudioMessage(local);
        //可设置额外信息-开发者设置的额外信息，需要开发者自己从extra中取出来
        Map<String, Object> map = new HashMap<>();
        map.put("from", "优酷");
        audio.setExtraMap(map);
        //设置语音文件时长：可选
//        audio.setDuration(length);
        c.sendMessage(audio, listener);
    }


    /**
     * 添加消息到聊天界面中
     *
     * @param event
     */
    private void addMessage2Chat(MessageEvent event) {
        BmobIMMessage msg = event.getMessage();
        if (c != null && event != null && c.getConversationId().equals(event.getConversation().getConversationId()) //如果是当前会话的消息
                && !msg.isTransient()) {//并且不为暂态消息
            if (adapter.findPosition(msg) < 0) {//如果未添加到界面中
                adapter.addMessage(msg);
                //更新该会话下面的已读状态
                c.updateReceiveStatus(msg);
            }
            scrollToBottom();
        } else {
            Logger.i("不是与当前聊天对象的消息");
        }
    }

    /**
     * 添加未读的通知栏消息到聊天界面
     */
    private void addUnReadMessage() {
        List<MessageEvent> cache = BmobNotificationManager.getInstance(this).getNotificationCacheList();
        if (cache.size() > 0) {
            int size = cache.size();
            for (int i = 0; i < size; i++) {
                MessageEvent event = cache.get(i);
                addMessage2Chat(event);
            }
        }
        scrollToBottom();
    }

    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        Logger.i("聊天页面接收到消息：" + list.size());
        //当注册页面消息监听时候，有消息（包含离线消息）到来时会回调该方法
        for (int i = 0; i < list.size(); i++) {
            addMessage2Chat(list.get(i));
        }
    }

    @Override
    protected void onResume() {
        //锁屏期间的收到的未读消息需要添加到聊天界面中
        addUnReadMessage();
        //添加页面消息监听器
        BmobIM.getInstance().addMessageListHandler(this);
        // 有可能锁屏期间，在聊天界面出现通知栏，这时候需要清除通知
        BmobNotificationManager.getInstance(this).cancelNotification();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移除页面消息监听器
        BmobIM.getInstance().removeMessageListHandler(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //清理资源
        if (recordManager != null) {
            recordManager.clear();
        }
        //更新此会话的所有消息为已读状态
        if (c != null) {
            c.updateLocalCache();
        }
        hideSoftInputView();
        super.onDestroy();
    }
}
