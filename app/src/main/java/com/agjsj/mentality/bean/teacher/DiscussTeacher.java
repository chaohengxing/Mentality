package com.agjsj.mentality.bean.teacher;

import com.agjsj.mentality.bean.student.StudentInfo;

import java.util.List;

public class DiscussTeacher {

    private String id;
    private String stuId;
    private StudentInfo studentInfo;
    private String teacherId;
    private String discussInfo;
    private String discussTime;
    private int discussLevel;

    public DiscussTeacher(){

    }

    /**
     * 发送咨询师评论
     * @param stuId
     * @param teacherId
     * @param discussInfo
     */
    public DiscussTeacher(String stuId, String teacherId, String discussInfo) {
        this.stuId = stuId;
        this.teacherId = teacherId;
        this.discussInfo = discussInfo;
    }

    private List<ReplayDiscussTeacher> replayDiscussTeachers;

    @Override
    public String toString() {
        return DiscussTeacher.class.getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", stuId='" + stuId + '\'' +
                ", studentInfo=" + studentInfo +
                ", teacherId='" + teacherId + '\'' +
                ", discussInfo='" + discussInfo + '\'' +
                ", discussTime='" + discussTime + '\'' +
                ", discussLevel=" + discussLevel +
                '}';
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }


    public List<ReplayDiscussTeacher> getReplayDiscussTeachers() {
        return replayDiscussTeachers;
    }

    public void setReplayDiscussTeachers(
            List<ReplayDiscussTeacher> replayDiscussTeachers) {
        this.replayDiscussTeachers = replayDiscussTeachers;
    }

    public String getId() {
        return id;
    }

    public void setId(String discussId) {
        this.id = discussId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getDiscussInfo() {
        return discussInfo;
    }

    public void setDiscussInfo(String discussInfo) {
        this.discussInfo = discussInfo;
    }

    public String getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(String discussTime) {
        this.discussTime = discussTime;
    }

    public int getDiscussLevel() {
        return discussLevel;
    }

    public void setDiscussLevel(int discussLevel) {
        this.discussLevel = discussLevel;
    }

}
