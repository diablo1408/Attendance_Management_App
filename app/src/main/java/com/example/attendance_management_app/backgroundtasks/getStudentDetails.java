package com.example.attendance_management_app.backgroundtasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.attendance_management_app.JSONParser;
import com.example.attendance_management_app.modals.StudentDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class getStudentDetails extends AsyncTask<Void, Void, ArrayList<StudentDetails>> {
  private   ArrayList<StudentDetails> mStudentDetailsList = new ArrayList<>();
     final String Url = "http://hiddenmasterminds.com/web/index.php?r=jflipgradattendance/getstudentbybatch";
     String batchValue;
   private   Context context;
   ProgressDialog  progressDialog;


    public getStudentDetails(String batchValue, Context context) {
        this.batchValue = batchValue;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

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

    @Override
    protected void onPostExecute(ArrayList<StudentDetails> studentDetailsArrayList) {
        super.onPostExecute(studentDetailsArrayList);
        hideProgressDialog();

    }

   private void  showProgressDialog(){
//       progressDialog = new ProgressDialog(context);
//        progressDialog.setMax(100);
//        progressDialog.setMessage("Its loading....");
//        progressDialog.setTitle("ProgressDialog bar example");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
       progressDialog=ProgressDialog.show(context,"","Loading...Please wait...");


    }
  private   void hideProgressDialog(){
        progressDialog.dismiss();

    }
}
