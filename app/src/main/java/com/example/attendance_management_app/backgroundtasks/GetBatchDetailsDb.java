package com.example.attendance_management_app.backgroundtasks;

import android.os.AsyncTask;

import com.example.attendance_management_app.database.AttendanceDatabase;
import com.example.attendance_management_app.modals.BatchDetails;

import java.util.ArrayList;

public class GetBatchDetailsDb extends AsyncTask<Void,Void, ArrayList<BatchDetails>> {
    ArrayList<BatchDetails> batchDetailsList=new ArrayList<>();
    AttendanceDatabase db;

    public GetBatchDetailsDb(AttendanceDatabase db) {
        this.db = db;
    }

    @Override
    protected ArrayList<BatchDetails> doInBackground(Void... voids) {
      batchDetailsList= (ArrayList<BatchDetails>) db.batchDetailsDao().getBatchDetailsList();
        return batchDetailsList;
    }
}
