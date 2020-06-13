package com.example.attendance_management_app.modals;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class StudentDetails {
    @PrimaryKey @ColumnInfo(name = "id")
    private   int id;
    @ColumnInfo(name = "full_name")
    private    String fullName;
    @ColumnInfo(name = "roll_No.")
    private  String rollNum;

    public StudentDetails(int id, String fullName, String rollNum) {
        this.id = id;
        this.fullName = fullName;
        this.rollNum = rollNum;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRollNum() {
        return rollNum;
    }

    public void setRollNum(String rollNum) {
        this.rollNum = rollNum;
    }


}
