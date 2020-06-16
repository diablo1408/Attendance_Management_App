package com.example.attendance_management_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.attendance_management_app.JSONParser;
import com.example.attendance_management_app.MainActivity;
import com.example.attendance_management_app.database.AttendanceDatabase;
import com.example.attendance_management_app.R;
import com.example.attendance_management_app.adapters.AttendanceAdapter;
import com.example.attendance_management_app.modals.AttendanceDetails;
import com.example.attendance_management_app.modals.BatchDetails;
import com.example.attendance_management_app.modals.StudentDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class AddAttendanceActivity extends AppCompatActivity  {
    ArrayList<StudentDetails> studentDetailsList = new ArrayList<>();
    ArrayList<AttendanceDetails> attendanceDetailsList= new ArrayList<>();
    HashMap<String, String> studentNameMap = new HashMap<>();
    AttendanceAdapter attendanceAdapter;

    static final String Url = "http://hiddenmasterminds.com/web/index.php?r=jflipgradattendance/getstudentbybatch";
    static String batchValue;
    static int lectureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);
        batchValue = getIntent().getStringExtra("batchValue");
        lectureId = getIntent().getIntExtra("lectureId", 0);
        final String date_time = getIntent().getStringExtra("date_time");
        final AttendanceDatabase db = AttendanceDatabase.getDatabase(this);
        FloatingActionButton addStudentAttendFab = findViewById(R.id.addStudentAttendFab);

        try {
            studentDetailsList = new getStudentDetails().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < studentDetailsList.size(); i++) {
           attendanceDetailsList.add( new AttendanceDetails(studentDetailsList.get(i).getRollNum(), lectureId, date_time, false));
        }
        for (int i = 0; i < studentDetailsList.size(); i++) {
            studentNameMap.put(studentDetailsList.get(i).getRollNum(), studentDetailsList.get(i).getFullName());
        }

        setStudentDetailsList();


        addStudentAttendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final ArrayList<AttendanceDetails> changedAttendanceDetailsList=attendanceAdapter.getAttendanceDetailsList();
                Log.d("stud",""+changedAttendanceDetailsList.get(0).isPresent());
                for (int i = 0; i < studentDetailsList.size(); i++) {
                    final int finalI = i;
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            db.AttendanceDetailsDao().insert(changedAttendanceDetailsList.get(finalI));
                            db.StudentDetailsDao().insert(studentDetailsList.get(finalI));
                            Log.d("db1", "success");
                        }
                    });

                }
                Toast.makeText(getApplicationContext(), "Attendance Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }
        });
    }


    static class getStudentDetails extends AsyncTask<Void, Void, ArrayList<StudentDetails>> {
        ArrayList<StudentDetails> mStudentDetailsList = new ArrayList<>();

        @Override
        protected ArrayList<StudentDetails> doInBackground(Void... voids) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("institute_id", "100003");
            params.put("batch_value", batchValue);
            String res = JSONParser.makeHttpRequest(Url, params);
            try {
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    String rollNo = jsonArray.getJSONObject(i).getString("roll_no");
                    int id = jsonArray.getJSONObject(i).getInt("user_id");
                    String studentName = jsonArray.getJSONObject(i).getString("first_name");
                    mStudentDetailsList.add(new StudentDetails(id, studentName, rollNo));

                }
                Log.d("res2", "" + jsonArray.length());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mStudentDetailsList;
        }
    }

    void setStudentDetailsList() {
        //  StudentDetails [] studentDetailsarr= studentDetailsList.toArray(new StudentDetails[studentDetailsList.size()]);


        RecyclerView classDetailsRv = findViewById(R.id.addStudentAttendRv);
        //  Log.d("lsit", "" + classDetailsList.size());
        attendanceAdapter = new AttendanceAdapter(attendanceDetailsList, studentNameMap);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        classDetailsRv.setLayoutManager(llm);
        classDetailsRv.setAdapter(attendanceAdapter);
    }
//    @Override
//    public void onCheckboxClickListener(AttendanceDetails attendanceDetails, boolean b) {
//        int pos=attendanceDetailsList.indexOf(attendanceDetails);
//        attendanceDetails.setPresent(b);
//      attendanceDetailsList.remove(pos);
//      attendanceDetailsList.add(attendanceDetails);
//      attendanceAdapter.notifyDataSetChanged();
//
//    }
}
