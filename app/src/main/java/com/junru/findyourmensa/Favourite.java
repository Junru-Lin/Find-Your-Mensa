package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_food")

public class Favourite {
    //define fields in favourite_food table

    @NonNull
    @PrimaryKey
    protected String description;

    public void setDesc(String Desc) {
        this.description = Desc;
    }

    @NonNull
    protected String mensa;

    public void setTitle(String Title) { this.mensa = Title; }

    @NonNull
    protected String price;

    public void setPrice(String Price) { this.price = Price; }


    protected String allergenes;

    public void setAller(String Aller) { this.allergenes = Aller; }


    public String getDesc() {
        return description;
    }

    public String getTitle() {
        return mensa;
    }

    public String getPrice() {return price;}

    public String getAller() {return allergenes;}

}
