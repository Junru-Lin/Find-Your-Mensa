package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mensa_table")

public class Mensa {
    @PrimaryKey
    @NonNull
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String mensa_name) {
        this.Name = mensa_name;
    }
}

