package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_food")

public class Favourite {

    @NonNull
    @PrimaryKey
    public int id;

    @NonNull
    public String description;

    @NonNull
    public String mensa;

    @NonNull
    public int mensa_id;

}
