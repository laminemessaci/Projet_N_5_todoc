package com.cleanup.todoc.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import static com.cleanup.todoc.model.Project.getAllProjects;


/**
 * Created by Lamine MESSACI on 15/04/2020.
 */

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile TodocDatabase INSTANCE;
    private static String DBNAME = "todoc";

    // --- DAO ---
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // --- INSTANCE ---
    public static synchronized TodocDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized(TodocDatabase.class) {
                if(INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, DBNAME)
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Project[] allProjects = getAllProjects();

                for (Project element: allProjects) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", element.getId());
                    contentValues.put("name", element.getName());
                    contentValues.put("color", element.getColor());
                    db.insert("Project", OnConflictStrategy.IGNORE, contentValues);

                }
            }
        };
    }
}