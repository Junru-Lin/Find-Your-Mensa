package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MensaOpenTime")

public class Mensa {

    protected String OpenTime;

    @PrimaryKey
    @NonNull
    protected String MensaName;

    public String getName() {
        return MensaName;
    }

    public String getTime() {
        return OpenTime;
    }

    public void setName(String mensa_name) {
        this.MensaName = mensa_name;
    }
}

