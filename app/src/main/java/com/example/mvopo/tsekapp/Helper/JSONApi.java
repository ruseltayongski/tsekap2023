package com.example.mvopo.tsekapp.Helper;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.example.mvopo.tsekapp.BuildConfig;
import com.example.mvopo.tsekapp.Fragments.HomeFragment;
import com.example.mvopo.tsekapp.Fragments.PendingDengvaxiaFragment;
import com.example.mvopo.tsekapp.LoginActivity;
import com.example.mvopo.tsekapp.MainActivity;

import com.example.mvopo.tsekapp.Model.Constants;
import com.example.mvopo.tsekapp.Model.DengvaxiaPatient;
import com.example.mvopo.tsekapp.Model.FamilyProfile;
import com.example.mvopo.tsekapp.Model.ProfileMedication;
import com.example.mvopo.tsekapp.Model.ServiceAvailed;
import com.example.mvopo.tsekapp.Model.User;
import com.example.mvopo.tsekapp.R;

/* //todo: Uncomment for managing facility and specialist
import com.example.mvopo.tsekapp.Fragments.ViewFacilitiesFragment;
import com.example.mvopo.tsekapp.Fragments.ViewSpecialistFragment;
import com.example.mvopo.tsekapp.Model.AffiliatedFacilitiesModel;
import com.example.mvopo.tsekapp.Model.BarangayModel;
import com.example.mvopo.tsekapp.Model.FacilityModel;
import com.example.mvopo.tsekapp.Model.FacilityService;
import com.example.mvopo.tsekapp.Model.MuncityModel;
import com.example.mvopo.tsekapp.Model.ProvinceModel;
import com.example.mvopo.tsekapp.Model.SpecialistModel;*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mvopo on 10/30/2017.
 */

public class JSONApi {

    private static JSONApi parser;
    static Context context;
    static DBHelper db;

    RequestQueue mRequestQueue;
    private ImageLoader imageLoader;
    String TAG = "JSONApi";

    public JSONApi(Context context) {
        this.context = context;
        this.mRequestQueue = getRequestQueue();

        imageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized JSONApi getInstance(Context context) {
        JSONApi.context = context;
        if (parser == null) {
            parser = new JSONApi(context);
            db = new DBHelper(context);
        }
        return parser;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public void login(final String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");

                            if (status.equalsIgnoreCase("success")) {
                                JSONObject data = response.getJSONObject("data");

                                String id = data.getString("id");
                                String fname = data.getString("fname");
                                String mname = data.getString("mname");
                                String lname = data.getString("lname");
                                String muncity = data.getString("muncity");
                                String contact = data.getString("contact");
                                String province = data.getString("province");
//                              String image = data.getString("hrh_photo");
                                String image = "";
                                String userBrgy = response.getJSONArray("userBrgy").toString();
                                String target = response.getString("target");
                                User user = new User(id, fname, mname, lname, muncity, contact, userBrgy, target, image, province);

                                db.addUser(user);

//                                Intent intent = new Intent(context, MainActivity.class);
//                                intent.putExtra("user", user);
//                                context.startActivity(intent);
//                                ((Activity) context).finish();

                                /* String urlLocation = Constants.url;

                               boolean prov = getProvinces(urlLocation.replace("?", "/getProvinces"));
                                boolean muncity1 = getMuncity(urlLocation.replace("?", "/getMuncities"));
                                boolean brgy = getBrgy(urlLocation.replace("?", "/getBarangays"));

                                if(prov && muncity1 && brgy)*/
                                    ((LoginActivity) context).showPinDialog(false, user);
                            } else {
                                Toast.makeText(context, "Invalid credentials.", Toast.LENGTH_SHORT).show();
                            }

                            LoginActivity.pd.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("LOGIN", error.getMessage());
                LoginActivity.pd.dismiss();
                Log.e("url", url);
                Toast.makeText(context, "Login failed! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    public void getCount(String url, final String brgyId, final int brgyCount) {
        Log.e(TAG,"getCount(" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final int currentCount = db.getProfilesCount(brgyId);
                            final int totalCount = Integer.parseInt(response.getString("count"));

                            if (currentCount >= totalCount) {
                                JSONArray arrayBrgy = new JSONArray(MainActivity.user.barangay);
                                if (Integer.parseInt(arrayBrgy.length() + "") > Integer.parseInt((brgyCount + 1) + "")) {
                                    JSONObject assignedBrgy = arrayBrgy.getJSONObject(brgyCount + 1);
                                    String barangayId = assignedBrgy.getString("barangay_id");
                                    MainActivity.hf.brgyName = assignedBrgy.getString("description");
                                    String url = Constants.url + "r=countProfile" + "&brgy=" + barangayId;
                                    getCount(url, barangayId, brgyCount + 1);
                                } else {
                                    Toast.makeText(context, "Nothing to download", Toast.LENGTH_SHORT).show();
                                    MainActivity.pd.dismiss();
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage((totalCount - currentCount) + " Profiles downloadable for " + MainActivity.hf.brgyName + ", tap PROCEED to start download.");
                                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MainActivity.pd.setTitle("Downloading " + currentCount + "/" + totalCount);
                                        String url = Constants.url + "r=profile" + "&brgy=" + brgyId + "&offset=" + currentCount + "&user_id=" + MainActivity.user.id;
                                        getProfile(url, totalCount, currentCount, brgyCount, brgyId);
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MainActivity.hf = new HomeFragment();
                                        MainActivity.ft = MainActivity.fm.beginTransaction();
                                        MainActivity.ft.replace(R.id.fragment_container, MainActivity.hf).commit();
                                        MainActivity.pd.dismiss();
                                    }
                                });
                                builder.show();

                            }
                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETCOUNT", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }

    public void uploadProfile(final String url, final JSONObject request, final int totalCount, final int currentCount) {
        Log.e(TAG, url);
        Log.e(TAG, "UPLOAD PROFILE: "+request.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String uniqueId = request.getJSONObject("data").getString("unique_id");
                            Log.e(TAG, uniqueId);
                            db.updateProfileById(uniqueId);
                            int count = db.getProfileUploadableCount();
                            // int serviceCount = db.getServicesCount(); //todo: uncomment for 3 must services status

                            if (count > 0) {
                                MainActivity.pd.setTitle("Uploading " + currentCount + "/" + (totalCount/* + serviceCount*/)); //todo: uncomment for 3 must services status
                                uploadProfile(url, Constants.getProfileJson(), totalCount, currentCount + 1);
                            } else {
                               /* if (serviceCount > 0) { //todo: uncomment for 3 must services status
                                    ServiceAvailed serviceAvailed = db.getServiceForUpload();
                                    uploadServices(Constants.url.replace("?", "/syncservices"), serviceAvailed, currentCount, totalCount + serviceCount);
                                } else {*/
                                    Toast.makeText(context, "Upload completed", Toast.LENGTH_SHORT).show();
//                                    compareVersion(Constants.url + "r=version");
                                    MainActivity.pd.dismiss();

                                    int feedbackCount = MainActivity.db.getFeedbacksCount();
                                    if (feedbackCount > 0) showFeedbackUploadDialog(feedbackCount);
                                //}
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("UPLOADPROFILE", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to connect to server.", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, String.valueOf(url), Toast.LENGTH_LONG).show();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjectRequest);
    }

    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    public void getProfile(final String url, final int totalCount, final int offset, final int brgyCount, final String brgyId) {
        Log.e(TAG,"getProfile(" + url);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject object) {

                        int currentCount = db.getProfilesCount(brgyId);
                        MainActivity.pd.setTitle("Downloading " + currentCount + "/" + totalCount);

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    JSONArray array = object.getJSONArray("data");
                                    int currentCount = db.getProfilesCount(brgyId);

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject response = array.getJSONObject(i);

                                        String id = response.getString("id");
                                        String unique_id = response.getString("unique_id");
                                        String familyID = response.getString("familyID");
                                        String phicID = response.getString("phicID");
                                        String nhts = response.getString("nhts");
                                        String four_ps = response.getString("four_ps");
                                        String ip = response.getString("ip");

                                        String head = response.getString("head");
                                        String relation = response.getString("relation");
                                        String member_others = response.getString("member_others");
                                        String fname = response.getString("fname");
                                        String mname = response.getString("mname");
                                        String lname = response.getString("lname");
                                        String suffix = response.getString("suffix");
                                        String dob = response.getString("dob");
                                        String sex = response.getString("sex");
                                        String barangay_id = response.getString("barangay_id");
                                        String muncity_id = response.getString("muncity_id");
                                        String province_id = response.getString("province_id");
                                        String income = response.getString("income");
                                        String unmet = response.getString("unmet");
                                        String water = response.getString("water");
                                        String toilet = response.getString("toilet");
                                        String education = response.getString("education");
                                        String balik_probinsya =  response.getString("balik_probinsya");

                                        String pwd = response.getString("pwd");
                                        String pregnant = response.getString("pregnant");

                                        String birth_place = response.getString("birth_place");
                                        String civil_status = response.getString("civil_status");
                                        String religion = response.getString("religion");
                                        String other_religion = response.getString("other_religion");
                                        String contact = response.getString("contact");
                                        String height = response.getString("height");
                                        String weight = response.getString("weight");
                                        String cancer = response.getString("cancer");
                                        String cancer_type = response.getString("cancer_type");
                                      /*String diabetic = response.getString("diabetic");
                                        String hypertension = response.getString("hypertension");
                                        String mental_med = response.getString("mental_med");
                                        String tbdots_med = response.getString("tbdots_med");
                                        String cvd_med = response.getString("cvd_med");*/
                                        String covid_status = response.getString("covid_status");
                                        String menarche = response.getString("menarche");
                                        String menarche_age = response.getString("menarche_age");
                                        String newborn_screen = response.getString("newborn_screen");
                                        String newborn_text = response.getString("newborn_text");
                                        String deceased = response.getString("deceased");
                                        String deceased_date = response.getString("deceased_date");
                                        String immu_stat = response.getString("immu_stat");
                                        String nutri_stat = response.getString("nutri_stat");
                                        String pwd_desc = response.getString("pwd_desc");
                                        String sexually_active = response.getString("sexually_active");

                                        db.addProfile(new FamilyProfile(id, unique_id, familyID, phicID, nhts, four_ps, ip, head, relation, member_others, fname, lname, mname, suffix, dob, sex,
                                                barangay_id, muncity_id, province_id, income, unmet, water, toilet, education, balik_probinsya, "0", pwd,
                                                pregnant, birth_place, civil_status, religion, other_religion, contact, height, weight, cancer, cancer_type,
                                                covid_status, menarche, menarche_age, newborn_screen, newborn_text, deceased, deceased_date,
                                                immu_stat, nutri_stat, pwd_desc, sexually_active));

                                        //Saving list of medication

                                        JSONArray medicationArray = response.getJSONArray("medication");
                                        for(int x = 0; x < medicationArray.length(); x++) {
                                            JSONObject affiliated = medicationArray.getJSONObject(x);

                                            String type = affiliated.getString("type");
                                            String status = affiliated.getString("status");
                                            String remarks = affiliated.getString("remarks");

                                            db.addProfileMedication(new ProfileMedication("", unique_id, type,status, remarks,"0"));
                                        }


                                        if (i == array.length() - 1) {
                                            currentCount = db.getProfilesCount(barangay_id);
                                            if (currentCount < totalCount) {
                                                String newUrl = url.replace("&offset=" + offset, "&offset=" + currentCount);
                                                getProfile(newUrl, totalCount, currentCount, brgyCount, brgyId);
                                            } else {
                                                JSONArray arrayBrgy = new JSONArray(MainActivity.user.barangay);

                                                if (Integer.parseInt(arrayBrgy.length() + "") > Integer.parseInt(brgyCount + 1 + "")) {
                                                    JSONObject assignedBrgy = arrayBrgy.getJSONObject(brgyCount + 1);

                                                    String barangayId = assignedBrgy.getString("barangay_id");
                                                    MainActivity.hf.brgyName = assignedBrgy.getString("description");

                                                    String url = Constants.url + "r=countProfile" + "&brgy=" + barangayId;
                                                    JSONApi.getInstance(context).getCount(url, barangayId, brgyCount + 1);
                                                } else {
                                                    ((Activity) context).runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            MainActivity.hf = new HomeFragment();
                                                            MainActivity.ft = MainActivity.fm.beginTransaction();
                                                            MainActivity.ft.replace(R.id.fragment_container, MainActivity.hf).commit();
                                                            Toast.makeText(context, "Download finished.", Toast.LENGTH_SHORT).show();
                                                            MainActivity.pd.dismiss();
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }).start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETPROFILE", error.getMessage());
                Log.e(TAG, error.toString());
                Toast.makeText(context, "Unable to get profile.", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }

    public void uploadServices(final String url, final ServiceAvailed serviceAvailed, final int currentCount, final int goalCount) {
        final JSONObject request = serviceAvailed.request;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            MainActivity.pd.setTitle("Uploading " + currentCount + "/" + goalCount);
                            String status = response.getString("status");

                            if (status.equalsIgnoreCase("Success")) {
                                db.deleteService(serviceAvailed.id);
                                if (db.getServicesCount() > 0) {
                                    ServiceAvailed serviceAvailed = db.getServiceForUpload();
                                    uploadServices(Constants.url.replace("?", "/syncservices"), serviceAvailed, currentCount + 1, goalCount);
                                } else {
                                    Toast.makeText(context, "Upload completed", Toast.LENGTH_SHORT).show();

                                    int feedbackCount = MainActivity.db.getFeedbacksCount();
                                    if (feedbackCount > 0) showFeedbackUploadDialog(feedbackCount);
//                                    else compareVersion(Constants.url + "r=version");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("UPLOADSERVICE", error.getMessage());
                Log.e("JSON", url);
                Log.e("JSON", request.toString());
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    public void compareVersion(final String url) {
        Log.e(TAG, url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String version = response.getString("version");
                            String updateInfo = response.getString("description");
                            String versionName = BuildConfig.VERSION_NAME;

                            updateInfo = updateInfo.replace("\\n", "\n");

                            if (!version.equalsIgnoreCase(versionName)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Notice!");
                                builder.setMessage("PHA Check-App v" + version + " is now available, please update your app." +
                                        "\nUPDATES:" + updateInfo + "\n\nNote: Updating will close the application to apply changes." +
                                        "\n\nNote: Download and install through ' http://222.127.126.34/tsekap/vii '");
                              /*  builder.setPositiveButton("Download", new DialogInterface.OnClickListener() { //todo: fix download and install
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MainActivity.pd = ProgressDialog.show(context, "Downloading", "Please wait...", false, false);
                                        downloadAndInstallApk();
                                    }
                                });*/
                                builder.setNegativeButton("Okay", null); //replaced from Later
                                builder.show();
                            } else {
                                Toast.makeText(context, "This is the latest version.", Toast.LENGTH_SHORT).show();
                            }

                            MainActivity.pd.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETVERSION", error.getMessage());
                if (error instanceof NoConnectionError)
                    Toast.makeText(context, "No Internet Connection.", Toast.LENGTH_SHORT).show();
                else compareVersion(url);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }

    public void getDengvaxiaPending(String url) {
        Log.e(TAG, url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<DengvaxiaPatient> patients = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);

                                String id = object.getString("id");
                                String name = object.getString("full_name");
                                String address = object.getString("address");
                                String dob = object.getString("dob");
                                String remarks = object.getString("remarks");
                                String status = object.getString("status");

                                patients.add(new DengvaxiaPatient(id, name, address, remarks, dob, status));

                                if (i == response.length() - 1) {
                                    PendingDengvaxiaFragment.flag = false;
                                    PendingDengvaxiaFragment.patients.addAll(patients);
                                    PendingDengvaxiaFragment.adapter.notifyDataSetChanged();
                                }
                            }

                            if (response.length() == 0) {
                                PendingDengvaxiaFragment.adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETDENGVAXIAPENDING", error.getMessage());
            }
        });
        mRequestQueue.add(jsonArrayRequest);
    }

//    public void dengvaxiaRegister(String url, final JSONObject request) {
//        Log.e(TAG, url);
//        Log.e(TAG, request.toString());
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Toast.makeText(context, response.getString("status"), Toast.LENGTH_SHORT).show();
//
//                            String facilityName = request.getString("facility_name");
//                            String listNumber = request.getString("list_number");
//                            String doseScreen = request.getString("dose_screened");
//                            String doseDate = request.getString("dose_date_given");
//                            String doseLot = request.getString("dose_lot_no");
//                            String doseBatch = request.getString("dose_batch_no");
//                            String doseExpiry = request.getString("dose_expiration");
//                            String doseAefi = request.getString("dose_AEFI");
//                            String remarks = request.getString("remarks");
//                            String status = "Pending";
//
//                            DengvaxiaDetails details = new DengvaxiaDetails(facilityName, listNumber, doseScreen,
//                                    doseDate, doseLot, doseBatch, doseExpiry, doseAefi, remarks, status);
//
//                            ((MainActivity) context).setDetailsToDengvaxia(details);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("DENGVAXIAREGISTRATION", error.getMessage());
//            }
//        });
//        mRequestQueue.add(jsonObjectRequest);
//    }

    public void registerToDengvaxia(final String url, final JSONObject request) {
        Log.e(TAG, url);
        Log.e(TAG, request.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, response.getString("status"), Toast.LENGTH_SHORT).show();
                            MainActivity.fm.popBackStackImmediate();
                            MainActivity.pd.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("DENGVAXIAREGISTRATION", error.toString());
                if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show();
                    MainActivity.pd.dismiss();
                } else registerToDengvaxia(url, request);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }

//    public void getDengvaxiaDetails(final String url) {
//        Log.e(TAG, url);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, "",
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            if(response.length() > 0) {
//                                JSONObject object = response.getJSONObject(0);
//
//                                String facilityName = object.getString("facility_name");
//                                String listNumber = object.getString("list_number");
//                                String doseScreen = object.getString("dose_screened");
//                                String doseDate = object.getString("dose_date_given");
//                                String doseLot = object.getString("dose_lot_no");
//                                String doseBatch = object.getString("dose_batch_no");
//                                String doseExpiry = object.getString("dose_expiration");
//                                String doseAefi = object.getString("dose_AEFI");
//                                String remarks = object.getString("remarks");
//                                String status = object.getString("status");
//
//                                DengvaxiaDetails details = new DengvaxiaDetails(facilityName, listNumber, doseScreen,
//                                        doseDate, doseLot, doseBatch, doseExpiry, doseAefi, remarks, status);
//
//                                ((MainActivity) context).setDetailsToDengvaxia(details);
//                            }else MainActivity.pd.dismiss();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("GETDENGVAXIAPENDING", error.getMessage());
//                getDengvaxiaDetails(url);
//            }
//        });
//        mRequestQueue.add(jsonArrayRequest);
//    }

    public void downloadAndInstallApk() {
        try {
            final String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/PHA Check-App.apk";
            final Uri uri = Uri.parse("file://" + destination);

            File file = new File(destination);
            if (file.exists()) file.delete();

            String url = Constants.apkUrl;

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Download new version of the App");
            request.setTitle("PHA Check-App");

            request.setDestinationUri(uri);

            final DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                        MainActivity.pd.dismiss();

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setDataAndType(Uri.fromFile(new File(destination)),
                                "application/vnd.android.package-archive");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            };

            IntentFilter filter = new IntentFilter();
            filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

            context.registerReceiver(onComplete, filter);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            MainActivity.pd.dismiss();
        }
    }

    /*public void getServicesStatus(final String url, final int totalCount, final int offset, final int brgyCount, final String brgyId) { todo: uncomment for 3 must services status
        Log.e(TAG, url);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject object) {

                        int currentCount = db.getServiceStatusCount(brgyId);
                        MainActivity.pd.setTitle("Downloading " + currentCount + "/" + totalCount);

                        new Thread(new Runnable() {
                            public void run() {
                                try {

                                    JSONArray array = object.getJSONArray("data");
                                    int currentCount;

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject response = array.getJSONObject(i);

                                        String name = response.getString("fullname");
                                        String group1 = response.getString("group1");
                                        String group2 = response.getString("group2");
                                        String group3 = response.getString("group3");

                                        db.addServiceStatus(new ServicesStatus(name, group1, group2, group3, brgyId));

                                        if (i == array.length() - 1) {
                                            currentCount = db.getServiceStatusCount(brgyId);
                                            if (currentCount < totalCount) {
                                                String newUrl = url.replace("&offset=" + offset, "&offset=" + currentCount);
                                                getServicesStatus(newUrl, totalCount, currentCount, brgyCount, brgyId);
                                            } else {
                                                JSONArray arrayBrgy = new JSONArray(MainActivity.user.barangay);

                                                if (Integer.parseInt(arrayBrgy.length() + "") > Integer.parseInt(brgyCount + 1 + "")) {
                                                    JSONObject assignedBrgy = arrayBrgy.getJSONObject(brgyCount + 1);

                                                    String barangayId = assignedBrgy.getString("barangay_id");

                                                    String url = Constants.url + "r=countmustservices" + "&brgy=" + barangayId;
                                                    JSONApi.getInstance(context).getServiceStatusCount(url, barangayId, brgyCount + 1);
                                                } else { todo: uncomment for 3 must services status
                                                    ((Activity) context).runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            MainActivity.ssf = new ServicesStatusFragment();
                                                            MainActivity.ft = MainActivity.fm.beginTransaction();
                                                            MainActivity.ft.replace(R.id.fragment_container, MainActivity.ssf).commit();
                                                            Toast.makeText(context, "Download finished.", Toast.LENGTH_SHORT).show();
                                                            MainActivity.pd.dismiss();
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }).start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETSERVICESSTATUSCOUNT", error.getMessage());
                Log.e(TAG, error.toString());
                Toast.makeText(context, "Unable to get profile.", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }*/

   /* public void getServiceStatusCount(String url, final String brgyId, final int brgyCount) { todo: uncomment for 3 must services status
        Log.e(TAG, url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final int currentCount = db.getServiceStatusCount(brgyId);
                            final int totalCount = Integer.parseInt(response.getString("count"));

                            if (currentCount >= totalCount) {
                                JSONArray arrayBrgy = new JSONArray(MainActivity.user.barangay);
                                if (Integer.parseInt(arrayBrgy.length() + "") > Integer.parseInt((brgyCount + 1) + "")) {
                                    JSONObject assignedBrgy = arrayBrgy.getJSONObject(brgyCount + 1);
                                    String barangayId = assignedBrgy.getString("barangay_id");
                                    String url = Constants.url + "r=countmustservices" + "&brgy=" + barangayId;
                                    getServiceStatusCount(url, barangayId, brgyCount + 1);
                                } else {
                                    Toast.makeText(context, "Nothing to download", Toast.LENGTH_SHORT).show();
                                    MainActivity.pd.dismiss();
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage((totalCount - currentCount) + " Services Status downloadable for " + Constants.getBrgyName(brgyId) +
                                        ", tap PROCEED to start download.");
                                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MainActivity.pd.setTitle("Downloading " + currentCount + "/" + totalCount);
                                        String url = Constants.url + "r=mustservices" + "&brgy=" + brgyId + "&offset=" + currentCount;
                                        getServicesStatus(url, totalCount, currentCount, brgyCount, brgyId);
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MainActivity.pd.dismiss();
                                    }
                                });
                                builder.show();

                            }
                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETCOUNTSERVICESSTATUS", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }*/

    public void showFeedbackUploadDialog(int feedbackCount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(feedbackCount + " Feedbacks uploadable. Proceed upload?");
        builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (new ConnectionChecker(context).isConnectedToInternet()) {
                    String body = MainActivity.db.getFeedbacksForUpload();
                    Log.e("QWEQWE", body);
                    BackgroundMail.newBuilder(context)
                            .withUsername("phacheckapp@gmail.com")
                            .withPassword("phacheckapp123")
                            .withMailto("hontoudesu123@gmail.com")
                            .withSubject("PHA Check-App Feedback")
                            .withBody(getSenderInfo() + body)
                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                @Override
                                public void onSuccess() {
                                    MainActivity.db.deleteFeedback("");
//                                    compareVersion(Constants.url + "r=version");
                                }
                            })
                            .send();
                } else
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                compareVersion(Constants.url + "r=version");
            }
        });
        builder.show();
    }

    public String getSenderInfo() {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber="";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return TO-DO;
            mPhoneNumber = tMgr.getLine1Number();
        }


        String senderInfo = "Name: " + MainActivity.user.fname + " " + MainActivity.user.lname + "\n" +
                "Registered Number: " + MainActivity.user.contact + "\n" +
                "Device Number:" + mPhoneNumber + "\n\n";

        return senderInfo;
    }
    /* //todo: Uncomment for managing facility and specialist
    public void getSpecialist(final String url, final int totalCount, final int offset) {
        Log.e(TAG,"getSpecialist(" + url);

        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray specialistArray) {
                        MainActivity.pd.setTitle("Downloading..." );
                        MainActivity.db.deleteSpecialist();
                        MainActivity.db.deleteAffiliatedFacility();
                        new Thread(new Runnable() {
                            public void run() {
                                try {

                                    for (int i = 0; i < specialistArray.length(); i++) {
                                        JSONObject response = specialistArray.getJSONObject(i);

                                        String username = response.getString("username");
                                        String fname = response.getString("fname");
                                        String mname = response.getString("mname");
                                        String lname = response.getString("lname");

                                        db.addSpecialist(new SpecialistModel("",username, fname, mname, lname, "0" ));

                                        JSONArray affiliatedArray = response.getJSONArray("affiliated");

                                        for(int x = 0; x < affiliatedArray.length(); x++) {
                                            JSONObject affiliated = affiliatedArray.getJSONObject(x);

                                            String facility_code = affiliated.getString("facility_code");
                                            String specialization = affiliated.getString("specialization");
                                            String contact = affiliated.getString("contact");
                                            String email = affiliated.getString("email");
                                            String schedule = affiliated.getString("schedule");
                                            String fee = affiliated.getString("fee");

                                             db.addAffiliatedFacility(new AffiliatedFacilitiesModel("", username, facility_code, specialization, contact, email, schedule, fee, "0"));
                                        }

                                    }

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MainActivity.vsf = new ViewSpecialistFragment();
                                            MainActivity.ft = MainActivity.fm.beginTransaction();
                                            MainActivity.ft.replace(R.id.fragment_container, MainActivity.vsf).commit();
                                            Toast.makeText(context, "Download finished.", Toast.LENGTH_SHORT).show();
                                            MainActivity.pd.dismiss();
                                        }
                                    });

                                } catch (JSONException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }).start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETSPECIALIST", error.getMessage());
                Log.e(TAG, error.toString());
                Toast.makeText(context, "Unable to get Specialists.", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }

    public void uploadSpecialists(final String url, final JSONObject request, final int totalCount, final int currentCount) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            if(response.getString("status").equalsIgnoreCase("success")){
                                MainActivity.db.updateSpecialistsStatus();
                                Toast.makeText(context, "Upload Completed", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Failed to Upload, please contact system admin", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                                e.printStackTrace();
                        }

                        MainActivity.pd.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("UPLOADSPECIALIST", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to connect to server.", Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjectRequest);
    }

    public void getFacilities(final String url, final int totalCount, final int offset) {
        Log.e(TAG,"getFacilities(" + url);

        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray facilityArray) {

                        MainActivity.pd.setTitle("Downloading...");
                        MainActivity.db.deleteFacilities();
                        MainActivity.db.deleteFacilityServices();
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    for (int i = 0; i < facilityArray.length(); i++) {
                                        JSONObject response = facilityArray.getJSONObject(i);

                                        String facility_code = response.getString("facility_code");
                                        String facility_name = response.getString("facility_name");
                                        String facility_abbr = response.getString("facility_abbr");
                                        String prov_id = response.getString("prov_id");
                                        String muncity_id = response.getString("muncity_id");
                                        String brgy_id = response.getString("brgy_id");
                                        String address = response.getString("address");
                                        String contact = response.getString("contact");
                                        String email = response.getString("email");
                                        String chief_hospital = response.getString("chief_hospital");
                                        String service_capability = response.getString("service_capability");
                                        String license_status = response.getString("license_status");
                                        String ownership = response.getString("ownership");
                                        String facility_status = response.getString("facility_status");
                                        String phic_status = response.getString("phic_status");
                                        String referral_status = response.getString("referral_status");
                                        String transport = response.getString("transport");
                                        String latitude = response.getString("latitude");
                                        String longitude = response.getString("longitude");
                                        String sched_day_from = response.getString("sched_day_from");
                                        String sched_day_to = response.getString("sched_day_to");
                                        String sched_time_from = response.getString("sched_time_from");
                                        String sched_time_to = response.getString("sched_time_to");
                                        String sched_notes = response.getString("sched_notes");

                                        db.addFacility(new FacilityModel("",facility_code,facility_name, facility_abbr, prov_id, muncity_id, brgy_id,
                                                address, contact, email, chief_hospital, service_capability, license_status, ownership, facility_status,
                                                referral_status, phic_status, transport, latitude, longitude, sched_day_from, sched_day_to, sched_time_from,
                                                sched_time_to, sched_notes, "0"));


                                        JSONArray servicesArray = response.getJSONArray("services_cost");
                                        for(int x = 0; x < servicesArray.length(); x++) {
                                            JSONObject services = servicesArray.getJSONObject(x);

                                            String service_type = services.getString("service_type");
                                            String service = services.getString("service");
                                            String cost = services.getString("cost");

                                            db.addFacilityService(new FacilityService("", facility_code, service_type, service, cost, "0"));
                                        }
                                    }

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MainActivity.vff = new ViewFacilitiesFragment();
                                            MainActivity.ft = MainActivity.fm.beginTransaction();
                                            MainActivity.ft.replace(R.id.fragment_container, MainActivity.vff).commit();

                                            Toast.makeText(context, "Download finished.", Toast.LENGTH_SHORT).show();
                                            MainActivity.pd.dismiss();
                                        }
                                    });
                                } catch (JSONException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }).start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETFACILITY", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to get facilities.", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }

    public void uploadFacilities(final String url, final JSONObject request, final int totalCount, final int currentCount) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            if(response.getString("status").equalsIgnoreCase("success")){
                                MainActivity.db.updateFacilitiesStatus();
                                Toast.makeText(context, "Upload Completed", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Failed to Upload, please contact system admin", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MainActivity.pd.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("UPLOADFACILITIES", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to connect to server.", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjectRequest);
    }

    public void getProvinces(final String url) {
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url.replace("?", "/getProvinces"), "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray array) {
                        MainActivity.pd.setTitle("Downloading provinces...");
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    MainActivity.db.deleteProvinces();
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject response = array.getJSONObject(i);

                                        String id = response.getString("id").trim();
                                        String name = response.getString("name").trim();

                                        db.addProvince(new ProvinceModel(id, name));
                                    }
                                    getMuncity(url);
                                } catch (JSONException e) {
                                    Log.e(TAG, e.getMessage());
                                    MainActivity.pd.dismiss();
                                }

                            }
                        }).start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETPROVINCE", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to get provinces, pls contact system administrator.", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
        Log.e(TAG, "last getProvinces");
    }

    public void getMuncity(final String url) {
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url.replace("?", "/getMuncities"), "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray array) {
                        MainActivity.pd.setTitle("Downloading municipalities...");
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    MainActivity.db.deleteMunCity();
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject response = array.getJSONObject(i);

                                        String id = response.getString("id").trim();
                                        String name = response.getString("name").trim();
                                        String prov_id = response.getString("prov_id").trim();

                                        db.addMuncity(new MuncityModel(id, name, prov_id ));
                                    }
                                    getBrgy(url);
                                } catch (JSONException e) {
                                    Log.e(TAG, e.getMessage());
                                    MainActivity.pd.dismiss();
                                }
                            }
                        }).start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETMUNCITY", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to get municipalities, pls contact system administrator.", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }

    public void getBrgy(final String url) {
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url.replace("?", "/getBarangays"), "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray array) {
                        MainActivity.pd.setTitle("Downloading barangays...");
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    MainActivity.db.deleteBrgy();
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject response = array.getJSONObject(i);

                                        String id = response.getString("id").trim();
                                        String name = response.getString("name").trim();
                                        String prov_id = response.getString("prov_id").trim();
                                        String muncity_id = response.getString("muncity_id").trim();

                                        db.addBrgy(new BarangayModel(id, name, prov_id, muncity_id ));
                                    }
                                    MainActivity.pd.dismiss();
                                } catch (JSONException e) {
                                    Log.e(TAG, e.getMessage());
                                    MainActivity.pd.dismiss();
                                }
                            }
                        }).start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("GETBARANGAY", error.getMessage());
                Log.e(TAG, error.toString());
                MainActivity.pd.dismiss();
                Toast.makeText(context, "Unable to get barangay, pls contact system administrator.", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(jsonObjectRequest);
    }*/

}
