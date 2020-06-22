package com.example.attendance_management_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.attendance_management_app.modals.AttendanceDetails;
import com.example.attendance_management_app.modals.BatchDetails;

import java.util.List;

@Dao
public interface BatchDetailsDao {
    @Query("SELECT * from batchdetails" )
    List<BatchDetails> getBatchDetailsList();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BatchDetails... batchDetails);

    @Delete
    void delete(BatchDetails... attendanceDetail);
}
