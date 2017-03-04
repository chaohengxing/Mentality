package com.agjsj.mentality.bean.teacher;

import com.agjsj.mentality.bean.user.MyUser;
import com.agjsj.mentality.bean.user.User;

import java.util.List;

/**
 * 教师的基本信息
 * Created by HengXing on 2016/11/29.
 */
public class TeacherInfo extends User {

    private String teacherNickName;
    private int userType;
    private String teacherIntro;
    private String teacherIcon;
    private String sex;
    private List<DiscussTeacher> discussTeachers;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return TeacherInfo.class.getSimpleName() + "{" +
                ", teacherNickName='" + teacherNickName + '\'' +
                ", userType=" + userType +
                ", teacherIntro='" + teacherIntro + '\'' +
                ", teacherIcon='" + teacherIcon + '\'' +
                '}';
    }

    public List<DiscussTeacher> getDiscussTeachers() {
        return discussTeachers;

    }

    public void setDiscussTeachers(List<DiscussTeacher> discussTeachers) {
        this.discussTeachers = discussTeachers;
    }

    public String getTeacherNickName() {
        return teacherNickName;
    }

    public void setTeacherNickName(String teacherNickName) {
        this.teacherNickName = teacherNickName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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
