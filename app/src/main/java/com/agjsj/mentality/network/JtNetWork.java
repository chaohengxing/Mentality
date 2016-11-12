package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.bean.jt.JtDiscuss;
import com.agjsj.mentality.bean.jt.JtDiscussReplay;
import com.agjsj.mentality.utils.PicassoUtils;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HengXing on 2016/11/6.
 */
public class JtNetWork {


    private static JtNetWork instance;

    public static JtNetWork getInstance() {
        if (instance == null) {
            instance = new JtNetWork();
        }
        return instance;
    }

    private JtNetWork() {
    }

    //---------------------------------------从网络中加载Jt内容

    /**
     * 分页加载
     * 请求参数  pageIndex  pageSize
     * <p/>
     * 数据端返回接口
     * Json字符串需要包含的内容:
     * jt的基本信息
     * 发布者的UserInfo
     */
    public List<JtBean> getJtBeans(int pageIndex, int pageSize) {

        List<String> images = new ArrayList<>();
        images.add("http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");
        images.add("http://img5q.duitang.com/uploads/item/201407/13/20140713142348_VNVBr.jpeg");
        images.add("http://img3.3lian.com/2013/s4/17/d/43.jpg");
        images.add("http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");
        images.add("http://img5q.duitang.com/uploads/item/201407/22/20140722114442_itsSW.jpeg");


        List<JtBean> jtbeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            JtBean jtbeanItem = new JtBean();


            jtbeanItem.setId(i);
            jtbeanItem.setContent("第" + i + "条说说的内容");
            jtbeanItem.setCreateDate(new Date().getTime());
            jtbeanItem.setUserInfo(UserNetwork.getInstance().getCurrentUser());
            jtbeanItem.setLikes(i);
            jtbeanItem.setStuId(UserNetwork.getInstance().getCurrentUser().getId());
            if (i % 3 == 0) {
                //带图片的说说
                jtbeanItem.setJtType(0);
                jtbeanItem.setImages(images);
                jtbeanItem.setIslikes(true);
            } else if (i % 3 == 1) {
                //不带图片的说说
                jtbeanItem.setJtType(0);
                jtbeanItem.setIslikes(false);
            } else if (i % 3 == 2) {
                //分享
                jtbeanItem.setJtType(1);
                jtbeanItem.setPreImage("http://mmbiz.qpic.cn/mmbiz_jpg/JmUxgMAv61RhHpPM13w98WcELPicaOfZYiawYWhphn3TwpYTZcwtMsprX7sPLkVmeLoOeibibJD48bmIstCMRlJKxg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
                jtbeanItem.setTitle("妈妈给我买了一双耐克的球鞋，学校就取消了我的贫困生助学金");
                jtbeanItem.setShareUrl("http://mp.weixin.qq.com/s?__biz=MjM5NjY3NjEyMA==&mid=2653016431&idx=1&sn=4df82b39863de15629f24e477367ab11&chksm=bd30e6188a476f0e2647ba49f613b93b397e6ff19cd5228fa92935ae977259787ad66fe591bb&scene=0#wechat_redirect");
                jtbeanItem.setFrom("微信");
            }
            jtbeans.add(jtbeanItem);
        }
        return jtbeans;
    }


    /**
     * 获取带有评论以及评论的回复  的  JtBean
     *
     * @param jtId
     * @return
     */
    public JtBean getJtBeanWithJtDiscussAndReplay(int jtId) {
        JtBean jtbeanItem = new JtBean();

        List<String> images = new ArrayList<>();
        images.add("http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");
        images.add("http://img5q.duitang.com/uploads/item/201407/13/20140713142348_VNVBr.jpeg");
        images.add("http://img3.3lian.com/2013/s4/17/d/43.jpg");
        images.add("http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");
        images.add("http://img5q.duitang.com/uploads/item/201407/22/20140722114442_itsSW.jpeg");

        jtbeanItem.setId(jtId);
        jtbeanItem.setContent("第" + jtId + "条说说的内容");
        jtbeanItem.setCreateDate(new Date().getTime());
        jtbeanItem.setUserInfo(UserNetwork.getInstance().getCurrentUser());
        jtbeanItem.setLikes(jtId);
        jtbeanItem.setStuId(UserNetwork.getInstance().getCurrentUser().getId());
        jtbeanItem.setJtType(0);
        jtbeanItem.setImages(images);
        jtbeanItem.setIslikes(true);

        List<JtDiscuss> jtDiscusses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            JtDiscuss jtDiscussItem = new JtDiscuss();
            jtDiscussItem.setId(i);
            jtDiscussItem.setCommentInfo("评论内容");
            jtDiscussItem.setCommentUserInfo(UserNetwork.getInstance().getCurrentUser());
            jtDiscussItem.setCommmentTime(new Date().getTime());
            jtDiscussItem.setCommentUserId(UserNetwork.getInstance().getCurrentUser().getId());
            jtDiscussItem.setJtId(jtId);
            List<JtDiscussReplay> replays = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                JtDiscussReplay replayItem = new JtDiscussReplay();
                replayItem.setId(j);
                replayItem.setDiscussId(i);
                replayItem.setReplayInfo("回复内容");
                replayItem.setPassiverUser(UserNetwork.getInstance().getCurrentUser());
                replayItem.setPassiveStuId(UserNetwork.getInstance().getCurrentUser().getId());
                replayItem.setReplayUser(UserNetwork.getInstance().getCurrentUser());
                replayItem.setReplayStuId(UserNetwork.getInstance().getCurrentUser().getId());
                replayItem.setReplayTime(new Date().getTime());
                replays.add(replayItem);
            }
            jtDiscussItem.setReplays(replays);


            jtDiscusses.add(jtDiscussItem);
        }

        jtbeanItem.setDiscusses(jtDiscusses);

        return jtbeanItem;
    }


}
