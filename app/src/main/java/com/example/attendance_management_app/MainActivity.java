package com.example.attendance_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.attendance_management_app.activities.AddLectureActivity;
import com.example.attendance_management_app.adapters.LectureDetailsAdapter;
import com.example.attendance_management_app.database.AttendanceDatabase;
import com.example.attendance_management_app.modals.LectureDetails;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LectureDetailsAdapter.onClickListener {
    ArrayList<LectureDetails> lectureDetailsList = new ArrayList<>();
    BottomSheetBehavior bottomSheetBehavior;
    TextView lectureDateTv;
    String lectureDate;
    int teacherId;
    String teacherName;
    LectureDetails lectureDetails;
    TextView tv;
    AttendanceDatabase db;
    View promptsView;
    TextInputEditText teacherIdEd;
    TextInputEditText teacherNameEd;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    static String Url = "http://hiddenmasterminds.com/web/index.php?r=jflipgradattendance/createlecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AttendanceDatabase.getDatabase(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        tv = findViewById(R.id.textView);
        lectureDateTv = findViewById(R.id.lectureDateTxt);

        FloatingActionButton addLectureFab = findViewById(R.id.menu_item2);
        MaterialButton nextBtn = findViewById(R.id.nextBtn);

        String teacherId = String.valueOf(pref.getInt("teacherId", 0));
        String teacherName = pref.getString("teacherName", "no value");

        addLectureFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddLectureActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            LayoutInflater li = LayoutInflater.from(this);
            promptsView = li.inflate(R.layout.prompt, null);
            Log.d("menu", "clicked");
            teacherIdEd = promptsView.findViewById(R.id.teacherIdEd);
            teacherNameEd = promptsView.findViewById(R.id.teacherNameEd);
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);


            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (teacherIdEd.getText() != null && teacherNameEd.getText() != null) {
                                teacherId = Integer.parseInt(teacherIdEd.getText().toString());
                                teacherName = teacherNameEd.getText().toString();
                                editor.putString("teacherName", teacherName); // Storing string
                                editor.putInt("teacherId", teacherId); // Storing integer
                                editor.commit();

                            }


                        }
                    })


                    .setCancelable(false);
            AlertDialog alertDialog = alertDialogBuilder.create();


            alertDialog.show();


        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                lectureDetailsList = (ArrayList<LectureDetails>) db.classDetailsDao().getAll();
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                if (lectureDetailsList.isEmpty()) {

                                    tv.setVisibility(View.VISIBLE);
                                } else {
                                    tv.setVisibility(View.INVISIBLE);
                                    setClassDetailsList();
                                }
                            }
                        });


                //   Log.d("db4", ""+ lectureDetailsList.size());
            }

        });
    }

    void setClassDetailsList() {
        RecyclerView classDetailsRv = findViewById(R.id.classDetailsRv);
        LectureDetailsAdapter lectureDetailsAdapter = new
                LectureDetailsAdapter(lectureDetailsList, this, getApplicationContext());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        classDetailsRv.setLayoutManager(llm);
        classDetailsRv.setAdapter(lectureDetailsAdapter);
    }


    @Override
    public void onEditBtnClickListener(LectureDetails lectureDetails) {
        Log.v("editclick", "clicked");
        Intent i = new Intent(getApplicationContext(), AddLectureActivity.class);
        i.putExtra("classDetails", lectureDetails);
        i.putExtra("flag", "edit");
        startActivity(i);

    }

    @Override
    public void onCopyBtnClickListener(LectureDetails lectureDetails) {
        Intent i = new Intent(getApplicationContext(), AddLectureActivity.class);
        i.putExtra("classDetails", lectureDetails);
        i.putExtra("flag", "copy");
        startActivity(i);

    }

    @Override
    public void onUploadBtnClickListener(LectureDetails lectureDetails) {
        new UploadData(lectureDetails,
                String.valueOf(pref.getInt("teacherId", -1)),
                pref.getString("teacherName", "no value"),
                this
        ).execute();

    }

    static class UploadData extends AsyncTask<Void, Void, Void> {
        LectureDetails lectureDetails;
        String teacherId;
        String teacherName;
        Context context;
        HashMap<String, String> params = new HashMap<>();

        public UploadData(LectureDetails lectureDetails, String teacherId, String teacherName, Context context) {
            this.lectureDetails = lectureDetails;
            this.teacherId = teacherId;
            this.teacherName = teacherName;
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            params.put("ID", String.valueOf(lectureDetails.getId()));
            params.put("Batch_ID", lectureDetails.getBatchId());
            params.put("Subject", lectureDetails.getSubject());
            params.put("Teacher_ID", teacherId);
            params.put("Teacher_Name", teacherName);
            params.put("Date__Time", lectureDetails.getDate_time());
            params.put("No_Of_Hours", String.valueOf(lectureDetails.getLectureHrs()));
            params.put("Start_Time", lectureDetails.getStartTime());


        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONParser.makeHttpRequest(Url, params);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "upload successful", Toast.LENGTH_SHORT).show();
        }
    }


}
