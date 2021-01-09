package com.junru.findyourmensa;

public class DataModel {

    private String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /** public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
     **/

    public DataModel(String text) { //, int imgid)
        this.text = text;
        //this.imgid = imgid;
    }
}