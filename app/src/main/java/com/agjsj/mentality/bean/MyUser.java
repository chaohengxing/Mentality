package com.agjsj.mentality.bean;

/**
 * 用户类  包括学生和教师
 *
 * @author chx
 * @date 2016/10/29
 */
public class MyUser {

    private Integer id;
    private String openId;
    private String userIcon;
    private String userName;
    private String nickName;
    private String password;
    private Integer userType;
    private Long lastAlterTime;
    private Integer phoneType;
    private Long lastLoginTime;
    private String teacherIntroduce;

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", lastAlterTime=" + lastAlterTime +
                ", phoneType=" + phoneType +
                ", lastLoginTime=" + lastLoginTime +
                ", teacherIntroduce='" + teacherIntroduce + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getLastAlterTime() {
        return lastAlterTime;
    }

    public void setLastAlterTime(Long lastAlterTime) {
        this.lastAlterTime = lastAlterTime;
    }

    public Integer getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(Integer phoneType) {
        this.phoneType = phoneType;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getTeacherIntroduce() {
        return teacherIntroduce;
    }

    public void setTeacherIntroduce(String teacherIntroduce) {
        this.teacherIntroduce = teacherIntroduce;
    }
}
