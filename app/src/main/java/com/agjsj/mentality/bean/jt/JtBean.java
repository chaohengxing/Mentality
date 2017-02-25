package com.agjsj.mentality.bean.jt;

import com.agjsj.mentality.bean.student.StudentInfo;
import com.agjsj.mentality.bean.teacher.TeacherInfo;
import com.agjsj.mentality.bean.user.MyUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.asm.Type;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HengXing on 2016/11/6.
 */
public class JtBean {
    private String id;
    private int userType;
    private String userId;
    private StudentInfo studentInfo;
    private TeacherInfo teacherInfo;
    private String jtImgUrl;
    private int jtType;
    private String jtInfo;
    private String preImage;
    private String shareTitle;
    private String shareFrom;
    private String shareUrl;
    private String jtTime;
    private List<DiscussJt> discussJts;

    public JtBean() {

    }
    // jtBean.setUserType(Integer.parseInt(request.getParameter("userType")));
    // jtBean.setUserId(request.getParameter("userId"));
    // jtBean.setJtType(Integer.parseInt(request.getParameter("jtType")));
    // jtBean.setJtInfo(request.getParameter("jtInfo"));
    // jtBean.setJtTime(Long.toString(new Date().getTime()));
    // jtBean.setJtImgUrl(request.getParameter("jtImageUrl"));

    /**
     * 发送鸡汤的构造器
     */
    public JtBean(int userType, String userId, int jtType, String jtInfo, String jtImgUrl) {
        this.userType = userType;
        this.userId = userId;
        this.jtType = jtType;
        this.jtInfo = jtInfo;
        this.jtImgUrl = jtImgUrl;
    }

    /**
     * 发送分享美文的构造器
     */
    public JtBean(int userType, String userId, int jtType, String jtInfo,String preImage,String shareTitle,String shareUrl,String shareFrom) {
        this.userType = userType;
        this.userId = userId;
        this.jtType = jtType;
        this.jtInfo = jtInfo;
        this.preImage=preImage;
        this.shareTitle = shareTitle;
        this.shareUrl = shareUrl;
        this.shareFrom = shareFrom;
    }
    @Override
    public String toString() {
        return JtBean.class.getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", userType=" + userType +
                ", userId='" + userId + '\'' +
                ", studentInfo=" + studentInfo +
                ", teacherInfo=" + teacherInfo +
                ", jtImgUrl='" + jtImgUrl + '\'' +
                ", jtType=" + jtType +
                ", jtInfo='" + jtInfo + '\'' +
                ", preImage='" + preImage + '\'' +
                ", shareTitle='" + shareTitle + '\'' +
                ", shareFrom='" + shareFrom + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", jtTime='" + jtTime + '\'' +
                '}';
    }

    /**
     * @return the jtTime
     */
    public String getJtTime() {
        return jtTime;
    }

    /**
     * @param jtTime the jtTime to set
     */
    public void setJtTime(String jtTime) {
        this.jtTime = jtTime;
    }

    /**
     * @return the discussJts
     */
    public List<DiscussJt> getDiscussJts() {
        return discussJts;
    }

    /**
     * @param discussJts the discussJts to set
     */
    public void setDiscussJts(List<DiscussJt> discussJts) {
        this.discussJts = discussJts;
    }

    /**
     * @return the studentInfo
     */
    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    /**
     * @param studentInfo the studentInfo to set
     */
    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    /**
     * @return the teacherInfo
     */
    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }

    /**
     * @param teacherInfo the teacherInfo to set
     */
    public void setTeacherInfo(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the userType
     */
    public int getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the jtImgUrl
     */
    public String getJtImgUrl() {
        return jtImgUrl;
    }

    /**
     * @param jtImgUrl the jtImgUrl to set
     */
    public void setJtImgUrl(String jtImgUrl) {
        this.jtImgUrl = jtImgUrl;
    }

    /**
     * @return the jtType
     */
    public int getJtType() {
        return jtType;
    }

    /**
     * @param jtType the jtType to set
     */
    public void setJtType(int jtType) {
        this.jtType = jtType;
    }

    /**
     * @return the jtInfo
     */
    public String getJtInfo() {
        return jtInfo;
    }

    /**
     * @param jtInfo the jtInfo to set
     */
    public void setJtInfo(String jtInfo) {
        this.jtInfo = jtInfo;
    }

    /**
     * @return the preImage
     */
    public String getPreImage() {
        return preImage;
    }

    /**
     * @param preImage the preImage to set
     */
    public void setPreImage(String preImage) {
        this.preImage = preImage;
    }

    /**
     * @return the shareTitle
     */
    public String getShareTitle() {
        return shareTitle;
    }

    /**
     * @param shareTitle the shareTitle to set
     */
    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    /**
     * @return the shareFrom
     */
    public String getShareFrom() {
        return shareFrom;
    }

    /**
     * @param shareFrom the shareFrom to set
     */
    public void setShareFrom(String shareFrom) {
        this.shareFrom = shareFrom;
    }

    /**
     * @return the shareUrl
     */
    public String getShareUrl() {
        return shareUrl;
    }

    /**
     * @param shareUrl the shareUrl to set
     */
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<String> getJtImgUrlList() {
        List<String> list = new ArrayList<>();
        try {
            if (jtImgUrl != null) {
                JSONArray jsonArray = new JSONArray(jtImgUrl);
                if (jsonArray != null)
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getString(i));
                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
