package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by Lamine MESSACI on 15/04/2020.
 */
@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM Task WHERE id = :id")
    LiveData<Task> getTask(long id);

    @Insert
    long insertTask(Task task);

    @Query("DELETE FROM Task WHERE id = :id")
    int deleteTask(long id);
    @Delete
    void deleteTask(Task task);


}
