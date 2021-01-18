package com.junru.findyourmensa;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Mensa.class, Favourite.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ListDAO getListDAO();

    public abstract FavouritesDAO getFavouritesDAO();
}

