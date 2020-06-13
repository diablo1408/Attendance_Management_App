package com.example.attendance_management_app.modals;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;



import java.util.ArrayList;
@Entity
public class AttendanceDetails {
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")
 private  int id;
    @ColumnInfo(name = "student_Id")
 private  String student_Id;
 @ColumnInfo(name = "lecture_Id")
 private  int lecture_Id;
 @ColumnInfo(name ="date_Time" )
 private  String date_Time;
 @ColumnInfo(name ="isPresent" )
 private  boolean isPresent;

    public AttendanceDetails(String student_Id, int lecture_Id, String date_Time, boolean isPresent) {
        this.student_Id = student_Id;
        this.lecture_Id = lecture_Id;
        this.date_Time = date_Time;
        this.isPresent = isPresent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(String student_Id) {
        this.student_Id = student_Id;
    }

    public int getLecture_Id() {
        return lecture_Id;
    }

    public void setLecture_Id(int lecture_Id) {
        this.lecture_Id = lecture_Id;
    }

    public String getDate_Time() {
        return date_Time;
    }

    public void setDate_Time(String date_Time) {
        this.date_Time = date_Time;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
