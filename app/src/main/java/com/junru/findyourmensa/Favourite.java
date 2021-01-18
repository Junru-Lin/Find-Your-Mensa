package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_food")

public class Favourite {

    @NonNull
    @PrimaryKey
    public String description;

    public void setDesc(String Desc) {
        this.description = Desc;
    }

    @NonNull
    public String mensa;

    public void setTitle(String Title) { this.mensa = Title; }


    @NonNull
    public String price;

    public void setPrice(String Price) { this.price = Price; }

}
