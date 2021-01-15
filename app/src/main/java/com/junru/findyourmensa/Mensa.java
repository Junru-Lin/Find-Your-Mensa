package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MensaOpenTime")

public class Mensa {

    //@NonNull @PrimaryKey
    //public int id;

    protected String OpenTime;

    @NonNull @PrimaryKey
    protected String MensaName;

    public String getName() {
        return MensaName;
    }

    public void setName(String mensa_name) {
        this.MensaName = mensa_name;
    }

    public String getTime() {
        return OpenTime;
    }

    public void setTime(String open_time) {
        this.OpenTime = open_time;
    }

}

