package com.example.mvopo.tsekapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProvinceModel implements Parcelable {

    public String id, name;

    public ProvinceModel(String id, String name){
        this.id = id;
        this.name = name;
    }

    protected ProvinceModel(Parcel in){
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Creator<ProvinceModel> CREATOR = new Creator<ProvinceModel>() {
        @Override
        public ProvinceModel createFromParcel(Parcel in) {
            return new ProvinceModel(in);
        }

        @Override
        public ProvinceModel[] newArray(int size) {
            return new ProvinceModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
