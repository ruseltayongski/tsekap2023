package com.example.mvopo.tsekapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by romaine on 04/29/2022.
 */

public class FacilityModel implements Parcelable {

    public String id, facility_code, facility_name, facility_abbr, prov_id, muncity_id, brgy_id, address,
            contact, email, chief_hospital, service_capability, license_status, ownership, facility_status, phic_status,
            status, referral_status, transport, latitude, longitude;

    public String sched_day_from, sched_day_to, sched_time_from, sched_time_to, sched_notes;

    public FacilityModel(String Id, String facility_code, String facility_name,
                         String facility_abbr, String prov_id, String muncity_id,
                         String brgy_id, String address, String contact, String email,
                         String chief_hospital, String service_capability, String license_status,
                         String ownership, String facility_status, String referral_status,
                         String phic_status, String transport, String latitude, String longitude,
                         String sched_day_from, String sched_day_to, String sched_time_from,
                         String sched_time_to, String sched_notes, String status){
        this.id=Id;
        this.facility_code = facility_code;
        this.facility_name = facility_name;
        this.facility_abbr = facility_abbr;
        this.prov_id = prov_id;
        this.muncity_id = muncity_id;
        this.brgy_id = brgy_id;
        this.address = address;

        this.contact = contact;
        this.email = email;
        this.chief_hospital = chief_hospital;
        this.service_capability = service_capability;
        this.license_status = license_status;
        this.ownership = ownership;
        this.facility_status = facility_status;
        this.phic_status = phic_status;
        this.referral_status = referral_status;
        this.transport = transport;
        this.latitude = latitude;
        this.longitude = longitude;

        this.sched_day_from = sched_day_from;
        this.sched_day_to = sched_day_to;
        this.sched_time_from = sched_time_from;
        this.sched_time_to = sched_time_to;
        this.sched_notes = sched_notes;

        this.status = status;


    }


    protected FacilityModel(Parcel in) {
        id = in.readString();
        facility_code = in.readString();
        facility_name = in.readString();
        facility_abbr = in.readString();
        prov_id = in.readString();
        muncity_id = in.readString();
        brgy_id = in.readString();
        address = in.readString();

        contact = in.readString();
        email = in.readString();
        chief_hospital = in.readString();
        service_capability = in.readString();
        license_status = in.readString();
        ownership = in.readString();
        facility_status = in.readString();
        phic_status = in.readString();

        referral_status = in.readString();
        transport = in.readString();
        latitude = in.readString();
        longitude = in.readString();

        sched_day_from = in.readString();
        sched_day_to = in.readString();
        sched_time_from = in.readString();
        sched_time_to = in.readString();
        sched_notes = in.readString();


        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(facility_code);
        dest.writeString(facility_name);
        dest.writeString(facility_abbr);

        dest.writeString(prov_id);
        dest.writeString(muncity_id);
        dest.writeString(brgy_id);
        dest.writeString(address);

        dest.writeString(contact);
        dest.writeString(email);
        dest.writeString(chief_hospital);
        dest.writeString(service_capability);
        dest.writeString(license_status);
        dest.writeString(ownership);
        dest.writeString(facility_status);
        dest.writeString(phic_status);

        dest.writeString(referral_status);
        dest.writeString(transport);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(sched_day_from);
        dest.writeString(sched_day_to);
        dest.writeString(sched_time_from);
        dest.writeString(sched_time_to);
        dest.writeString(sched_notes);

        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FacilityModel> CREATOR = new Creator<FacilityModel>() {
        @Override
        public FacilityModel createFromParcel(Parcel in) {
            return new FacilityModel(in);
        }

        @Override
        public FacilityModel[] newArray(int size) {
            return new FacilityModel[size];
        }
    };
}
