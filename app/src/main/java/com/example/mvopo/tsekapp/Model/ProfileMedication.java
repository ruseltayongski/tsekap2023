package com.example.mvopo.tsekapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileMedication implements Parcelable {

    public String id, uniqueId, type, status, remarks,medication_status;

    public ProfileMedication(String id, String uniqueId, String type, String medication_status, String remarks, String status){
        this.id = id;
        this.uniqueId = uniqueId;
        this.type = type;
        this.medication_status = medication_status;
        this.remarks = remarks;
        this.status = status;
    }

    protected ProfileMedication(Parcel in){
        id = in.readString();
        uniqueId = in.readString();
        type = in.readString();
        status = in.readString();
        remarks = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uniqueId);
        dest.writeString(type);
        dest.writeString(status);
        dest.writeString(remarks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<FamilyProfile> CREATOR = new Parcelable.Creator<FamilyProfile>() {
        @Override
        public FamilyProfile createFromParcel(Parcel in) {
            return new FamilyProfile(in);
        }

        @Override
        public FamilyProfile[] newArray(int size) {
            return new FamilyProfile[size];
        }
    };

}
