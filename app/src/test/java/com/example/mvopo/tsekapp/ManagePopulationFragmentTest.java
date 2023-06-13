package com.example.mvopo.tsekapp;

import static org.junit.jupiter.api.Assertions.*;

import com.example.mvopo.tsekapp.Model.FamilyProfile;

import org.junit.jupiter.api.Test;

class ManagePopulationFragmentTest {

    @Test
    void checkFamilyProfileFields() {
        String famId = "1234";
        String fname = "TestFname";
        String mname = "TestMname";
        String lname = "TestLname";
        String suffix = "III";
        String householdNum = "123";
        String philhealth_categ = "direct";
        String philId = "4321";
        String nhts = "yes";
        String fourPs = "yes";
        String fourPsNumber = "1234";
        String ip = "no";
        String head = "yes";
        String relation = "head";
        String member_others = "";
        String contact = "09090909090";
        String bday = "1990-10-10";
        String birth_place = "Cebu City";
        String sex = "Female";
        String brgy = "1444";
        String muncityId = "Cebu City";
        String provinceId = "Cebu";
        String height = "160";
        String weight = "60";
        String civil_status = "Married";
        String religion = "others";
        String other_religion = "test";
        String income = "1income";
        String supply = "1supply";
        String toilet = "sewage";
        String education = "college_grad";
        String balik_probinsya = "yes";
        String age_group = "A";
        String cancer = "yes";
        String cancer_type = "cancer";
        String status = "1";
        String newborn_screen = "";
        String newborn_text = "";
        String deceased = "no";
        String deceased_date = "0000-00-00";
        String other_med_history = "asthma";
        String covid_status = "Booster Dose";
        String menarche = "yes";
        String menarche_age = "10";
        String sexually_active = "yes";
        String fam_plan = "yes";
        String fam_plan_method = "other";
        String fam_plan_other_method = "famPlanOther";
        String fam_plan_status = "other";
        String fam_plan_other_status = "otherStatus";
        String unmetNeed = "0";
        String pregnant = "2023-11-11";
        String pwd = "yes";
        String pwd_desc = "pwdTest";
        FamilyProfile familyProfile = new FamilyProfile("1", fname + mname + lname + suffix, famId, householdNum, philhealth_categ, philId, nhts, fourPs, fourPsNumber, ip, head, relation, member_others, fname,
                mname, lname, suffix, contact, bday, birth_place, sex, brgy, muncityId, provinceId, height, weight, civil_status, religion, other_religion, income, supply, toilet, education, balik_probinsya, age_group,
                cancer, cancer_type, status, newborn_screen, newborn_text, deceased, deceased_date, other_med_history, covid_status, menarche,menarche_age, sexually_active, fam_plan, fam_plan_method,fam_plan_other_method,
                fam_plan_status, fam_plan_other_status, unmetNeed, pregnant, pwd, pwd_desc);
        assertNotNull (familyProfile);
        assertEquals("1", familyProfile.id);
        assertEquals(fname + mname + lname + suffix, familyProfile.uniqueId);
        assertEquals(householdNum, familyProfile.household_num);
        assertEquals(philhealth_categ, familyProfile.philhealth_categ);
        assertEquals(philId, familyProfile.philId);
        assertEquals(nhts, familyProfile.nhts);
        assertEquals(fname, familyProfile.fname);
        assertEquals(mname, familyProfile.mname);
        assertEquals(lname, familyProfile.lname);
        assertEquals(suffix, familyProfile.suffix);
        assertEquals(fourPs, familyProfile.four_ps);
        assertEquals(fourPsNumber, familyProfile.fourps_num);
        assertEquals(ip, familyProfile.ip);
        assertEquals(head, familyProfile.isHead);
        assertEquals(relation, familyProfile.relation);
        assertEquals(member_others, familyProfile.member_others);
        assertEquals(contact, familyProfile.contact);
        assertEquals(bday, familyProfile.dob);
        assertEquals(birth_place, familyProfile.birth_place);
        assertEquals(sex, familyProfile.sex);
        assertEquals(brgy, familyProfile.barangayId);
        assertEquals(muncityId, familyProfile.muncityId);
        assertEquals(provinceId, familyProfile.provinceId);
        assertEquals(height, familyProfile.height);
        assertEquals(weight, familyProfile.weight);
        assertEquals(civil_status, familyProfile.civil_status);
        assertEquals(religion, familyProfile.religion);
        assertEquals(other_religion, familyProfile.other_religion);
        assertEquals(income, familyProfile.income);
        assertEquals(supply, familyProfile.waterSupply);
        assertEquals(toilet, familyProfile.sanitaryToilet);
        assertEquals(education, familyProfile.educationalAttainment);
        assertEquals(balik_probinsya, familyProfile.balik_probinsya);
        assertEquals(age_group, familyProfile.health_group);
        assertEquals(cancer, familyProfile.cancer);
        assertEquals(cancer_type, familyProfile.cancer_type);
        assertEquals(status, familyProfile.status);
        assertEquals(newborn_screen, familyProfile.newborn_screen);
        assertEquals(newborn_text, familyProfile.newborn_text);
        assertEquals(deceased, familyProfile.deceased);
        assertEquals(deceased_date, familyProfile.deceased_date);
        assertEquals(other_med_history, familyProfile.other_med_history);
        assertEquals(covid_status, familyProfile.covid_status);
        assertEquals(menarche, familyProfile.menarche);
        assertEquals(menarche_age, familyProfile.menarche_age);
        assertEquals(sexually_active, familyProfile.sexually_active);
        assertEquals(fam_plan, familyProfile.fam_plan);
        assertEquals(fam_plan_method, familyProfile.fam_plan_method);
        assertEquals(fam_plan_other_method, familyProfile.fam_plan_other_method);
        assertEquals(fam_plan_status, familyProfile.fam_plan_status);
        assertEquals(fam_plan_other_status, familyProfile.fam_plan_other_status);
        assertEquals(unmetNeed, familyProfile.unmetNeed);
        assertEquals(pregnant, familyProfile.pregnant);
        assertEquals(pwd, familyProfile.pwd);
        assertEquals(pwd_desc, familyProfile.pwd_desc);
    }
}