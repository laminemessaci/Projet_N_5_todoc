package com.cleanup.todoc;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Lamine MESSACI on 21/04/2020.
 */
public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    // EXECUTOR
    private final Executor executor;
    // PROJECTS
    private LiveData<List<Project>> projects;


    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    // -------------
    // FOR PROJECT
    // -------------
    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    // -------------
    // FOR TASK
    // -------------
    public LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
    }

    public void createTask(final Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(final Task task) {
        executor.execute(() -> taskDataSource.deleteTask(task));
    }


    public void init() {
        if (projects == null) {
            projects = projectDataSource.getProjects();
        }
    }

}
