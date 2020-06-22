package com.example.attendance_management_app.modals;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BatchDetails {

    @PrimaryKey @ColumnInfo(name = "id")
    private   int id;
    @ColumnInfo(name = "batchName")
   private String batchName;
    @ColumnInfo(name = "batchValue")
    private String batchValue;


    public BatchDetails(int id, String batchName, String batchValue) {
        this.id = id;
        this.batchName = batchName;
        this.batchValue = batchValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchValue() {
        return batchValue;
    }

    public void setBatchValue(String batchValue) {
        this.batchValue = batchValue;
    }
}
