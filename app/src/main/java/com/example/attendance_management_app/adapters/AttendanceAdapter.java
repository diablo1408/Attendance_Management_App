package com.example.attendance_management_app.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance_management_app.R;
import com.example.attendance_management_app.modals.AttendanceDetails;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.Viewholder> {

    private ArrayList<AttendanceDetails> attendanceDetailsList;
    private HashMap<String, String> studentNameMap;
    private clickListener onClickListener;


    public AttendanceAdapter(ArrayList<AttendanceDetails> attendanceDetailsList, HashMap<String, String> studentNameMap, clickListener onClickListener) {
        this.attendanceDetailsList = attendanceDetailsList;
        this.studentNameMap = studentNameMap;
        this.onClickListener = onClickListener;
    }

    public AttendanceAdapter(ArrayList<AttendanceDetails> attendanceDetailsList, HashMap<String, String> studentNameMap) {
        this.attendanceDetailsList = attendanceDetailsList;
        this.studentNameMap = studentNameMap;
    }

    public ArrayList<AttendanceDetails> getAttendanceDetailsList() {
        return attendanceDetailsList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_attend_item, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        final AttendanceDetails item=attendanceDetailsList.get(position);

        holder.studentId.setText(item.getStudent_Id());
        holder.studentName.setText(studentNameMap.get(item.getStudent_Id()));
        holder.Id.setText(String.valueOf(position + 1) + ". ");

        holder.isPresent.setChecked(item.isPresent());
        Log.d("bool", "" + item.isPresent());


        //  holder.isPresent.setEnabled(true);
        //    Log.d("check",""+position);

        holder.isPresent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                attendanceDetailsList.get(position).setPresent(b);
               //   onClickListener.onCheckboxClickListener(item,b);

            }
        });

//        else {
//            Log.d("check2",""+studentDetailsList[pos].getIsPresent());
//            boolean p=studentDetailsList[pos].getIsPresent();
//            if(p) {
//                holder.studentCv.setCardBackgroundColor(Color.parseColor("#4CAF50"));
//                holder.isPresent.setChecked(p);
//                holder.isPresent.setEnabled(false);
//            }
//            else {
//                holder.studentCv.setCardBackgroundColor(Color.parseColor("#D32F2F"));
//            }
//        }
        //
    }

    @Override
    public int getItemCount() {
        return attendanceDetailsList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder {

        TextView studentId;
        TextView Id;
        TextView studentName;
        MaterialCheckBox isPresent;
        CardView studentCv;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            studentId = itemView.findViewById(R.id.studIdTv);
            Id = itemView.findViewById(R.id.IdTv);
            studentName = itemView.findViewById(R.id.studNameTv);
            isPresent = itemView.findViewById(R.id.checkbox);
            studentCv = itemView.findViewById(R.id.studentDetailsCardView);
        }
    }

    public interface clickListener {
        void onCheckboxClickListener(AttendanceDetails item, boolean b);
    }


}
