package com.diplomska.emed.martin.e_medicine.models;

/**
 * Created by Martin on 8/28/2015.
 */
public class PillModel {

    private int rxcui;
    private String name;
    private String imgUrl;

    public PillModel() {
    }

    public PillModel(int rxcui, String name, String imgUrl) {
        this.rxcui = rxcui;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public int getRxcui() {
        return rxcui;
    }

    public void setRxcui(int rxcui) {
        this.rxcui = rxcui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
