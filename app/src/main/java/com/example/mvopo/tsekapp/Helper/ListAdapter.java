package com.example.mvopo.tsekapp.Helper;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvopo.tsekapp.Fragments.FeedbackFragment;
import com.example.mvopo.tsekapp.MainActivity;
import com.example.mvopo.tsekapp.Model.FacilityModel;
import com.example.mvopo.tsekapp.Model.FamilyProfile;
import com.example.mvopo.tsekapp.Model.FeedBack;
import com.example.mvopo.tsekapp.Model.ServicesStatus;
import com.example.mvopo.tsekapp.Model.SpecialistModel;
import com.example.mvopo.tsekapp.R;

import java.util.List;

/**
 * Created by mvopo on 10/19/2017.
 */

public class ListAdapter extends ArrayAdapter {

    Context context;
    int layoutId;
    List<FamilyProfile> familyProfiles;
    List<ServicesStatus> serviceStatus;
    List<FeedBack> feedbacks;

    List<FacilityModel> facilityModels;
    List<SpecialistModel> specialistModels;


    LayoutInflater inflater;

    public ListAdapter(Context context, int resource, List familyProfiles, List serviceStatus, List feedbacks, List facility, List specialistModels) {
        super(context, resource);

        this.context = context;
        layoutId = resource;
        this.familyProfiles = familyProfiles;
        this.serviceStatus = serviceStatus;
        this.feedbacks = feedbacks;
        this.facilityModels = facility;
        this.specialistModels = specialistModels;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        int size = 0;

        if(familyProfiles!=null) size = familyProfiles.size();
        if(serviceStatus!=null) size = serviceStatus.size();
        if(feedbacks!=null) size = feedbacks.size();
        if(facilityModels!=null) size = facilityModels.size();
        if(specialistModels!=null) size = specialistModels.size();
        return size;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(layoutId, parent, false);

        if(layoutId == R.layout.population_item){
            if(familyProfiles!=null){
                TextView name = convertView.findViewById(R.id.population_name);
                TextView id = convertView.findViewById(R.id.population_family_id);

                String fullName = familyProfiles.get(position).fname + " " +
                        familyProfiles.get(position).mname + " " + familyProfiles.get(position).lname + " " + familyProfiles.get(position).suffix;

                name.setText(fullName);
                id.setText(familyProfiles.get(position).familyId);

                if(familyProfiles.get(position).isHead.equalsIgnoreCase("Yes")) name.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            }else if(facilityModels!=null){ /**inserted rml 04/29/22 */
                TextView name = convertView.findViewById(R.id.population_name);
                TextView id = convertView.findViewById(R.id.population_family_id);

                name.setText(facilityModels.get(position).facility_name);
                id.setText(facilityModels.get(position).facility_code);
            }else if(specialistModels!=null){ /**inserted rml 04/29/22 */
                TextView facility = convertView.findViewById(R.id.population_name);
                TextView name = convertView.findViewById(R.id.population_family_id);

                name.setText(specialistModels.get(position).fname + " " + specialistModels.get(position).mname +" " + specialistModels.get(position).lname);
                facility.setText(specialistModels.get(position).username);
            }

        }else if(layoutId == R.layout.population_dialog_item){
            if(familyProfiles!=null){
                TextView name = convertView.findViewById(R.id.profile_name);
                TextView relation = convertView.findViewById(R.id.profile_relation);


                String fullName = familyProfiles.get(position).lname + ", " +
                        familyProfiles.get(position).fname + " " + familyProfiles.get(position).mname + " " + familyProfiles.get(position).suffix;

                name.setText(fullName);
                relation.setText("(" + familyProfiles.get(position).relation + ", " + familyProfiles.get(position).sex + ")");

                if(familyProfiles.get(position).isHead.equalsIgnoreCase("Yes")) name.setTextColor(getContext().getResources().getColor(R.color.colorAccent));

            }else if(specialistModels!=null){
                TextView name = convertView.findViewById(R.id.profile_name);
                TextView username = convertView.findViewById(R.id.profile_relation);


                String fullName = specialistModels.get(position).lname + ", " +
                        specialistModels.get(position).fname + " " + specialistModels.get(position).mname;

                name.setText(fullName);
                username.setText("(" +specialistModels.get(position).username+ ")");
            }else if(facilityModels!=null){
                TextView name = convertView.findViewById(R.id.profile_name);
                TextView id = convertView.findViewById(R.id.profile_relation);

                name.setText(facilityModels.get(position).facility_name);
                id.setText(facilityModels.get(position).facility_code);
            }


        }else if(layoutId == R.layout.services_item){
            TextView name = convertView.findViewById(R.id.services_name);
            ImageView ivGroup1 = convertView.findViewById(R.id.iv_group1);
            ImageView ivGroup2 = convertView.findViewById(R.id.iv_group2);
            ImageView ivGroup3 = convertView.findViewById(R.id.iv_group3);

            name.setText(serviceStatus.get(position).name);

            if(serviceStatus.get(position).group1.equals("1")) ivGroup1.setImageResource(R.drawable.success);
            if(serviceStatus.get(position).group2.equals("1")) ivGroup2.setImageResource(R.drawable.success);
            if(serviceStatus.get(position).group3.equals("1")) ivGroup3.setImageResource(R.drawable.success);
        }else if(layoutId == R.layout.feedback_item){
            TextView tvSubject = convertView.findViewById(R.id.feedback_subject);
            final TextView tvBody = convertView.findViewById(R.id.feedback_body);
            ImageView ivDelete = convertView.findViewById(R.id.feedback_delete);

            final String body = feedbacks.get(position).body;
            tvSubject.setText(feedbacks.get(position).subject);
            tvBody.setText(body);

            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete this feedback?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.db.deleteFeedback(feedbacks.get(position).id);
                            MainActivity.ft = MainActivity.fm.beginTransaction();
                            MainActivity.ft.replace(R.id.fragment_container, new FeedbackFragment()).commit();
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }
            });
        }

        return convertView;
    }
}
