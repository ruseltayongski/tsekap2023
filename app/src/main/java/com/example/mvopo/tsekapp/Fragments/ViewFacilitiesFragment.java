package com.example.mvopo.tsekapp.Fragments;
import androidx.fragment.app.Fragment;

/*import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


import com.example.mvopo.tsekapp.Helper.JSONApi;
import com.example.mvopo.tsekapp.Helper.ListAdapter;
import com.example.mvopo.tsekapp.MainActivity;
import com.example.mvopo.tsekapp.Model.Constants;
import com.example.mvopo.tsekapp.Model.FacilityModel;
import com.example.mvopo.tsekapp.R;

import java.util.ArrayList;
import java.util.List;*/
//todo: Uncomment for managing facility
public class ViewFacilitiesFragment extends Fragment {

  /*  public View view;
    ListView lv;
    ListAdapter adapter;
    ImageView btnSearch;
    EditText txtSearch;
    ManageFacilityFragment mff;
    Menu menu;
    Bundle bundle = new Bundle();
    List<FacilityModel> facilityModelList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_layout, container, false);
        lv = view.findViewById(R.id.lv);
        txtSearch = view.findViewById(R.id.list_searchTxt);
        btnSearch = view.findViewById(R.id.list_searchBtn);
        mff=new ManageFacilityFragment();
        facilityModelList = MainActivity.db.getFacilities("");

        adapter = new ListAdapter(getContext(), R.layout.population_item, null, null, null, facilityModelList,null);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                View focusedView = getActivity().getCurrentFocus();
                if (focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                bundle.putParcelable("facilityModel", facilityModelList.get(position));
                bundle.putBoolean("toUpdate", true);
                mff.setArguments(bundle);
                MainActivity.ft = MainActivity.fm.beginTransaction();
                MainActivity.ft.replace(R.id.fragment_container, mff).addToBackStack("").commit();

            }});

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = txtSearch.getText().toString();

                facilityModelList.clear();

                if (!search.isEmpty()) {
                    facilityModelList.addAll(MainActivity.db.getFacilities(search));
                }else{
                    facilityModelList.addAll(MainActivity.db.getFacilities(""));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showTutorial();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.download_add, menu);

        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:

                FacilityModel facilityModel = new FacilityModel("", "", "", "", "", "", "", "", "", "", ""
                        , "", "", "", "", "",   "", "", "", "", ""
                        , "", "", "","", "" );

                mff = new ManageFacilityFragment();
                bundle.putParcelable("facilityModel", facilityModel);
                bundle.putBoolean("toUpdate", false);
                mff.setArguments(bundle);
                MainActivity.ft = MainActivity.fm.beginTransaction();
                MainActivity.ft.replace(R.id.fragment_container, mff).addToBackStack("").commit();
                break;

            case R.id.action_download: // http://222.127.126.34/tsekap/dummy/apiv21/getSpecialists/3270
                int notUploadedCount = MainActivity.db.getFacilityUploadableCount();
                String msg = "Downloading data from server will overwrite all records of facilities in this device.";
                if(notUploadedCount>0){
                    msg += "\n\n" + notUploadedCount + " not uploaded facility(s) will also be deleted";
                }
                msg += " Do you wish to proceed downloading?";

                AlertDialog.Builder builderDownload = new AlertDialog.Builder(getContext());
                builderDownload.setMessage(msg);
                builderDownload.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.pd = ProgressDialog.show(getContext(), "Loading...", "Please wait...", false, false);
                        String url = Constants.url.replace("?", "/getFacilities/") + MainActivity.user.id ;
                        JSONApi.getInstance(getContext()).getFacilities(url, 0, 0);
                    }
                });
                builderDownload.setNegativeButton("Cancel", null);
                builderDownload.show();

                break;

            case R.id.action_upload: // http://222.127.126.34/tsekap/dummy/apiv21/uploadFacility
                int uploadCount = MainActivity.db.getFacilityUploadableCount();
                if(uploadCount>0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Uploadable Facility(s): " + uploadCount );
                    builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.pd = ProgressDialog.show(getContext(), "Loading...", "Uploading " + uploadCount + " facility(s). \nPlease wait...", false, false);
                            String url = Constants.url.replace("?", "/uploadFacility");
                            JSONApi.getInstance(getContext()).uploadFacilities(url, Constants.getFacilitiesJson(), uploadCount, 0);
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }else {
                    Toast.makeText(getContext(), "Nothing to upload", Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    public void showTutorial(){
        MainActivity.queue.clear();
        MainActivity.queue.add(MainActivity.makeSpotlightView(view.findViewById(R.id.spotlight_search_focus),
                "Looking for Facility?",
                "Well, well, well! Just type their name/code/abbr and i'll find them for yah.",
                "ViewFacilitySearch"));

        MainActivity.queue.add(MainActivity.makeSpotlightView(lv,
                "Eyes Here!",
                "These are list of health facilities",
                "ViewFacilityList"));

        MainActivity.queue.add(MainActivity.makeSpotlightView(MainActivity.toolbar.getChildAt(2),
                "Do Something",
                "*Plus Button: \n\t\tJust click me if you want to add a new Health Facility\n\n"+
                        "*Arrow Down Button: \n\t\tClick me if you want to download list of Health Facilities from server. " +
                        "I suggest you upload first before clicking me to count your newly added Facilities\n\n" +

                        "*Arrow Up Button: \n\t\tClick me if you want to upload your newly edited/added Health Facilities to server",
                "ViewFacilityAdd"));

        MainActivity.startSequence();
    }
*/
}
