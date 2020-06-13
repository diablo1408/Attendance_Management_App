package com.example.attendance_management_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.attendance_management_app.modals.StudentDetails;

import java.util.List;
@Dao
public interface StudentDetailsDao {
   @Query("SELECT * FROM studentdetails")
   List<StudentDetails> getAll();

//    @Query("SELECT * FROM studentdetails WHERE section == :classSection")
 //   List<StudentDetails> getStudentsDetailsSectionWise(String classSection);




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(StudentDetails... studentDetails);

    @Delete
    void delete(StudentDetails... studentDetails);
}
