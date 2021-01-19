package com.junru.findyourmensa;

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

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMensaName() {return mensaName;}

    public String getAllergenes() {return allergenes;}

    public DataModel(String text, String price, String mensaName) {
        this.text = text;
        this.price = price;
        this.mensaName = mensaName;
        this.allergenes = allergenes;
    }
}