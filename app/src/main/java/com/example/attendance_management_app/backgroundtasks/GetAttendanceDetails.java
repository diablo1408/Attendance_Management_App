package com.example.attendance_management_app.backgroundtasks;

import android.os.AsyncTask;

import com.example.attendance_management_app.database.AttendanceDatabase;
import com.example.attendance_management_app.modals.AttendanceDetails;

import java.util.ArrayList;

public  class  GetAttendanceDetails extends AsyncTask<Void,Void,ArrayList<AttendanceDetails>> {
    ArrayList<AttendanceDetails> attendanceDetails=new ArrayList<>();
    AttendanceDatabase db;
    int lectureId;
    String flag;


    public GetAttendanceDetails( AttendanceDatabase db, int lectureId ){

        this.db = db;
        this.lectureId = lectureId;
    }

    @Override
    protected ArrayList<AttendanceDetails> doInBackground(Void... voids) {

        attendanceDetails= (ArrayList<AttendanceDetails>) db.attendanceDetailsDao().getAttendanceListStudent(lectureId);

        return attendanceDetails;
    }
}
