package com.example.attendance_management_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.attendance_management_app.modals.LectureDetails;

import java.util.List;


@Dao
public interface LectureDetailsDao {
   @Query("SELECT * FROM lecturedetails ")
 List<LectureDetails> getAll();
   @Update
   void update(LectureDetails... lectureDetails);




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LectureDetails... classes);

    @Delete
    void delete(LectureDetails... classDetail);

    @Query("SELECT id FROM lecturedetails WHERE date_time=:date_time")
    int getLectureId(String date_time);

}
