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
            immu_stat, nutri_stat, pwd_desc, sexually_active, ip, four_ps, balik_probinsya, member_others, health_group,
            philhealth_categ, other_med_history, fam_plan, fam_plan_method, fam_plan_other_method,
            fam_plan_status, fam_plan_other_status, household_num, fourps_num;

    public FamilyProfile(String id, String uniqueId, String familyId, String household_num, String philhealth_categ, String philId, String nhts, String four_ps, String fourps_num,
                         String ip, String isHead, String relation, String member_others, String fname, String mname, String lname, String suffix, String contact, String dob,
                         String birth_place, String sex, String barangayId, String muncityId, String provinceId, String height, String weight, String civil_status, String religion,
                         String other_religion, String income, String waterSupply, String sanitaryToilet, String educationalAttainment, String balik_probinsya, String health_group,
                         String cancer, String cancer_type, String status, String newborn_screen, String newborn_text, String deceased, String deceased_date, String other_med_history,
                         String covid_status, String menarche, String menarche_age, String sexually_active, String fam_plan, String fam_plan_method, String fam_plan_other_method,
                         String fam_plan_status, String fam_plan_other_status, String unmetNeed, String pregnant, String pwd, String pwd_desc){
        this.id = id;
        this.uniqueId = uniqueId;
        this.familyId = familyId;
        this.household_num = household_num;
        this.philhealth_categ = philhealth_categ;
        this.philId = philId;
        this.nhts = nhts;
        this.four_ps = four_ps;
        this.fourps_num = fourps_num;
        this.ip = ip;
        this.isHead = isHead;
        this.relation = relation;
        this.member_others = member_others;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.suffix = suffix;
        this.contact = contact;
        this.dob = dob;
        this.birth_place = birth_place;
        this.sex = sex;
        this.barangayId = barangayId;
        this.muncityId = muncityId;
        this.provinceId = provinceId;
        this.height = height;
        this.weight = weight;
        this.civil_status=civil_status;
        this.religion = religion;
        this.other_religion = other_religion;
        this.income = income;
        this.waterSupply = waterSupply;
        this.sanitaryToilet = sanitaryToilet;
        this.educationalAttainment = educationalAttainment;
        this.balik_probinsya = balik_probinsya;
        this.health_group = health_group;
        this.cancer = cancer;
        this.cancer_type = cancer_type;
        this.status = status;
        this.newborn_screen = newborn_screen;
        this.newborn_text = newborn_text;
        this.deceased = deceased;
        this.deceased_date = deceased_date;
        this.other_med_history = other_med_history;
        this.covid_status = covid_status;
        this.menarche = menarche;
        this.menarche_age = menarche_age;
        this.sexually_active = sexually_active;
        this.fam_plan = fam_plan;
        this.fam_plan_method = fam_plan_method;
        this.fam_plan_other_method = fam_plan_other_method;
        this.fam_plan_status = fam_plan_status;
        this.fam_plan_other_status = fam_plan_other_status;
        this.unmetNeed = unmetNeed;
        this.pwd = pwd;
        this.pregnant = pregnant;
        this.pwd_desc = pwd_desc;
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
