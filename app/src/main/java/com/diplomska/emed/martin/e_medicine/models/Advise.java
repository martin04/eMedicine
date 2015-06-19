package com.diplomska.emed.martin.e_medicine.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Martin on 18-Jun-15.
 */
public class Advise implements Parcelable {

    private Long id;
    private Drug drug;
    private String advise;

    public Advise() { }

    public Advise(Drug drug, String advise) {
        super();
        this.drug = drug;
        this.advise = advise;
    }

    public Advise(Parcel in){
        super();
        this.id=in.readLong();
        this.drug=in.readParcelable(Drug.class.getClassLoader());
        this.advise=in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeParcelable(getDrug(), flags);
        dest.writeString(getAdvise());
    }

    public static final Parcelable.Creator<Advise> CREATOR = new Parcelable.Creator<Advise>(){
        @Override
        public Advise createFromParcel(Parcel source) {
            return new Advise(source);
        }

        @Override
        public Advise[] newArray(int size) {
            return new Advise[size];
        }
    };
}
