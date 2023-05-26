package com.example.mvopo.tsekapp.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvopo.tsekapp.Helper.AgeClass;
import com.example.mvopo.tsekapp.Model.ProfileMedication;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvopo.tsekapp.Helper.ListAdapter;
import com.example.mvopo.tsekapp.MainActivity;
import com.example.mvopo.tsekapp.Model.Constants;
import com.example.mvopo.tsekapp.Model.FamilyProfile;
import com.example.mvopo.tsekapp.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mvopo on 10/20/2017.
 * Revised by romainelorena on 02/2022
 */

public class ManagePopulationFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private final int CAMERA_CODE = 100;
    LinearLayout headFields;
    ScrollView optionHolder;
    EditText txtFamilyId, txtPhilHealthId, txtNhts, txtFname, txtMname, txtLname, txtBday, txtBrgy,
             txtEducation, txtSuffix, txtSex, txtIncome, txtUnmet, txtSupply, txtToilet, txtRelation,
            txtFacilityName, txtListNumber, txtDoseScreen, txtDoseDate, txtDoseLot, txtDoseBatch, txtDoseExpiration,
            txtDoseAefi, txtRemarks, txtAgeClass;

    CheckBox checkBox4p, checkBoxNHTS, checkBoxIP;
    TextView  tvStatus;
    Button manageBtn, optionBtn, manageDengvaxia, dengvaxiaRegisterBtn;
    TextInputLayout unmetFrame;
    View view;
    ImageView ivPatient;

    // UPDATE
    EditText txtDiabetic, txtHypertension, txtPWD, txtPregnantDate, txtIsPregnant;
    TextInputLayout til_isPregnantFrame, til_pregnantDate;
    /*END*/

    FamilyProfile familyProfile;
    List<FamilyProfile> matchingProfiles = new ArrayList<>();

    String famId, philId, nhts, fname, mname, lname, bday, brgy, head, relation, education, suffix, sex, income, unmet, supply, toilet;

    // UPDATE
    String diabetic, hypertension, pwd, pregnant_date, isPregnant;   /*END*/

    // UPDATE - Romaine
    EditText  txtBirthPlace, txtCivil, txtReligion, txtOtherReligion, txtContact, txtHeight, txtWeight, txtCancer,txtCancerType, txtMental, txtTBDOTS, txtCVD,
            txtCovid, txtPwdDesc, txtMenarche, txtMenarcheAge, txtDecease, txtDeceasedDate, txtImmunization, txtNutrition, txtNewbornScreen, txtNewbornText, txtSexually,
    txtIP, txtFourPs, txtBalikProbinsya,txtOther_relation, txtFamilyPlanning;

    String birth_place, civil_status, religion, other_religion, contact, height, weight, cancer, cancer_type, mental_med,
        tbdots_med,cvd_med, covid_status, menarche, menarche_age, newborn_screen, newborn_text, deceased, deceased_date,
        immu_stat, nutri_stat, pwd_desc, sexually_active, ip, fourPs, balik_probinsya, member_others, fourPsNumber, age_class;

    TextInputLayout til_otherReligion, til_pwdDesc, til_cancerType, til_deceased, til_deceasedDate, til_menarche, til_menarcheAge, til_newborn, til_sexually, til_otherRelation, til_family_planning;
    String[] brgyIds;
    LinearLayout layout_adult, layout_age5;


    TextInputLayout til_mental, til_cvd, til_diabetic, til_hypertension, til_tbdots, til_familyPlanningMethod, til_familyPlanningStatus, til_familyPlanningMethodOther, til_familyPlanningStatusOther;
    EditText txtMentalRemarks, txtCvdRemarks, txtDiabeticRemarks, txtHypertensionRemarks, txtTbdotsRemarks, txtManageFourPNumber, txtFamilyPlanningMethod, txtFamilyPlanningStatus,
    txtFamilyPlanningMethodOther, txtFamilyPlanningStatusOther;
    String mental_remarks, cvd_remarks, diabetic_remarks, hypertension_remarks, tbdots_remarks;


    ArrayList<ProfileMedication> profileMedicationsList = new ArrayList<>();
    ProfileMedication profileMedication;
    /*END*/

    String[] brgys, value;
    int age = 0;
    boolean brgyFieldClicked = false;
    boolean toUpdate, addHead;


    String males = "Son, Husband, Father, Brother, Nephew, Grandfather, Grandson, Son in Law, Brother in Law, Father in Law";
    String females = "Daughter, Wife, Mother, Sister, Niece, Grandmother, Granddaugther, Daughter in Law, Sister in Law, Mother in Law";

    Calendar now = Calendar.getInstance();
    DatePickerDialog dpd = DatePickerDialog.newInstance(
            ManagePopulationFragment.this,
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
    );

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_member, container, false);
        findViewByIds();

        toUpdate = getArguments().getBoolean("toUpdate");
        addHead = getArguments().getBoolean("addHead");
        familyProfile = getArguments().getParcelable("familyProfile");

        if(familyProfile!=null)
        profileMedicationsList = MainActivity.db.getProfileMedications(familyProfile.uniqueId);
       // Log.e("med", ""+profileMedicationsList.size());
        //Toast.makeText(getContext(), addHead+"", Toast.LENGTH_SHORT).show();
        dpd.setMaxDate(now);
        value = getResources().getStringArray(R.array.educational_attainment_value);
        try {
            JSONArray array = new JSONArray(MainActivity.user.barangay);

            brgys = new String[array.length()];
            brgyIds = new String[array.length()];

            for (int i = 0; i < array.length(); i++) {
                brgys[i] = array.getJSONObject(i).getString("description");
                brgyIds[i] = array.getJSONObject(i).getString("barangay_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//       manageDengvaxia = view.findViewById(R.id.manageDengvaxia);

        txtBalikProbinsya.setOnClickListener(this);
        txtCovid.setOnClickListener(this);

        txtBrgy.setOnClickListener(this);
        txtBday.setOnClickListener(this);
        txtEducation.setOnClickListener(this);

//        if (!addHead){
//            txtHead.setOnClickListener(this);
//            txtHead.setText("NO");
//        }
//        else {
//            txtHead.setText("YES");
//        }

        checkBox4p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtManageFourPNumber.setVisibility(View.VISIBLE);
                } else {
                    txtManageFourPNumber.setVisibility(View.GONE);
                }
            }
        });
        txtRelation.setOnClickListener(this);
        txtSex.setOnClickListener(this);
        txtSuffix.setOnClickListener(this);
        txtIncome.setOnClickListener(this);
        txtUnmet.setOnClickListener(this);
        txtSupply.setOnClickListener(this);
        txtToilet.setOnClickListener(this);
        txtDiabetic.setOnClickListener(this);
        txtHypertension.setOnClickListener(this);
        txtPWD.setOnClickListener(this);
        txtPregnantDate.setOnClickListener(this);
        txtIsPregnant.setOnClickListener(this);
        //      manageDengvaxia.setOnClickListener(this);
        //update r
        txtMental.setOnClickListener(this);
        txtTBDOTS.setOnClickListener(this);
        txtCVD.setOnClickListener(this);
        txtCivil.setOnClickListener(this);
        txtReligion.setOnClickListener(this);
        txtDecease.setOnClickListener(this);
        txtCancer.setOnClickListener(this);
        txtDeceasedDate.setOnClickListener(this);
        txtMenarche.setOnClickListener(this);
        txtNewbornScreen.setOnClickListener(this);
        txtImmunization.setOnClickListener(this);
        txtNutrition.setOnClickListener(this);
        txtSexually.setOnClickListener(this);
        txtFamilyPlanning.setOnClickListener(this);
        txtFamilyPlanningMethod.setOnClickListener(this);
        txtFamilyPlanningStatus.setOnClickListener(this);
        //end
        manageBtn.setOnClickListener(this);

        Constants.setDateTextWatcher(getContext(), txtBday);
        Constants.setDateTextWatcher(getContext(), txtDeceasedDate);
        Constants.setDateTextWatcher(getContext(), txtPregnantDate);


        /*txtHead.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtHead.getText().toString().equalsIgnoreCase("NO")) { //NOT head
                    headFields.setVisibility(View.GONE);
                    txtRelation.setText("Son");
                    ManagePopulationFragment.this.view.findViewById(R.id.layout_relation).setVisibility(View.VISIBLE);
                } else { //HEAD
                    txtRelation.setText("");
                    txtOther_relation.setText("");
                    ManagePopulationFragment.this.view.findViewById(R.id.layout_relation).setVisibility(View.GONE);
                    ManagePopulationFragment.this.view.findViewById(R.id.layout_other_relation).setVisibility(View.GONE);

                    headFields.setVisibility(View.VISIBLE);
                }
            }
        });*/

        txtRelation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtRelation.getText().toString().trim().equalsIgnoreCase("Others")) til_otherRelation.setVisibility(View.VISIBLE);
                else til_otherRelation.setVisibility(View.GONE);
            }
        });



        txtBday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String age1 = Constants.getAge(txtBday.getText().toString(), Calendar.getInstance())/*.split(" ")[0]*/;
                if(age1.contains("m/o") || age1.contains("d/o")){
                    age =0;
                    if(age1.contains("d/o")){
                        if(Integer.parseInt(age1.split(" ")[0]) < 29){
                            txtAgeClass.setText(AgeClass.NEWBORN.getStringValue());
                        }
                    }
                    if(age1.contains("m/o")){
                        txtAgeClass.setText(AgeClass.INFANT.getStringValue());
                    }
                }else {
                    age = Integer.parseInt(age1.split(" ")[0]);
                    if(age < 2 && age >= 0){
                        txtAgeClass.setText(AgeClass.INFANT.getStringValue());
                    }
                    else if(age < 5 && age >= 2){
                        txtAgeClass.setText(AgeClass.PSAC.getStringValue());
                    }
                    else if(age < 10 && age >= 5){
                        txtAgeClass.setText(AgeClass.SCHOOLAGE.getStringValue());
                    }
                    else if(age < 20 && age >= 10){
                        txtAgeClass.setText(AgeClass.ADOLESCENT.getStringValue());
                    }
                    else if(age < 60 && age >= 20){
                        txtAgeClass.setText(AgeClass.ADULT.getStringValue());
                    }
                    else if(age >= 60){
                        txtAgeClass.setText(AgeClass.SENIOR.getStringValue());
                    }

                }

                if(age < 5){
                    layout_adult.setVisibility(View.GONE);
                    layout_age5.setVisibility(View.VISIBLE);
                }else {
                    layout_adult.setVisibility(View.VISIBLE);
                    layout_age5.setVisibility(View.GONE);
                }

                if (age > 5 && txtSex.getText().toString().equalsIgnoreCase("Female")){
                    til_menarche.setVisibility(View.VISIBLE);
                }else {
                    til_menarche.setVisibility(View.GONE);
                    til_menarcheAge.setVisibility(View.GONE);
                }

                if ((age >= 10 && age<=49) && txtSex.getText().toString().equalsIgnoreCase("Female")){
                    unmetFrame.setVisibility(View.VISIBLE);
                    til_isPregnantFrame.setVisibility(View.VISIBLE);
                    til_sexually.setVisibility(View.VISIBLE);
                    til_family_planning.setVisibility(View.VISIBLE);
                }else {
                    unmetFrame.setVisibility(View.GONE);
                    til_isPregnantFrame.setVisibility(View.GONE);
                    til_pregnantDate.setVisibility(View.GONE);
                    til_sexually.setVisibility(View.GONE);
                    til_family_planning.setVisibility(View.GONE);
                }
            }
        });

        txtSex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                if (age > 5 && txtSex.getText().toString().equalsIgnoreCase("Female")){ //6 and above
                    if(!txtMenarche.getText().toString().trim().isEmpty()){
                        String txt = txtMenarche.getText().toString().trim();
                        txtMenarche.setText(txt);
                    }
                    til_menarche.setVisibility(View.VISIBLE);
                }else {
                    til_menarche.setVisibility(View.GONE);
                    til_menarcheAge.setVisibility(View.GONE);
                }

                if ((age >= 10 && age<=49) && txtSex.getText().toString().equalsIgnoreCase("Female")){
                    unmetFrame.setVisibility(View.VISIBLE);
                    til_isPregnantFrame.setVisibility(View.VISIBLE);
                    til_sexually.setVisibility(View.VISIBLE);
                    til_family_planning.setVisibility(View.VISIBLE);
                }else {
                    unmetFrame.setVisibility(View.GONE);
                    til_isPregnantFrame.setVisibility(View.GONE);
                    til_pregnantDate.setVisibility(View.GONE);
                    til_sexually.setVisibility(View.GONE);
                    til_family_planning.setVisibility(View.GONE);
                }
            }
        });

        txtMenarche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtMenarche.getText().toString().trim().equalsIgnoreCase("Yes"))
                     til_menarcheAge.setVisibility(View.VISIBLE);
                else til_menarcheAge.setVisibility(View.GONE);
            }
        });

        txtIsPregnant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtIsPregnant.getText().toString().trim().equalsIgnoreCase("Yes"))
                    til_pregnantDate.setVisibility(View.VISIBLE);
                else til_pregnantDate.setVisibility(View.GONE);
            }
        });

        txtNewbornScreen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtNewbornScreen.getText().toString().trim().equalsIgnoreCase("Yes"))
                    til_newborn.setVisibility(View.VISIBLE);
                else til_newborn.setVisibility(View.GONE);
            }
        });

        txtReligion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtReligion.getText().toString().trim().equalsIgnoreCase("Others")) til_otherReligion.setVisibility(View.VISIBLE);
                else til_otherReligion.setVisibility(View.GONE);
            }
        });

        txtPWD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtPWD.getText().toString().trim().equalsIgnoreCase("Yes")) til_pwdDesc.setVisibility(View.VISIBLE);
                else til_pwdDesc.setVisibility(View.GONE);
            }
        });

        txtCancer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtCancer.getText().toString().trim().equalsIgnoreCase("Yes")) til_cancerType.setVisibility(View.VISIBLE);
                else til_cancerType.setVisibility(View.GONE);
            }
        });

        txtDecease.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtDecease.getText().toString().trim().equalsIgnoreCase("Yes")) til_deceasedDate.setVisibility(View.VISIBLE);
                else til_deceasedDate.setVisibility(View.GONE);
            }
        });

        txtMental.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                txtMentalRemarks.setText("");
                if (txtMental.getText().toString().trim().equalsIgnoreCase("Medication Avail"))
                {
                    til_mental.setVisibility(View.VISIBLE);
                    til_mental.setHint("Facility where medicine was availed(tap again for choices)");
                    txtMentalRemarks.setOnClickListener(ManagePopulationFragment.this);
                }
                else if (txtMental.getText().toString().trim().equalsIgnoreCase("No Medication Avail")) {
                    til_mental.setVisibility(View.VISIBLE);
                    til_mental.setHint("Reason for not availing medicine");
                    txtMentalRemarks.setOnClickListener(null);
                }
                else {
                    txtMentalRemarks.setText("");
                    til_mental.setVisibility(View.GONE);
                }
            }
        });

        txtTBDOTS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                txtTbdotsRemarks.setText("");
                if (txtTBDOTS.getText().toString().trim().equalsIgnoreCase("Medication Avail"))
                {
                    til_tbdots.setVisibility(View.VISIBLE);
                    til_tbdots.setHint("Facility where medicine was availed(tap again for choices)");
                    txtTbdotsRemarks.setOnClickListener(ManagePopulationFragment.this);
                }
                else if (txtTBDOTS.getText().toString().trim().equalsIgnoreCase("No Medication Avail")) {
                    til_tbdots.setVisibility(View.VISIBLE);
                    til_tbdots.setHint("Reason for not availing medicine");
                    txtTbdotsRemarks.setOnClickListener(null);
                }
                else {
                    txtTbdotsRemarks.setText("");
                    til_tbdots.setVisibility(View.GONE);
                }
            }
        });

        txtCVD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                txtCvdRemarks.setText("");
                if (txtCVD.getText().toString().trim().equalsIgnoreCase("Medication Avail"))
                {
                    til_cvd.setVisibility(View.VISIBLE);
                    til_cvd.setHint("Facility where medicine was availed(tap again for choices)");
                    txtCvdRemarks.setOnClickListener(ManagePopulationFragment.this);
                }
                else if (txtCVD.getText().toString().trim().equalsIgnoreCase("No Medication Avail")) {
                    til_cvd.setVisibility(View.VISIBLE);
                    til_cvd.setHint("Reason for not availing medicine");
                    txtCvdRemarks.setOnClickListener(null);
                }
                else {
                    txtCvdRemarks.setText("");
                    til_cvd.setVisibility(View.GONE);
                }
            }
        });

        txtDiabetic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                txtDiabeticRemarks.setText("");
                if (txtDiabetic.getText().toString().trim().equalsIgnoreCase("Medication Avail"))
                {
                    til_diabetic.setVisibility(View.VISIBLE);
                    til_diabetic.setHint("Facility where medicine was availed(tap again for choices)");
                    txtDiabeticRemarks.setOnClickListener(ManagePopulationFragment.this);
                }
                else if (txtDiabetic.getText().toString().trim().equalsIgnoreCase("No Medication Avail")) {
                    til_diabetic.setVisibility(View.VISIBLE);
                    til_diabetic.setHint("Reason for not availing medicine");
                    txtDiabeticRemarks.setOnClickListener(null);
                }
                else {
                    txtDiabeticRemarks.setText("");
                    til_diabetic.setVisibility(View.GONE);
                }
            }
        });

        txtHypertension.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                txtHypertensionRemarks.setText("");
                if (txtHypertension.getText().toString().trim().equalsIgnoreCase("Medication Avail"))
                {
                    til_hypertension.setVisibility(View.VISIBLE);
                    til_hypertension.setHint("Facility where medicine was availed(tap again for choices)");
                    txtHypertensionRemarks.setOnClickListener(ManagePopulationFragment.this);
                }
                else if (txtHypertension.getText().toString().trim().equalsIgnoreCase("No Medication Avail")) {
                    til_hypertension.setVisibility(View.VISIBLE);
                    til_hypertension.setHint("Reason for not availing medicine");
                    txtHypertensionRemarks.setOnClickListener(null);
                }
                else {
                    txtHypertensionRemarks.setText("");
                    til_hypertension.setVisibility(View.GONE);
                }
            }
        });

        txtFamilyPlanning.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                txtFamilyPlanningMethod.setText("");
                txtFamilyPlanningStatus.setText("");
                if (txtFamilyPlanning.getText().toString().trim().equalsIgnoreCase("Yes")){
                    til_familyPlanningMethod.setVisibility(View.VISIBLE);
                    til_familyPlanningMethod.setHint("Family Planning Methods Used");
                    til_familyPlanningStatus.setVisibility(View.VISIBLE);
                    til_familyPlanningStatus.setHint("Family Planning Status");
                }
                else {
                    txtFamilyPlanningMethod.setText("");
                    til_familyPlanningMethod.setVisibility(View.GONE);
                    txtFamilyPlanningStatus.setText("");
                    til_familyPlanningStatus.setVisibility(View.GONE);

                }
            }
        });

        txtFamilyPlanningMethod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                txtFamilyPlanningMethodOther.setText("");
                if(txtFamilyPlanningMethod.getText().toString().trim().equalsIgnoreCase("Others (Specify)")){
                    til_familyPlanningMethodOther.setVisibility(View.VISIBLE);
                    txtFamilyPlanningMethodOther.setHint("(Other Family Planning Methods)");
                }
                else {
                    til_familyPlanningMethodOther.setVisibility(View.GONE);
                    txtFamilyPlanningStatusOther.setText("");
                }
            }
        });

        txtFamilyPlanningStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                txtFamilyPlanningStatusOther.setText("");
                if(txtFamilyPlanningStatus.getText().toString().trim().equalsIgnoreCase("Others (Specify)")){
                    System.out.println(txtFamilyPlanningStatusOther.getText().toString().trim());
                    til_familyPlanningStatusOther.setVisibility(View.VISIBLE);
                    txtFamilyPlanningStatusOther.setHint("(Other Family Planning Status)");
                }
                else {
                    til_familyPlanningStatusOther.setVisibility(View.GONE);
                    txtFamilyPlanningStatusOther.setHint("");
                }
            }
        });
        if (!toUpdate){
            showProfileCheckerDialog();
            til_deceasedDate.setVisibility(View.GONE);
            til_deceased.setVisibility(View.GONE);
        }

        txtFamilyId.setText(familyProfile.familyId);
        if (!toUpdate) {
            //view.findViewById(R.id.layout_head).setVisibility(View.GONE);
//            if (!addHead || txtHead.getText().toString().equalsIgnoreCase("NO")) {
//                headFields.setVisibility(View.GONE);
//                view.findViewById(R.id.layout_relation).setVisibility(View.VISIBLE);
//            }
            manageBtn.setText("Add");
        } else {
            try{
                if(Integer.parseInt(Constants.getAge(familyProfile.dob, Calendar.getInstance())) > 8){
                    manageDengvaxia.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){}
            setFieldTexts();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // UPDATE
            case R.id.manage_covid:
                showOptionDialog(R.array.covid_vaccine, txtCovid);
                break;
            case R.id.manage_balikprobinsya:
                showOptionDialog(R.array.yes_no, txtBalikProbinsya);
                break;
            case R.id.manage_immunization:
                showChecklistDialog(R.array.immunization, txtImmunization);
                break;
            case R.id.manage_nutrition:
                showChecklistDialog(R.array.nutrition, txtNutrition);
                break;

            case R.id.manage_diabetes:
                showOptionDialog(R.array.medication_availment, txtDiabetic);
                break;
            case R.id.manage_diabetes_remarks:
                showOptionDialog(R.array.facility_availment, txtDiabeticRemarks);
                break;

            case R.id.manage_hypertension:
                showOptionDialog(R.array.medication_availment, txtHypertension);
                break;
            case R.id.manage_hypertension_remarks:
                showOptionDialog(R.array.facility_availment, txtHypertensionRemarks);
                break;

            case R.id.manage_mental:
                showOptionDialog(R.array.medication_availment, txtMental);
                break;
            case R.id.manage_mental_remarks:
                showOptionDialog(R.array.facility_availment, txtMentalRemarks);
                break;

            case R.id.manage_tbdots:
                showOptionDialog(R.array.medication_availment, txtTBDOTS);
                break;
            case R.id.manage_tbdots_remarks:
                showOptionDialog(R.array.facility_availment, txtTbdotsRemarks);
                break;

            case R.id.manage_cvd:
                showOptionDialog(R.array.medication_availment, txtCVD);
                break;
            case R.id.manage_cvd_remarks:
                showOptionDialog(R.array.facility_availment, txtCvdRemarks);
                break;

            case R.id.manage_religion:
                showOptionDialog(R.array.religion, txtReligion);

                break;
            case R.id.manage_civil:
                showOptionDialog(R.array.civil_status, txtCivil);
                break;
            case R.id.manage_cancer:
                showOptionDialog(R.array.yes_no, txtCancer);
                break;
            case R.id.manage_menarche:
                showOptionDialog(R.array.yes_no, txtMenarche);
                break;
            case R.id.manage_sexually:
                showOptionDialog(R.array.yes_no, txtSexually);
                break;
            case R.id.manage_family_planning:
                showOptionDialog(R.array.yes_no, txtFamilyPlanning);
                break;
            case R.id.manage_family_planning_method_remarks:
                showOptionDialog(R.array.family_planning_methods, txtFamilyPlanningMethod);
                break;
            case R.id.manage_family_planning_status_remarks:
                showOptionDialog(R.array.family_planning_status, txtFamilyPlanningStatus);
                break;
            case R.id.manage_newborn:
                showOptionDialog(R.array.yes_no, txtNewbornScreen);
                break;

            case R.id.manage_deceased:
                showOptionDialog(R.array.yes_no, txtDecease);
                break;
            case R.id.manage_deceased_date:
                dpd.show(getActivity().getFragmentManager(), "deceased");
                break;
            case R.id.manage_pwd:
                showOptionDialog(R.array.yes_no, txtPWD);
                break;
            case R.id.manage_isPregnant:
                showOptionDialog(R.array.yes_no, txtIsPregnant);
                break;
//                 END
            case R.id.manage_education:
                showOptionDialog(R.array.educational_attainment, txtEducation);
                break;
            case R.id.manage_relation:
                showOptionDialog(R.array.relation_to_head, txtRelation);
                break;
            case R.id.manage_sex:
                showOptionDialog(R.array.sex, txtSex);
                break;
            case R.id.manage_suffix:
                showOptionDialog(R.array.suffix, txtSuffix);
                break;
            case R.id.manage_bday:
                dpd.show(getActivity().getFragmentManager(), "bday");
                break;
            case R.id.manage_pregnantDate:
                dpd.show(getActivity().getFragmentManager(), "lmp");
                break;
            case R.id.manage_income:
                showOptionDialog(R.array.monthly_income, txtIncome);
                break;
            case R.id.manage_unmet:
                showOptionDialog(R.array.unmet_needs, txtUnmet);
                break;
            case R.id.manage_supply:
                showOptionDialog(R.array.water_supply, txtSupply);
                break;
            case R.id.manage_toilet:
                showOptionDialog(R.array.sanitary_toilet, txtToilet);
                break;
            case R.id.manage_barangay:
                brgyFieldClicked = true;
                showOptionDialog(0, txtBrgy);
                break;
//            case R.id.manageDengvaxia:
////                showDengvaxiaDialog();
//
//                bundle.putParcelable("familyProfile", familyProfile);
//
//                DengvaxiaFormFragment dff = new DengvaxiaFormFragment();
//                dff.setArguments(bundle);
//
//                MainActivity.ft = MainActivity.fm.beginTransaction();
//                MainActivity.ft.replace(R.id.fragment_container, dff).addToBackStack(null).commit();
//                break;

            case R.id.dengvaxia_dose_screen:
                showOptionDialog(R.array.yes_no, txtDoseScreen);
                break;
            case R.id.dengvaxia_dose_date:
                dpd.show(getActivity().getFragmentManager(), "dose_date");
                break;
            case R.id.dengvaxia_dose_expiration:
                dpd.show(getActivity().getFragmentManager(), "dose_expiry");
                break;
            case R.id.dengvaxia_dose_aefi:
                showOptionDialog(R.array.yes_no, txtDoseAefi);
                break;
            case R.id.dengvaxia_patient_image:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                CAMERA_CODE);
                        break;
                    }
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_CODE);

                break;

/*            case R.id.dengvaxiaBtn:
                String doseDate = txtDoseDate.getText().toString().trim();
                if (doseDate.isEmpty() && doseDate.length() != 10) {
                    Toast.makeText(getContext(), "Invalid date, please follow YYYY-MM-DD format", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        JSONObject request = new JSONObject();
                        request.accumulate("tsekap_id", familyProfile.id);
                        request.accumulate("facility_name", txtFacilityName.getText().toString());
                        request.accumulate("list_number", txtListNumber.getText().toString());
                        request.accumulate("fname", familyProfile.fname);
                        request.accumulate("mname", familyProfile.mname);
                        request.accumulate("lname", familyProfile.lname);
                        request.accumulate("muncity", familyProfile.muncityId);
                        request.accumulate("barangay", familyProfile.barangayId);
                        request.accumulate("dob", familyProfile.dob);
                        request.accumulate("sex", familyProfile.sex);
                        request.accumulate("dose_screened", txtDoseScreen.getText().toString());
                        request.accumulate("dose_date_given", doseDate);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date;
                        Calendar c = Calendar.getInstance();

                        try {
                            date = sdf.parse(doseDate);
                            c.setTime(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String age = Constants.getAge(familyProfile.dob, c);

                        request.accumulate("dose_age", age);

                        if(!age.contains("/") && (Integer.parseInt(age) >= 9 &&
                                Integer.parseInt(age) <= 14)) request.accumulate("validation", "Yes");
                        else request.accumulate("validation", "No");

                        request.accumulate("dose_lot_no", txtDoseLot.getText().toString());
                        request.accumulate("dose_batch_no", txtDoseBatch.getText().toString());
                        request.accumulate("dose_expiration", txtDoseExpiration.getText().toString());
                        request.accumulate("dose_AEFI", txtDoseAefi.getText().toString());
                        request.accumulate("remarks", txtRemarks.getText().toString());

                        MainActivity.pd = ProgressDialog.show(getContext(), "Loading", "Please wait...", false, false);
                        JSONApi.getInstance(getContext()).dengvaxiaRegister(Constants.dengvaxiaUrl + "cmd=register", request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;*/

            case R.id.manageBtn: //saving or updating
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
//                head = txtHead.getText().toString().trim();

                fname = txtFname.getText().toString().trim();
                mname = txtMname.getText().toString().trim();
                lname = txtLname.getText().toString().trim();
                suffix = txtSuffix.getText().toString().trim();
                bday = txtBday.getText().toString().trim();
                brgy = txtBrgy.getText().toString().trim();
                sex = txtSex.getText().toString().trim();

                civil_status = txtCivil.getText().toString().trim();
                covid_status = txtCovid.getText().toString().trim();

                tbdots_med = txtTBDOTS.getText().toString().trim();
                tbdots_remarks = txtTbdotsRemarks.getText().toString().trim();

                cvd_med = txtCVD.getText().toString().trim();
                cvd_remarks = txtCvdRemarks.getText().toString().trim();

                mental_med = txtMental.getText().toString().trim();
                mental_remarks = txtMentalRemarks.getText().toString().trim();

                diabetic = txtDiabetic.getText().toString().trim();
                diabetic_remarks = txtDiabeticRemarks.getText().toString().trim();

                hypertension = txtHypertension.getText().toString().trim();
                hypertension_remarks = txtHypertensionRemarks.getText().toString().trim();


                religion = txtReligion.getText().toString().trim();
                if(religion.trim().equalsIgnoreCase("others")) other_religion = txtOtherReligion.getText().toString().trim();
                else other_religion = "";

                if(head.trim().equalsIgnoreCase("NO")){
                    relation = txtRelation.getText().toString().trim();
                    if(relation.equalsIgnoreCase("Live-in Partner")) {relation = "partner";}

                    if(relation.equalsIgnoreCase("Others")) {
                        member_others = txtOther_relation.getText().toString().trim();
                    } else member_others ="";

                    income="0";
                    supply="0";
                    toilet="";
                }else { //HEAD
                    relation="Head"; member_others ="";
                    try { income = txtIncome.getTag().toString();
                    } catch (Exception e) {
                        income = familyProfile.income;
                    }

                    try {
                        supply = txtSupply.getTag().toString();
                    } catch (Exception e) {
                        supply = familyProfile.waterSupply;
                    }

                    try {
                        toilet = txtToilet.getTag().toString();
                    } catch (Exception e) {
                        toilet = familyProfile.sanitaryToilet;
                    }
                }


                if (!mental_med.isEmpty() && mental_remarks.isEmpty()) {
                    txtMentalRemarks.setError("Required");
                    txtMentalRemarks.requestFocus();
                } else if (!tbdots_med.isEmpty() && tbdots_remarks.isEmpty()) {
                    txtTbdotsRemarks.setError("Required");
                    txtTbdotsRemarks.requestFocus();
                }  else if (!cvd_med.isEmpty() && cvd_remarks.isEmpty()) {
                    txtCvdRemarks.setError("Required");
                    txtCvdRemarks.requestFocus();
                }else if (age>5 && !diabetic.isEmpty() && diabetic_remarks.isEmpty()) {
                    txtDiabeticRemarks.setError("Required");
                    txtDiabeticRemarks.requestFocus();
                }else if (age>5 && !hypertension.isEmpty() && hypertension_remarks.isEmpty()) {
                    txtHypertensionRemarks.setError("Required");
                    txtHypertensionRemarks.requestFocus();
                }else if (fname.isEmpty()) {
                    txtFname.setError("Required");
                    txtFname.requestFocus();
                } else if (mname.isEmpty()) {
                    txtMname.setError("Required");
                    txtMname.requestFocus();
                }else if (lname.isEmpty()) {
                    txtLname.setError("Required");
                    txtLname.requestFocus();
                } else if (bday.length() != 10) {
                    Toast.makeText(getContext(), "Invalid date, please follow YYYY-MM-DD format", Toast.LENGTH_SHORT).show();
                    txtBday.setError("Invalid");
                    txtBday.requestFocus();
                } else if (sex.isEmpty()) {
                    txtSex.setError("Required");
                    txtSex.requestFocus();
                }else if (civil_status.isEmpty()) {
                    txtCivil.setError("Required");
                    txtCivil.requestFocus();
                }else if (religion.isEmpty()) {
                    txtReligion.setError("Required");
                    txtReligion.requestFocus();
                } else if (religion.equalsIgnoreCase("others")) {
                    txtOtherReligion.setError("Required");
                    txtOtherReligion.requestFocus();
                } else if (relation.isEmpty() && head.equalsIgnoreCase("no")) {
                    txtRelation.setError("Required");
                    txtRelation.requestFocus();
                } else if (relation.equalsIgnoreCase("others") && head.equalsIgnoreCase("no") && member_others.isEmpty()) {
                    txtOther_relation.setError("Required");
                    txtOther_relation.requestFocus();
                }else if (supply.isEmpty() && head.equalsIgnoreCase("yes")) {
                    txtSupply.setError("Required");
                    txtSupply.requestFocus();
                } else if (toilet.isEmpty() && head.equalsIgnoreCase("yes")) {
                    txtToilet.setError("Required");
                    txtToilet.requestFocus();
                } else if (brgy.isEmpty())
                {
                    Toast.makeText(getContext(), "Barangay required", Toast.LENGTH_SHORT).show();
                } else { // filled out all required fields
                    if (brgyFieldClicked) brgy = txtBrgy.getTag().toString();
                    else brgy = familyProfile.barangayId;

                    //reading the rest of fields
                    famId = txtFamilyId.getText().toString().trim();
                    philId = txtPhilHealthId.getText().toString().trim();
                    nhts = txtNhts.getText().toString().trim();
                    ip = txtIP.getText().toString().trim();
                    fourPs = txtFourPs.getText().toString().trim();
                    fourPsNumber = txtManageFourPNumber.getText().toString().trim();
                    balik_probinsya = txtBalikProbinsya.getText().toString().trim();
                    birth_place = txtBirthPlace.getText().toString().trim();
                    contact = txtContact.getText().toString().trim();
                    height = txtHeight.getText().toString().trim();
                    weight = txtWeight.getText().toString().trim();
                    age_class = txtAgeClass.getText().toString().trim();

                    cancer = txtCancer.getText().toString().trim();
                    if(cancer.trim().equalsIgnoreCase("NO")) cancer_type = "";
                    else cancer_type = txtCancerType.getText().toString().trim();

                    pwd = txtPWD.getText().toString().trim();
                    if(pwd.trim().equalsIgnoreCase("NO")) pwd_desc = "";
                    else pwd_desc = txtPwdDesc.getText().toString().trim();

                    // Female inputs
                    if(age > 5 && sex.equalsIgnoreCase("Female")){
                        menarche = txtMenarche.getText().toString().trim();
                        menarche_age = txtMenarcheAge.getText().toString().trim();
                    }
                    else {
                        menarche="";
                        menarche_age="";
                    }

                    if ((age >= 10 && age<=49) && sex.equalsIgnoreCase("Female")) {
                        pregnant_date = txtPregnantDate.getText().toString().trim();
                        sexually_active = txtSexually.getText().toString().trim();
                        try {
                            unmet = txtUnmet.getTag().toString();
                        } catch (Exception e) {
                            unmet = familyProfile.unmetNeed;
                        }
                    }else {
                        pregnant_date="";
                        sexually_active = "";
                        unmet="0";
                    }

                    saveUpdateProfileMedication(txtMental, "Mental Health Medication", mental_remarks);
                    saveUpdateProfileMedication(txtTBDOTS, "TB Medication", tbdots_remarks);
                    saveUpdateProfileMedication(txtCVD, "CVD Medication", cvd_remarks);
                    saveUpdateProfileMedication(txtDiabetic, "Diabetic", diabetic_remarks);
                    saveUpdateProfileMedication(txtHypertension, "Hypertension", hypertension_remarks);

                    // Adult and child inputs
                    if(age < 5){
                       /* diabetic="";
                        hypertension="";*/

                        newborn_screen = txtNewbornScreen.getText().toString().trim();
                        if(newborn_screen.equalsIgnoreCase("NO")) newborn_text = "";
                        else newborn_text = txtNewbornText.getText().toString().trim();

                        immu_stat = txtImmunization.getText().toString().trim();
                        nutri_stat = txtNutrition.getText().toString().trim();
                    }else{
                       /* diabetic = txtDiabetic.getText().toString().trim();
                        hypertension = txtHypertension.getText().toString().trim();*/

                        newborn_screen="";
                        newborn_text = "";
                        immu_stat = "";
                        nutri_stat = "";
                    }

                    try {
                        education = txtEducation.getTag().toString();
                    } catch (Exception e) {
                        education = familyProfile.educationalAttainment;
                    }


                    if (toUpdate){
                        deceased = txtDecease.getText().toString().trim();
                        if(deceased.trim().equalsIgnoreCase("NO")) deceased_date = "";
                        else deceased_date = txtDeceasedDate.getText().toString().trim();
                    } else{
                        deceased = txtDecease.getText().toString().trim();
                        deceased_date = "";
                    }


                    //saving or updating
                    if (manageBtn.getText().toString().equalsIgnoreCase("ADD")) {
                        if (addHead) {
                            head = "YES";
                            relation = "Head";
                        } else head = "NO";

                        FamilyProfile newFamilyProfile = new FamilyProfile(
                                "",
                                fname + mname + lname + suffix + brgy + MainActivity.user.muncity,
                                famId, philId, nhts,fourPs, ip, head, relation, member_others,  fname, lname, mname, suffix, bday, sex, brgy,
                                MainActivity.user.muncity,
                                "", income, unmet, supply, toilet, education, balik_probinsya,"1",  pwd, pregnant_date,
                                birth_place, civil_status, religion, other_religion, contact, height, weight, cancer, cancer_type,
                                covid_status, menarche, menarche_age, newborn_screen, newborn_text, deceased, deceased_date,
                                immu_stat, nutri_stat, pwd_desc, sexually_active);
                        MainActivity.db.addProfile(newFamilyProfile);
                        Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                    } else {
                        if (relation.isEmpty()) relation = familyProfile.relation;
                        if (head.equalsIgnoreCase("Yes")) relation = "Head";

                        FamilyProfile updatedFamilyProfile = new FamilyProfile(familyProfile.id, familyProfile.uniqueId, famId, philId,
                                nhts,fourPs, ip, head, relation, member_others, fname, lname, mname, suffix, bday, sex, brgy, familyProfile.muncityId, familyProfile.provinceId,
                                income, unmet, supply, toilet, education, balik_probinsya, "1", pwd,
                                pregnant_date, birth_place, civil_status, religion, other_religion, contact, height, weight, cancer, cancer_type,
                                covid_status, menarche, menarche_age, newborn_screen, newborn_text, deceased, deceased_date,
                                immu_stat, nutri_stat, pwd_desc, sexually_active);
                        MainActivity.db.updateProfile(updatedFamilyProfile);
                        Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                    }
                    MainActivity.fm.popBackStack();
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            Log.e("ManagePop", data.toString());
            switch (requestCode) {
                case CAMERA_CODE:
                    Uri imageUri = data.getData();
                    CropImage.activity(imageUri)
                            .setFixAspectRatio(true)
                            .start(getContext(), this);
                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    ivPatient.setImageURI(resultUri);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_CODE);
                }else{
                    Toast.makeText(getContext(), "Camera Permission Denied, cant proceed this action", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void setFieldTexts() {
        txtFamilyId.setText(familyProfile.familyId);
        txtPhilHealthId.setText(familyProfile.philId);
        txtNhts.setText(familyProfile.nhts);
        txtFourPs.setText(familyProfile.four_ps);
        txtIP.setText(familyProfile.ip);
        txtBalikProbinsya.setText(familyProfile.balik_probinsya);

        txtFname.setText(familyProfile.fname);
        txtMname.setText(familyProfile.mname);
        txtLname.setText(familyProfile.lname);
        txtBday.setText(familyProfile.dob);

        txtBrgy.setText(Constants.getBrgyName(familyProfile.barangayId));
        txtSuffix.setText(familyProfile.suffix);
        txtCovid.setText(familyProfile.covid_status);
        txtBirthPlace.setText(familyProfile.birth_place);
        txtCivil.setText(familyProfile.civil_status);
        txtContact.setText(familyProfile.contact);
        txtHeight.setText(familyProfile.height);
        txtWeight.setText(familyProfile.weight);

        //txtHead.setText(familyProfile.isHead);

        if (familyProfile.relation.equalsIgnoreCase("partner")) {
            txtRelation.setText("Live-in Partner");
        }
        else {
            txtRelation.setText(familyProfile.relation);
        }

        txtOther_relation.setText(familyProfile.member_others);

        txtSex.setText(familyProfile.sex);

        txtMenarche.setText(familyProfile.menarche);
        txtMenarcheAge.setText(familyProfile.menarche_age);

        txtSexually.setText(familyProfile.sexually_active);

        if(familyProfile.pregnant.isEmpty() || familyProfile.pregnant.equalsIgnoreCase("0000-00-00")){
            txtIsPregnant.setText("NO");
        } else {
            txtIsPregnant.setText("YES");
            txtPregnantDate.setText(familyProfile.pregnant);
        }

        txtReligion.setText(familyProfile.religion);
        txtOtherReligion.setText(familyProfile.other_religion);

        txtDecease.setText(familyProfile.deceased);
        txtDeceasedDate.setText(familyProfile.deceased_date);

        txtPWD.setText(familyProfile.pwd);
        txtPwdDesc.setText(familyProfile.pwd_desc);

        txtCancer.setText(familyProfile.cancer);
        txtCancerType.setText(familyProfile.cancer_type);

        //age <5
        txtNewbornScreen.setText(familyProfile.newborn_screen);
        txtNewbornText.setText(familyProfile.newborn_text);
        txtNutrition.setText(familyProfile.nutri_stat);
        txtImmunization.setText(familyProfile.immu_stat);

        //adult
      /*  txtDiabetic.setText(familyProfile.diabetic);
        txtHypertension.setText(familyProfile.hypertension);
        txtMental.setText(familyProfile.mental_med);
        txtTBDOTS.setText(familyProfile.tbdots_med);
        txtCVD.setText(familyProfile.cvd_med);*/

        for(ProfileMedication med : profileMedicationsList){
            switch (med.type){
                case "Mental Health Medication":
                    txtMental.setText(med.medication_status);
                    txtMental.setTag(med.id);
                    txtMentalRemarks.setText(med.remarks);
                    break;
                case "TB Medication":
                    txtTBDOTS.setText(med.medication_status);
                    txtTBDOTS.setTag(med.id);
                    txtTbdotsRemarks.setText(med.remarks);
                    break;
                case "CVD Medication":
                    txtCVD.setText(med.medication_status);
                    txtCVD.setTag(med.id);
                    txtCvdRemarks.setText(med.remarks);
                    break;
                case "Hypertension":
                    txtHypertension.setText(med.medication_status);
                    txtHypertension.setTag(med.id);
                    txtHypertensionRemarks.setText(med.remarks);
                    break;
                case "Diabetic":
                    txtDiabetic.setText(med.medication_status);
                    txtDiabetic.setTag(med.id);
                    txtDiabeticRemarks.setText(med.remarks);
                    break;
            }
        }




        for (int i = 0; i < value.length; i++) {
            if (familyProfile.educationalAttainment.equals(value[i])) {
                txtEducation.setText(getResources()
                        .getStringArray(R.array.educational_attainment)[i]);
                break;
            }
        }
        Log.e("MPF", familyProfile.income + " " + familyProfile.unmetNeed + " " + familyProfile.waterSupply + " " + familyProfile.sanitaryToilet);
        if (!familyProfile.income.isEmpty() && !familyProfile.income.equals("0"))
            txtIncome.setText(getResources().getStringArray(R.array.monthly_income)[Integer.parseInt(familyProfile.income) - 1]);
        if (!familyProfile.unmetNeed.isEmpty() && !familyProfile.unmetNeed.equals("0"))
            txtUnmet.setText(getResources().getStringArray(R.array.unmet_needs)[Integer.parseInt(familyProfile.unmetNeed) - 1]);
        if (!familyProfile.waterSupply.isEmpty() && !familyProfile.waterSupply.equals("0"))
            txtSupply.setText(getResources().getStringArray(R.array.water_supply)[Integer.parseInt(familyProfile.waterSupply) - 1]);
        try {
            if (!familyProfile.sanitaryToilet.isEmpty() && !familyProfile.sanitaryToilet.equals("0"))
                txtToilet.setText(getResources().getStringArray(R.array.sanitary_toilet)[Integer.parseInt(familyProfile.sanitaryToilet) - 1]);
        } catch (Exception e) {
            String toilet = familyProfile.sanitaryToilet;

            if (toilet.equals("non")) {
                txtToilet.setText(getResources().getStringArray(R.array.sanitary_toilet)[0]);
            } else if (toilet.equals("comm")) {
                txtToilet.setText(getResources().getStringArray(R.array.sanitary_toilet)[1]);
            } else {
                txtToilet.setText(getResources().getStringArray(R.array.sanitary_toilet)[2]);
            }
        }

    }

    public void showOptionDialog(final int id, final EditText txtView) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.options_dialog, null);
        optionHolder = view.findViewById(R.id.option_holder);
        optionBtn = view.findViewById(R.id.optionBtn);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        String[] labels;

        if (id != 0) labels = getResources().getStringArray(id);
        else labels = brgys;

        final RadioGroup radioGroup = new RadioGroup(getContext());

        LinearLayout.LayoutParams rbParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rbParam.bottomMargin = 5;
        rbParam.topMargin = 5;

        for (int i = 0; i < labels.length; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setLayoutParams(rbParam);
            radioButton.setText(labels[i]);

            if (txtView.getId() == R.id.manage_barangay)
                radioButton.setId(Integer.parseInt(brgyIds[i]));
            else if (txtView.getId() == R.id.manage_education) {
                radioButton.setTag(value[i]);
            } else if (txtView.getId() == R.id.manage_income || txtView.getId() == R.id.manage_unmet ||
                    txtView.getId() == R.id.manage_supply || txtView.getId() == R.id.manage_toilet) {
                radioButton.setId(i + 1);
            }
            View lineView = new View(getContext());
            lineView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            lineView.setLayoutParams(params);

            radioGroup.addView(radioButton);
            radioGroup.addView(lineView);
        }

        //((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        optionHolder.addView(radioGroup);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        final AlertDialog optionDialog = builder.create();
        optionDialog.show();

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                if (radioButton != null) {

                    if(txtView.getId() == R.id.manage_mental || txtView.getId() == R.id.manage_cvd || txtView.getId() == R.id.manage_hypertension || txtView.getId() == R.id.manage_diabetes || txtView.getId() == R.id.manage_tbdots){
                        if(radioButton.getText().toString().trim().equalsIgnoreCase("Clear Choice")){
                            txtView.setText("");
                        }else {
                            txtView.setText(radioButton.getText());
                        }
                    }
                    else {
                        txtView.setText(radioButton.getText());

                        if (txtView.getId() == R.id.manage_barangay) {
                            txtView.setTag(radioButton.getId());
                            txtBrgy = txtView;

                        } else if (txtView.getId() == R.id.manage_education) {
                            txtView.setTag(radioButton.getTag().toString());

                        } else if (txtView.getId() == R.id.manage_income || txtView.getId() == R.id.manage_unmet || txtView.getId() == R.id.manage_supply || txtView.getId() == R.id.manage_toilet) {
                            txtView.setTag(radioButton.getId());

                        } else if (txtView.getId() == R.id.manage_relation) {
                            String relation = txtView.getText().toString();
                            if (males.contains(relation)) txtSex.setText("Male");
                            else if (females.contains(relation)) txtSex.setText("Female");
                            else txtSex.setText("");
                        }
                    }


                } else {
                    txtView.setText("");
                    txtView.setTag("");
                }
                optionDialog.dismiss();
            }
        });
    }

    private String selectedCheckbox = "";
    public void showChecklistDialog(final int id, final EditText txtView) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.options_dialog, null);
        optionHolder = view.findViewById(R.id.option_holder);
        optionBtn = view.findViewById(R.id.optionBtn);
        selectedCheckbox = "";

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        final RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        final String[] labels;

        if (id != 0) labels = getResources().getStringArray(id);
        else labels = brgys;

        final LinearLayout.LayoutParams rbParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rbParam.bottomMargin = 5;
        rbParam.topMargin = 5;

        final LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        final CheckBox[] checkBoxes= new CheckBox[labels.length];
        for (int i = 0; i < labels.length; i++) {

            CheckBox cb = new CheckBox(getContext());
            cb.setLayoutParams(rbParam);
            cb.setText(labels[i]);

            checkBoxes[i]=cb;

            View lineView = new View(getContext());
            lineView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            lineView.setLayoutParams(params);

            ll.addView(cb);
            ll.addView(lineView);
        }
        optionHolder.addView(ll);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        final AlertDialog optionDialog = builder.create();
        optionDialog.show();

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < labels.length; i++) {
                    if(checkBoxes[i].isChecked()){
                        selectedCheckbox +=(checkBoxes[i].getText().toString().trim() + ", ");
                    }
                }

                if(!selectedCheckbox.equalsIgnoreCase("")) {
                    txtView.setText(selectedCheckbox);
                    txtView.setTag("");

                } else {
                    txtView.setText("");
                    txtView.setTag("");
                }
                optionDialog.dismiss();
            }
        });

    }


//    public void showDengvaxiaDialog() {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.dengvaxia_dialog, null);
//
//        tvStatus = view.findViewById(R.id.dengvaxia_status);
//        txtFacilityName = view.findViewById(R.id.dengvaxia_facility_name);
//        txtListNumber = view.findViewById(R.id.dengvaxia_list_number);
//        txtDoseScreen = view.findViewById(R.id.dengvaxia_dose_screen);
//        txtDoseDate = view.findViewById(R.id.dengvaxia_dose_date);
//        txtDoseLot = view.findViewById(R.id.dengvaxia_dose_lot);
//        txtDoseBatch = view.findViewById(R.id.dengvaxia_dose_batch);
//        txtDoseExpiration = view.findViewById(R.id.dengvaxia_dose_expiration);
//        txtDoseAefi = view.findViewById(R.id.dengvaxia_dose_aefi);
//        txtRemarks = view.findViewById(R.id.dengvaxia_remarks);
//        dengvaxiaRegisterBtn = view.findViewById(R.id.dengvaxiaBtn);
//        ivPatient = view.findViewById(R.id.dengvaxia_patient_image);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setView(view);
//        builder.show();
//
//        txtDoseScreen.setOnClickListener(this);
//        txtDoseDate.setOnClickListener(this);
//        txtDoseExpiration.setOnClickListener(this);
//        txtDoseAefi.setOnClickListener(this);
//        dengvaxiaRegisterBtn.setOnClickListener(this);
//        ivPatient.setOnClickListener(this);
//
//        Constants.setDateTextWatcher(getContext(), txtDoseDate);
//        Constants.setDateTextWatcher(getContext(), txtDoseExpiration);
//
//        MainActivity.pd = ProgressDialog.show(getContext(), "Loading", "Please wait...", false, false);
//        JSONApi.getInstance(getContext()).getDengvaxiaDetails(Constants.dengvaxiaUrl + "cmd=dose&id="+familyProfile.id);
//    }

//    public void setDengvaxiaDetails(DengvaxiaDetails details){
//        tvStatus.setText("STATUS: " + details.getStatus().toUpperCase());
//        txtFacilityName.setText(details.getFacilityName());
//        txtListNumber.setText(details.getListNumber());
//        txtDoseScreen.setText(details.getDoseScreen());
//        txtDoseDate.setText(details.getDoseDate());
//        txtDoseLot.setText(details.getDoseLot());
//        txtDoseBatch.setText(details.getDoseBatch());
//        txtDoseExpiration.setText(details.getDoseExpiry());
//        txtDoseAefi.setText(details.getDoseAefi());
//        txtRemarks.setText(details.getRemarks());
//
//        txtFacilityName.setEnabled(false);
//        txtListNumber.setEnabled(false);
//        txtDoseScreen.setEnabled(false);
//        txtDoseDate.setEnabled(false);
//        txtDoseLot.setEnabled(false);
//        txtDoseBatch.setEnabled(false);
//        txtDoseExpiration.setEnabled(false);
//        txtDoseAefi.setEnabled(false);
//        txtRemarks.setEnabled(false);
//
//        dengvaxiaRegisterBtn.setVisibility(View.GONE);
//
//        MainActivity.pd.dismiss();
//    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        switch (view.getTag().toString()) {
            case "bday":
                txtBday.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                break;
            case "dose_date":
                txtDoseDate.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                break;
            case "dose_expiry":
                txtDoseExpiration.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                break;
            case "lmp" :
                txtPregnantDate.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                break;

            case "deceased": txtDeceasedDate.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));

        }
    }

    public void showProfileCheckerDialog() {
        View checkerDialogView = LayoutInflater.from(getContext()).inflate(R.layout.profile_checker_dialog, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(checkerDialogView);

        final EditText txtCheckerFname, txtCheckerMname, txtCheckerLname, txtCheckerSuffix;
        final ListView lvMatchingProfiles;
        final LinearLayout txtFrame, lvFrame;
        TextView btnCheck, btnUpdate, btnNew;

        txtCheckerFname = checkerDialogView.findViewById(R.id.checker_fname);
        txtCheckerMname = checkerDialogView.findViewById(R.id.checker_mname);
        txtCheckerLname = checkerDialogView.findViewById(R.id.checker_lname);
        txtCheckerSuffix = checkerDialogView.findViewById(R.id.checker_suffix);
        lvMatchingProfiles = checkerDialogView.findViewById(R.id.checker_lv);
        lvFrame = checkerDialogView.findViewById(R.id.chercker_lv_frame);
        txtFrame = checkerDialogView.findViewById(R.id.checker_txt_frame);
        btnCheck = checkerDialogView.findViewById(R.id.checker_btnCheck);
        btnUpdate = checkerDialogView.findViewById(R.id.checker_btnUpdate);
        btnNew = checkerDialogView.findViewById(R.id.checker_btnNew);

        final AlertDialog checkerDialog = builder.create();
        checkerDialog.show();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname, mname, lname, suffix;

                fname = txtCheckerFname.getText().toString().trim();
                mname = txtCheckerMname.getText().toString().trim();
                lname = txtCheckerLname.getText().toString().trim();
                suffix = txtCheckerSuffix.getText().toString().trim();

                matchingProfiles = MainActivity.db.getMatchingProfiles(fname, mname, lname, suffix);

                if (matchingProfiles.size() > 0) {
                    ListAdapter adapter = new ListAdapter(getContext(), R.layout.population_dialog_item, matchingProfiles, null, null,null,null);
                    lvMatchingProfiles.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    lvFrame.setVisibility(View.VISIBLE);
                    txtFrame.setVisibility(View.GONE);
                } else {
                    txtFname.setText(fname);
                    txtMname.setText(mname);
                    txtLname.setText(lname);
                    txtSuffix.setText(suffix);
                    checkerDialog.dismiss();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lvMatchingProfiles.getCheckedItemPosition() >= 0) {
                    familyProfile = matchingProfiles.get(lvMatchingProfiles.getCheckedItemPosition());
                    setFieldTexts();
                    checkerDialog.dismiss();
                } else
                    Toast.makeText(getContext(), "Please select profile to update.", Toast.LENGTH_SHORT).show();
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFname.setText(txtCheckerFname.getText().toString().trim());
                txtMname.setText(txtCheckerMname.getText().toString().trim());
                txtLname.setText(txtCheckerLname.getText().toString().trim());
                txtSuffix.setText(txtCheckerSuffix.getText().toString().trim());
                checkerDialog.dismiss();
            }
        });
    }

    public void saveUpdateProfileMedication(EditText editText, String type, String remark){
        String medication_status = editText.getText().toString().trim();
        String uniqueId = familyProfile.uniqueId;

       if(uniqueId.isEmpty())
           uniqueId = fname + mname + lname + suffix + brgy + MainActivity.user.muncity;

       profileMedication = new ProfileMedication("", uniqueId, type, medication_status, remark,"1" );

        if(!medication_status.isEmpty()){
            if(editText.getTag()!=null){ //update
                if(!editText.getTag().toString().isEmpty()){
                    if(age<5 && (type.equalsIgnoreCase("Diabetic") || type.equalsIgnoreCase("Hypertension"))){
                        MainActivity.db.deleteProfileMedication(editText.getTag().toString());
                    }else{
                        profileMedication = new ProfileMedication(editText.getTag().toString(),uniqueId, type, medication_status, remark,"1" );
                        MainActivity.db.updateProfileMedication(profileMedication);
                    }
                }
            }else { //add, if not existing to db
                if((!type.equalsIgnoreCase("Diabetic") && !type.equalsIgnoreCase("Hypertension")) || // for any ages, cvd/tb/mental)
                        (age>5 && (type.equalsIgnoreCase("Diabetic") || type.equalsIgnoreCase("Hypertension")))){
                    MainActivity.db.addProfileMedication(profileMedication);
                }
            }
        }else {
            if(editText.getTag()!=null){ //delete if empty, and existing DB
                if(!editText.getTag().toString().isEmpty()){
                    MainActivity.db.deleteProfileMedication(editText.getTag().toString());
                }
            }
        }


    }


    public void findViewByIds(){
        txtFamilyId = view.findViewById(R.id.manage_familyId);
        txtPhilHealthId = view.findViewById(R.id.manage_philhealthId);
        checkBoxNHTS = view.findViewById(R.id.checkBox_nhts);
        checkBox4p = view.findViewById(R.id.checkBox_4p);
        checkBoxIP = view.findViewById(R.id.checkBox_ip);
        txtFname = view.findViewById(R.id.manage_fname);
        txtMname = view.findViewById(R.id.manage_mname);
        txtLname = view.findViewById(R.id.manage_lname);
        txtBday = view.findViewById(R.id.manage_bday);
        txtBrgy = view.findViewById(R.id.manage_barangay);
        txtEducation = view.findViewById(R.id.manage_education);
        txtSex = view.findViewById(R.id.manage_sex);
        txtSuffix = view.findViewById(R.id.manage_suffix);
        txtIncome = view.findViewById(R.id.manage_income);
        txtUnmet = view.findViewById(R.id.manage_unmet);
        txtSupply = view.findViewById(R.id.manage_supply);
        txtToilet = view.findViewById(R.id.manage_toilet);
        txtRelation = view.findViewById(R.id.manage_relation);
        txtAgeClass = view.findViewById(R.id.manage_ageclassification);

        txtManageFourPNumber = view.findViewById(R.id.manage_4p_number);
        // UPDATE
        txtDiabetic = view.findViewById(R.id.manage_diabetes);
        txtHypertension = view.findViewById(R.id.manage_hypertension);
        txtPWD = view.findViewById(R.id.manage_pwd);

        txtIsPregnant = view.findViewById(R.id.manage_isPregnant);
        txtPregnantDate = view.findViewById(R.id.manage_pregnantDate);
        til_isPregnantFrame = view.findViewById(R.id.isPregnant_Frame);
        til_pregnantDate = view.findViewById(R.id.pregnantDate_frame);

        //update r
        txtBirthPlace = view.findViewById(R.id.manage_bplace);

        txtContact = view.findViewById(R.id.manage_contact);
        txtHeight = view.findViewById(R.id.manage_height);
        txtWeight = view.findViewById(R.id.manage_weight);


        txtMental = view.findViewById(R.id.manage_mental);
        txtTBDOTS = view.findViewById(R.id.manage_tbdots);
        txtCVD = view.findViewById(R.id.manage_cvd);
        txtCovid = view.findViewById(R.id.manage_covid);

        txtCivil = view.findViewById(R.id.manage_civil);

        txtReligion = view.findViewById(R.id.manage_religion);
        txtOtherReligion = view.findViewById(R.id.manage_other_religion);
        til_otherReligion = view.findViewById(R.id.religion_frame);

        txtCancer = view.findViewById(R.id.manage_cancer);
        txtCancerType = view.findViewById(R.id.manage_cancer_type);
        til_cancerType = view.findViewById(R.id.cancer_frame);

        txtPwdDesc = view.findViewById(R.id.manage_pwd_type);
        til_pwdDesc = view.findViewById(R.id.pwd_frame);

        txtDecease = view.findViewById(R.id.manage_deceased);
        til_deceased = view.findViewById(R.id.deceased_frame);
        txtDeceasedDate = view.findViewById(R.id.manage_deceased_date);
        til_deceasedDate = view.findViewById(R.id.deceasedAge_frame);


        txtMenarche  = view.findViewById(R.id.manage_menarche);
        txtMenarcheAge  = view.findViewById(R.id.manage_menarche_age);
        til_menarche  = view.findViewById(R.id.menarche_frame);
        til_menarcheAge  = view.findViewById(R.id.menarche_age_frame);
        layout_adult = view.findViewById(R.id.adult_medication_holder);
        layout_age5 = view.findViewById(R.id.age5_holder);

        txtNewbornScreen = view.findViewById(R.id.manage_newborn);
        txtNewbornText = view.findViewById(R.id.manage_newborn_type);
        til_newborn  = view.findViewById(R.id.newborn_frame);

        txtImmunization  = view.findViewById(R.id.manage_immunization);
        txtNutrition  = view.findViewById(R.id.manage_nutrition);

        headFields = view.findViewById(R.id.head_fields_holder);
        unmetFrame = view.findViewById(R.id.unmet_frame);
        manageBtn = view.findViewById(R.id.manageBtn);

        txtSexually = view.findViewById(R.id.manage_sexually);
        txtFamilyPlanning = view.findViewById(R.id.manage_family_planning);
        til_sexually = view.findViewById(R.id.sexually_frame);
        til_family_planning = view.findViewById(R.id.family_planning_frame);

        txtFamilyPlanningMethod = view.findViewById(R.id.manage_family_planning_method_remarks);
        til_familyPlanningMethod = view.findViewById(R.id.family_planning_method_frame);
        txtFamilyPlanningMethodOther = view.findViewById(R.id.manage_family_planning_method_other_remarks);
        til_familyPlanningMethodOther = view.findViewById(R.id.family_planning_method_other_frame);

        txtFamilyPlanningStatus = view.findViewById(R.id.manage_family_planning_status_remarks);
        til_familyPlanningStatus = view.findViewById(R.id.family_planning_status_frame);
        txtFamilyPlanningStatusOther = view.findViewById(R.id.manage_family_planning_status_other_remarks);
        til_familyPlanningStatusOther = view.findViewById(R.id.family_planning_status_other_frame);

        txtBalikProbinsya = view.findViewById(R.id.manage_balikprobinsya);
        txtOther_relation = view.findViewById(R.id.manage_other_relation);
        til_otherRelation = view.findViewById(R.id.layout_other_relation);

        til_mental = view.findViewById(R.id.mental_frame);
        til_cvd = view.findViewById(R.id.cvd_frame);
        til_diabetic = view.findViewById(R.id.diabetes_frame);
        til_hypertension = view.findViewById(R.id.hypertension_frame);
        til_tbdots = view.findViewById(R.id.tbdots_frame);

        txtMentalRemarks =  view.findViewById(R.id.manage_mental_remarks);
        txtCvdRemarks =  view.findViewById(R.id.manage_cvd_remarks);
        txtDiabeticRemarks =  view.findViewById(R.id.manage_diabetes_remarks);
        txtHypertensionRemarks =  view.findViewById(R.id.manage_hypertension_remarks);
        txtTbdotsRemarks =  view.findViewById(R.id.manage_tbdots_remarks);

    }

    public void setVisibilityFemale(int visibility){

    }
}
