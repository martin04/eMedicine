package com.diplomska.emed.martin.e_medicine.models;

/**
 * Created by Martin on 8/25/2015.
 */
public class ColorModel {
    private String name;
    private String colorHex;

    public ColorModel() {
    }

    public ColorModel(String name, String colorHex) {

        this.name = name;
        this.colorHex = colorHex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
