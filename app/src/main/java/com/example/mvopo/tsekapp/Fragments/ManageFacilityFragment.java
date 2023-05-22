package com.example.mvopo.tsekapp.Fragments;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.mvopo.tsekapp.Helper.ListAdapter;
import com.example.mvopo.tsekapp.MainActivity;
import com.example.mvopo.tsekapp.Model.Constants;
import com.example.mvopo.tsekapp.Model.FacilityModel;
import com.example.mvopo.tsekapp.Model.FacilityService;
import com.example.mvopo.tsekapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ManageFacilityFragment extends Fragment implements View.OnClickListener {
    View view;
    ScrollView optionHolder;
    Button optionBtn, updateBtn;
    EditText txtFacilityCode, txtFacilityName, txtFacilityAbbr, txtAddress,
            txtContact, txtEmail, txtChief, txtLatitude, txtLongitude;

    AutoCompleteTextView txtProvince, txtMuncity, txtBarangay;
    EditText  txtServiceCapability, txtOwnership, txtFacilityStatus, txtReferralStatus, txtTransport, txtPHIC,txtLicenseStatus;
    EditText txtDayFrom, txtDayTo, txtTimeFrom, txtTimeTo, txtSchedNote;

    EditText   txtOtherServices;
    TableLayout tblOtherServices, tblConsult, tblTbdots, tblAbtc, tblDental, tblLaboratory, tblFamPlan;

    EditText txtBirthing, txtDialysis;
    TextInputLayout tilBirthing, tilDialysis;

    EditText txtConsult, txtConsultPrivate, txtConsultPublic;
    TextInputLayout tilConsultPrivate, tilConsultPublic;

    EditText txtTbdots, txtTbdots1, txtTbdots2;
    TextInputLayout tilTbdots1, tilTbdots2;

    EditText txtAbtc, txtAbtc1, txtAbtc2, txtAbtc3;
    TextInputLayout tilAbtc1, tilAbtc2, tilAbtc3;

    EditText txtDentalServices, txtDentalExtract, txtDentalTempFill, txtDentalPermFill, txtDentalClean, txtDentalOrtho;
    TextInputLayout tilDentalExtract, tilDentalTempFill, tilDentalPermFill, tilDentalClean, tilDentalOrtho;

    EditText txtLaboratoryServices, txtLabXray, txtLabCBC, txtLabCreatine, txtLabECG, txtLabFBS, txtLabFecal, txtLabFOB, txtLabHbAIC, txtLabLipid, txtLabOGT, txtLabPap, txtLabSputum, txtLabUrine;
    TextInputLayout                 tilLabXray, tilLabCBC, tilLabCreatine, tilLabECG, tilLabFBS, tilLabFecal, tilLabFOB, tilLabHbAIC, tilLabLipid, tilLabOGT, tilLabPap, tilLabSputum, tilLabUrine;


    EditText txtFamPlan, txtFamNSV, txtFamBTL, txtFamCondom, txtFamLAM, txtFamProgesterone, txtFamImplant, txtFamPOP, txtFamCOC, txtFamPIC, txtFamCIC, txtFamInternal, txtFamPostpartum;
    TextInputLayout    tilFamNSV, tilFamBTL, tilFamCondom, tilFamLAM, tilFamProgesterone, tilFamImplant, tilFamPOP, tilFamCOC, tilFamPIC, tilFamCIC, tilFamInternal, tilFamPostpartum;


    Boolean toUpdate;
    FacilityModel facilityModel;
    String facility_code, facility_name, facility_abbr, address,
            contact, email, chief_hospital, service_capability, license_status, ownership, facility_status, phic_status,
            referral_status, transport, latitude, longitude, sched_day_from, sched_day_to, sched_time_from, sched_time_to, sched_notes;

    String prov="", muncity="", brgy="" , province_id ="", muncity_id="", brgy_id="";

    TextInputLayout[] tils_otherServices, tils_consult, tils_tbdots, tils_abtc, tils_dental, tils_laboratory, tils_famPlan;

    ArrayList<FacilityService> facilityServices = new ArrayList<>();
    ArrayList<FacilityModel> matchingFacilities = new ArrayList<>();
    FacilityService facilityService;
    String[] otherServicesList={}, consultList={}, tbdotsList={}, abtcList={}, dentalList={}, laboratoryList={}, famPlanList={};

    ArrayList<String> listOfProvinces = new ArrayList<>();
    ArrayList<String>  listOfMuncities = new ArrayList<>();
    ArrayList<String> listOfBarangays = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_facility, container, false);
        findViewByIds();

        toUpdate=getArguments().getBoolean("toUpdate");
        facilityModel=getArguments().getParcelable("facilityModel");
        listOfProvinces = MainActivity.db.getProvinceNames();

        tils_otherServices = new TextInputLayout[] {tilBirthing, tilDialysis};
        tils_consult = new TextInputLayout[] {tilConsultPrivate, tilConsultPublic};
        tils_tbdots = new TextInputLayout[] {tilTbdots1, tilTbdots2};
        tils_abtc = new TextInputLayout[] {tilAbtc1, tilAbtc2, tilAbtc3};
        tils_dental = new TextInputLayout[] {tilDentalExtract, tilDentalTempFill, tilDentalPermFill, tilDentalOrtho, tilDentalClean};
        tils_laboratory = new TextInputLayout[] {tilLabXray, tilLabCBC, tilLabCreatine, tilLabECG, tilLabFBS, tilLabFecal, tilLabFOB, tilLabHbAIC, tilLabLipid, tilLabOGT, tilLabPap, tilLabSputum, tilLabUrine};
        tils_famPlan = new TextInputLayout[] {tilFamNSV, tilFamBTL, tilFamCondom, tilFamLAM, tilFamProgesterone, tilFamImplant, tilFamPOP, tilFamCOC, tilFamPIC, tilFamCIC, tilFamInternal, tilFamPostpartum};


        updateBtn.setOnClickListener(this);
        txtServiceCapability.setOnClickListener(this);
        txtOwnership.setOnClickListener(this);
        txtFacilityStatus.setOnClickListener(this);
        txtReferralStatus.setOnClickListener(this);
        txtTransport.setOnClickListener(this);
        txtPHIC.setOnClickListener(this);
        txtLicenseStatus.setOnClickListener(this);

        txtDayFrom.setOnClickListener(this);
        txtDayTo.setOnClickListener(this);
        txtTimeFrom.setOnClickListener(this);
        txtTimeTo.setOnClickListener(this);

        txtConsult.setOnClickListener(this);
        txtTbdots.setOnClickListener(this);
        txtAbtc.setOnClickListener(this);
        txtOtherServices.setOnClickListener(this);
        txtDentalServices.setOnClickListener(this);
        txtLaboratoryServices.setOnClickListener(this);
        txtFamPlan.setOnClickListener(this);

        setMoneyTextWatcher(getContext());

        txtOtherServices.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String service = txtOtherServices.getText().toString().trim().toUpperCase();
                otherServicesList = service.trim().replace(",   ", ",").split(",");

                disableServicesCosts(tils_otherServices);
                if(!service.trim().isEmpty()){

                    if(otherServicesList.length==1 && otherServicesList[0].trim().equalsIgnoreCase("PHARMACY"))
                        tblOtherServices.setVisibility(View.GONE);
                    else tblOtherServices.setVisibility(View.VISIBLE);

                    for (String value : otherServicesList) {
                        switch (value.trim()) {
                            case "BIRTHING": enableCostOfService(tilBirthing);
                                break;
                            case "DIALYSIS CENTER": enableCostOfService(tilDialysis);
                                break;
                        }
                    }
                }else tblOtherServices.setVisibility(View.GONE);
            }
        });

        txtConsult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String service = txtConsult.getText().toString().toUpperCase();
                consultList = service.replace(",   ", ",").split(",");

                disableServicesCosts(tils_consult);
                if (!service.trim().isEmpty()) {
                    tblConsult.setVisibility(View.VISIBLE);

                    for (String value : consultList) {
                        switch (value.trim()) {
                            case "PRIVATE CLINIC": enableCostOfService(tilConsultPrivate);
                                break;
                            case "PUBLIC CLINIC": enableCostOfService(tilConsultPublic);
                                break;
                        }
                    }
                }else tblConsult.setVisibility(View.GONE);
            }
        });

        txtTbdots.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String service = txtTbdots.getText().toString().trim().toUpperCase();
                tbdotsList = service.trim().replace(",   ", ",").split(",");

                disableServicesCosts(tils_tbdots);
                if(!service.trim().isEmpty()){
                    tblTbdots.setVisibility(View.VISIBLE);
                    for (String value : tbdotsList) {
                        switch (value.trim()) {
                            case "CATEGORY 1": enableCostOfService(tilTbdots1);
                                break;
                            case "CATEGORY 2": enableCostOfService(tilTbdots2);
                                break;
                        }
                    }
                } else tblTbdots.setVisibility(View.GONE);
            }
        });

        txtAbtc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String service = txtAbtc.getText().toString().trim().toUpperCase();
                abtcList = service.trim().replace(",   ", ",").split(",");

                disableServicesCosts(tils_abtc);
                if(!service.trim().isEmpty()){
                    tblAbtc.setVisibility(View.VISIBLE);
                    for (String value : abtcList) {
                        switch (value.trim()) {
                            case  "CATEGORY 1": enableCostOfService(tilAbtc1);
                                break;
                            case  "CATEGORY 2": enableCostOfService(tilAbtc2);
                                break;
                            case  "CATEGORY 3": enableCostOfService(tilAbtc3);
                                break;
                        }
                    }
                } else  tblAbtc.setVisibility(View.GONE);
            }
        });

        txtDentalServices.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String service = txtDentalServices.getText().toString().trim().toUpperCase();
                dentalList = service.trim().replace(",   ", ",").split(",");

                disableServicesCosts(tils_dental);
                if(!service.trim().isEmpty()){
                    tblDental.setVisibility(View.VISIBLE);
                    for (String value : dentalList) {
                        switch (value.trim()) {
                            case "EXTRACTION": enableCostOfService(tilDentalExtract);
                                break;
                            case "TEMPORARY FILLING": enableCostOfService(tilDentalTempFill);
                                break;
                            case "PERMANENT FILLING": enableCostOfService(tilDentalPermFill);
                                break;
                            case "ORTHODONTICS": enableCostOfService(tilDentalOrtho);
                                break;
                            case "ORAL PROPHYLAXIS (CLEANING)": enableCostOfService(tilDentalClean);
                                break;
                        }
                    }
                } else tblDental.setVisibility(View.GONE);
            }
        });

        txtLaboratoryServices.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             }

             @Override
             public void afterTextChanged(Editable editable) {
                 String service = txtLaboratoryServices.getText().toString().trim().toUpperCase();
                 laboratoryList = service.trim().replace(",   ", ",").split(",");

                 disableServicesCosts(tils_laboratory);
                 if(!service.trim().isEmpty()){
                     tblLaboratory.setVisibility(View.VISIBLE);
                     for (String value : laboratoryList) {
                         switch (value.trim()) {
                             case "CHEST X-RAY": enableCostOfService(tilLabXray);
                                 break;
                             case "COMPLETE BLOOD COUNT W/ PLATELET COUNT": enableCostOfService(tilLabCBC);
                                 break;
                             case "CREATININE": enableCostOfService(tilLabCreatine);
                                 break;
                             case "ECG": enableCostOfService(tilLabECG);
                                 break;
                             case "FBS": enableCostOfService(tilLabFBS);
                                 break;
                             case "FECALYSIS": enableCostOfService(tilLabFecal);
                                 break;
                             case "FECAL OCCULT BLOOD": enableCostOfService(tilLabFOB);
                                 break;
                             case "HBAIC": enableCostOfService(tilLabHbAIC);
                                 break;
                             case "LIPID PROFILE": enableCostOfService(tilLabLipid);
                                 break;
                             case "ORAL GLUCOSE TOLERANCE TEST": enableCostOfService(tilLabOGT);
                                 break;
                             case "PAP SMEAR": enableCostOfService(tilLabPap);
                                 break;
                             case "SPUTUM MICROSCOPY": enableCostOfService(tilLabSputum);
                                 break;
                             case "URINALYSIS": enableCostOfService(tilLabUrine);
                                 break;
                         }
                     }
                 } else  tblLaboratory.setVisibility(View.GONE);
             }
         });

        txtFamPlan.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             }

             @Override
             public void afterTextChanged(Editable editable) {
                 String service = txtFamPlan.getText().toString().trim().toUpperCase();
                 famPlanList = service.trim().replace(",   ", ",").split(",");

                 disableServicesCosts(tils_famPlan);
                 if(!service.trim().isEmpty()){
                     tblFamPlan.setVisibility(View.VISIBLE);
                     for (String value : famPlanList) {
                         switch (value.trim()) {
                             case "NSV": enableCostOfService(tilFamNSV);
                                 break;
                             case "BTL":enableCostOfService(tilFamBTL);
                                 break;
                             case "CONDOM":enableCostOfService(tilFamCondom);
                                 break;
                             case "LAM":enableCostOfService(tilFamLAM);
                                 break;
                             case "PROGESTERONE":enableCostOfService(tilFamProgesterone);
                                 break;
                             case "IMPLANT":enableCostOfService(tilFamImplant);
                                 break;
                             case "(ORAL PILLS) PROGESTERONE ONLY": enableCostOfService(tilFamPOP);
                                 break;
                             case "(ORAL PILLS) COMBINED ORAL CONTRACEPTIVES":enableCostOfService(tilFamCOC);
                                 break;
                             case "(DMPA) PURE INJECT CONTRACEPTIVES":enableCostOfService(tilFamPIC);
                                 break;
                             case "(DMPA) COMBINED INJECT CONTRACEPTIVES":enableCostOfService(tilFamCIC);
                                 break;
                             case "(IUD) INTERNAL":enableCostOfService(tilFamInternal);
                                 break;
                             case "(IUD) POSTPARTUM":enableCostOfService(tilFamPostpartum);
                                 break;
                         }
                     }
                 } else tblFamPlan.setVisibility(View.GONE);
             }
         });

        if(toUpdate){
            facilityServices = MainActivity.db.getFacilityServices(facilityModel.facility_code);
            setFieldTexts();
        }else{
            showFacilityCheckerDialog();
        }

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listOfProvinces);
        txtProvince.setAdapter(provinceAdapter);

        txtProvince.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String temp = txtProvince.getText().toString().trim();
                if(!temp.isEmpty()) {
                    temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();

                    if(listOfProvinces.contains(temp)){
                        if(!prov.equalsIgnoreCase(temp)){
                            txtMuncity.setText("");   muncity = ""; muncity_id = "";
                            txtBarangay.setText("");  brgy = ""; brgy_id = "";

                            prov = txtProvince.getText().toString().trim();
                            province_id = MainActivity.db.getProvIdByName(prov);

                            listOfMuncities = MainActivity.db.getMuncityNamesByProv(province_id);
                            ArrayAdapter<String> muncityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listOfMuncities);
                            txtMuncity.setAdapter(muncityAdapter);
                        }
                    }
                }
                else {
                    prov = ""; province_id = "";
                    txtMuncity.setText(""); muncity = ""; muncity_id = "";
                    txtBarangay.setText(""); brgy = ""; brgy_id = "";
                }
            }
        });

        txtMuncity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String temp = txtMuncity.getText().toString().trim();
                if(temp.isEmpty()){
                    muncity = ""; muncity_id = "";
                    txtBarangay.setText(""); brgy = ""; brgy_id = "";
                } else {
                    if(listOfMuncities.contains(temp)){
                        if(!muncity.equalsIgnoreCase(temp)){
                            txtBarangay.setText(""); brgy = ""; brgy_id = "";

                            muncity = txtMuncity.getText().toString().trim();
                            muncity_id = MainActivity.db.getMuncityIdByNameProv(muncity,province_id);

                            listOfBarangays = MainActivity.db.getBrgyNamesByProvMuncity(province_id, muncity_id);
                            ArrayAdapter<String> brgyAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listOfBarangays);
                            txtBarangay.setAdapter(brgyAdapter);
                        }
                    }
                }


            }
        });
        return view;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.facility_serviceCapability:
                showOptionDialog(R.array.service_capability, null,txtServiceCapability);
                break;
            case R.id.facility_ownership:
                showOptionDialog(R.array.ownership, null,txtOwnership);
                break;
            case R.id.facility_hospitalStatus:
                showOptionDialog(R.array.facility_status, null, txtFacilityStatus);
                break;
            case R.id.facility_referralStatus:
                showOptionDialog(R.array.referral_status, null, txtReferralStatus);
                break;
            case R.id.facility_transport:
                showOptionDialog(R.array.transport_type, null,txtTransport);
                break;
            case R.id.facility_phicStatus:
                showOptionDialog(R.array.accreditation_status, null, txtPHIC);
                break;

            case R.id.facility_licensingStatus:
                showOptionDialog(R.array.license_status, null, txtLicenseStatus);
                break;

            /**SCHEDULE*/
            case R.id.facility_dayFrom:
                showOptionDialog(R.array.weekdays, null,txtDayFrom);
                break;
            case R.id.facility_dayTo:
                showOptionDialog(R.array.weekdays, null,txtDayTo);
                break;
            case R.id.facility_timeFrom:
                showTimePicker(txtTimeFrom);
                break;
            case R.id.facility_timeTo:
                showTimePicker(txtTimeTo);
                break;
            /**Services Available*/
            case R.id.facility_otherServices:
                showCheckboxDialog(R.array.other_services, txtOtherServices);
                break;
            case R.id.facility_consultServices:
                showCheckboxDialog(R.array.consultation_services, txtConsult);
                break;
            case R.id.facility_tbdotsServices:
                showCheckboxDialog(R.array.tbdots_services, txtTbdots);
                break;
            case R.id.facility_abtcServices:
                showCheckboxDialog(R.array.abtc_services, txtAbtc);
                break;
            case R.id.facility_dentalServices:
                showCheckboxDialog(R.array.dental_services, txtDentalServices);
                break;
            case R.id.facility_labServices:
                showCheckboxDialog(R.array.laboratory_services, txtLaboratoryServices);
                break;
            case R.id.facility_famPlanServices:
                showCheckboxDialog(R.array.familyPlanning_services, txtFamPlan);
                break;

            case R.id.facilityBtn: /**SAVE OR UPDATE*/
            boolean facilityFlag = false;
                facility_code = txtFacilityCode.getText().toString().trim();
                facility_name = txtFacilityName.getText().toString().trim();
                facility_abbr = txtFacilityAbbr.getText().toString().trim();
                /*prov = txtProvince.getText().toString().trim();
                muncity = txtMuncity.getText().toString().trim();*/
                brgy = txtBarangay.getText().toString().trim();
                if(!brgy.isEmpty() && !province_id.isEmpty() && !muncity_id.isEmpty()){
                    brgy_id = MainActivity.db.getBrgyIdByNameProvMuncity(brgy, province_id, muncity_id);
                }

                address = txtAddress.getText().toString().trim();
                contact = txtContact.getText().toString().trim();
                email = txtEmail.getText().toString().trim();
                chief_hospital = txtChief.getText().toString().trim();
                service_capability = txtServiceCapability.getText().toString().trim();
                license_status = txtLicenseStatus.getText().toString().trim();
                ownership = txtOwnership.getText().toString().trim();
                facility_status = txtFacilityStatus.getText().toString().trim();
                referral_status = txtReferralStatus.getText().toString().trim();
                phic_status = txtPHIC.getText().toString().trim();
                transport = txtTransport.getText().toString().trim();
                latitude = txtLatitude.getText().toString().trim();
                longitude = txtLongitude.getText().toString().trim();

                sched_day_from = txtDayFrom.getText().toString().trim();
                sched_day_to = txtDayTo.getText().toString().trim();
                sched_time_from = txtTimeFrom.getText().toString().trim();
                sched_time_to = txtTimeTo.getText().toString().trim();
                sched_notes = txtSchedNote.getText().toString().trim();

                if(facility_name.isEmpty()){
                    txtFacilityName.setError("Required");
                    txtFacilityName.requestFocus();
                }
                if(facility_code.isEmpty()){
                    txtFacilityCode.setError("Required");
                    txtFacilityCode.requestFocus();
                }
                if(province_id.isEmpty()){
                    txtProvince.setError("Required");
                    txtProvince.requestFocus();
                }
                if(muncity_id.isEmpty()){
                    txtMuncity.setError("Required");
                    txtMuncity.requestFocus();
                }
                if(brgy_id.isEmpty()){
                    txtBarangay.setError("Required");
                    txtBarangay.requestFocus();
                }
                if(address.isEmpty()){
                    txtAddress.setError("Required");
                    txtAddress.requestFocus();
                }
                if(contact.isEmpty()){
                    txtContact.setError("Required");
                    txtContact.requestFocus();
                }
                if(email.isEmpty()){
                    txtEmail.setError("Required");
                    txtEmail.requestFocus();
                }
                if(chief_hospital.isEmpty()){
                    txtChief.setError("Required");
                    txtChief.requestFocus();
                }

                if(facility_name.isEmpty() || facility_code.isEmpty() || province_id.isEmpty() || muncity_id.isEmpty() || brgy_id.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty() || chief_hospital.isEmpty()){
                    facilityFlag = false;
                }else {
                    if(toUpdate){
                        facilityModel =  new FacilityModel(facilityModel.id, facility_code, facility_name, facility_abbr, province_id, muncity_id, brgy_id, address, contact,email,
                                chief_hospital, service_capability, license_status, ownership, facility_status,
                                referral_status, phic_status, transport, latitude, longitude,
                                sched_day_from, sched_day_to, sched_time_from, sched_time_to, sched_notes, "1" );
                        Toast.makeText(getContext(),"toUpdate", Toast.LENGTH_SHORT).show();
                        MainActivity.db.updateFacility(facilityModel);
                    }else {
                        facilityModel =  new FacilityModel("", facility_code, facility_name, facility_abbr, province_id, muncity_id, brgy_id, address, contact,email,
                                chief_hospital, service_capability, license_status, ownership, facility_status,
                                referral_status, phic_status, transport, latitude, longitude,
                                sched_day_from, sched_day_to, sched_time_from, sched_time_to, sched_notes, "1" );
                        MainActivity.db.addFacility(facilityModel);
                    }
                    facilityFlag = true;


                    for(FacilityService service: facilityServices){ //delete daan ang mga wala nay labot
                        if(!Arrays.asList(otherServicesList).contains(service.service.trim().toUpperCase()) && !Arrays.asList(consultList).contains(service.service.trim().toUpperCase()) &&
                                !Arrays.asList(tbdotsList).contains(service.service.trim().toUpperCase())   && !Arrays.asList(abtcList).contains(service.service.trim().toUpperCase()) &&
                                !Arrays.asList(dentalList).contains(service.service.trim().toUpperCase())   && !Arrays.asList(laboratoryList).contains(service.service.trim().toUpperCase()) &&
                                !Arrays.asList(famPlanList).contains(service.service.trim().toUpperCase())){
                            MainActivity.db.deleteFacilityService(service.id);
                        }
                    }

                    for (String value : otherServicesList) {
                        switch (value.trim()) {
                            case "BIRTHING": saveUpdateFacilityService(txtBirthing, "Other Services", "Birthing");
                                break;
                            case "DIALYSIS CENTER": saveUpdateFacilityService(txtDialysis, "Other Services", "Dialysis Center");
                                break;
                            case "PHARMACY": saveUpdateFacilityService(null, "Other Services", "Pharmacy");
                                break;
                        }
                    }

                    for(String value : consultList){
                        switch (value.trim()) {
                            case "PRIVATE CLINIC": saveUpdateFacilityService(txtConsultPrivate, "Consultation", "Private Clinic");
                                break;
                            case "PUBLIC CLINIC": saveUpdateFacilityService(txtConsultPublic, "Consultation", "Public Clinic");
                                break;
                        }
                    }

                    for(String value : tbdotsList){
                        switch (value.trim()) {
                            case "CATEGORY 1": saveUpdateFacilityService(txtTbdots1, "TB DOTS", "Category 1");
                                break;
                            case "CATEGORY 2": saveUpdateFacilityService(txtTbdots2, "TB DOTS", "Category 2");
                                break;
                        }
                    }

                    for(String value : abtcList){
                        switch (value.trim()) {
                            case "CATEGORY 1": saveUpdateFacilityService(txtAbtc1, "ABTC", "Category 1");
                                break;
                            case "CATEGORY 2": saveUpdateFacilityService(txtAbtc2, "ABTC", "Category 2");
                                break;
                            case "CATEGORY 3": saveUpdateFacilityService(txtAbtc3, "ABTC", "Category 3");
                                break;
                        }
                    }

                    for(String value : dentalList){
                        switch (value.trim()) {
                            case "EXTRACTION": saveUpdateFacilityService(txtDentalExtract, "Dental", "Extraction");
                                break;
                            case "TEMPORARY FILLING": saveUpdateFacilityService(txtDentalTempFill, "Dental", "Temporary Filling");
                                break;
                            case "PERMANENT FILLING": saveUpdateFacilityService(txtDentalPermFill, "Dental", "Permanent Filling");
                                break;
                            case "ORTHODONTICS": saveUpdateFacilityService(txtDentalOrtho, "Dental", "Orthodontics");
                                break;
                            case "ORAL PROPHYLAXIS (CLEANING)": saveUpdateFacilityService(txtDentalClean, "Dental", "Oral Prophylaxis (Cleaning)");
                                break;
                        }
                    }

                    for(String value : laboratoryList){
                        switch (value.trim()) {
                            case "CHEST X-RAY": saveUpdateFacilityService(txtLabXray, "Laboratory", "Chest X-Ray");
                                break;
                            case "COMPLETE BLOOD COUNT W/ PLATELET COUNT": saveUpdateFacilityService(txtLabCBC, "Laboratory", "Complete Blood Count w/ Platelet Count");
                                break;
                            case "CREATININE": saveUpdateFacilityService(txtLabCreatine, "Laboratory", "Creatinine");
                                break;
                            case "ECG": saveUpdateFacilityService(txtLabECG, "Laboratory", "ECG");
                                break;
                            case "FBS": saveUpdateFacilityService(txtLabFBS, "Laboratory", "FBS");
                                break;
                            case "FECALYSIS": saveUpdateFacilityService(txtLabFecal, "Laboratory", "Fecalysis");
                                break;
                            case "FECAL OCCULT BLOOD": saveUpdateFacilityService(txtLabFOB, "Laboratory", "Fecal Occult Blood");
                                break;
                            case "HBAIC": saveUpdateFacilityService(txtLabHbAIC, "Laboratory", "HbAIC");
                                break;
                            case "LIPID PROFILE": saveUpdateFacilityService(txtLabLipid, "Laboratory", "Lipid Profile");
                                break;
                            case "ORAL GLUCOSE TOLERANCE TEST": saveUpdateFacilityService(txtLabOGT, "Laboratory", "Oral Glucose Tolerance Test");
                                break;
                            case "PAP SMEAR": saveUpdateFacilityService(txtLabPap, "Laboratory", "Pap Smear");
                                break;
                            case "SPUTUM MICROSCOPY": saveUpdateFacilityService(txtLabSputum, "Laboratory", "Sputum Microscopy");
                                break;
                            case "URINALYSIS": saveUpdateFacilityService(txtLabUrine, "Laboratory", "Urinalysis");
                                break;
                        }
                    }

                    for(String value : famPlanList){
                        switch (value.trim()) {
                            case "NSV": saveUpdateFacilityService(txtFamNSV, "Family Planning", "NSV");
                                break;
                            case "BTL": saveUpdateFacilityService(txtFamBTL, "Family Planning", "BTL");
                                break;
                            case "CONDOM": saveUpdateFacilityService(txtFamCondom, "Family Planning", "Condom");
                                break;
                            case "LAM": saveUpdateFacilityService(txtFamLAM, "Family Planning", "LAM");
                                break;
                            case "PROGESTERONE": saveUpdateFacilityService(txtFamProgesterone, "Family Planning", "Progesterone");
                                break;
                            case "IMPLANT": saveUpdateFacilityService(txtFamImplant, "Family Planning", "Implant");
                                break;
                            case "(ORAL PILLS) PROGESTERONE ONLY": saveUpdateFacilityService(txtFamPOP, "Family Planning", "(Oral Pills) Progesterone Only");
                                break;
                            case "(ORAL PILLS) COMBINED ORAL CONTRACEPTIVES": saveUpdateFacilityService(txtFamCOC, "Family Planning", "(Oral Pills) Combined Oral Contraceptives");
                                break;
                            case "(DMPA) PURE INJECT CONTRACEPTIVES": saveUpdateFacilityService(txtFamPIC, "Family Planning", "(DMPA) Pure Inject Contraceptives");
                                break;
                            case "(DMPA) COMBINED INJECT CONTRACEPTIVES": saveUpdateFacilityService(txtFamCIC, "Family Planning", "(DMPA) Combined Inject Contraceptives");
                                break;
                            case "(IUD) INTERNAL": saveUpdateFacilityService(txtFamInternal, "Family Planning", "(IUD) Internal");
                                break;
                            case "(IUD) POSTPARTUM": saveUpdateFacilityService(txtFamPostpartum, "Family Planning", "(IUD) Postpartum");
                                break;
                        }
                    }

                }


                if(facilityFlag){
                    MainActivity.fm.popBackStack();
                }
                break;
        }
    }

    public void  showOptionDialog(final int id, final List<String> list, final EditText txtView) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.options_dialog, null);
        optionHolder = view.findViewById(R.id.option_holder);
        optionBtn = view.findViewById(R.id.optionBtn);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        String[] labels;

        if (id != 0) labels = getResources().getStringArray(id);
        else labels = list.toArray(new String[0]) ;

        final RadioGroup radioGroup = new RadioGroup(getContext());

        LinearLayout.LayoutParams rbParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rbParam.bottomMargin = 5;
        rbParam.topMargin = 5;

        for (int i = 0; i < labels.length; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setLayoutParams(rbParam);
            radioButton.setText(labels[i]);

            View lineView = new View(getContext());
            lineView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            lineView.setLayoutParams(params);


            radioGroup.addView(radioButton);
            radioGroup.addView(lineView);
        }

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
                    txtView.setText(radioButton.getText());
                    txtView.setTag(radioButton.getId());
                } else {
                    txtView.setText("");
                    txtView.setTag("");
                }
                optionDialog.dismiss();
            }
        });
    }

    public void showTimePicker(EditText editText){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                editText.setText(  Constants.twoDigitFormat(selectedHour) + ":" + Constants.twoDigitFormat(selectedMinute) + ":00");
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.show();
    }

    String selectedCheckbox = ""; String[] labels = {};
    public void showCheckboxDialog(final int id, final EditText txtView) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.options_dialog, null);
        optionHolder = view.findViewById(R.id.option_holder);
        optionBtn = view.findViewById(R.id.optionBtn);
        selectedCheckbox = "";

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        final RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        if (id != 0) labels = getResources().getStringArray(id);

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
                        selectedCheckbox +=(checkBoxes[i].getText().toString().trim() + ",   ");
                    }
                }
                txtView.setText(selectedCheckbox);
                optionDialog.dismiss();
            }
        });

    }

    public void findViewByIds(){
        updateBtn = view.findViewById(R.id.facilityBtn);
        txtFacilityCode = view.findViewById(R.id.facility_code);
        txtFacilityName = view.findViewById(R.id.facility_name);
        txtFacilityAbbr = view.findViewById(R.id.facility_abbr);
        txtProvince = view.findViewById(R.id.facility_province);
        txtMuncity = view.findViewById(R.id.facility_municipality);
        txtBarangay = view.findViewById(R.id.facility_barangay);
        txtAddress = view.findViewById(R.id.facility_address);
        txtContact = view.findViewById(R.id.facility_contact);
        txtEmail = view.findViewById(R.id.facility_email);
        txtChief = view.findViewById(R.id.facility_chief);
        txtLatitude = view.findViewById(R.id.facility_latitude);
        txtLongitude = view.findViewById(R.id.facility_longitude);

        txtServiceCapability = view.findViewById(R.id.facility_serviceCapability);
        txtOwnership = view.findViewById(R.id.facility_ownership);
        txtFacilityStatus = view.findViewById(R.id.facility_hospitalStatus);
        txtReferralStatus = view.findViewById(R.id.facility_referralStatus);
        txtTransport = view.findViewById(R.id.facility_transport);
        txtPHIC = view.findViewById(R.id.facility_phicStatus);
        txtLicenseStatus = view.findViewById(R.id.facility_licensingStatus);
        //Schedule
        txtDayFrom = view.findViewById(R.id.facility_dayFrom);
        txtDayTo = view.findViewById(R.id.facility_dayTo);
        txtTimeFrom = view.findViewById(R.id.facility_timeFrom);
        txtTimeTo = view.findViewById(R.id.facility_timeTo);
        txtSchedNote = view.findViewById(R.id.facility_schedNote);

        /**Services And costs*/
        tblOtherServices = view.findViewById(R.id.table_otherServices);
        txtOtherServices = view.findViewById(R.id.facility_otherServices);
        txtBirthing = view.findViewById(R.id.facility_birthing);
        txtDialysis = view.findViewById(R.id.facility_dialysis);

        tilBirthing = view.findViewById(R.id.facility_til_birthing);
        tilDialysis = view.findViewById(R.id.facility_til_dialysis);
            /**Consult*/
        tblConsult = view.findViewById(R.id.table_consult);
        txtConsult = view.findViewById(R.id.facility_consultServices);
        txtConsultPublic = view.findViewById(R.id.facility_consultPublic);
        txtConsultPrivate = view.findViewById(R.id.facility_consultPrivate);

        tilConsultPublic = view.findViewById(R.id.facility_til_consultPublic);
        tilConsultPrivate = view.findViewById(R.id.facility_til_consultPrivate);
            /**tbdots*/
        tblTbdots = view.findViewById(R.id.table_tbdots);
        txtTbdots = view.findViewById(R.id.facility_tbdotsServices);
        txtTbdots1 = view.findViewById(R.id.facility_tbdots1);
        txtTbdots2 = view.findViewById(R.id.facility_tbdots2);

        tilTbdots1 = view.findViewById(R.id.facility_til_tbdots1);
        tilTbdots2 = view.findViewById(R.id.facility_til_tbdots2);
            /**abtc*/
        tblAbtc = view.findViewById(R.id.table_abtc);
        txtAbtc = view.findViewById(R.id.facility_abtcServices);
        txtAbtc1 = view.findViewById(R.id.facility_abtc1);
        txtAbtc2 = view.findViewById(R.id.facility_abtc2);
        txtAbtc3 = view.findViewById(R.id.facility_abtc3);

        tilAbtc1 = view.findViewById(R.id.facility_til_abtc1);
        tilAbtc2 = view.findViewById(R.id.facility_til_abtc2);
        tilAbtc3 = view.findViewById(R.id.facility_til_abtc3);
            /**Laboratory*/
        txtLaboratoryServices = view.findViewById(R.id.facility_labServices);
        tblLaboratory = view.findViewById(R.id.table_laboratoryServices);
        txtLabXray = view.findViewById(R.id.facility_labXray);
        txtLabCBC = view.findViewById(R.id.facility_labCBC);
        txtLabCreatine = view.findViewById(R.id.facility_labCreatine);
        txtLabECG = view.findViewById(R.id.facility_labECG);
        txtLabFBS = view.findViewById(R.id.facility_labFBS);
        txtLabFecal = view.findViewById(R.id.facility_labFecalysis);
        txtLabFOB = view.findViewById(R.id.facility_labFOB);
        txtLabHbAIC = view.findViewById(R.id.facility_labHbAIC);
        txtLabLipid = view.findViewById(R.id.facility_labLipid);
        txtLabOGT = view.findViewById(R.id.facility_labOGT);
        txtLabPap = view.findViewById(R.id.facility_labPap);
        txtLabSputum = view.findViewById(R.id.facility_labsputum);
        txtLabUrine = view.findViewById(R.id.facility_labUrine);

        tilLabXray = view.findViewById(R.id.facility_til_labXray);
        tilLabCBC = view.findViewById(R.id.facility_til_labCBC);
        tilLabCreatine = view.findViewById(R.id.facility_til_labCreatine);
        tilLabECG = view.findViewById(R.id.facility_til_labECG);
        tilLabFBS = view.findViewById(R.id.facility_til_labFBS);
        tilLabFecal = view.findViewById(R.id.facility_til_labFecalysis);
        tilLabFOB = view.findViewById(R.id.facility_til_labFOB);
        tilLabHbAIC = view.findViewById(R.id.facility_til_labHbAIC);
        tilLabLipid = view.findViewById(R.id.facility_til_labLipid);
        tilLabOGT = view.findViewById(R.id.facility_til_labOGT);
        tilLabPap = view.findViewById(R.id.facility_til_labPap);
        tilLabSputum = view.findViewById(R.id.facility_til_labSputum);
        tilLabUrine = view.findViewById(R.id.facility_til_labUrine);

            /**Dental*/
        txtDentalServices = view.findViewById(R.id.facility_dentalServices);
        tblDental = view.findViewById(R.id.table_dentalServices);
        txtDentalExtract = view.findViewById(R.id.facility_dentalExtract);
        txtDentalTempFill = view.findViewById(R.id.facility_dentalTempFill);
        txtDentalPermFill = view.findViewById(R.id.facility_dentalPermFill);
        txtDentalClean = view.findViewById(R.id.facility_dentalClean);
        txtDentalOrtho = view.findViewById(R.id.facility_dentalOrtho);

        tilDentalExtract = view.findViewById(R.id.facility_til_dentalExtract);
        tilDentalTempFill = view.findViewById(R.id.facility_til_dentalTempFill);
        tilDentalPermFill = view.findViewById(R.id.facility_til_dentalPermFill);
        tilDentalClean = view.findViewById(R.id.facility_til_dentalClean);
        tilDentalOrtho = view.findViewById(R.id.facility_til_dentalOrtho);

            /**family planning*/
        tblFamPlan = view.findViewById(R.id.table_famplan);
        txtFamPlan = view.findViewById(R.id.facility_famPlanServices);

        txtFamNSV = view.findViewById(R.id.facility_famNsv);
        txtFamBTL = view.findViewById(R.id.facility_famBtl);
        txtFamCondom = view.findViewById(R.id.facility_famCondom);
        txtFamLAM = view.findViewById(R.id.facility_famLam);
        txtFamProgesterone = view.findViewById(R.id.facility_famProgesterone);
        txtFamImplant = view.findViewById(R.id.facility_famImplant);
        txtFamPOP = view.findViewById(R.id.facility_famPOP);
        txtFamCOC = view.findViewById(R.id.facility_famCOC);
        txtFamPIC = view.findViewById(R.id.facility_famPIC);
        txtFamCIC = view.findViewById(R.id.facility_famCIC);
        txtFamInternal = view.findViewById(R.id.facility_famInternal);
        txtFamPostpartum = view.findViewById(R.id.facility_famPostpartum);

        tilFamNSV = view.findViewById(R.id.facility_til_famNsv);
        tilFamBTL = view.findViewById(R.id.facility_til_famBtl);
        tilFamCondom = view.findViewById(R.id.facility_til_famCondom);
        tilFamLAM = view.findViewById(R.id.facility_til_famLam);
        tilFamProgesterone = view.findViewById(R.id.facility_til_famProgesterone);
        tilFamImplant = view.findViewById(R.id.facility_til_famImplant);
        tilFamPOP = view.findViewById(R.id.facility_til_famPOP);
        tilFamCOC = view.findViewById(R.id.facility_til_famCOC);
        tilFamPIC = view.findViewById(R.id.facility_til_famPIC);
        tilFamCIC = view.findViewById(R.id.facility_til_famCIC);
        tilFamInternal = view.findViewById(R.id.facility_til_famInternal);
        tilFamPostpartum = view.findViewById(R.id.facility_til_famPostpartum);

    }

    public void setFieldTexts(){
        //facilityModel
        txtFacilityCode.setText(facilityModel.facility_code);
        txtFacilityName.setText(facilityModel.facility_name);
        txtFacilityAbbr.setText(facilityModel.facility_abbr);

        if(!facilityModel.prov_id.trim().isEmpty()){
            province_id = facilityModel.prov_id.trim();
            prov = MainActivity.db.getProvNameById(province_id);
        }

        if(!facilityModel.muncity_id.trim().isEmpty()){
            muncity_id = facilityModel.muncity_id.trim();
            muncity = MainActivity.db.getMuncityNameById(muncity_id);
        }

        if(!facilityModel.brgy_id.trim().isEmpty()){
            brgy_id = facilityModel.brgy_id.trim();
            brgy = MainActivity.db.getBrgyNameById(brgy_id);
        }

        txtProvince.setText(prov);
        txtMuncity.setText(muncity);
        txtBarangay.setText(brgy);

        if(!facilityModel.prov_id.isEmpty()){
            listOfMuncities = MainActivity.db.getMuncityNamesByProv(province_id);
            ArrayAdapter<String> muncityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listOfMuncities);
            txtMuncity.setAdapter(muncityAdapter);

            if(!facilityModel.muncity_id.isEmpty()){
                listOfBarangays = MainActivity.db.getBrgyNamesByProvMuncity(province_id, muncity_id);
                ArrayAdapter<String> brgyAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listOfBarangays);
                txtBarangay.setAdapter(brgyAdapter);
            }
        }

        txtAddress.setText(facilityModel.address);

        txtContact.setText(facilityModel.contact);
        txtEmail.setText(facilityModel.email);
        txtChief.setText(facilityModel.chief_hospital);
        txtServiceCapability.setText(facilityModel.service_capability);
        txtLicenseStatus.setText(facilityModel.license_status);
        txtOwnership.setText(facilityModel.ownership);
        txtFacilityStatus.setText(facilityModel.facility_status);
        txtReferralStatus.setText(facilityModel.referral_status);
        txtPHIC.setText(facilityModel.phic_status);
        txtTransport.setText(facilityModel.transport);
        txtLatitude.setText(facilityModel.latitude);
        txtLongitude.setText(facilityModel.longitude);

        txtDayFrom.setText(facilityModel.sched_day_from);
        txtDayTo.setText(facilityModel.sched_day_to);
        txtTimeFrom.setText(facilityModel.sched_time_from);
        txtTimeTo.setText(facilityModel.sched_time_to);
        txtSchedNote.setText(facilityModel.sched_notes);

        String otherServices ="", consult="", tbdots="", abtc="", dental="", laboratory="", family_planning="";

        for(FacilityService service: facilityServices){
            switch (service.service_type.toUpperCase()){
                case "OTHER SERVICES": otherServices+= service.service +",   ";
                        break;
                case "CONSULTATION": consult+= service.service +",   ";
                    break;
                case "TB DOTS": tbdots+= service.service +",   ";
                    break;
                case "ABTC": abtc+= service.service +",   ";
                    break;
                case "DENTAL": dental+= service.service +",   ";
                    break;
                case "LABORATORY": laboratory+= service.service +",   ";
                    break;
                case "FAMILY PLANNING": family_planning+= service.service +",   ";
                    break;
            }
        }
        txtOtherServices.setText(otherServices);
        txtConsult.setText(consult);
        txtTbdots.setText(tbdots);
        txtAbtc.setText(abtc);
        txtDentalServices.setText(dental);
        txtLaboratoryServices.setText(laboratory);
        txtFamPlan.setText(family_planning);

        for(FacilityService services : facilityServices){
            switch (services.service_type.toUpperCase()){
                case "OTHER SERVICES":
                    switch (services.service.trim().toUpperCase()) {
                        case "BIRTHING": txtBirthing.setText(services.cost); txtBirthing.setTag(services.id);
                            break;
                        case "DIALYSIS CENTER": txtDialysis.setText(services.cost); txtDialysis.setTag(services.id);
                            break;
                    }
                case "CONSULTATION":
                    switch (services.service.trim().toUpperCase()) {
                        case "PRIVATE CLINIC": txtConsultPrivate.setText(services.cost); txtConsultPrivate.setTag(services.id);
                            break;
                        case "PUBLIC CLINIC": txtConsultPublic.setText(services.cost); txtConsultPublic.setTag(services.id);
                            break;
                    }break;
                case "TB DOTS":
                    switch (services.service.trim().toUpperCase()) {
                        case "CATEGORY 1": txtTbdots1.setText(services.cost); txtTbdots1.setTag(services.id);
                            break;
                        case "CATEGORY 2": txtTbdots2.setText(services.cost); txtTbdots2.setTag(services.id);
                            break;
                    }
                    break;
                case "ABTC":
                    switch (services.service.trim().toUpperCase()) {
                        case  "CATEGORY 1": txtAbtc1.setText(services.cost); txtAbtc1.setTag(services.id);
                            break;
                        case  "CATEGORY 2": txtAbtc2.setText(services.cost); txtAbtc2.setTag(services.id);
                            break;
                        case  "CATEGORY 3": txtAbtc3.setText(services.cost); txtAbtc3.setTag(services.id);
                            break;
                    } break;
                case "DENTAL":
                    switch (services.service.trim().toUpperCase()) {
                        case "EXTRACTION": txtDentalExtract.setText(services.cost); txtDentalExtract.setTag(services.id);
                            break;
                        case "TEMPORARY FILLING": txtDentalTempFill.setText(services.cost); txtDentalTempFill.setTag(services.id);
                            break;
                        case "PERMANENT FILLING": txtDentalPermFill.setText(services.cost); txtDentalPermFill.setTag(services.id);
                            break;
                        case "ORTHODONTICS": txtDentalOrtho.setText(services.cost); txtDentalOrtho.setTag(services.id);
                            break;
                        case "ORAL PROPHYLAXIS (CLEANING)": txtDentalClean.setText(services.cost); txtDentalClean.setTag(services.id);
                            break;
                    }break;
                case "LABORATORY":
                    switch (services.service.trim().toUpperCase()) {
                        case "CHEST X-RAY": txtLabXray.setText(services.cost); txtLabXray.setTag(services.id);
                            break;
                        case "COMPLETE BLOOD COUNT W/ PLATELET COUNT": txtLabCBC.setText(services.cost); txtLabCBC.setTag(services.id);
                            break;
                        case "CREATININE": txtLabCreatine.setText(services.cost); txtLabCreatine.setTag(services.id);
                            break;
                        case "ECG": txtLabECG.setText(services.cost); txtLabECG.setTag(services.id);
                            break;
                        case "FBS": txtLabFBS.setText(services.cost); txtLabFBS.setTag(services.id);
                            break;
                        case "FECALYSIS": txtLabFecal.setText(services.cost); txtLabFecal.setTag(services.id);
                            break;
                        case "FECAL OCCULT BLOOD": txtLabFOB.setText(services.cost); txtLabFOB.setTag(services.id);
                            break;
                        case "HBAIC": txtLabHbAIC.setText(services.cost); txtLabHbAIC.setTag(services.id);
                            break;
                        case "LIPID PROFILE": txtLabLipid.setText(services.cost); txtLabLipid.setTag(services.id);
                            break;
                        case "ORAL GLUCOSE TOLERANCE TEST": txtLabOGT.setText(services.cost); txtLabOGT.setTag(services.id);
                            break;
                        case "PAP SMEAR": txtLabPap.setText(services.cost); txtLabPap.setTag(services.id);
                            break;
                        case "SPUTUM MICROSCOPY": txtLabSputum.setText(services.cost); txtLabSputum.setTag(services.id);
                            break;
                        case "URINALYSIS": txtLabUrine.setText(services.cost); txtLabUrine.setTag(services.id);
                            break;
                    }break;
                case "FAMILY PLANNING":
                    switch (services.service.trim().toUpperCase()) {
                        case "NSV": txtFamNSV.setText(services.cost); txtFamNSV.setTag(services.id);
                            break;
                        case "BTL":txtFamBTL.setText(services.cost); txtFamBTL.setTag(services.id);
                            break;
                        case "CONDOM":txtFamCondom.setText(services.cost); txtFamCondom.setTag(services.id);
                            break;
                        case "LAM": txtFamLAM.setText(services.cost); txtFamLAM.setTag(services.id);
                            break;
                        case "PROGESTERONE":txtFamProgesterone.setText(services.cost); txtFamProgesterone.setTag(services.id);
                            break;
                        case "IMPLANT":txtFamImplant.setText(services.cost); txtFamImplant.setTag(services.id);
                            break;
                        case "(ORAL PILLS) PROGESTERONE ONLY": txtFamPOP.setText(services.cost); txtFamPOP.setTag(services.id);
                            break;
                        case "(ORAL PILLS) COMBINED ORAL CONTRACEPTIVES":txtFamCOC.setText(services.cost); txtFamCOC.setTag(services.id);
                            break;
                        case "(DMPA) PURE INJECT CONTRACEPTIVES":txtFamPIC.setText(services.cost); txtFamPIC.setTag(services.id);
                            break;
                        case "(DMPA) COMBINED INJECT CONTRACEPTIVES":txtFamCIC.setText(services.cost); txtFamCIC.setTag(services.id);
                            break;
                        case "(IUD) INTERNAL":txtFamInternal.setText(services.cost); txtFamInternal.setTag(services.id);
                            break;
                        case "(IUD) POSTPARTUM":txtFamPostpartum.setText(services.cost); txtFamPostpartum.setTag(services.id);
                            break;
                    }break;
            }

        }

    }

    public void disableServicesCosts(TextInputLayout[] textInputLayouts){
        for(TextInputLayout til: textInputLayouts){
            til.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            til.setEnabled(false);
        }
    }

    public void enableCostOfService(TextInputLayout til){
        til.setEnabled(true);
        til.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_edittext,null));
    }

    public void saveUpdateFacilityService(EditText tv, String service_type, String service){
        if(tv!=null){
            facilityService = new FacilityService("", facilityModel.facility_code, service_type, service, tv.getText().toString(), "1");
            if(tv.getTag()!=null){
                if(!tv.getTag().toString().isEmpty()){
                    facilityService = new FacilityService(tv.getTag().toString(), facilityModel.facility_code, service_type, service, tv.getText().toString(), "1");
                    MainActivity.db.updateFacilityService(facilityService);
                }else{
                    MainActivity.db.addFacilityService(facilityService);
                }
            }else{
                MainActivity.db.addFacilityService(facilityService);
            }
        }else{ //for pharmacy
            facilityService = new FacilityService("", facilityModel.facility_code, service_type, service, "", "1");
            MainActivity.db.addFacilityService(facilityService);
        }

    }


    public void showFacilityCheckerDialog() {
        View checkerDialogView = LayoutInflater.from(getContext()).inflate(R.layout.profile_checker_dialog, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(checkerDialogView);

        final EditText txtCheckerCode, txtCheckerName, txtCheckerAbbr;
        final ListView lvMatchingProfiles;
        final LinearLayout txtFrame, lvFrame;
        TextView btnCheck, btnUpdate, btnNew;

        txtCheckerCode = checkerDialogView.findViewById(R.id.checker_fname);
        txtCheckerName = checkerDialogView.findViewById(R.id.checker_mname);
        txtCheckerAbbr = checkerDialogView.findViewById(R.id.checker_lname);

        final TextInputLayout tilCode, tilName, tilAbbr;
        tilCode = checkerDialogView.findViewById(R.id.checker_fname_til);
        tilName = checkerDialogView.findViewById(R.id.checker_mname_til);
        tilAbbr = checkerDialogView.findViewById(R.id.checker_lname_til);

        tilCode.setHint("Facility Code");
        tilName.setHint("Facility Name");
        tilAbbr.setHint("Facility Abbr");


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
                String name, code, abbr;

                code = txtCheckerCode.getText().toString().trim();
                name = txtCheckerName.getText().toString().trim();
                abbr = txtCheckerAbbr.getText().toString().trim();

                matchingFacilities = MainActivity.db.getMatchingFacilities(code, name, abbr);

                if (matchingFacilities.size() > 0) {
                    ListAdapter adapter = new ListAdapter(getContext(), R.layout.population_dialog_item, null, null, null,matchingFacilities,null);
                    lvMatchingProfiles.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    lvFrame.setVisibility(View.VISIBLE);
                    txtFrame.setVisibility(View.GONE);
                } else {
                    txtFacilityName.setText(name);
                    txtFacilityCode.setText(code);
                    txtFacilityAbbr.setText(abbr);
                    checkerDialog.dismiss();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lvMatchingProfiles.getCheckedItemPosition() >= 0) {
                    facilityModel = matchingFacilities.get(lvMatchingProfiles.getCheckedItemPosition());
                    facilityServices = MainActivity.db.getFacilityServices(facilityModel.facility_code);
                    setFieldTexts();
                    checkerDialog.dismiss();
                } else
                    Toast.makeText(getContext(), "Please select profile to update.", Toast.LENGTH_SHORT).show();
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFacilityCode.setText(txtCheckerCode.getText().toString().trim());
                txtFacilityName.setText(txtCheckerName.getText().toString().trim());
                txtFacilityAbbr.setText(txtCheckerAbbr.getText().toString().trim());
                updateBtn.setText("ADD FACILITY");
                updateBtn.setTag("add");
                checkerDialog.dismiss();
            }
        });
    }

    public void setMoneyTextWatcher(Context c){
        /**Other Services*/
        Constants.setMoneyTextWatcher(c, txtBirthing);
        Constants.setMoneyTextWatcher(c, txtDialysis);
        /**Consult*/
        Constants.setMoneyTextWatcher(c, txtConsultPublic);
        Constants.setMoneyTextWatcher(c, txtConsultPrivate);
        /**TBDOTS*/
        Constants.setMoneyTextWatcher(c, txtTbdots1);
        Constants.setMoneyTextWatcher(c, txtTbdots2);
        /**ABTC*/
        Constants.setMoneyTextWatcher(c, txtAbtc1);
        Constants.setMoneyTextWatcher(c, txtAbtc2);
        Constants.setMoneyTextWatcher(c, txtAbtc3);
        /**Dental*/
        Constants.setMoneyTextWatcher(c, txtDentalExtract);
        Constants.setMoneyTextWatcher(c, txtDentalTempFill);
        Constants.setMoneyTextWatcher(c, txtDentalPermFill);
        Constants.setMoneyTextWatcher(c, txtDentalClean);
        Constants.setMoneyTextWatcher(c, txtDentalOrtho);
        /**Lab*/
        Constants.setMoneyTextWatcher(c, txtLabXray);
        Constants.setMoneyTextWatcher(c, txtLabCBC);
        Constants.setMoneyTextWatcher(c, txtLabCreatine);
        Constants.setMoneyTextWatcher(c, txtLabECG);
        Constants.setMoneyTextWatcher(c, txtLabFBS);
        Constants.setMoneyTextWatcher(c, txtLabFecal);
        Constants.setMoneyTextWatcher(c, txtLabFOB);
        Constants.setMoneyTextWatcher(c, txtLabHbAIC);
        Constants.setMoneyTextWatcher(c, txtLabLipid);
        Constants.setMoneyTextWatcher(c, txtLabOGT);
        Constants.setMoneyTextWatcher(c, txtLabPap);
        Constants.setMoneyTextWatcher(c, txtLabSputum);
        Constants.setMoneyTextWatcher(c, txtLabUrine);
        /**family Planning*/
        Constants.setMoneyTextWatcher(c, txtFamNSV);
        Constants.setMoneyTextWatcher(c, txtFamBTL);
        Constants.setMoneyTextWatcher(c, txtFamCondom);
        Constants.setMoneyTextWatcher(c, txtFamLAM);
        Constants.setMoneyTextWatcher(c, txtFamProgesterone);
        Constants.setMoneyTextWatcher(c, txtFamImplant);
        Constants.setMoneyTextWatcher(c, txtFamPOP);
        Constants.setMoneyTextWatcher(c, txtFamCOC);
        Constants.setMoneyTextWatcher(c, txtFamPIC);
        Constants.setMoneyTextWatcher(c, txtFamCIC);
        Constants.setMoneyTextWatcher(c, txtFamInternal);
        Constants.setMoneyTextWatcher(c, txtFamPostpartum);
 /*       *//**Other Services*//*
        Constants.setMoneyTextWatcher(getContext(), txtBirthing);
        Constants.setMoneyTextWatcher(getContext(), txtDialysis);
        *//**Consult*//*
        Constants.setMoneyTextWatcher(getContext(), txtConsultPublic);
        Constants.setMoneyTextWatcher(getContext(), txtConsultPrivate);
        *//**TBDOTS*//*
        Constants.setMoneyTextWatcher(getContext(), txtTbdots1);
        Constants.setMoneyTextWatcher(getContext(), txtTbdots2);
        *//**ABTC*//*
        Constants.setMoneyTextWatcher(getContext(), txtAbtc1);
        Constants.setMoneyTextWatcher(getContext(), txtAbtc2);
        Constants.setMoneyTextWatcher(getContext(), txtAbtc3);
        *//**Dental*//*
        Constants.setMoneyTextWatcher(getContext(), txtDentalExtract);
        Constants.setMoneyTextWatcher(getContext(), txtDentalTempFill);
        Constants.setMoneyTextWatcher(getContext(), txtDentalPermFill);
        Constants.setMoneyTextWatcher(getContext(), txtDentalClean);
        Constants.setMoneyTextWatcher(getContext(), txtDentalOrtho);
        *//**Lab*//*
        Constants.setMoneyTextWatcher(getContext(), txtLabXray);
        Constants.setMoneyTextWatcher(getContext(), txtLabCBC);
        Constants.setMoneyTextWatcher(getContext(), txtLabCreatine);
        Constants.setMoneyTextWatcher(getContext(), txtLabECG);
        Constants.setMoneyTextWatcher(getContext(), txtLabFBS);
        Constants.setMoneyTextWatcher(getContext(), txtLabFecal);
        Constants.setMoneyTextWatcher(getContext(), txtLabFOB);
        Constants.setMoneyTextWatcher(getContext(), txtLabHbAIC);
        Constants.setMoneyTextWatcher(getContext(), txtLabLipid);
        Constants.setMoneyTextWatcher(getContext(), txtLabOGT);
        Constants.setMoneyTextWatcher(getContext(), txtLabPap);
        Constants.setMoneyTextWatcher(getContext(), txtLabSputum);
        Constants.setMoneyTextWatcher(getContext(), txtLabUrine);
        *//**family Planning*//*
        Constants.setMoneyTextWatcher(getContext(), txtFamNSV);
        Constants.setMoneyTextWatcher(getContext(), txtFamBTL);
        Constants.setMoneyTextWatcher(getContext(), txtFamCondom);
        Constants.setMoneyTextWatcher(getContext(), txtFamLAM);
        Constants.setMoneyTextWatcher(getContext(), txtFamProgesterone);
        Constants.setMoneyTextWatcher(getContext(), txtFamImplant);
        Constants.setMoneyTextWatcher(getContext(), txtFamPOP);
        Constants.setMoneyTextWatcher(getContext(), txtFamCOC);
        Constants.setMoneyTextWatcher(getContext(), txtFamPIC);
        Constants.setMoneyTextWatcher(getContext(), txtFamCIC);
        Constants.setMoneyTextWatcher(getContext(), txtFamInternal);
        Constants.setMoneyTextWatcher(getContext(), txtFamPostpartum);*/
    }

}
