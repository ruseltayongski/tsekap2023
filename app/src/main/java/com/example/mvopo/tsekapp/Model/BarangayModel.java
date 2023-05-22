package com.example.mvopo.tsekapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BarangayModel implements Parcelable {
    public String id, name, prov_id, muncity_id;

    public BarangayModel (String id, String name, String prov_id, String muncity_id){
        this.id = id;
        this.name = name;
        this.prov_id = prov_id;
        this.muncity_id = muncity_id;
    }

    protected BarangayModel(Parcel in){
        id = in.readString();
        name = in.readString();
        prov_id = in.readString();
        muncity_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(prov_id);
        dest.writeString(muncity_id);
    }

    public static final Parcelable.Creator<BarangayModel> CREATOR = new Parcelable.Creator<BarangayModel>() {
        @Override
        public BarangayModel createFromParcel(Parcel in) {
            return new BarangayModel(in);
        }

        @Override
        public BarangayModel[] newArray(int size) {
            return new BarangayModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
