package com.geek.taskapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.geek.taskapp.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT*FROM task")
    List<Task> getAll();

    @Query("SELECT * from task order by title asc")
    List<Task> getSortedList();

    @Insert
    void insert(Task model);

    @Query("SELECT * FROM task WHERE id=:position")
    Task getTaskModel(long position);

    @Query("UPDATE task set title=:title where id=:id ")
    void updateTitle(String title, long id);

    @Query("delete from task where id=:id")
    void delete(long id);
}
