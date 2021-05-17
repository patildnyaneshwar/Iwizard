package com.example.assignmentiwizards.repository.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.assignmentiwizards.repository.model.JobsModel;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 07:26 PM
 * */
@Database(entities = {JobsModel.class}, version = 1)
public abstract class JobsDatabase extends RoomDatabase {
    private static final String DB_NAME = "jobs_database";
    private static JobsDatabase instance;

    public abstract JobsDao dbDao();

    public static JobsDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (JobsDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, JobsDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
