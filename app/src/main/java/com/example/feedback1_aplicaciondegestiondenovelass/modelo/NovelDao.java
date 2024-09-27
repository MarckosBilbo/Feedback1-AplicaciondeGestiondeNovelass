package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;



@Dao
public interface NovelDao {
    @Insert
    void insert(Novel novel);

    @Delete
    void delete(Novel novel);

    @Update
    void update(Novel novel);

    @Query("SELECT * FROM novel_table ORDER BY title ASC")
    LiveData<List<Novel>> getAllNovels();
}
