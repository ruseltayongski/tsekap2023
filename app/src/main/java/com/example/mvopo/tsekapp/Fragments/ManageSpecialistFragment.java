package com.example.mvopo.tsekapp.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mvopo.tsekapp.Helper.ListAdapter;
import com.example.mvopo.tsekapp.MainActivity;
import com.example.mvopo.tsekapp.Model.AffiliatedFacilitiesModel;
import com.example.mvopo.tsekapp.Model.Constants;
import com.example.mvopo.tsekapp.Model.SpecialistModel;
import com.example.mvopo.tsekapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ManageSpecialistFragment extends Fragment implements View.OnClickListener {
    View view;
    Button updateBtn, addFacility;
    EditText  txtSpecialization, txtContact, txtEmail, txtSchedule, txtFee;
    AutoCompleteTextView txtFacility;
    LinearLayout facilitiesHolder;
    String fName, mName, lName, username;
    EditText txtFName, txtMName, txtLName;

    Boolean toUpdate;
    SpecialistModel specialist;

    ArrayList<AffiliatedFacilitiesModel> facilities = new ArrayList<>();
    ArrayList<String> removedFacilityIDs = new ArrayList<>();
    ArrayList<SpecialistModel> matchingSpecialists = new ArrayList<>();
    ArrayList<String> facilityNames = new ArrayList<>();
    ArrayAdapter<String> facilityAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_specialist, container, false);
        findViewByIds();

        toUpdate = getArguments().getBoolean("toUpdate");
        specialist = getArguments().getParcelable("specialist");
        facilityNames = MainActivity.db.getFacilityNames();

        facilityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, facilityNames);

        if(toUpdate){
            facilities = MainActivity.db.getAffiliatedFacilities(specialist.username);
            setFieldTexts();
        }else{
            showSpecialistCheckerDialog();
        }


        updateBtn.setOnClickListener(this);
        addFacility.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.specialist_add: //add affiliated facility
                addFacility();
                break;

            case R.id.specialist_remove: //remove affiliated facility
                Log.e("affiliated onclick", "tag: "+v.getTag());
                txtSpecialization = facilitiesHolder.getChildAt(Integer.parseInt(v.getTag().toString())).findViewById(R.id.specialist_specialization);
                if(txtSpecialization.getTag()!=null){
                    if(!txtSpecialization.getTag().toString().isEmpty()){
                        removedFacilityIDs.add(txtSpecialization.getTag().toString().trim());
                    }
                }
                removeFacility(v.getTag().toString());
                break;

            case R.id.specialistBtn: //update specialist
                DateFormat df = new SimpleDateFormat("yyMMddHHmm"); // last 2digit of Year, Month, Day, Hour24, Minutes
                String code = df.format(Calendar.getInstance().getTime());
                boolean affFacility = false, specialistName = false;
                fName = txtFName.getText().toString().trim();
                mName= txtMName.getText().toString().trim();
                lName= txtLName.getText().toString().trim();

                if(fName.isEmpty()){
                    txtFName.setError("Required");
                    txtFName.requestFocus();
                }

                if(lName.isEmpty()){
                    txtLName.setError("Required");
                    txtLName.requestFocus();
                }

                if(fName.isEmpty() || lName.isEmpty()){
                    specialistName = false;
                }else{
                    if(toUpdate){
                        specialist = new SpecialistModel(specialist.id, specialist.username, fName, mName, lName,"1");
                        MainActivity.db.updateSpecialist(specialist);
                    }else { //add new
                        username = fName.substring(0,1).toLowerCase(Locale.ROOT) + lName.toLowerCase(Locale.ROOT) + code;
                        specialist = new SpecialistModel("",username, fName, mName, lName,"1");
                        MainActivity.db.addSpecialist(specialist);
                    }
                    specialistName = true;
                }

                int child = facilitiesHolder.getChildCount();
                for(int x=0; x<child ; x++){
                    View view = facilitiesHolder.getChildAt(x); //reading
                    txtFacility = view.findViewById(R.id.specialist_facility);
                    txtSpecialization = view.findViewById(R.id.specialist_specialization);
                    txtContact = view.findViewById(R.id.specialist_contact);
                    txtEmail = view.findViewById(R.id.specialist_email);
                    txtSchedule = view.findViewById(R.id.specialist_sched);
                    txtFee = view.findViewById(R.id.specialist_fee);

                    String facility_code ="";
                    String facilityName = txtFacility.getText().toString().trim();
                    if(!facilityName.isEmpty()){
                         facility_code = MainActivity.db.getFacilityCodeByName(facilityName);
                    }

                    String contact = txtContact.getText().toString().trim();
                    String email = txtEmail.getText().toString().trim();

                    if(facility_code.isEmpty()){
                        txtFacility.setError("Required");
                        txtFacility.requestFocus();
                    }

                    if(contact.isEmpty()){
                        txtContact.setError("Required");
                        txtContact.requestFocus();
                    }

                    if(email.isEmpty()){
                        txtEmail.setError("Required");
                        txtEmail.requestFocus();
                    }

                    if(facility_code.isEmpty() || contact.isEmpty() || email.isEmpty()){
                        affFacility = false;
                        break;
                    } else{
                        if(txtSpecialization.getTag()!=null){
                            if(!txtSpecialization.getTag().toString().isEmpty()){
                                Log.e("specialist", "specialization tag toUpdate" + txtSpecialization.getTag().toString());
                                AffiliatedFacilitiesModel facility = new AffiliatedFacilitiesModel(""+txtSpecialization.getTag().toString(), specialist.username, facility_code,txtSpecialization.getText().toString().trim(),
                                        contact, email, txtSchedule.getText().toString().trim(),txtFee.getText().toString().trim(),"1");
                                MainActivity.db.updateAffiliatedFacility(facility);
                                affFacility = true;
                            }
                        }else {
                            AffiliatedFacilitiesModel facility = new AffiliatedFacilitiesModel("", specialist.username, facility_code,txtSpecialization.getText().toString().trim(),
                                    contact, email, txtSchedule.getText().toString().trim(),txtFee.getText().toString().trim(),"1");
                            MainActivity.db.addAffiliatedFacility(facility);
                            affFacility = true;
                        }
                    }
                }

                for(String s : removedFacilityIDs){
                    MainActivity.db.deleteAffiliatedFacilityById(s);
                }

                if(affFacility && specialistName){
                    MainActivity.fm.popBackStack();
                }


                break;
        }
    }

    public void addFacility(){
        View affiliatedFacilityView = LayoutInflater.from(getContext()).inflate(R.layout.specialist_facilities, null);
        affiliatedFacilityView.findViewById(R.id.specialist_remove).setOnClickListener(this);
        AutoCompleteTextView facility = affiliatedFacilityView.findViewById(R.id.specialist_facility);
        facility.setAdapter(facilityAdapter);

        Constants.setMoneyTextWatcher(getContext(), affiliatedFacilityView.findViewById(R.id.specialist_fee));

        facilitiesHolder.addView(affiliatedFacilityView);
        changeChildTags();
    }

    public void changeChildTags(){
        for (int pos = 0;  pos<facilitiesHolder.getChildCount(); pos++){
            Button btn = facilitiesHolder.getChildAt(pos).findViewById(R.id.specialist_remove);
            btn.setTag(pos);
        }
    }

    public void removeFacility(String index){
        facilitiesHolder.removeViewAt(Integer.parseInt(index));
        changeChildTags();
    }

    public void setFieldTexts(){
        Log.e("specialist", "setFieldTexts specialist id: " + specialist.id);
        txtFName.setText(specialist.fname);
        txtMName.setText(specialist.mname);
        txtLName.setText(specialist.lname);

        Log.e("specialist","msf, facility count: " + facilities.size());
        for(AffiliatedFacilitiesModel model : facilities){
            View affiliatedFacilityView = LayoutInflater.from(getContext()).inflate(R.layout.specialist_facilities, null);


            txtFacility = affiliatedFacilityView.findViewById(R.id.specialist_facility);
            txtSpecialization = affiliatedFacilityView.findViewById(R.id.specialist_specialization);
            txtContact = affiliatedFacilityView.findViewById(R.id.specialist_contact);
            txtEmail = affiliatedFacilityView.findViewById(R.id.specialist_email);
            txtSchedule = affiliatedFacilityView.findViewById(R.id.specialist_sched);
            txtFee = affiliatedFacilityView.findViewById(R.id.specialist_fee);

            txtSpecialization.setText(model.specialization);
            txtSpecialization.setTag(""+model.id);

            txtContact.setText(model.contact);
            txtEmail.setText(model.email);
            txtSchedule.setText(model.schedule);
            txtFee.setText(model.fee);
            affiliatedFacilityView.findViewById(R.id.specialist_remove).setOnClickListener(this);
            Constants.setMoneyTextWatcher(getContext(), txtFee);

            if(!model.facility_code.trim().isEmpty()){
                String facilityName = MainActivity.db.getFacilityNameByCode(model.facility_code);
                txtFacility.setText(facilityName);
                txtFacility.setTag(model.facility_code);
            }
            txtFacility.setAdapter(facilityAdapter);

            facilitiesHolder.addView(affiliatedFacilityView);
            changeChildTags();
        }
    }

    public void showSpecialistCheckerDialog() {
        View checkerDialogView = LayoutInflater.from(getContext()).inflate(R.layout.profile_checker_dialog, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(checkerDialogView);

        final EditText txtCheckerFname, txtCheckerMname, txtCheckerLname;
        final ListView lvMatchingProfiles;
        final LinearLayout txtFrame, lvFrame;
        TextView btnCheck, btnUpdate, btnNew;

        txtCheckerFname = checkerDialogView.findViewById(R.id.checker_fname);
        txtCheckerMname = checkerDialogView.findViewById(R.id.checker_mname);
        txtCheckerLname = checkerDialogView.findViewById(R.id.checker_lname);
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
                String fname, mname, lname;

                fname = txtCheckerFname.getText().toString().trim();
                mname = txtCheckerMname.getText().toString().trim();
                lname = txtCheckerLname.getText().toString().trim();

                matchingSpecialists = MainActivity.db.getMatchingSpecialists(fname, mname, lname);

                if (matchingSpecialists.size() > 0) {
                    ListAdapter adapter = new ListAdapter(getContext(), R.layout.population_dialog_item, null, null, null,null,matchingSpecialists);
                    lvMatchingProfiles.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    lvFrame.setVisibility(View.VISIBLE);
                    txtFrame.setVisibility(View.GONE);
                } else {
                    txtFName.setText(fname);
                    txtMName.setText(mname);
                    txtLName.setText(lname);
                    checkerDialog.dismiss();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lvMatchingProfiles.getCheckedItemPosition() >= 0) {
                    specialist = matchingSpecialists.get(lvMatchingProfiles.getCheckedItemPosition());
                    facilities = MainActivity.db.getAffiliatedFacilities(specialist.username);
                    setFieldTexts();
                    checkerDialog.dismiss();
                } else
                    Toast.makeText(getContext(), "Please select profile to update.", Toast.LENGTH_SHORT).show();
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFName.setText(txtCheckerFname.getText().toString().trim());
                txtMName.setText(txtCheckerMname.getText().toString().trim());
                txtLName.setText(txtCheckerLname.getText().toString().trim());
                updateBtn.setText("ADD SPECIALIST");
                updateBtn.setTag("add");
                checkerDialog.dismiss();
            }
        });
    }

    public void findViewByIds(){
        updateBtn = view.findViewById(R.id.specialistBtn);
        txtFName = view.findViewById(R.id.specialist_fname);
        txtMName = view.findViewById(R.id.specialist_mname);
        txtLName = view.findViewById(R.id.specialist_lname);

        facilitiesHolder = view.findViewById(R.id.specialist_holder1);
        addFacility = view.findViewById(R.id.specialist_add);

    }


}
