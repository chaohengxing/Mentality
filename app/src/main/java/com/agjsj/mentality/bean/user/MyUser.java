package com.agjsj.mentality.bean.user;

import java.io.Serializable;

/**
 * 用户类  包括学生和教师
 *
 * @author chx
 * @date 2016/10/29
 */
public class MyUser implements Serializable {

    //一下是用户公用字段
    private String id;
    private String account;
    private String password;
    private int userType;
    private String sex;
    //一下是学生用户 用的字段
    private String userClass;
    private String userMajor;
    private String registerTime;
    private String stuIcon;
    private int stuStatus;
    private String stuNickName;

    //一下是教师用户 用的字段
    private String teacherNickName;
    private String teacherIntro;
    private String teacherIcon;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserIcon() {
        return UserType.StudentType == userType ? stuIcon : UserType.TeacherType == userType ? teacherIcon : "";
    }

    public String getNickName() {
        return UserType.StudentType == userType ? stuNickName : UserType.TeacherType == userType ? teacherNickName : "";

    }


    @Override
    public String toString() {
        return MyUser.class.getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", userType=" + userType +
                ", userClass='" + userClass + '\'' +
                ", userMajor='" + userMajor + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", stuIcon='" + stuIcon + '\'' +
                ", stuStatus=" + stuStatus +
                ", stuNickName='" + stuNickName + '\'' +
                ", teacherNickName='" + teacherNickName + '\'' +
                ", teacherIntro='" + teacherIntro + '\'' +
                ", teacherIcon='" + teacherIcon + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getUserMajor() {
        return userMajor;
    }

    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getStuIcon() {
        return stuIcon;
    }

    public void setStuIcon(String stuIcon) {
        this.stuIcon = stuIcon;
    }

    public int getStuStatus() {
        return stuStatus;
    }

    public void setStuStatus(int stuStatus) {
        this.stuStatus = stuStatus;
    }

    public String getStuNickName() {
        return stuNickName;
    }

    public void setStuNickName(String stuNickName) {
        this.stuNickName = stuNickName;
    }

    public String getTeacherNickName() {
        return teacherNickName;
    }

    public void setTeacherNickName(String teacherNickName) {
        this.teacherNickName = teacherNickName;
    }

    public String getTeacherIntro() {
        return teacherIntro;
    }

    public void setTeacherIntro(String teacherIntro) {
        this.teacherIntro = teacherIntro;
    }

    public String getTeacherIcon() {
        return teacherIcon;
    }

    public void setTeacherIcon(String teacherIcon) {
        this.teacherIcon = teacherIcon;
    }
}
