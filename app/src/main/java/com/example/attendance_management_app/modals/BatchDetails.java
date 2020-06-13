package com.example.attendance_management_app.modals;

public class BatchDetails {


    private   int id;
   private String batch_Name;
    private String batch_Value;
    public BatchDetails(int id, String batch_Name, String batch_Value) {
        this.id = id;
        this.batch_Name = batch_Name;
        this.batch_Value = batch_Value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatch_Name() {
        return batch_Name;
    }

    public void setBatch_Name(String batch_Name) {
        this.batch_Name = batch_Name;
    }

    public String getBatch_Value() {
        return batch_Value;
    }

    public void setBatch_Value(String batch_Value) {
        this.batch_Value = batch_Value;
    }
}
