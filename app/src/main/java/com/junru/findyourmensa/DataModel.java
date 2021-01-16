package com.junru.findyourmensa;

public class DataModel {

    private String text;
    private String price;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public DataModel(String text, String price) {
        this.text = text;
        this.price = price;
    }
}