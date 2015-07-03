package com.diplomska.emed.martin.e_medicine.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Martin on 18-Jun-15.
 */
public class Drug implements Parcelable {

    private Long id;
    private String code;
    private String latin_name;
    private String generic_name;
    private String color;
    private String shape;

    public Drug() {
    }

    public Drug(String code, String latin_name, String generic_name, String color, String shape) {
        super();
        this.code = code;
        this.latin_name = latin_name;
        this.generic_name = generic_name;
        this.color = color;
        this.shape = shape;
    }

    //za da moze da se rekreira objektot
    public Drug(Parcel in) {
        super();
        this.code = in.readString();
        this.latin_name = in.readString();
        this.generic_name = in.readString();
        this.color = in.readString();
        this.shape = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatin_name() {
        return latin_name;
    }

    public void setLatin_name(String latin_name) {
        this.latin_name = latin_name;
    }

    public String getGeneric_name() {
        return generic_name;
    }

    public void setGeneric_name(String generic_name) {
        this.generic_name = generic_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getCode());
        dest.writeString(getLatin_name());
        dest.writeString(getGeneric_name());
        dest.writeString(getColor());
        dest.writeString(getShape());
    }

    //potrebna za da mozi da se kreiraat instanci od klasata
    public static final Parcelable.Creator<Drug> CREATOR = new Parcelable.Creator<Drug>() {
        @Override
        public Drug createFromParcel(Parcel source) {
            return new Drug(source);
        }

        @Override
        public Drug[] newArray(int size) {
            return new Drug[size];
        }
    };
}
