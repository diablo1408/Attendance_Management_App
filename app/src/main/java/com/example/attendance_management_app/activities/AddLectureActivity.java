package com.example.attendance_management_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.attendance_management_app.JSONParser;
import com.example.attendance_management_app.MainActivity;
import com.example.attendance_management_app.R;
import com.example.attendance_management_app.backgroundtasks.getBatchDetails;
import com.example.attendance_management_app.database.AttendanceDatabase;
import com.example.attendance_management_app.modals.BatchDetails;
import com.example.attendance_management_app.modals.LectureDetails;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class AddLectureActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextInputEditText startTimeEd;
    TextInputEditText hrsCount;
    TextInputEditText lectureDateEd;
    TextInputEditText subjectName;
    MaterialButton nextBtn;
    String startTime;
    String lectureDate;
    String batchValue;
    int teacherId;
    int id;
    AttendanceDatabase db;
    List<BatchDetails> batchDetailsList = new ArrayList<>();
    Map<String, BatchDetails> batchDetailsMap = new HashMap<>();
    String flag = "";
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecture);
        startTimeEd = findViewById(R.id.startTimeEdit);
        hrsCount = findViewById(R.id.hrsCountEdit);
        lectureDateEd = findViewById(R.id.lectureDateEdit);
        nextBtn = findViewById(R.id.lectureNxtBtn);
        subjectName = findViewById(R.id.subjectNameEdit);
        db = AttendanceDatabase.getDatabase(this);
        final LectureDetails lectureDetails = (LectureDetails) getIntent().getSerializableExtra("classDetails");
        flag = getIntent().getStringExtra("flag");
        if (flag == null) {
            flag = "not needed";
        }
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        teacherId = pref.getInt("teacherId", 0);

        try {
            batchDetailsList = new getBatchDetails().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> batchNameList = new ArrayList<>();
        for (int i = 0; i < batchDetailsList.size(); i++) {
            String name = batchDetailsList.get(i).getBatch_Name();
            batchNameList.add(name);
        }
        for (BatchDetails b : batchDetailsList) {
            batchDetailsMap.put(b.getBatch_Name(), b);
        }


        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        batchNameList);

        AutoCompleteTextView editTextFilledExposedDropdown =
                findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setText("",false);
        if (flag.equals("edit")) {


            editTextFilledExposedDropdown.setText("Batch" + lectureDetails.getBatchId());
            subjectName.setText(lectureDetails.getSubject());
            startTimeEd.setText(lectureDetails.getStartTime());
            lectureDateEd.setText(lectureDetails.getDate_time().substring(0, lectureDetails.getDate_time().indexOf("_")));
            lectureDateEd.setFocusable(false);
            startTimeEd.setText(lectureDetails.getStartTime());
            hrsCount.setText(String.valueOf(lectureDetails.getLectureHrs()));
            nextBtn.setText("Save");

        }
        if(flag.equals("copy")){
            subjectName.setText(lectureDetails.getSubject());
            startTimeEd.setText(lectureDetails.getStartTime());
            lectureDateEd.setText(lectureDetails.getDate_time().substring(0, lectureDetails.getDate_time().indexOf("_")));

            startTimeEd.setText(lectureDetails.getStartTime());
            hrsCount.setText(String.valueOf(lectureDetails.getLectureHrs()));


        }
        startTimeEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showTimePickerDialog();
                }
            }
        });
        lectureDateEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickerDialog();
                }
            }
        });
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String batchName = (String) adapterView.getItemAtPosition(i);
                BatchDetails batchItem = batchDetailsMap.get(batchName);
                assert batchItem != null;
                batchValue = batchItem.getBatch_Value();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String subject = subjectName.getText().toString();
                final int hrs = Integer.parseInt(hrsCount.getText().toString());
                startTime = startTimeEd.getText().toString();

                //String letureDate=lectureDateEd.getText().toString();


                if (flag.equals("edit")) {
                    String batchValueTem = lectureDetails.getBatchId();
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            db.classDetailsDao().update(new LectureDetails(lectureDetails.getId(), lectureDetails.getBatchId(), subject, teacherId, lectureDetails.getDate_time(), hrs, startTime));
                            // Log.d("edit","successful");
                        }
                    });
                    Toast.makeText(getApplicationContext(), "Lecture Details Edited", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
                else if(flag.equals("copy")) {
                    if (subjectName.getText() != null && hrsCount.getText() != null && startTime != null&&batchValue!=null) {
                        Date curTime = Calendar.getInstance().getTime();
                        final String date_time = lectureDate+ "_" + curTime;
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                db.classDetailsDao().insert(new LectureDetails(batchValue, subject, teacherId, date_time, hrs, startTime));
                                id = db.classDetailsDao().getLectureId(date_time);
                            }
                        });
                        Intent i = new Intent(getApplicationContext(), AddAttendanceActivity.class);
                        i.putExtra("lectureId", id);
                        i.putExtra("batchValue", batchValue);
                        i.putExtra("dateTime", date_time);
                        startActivity(i);
                    }
                }
                else {
                    if (subjectName.getText() != null && hrsCount.getText() != null && lectureDate != null && startTime != null && batchValue != null) {
                        Date curTime = Calendar.getInstance().getTime();
                        final String date_time = lectureDate + "_" + curTime;
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                db.classDetailsDao().insert(new LectureDetails(batchValue, subject, teacherId, date_time, hrs, startTime));
                                id = db.classDetailsDao().getLectureId(date_time);
                                Intent i = new Intent(getApplicationContext(), AddAttendanceActivity.class);
                                i.putExtra("lectureId", id);
                                i.putExtra("batchValue", batchValue);
                                i.putExtra("dateTime", date_time);
                                startActivity(i);
                            }
                        });




                    }
                }
            }
        });

    }



    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void showTimePickerDialog() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        lectureDate = +dayOfMonth + "/" + month + "/" + year;
        lectureDateEd.setText(lectureDate);


    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hrOfDay, int min) {
        startTime = hrOfDay + ":" + min;
        startTimeEd.setText(startTime);
    }
}
