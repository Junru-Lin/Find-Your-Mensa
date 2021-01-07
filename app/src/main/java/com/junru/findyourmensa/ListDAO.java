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

    @Query("SELECT MensaName FROM MensaOpenTime")
    public List<Mensa> getAllMensa();

    @Query("SELECT * FROM MensaOpenTime WHERE MensaName = :mensa_name")
    public List<Mensa> getMensaInfoByName(String mensa_name);

    @Query("SELECT * FROM MensaOpenTime WHERE MensaName LIKE '%' || :mensaName || '%' ")
    public List<Mensa> getSearchResult(String mensaName);

}