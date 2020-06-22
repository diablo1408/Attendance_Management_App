package com.example.attendance_management_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.attendance_management_app.modals.AttendanceDetails;
import com.example.attendance_management_app.modals.BatchDetails;
import com.example.attendance_management_app.modals.LectureDetails;
import com.example.attendance_management_app.modals.StudentDetails;

@Database(entities = {LectureDetails.class, StudentDetails.class, AttendanceDetails.class, BatchDetails.class},version = 1,exportSchema = false)

public abstract class AttendanceDatabase extends RoomDatabase {
    public abstract LectureDetailsDao lectureDetailsDao();
    public abstract StudentDetailsDao studentDetailsDao();
    public abstract AttendanceDetailsDao attendanceDetailsDao();
    public abstract BatchDetailsDao batchDetailsDao();

    private static volatile AttendanceDatabase INSTANCE;

  public static AttendanceDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AttendanceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AttendanceDatabase.class, "classDetails_database").build();

                }
            }
        }
        return INSTANCE;
    }
}
