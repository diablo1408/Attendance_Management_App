package com.example.attendance_management_app.modals;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "LectureDetails")
public class LectureDetails implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
     private    int id;
    @ColumnInfo(name = "batchId")
    private String batchId;
    @ColumnInfo(name = "subject")
    private String subject;
    @ColumnInfo(name = "teacherId")
    private int teacherId;
    @ColumnInfo(name = "date_time")
    private   String date_time;
    @ColumnInfo(name = "lectureHrs")
     private   int lectureHrs;
    @ColumnInfo(name = "startTime")
    private   String startTime;

    public LectureDetails(String batchId, String subject, int teacherId, String date_time, int lectureHrs, String startTime) {
        this.batchId = batchId;
        this.subject = subject;
        this.teacherId = teacherId;
        this.date_time = date_time;
        this.lectureHrs = lectureHrs;
        this.startTime = startTime;
    }
      @Ignore
    public LectureDetails(int id, String batchId, String subject, int teacherId, String date_time, int lectureHrs, String startTime) {
        this.id = id;
        this.batchId = batchId;
        this.subject = subject;
        this.teacherId = teacherId;
        this.date_time = date_time;
        this.lectureHrs = lectureHrs;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getLectureHrs() {
        return lectureHrs;
    }

    public void setLectureHrs(int lectureHrs) {
        this.lectureHrs = lectureHrs;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
