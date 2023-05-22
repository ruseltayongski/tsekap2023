package com.example.mvopo.tsekapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MuncityModel implements Parcelable {

    public String id, name, prov_id;

    public MuncityModel (String id, String name, String prov_id){
        this.id = id;
        this.name = name;
        this.prov_id = prov_id;
    }

    protected MuncityModel(Parcel in){
        id = in.readString();
        name = in.readString();
        prov_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(prov_id);
    }

    public static final Parcelable.Creator<MuncityModel> CREATOR = new Parcelable.Creator<MuncityModel>() {
        @Override
        public MuncityModel createFromParcel(Parcel in) {
            return new MuncityModel(in);
        }

        @Override
        public MuncityModel[] newArray(int size) {
            return new MuncityModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
