package com.example.mvopo.tsekapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class FacilityService implements Parcelable {
    public String id, facility_code, service_type, service, cost, status;

    public FacilityService(String id, String facility_code,
                           String service_type, String service,
                           String cost, String status){
        this.id = id;
        this.facility_code = facility_code;
        this.service_type = service_type;
        this.service = service;
        this.cost = cost;
        this.status = status;
    }

    protected FacilityService(Parcel in){
        id =  in.readString();
        facility_code =  in.readString();
        service_type =  in.readString();
        service =  in.readString();
        cost =  in.readString();
        status =  in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(id);
        dest.writeString(facility_code);
        dest.writeString(service_type);
        dest.writeString(service);
        dest.writeString(cost);
        dest.writeString(status);
    }

    @Override
    public int describeContents(){ return 0;}

    public static final Creator<FacilityService> CREATOR = new Creator<FacilityService>() {
        @Override
        public FacilityService createFromParcel(Parcel in) {
            return new FacilityService(in);
        }

        @Override
        public FacilityService[] newArray(int size) {
            return new FacilityService[size];
        }
    };
}
