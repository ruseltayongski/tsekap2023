package com.example.mvopo.tsekapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/*import com.example.mvopo.tsekapp.Fragments.ManageFacilityFragment;
import com.example.mvopo.tsekapp.Fragments.ManageSpecialistFragment;
import com.example.mvopo.tsekapp.Fragments.ViewFacilitiesFragment;
import com.example.mvopo.tsekapp.Fragments.ViewSpecialistFragment;
import androidx.appcompat.view.menu.ActionMenuItemView;*/

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvopo.tsekapp.Fragments.ChangePassFragment;
import com.example.mvopo.tsekapp.Fragments.FeedbackFragment;
import com.example.mvopo.tsekapp.Fragments.HomeFragment;
import com.example.mvopo.tsekapp.Fragments.PendingDengvaxiaFragment;
import com.example.mvopo.tsekapp.Fragments.ViewChatThreadFragment;
import com.example.mvopo.tsekapp.Fragments.ViewPopulationFragment;
import com.example.mvopo.tsekapp.Helper.ConnectionChecker;
import com.example.mvopo.tsekapp.Helper.DBHelper;
import com.example.mvopo.tsekapp.Helper.JSONApi;
import com.example.mvopo.tsekapp.Model.Constants;
import com.example.mvopo.tsekapp.Model.User;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager fm;
    public static FragmentTransaction ft;
    public static User user;
    public static DBHelper db;
    public static ProgressDialog pd;
    public static String rowID = "";
    public static FloatingActionMenu fabMenu;
    public static Toolbar toolbar;
    public static Activity mainActivity;
    public static Queue<SpotlightView.Builder> queue = new LinkedList<>();

    int[] location = new int[2];

    NavigationView navigationView;
    FloatingActionButton fabDownload, fabUpload;
    String TAG = "MainActivity";

    public static HomeFragment hf = new HomeFragment();
    public static ViewPopulationFragment vpf = new ViewPopulationFragment();
    //public static ServicesStatusFragment ssf = new ServicesStatusFragment(); todo: uncomment for 3 must services status

    /* //todo: Uncomment for managing facility and specialist
    public static ViewFacilitiesFragment vff = new ViewFacilitiesFragment();
    public static ViewSpecialistFragment vsf = new ViewSpecialistFragment();
    public static ManageFacilityFragment mff=new ManageFacilityFragment();
    public static ManageSpecialistFragment msf = new ManageSpecialistFragment();*/


    FeedbackFragment ff = new FeedbackFragment();
    PendingDengvaxiaFragment pdf = new PendingDengvaxiaFragment();
    /*AvailServicesPopulationFragment aspf = new AvailServicesPopulationFragment();*/ //todo: Uncomment for avail services
    ChangePassFragment cpf = new ChangePassFragment();
    ViewChatThreadFragment vctf = new ViewChatThreadFragment();


    //AlertDialog dialog;
    View headerView;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(this);

        //new PreferencesManager(this).resetAll();
        mainActivity = this;
        db = new DBHelper(this);

        try {
            Bundle bundle = getIntent().getExtras();
            user = bundle.getParcelable("user");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, hf).commit();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                showNavTutorial();
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpHeader();

        fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);
        fabDownload = (FloatingActionButton) findViewById(R.id.download);
        fabUpload = (FloatingActionButton) findViewById(R.id.upload);

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try{ takePicture(); }catch (Exception e){ Log.e("QWEASD", e.getMessage()); };

                int uploadableCount = db.getProfileUploadableCount();
                // int serviceCount = db.getServicesCount(); //todo: Uncomment for avail services
                int profileCount = db.getProfilesCount("");
                if (profileCount == 0) {
                    downloadProfiles();
                } else if (uploadableCount > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please upload data before syncing from server.\n\nProfile(s): " + uploadableCount /*+
                            "\nServices Availed: " + serviceCount*/); //todo: Uncomment for avail services
                    builder.setPositiveButton("Ok", null);
                    builder.show();
                } else {
//                    try{ takePicture(); }catch (Exception e){ Log.e("QWEASD1", e.getMessage()); };
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Downloading data from server will clear all records, Do you wish to proceed downloading?");
                    builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteProfiles();
                            db.deleteProfileMedications();
                            downloadProfiles();
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }
            }
        });

        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int uploadableCount = db.getProfileUploadableCount();
                //final int serviceCount = db.getServicesCount(); //todo: Uncomment for avail services

                if (uploadableCount /*+ serviceCount*/ > 0) { //todo: Uncomment for avail services
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Uploadable Profile: " + uploadableCount /*+ "\nUploadable Services: " + serviceCount*/); //todo: Uncomment for avail services
                    builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            MainActivity.pd = ProgressDialog.show(MainActivity.this, "Uploading 1/" + (uploadableCount /*+ serviceCount*/), //todo: Uncomment for avail services
                                    "Please wait...",   false, true);

                            if (uploadableCount > 0) {
                                String url = Constants.url.replace("?", "/syncprofilev21");
                                JSONApi.getInstance(MainActivity.this).uploadProfile(url, Constants.getProfileJson(), uploadableCount, 1);
                            } /*else if (serviceCount > 0) { //todo: Uncomment for avail services
                                ServiceAvailed serviceAvailed = db.getServiceForUpload();
                                JSONApi.getInstance(MainActivity.this).uploadServices(Constants.url.replace("?", "/syncservices"), serviceAvailed,
                                        1, uploadableCount + serviceCount);
                            }*/ else {
                                //upload services here
                                Toast.makeText(MainActivity.this, "Uploading services is under development", Toast.LENGTH_SHORT).show();
                                MainActivity.pd.dismiss();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                } else {
                    Toast.makeText(MainActivity.this, "Nothing to upload", Toast.LENGTH_SHORT).show();
                }
            }
        });
        showTutorial();

        /*  //todo: Uncomment for managing facility - Dropdowns brgy, muncity, prov
        int provinceCount = db.getProvincesCount();
        int muncityCount = db.getMuncityCount();
        int brgyCount = db.getBrgyCount();

        if(provinceCount  <=0 || muncityCount <=0 || brgyCount <=0){
            pd = ProgressDialog.show(this, "Loading...", "Downloading dropdown options. \n\nPlease wait...", false, false);
            JSONApi.getInstance(this).getProvinces(Constants.url);
        }*/
    }

    public void setUpHeader() {
        View view = getLayoutInflater().inflate(R.layout.nav_header_main, null);

        profile_image = view.findViewById(R.id.profile_image);
        TextView name = view.findViewById(R.id.user_name);
        TextView contact = view.findViewById(R.id.user_contact);
        TextView version = view.findViewById(R.id.version);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(new ConnectionChecker(MainActivity.this).isConnectedToInternet()){
                    try {
                        URL newurl = new URL(Constants.imageBaseUrl + user.getImage());
                        Bitmap userImage = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        profile_image.setImageBitmap(userImage);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        name.setText(user.fname + " " + user.lname);
        contact.setText(user.contact.replace(" ", ""));
        version.setText("APP VERSION " + BuildConfig.VERSION_NAME);
//        version.setText("DUMMY VERSION " + BuildConfig.VERSION_NAME);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        navigationView.addHeaderView(view);
        headerView = view;
    }

    public void downloadProfiles() {
        try {
            MainActivity.pd = ProgressDialog.show(MainActivity.this, "Loading", "Please wait...", false, false);
            JSONArray arrayBrgy = new JSONArray(MainActivity.user.barangay);
            JSONObject assignedBrgy = arrayBrgy.getJSONObject(0);

            String barangayId = assignedBrgy.getString("barangay_id");
            hf.brgyName = assignedBrgy.getString("description");
            String url = Constants.url + "r=countProfile" + "&brgy=" + barangayId;
            JSONApi.getInstance(MainActivity.this).getCount(url, barangayId, 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fm.getBackStackEntryCount() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit app?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            } else {
                fm.popBackStackImmediate();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.add_head, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        try {
            vctf.removeRegisteredListener();
        } catch (Exception e) {
        }

        if (id != R.id.nav_logout) fabMenu.setVisibility(View.GONE);

        ft = fm.beginTransaction();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (id == R.id.nav_home) {
            // Handle the camera action
            fabMenu.setVisibility(View.VISIBLE);
            hf = new HomeFragment();
            ft.replace(R.id.fragment_container, hf).commit();
        }/* else if (id == R.id.nav_services) { //todo: Uncomment for avail services
            ft.replace(R.id.fragment_container, aspf).commit();
        } */
        else if (id == R.id.nav_manage_population) {
            ft.replace(R.id.fragment_container, vpf).commit();
        } /*else if (id == R.id.nav_services_status) { //todo: uncomment for 3 must services status
            ssf = new ServicesStatusFragment();
            ft.replace(R.id.fragment_container, ssf).commit();
            Toast.makeText(MainActivity.this, "This feature is under development", Toast.LENGTH_SHORT).show();
        }*/
//        else if (id == R.id.nav_services_report) {
//
//        } else if (id == R.id.nav_case_referred) {
//
//        } else if (id == R.id.nav_change_pass) {
//            ft.replace(R.id.fragment_container, cpf).commit();
        else if (id == R.id.nav_check_update) {
            pd = ProgressDialog.show(this, "Loading", "Please wait...", false, false);
            JSONApi.getInstance(this).compareVersion(Constants.url + "r=version");
        } /*else if (id == R.id.nav_chat) { //todo: uncomment for online chat
            vctf = new ViewChatThreadFragment();
            ft.replace(R.id.fragment_container, vctf).commit();
        } */else if (id == R.id.nav_feedback) {
            ft.replace(R.id.fragment_container, ff).commit();
//        } else if (id == R.id.nav_cross_match) {
//            Toast.makeText(this, "This feature is under development process", Toast.LENGTH_SHORT).show();
//        } else if (id == R.id.nav_pending_dengvaxia) {
//            ft.replace(R.id.fragment_container, pdf).commit();
        }

        /*   else if(id==R.id.nav_manage_facility){ *//**Inserted by Romaine, 04/2022*//*  //todo: Uncomment for managing facility
            ft.replace(R.id.fragment_container, vff).commit();
        }*/

        /* else if(id==R.id.nav_manage_specialist){ *//**Inserted by Romaine, 04/2022*//* //todo: Uncomment for managing specialist
            ft.replace(R.id.fragment_container, vsf).commit();
        }*/
        else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            int uploadableCount = db.getProfileUploadableCount();
            /*int uploadableSpecialist = db.getSpecialistUploadableCount(); //todo: Uncomment for managing facility and specialist
            int uploadableFacilities = db.getFacilityUploadableCount();*/

            if ((uploadableCount/* + uploadableSpecialist + uploadableFacilities*/) <= 0) {
                builder.setMessage("Logging out will delete all profile." + /*/specialist/facility data.*/ " Are you sure you want to proceed?");
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteProfiles();
                        db.deleteProfileMedications();
                        db.deleteUser();
                      //  db.deleteServiceStatus(); todo: Uncomment for 3 must services status

                        //updates
                       /* //todo: Uncomment for managing facility and specialist
                        db.deleteSpecialist();
                        db.deleteAffiliatedFacility();

                        db.deleteFacilities();
                        db.deleteFacilityServices();

                        db.deleteProvinces();
                        db.deleteMunCity();
                        db.deleteBrgy();*/

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                });
                builder.setNegativeButton("Cancel", null);
            } else {
                builder.setMessage("Please upload data before logging out.\n\nProfile(s): " + uploadableCount
                        /*+ "\nHealth Facility(s): " + uploadableFacilities + "\nHealth Specialist(s): " + uploadableSpecialist*/); //todo: Uncomment for managing facility and specialist

                builder.setPositiveButton("Ok", null);
            }
            builder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 100:
                    profile_image.setImageURI(data.getData());
            }
        }
    }

    public void showTutorial() {
        queue.add(makeSpotlightView(fabMenu.getMenuIconView(),
                "Whoops!\nRead Me!",
                "Im a floating menu button, click me to show available actions for this Page",
                "FabMenu"));
        queue.add(makeSpotlightView(toolbar.getChildAt(1),
                "Me! Me! Me!",
                "Im Navigation Drawer, a toggle that shows/hides your main navigation menu for this app",
                "NavDrawer"));
        startSequence();

        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (fabMenu.isOpened()) {
                    queue.clear();
                    queue.add(makeSpotlightView(fabDownload,
                            "Hello!",
                            "Im responsible for downloading profiles. Sadly, i require Internet Connection." +
                                    " Im Download Button btw, YOROSHIKU ONEGAI!",
                            "FabDownload"));

                    queue.add(makeSpotlightView(fabUpload,
                            "Sumimasen",
                            "Im Upload Button, I save Added/Updated profiles from your phone to the web server. I also require Internet Connection!",
                            "FabUpload"));

                    startSequence();
                }
            }
        });
    }

    public void showNavTutorial() {
        queue.clear();
        queue.add(makeSpotlightView(headerView.findViewById(R.id.user_info),
                "You \nEquals Me",
                "Im User Info, as you can see i have your name and other information. So basically, im you.",
                "NavUserInfo"));
        startSequence();
    }

//    public void setDetailsToDengvaxia(DengvaxiaDetails details){
//        ManagePopulationFragment mpf = vpf.getMPF();
//        mpf.setDengvaxiaDetails(details);
//    }

    public static SpotlightView.Builder makeSpotlightView(View view, String header, String body, String id) {
        return new SpotlightView.Builder(mainActivity)
                //.introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(25)
                .headingTvText(header)
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(15)
                .subHeadingTvText(body)
                .maskColor(Color.parseColor("#dc000000"))
                .target(view)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .setListener(new SpotlightListener() {
                    @Override
                    public void onUserClicked(String s) {
                        playNext();
                    }
                })
                .usageId(id);
//                .show();
    }

    public static void startSequence(){
        if(queue.isEmpty()) Log.d("MainActivity", "EMPTY SEQUENCE");
        else queue.poll().show();
    }

    public static void playNext(){
        SpotlightView.Builder next = queue.poll();

        if(next != null) next.show().setReady(true);
        else Log.d("MainActivity", "END OF QUEUE");
    }
}