package com.example.mvopo.tsekapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mvopo on 10/19/2017.
 */

public class FamilyProfile implements Parcelable {
    public String id, uniqueId, familyId, philId, nhts, isHead, relation, fname, lname, mname, suffix, dob,
            sex, barangayId, muncityId, provinceId, income, unmetNeed, waterSupply, sanitaryToilet, educationalAttainment,
            status;

    // UPDATE
    public String  pwd;
    public String pregnant;

    //Update r
    public String birth_place, civil_status, religion, other_religion, contact, height, weight, cancer, cancer_type,
             covid_status, menarche, menarche_age, newborn_screen, newborn_text, deceased, deceased_date,
            immu_stat, nutri_stat, pwd_desc, sexually_active, ip, four_ps, balik_probinsya, member_others;

    public FamilyProfile(String id, String uniqueId, String familyId, String philId, String nhts, String four_ps, String ip, String isHead,
                         String relation, String member_others, String fname, String lname, String mname, String suffix, String dob, String sex,
                         String barangayId, String muncityId, String provinceId, String income, String unmetNeed, String waterSupply,
                         String sanitaryToilet, String educationalAttainment, String balik_probinsya, String status ,  String pwd, String pregnant,
                         String birth_place, String civil_status, String religion, String other_religion, String contact, String height, String weight, String cancer,
                         String cancer_type,  String covid_status, String menarche, String menarche_age, String newborn_screen,
                         String newborn_text, String deceased, String deceased_date, String immu_stat, String nutri_stat, String pwd_desc, String sexually_active) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.familyId = familyId;
        this.philId = philId;
        this.nhts = nhts;
        this.four_ps = four_ps;
        this.ip = ip;

        this.isHead = isHead;
        this.relation = relation;
        this.member_others = member_others;
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.suffix = suffix;
        this.dob = dob;
        this.sex = sex;
        this.barangayId = barangayId;
        this.muncityId = muncityId;
        this.provinceId = provinceId;
        this.income = income;
        this.unmetNeed = unmetNeed;
        this.waterSupply = waterSupply;
        this.sanitaryToilet = sanitaryToilet;
        this.educationalAttainment = educationalAttainment;
        this.balik_probinsya = balik_probinsya;
        this.status = status;

        // UPDATE
        this.pwd = pwd;
        this.pregnant = pregnant;

        //Update R
        this.birth_place = birth_place;
        this.civil_status = civil_status;
        this.religion = religion;
        this.other_religion = other_religion;
        this.contact = contact;
        this.height = height;
        this.weight = weight;
        this.cancer = cancer;
        this.cancer_type = cancer_type;
        this.covid_status = covid_status;
        this.menarche = menarche;
        this.menarche_age = menarche_age;
        this.newborn_screen = newborn_screen;
        this.newborn_text = newborn_text;
        this.deceased = deceased;
        this.deceased_date = deceased_date;
        this.immu_stat = immu_stat;
        this.nutri_stat = nutri_stat;
        this.pwd_desc = pwd_desc;
        this.sexually_active = sexually_active;
    }

    protected FamilyProfile(Parcel in) {
        id = in.readString();
        uniqueId = in.readString();
        familyId = in.readString();
        philId = in.readString();
        nhts = in.readString();
        four_ps = in.readString();
        ip = in.readString();
        isHead = in.readString();
        relation = in.readString();
        member_others = in.readString();
        fname = in.readString();
        lname = in.readString();
        mname = in.readString();
        suffix = in.readString();
        dob = in.readString();
        sex = in.readString();
        barangayId = in.readString();
        muncityId = in.readString();
        provinceId = in.readString();
        income = in.readString();
        unmetNeed = in.readString();
        waterSupply = in.readString();
        sanitaryToilet = in.readString();
        educationalAttainment = in.readString();
        balik_probinsya = in.readString();
        status = in.readString();
        // UPDATE
        pwd = in.readString();
        pregnant = in.readString();

        //update r
        birth_place = in.readString();
        civil_status = in.readString();
        religion = in.readString();
        other_religion = in.readString();
        contact = in.readString();
        height = in.readString();
        weight = in.readString();
        cancer = in.readString();
        cancer_type = in.readString();
        covid_status = in.readString();
        menarche = in.readString();
        menarche_age = in.readString();
        newborn_screen = in.readString();
        newborn_text = in.readString();
        deceased = in.readString();
        deceased_date = in.readString();
        immu_stat = in.readString();
        nutri_stat = in.readString();
        pwd_desc = in.readString();
        sexually_active = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uniqueId);
        dest.writeString(familyId);
        dest.writeString(philId);
        dest.writeString(nhts);
        dest.writeString(four_ps);
        dest.writeString(ip);
        dest.writeString(isHead);
        dest.writeString(relation);
        dest.writeString(member_others);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(mname);
        dest.writeString(suffix);
        dest.writeString(dob);
        dest.writeString(sex);
        dest.writeString(barangayId);
        dest.writeString(muncityId);
        dest.writeString(provinceId);
        dest.writeString(income);
        dest.writeString(unmetNeed);
        dest.writeString(waterSupply);
        dest.writeString(sanitaryToilet);
        dest.writeString(educationalAttainment);
        dest.writeString(balik_probinsya);
        dest.writeString(status);
        // UPDATE
        dest.writeString(pwd);
        dest.writeString(pregnant);

        //update r
        dest.writeString(birth_place);
        dest.writeString(civil_status);
        dest.writeString(religion);
        dest.writeString(other_religion);
        dest.writeString(contact);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(cancer);
        dest.writeString(cancer_type);
        dest.writeString(covid_status);
        dest.writeString(menarche);
        dest.writeString(menarche_age);
        dest.writeString(newborn_screen);
        dest.writeString(newborn_text);
        dest.writeString(deceased);
        dest.writeString(deceased_date);
        dest.writeString(immu_stat);
        dest.writeString(nutri_stat);
        dest.writeString(pwd_desc);
        dest.writeString(sexually_active);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FamilyProfile> CREATOR = new Creator<FamilyProfile>() {
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
