package com.junru.findyourmensa;

//DataModel for Recycleview

public class DataModel {

    private String text;
    private String price;
    private String mensaName;
    private String allergenes;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrice() {
        return price;
    }

    public String getMensaName() {return mensaName;}

    public String getAllergenes() {return allergenes;}

    public DataModel(String text, String price, String mensaName, String allergenes) {
        this.text = text;
        this.price = price;
        this.mensaName = mensaName;
        this.allergenes = allergenes;
    }
}