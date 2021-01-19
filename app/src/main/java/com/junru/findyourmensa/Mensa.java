package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MensaOpenTime")

public class Mensa {
    //define fields in MensaOpenTime table

    protected String OpenTime;

    public void setTime(String open_time) {
        this.OpenTime = open_time;
    }

    public String getTime() {
        return OpenTime;
    }

    @PrimaryKey
    @NonNull
    protected String MensaName;

    public void setName(String mensa_name) {
        this.MensaName = mensa_name;
    }

    public String getName() {
        return MensaName;
    }

    protected String Allergenes;

}

