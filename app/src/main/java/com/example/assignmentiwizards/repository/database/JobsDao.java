package com.example.assignmentiwizards.repository.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.assignmentiwizards.repository.model.JobsModel;
import java.util.List;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 07:32 PM
 * */
@Dao
public interface JobsDao {

    @Query("SELECT * FROM jobs_table")
    List<JobsModel> getAllJobs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<JobsModel> mainModels);

    @Update
    void update(JobsModel mainModel);
}
