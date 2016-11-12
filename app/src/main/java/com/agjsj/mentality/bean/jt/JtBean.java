package com.agjsj.mentality.bean.jt;

import com.agjsj.mentality.bean.MyUser;

import java.util.List;

/**
 * Created by HengXing on 2016/11/6.
 */
public class JtBean {


    private int id;
    private int userType;
    private int stuId;
    private int teacherId;
    private MyUser userInfo;
    private String content;
    private List<String> images;
    private Long createDate;
    private int jtType;
    private int likes;
    private boolean islikes;

    //分享类的JtBean
    private String preImage;
    private String title;
    private String from;
    private String shareUrl;

    private List<JtDiscuss> discusses;


    @Override
    public String toString() {
        return "JtBean{" +
                "id=" + id +
                ", userType=" + userType +
                ", stuId=" + stuId +
                ", teacherId=" + teacherId +
                ", userInfo=" + userInfo +
                ", content='" + content + '\'' +
                ", images=" + images +
                ", createDate=" + createDate +
                ", jtType=" + jtType +
                ", likes=" + likes +
                ", islikes=" + islikes +
                ", preImage='" + preImage + '\'' +
                ", title='" + title + '\'' +
                ", from='" + from + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", discusses=" + discusses +
                '}';
    }

    public List<JtDiscuss> getDiscusses() {
        return discusses;
    }

    public void setDiscusses(List<JtDiscuss> discusses) {
        this.discusses = discusses;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getPreImage() {
        return preImage;
    }

    public void setPreImage(String preImage) {
        this.preImage = preImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean islikes() {
        return islikes;
    }

    public void setIslikes(boolean islikes) {
        this.islikes = islikes;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public MyUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(MyUser userInfo) {
        this.userInfo = userInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public int getJtType() {
        return jtType;
    }

    public void setJtType(int jtType) {
        this.jtType = jtType;
    }

    public int getLikes() {

        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
