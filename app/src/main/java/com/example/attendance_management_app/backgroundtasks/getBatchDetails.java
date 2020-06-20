package com.example.attendance_management_app.backgroundtasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.attendance_management_app.JSONParser;
import com.example.attendance_management_app.modals.BatchDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class getBatchDetails extends AsyncTask<Void, Void, List<BatchDetails>> {
    List<BatchDetails> mBatchDetailsList = new ArrayList<>();
  final String Url = "http://hiddenmasterminds.com/web/index.php?r=jflipgradattendance/getbatchbyinstitute";


    @Override
    protected List<BatchDetails> doInBackground(Void... voids) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("institute_id", "100003");
        String res = JSONParser.makeHttpRequest(Url, params);
        try {
            JSONObject jsonObject = new JSONObject(res);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                int id = jsonArray.getJSONObject(i).getInt("id");
                String name = jsonArray.getJSONObject(i).getString("name");
                String value = jsonArray.getJSONObject(i).getString("value");
                mBatchDetailsList.add(new BatchDetails(id, name, value));

            }
            Log.d("res", "" + jsonArray.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return mBatchDetailsList;

    }
}
