package com.diplomska.emed.martin.e_medicine.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Martin on 18-Jun-15.
 */
public class Contraindication implements Parcelable{

    private Long id;
    private Drug drug;
    private String contraindication;

    public Contraindication(){}
    public Contraindication(Long id,Drug drug,String contraindication){
        super();
        this.id=id;
        this.drug=drug;
        this.contraindication=contraindication;
    }
    public Contraindication(Parcel in){
        super();
        this.id=in.readLong();
        this.drug=in.readParcelable(Drug.class.getClassLoader());
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

    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeParcelable(getDrug(), flags);
        dest.writeString(getContraindication());
    }

    public static final Parcelable.Creator<Contraindication> CREATOR = new Parcelable.Creator<Contraindication>(){
        @Override
        public Contraindication createFromParcel(Parcel source) {
            return new Contraindication(source);
        }

        @Override
        public Contraindication[] newArray(int size) {
            return new Contraindication[size];
        }
    };
}
