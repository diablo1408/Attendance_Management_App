package com.example.attendance_management_app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance_management_app.R;

import com.example.attendance_management_app.modals.LectureDetails;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LectureDetailsAdapter extends RecyclerView.Adapter<LectureDetailsAdapter.ViewHolder> {
    private ArrayList<LectureDetails> lectureDetailsList;
    private onClickListener clickListener;
    private Context context;

    public LectureDetailsAdapter(ArrayList<LectureDetails> lectureDetailsList, onClickListener clickListener, Context context) {
        this.lectureDetailsList = lectureDetailsList;
        this.clickListener = clickListener;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.classdetails_item_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LectureDetails item = lectureDetailsList.get(position);
        Log.d("id", ""+item.getId());
        holder.mLectureId.setText(String.valueOf(item.getId()));
        holder.mSection.setText(item.getBatchId());
        holder.mSubject.setText(item.getSubject());
        holder.mLectureDate.setText(item.getDate_time().substring(0, item.getDate_time().indexOf("_")));

        holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onEditBtnClickListener(item);
            }
        });
        holder.mCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onCopyBtnClickListener(item);
            }
        });
        holder.mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clickListener.onUploadBtnClickListener(item);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return lectureDetailsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mSection;
        TextView mSubject;
        TextView mLectureDate;
        TextView mLectureId;
        ImageButton mEditBtn;
        ImageButton mCopyBtn;
        ImageButton mUploadBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSection = itemView.findViewById(R.id.sectionTv);
            mSubject = itemView.findViewById(R.id.subjectTv);
            mLectureDate = itemView.findViewById(R.id.lectureDateTv);
            mLectureId=itemView.findViewById(R.id.lectureIdTv);
            mEditBtn = itemView.findViewById(R.id.lectureEditBtn);
            mCopyBtn = itemView.findViewById(R.id.lectureCopyBtn);
            mUploadBtn = itemView.findViewById(R.id.lectureUploadBtn);
            // mNoOfStudents=itemView.findViewById(R.id.studentCountTv);
            //  mAddAttendBtn=itemView.findViewById(R.id.addAttendBtn);
            //  mViewAttendBtn=itemView.findViewById(R.id.viewAttendBtn);
        }
    }

    public interface onClickListener {
        void onEditBtnClickListener(LectureDetails lectureDetails);

        void onCopyBtnClickListener(LectureDetails lectureDetails);

        void onUploadBtnClickListener(LectureDetails lectureDetails) throws ExecutionException, InterruptedException;
    }


}
