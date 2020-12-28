package com.junru.findyourmensa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao

public interface ListDAO {
    @Insert
    public void insert(Mensa mensa);
    @Update
    public void update(Mensa mensa);

    @Query("SELECT * FROM mensa_table")
    public List<Mensa> getAllMensa();

    @Query("SELECT * FROM mensa_table WHERE Name = :mensa_name")
    public List<Mensa> getMensaInfoByName(String mensa_name);
}