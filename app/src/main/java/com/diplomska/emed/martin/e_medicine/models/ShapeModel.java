package com.diplomska.emed.martin.e_medicine.models;

/**
 * Created by Martin on 8/28/2015.
 */
public class ShapeModel {

    private String shape;
    private int image;

    public ShapeModel() {
    }

    public ShapeModel(String shape, int image) {
        this.shape = shape;
        this.image = image;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
