package com.example.mvopo.tsekapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.mvopo.tsekapp.Model.AffiliatedFacilitiesModel;
import com.example.mvopo.tsekapp.Model.BarangayModel;
import com.example.mvopo.tsekapp.Model.FacilityModel;
import com.example.mvopo.tsekapp.Model.FacilityService;
import com.example.mvopo.tsekapp.Model.FamilyProfile;
import com.example.mvopo.tsekapp.Model.FeedBack;
import com.example.mvopo.tsekapp.Model.ProfileMedication;
import com.example.mvopo.tsekapp.Model.MuncityModel;
import com.example.mvopo.tsekapp.Model.ProvinceModel;
import com.example.mvopo.tsekapp.Model.ServicesStatus;
import com.example.mvopo.tsekapp.Model.ServiceAvailed;
import com.example.mvopo.tsekapp.Model.SpecialistModel;
import com.example.mvopo.tsekapp.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    final static String DBNAME = "db_tsekap";
    final static String USERS = "tbl_user";
    final static String SERVICES = "tbl_services";
    final static String SERVICESTATUS = "tbl_must_services";

    final static String PROFILES = "tbl_profile";
    final static String PROFILE_MEDICATION = "tbl_medication";

    final static String FEEDBACK = "tbl_feedback";
    final static String CHPHS = "tbl_chphs";
    final static String CLUSTER = "tbl_cluster";
    final static String DISTRICT = "tbl_district";

    final static String SPECIALIST = "tbl_specialist";
    final static String FACILITY = "tbl_facility";
    final static String FACILITY_ASSIGNMENT = "tbl_facility_assignment";
    final static String FACILITYSERVICES = "tbl_facility_services";

    final static String PROVINCE = "tbl_province";
    final static String MUNCITY = "tbl_muncity";
    final static String BRGY = "tbl_barangay";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 6);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table " + USERS + " (id integer, fname varchar(50), mname varchar(50), lname varchar(50)," +
                " muncity varchar(50), contact varchar(50), barangay varchar(255), target varchar(100), image varchar(50), province varchar(50))";

        String sql1 = "Create table " + PROFILES + " " +
                "(id integer, " +
                "uniqueId varchar(100), " +
                "familyId varchar(50), " +
                "household_num varchar(50), " +
                "philhealth_categ varchar(10), " +
                "philId varchar(50), " +
                "nhts varchar(50), " +
                "four_ps varchar(50), " +
                "fourps_num varchar(50), " +
                "ip varchar(50), " +
                "isHead varchar(50), " +
                "relation varchar(50), " +
                "member_others varchar(50), " +
                "fname varchar(50), " +
                "mname varchar(50), " +
                "lname varchar(50), " +
                "suffix varchar(50), " +
                "contact varchar(15), " +
                "dob varchar(50), " +
                "birth_place varchar(15), " +
                "sex varchar(50), " +
                "barangayId varchar(50), " +
                "muncityId varchar(50),  " +
                "provinceId varchar(50), " +
                "height varchar(10), " +
                "weight varchar(10), " +
                "civil_status varchar(15), " +
                "religion varchar(30)," +
                "other_religion varchar(30)," +
                "income varchar(50), " +
                "waterSupply varchar(50), " +
                "sanitaryToilet varchar(50), " +
                "educationalAttainment varchar(50)," +
                "balik_probinsya varchar(50)," +
                "health_group varchar(30), " +
                "cancer varchar(5)," +
                "cancer_type varchar(100)," +
                "status varchar(3)," +
                "newborn_screen varchar(5)," +
                "newborn_text varchar(50)," +
                "deceased varchar(5)," +
                "deceased_date varchar(50)," +
                "other_med_history varchar(50), " +
                "covid_status varchar(30)," +
                "menarche varchar(30)," +
                "menarche_age varchar(30)," +
                "sexually_active varchar(5), " +
                "fam_plan varchar(10), " +
                "fam_plan_method varchar(10), " +
                "fam_plan_other_method varchar(50), " +
                "fam_plan_status varchar(20), " +
                "fam_plan_other_status varchar(50), " +
                "unmetNeed varchar(50), " +
                "pwd varchar(5)," +
                "pwd_desc varchar(100), " +
                "pregnant varchar(15))";

        String sql2 = "Create table " + SERVICES + " (id integer primary key autoincrement, request TEXT)";

        String sql3 = "Create table " + SERVICESTATUS + " (id integer primary key autoincrement, name varchar(100), group1 varchar(2), group2 varchar(2)," +
                " group3 varchar(2), barangayId varchar(10))";

        String sql4 = "Create table " + FEEDBACK + " (id integer primary key autoincrement, subject varchar(25), body varchar(255))";

        String sql5 = "Create table " + SPECIALIST + " " +
                "(id integer  primary key autoincrement, " +
                "username varchar(100), " +
                "fname varchar(100), " +
                "mname varchar(100), " +
                "lname varchar(100), " +
                "status varchar(5))";

        String sql6 = "Create table " + FACILITY_ASSIGNMENT + " " +
                "(id integer  primary key autoincrement, " +
                "username varchar(100), " +
                "facility_code varchar(50), " +
                "specialization varchar(100), " +
                "contact varchar(100), " +
                "email varchar(100), " +
                "schedule varchar(100), " +
                "fee varchar(100), " +
                "status varchar(5))";

        String sql7 = "Create table " + FACILITY + " " +
                "(id integer  primary key autoincrement, " +
                "facility_code varchar(100), " +
                "facility_name varchar(255), " +
                "facility_abbr varchar(255), " +

                "province_id varchar(11), " +
                "municipality_id varchar(11), " +
                "barangay_id varchar(11), " +
                "address varchar(255), " +

                "contact varchar(255), " +
                "email varchar(255), " +
                "chief_hospital varchar(255), " +
                "service_capability varchar(100), " +
                "license_status varchar(100), " +
                "ownership varchar(100), " +
                "facility_status varchar(100), " +
                "phic_status varchar(100), " +
                "referral_status varchar(100), " +
                "transport varchar(100), " +
                "latitude varchar(255), " +
                "longitude varchar(255), " +
                "sched_day_from varchar(100), " +
                "sched_day_to varchar(100), " +
                "sched_time_from varchar(100), " +
                "sched_time_to varchar(100), " +
                "sched_notes varchar(255), " +

                "status varchar(5))";

        String sql8 = "Create table " + FACILITYSERVICES + " " +
                "(id integer  primary key autoincrement, " +
                "facility_code varchar(100), " +
                "service_type varchar(100), " +
                "service varchar(100), " +
                "cost varchar(100), " +
                "status varchar(5))";


        String sql9 = "Create table " + PROVINCE + " " +
                "(id integer, " +
                "name varchar(255))";

        String sql10 = "Create table " + MUNCITY + " " +
                "(id integer, " +
                "name varchar(255), " +
                "prov_id varchar(100))";

        String sql11 = "Create table " + BRGY + " " +
                "(id integer, " +
                "name varchar(255), " +
                "prov_id varchar(100), " +
                "muncity_id varchar(100))";

        String sql12 = "Create table " + PROFILE_MEDICATION + " " +
                "(id integer  primary key autoincrement, " +
                "uniqueId varchar(100), " +
                "type varchar(30)," +
                "medication_status varchar(100)," +
                "remarks varchar(100)," +
                "status varchar(5))";

       /* "mental_med varchar(30)," +
                "tbdots_med varchar(30)," +
                "cvd_med varchar(30)," +
                "diabetic varchar(30)," +
                "hypertension varchar(30)," +
        */

        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);

        db.execSQL(sql9);
        db.execSQL(sql10);
        db.execSQL(sql11);

        db.execSQL(sql12);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql = "Create table IF NOT EXISTS " + SERVICESTATUS + " (id integer primary key autoincrement, name varchar(100), group1 varchar(2), group2 varchar(2)," +
//                " group3 varchar(2), barangayId varchar(10))";
//
//        String sql1 = "Create table IF NOT EXISTS " + FEEDBACK + " (id integer primary key autoincrement, subject varchar(25), body varchar(255))";

//        String sql2 = "ALTER TABLE "+ USERS +" ADD image varchar(50)";
        String sql = "Create table IF NOT EXISTS " + CHPHS + " (id integer primary key autoincrement, cluster varchar(5), district varchar(5), houseNo varchar(50)," +
                " street varchar(100), sitio varchar(50), purok varchar(50), bloodType varchar(5), weight varchar(10), height varchar(10), contact varchar(50))";

        String sql1 = "Create table IF NOT EXISTS " + CLUSTER + " (id integer primary key autoincrement, description varchar(100))";

        String sql2 = "Create table IF NOT EXISTS " + DISTRICT + " (id integer primary key autoincrement, description varchar(100))";

        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", user.id);
        cv.put("fname", user.fname);
        cv.put("mname", user.mname);
        cv.put("lname", user.lname);
        cv.put("muncity", user.muncity);
        cv.put("contact", user.contact);
        cv.put("barangay", user.barangay);
        cv.put("target", user.target);
        cv.put("image", user.image);
        cv.put("province", user.province);
        db.insert(USERS, null, cv);
        db.close();
    }

    public void deleteUser() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(USERS, null, null);
    }

    public User getUser() {
        User user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select * from " + USERS;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            String id = c.getString(c.getColumnIndexOrThrow("id"));
            String fname = c.getString(c.getColumnIndexOrThrow("fname"));
            String mname = c.getString(c.getColumnIndexOrThrow("mname"));
            String lname = c.getString(c.getColumnIndexOrThrow("lname"));
            String muncity = c.getString(c.getColumnIndexOrThrow("muncity"));
            String contact = c.getString(c.getColumnIndexOrThrow("contact"));
            String barangay = c.getString(c.getColumnIndexOrThrow("barangay"));
            String target = c.getString(c.getColumnIndexOrThrow("target"));
            String image = c.getString(c.getColumnIndexOrThrow("image"));
            String province = c.getString(c.getColumnIndexOrThrow("province"));
            user = new User(id, fname, mname, lname, muncity, contact, barangay, target, image, province);
        }
        c.close();
        return user;
    }

/*    public String getUserId() {
        String id="";
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select * from " + USERS;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
             id = c.getString(c.getColumnIndexOrThrow("id"));
        }
        c.close();
        return id;
    }*/

    public void addProfile(FamilyProfile familyProfile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", familyProfile.id);
        cv.put("uniqueId", familyProfile.uniqueId);
        cv.put("familyId", familyProfile.familyId);
        cv.put("household_num", familyProfile.household_num);
        cv.put("philhealth_categ", familyProfile.philhealth_categ);
        cv.put("philId", familyProfile.philId);
        cv.put("nhts", familyProfile.nhts);
        cv.put("four_ps", familyProfile.four_ps);
        cv.put("fourps_num", familyProfile.fourps_num);
        cv.put("ip", familyProfile.ip);

        cv.put("isHead", familyProfile.isHead);
        cv.put("relation", familyProfile.relation);
        cv.put("member_others", familyProfile.member_others);
        cv.put("fname", familyProfile.fname);
        cv.put("mname", familyProfile.mname);
        cv.put("lname", familyProfile.lname);
        cv.put("suffix", familyProfile.suffix);
        cv.put("contact", familyProfile.contact);
        cv.put("dob", familyProfile.dob);
        cv.put("birth_place", familyProfile.birth_place);
        cv.put("sex", familyProfile.sex);
        cv.put("barangayId", familyProfile.barangayId);
        cv.put("muncityId", familyProfile.muncityId);
        cv.put("provinceId", familyProfile.provinceId);
        cv.put("height", familyProfile.height);
        cv.put("weight", familyProfile.weight);
        cv.put("civil_status", familyProfile.civil_status);
        cv.put("religion", familyProfile.religion);
        cv.put("other_religion", familyProfile.other_religion);
        cv.put("income", familyProfile.income);
        cv.put("waterSupply", familyProfile.waterSupply);
        cv.put("sanitaryToilet", familyProfile.sanitaryToilet);
        cv.put("educationalAttainment", familyProfile.educationalAttainment);
        cv.put("balik_probinsya", familyProfile.balik_probinsya);
        cv.put("health_group", familyProfile.health_group);
        cv.put("cancer", familyProfile.cancer);
        cv.put("cancer_type", familyProfile.cancer_type);
        cv.put("status", familyProfile.status);
        cv.put("newborn_screen", familyProfile.newborn_screen);
        cv.put("newborn_text", familyProfile.newborn_text);
        cv.put("deceased", familyProfile.deceased);
        cv.put("deceased_date", familyProfile.deceased_date);
        cv.put("other_med_history", familyProfile.other_med_history);
        cv.put("covid_status", familyProfile.covid_status);
        cv.put("menarche", familyProfile.menarche);
        cv.put("menarche_age", familyProfile.menarche_age);
        cv.put("sexually_active", familyProfile.sexually_active);
        cv.put("fam_plan", familyProfile.fam_plan);
        cv.put("fam_plan_method", familyProfile.fam_plan_method);
        cv.put("fam_plan_other_method", familyProfile.fam_plan_other_method);
        cv.put("fam_plan_status", familyProfile.fam_plan_status);
        cv.put("fam_plan_other_status", familyProfile.fam_plan_other_status);
        cv.put("unmetNeed", familyProfile.unmetNeed);
        cv.put("pregnant", familyProfile.pregnant);
        cv.put("pwd", familyProfile.pwd);
        cv.put("pwd_desc", familyProfile.pwd_desc);
        db.insertWithOnConflict(PROFILES, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void updateProfile(FamilyProfile familyProfile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", familyProfile.id);
        cv.put("uniqueId", familyProfile.uniqueId);
        cv.put("familyId", familyProfile.familyId);
        cv.put("household_num", familyProfile.household_num);
        cv.put("philhealth_categ", familyProfile.philhealth_categ);
        cv.put("philId", familyProfile.philId);
        cv.put("nhts", familyProfile.nhts);
        cv.put("four_ps", familyProfile.four_ps);
        cv.put("fourps_num", familyProfile.fourps_num);
        cv.put("ip", familyProfile.ip);
        cv.put("isHead", familyProfile.isHead);
        cv.put("relation", familyProfile.relation);
        cv.put("member_others", familyProfile.member_others);
        cv.put("fname", familyProfile.fname);
        cv.put("mname", familyProfile.mname);
        cv.put("lname", familyProfile.lname);
        cv.put("suffix", familyProfile.suffix);
        cv.put("contact", familyProfile.contact);
        cv.put("dob", familyProfile.dob);
        cv.put("birth_place", familyProfile.birth_place);
        cv.put("sex", familyProfile.sex);
        cv.put("barangayId", familyProfile.barangayId);
        cv.put("muncityId", familyProfile.muncityId);
        cv.put("provinceId", familyProfile.provinceId);
        cv.put("height", familyProfile.height);
        cv.put("weight", familyProfile.weight);
        cv.put("civil_status", familyProfile.civil_status);
        cv.put("religion", familyProfile.religion);
        cv.put("other_religion", familyProfile.other_religion);
        cv.put("income", familyProfile.income);
        cv.put("waterSupply", familyProfile.waterSupply);
        cv.put("sanitaryToilet", familyProfile.sanitaryToilet);
        cv.put("educationalAttainment", familyProfile.educationalAttainment);
        cv.put("balik_probinsya", familyProfile.balik_probinsya);
        cv.put("health_group", familyProfile.health_group);
        cv.put("cancer", familyProfile.cancer);
        cv.put("cancer_type", familyProfile.cancer_type);
        cv.put("status", familyProfile.status);
        cv.put("newborn_screen", familyProfile.newborn_screen);
        cv.put("newborn_text", familyProfile.newborn_text);
        cv.put("deceased", familyProfile.deceased);
        cv.put("deceased_date", familyProfile.deceased_date);
        cv.put("other_med_history", familyProfile.other_med_history);
        cv.put("covid_status", familyProfile.covid_status);
        cv.put("menarche", familyProfile.menarche);
        cv.put("menarche_age", familyProfile.menarche_age);
        cv.put("sexually_active", familyProfile.sexually_active);
        cv.put("fam_plan", familyProfile.fam_plan);
        cv.put("fam_plan_method", familyProfile.fam_plan_method);
        cv.put("fam_plan_other_method", familyProfile.fam_plan_other_method);
        cv.put("fam_plan_status", familyProfile.fam_plan_status);
        cv.put("fam_plan_other_status", familyProfile.fam_plan_other_status);
        cv.put("unmetNeed", familyProfile.unmetNeed);
        cv.put("pregnant", familyProfile.pregnant);
        cv.put("pwd", familyProfile.pwd);
        cv.put("pwd_desc", familyProfile.pwd_desc);
        db.update(PROFILES, cv, "uniqueId=?", new String[]{familyProfile.uniqueId});
        db.close();
    }

    public ArrayList<FamilyProfile> getFamilyProfiles(String name) {
        name += "%";
        ArrayList<FamilyProfile> profiles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(PROFILES, null, "fname LIKE ? or mname LIKE ? or lname LIKE ? or familyId LIKE ?", new String[]{name, name, name, name}, null, null, null, "20");

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String uniqueId = c.getString(c.getColumnIndexOrThrow("uniqueId"));
                String familyId = c.getString(c.getColumnIndexOrThrow("familyId"));
                String householdNum = c.getString(c.getColumnIndexOrThrow("household_num"));
                String philhealth_categ = c.getString(c.getColumnIndexOrThrow("philhealth_categ"));
                String philId = c.getString(c.getColumnIndexOrThrow("philId"));
                String nhts = c.getString(c.getColumnIndexOrThrow("nhts"));
                String fourPs = c.getString(c.getColumnIndexOrThrow("four_ps"));
                String fourPsNumber = c.getString(c.getColumnIndexOrThrow("fourps_num"));
                String ip = c.getString(c.getColumnIndexOrThrow("ip"));
                String head = c.getString(c.getColumnIndexOrThrow("isHead"));
                String relation = c.getString(c.getColumnIndexOrThrow("relation"));
                String member_others = c.getString(c.getColumnIndexOrThrow("member_others"));
                String fname = c.getString(c.getColumnIndexOrThrow("fname"));
                String mname = c.getString(c.getColumnIndexOrThrow("mname"));
                String lname = c.getString(c.getColumnIndexOrThrow("lname"));
                String suffix = c.getString(c.getColumnIndexOrThrow("suffix"));
                String dob = c.getString(c.getColumnIndexOrThrow("dob"));
                String sex = c.getString(c.getColumnIndexOrThrow("sex"));
                String barangayId = c.getString(c.getColumnIndexOrThrow("barangayId"));
                String muncityId = c.getString(c.getColumnIndexOrThrow("muncityId"));
                String provinceId = c.getString(c.getColumnIndexOrThrow("provinceId"));
                String income = c.getString(c.getColumnIndexOrThrow("income"));
                String waterSupply = c.getString(c.getColumnIndexOrThrow("waterSupply"));
                String sanitaryToilet = c.getString(c.getColumnIndexOrThrow("sanitaryToilet"));
                String educationalAttainment = c.getString(c.getColumnIndexOrThrow("educationalAttainment"));
                String balik_probinsya = c.getString(c.getColumnIndexOrThrow("balik_probinsya"));
                String age_class = c.getString(c.getColumnIndexOrThrow("health_group"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));
//                String diabetic = c.getString(c.getColumnIndexOrThrow("diabetic"));
//                String hypertension = c.getString(c.getColumnIndexOrThrow("hypertension"));
                String pwd = c.getString(c.getColumnIndexOrThrow("pwd"));
                String pregnant = c.getString(c.getColumnIndexOrThrow("pregnant"));

                String birth_place = c.getString(c.getColumnIndexOrThrow("birth_place"));
                String civil_status = c.getString(c.getColumnIndexOrThrow("civil_status"));
                String religion = c.getString(c.getColumnIndexOrThrow("religion"));
                String other_religion = c.getString(c.getColumnIndexOrThrow("other_religion"));
                String contact = c.getString(c.getColumnIndexOrThrow("contact"));
                String height = c.getString(c.getColumnIndexOrThrow("height"));
                String weight = c.getString(c.getColumnIndexOrThrow("weight"));
                String cancer = c.getString(c.getColumnIndexOrThrow("cancer"));
                String cancer_type = c.getString(c.getColumnIndexOrThrow("cancer_type"));
               /* String mental_med = c.getString(c.getColumnIndexOrThrow("mental_med"));
                String tbdots_med = c.getString(c.getColumnIndexOrThrow("tbdots_med"));
                String cvd_med = c.getString(c.getColumnIndexOrThrow("cvd_med"));*/
                String covid_status = c.getString(c.getColumnIndexOrThrow("covid_status"));
                String menarche = c.getString(c.getColumnIndexOrThrow("menarche"));
                String menarche_age = c.getString(c.getColumnIndexOrThrow("menarche_age"));
                String newborn_screen = c.getString(c.getColumnIndexOrThrow("newborn_screen"));
                String newborn_text = c.getString(c.getColumnIndexOrThrow("newborn_text"));
                String deceased = c.getString(c.getColumnIndexOrThrow("deceased"));
                String deceased_date = c.getString(c.getColumnIndexOrThrow("deceased_date"));
                String pwd_desc = c.getString(c.getColumnIndexOrThrow("pwd_desc"));
                String sexually_active = c.getString(c.getColumnIndexOrThrow("sexually_active"));
                String other_med_history = c.getString(c.getColumnIndexOrThrow("other_med_history"));
                String familyPlanning = c.getString(c.getColumnIndexOrThrow("fam_plan"));
                String familyPlanningMethod = c.getString(c.getColumnIndexOrThrow("fam_plan_method"));
                String familyPlanningOtherMethod = c.getString(c.getColumnIndexOrThrow("fam_plan_other_method"));
                String familyPlanningStatus = c.getString(c.getColumnIndexOrThrow("fam_plan_status"));
                String familyPlanningOtherStatus = c.getString(c.getColumnIndexOrThrow("fam_plan_other_status"));
                String unmet = c.getString(c.getColumnIndexOrThrow("unmetNeed"));
                String pregnant_date = c.getString(c.getColumnIndexOrThrow("pregnant"));

                FamilyProfile profile = new FamilyProfile(
                        id + "",
                        uniqueId, familyId, householdNum, philhealth_categ, philId, nhts, fourPs, fourPsNumber,
                        ip, head, relation, member_others, fname, mname, lname, suffix, contact, dob,
                        birth_place, sex, barangayId, muncityId, provinceId, height, weight, civil_status, religion,
                        other_religion, income, waterSupply, sanitaryToilet, educationalAttainment,
                        balik_probinsya, age_class, cancer, cancer_type, status, newborn_screen,
                        newborn_text, deceased, deceased_date, other_med_history, covid_status, menarche,
                        menarche_age, sexually_active, familyPlanning, familyPlanningMethod, familyPlanningOtherMethod, familyPlanningStatus,
                        familyPlanningOtherStatus, unmet, pregnant_date, pwd, pwd_desc);

                if(familyId.equals(name.substring(0, name.length()-1)) && relation.equalsIgnoreCase("Head")) profiles.add(0, profile);
                else profiles.add(profile);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return profiles;
    }

    public FamilyProfile getProfileForSync() {
        FamilyProfile familyProfile = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(PROFILES, null, "status = 1", null, null, null, null, "20");

        if (c.moveToFirst()) {
            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            String uniqueId = c.getString(c.getColumnIndexOrThrow("uniqueId"));
            String familyId = c.getString(c.getColumnIndexOrThrow("familyId"));
            String householdNum = c.getString(c.getColumnIndexOrThrow("household_num"));
            String philhealth_categ = c.getString(c.getColumnIndexOrThrow("philhealth_categ"));
            String philId = c.getString(c.getColumnIndexOrThrow("philId"));
            String nhts = c.getString(c.getColumnIndexOrThrow("nhts"));
            String fourPs = c.getString(c.getColumnIndexOrThrow("four_ps"));
            String fourPsNumber = c.getString(c.getColumnIndexOrThrow("fourps_num"));
            String ip = c.getString(c.getColumnIndexOrThrow("ip"));
            String head = c.getString(c.getColumnIndexOrThrow("isHead"));
            String relation = c.getString(c.getColumnIndexOrThrow("relation"));
            String member_others = c.getString(c.getColumnIndexOrThrow("member_others"));
            String fname = c.getString(c.getColumnIndexOrThrow("fname"));
            String mname = c.getString(c.getColumnIndexOrThrow("mname"));
            String lname = c.getString(c.getColumnIndexOrThrow("lname"));
            String suffix = c.getString(c.getColumnIndexOrThrow("suffix"));
            String dob = c.getString(c.getColumnIndexOrThrow("dob"));
            String sex = c.getString(c.getColumnIndexOrThrow("sex"));
            String barangayId = c.getString(c.getColumnIndexOrThrow("barangayId"));
            String muncityId = c.getString(c.getColumnIndexOrThrow("muncityId"));
            String provinceId = c.getString(c.getColumnIndexOrThrow("provinceId"));
            String income = c.getString(c.getColumnIndexOrThrow("income"));
            String waterSupply = c.getString(c.getColumnIndexOrThrow("waterSupply"));
            String sanitaryToilet = c.getString(c.getColumnIndexOrThrow("sanitaryToilet"));
            String educationalAttainment = c.getString(c.getColumnIndexOrThrow("educationalAttainment"));
            String balik_probinsya = c.getString(c.getColumnIndexOrThrow("balik_probinsya"));
            String age_class = c.getString(c.getColumnIndexOrThrow("health_group"));
            String status = c.getString(c.getColumnIndexOrThrow("status"));
//                String diabetic = c.getString(c.getColumnIndexOrThrow("diabetic"));
//                String hypertension = c.getString(c.getColumnIndexOrThrow("hypertension"));
            String pwd = c.getString(c.getColumnIndexOrThrow("pwd"));

            String birth_place = c.getString(c.getColumnIndexOrThrow("birth_place"));
            String civil_status = c.getString(c.getColumnIndexOrThrow("civil_status"));
            String religion = c.getString(c.getColumnIndexOrThrow("religion"));
            String other_religion = c.getString(c.getColumnIndexOrThrow("other_religion"));
            String contact = c.getString(c.getColumnIndexOrThrow("contact"));
            String height = c.getString(c.getColumnIndexOrThrow("height"));
            String weight = c.getString(c.getColumnIndexOrThrow("weight"));
            String cancer = c.getString(c.getColumnIndexOrThrow("cancer"));
            String cancer_type = c.getString(c.getColumnIndexOrThrow("cancer_type"));
            String covid_status = c.getString(c.getColumnIndexOrThrow("covid_status"));
            String menarche = c.getString(c.getColumnIndexOrThrow("menarche"));
            String menarche_age = c.getString(c.getColumnIndexOrThrow("menarche_age"));
            String newborn_screen = c.getString(c.getColumnIndexOrThrow("newborn_screen"));
            String newborn_text = c.getString(c.getColumnIndexOrThrow("newborn_text"));
            String deceased = c.getString(c.getColumnIndexOrThrow("deceased"));
            String deceased_date = c.getString(c.getColumnIndexOrThrow("deceased_date"));
            String pwd_desc = c.getString(c.getColumnIndexOrThrow("pwd_desc"));
            String sexually_active = c.getString(c.getColumnIndexOrThrow("sexually_active"));
            String other_med_history = c.getString(c.getColumnIndexOrThrow("other_med_history"));
            String familyPlanning = c.getString(c.getColumnIndexOrThrow("fam_plan"));
            String familyPlanningMethod = c.getString(c.getColumnIndexOrThrow("fam_plan_method"));
            String familyPlanningOtherMethod = c.getString(c.getColumnIndexOrThrow("fam_plan_other_method"));
            String familyPlanningStatus = c.getString(c.getColumnIndexOrThrow("fam_plan_status"));
            String familyPlanningOtherStatus = c.getString(c.getColumnIndexOrThrow("fam_plan_other_status"));
            String unmet = c.getString(c.getColumnIndexOrThrow("unmetNeed"));
            String pregnant_date = c.getString(c.getColumnIndexOrThrow("pregnant"));

            familyProfile = new FamilyProfile(
                    id + "",
                    uniqueId, familyId, householdNum, philhealth_categ, philId, nhts, fourPs, fourPsNumber,
                    ip, head, relation, member_others, fname, mname, lname, suffix, contact, dob,
                    birth_place, sex, barangayId, muncityId, provinceId, height, weight, civil_status, religion,
                    other_religion, income, waterSupply, sanitaryToilet, educationalAttainment,
                    balik_probinsya, age_class, cancer, cancer_type, status, newborn_screen,
                    newborn_text, deceased, deceased_date, other_med_history, covid_status, menarche,
                    menarche_age, sexually_active, familyPlanning, familyPlanningMethod, familyPlanningOtherMethod, familyPlanningStatus,
                    familyPlanningOtherStatus, unmet, pregnant_date, pwd, pwd_desc);
        }
        c.close();
        db.close();
        return familyProfile;
    }

    public ArrayList<FamilyProfile> getMatchingProfiles(String cFname, String cMname, String cLname, String cSuffix) {
        ArrayList<FamilyProfile> profiles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(PROFILES, null, "fname LIKE ? and mname LIKE ? and lname LIKE ? and suffix LIKE ?",
                new String[]{cFname + "%", cMname + "%", cLname + "%", cSuffix + "%"}, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String uniqueId = c.getString(c.getColumnIndexOrThrow("uniqueId"));
                String familyId = c.getString(c.getColumnIndexOrThrow("familyId"));
                String householdNum = c.getString(c.getColumnIndexOrThrow("household_num"));
                String philhealth_categ = c.getString(c.getColumnIndexOrThrow("philhealth_categ"));
                String philId = c.getString(c.getColumnIndexOrThrow("philId"));
                String nhts = c.getString(c.getColumnIndexOrThrow("nhts"));
                String fourPs = c.getString(c.getColumnIndexOrThrow("four_ps"));
                String fourPsNumber = c.getString(c.getColumnIndexOrThrow("fourps_num"));
                String ip = c.getString(c.getColumnIndexOrThrow("ip"));
                String head = c.getString(c.getColumnIndexOrThrow("isHead"));
                String relation = c.getString(c.getColumnIndexOrThrow("relation"));
                String member_others = c.getString(c.getColumnIndexOrThrow("member_others"));
                String fname = c.getString(c.getColumnIndexOrThrow("fname"));
                String mname = c.getString(c.getColumnIndexOrThrow("mname"));
                String lname = c.getString(c.getColumnIndexOrThrow("lname"));
                String suffix = c.getString(c.getColumnIndexOrThrow("suffix"));
                String dob = c.getString(c.getColumnIndexOrThrow("dob"));
                String sex = c.getString(c.getColumnIndexOrThrow("sex"));
                String barangayId = c.getString(c.getColumnIndexOrThrow("barangayId"));
                String muncityId = c.getString(c.getColumnIndexOrThrow("muncityId"));
                String provinceId = c.getString(c.getColumnIndexOrThrow("provinceId"));
                String income = c.getString(c.getColumnIndexOrThrow("income"));
                String unmetNeed = c.getString(c.getColumnIndexOrThrow("unmetNeed"));
                String waterSupply = c.getString(c.getColumnIndexOrThrow("waterSupply"));
                String sanitaryToilet = c.getString(c.getColumnIndexOrThrow("sanitaryToilet"));
                String educationalAttainment = c.getString(c.getColumnIndexOrThrow("educationalAttainment"));
                String balik_probinsya = c.getString(c.getColumnIndexOrThrow("balik_probinsya"));
                String age_class = c.getString(c.getColumnIndexOrThrow("health_group"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));
//                String diabetic = c.getString(c.getColumnIndexOrThrow("diabetic"));
//                String hypertension = c.getString(c.getColumnIndexOrThrow("hypertension"));
                String pwd = c.getString(c.getColumnIndexOrThrow("pwd"));
                String pregnant = c.getString(c.getColumnIndexOrThrow("pregnant"));

                String birth_place = c.getString(c.getColumnIndexOrThrow("birth_place"));
                String civil_status = c.getString(c.getColumnIndexOrThrow("civil_status"));
                String religion = c.getString(c.getColumnIndexOrThrow("religion"));
                String other_religion = c.getString(c.getColumnIndexOrThrow("other_religion"));
                String contact = c.getString(c.getColumnIndexOrThrow("contact"));
                String height = c.getString(c.getColumnIndexOrThrow("height"));
                String weight = c.getString(c.getColumnIndexOrThrow("weight"));
                String cancer = c.getString(c.getColumnIndexOrThrow("cancer"));
                String cancer_type = c.getString(c.getColumnIndexOrThrow("cancer_type"));
               /* String mental_med = c.getString(c.getColumnIndexOrThrow("mental_med"));
                String tbdots_med = c.getString(c.getColumnIndexOrThrow("tbdots_med"));
                String cvd_med = c.getString(c.getColumnIndexOrThrow("cvd_med"));*/
                String covid_status = c.getString(c.getColumnIndexOrThrow("covid_status"));
                String menarche = c.getString(c.getColumnIndexOrThrow("menarche"));
                String menarche_age = c.getString(c.getColumnIndexOrThrow("menarche_age"));
                String newborn_screen = c.getString(c.getColumnIndexOrThrow("newborn_screen"));
                String newborn_text = c.getString(c.getColumnIndexOrThrow("newborn_text"));
                String deceased = c.getString(c.getColumnIndexOrThrow("deceased"));
                String deceased_date = c.getString(c.getColumnIndexOrThrow("deceased_date"));
                String immu_stat = c.getString(c.getColumnIndexOrThrow("immu_stat"));
                String nutri_stat = c.getString(c.getColumnIndexOrThrow("nutri_stat"));
                String pwd_desc = c.getString(c.getColumnIndexOrThrow("pwd_desc"));
                String sexually_active = c.getString(c.getColumnIndexOrThrow("sexually_active"));
                String other_med_history = c.getString(c.getColumnIndexOrThrow("other_med_history"));
                String familyPlanning = c.getString(c.getColumnIndexOrThrow("fam_plan"));
                String familyPlanningMethod = c.getString(c.getColumnIndexOrThrow("fam_plan_method"));
                String familyPlanningOtherMethod = c.getString(c.getColumnIndexOrThrow("fam_plan_other_method"));
                String familyPlanningStatus = c.getString(c.getColumnIndexOrThrow("fam_plan_status"));
                String familyPlanningOtherStatus = c.getString(c.getColumnIndexOrThrow("fam_plan_other_status"));
                String unmet = c.getString(c.getColumnIndexOrThrow("unmetNeed"));
                String pregnant_date = c.getString(c.getColumnIndexOrThrow("pregnant"));


                FamilyProfile profile = new FamilyProfile(
                        id + "",
                        uniqueId, familyId, householdNum, philhealth_categ, philId, nhts, fourPs, fourPsNumber,
                        ip, head, relation, member_others, fname, mname, lname, suffix, contact, dob,
                        birth_place, sex, barangayId, muncityId, provinceId, height, weight, civil_status, religion,
                        other_religion, income, waterSupply, sanitaryToilet, educationalAttainment,
                        balik_probinsya, age_class, cancer, cancer_type, status, newborn_screen,
                        newborn_text, deceased, deceased_date, other_med_history, covid_status, menarche,
                        menarche_age, sexually_active, familyPlanning, familyPlanningMethod, familyPlanningOtherMethod, familyPlanningStatus,
                        familyPlanningOtherStatus, unmet, pregnant_date, pwd, pwd_desc);

                if(relation.equalsIgnoreCase("Head")) profiles.add(0, profile);
                else profiles.add(profile);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return profiles;
    }

    public void updateProfileById(String uniqueId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", 0);

        db.update(PROFILES, cv, "uniqueId = ?", new String[]{uniqueId});
    }


    public void deleteProfiles() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PROFILES, null, null);
    }

    public int getProfilesCount(String brgyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + PROFILES;

        if(!brgyId.equals("")) countQuery += " where barangayId = '"+ brgyId +"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public int getProfileUploadableCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + PROFILES + " where status = '1'";

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }





    //    public void addAccoount(Accounts acc) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("category", acc.category);
//        cv.put("site_name", acc.site_name);
//        cv.put("user_name", acc.user_name);
//        cv.put("password", acc.password);
//        db.insert(ACCOUNTS, null, cv);
//        db.close();
//    }
//
    public void addServicesAvail(String request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("request", request);
        db.insert(SERVICES, null, cv);
        db.close();

        Toast.makeText(context, "Succesfully availed", Toast.LENGTH_SHORT).show();
    }

    public int getServicesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + SERVICES;

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public ServiceAvailed getServiceForUpload() {
        ServiceAvailed serviceAvailed = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(SERVICES, null, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            JSONObject request=null;
            String id = c.getString(c.getColumnIndexOrThrow("id"));
            try {
                request = new JSONObject(c.getString(c.getColumnIndexOrThrow("request")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            serviceAvailed = new ServiceAvailed(id, request);
            Log.e("DBHelper", request.toString());
        }
        c.close();
        db.close();
        return serviceAvailed;
    }

    public void deleteService(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SERVICES, "id=?", new String[]{id});
    }

    public void addServiceStatus(ServicesStatus ss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", ss.name);
        cv.put("group1", ss.group1);
        cv.put("group2", ss.group2);
        cv.put("group3", ss.group3);
        cv.put("barangayId", ss.brgyId);
        db.insert(SERVICESTATUS, null, cv);
        db.close();
    }

    public int getServiceStatusCount(String brgyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + SERVICESTATUS;

        if(!brgyId.equals("")) countQuery += " where barangayId = '"+ brgyId +"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public void deleteServiceStatus() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SERVICESTATUS, null, null);
    }

    public ArrayList<ServicesStatus> getServicesStatus(String filter) {
        ArrayList<ServicesStatus> servicesStatuses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(SERVICESTATUS, null, filter, null, null, null, null, "20");

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndexOrThrow("name"));
                String group1 = c.getString(c.getColumnIndexOrThrow("group1"));
                String group2 = c.getString(c.getColumnIndexOrThrow("group2"));
                String group3 = c.getString(c.getColumnIndexOrThrow("group3"));
                String brgyId = c.getString(c.getColumnIndexOrThrow("barangayId"));

                ServicesStatus servicesStatus = new ServicesStatus(name, group1, group2, group3, brgyId);

                servicesStatuses.add(servicesStatus);
                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return servicesStatuses;
    }

    public void addFeedback(FeedBack fb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subject", fb.subject);
        cv.put("body", fb.body);
        db.insert(FEEDBACK, null, cv);
        db.close();
    }

    public void deleteFeedback(String id) {
        SQLiteDatabase db = getWritableDatabase();
        if(id.isEmpty()) db.delete(FEEDBACK, null, null);
        else db.delete(FEEDBACK, "id=?", new String[]{id});
    }

    public String getFeedbacksForUpload() {
        String feedback = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FEEDBACK, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String subject = c.getString(c.getColumnIndexOrThrow("subject"));
                String body = c.getString(c.getColumnIndexOrThrow("body"));

                feedback +=  (c.getPosition() + 1) + ". " + subject + " - " + body + "\n\n";
                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return feedback;
    }

    public ArrayList<FeedBack> getFeedbacks() {
        ArrayList<FeedBack> feedBacks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FEEDBACK, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String id = c.getString(c.getColumnIndexOrThrow("id"));
                String subject = c.getString(c.getColumnIndexOrThrow("subject"));
                String body = c.getString(c.getColumnIndexOrThrow("body"));

                feedBacks.add(new FeedBack(id, subject, body));
                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return feedBacks;
    }

    public int getFeedbacksCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + FEEDBACK;

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }


    /**SPECIALIST*/

    public void deleteSpecialist() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SPECIALIST, null, null);
    }

    public void addSpecialist(SpecialistModel specialist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", specialist.username);
        cv.put("fname", specialist.fname);
        cv.put("mname", specialist.mname);
        cv.put("lname", specialist.lname);
        cv.put("status", specialist.status);
        db.insertWithOnConflict(SPECIALIST, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void updateSpecialist(SpecialistModel specialist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", specialist.username);
        cv.put("fname", specialist.fname);
        cv.put("mname", specialist.mname);
        cv.put("lname", specialist.lname);
        cv.put("status", specialist.status);
        db.update(SPECIALIST, cv, "username=?", new String[]{specialist.username});
        db.close();
    }

    public void updateSpecialistsStatus() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", 0);

        db.update(SPECIALIST, cv, "status = 1", null);
        db.update(FACILITY_ASSIGNMENT, cv, "status = 1", null);
    }

    public ArrayList<SpecialistModel> getSpecialists(String name) {
        name += "%";
        ArrayList<SpecialistModel> specialists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(SPECIALIST, null, "fname LIKE ? or mname LIKE ? or lname LIKE ?", new String[]{name, name, name}, null, null, null, "20");

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String username = c.getString(c.getColumnIndexOrThrow("username"));
                String fname = c.getString(c.getColumnIndexOrThrow("fname"));
                String mname = c.getString(c.getColumnIndexOrThrow("mname"));
                String lname = c.getString(c.getColumnIndexOrThrow("lname"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                SpecialistModel specialist = new SpecialistModel(id + "", username, fname, mname, lname, status);
                specialists.add(specialist);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return specialists;
    }

    public ArrayList<SpecialistModel> getMatchingSpecialists(String first, String middle, String last) {
        ArrayList<SpecialistModel> specialists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(SPECIALIST, null, "fname LIKE ? and mname LIKE ? and lname LIKE ?",
                new String[]{first + "%", middle + "%", last + "%",}, null, null, null );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String username = c.getString(c.getColumnIndexOrThrow("username"));
                String fname = c.getString(c.getColumnIndexOrThrow("fname"));
                String mname = c.getString(c.getColumnIndexOrThrow("mname"));
                String lname = c.getString(c.getColumnIndexOrThrow("lname"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                SpecialistModel specialist = new SpecialistModel(id + "", username, fname, mname, lname, status);
                specialists.add(specialist);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return specialists;
    }

    public ArrayList<SpecialistModel> getSpecialistsForSync() {
        ArrayList<SpecialistModel> specialists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(SPECIALIST, null, "status = 1", null, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String username = c.getString(c.getColumnIndexOrThrow("username"));
                String fname = c.getString(c.getColumnIndexOrThrow("fname"));
                String mname = c.getString(c.getColumnIndexOrThrow("mname"));
                String lname = c.getString(c.getColumnIndexOrThrow("lname"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                SpecialistModel specialist = new SpecialistModel(id + "", username, fname, mname, lname, status);
                specialists.add(specialist);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return specialists;
    }

    public int getSpecialistUploadableCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + SPECIALIST + " where status = '1'";

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public void deleteAffiliatedFacility() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FACILITY_ASSIGNMENT, null, null);
    }

    public void deleteAffiliatedFacilityById(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FACILITY_ASSIGNMENT, "id=?", new String[]{id});
    }

    public void addAffiliatedFacility(AffiliatedFacilitiesModel facility) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", facility.username);
        cv.put("facility_code", facility.facility_code);
        cv.put("specialization", facility.specialization);
        cv.put("contact", facility.contact);
        cv.put("email", facility.email);
        cv.put("schedule", facility.schedule);
        cv.put("fee", facility.fee);
        cv.put("status", facility.status);

        db.insertWithOnConflict(FACILITY_ASSIGNMENT, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void updateAffiliatedFacility(AffiliatedFacilitiesModel facility) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", facility.username);
        cv.put("facility_code", facility.facility_code);
        cv.put("specialization", facility.specialization);
        cv.put("contact", facility.contact);
        cv.put("email", facility.email);
        cv.put("schedule", facility.schedule);
        cv.put("fee", facility.fee);
        cv.put("status", facility.status);

        db.update(FACILITY_ASSIGNMENT, cv, "id=?", new String[]{facility.id});
        db.close();
    }

    public ArrayList<AffiliatedFacilitiesModel> getAffiliatedFacilities(String name) {
        name += "%";
        ArrayList<AffiliatedFacilitiesModel> facilities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FACILITY_ASSIGNMENT, null, "username LIKE ? ", new String[]{name}, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String username = c.getString(c.getColumnIndexOrThrow("username"));
                String facility_code = c.getString(c.getColumnIndexOrThrow("facility_code"));
                String specialization = c.getString(c.getColumnIndexOrThrow("specialization"));
                String contact = c.getString(c.getColumnIndexOrThrow("contact"));
                String email = c.getString(c.getColumnIndexOrThrow("email"));
                String schedule = c.getString(c.getColumnIndexOrThrow("schedule"));
                String fee = c.getString(c.getColumnIndexOrThrow("fee"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                AffiliatedFacilitiesModel facility = new AffiliatedFacilitiesModel(id + "", username, facility_code, specialization, contact, email, schedule, fee, status);
                facilities.add(facility);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return facilities;
    }


    /**Manage facilities*/

    public void deleteFacilities() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FACILITY, null, null);
    }

    public void addFacility(FacilityModel facility) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("facility_code", facility.facility_code);
        cv.put("facility_name", facility.facility_name);
        cv.put("facility_abbr", facility.facility_abbr);
        cv.put("province_id", facility.prov_id);
        cv.put("municipality_id", facility.muncity_id);
        cv.put("barangay_id", facility.brgy_id);
        cv.put("address", facility.address);

        cv.put("contact", facility.contact);
        cv.put("email", facility.email);
        cv.put("chief_hospital", facility.chief_hospital);
        cv.put("license_status", facility.license_status);
        cv.put("service_capability", facility.service_capability);
        cv.put("ownership", facility.ownership);
        cv.put("facility_status", facility.facility_status);
        cv.put("referral_status", facility.referral_status);
        cv.put("phic_status", facility.phic_status);
        cv.put("transport", facility.transport);
        cv.put("latitude", facility.latitude);
        cv.put("longitude", facility.longitude);

        cv.put("sched_day_from", facility.sched_day_from);
        cv.put("sched_day_to", facility.sched_day_to);
        cv.put("sched_time_from", facility.sched_time_from);
        cv.put("sched_time_to", facility.sched_time_to);
        cv.put("sched_notes", facility.sched_notes);

        cv.put("status", facility.status);

        db.insertWithOnConflict(FACILITY, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void updateFacility(FacilityModel facility) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("facility_code", facility.facility_code);
        cv.put("facility_name", facility.facility_name);
        cv.put("facility_abbr", facility.facility_abbr);
        cv.put("province_id", facility.prov_id);
        cv.put("municipality_id", facility.muncity_id);
        cv.put("barangay_id", facility.brgy_id);
        cv.put("address", facility.address);

        cv.put("contact", facility.contact);
        cv.put("email", facility.email);
        cv.put("chief_hospital", facility.chief_hospital);
        cv.put("service_capability", facility.service_capability);
        cv.put("license_status", facility.license_status);
        cv.put("ownership", facility.ownership);
        cv.put("facility_status", facility.facility_status);
        cv.put("referral_status", facility.referral_status);
        cv.put("phic_status", facility.phic_status);
        cv.put("transport", facility.transport);
        cv.put("latitude", facility.latitude);
        cv.put("longitude", facility.longitude);

        cv.put("sched_day_from", facility.sched_day_from);
        cv.put("sched_day_to", facility.sched_day_to);
        cv.put("sched_time_from", facility.sched_time_from);
        cv.put("sched_time_to", facility.sched_time_to);
        cv.put("sched_notes", facility.sched_notes);

        cv.put("status", facility.status);

        db.update(FACILITY, cv, "facility_code=? or id=?", new String[]{facility.facility_code, facility.id});
        db.close();
    }

    public void updateFacilitiesStatus() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", 0);

        db.update(FACILITY, cv, "status = 1", null);
        db.update(FACILITYSERVICES, cv, "status = 1", null);
    }

    public ArrayList<FacilityModel> getFacilities(String name) {
        name += "%";
        ArrayList<FacilityModel> facilities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FACILITY, null, "facility_code LIKE ? or facility_name LIKE ? or facility_abbr LIKE ?", new String[]{name, name, name}, null, null, null, "20");

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String facility_code = c.getString(c.getColumnIndexOrThrow("facility_code"));
                String facility_name = c.getString(c.getColumnIndexOrThrow("facility_name"));
                String facility_abbr = c.getString(c.getColumnIndexOrThrow("facility_abbr"));
                String province_id = c.getString(c.getColumnIndexOrThrow("province_id"));
                String municipality_id = c.getString(c.getColumnIndexOrThrow("municipality_id"));
                String barangay_id = c.getString(c.getColumnIndexOrThrow("barangay_id"));
                String address = c.getString(c.getColumnIndexOrThrow("address"));

                String contact = c.getString(c.getColumnIndexOrThrow("contact"));
                String email = c.getString(c.getColumnIndexOrThrow("email"));
                String chief_hospital = c.getString(c.getColumnIndexOrThrow("chief_hospital"));
                String service_capability = c.getString(c.getColumnIndexOrThrow("service_capability"));
                String license_status = c.getString(c.getColumnIndexOrThrow("license_status"));
                String ownership = c.getString(c.getColumnIndexOrThrow("ownership"));
                String facility_status = c.getString(c.getColumnIndexOrThrow("facility_status"));
                String phic_status = c.getString(c.getColumnIndexOrThrow("phic_status"));
                String referral_status = c.getString(c.getColumnIndexOrThrow("referral_status"));
                String transport = c.getString(c.getColumnIndexOrThrow("transport"));
                String latitude = c.getString(c.getColumnIndexOrThrow("latitude"));
                String longitude = c.getString(c.getColumnIndexOrThrow("longitude"));


                String sched_day_from = c.getString(c.getColumnIndexOrThrow("sched_day_from"));
                String sched_day_to = c.getString(c.getColumnIndexOrThrow("sched_day_to"));
                String sched_time_from = c.getString(c.getColumnIndexOrThrow("sched_time_from"));
                String sched_time_to = c.getString(c.getColumnIndexOrThrow("sched_time_to"));
                String sched_notes = c.getString(c.getColumnIndexOrThrow("sched_notes"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                FacilityModel facility =  new FacilityModel(id +"", facility_code, facility_name, facility_abbr, province_id, municipality_id, barangay_id, address, contact,email,
                        chief_hospital, service_capability, license_status, ownership, facility_status,
                        referral_status, phic_status,  transport, latitude, longitude,
                        sched_day_from, sched_day_to, sched_time_from, sched_time_to, sched_notes, status );
                facilities.add(facility);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return facilities;
    }

    public ArrayList<FacilityModel> getMatchingFacilities(String  code, String name, String abbr) {
        ArrayList<FacilityModel> facilities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FACILITY, null, "facility_code LIKE ? and facility_name LIKE ? and facility_abbr LIKE ?",
                new String[]{code + "%", name + "%", abbr + "%",}, null, null, null );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String facility_code = c.getString(c.getColumnIndexOrThrow("facility_code"));
                String facility_name = c.getString(c.getColumnIndexOrThrow("facility_name"));
                String facility_abbr = c.getString(c.getColumnIndexOrThrow("facility_abbr"));
                String province_id = c.getString(c.getColumnIndexOrThrow("province_id"));
                String municipality_id = c.getString(c.getColumnIndexOrThrow("municipality_id"));
                String barangay_id = c.getString(c.getColumnIndexOrThrow("barangay_id"));
                String address = c.getString(c.getColumnIndexOrThrow("address"));

                String contact = c.getString(c.getColumnIndexOrThrow("contact"));
                String email = c.getString(c.getColumnIndexOrThrow("email"));
                String chief_hospital = c.getString(c.getColumnIndexOrThrow("chief_hospital"));
                String service_capability = c.getString(c.getColumnIndexOrThrow("service_capability"));
                String license_status = c.getString(c.getColumnIndexOrThrow("license_status"));
                String ownership = c.getString(c.getColumnIndexOrThrow("ownership"));
                String facility_status = c.getString(c.getColumnIndexOrThrow("facility_status"));
                String phic_status = c.getString(c.getColumnIndexOrThrow("phic_status"));
                String referral_status = c.getString(c.getColumnIndexOrThrow("referral_status"));
                String transport = c.getString(c.getColumnIndexOrThrow("transport"));
                String latitude = c.getString(c.getColumnIndexOrThrow("latitude"));
                String longitude = c.getString(c.getColumnIndexOrThrow("longitude"));


                String sched_day_from = c.getString(c.getColumnIndexOrThrow("sched_day_from"));
                String sched_day_to = c.getString(c.getColumnIndexOrThrow("sched_day_to"));
                String sched_time_from = c.getString(c.getColumnIndexOrThrow("sched_time_from"));
                String sched_time_to = c.getString(c.getColumnIndexOrThrow("sched_time_to"));
                String sched_notes = c.getString(c.getColumnIndexOrThrow("sched_notes"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                FacilityModel facility =  new FacilityModel(id +"", facility_code, facility_name, facility_abbr, province_id, municipality_id, barangay_id, address, contact,email,
                        chief_hospital, service_capability, license_status, ownership, facility_status,
                        referral_status, phic_status,  transport, latitude, longitude,
                        sched_day_from, sched_day_to, sched_time_from, sched_time_to, sched_notes, status );

                facilities.add(facility);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return facilities;
    }

    public int getFacilityUploadableCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + FACILITY + " where status = '1'";

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public int getFacilityTotalCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + FACILITY;

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<FacilityModel> getFacilitiesForSync() {
        ArrayList<FacilityModel> facilities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FACILITY, null, "status = 1", null, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String facility_code = c.getString(c.getColumnIndexOrThrow("facility_code"));
                String facility_name = c.getString(c.getColumnIndexOrThrow("facility_name"));
                String facility_abbr = c.getString(c.getColumnIndexOrThrow("facility_abbr"));
                String province_id = c.getString(c.getColumnIndexOrThrow("province_id"));
                String municipality_id = c.getString(c.getColumnIndexOrThrow("municipality_id"));
                String barangay_id = c.getString(c.getColumnIndexOrThrow("barangay_id"));
                String address = c.getString(c.getColumnIndexOrThrow("address"));

                String contact = c.getString(c.getColumnIndexOrThrow("contact"));
                String email = c.getString(c.getColumnIndexOrThrow("email"));
                String chief_hospital = c.getString(c.getColumnIndexOrThrow("chief_hospital"));
                String service_capability = c.getString(c.getColumnIndexOrThrow("service_capability"));
                String license_status = c.getString(c.getColumnIndexOrThrow("license_status"));
                String ownership = c.getString(c.getColumnIndexOrThrow("ownership"));
                String facility_status = c.getString(c.getColumnIndexOrThrow("facility_status"));
                String phic_status = c.getString(c.getColumnIndexOrThrow("phic_status"));
                String referral_status = c.getString(c.getColumnIndexOrThrow("referral_status"));
                String transport = c.getString(c.getColumnIndexOrThrow("transport"));
                String latitude = c.getString(c.getColumnIndexOrThrow("latitude"));
                String longitude = c.getString(c.getColumnIndexOrThrow("longitude"));


                String sched_day_from = c.getString(c.getColumnIndexOrThrow("sched_day_from"));
                String sched_day_to = c.getString(c.getColumnIndexOrThrow("sched_day_to"));
                String sched_time_from = c.getString(c.getColumnIndexOrThrow("sched_time_from"));
                String sched_time_to = c.getString(c.getColumnIndexOrThrow("sched_time_to"));
                String sched_notes = c.getString(c.getColumnIndexOrThrow("sched_notes"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                FacilityModel facility =  new FacilityModel(id +"", facility_code, facility_name, facility_abbr, province_id, municipality_id, barangay_id, address, contact,email,
                        chief_hospital, service_capability, license_status, ownership, facility_status,
                        referral_status, phic_status,  transport, latitude, longitude,
                        sched_day_from, sched_day_to, sched_time_from, sched_time_to, sched_notes, status );
                facilities.add(facility);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return facilities;
    }


    public String getFacilityCodeByName(String name) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FACILITY, null, "facility_name LIKE ?", new String[]{name+"%"}, null, null, null);

        if (c.moveToFirst()) {
            id = c.getString(c.getColumnIndexOrThrow("facility_code")) + "";
            c.moveToNext();
        }
        c.close();
        db.close();
        return id;
    }

    public String getFacilityNameByCode(String code) {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FACILITY, null, "facility_code LIKE ?", new String[]{code+"%"}, null, null, null);

        if (c.moveToFirst()) {
            name = c.getString(c.getColumnIndexOrThrow("facility_name"));
            c.moveToNext();
        }
        c.close();
        db.close();
        return name;
    }

    public ArrayList<String> getFacilityNames() {
        ArrayList<String> facilities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FACILITY, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndexOrThrow("facility_name"));
                facilities.add(name);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return facilities;
    }

    /**Manage facilities - Services Cost**/
    public void deleteFacilityServices() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FACILITYSERVICES, null, null);
    }

    public void deleteFacilityService(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FACILITYSERVICES, "id=?", new String[]{id});
    }

    public void addFacilityService(FacilityService facility) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("facility_code", facility.facility_code);
        cv.put("service_type", facility.service_type);
        cv.put("service", facility.service);
        cv.put("cost", facility.cost);

        cv.put("status", facility.status);

        db.insertWithOnConflict(FACILITYSERVICES, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public ArrayList<FacilityService> getFacilityServices(String name) {
        name += "%";
        ArrayList<FacilityService> facilityServices = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(FACILITYSERVICES, null, "facility_code LIKE ?", new String[]{name}, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String facility_code = c.getString(c.getColumnIndexOrThrow("facility_code"));
                String service_type = c.getString(c.getColumnIndexOrThrow("service_type"));
                String service = c.getString(c.getColumnIndexOrThrow("service"));
                String cost = c.getString(c.getColumnIndexOrThrow("cost"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                FacilityService facility =  new FacilityService(id+"", facility_code, service_type,
                        service, cost,  status );
                facilityServices.add(facility);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return facilityServices;
    }

    public void updateFacilityService(FacilityService facility){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("facility_code", facility.facility_code);
        cv.put("service_type", facility.service_type);
        cv.put("service", facility.service);
        cv.put("cost", facility.cost);
        cv.put("status", facility.status);

        db.update(FACILITYSERVICES, cv, "id=?", new String[]{facility.id});
        db.close();
    }

    /** PROVINCE, MUNCITY, BRGY**/
    public void deleteProvinces() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PROVINCE, null, null);
    }

    public void addProvince(ProvinceModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("id", model.id);
        cv.put("name", model.name);

        db.insertWithOnConflict(PROVINCE, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public int getProvincesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + PROVINCE;

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<String> getProvinceNames() {
        ArrayList<String> provinces = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(PROVINCE, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndexOrThrow("name"));
                provinces.add(name);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return provinces;
    }

    public String getProvIdByName(String provName) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(PROVINCE, null, "name LIKE ?", new String[]{provName+"%"}, null, null, null);

        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndexOrThrow("id")) + "";
                c.moveToNext();
        }
        c.close();
        db.close();
        return id;
    }

    public String getProvNameById(String id) {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(PROVINCE, null, "id LIKE ?", new String[]{id+"%"}, null, null, null);

        if (c.moveToFirst()) {
            name = c.getString(c.getColumnIndexOrThrow("name"));
            c.moveToNext();
        }
        c.close();
        db.close();
        return name;
    }


    public void deleteMunCity() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MUNCITY, null, null);
    }

    public void addMuncity(MuncityModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("id", model.id);
        cv.put("name", model.name);
        cv.put("prov_id", model.prov_id);

        db.insertWithOnConflict(MUNCITY, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public int getMuncityCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + MUNCITY;

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<String> getMuncityNamesByProv(String prov) {
        ArrayList<String> muncities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MUNCITY, null, "prov_id LIKE ?", new String[]{prov+"%"}, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndexOrThrow("name"));
                muncities.add(name);
                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return muncities;
    }

    public String getMuncityIdByNameProv(String name, String prov) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MUNCITY, null, "name LIKE ? and prov_id LIKE ?", new String[]{name+"%", prov+"%"}, null, null, null);

        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndexOrThrow("id")) + "";
            c.moveToNext();
        }
        c.close();
        db.close();
        return id;
    }

    public String getMuncityNameById(String id) {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MUNCITY, null, "id LIKE ?", new String[]{id+"%"}, null, null, null);

        if (c.moveToFirst()) {
            name = c.getString(c.getColumnIndexOrThrow("name"));
            c.moveToNext();
        }
        c.close();
        db.close();
        return name;
    }

    public void deleteBrgy() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(BRGY, null, null);
    }

    public void addBrgy(BarangayModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("id", model.id);
        cv.put("name", model.name);
        cv.put("prov_id", model.prov_id);
        cv.put("muncity_id", model.muncity_id);

        db.insertWithOnConflict(BRGY, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public int getBrgyCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + BRGY;

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<String> getBrgyNamesByProvMuncity(String prov, String muncity) {
        ArrayList<String> brgys = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(BRGY, null, "prov_id LIKE ? and muncity_id LIKE ?", new String[]{prov+"%", muncity+"%"}, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndexOrThrow("name"));
                brgys.add(name);
                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return brgys;
    }

    public String getBrgyIdByNameProvMuncity(String name, String prov, String muncity) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(BRGY, null, "name LIKE ? and prov_id LIKE ? and muncity_id LIKE ?", new String[]{name+"%", prov+"%", muncity+"%"}, null, null, null);

        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndexOrThrow("id")) + "";
            c.moveToNext();
        }
        c.close();
        db.close();
        return id;
    }

    public String getBrgyNameById(String id) {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(BRGY, null, "id LIKE ?", new String[]{id+"%"}, null, null, null);

        if (c.moveToFirst()) {
            name = c.getString(c.getColumnIndexOrThrow("name"));
            c.moveToNext();
        }
        c.close();
        db.close();
        return name;
    }

    /**Manage Profiles - Medication Avail**/
    public void deleteProfileMedications() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PROFILE_MEDICATION, null, null);
    }

    public void deleteProfileMedication(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PROFILE_MEDICATION, "id=?", new String[]{id});
    }

    public void addProfileMedication(ProfileMedication medication) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("uniqueId", medication.uniqueId);
        cv.put("type", medication.type);
        cv.put("medication_status", medication.medication_status);
        cv.put("remarks", medication.remarks);
        cv.put("status", medication.status);

        db.insertWithOnConflict(PROFILE_MEDICATION, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public ArrayList<ProfileMedication> getProfileMedications(String name) {
        name += "%";
        ArrayList<ProfileMedication> profileMedications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(PROFILE_MEDICATION, null, "uniqueId LIKE ?", new String[]{name}, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                String uniqueId = c.getString(c.getColumnIndexOrThrow("uniqueId"));
                String type = c.getString(c.getColumnIndexOrThrow("type"));
                String medication_status = c.getString(c.getColumnIndexOrThrow("medication_status"));
                String remarks = c.getString(c.getColumnIndexOrThrow("remarks"));
                String status = c.getString(c.getColumnIndexOrThrow("status"));

                ProfileMedication profileMedication =  new ProfileMedication(id+"", uniqueId, type,
                        medication_status, remarks,  status );
                profileMedications.add(profileMedication);

                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return profileMedications;
    }

    public void updateProfileMedication(ProfileMedication medication){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("uniqueId", medication.uniqueId);
        cv.put("type", medication.type);
        cv.put("medication_status", medication.medication_status);
        cv.put("remarks", medication.remarks);
        cv.put("status", medication.status);

        db.update(PROFILE_MEDICATION, cv, "id=?", new String[]{medication.id});
        db.close();
    }

}