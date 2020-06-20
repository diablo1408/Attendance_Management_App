package com.example.attendance_management_app.backgroundtasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.attendance_management_app.JSONParser;
import com.example.attendance_management_app.modals.AttendanceDetails;
import com.example.attendance_management_app.modals.LectureDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadAttendanceData extends AsyncTask<Void, Void, Void> {
    LectureDetails lectureDetails;
    ArrayList<AttendanceDetails> attendanceDetails;
    String teacherId;
    String teacherName;
    Context context;

    ProgressDialog  progressDialog;
    HashMap<String, String> paramsLectureDetails = new HashMap<>();
  //  HashMap<String, String> paramsAttendanceDetails = new HashMap<>();
    ArrayList<HashMap<String, String>> listOfStudents=new ArrayList<>();
     String Url = "http://hiddenmasterminds.com/web/index.php?r=jflipgradattendance/createlecture";
    String Url2 = "http://hiddenmasterminds.com/web/index.php?r=jflipgradattendance/createattendance";

    public UploadAttendanceData(LectureDetails lectureDetails,    ArrayList<  AttendanceDetails>  attendanceDetails, String teacherId, String teacherName, Context context) {
        this.lectureDetails = lectureDetails;
        this.attendanceDetails = attendanceDetails;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        showProgressDialog();
        paramsLectureDetails.put("id", String.valueOf(lectureDetails.getId()));
        paramsLectureDetails.put("batch_id", lectureDetails.getBatchId());
        paramsLectureDetails.put("subject", lectureDetails.getSubject());
        paramsLectureDetails.put("teacher_id", teacherId);
        paramsLectureDetails.put("teacher_name", teacherName);
        paramsLectureDetails.put("date_time", lectureDetails.getDate_time());
        paramsLectureDetails.put("no_of_hours", String.valueOf(lectureDetails.getLectureHrs()));
        paramsLectureDetails.put("start_time", lectureDetails.getStartTime());
        Log.d("attsize",""+attendanceDetails.size());
        for(int i=0;i<attendanceDetails.size();i++){
            HashMap<String, String> attendanceMap=new HashMap<>();
            AttendanceDetails item=attendanceDetails.get(i);
            attendanceMap.put("id", String.valueOf(item.getId()));
            attendanceMap.put("student_id", item.getStudent_Id());
            attendanceMap.put("lecture_id", String.valueOf(item.getLecture_Id()));
            attendanceMap.put("date_time", item.getDate_Time());
            attendanceMap.put("is_present", String.valueOf(item.isPresent()));
            listOfStudents.add(attendanceMap);

        }



    }

    @Override
    protected Void doInBackground(Void... voids) {
        JSONParser.makeHttpRequest(Url, paramsLectureDetails);
        for (int i=0;i<attendanceDetails.size();i++){
            Log.d("map","sdsdsdfsd");
            JSONParser.makeHttpRequest(Url2, listOfStudents.get(i));
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        hideProgressDialog();
        Toast.makeText(context, "upload successful", Toast.LENGTH_SHORT).show();
    }
    private void  showProgressDialog(){
       progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("It's uploading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
      //  progressDialog= ProgressDialog.show(context,"","Loading...Please wait...");


    }
    private   void hideProgressDialog(){
        progressDialog.dismiss();

    }

}
