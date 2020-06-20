package com.example.attendance_management_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.attendance_management_app.modals.AttendanceDetails;
import com.example.attendance_management_app.modals.StudentDetails;

import java.util.List;

@Dao
public interface AttendanceDetailsDao {
    @Query("SELECT * FROM attendancedetails")
    List<AttendanceDetails> getAll();

 //  @Query("SELECT * FROM attendancedetails WHERE date_section==:lectureDate_section")
 //  List<AttendanceDetails> getStudentDetailsDateWise(String lectureDate_section);

 //  @Query("SELECT lecture_Date FROM attendancedetails WHERE section==:section AND subject==:subject")
//   List<String>  getLectureDateClassWise(String section,String subject);
   @Query("SELECT * FROM attendancedetails WHERE lecture_Id==:lectureId")
     List<AttendanceDetails> getAttendanceListStudent(int lectureId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AttendanceDetails... attendanceDetail);

    @Delete
    void delete(AttendanceDetails... attendanceDetail);
}
