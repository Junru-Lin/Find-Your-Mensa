package com.junru.findyourmensa;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Mensa.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ListDAO getListDAO();
}
