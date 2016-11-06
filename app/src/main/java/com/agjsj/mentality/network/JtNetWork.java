package com.agjsj.mentality.network;

import com.agjsj.mentality.bean.jt.JtBean;
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
        images.add("http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");
        images.add("http://img5q.duitang.com/uploads/item/201407/13/20140713142348_VNVBr.jpeg");
        images.add("http://img3.3lian.com/2013/s4/17/d/43.jpg");
        images.add("http://www.xiufa.com/Images/Articles/2016-09-18/1714778705.jpg");
        images.add("http://img5q.duitang.com/uploads/item/201407/22/20140722114442_itsSW.jpeg");


        List<JtBean> jtbeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            JtBean jtbeanItem = new JtBean();
            jtbeanItem.setId(i);
            jtbeanItem.setContent("第" + i + "条说说的内容");
            jtbeanItem.setCreateDate(new Date().getTime());
            jtbeanItem.setJtType(0);
            jtbeanItem.setImages(images);
            jtbeanItem.setUserInfo(UserNetwork.getInstance().getCurrentUser());
            jtbeanItem.setLikes(i);
            jtbeanItem.setStuId(UserNetwork.getInstance().getCurrentUser().getId());
            if (i % 2 == 0) {
                jtbeanItem.setIslikes(true);
            } else if (i % 2 == 1) {
                jtbeanItem.setIslikes(false);
            }
            jtbeans.add(jtbeanItem);


        }
        return jtbeans;
    }


}
