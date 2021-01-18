package com.junru.findyourmensa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;
import java.util.List;

@Dao
public interface FavouritesDAO {

    @Insert
    public void insert(Favourite favourite);

    @Update
    public void update(Favourite favourite);

    @Delete
    public void delete(Favourite favourite);

    @Query("SELECT * FROM favourite_food")
    public List<Favourite> getAllFavourite();

}
