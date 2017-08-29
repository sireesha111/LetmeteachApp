
package varnalabs.letmetutorapp.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import varnalabs.letmetutorapp.R;
import varnalabs.letmetutorapp.activity.StudentActivity;
import varnalabs.letmetutorapp.app.AppConfig;
import varnalabs.letmetutorapp.app.AppController;
import dmax.dialog.SpotsDialog;
import me.drakeet.materialdialog.MaterialDialog;

import static android.app.Activity.RESULT_OK;


public class StudentEditProfileFragment extends Fragment implements StudentActivity.OnBackPressedListener {

    Activity context;
    View view_profile;
    private static final String TAG = StudentEditProfileFragment.class.getSimpleName();


    EditText ed_fullname, ed_dateofbirth, ed_address, ed_landmark, ed_contactno, ed_landlineno, ed_otherno, ed_ifother;
    TextView ed_username, ed_email, teachercode, txt_username;

    EditText ed_feeshourly, ed_feesmonthly;

    RadioButton radioMale, radioFemale;
    RadioGroup radioGender;

    Spinner sp_city, sp_zone, sp_subarea, sp_intrteaching, sp_fromtime, sp_totime, sp_experience, sp_intrmedium, sp_intrboard;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datedialog;

    TextView txtCityid, txtZoneid, txtSubareaid, txtintrteaching, txtMedium, txtBoard;

    private JSONArray city_result;
    ArrayList<String> citylist;
    private RequiredSpinnerAdapter city_adapter;

    private JSONArray zone_result;
    private ArrayList<String> zonelist;
    private RequiredSpinnerAdapter zone_adapter;

    private JSONArray subarea_result;
    private ArrayList<String> subarealist;
    private RequiredSpinnerAdapter subarea_adapter;

    private JSONArray class_result;
    ArrayList<String> classlist;

    private JSONArray teachloc_result;
    ArrayList<String> teachloclist;

    private JSONArray subject_result;
    ArrayList<String> subjectlist;

    private JSONArray quali_result;
    ArrayList<String> qualilist;

    private JSONArray from_result;
    ArrayList<String> fromlist;
    private RequiredSpinnerAdapter from_adapter;

    ArrayList<String> tolist;
    private RequiredSpinnerAdapter to_adapter;

    ImageView dateofbirthimg;

    String MobilePattern = "^[7-9][0-9]{10}$";
    String NamePattern = "^[a-zA-Z]+[\\-'\\s]?[a-zA-Z ]+$";


    String[] spTeachingValue = {
            "TYPE OF TUTOR REQUIRED",
            "Male",
            "Female"
    };

    String[] spExperienceValue = {
            "Select Experience", "1 year(s)", "2 year(s)", "3 year(s)", "4 year(s)", "5 year(s)", "6 year(s)", "7 year(s)", "8 year(s)", "9 year(s)", "10 year(s)", "11 year(s)", "12 year(s)", "13 year(s)", "14 year(s)", "15 year(s)", "16 year(s)", "17 year(s)", "18 year(s)", "19 year(s)", "20 year(s)", "21 year(s)", "22 year(s)", "23 year(s)", "24 year(s)", "25 year(s)", "26 year(s)", "27 year(s)", "28 year(s)", "30 year(s)", "31 year(s)", "32 year(s)", "33 year(s)", "34 year(s)", "35 year(s)", "36 year(s)", "37 year(s)", "38 year(s)", "39 year(s)", "40 year(s)", "41 year(s)", "42 year(s)", "43 year(s)", "44 year(s)", "45 year(s)", "46 year(s)", "47 year(s)", "48 year(s)", "49 year(s)", "50 year(s)"};



    String[] spMediumValue = {
            "Select Medium",
            "English",
            "Hindi"
    };

    String[] spBoardValue = {
            "Select Board",
            "None",
            "CBSE",
            "ICSE",
            "Other"
    };

    FrameLayout frame_zone, frame_subarea;
    List<KeyPairBoolData> listArrayClass, listArrayTeachLoc, listArraySubject, listArrayQuali;

    MultiSpinnerSearch searchMultiSpinnerLimit, searchMultiSpinnerTeachLoc, searchMultiSpinnerSubject, searchMultiSpinnerQuali;

    TextView txtClass, txtTeachLoc, txtSubject, txtQuali;

    ImageView imgPhoto;
    FloatingActionButton btnimgplus;

    Button btnTeacher;

    Typeface font_text;
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    String loginuser;

    TextView txtGender;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((StudentActivity) getActivity()).setOnBackPressedListener(this);
    }

    public void doBack() {
        StudentDashboardFragment fragment = new StudentDashboardFragment();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_profile = inflater.inflate(R.layout.fragment_editstudentsprofile, container, false);
        context = getActivity();
        //Used for Checking Internet Connection
        if (!haveNetworkConnection()) {
            final MaterialDialog mMaterialDialog = new MaterialDialog(context);
            mMaterialDialog.setTitle("No Internet Connection")
                    .setMessage("You are offline please check your internet connection")

                    .setPositiveButton(
                            "OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    context.moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                    context.finish();

                                }
                            }
                    )
                    .show();
        } else {

            font_text = Typeface.createFromAsset(context.getAssets(),
                    "fontRegular.ttf");

            SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.SHARED_STUDENTS_PREF_NAME, Context.MODE_PRIVATE);
            loginuser = sharedPreferences.getString(AppConfig.STUDENTSUSER_SHARED_PREF, "Not Available");

            imgPhoto = (ImageView) view_profile.findViewById(R.id.imgPhoto);
            imgPhoto.setVisibility(View.VISIBLE);

            btnimgplus = (FloatingActionButton) view_profile.findViewById(R.id.btnimgplus);

            btnTeacher = (Button) view_profile.findViewById(R.id.btnTeacher);

            btnTeacher.setTypeface(font_text, Typeface.BOLD);

            ed_fullname = (EditText) view_profile.findViewById(R.id.ed_fullname);
            ed_dateofbirth = (EditText) view_profile.findViewById(R.id.ed_dateofbirth);
            ed_landmark = (EditText) view_profile.findViewById(R.id.ed_landmark);
            ed_ifother = (EditText) view_profile.findViewById(R.id.ed_ifother);
            ed_address = (EditText) view_profile.findViewById(R.id.ed_address);
            ed_contactno = (EditText) view_profile.findViewById(R.id.ed_contactno);
            ed_landlineno = (EditText) view_profile.findViewById(R.id.ed_landlineno);
            ed_otherno = (EditText) view_profile.findViewById(R.id.ed_otherno);

            ed_dateofbirth.setTypeface(font_text);
            ed_landmark.setTypeface(font_text);
            ed_address.setTypeface(font_text);
            ed_contactno.setTypeface(font_text);
            ed_landlineno.setTypeface(font_text);
            ed_otherno.setTypeface(font_text);
            ed_ifother.setTypeface(font_text);

            ed_feeshourly = (EditText) view_profile.findViewById(R.id.ed_feeshourly);
            ed_feesmonthly = (EditText) view_profile.findViewById(R.id.ed_feesmonthly);

            ed_feeshourly.setTypeface(font_text);
            ed_feesmonthly.setTypeface(font_text);

            ed_username = (TextView) view_profile.findViewById(R.id.ed_username);
            ed_email = (TextView) view_profile.findViewById(R.id.ed_email);
            teachercode = (TextView) view_profile.findViewById(R.id.teachercode);
            txt_username = (TextView) view_profile.findViewById(R.id.txt_username);

            ed_username.setTypeface(font_text);
            ed_email.setTypeface(font_text);
            teachercode.setTypeface(font_text);
            txt_username.setTypeface(font_text);

            radioMale = (RadioButton) view_profile.findViewById(R.id.radioMale);
            radioFemale = (RadioButton) view_profile.findViewById(R.id.radioFemale);
            radioGender = (RadioGroup) view_profile.findViewById(R.id.radioGender);

            radioMale.setTypeface(font_text);
            radioFemale.setTypeface(font_text);

            dateofbirthimg = (ImageView) view_profile.findViewById(R.id.dateofbirthimg);

            txtGender = (TextView) view_profile.findViewById(R.id.txtGender);

            txtClass = (TextView) view_profile.findViewById(R.id.txtClass);
            txtTeachLoc = (TextView) view_profile.findViewById(R.id.txtTeachLoc);
            txtSubject = (TextView) view_profile.findViewById(R.id.txtSubject);
            txtQuali = (TextView) view_profile.findViewById(R.id.txtQuali);
            txtMedium = (TextView) view_profile.findViewById(R.id.txtMedium);
            txtBoard = (TextView) view_profile.findViewById(R.id.txtBoard);

            sp_city = (Spinner) view_profile.findViewById(R.id.sp_city);
            sp_zone = (Spinner) view_profile.findViewById(R.id.sp_zone);
            sp_subarea = (Spinner) view_profile.findViewById(R.id.sp_subarea);
            sp_intrteaching = (Spinner) view_profile.findViewById(R.id.sp_intrteaching);
            sp_fromtime = (Spinner) view_profile.findViewById(R.id.sp_fromtime);
            sp_totime = (Spinner) view_profile.findViewById(R.id.sp_totime);
            sp_experience = (Spinner) view_profile.findViewById(R.id.sp_experience);
            sp_intrmedium = (Spinner) view_profile.findViewById(R.id.sp_intrmedium);
            sp_intrboard = (Spinner) view_profile.findViewById(R.id.sp_intrboard);

            txtCityid = (TextView) view_profile.findViewById(R.id.txtCityid);
            txtZoneid = (TextView) view_profile.findViewById(R.id.txtZoneid);
            txtSubareaid = (TextView) view_profile.findViewById(R.id.txtSubareaid);
            txtintrteaching = (TextView) view_profile.findViewById(R.id.txtintrteaching);

            frame_zone = (FrameLayout) view_profile.findViewById(R.id.frame_zone);
            frame_subarea = (FrameLayout) view_profile.findViewById(R.id.frame_subarea);

            citylist = new ArrayList<String>();
            zonelist = new ArrayList<String>();
            subarealist = new ArrayList<String>();

            classlist = new ArrayList<String>();
            teachloclist = new ArrayList<String>();
            subjectlist = new ArrayList<String>();
            qualilist = new ArrayList<String>();

            listArrayClass = new ArrayList<>();
            listArrayTeachLoc = new ArrayList<>();
            listArraySubject = new ArrayList<>();
            listArrayQuali = new ArrayList<>();

            tolist = new ArrayList<String>();
            fromlist = new ArrayList<String>();

            searchMultiSpinnerLimit = (MultiSpinnerSearch) view_profile.findViewById(R.id.searchMultiSpinnerClass);
            searchMultiSpinnerTeachLoc = (MultiSpinnerSearch) view_profile.findViewById(R.id.searchMultiSpinnerTeachLoc);
            searchMultiSpinnerSubject = (MultiSpinnerSearch) view_profile.findViewById(R.id.searchMultiSpinnerSubject);
            searchMultiSpinnerQuali = (MultiSpinnerSearch) view_profile.findViewById(R.id.searchMultiSpinnerQuali);

            /***
             * -1 is no by default selection
             * 0 to length will select corresponding values
             */
            searchMultiSpinnerLimit.setItems(listArrayClass, -1, new SpinnerListener() {

                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {

                    txtClass.setText("");

                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).isSelected()) {
                            Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());

                            txtClass.setText(searchMultiSpinnerLimit.getSelectedItem().toString());

                        }
                    }
                }
            });

            searchMultiSpinnerLimit.setLimit(5, new MultiSpinnerSearch.LimitExceedListener() {
                @Override
                public void onLimitListener(KeyPairBoolData data) {
                    Toast.makeText(getActivity(),
                            "You can only select 5 items", Toast.LENGTH_LONG).show();
                }
            });

            /***
             * -1 is no by default selection
             * 0 to length will select corresponding values
             */
            searchMultiSpinnerTeachLoc.setItems(listArrayTeachLoc, -1, new SpinnerListener() {

                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {

                    txtTeachLoc.setText("");

                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).isSelected()) {
                            Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                            txtTeachLoc.setText(searchMultiSpinnerTeachLoc.getSelectedItem().toString());
                        }
                    }
                }
            });

            searchMultiSpinnerTeachLoc.setLimit(5, new MultiSpinnerSearch.LimitExceedListener() {
                @Override
                public void onLimitListener(KeyPairBoolData data) {
                    Toast.makeText(getActivity(),
                            "You can only select 5 items", Toast.LENGTH_LONG).show();
                }
            });


            /***
             * -1 is no by default selection
             * 0 to length will select corresponding values
             */
            searchMultiSpinnerSubject.setItems(listArraySubject, -1, new SpinnerListener() {

                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {

                    txtSubject.setText("");

                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).isSelected()) {
                            Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());

                            txtSubject.setText(searchMultiSpinnerSubject.getSelectedItem().toString());
                        }
                    }
                }
            });

            searchMultiSpinnerSubject.setLimit(5, new MultiSpinnerSearch.LimitExceedListener() {
                @Override
                public void onLimitListener(KeyPairBoolData data) {
                    Toast.makeText(getActivity(),
                            "You can only select 5 items", Toast.LENGTH_LONG).show();
                }
            });


            /***
             * -1 is no by default selection
             * 0 to length will select corresponding values
             */
            searchMultiSpinnerQuali.setItems(listArrayQuali, -1, new SpinnerListener() {

                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {

                    txtQuali.setText("");

                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).isSelected()) {
                            Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());

                            txtQuali.setText(searchMultiSpinnerQuali.getSelectedItem().toString());
                        }
                    }
                }
            });

            searchMultiSpinnerQuali.setLimit(5, new MultiSpinnerSearch.LimitExceedListener() {
                @Override
                public void onLimitListener(KeyPairBoolData data) {
                    Toast.makeText(getActivity(),
                            "You can only select 5 items", Toast.LENGTH_LONG).show();
                }
            });


            sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    parent.post(new Runnable() {
                        @Override
                        public void run() {
                            sp_city.requestFocusFromTouch();
                        }
                    });

                    if (sp_city.getSelectedItem() == "Select City") {

                        parent.post(new Runnable() {
                            @Override
                            public void run() {
                                sp_city.requestFocusFromTouch();
                            }
                        });

                    } else {
                        txtCityid.setText(getCityID(position));
                        String cityid = txtCityid.getText().toString();

                        getSpZoneData(cityid);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            sp_zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    parent.post(new Runnable() {
                        @Override
                        public void run() {
                            sp_zone.requestFocusFromTouch();
                        }
                    });

                    if (sp_zone.getSelectedItem() == "Select Zone") {

                        parent.post(new Runnable() {
                            @Override
                            public void run() {
                                sp_zone.requestFocusFromTouch();
                            }
                        });

                    } else {
                        txtZoneid.setText(getZoneID(position));
                        String zoneid = txtZoneid.getText().toString();

                        getSpSubareaData(zoneid);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            sp_subarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    parent.post(new Runnable() {
                        @Override
                        public void run() {
                            sp_subarea.requestFocusFromTouch();
                        }
                    });

                    if (sp_subarea.getSelectedItem() == "Select Subarea") {

                        parent.post(new Runnable() {
                            @Override
                            public void run() {
                                sp_subarea.requestFocusFromTouch();
                            }
                        });

                    } else {
                        txtSubareaid.setText(getSubareaID(position));

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



            final List<String> plantsList = new ArrayList<>(Arrays.asList(spTeachingValue));

            final ArrayAdapter<String> teachingaa = new ArrayAdapter<String>(
                    getActivity(), R.layout.spinner_item_position, plantsList) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        // Set the hint text color gray
                        tv.setTextColor(Color.parseColor("#303030"));
                        tv.setTypeface(font_text);
                    } else {
                        tv.setTextColor(Color.parseColor("#303030"));
                        tv.setTypeface(font_text);
                    }
                    return view;
                }
            };
            teachingaa.setDropDownViewResource(R.layout.spinner_item_position);
            sp_intrteaching.setAdapter(teachingaa);

            sp_intrteaching.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    if (sp_intrteaching.getSelectedItem() == "TYPE OF TUTOR REQUIRED") {

                        //Do nothing.
                    } else {
                        String intteaching = sp_intrteaching.getSelectedItem().toString();
                        txtintrteaching.setText(intteaching);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });

            final List<String> expList = new ArrayList<>(Arrays.asList(spExperienceValue));

            final ArrayAdapter<String> expaa = new ArrayAdapter<String>(
                    getActivity(), R.layout.spinner_item_position, expList) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        // Set the hint text color gray
                        tv.setTextColor(Color.parseColor("#303030"));
                        tv.setTypeface(font_text);
                    } else {
                        tv.setTextColor(Color.parseColor("#303030"));
                        tv.setTypeface(font_text);
                    }
                    return view;
                }
            };
            expaa.setDropDownViewResource(R.layout.spinner_item_position);
            sp_experience.setAdapter(expaa);

            sp_experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    if (sp_experience.getSelectedItem() == "Select Experience") {

                        //Do nothing.
                    } else {
                        String intteaching = sp_intrteaching.getSelectedItem().toString();
                        txtintrteaching.setText(intteaching);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });


            final List<String> mediumList = new ArrayList<>(Arrays.asList(spMediumValue));

            final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                    getActivity(), R.layout.spinner_item_position, mediumList) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        // Set the hint text color gray
                        tv.setTextColor(Color.parseColor("#303030"));
                        tv.setTypeface(font_text);
                    } else {
                        tv.setTextColor(Color.parseColor("#303030"));
                        tv.setTypeface(font_text);
                    }
                    return view;
                }
            };
            spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item_position);
            sp_intrmedium.setAdapter(spinnerArrayAdapter2);

            sp_intrmedium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    if (sp_experience.getSelectedItem() == "Select Medium") {

                        //Do nothing.
                    } else {
                        String intmedium = sp_intrmedium.getSelectedItem().toString();
                        txtMedium.setText(intmedium);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });



            final List<String> boardList = new ArrayList<>(Arrays.asList(spBoardValue));

            final ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    getActivity(), R.layout.spinner_item_position, boardList) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        // Set the hint text color gray
                        tv.setTextColor(Color.parseColor("#303030"));
                        tv.setTypeface(font_text);
                    } else {
                        tv.setTextColor(Color.parseColor("#303030"));
                        tv.setTypeface(font_text);
                    }
                    return view;
                }
            };
            spinnerArrayAdapter3.setDropDownViewResource(R.layout.spinner_item_position);
            sp_intrboard.setAdapter(spinnerArrayAdapter3);

            sp_intrboard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    if (sp_intrboard.getSelectedItem() == "Select Board") {

                        //Do nothing.
                    } else {
                        String intboard = sp_intrboard.getSelectedItem().toString();
                        txtBoard.setText(intboard);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });



            setBirthField();
            getSpCityData();
            getSpClassData();
            getSpTeachLocationData();
            getSpSubjectData();
            getSpTimeData();
            getSpQualiData();
            getTeacherProfile();

            btnimgplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                }
            });

            ed_username.setText("Username: " + loginuser);
            txt_username.setText(loginuser);


            btnTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String username = txt_username.getText().toString();
                    String fullname = ed_fullname.getText().toString();
                    String dob = ed_dateofbirth.getText().toString();

                    RadioButton selectRadio = (RadioButton) view_profile.findViewById(radioGender
                            .getCheckedRadioButtonId());
                    String gander = selectRadio.getText().toString();

                    String address = ed_address.getText().toString();
                    String landmark = ed_landmark.getText().toString();
                    String cityid = txtCityid.getText().toString();
                    String zoneid = txtZoneid.getText().toString();
                    String subareaid = txtSubareaid.getText().toString();
                    String strmedium = txtMedium.getText().toString();
                    String strboard = txtBoard.getText().toString();

                    String intrteaching = txtintrteaching.getText().toString();
                    String strclass = txtClass.getText().toString();
                    String strsubject = txtSubject.getText().toString();
                    String strifother = ed_ifother.getText().toString();

                    String contactno = ed_contactno.getText().toString();
                    String landlineno = ed_landlineno.getText().toString();
                    String otherno = ed_otherno.getText().toString();
                    String strfromtime = sp_fromtime.getSelectedItem().toString();
                    String strtotime = sp_totime.getSelectedItem().toString();

                    String strexperience = sp_experience.getSelectedItem().toString();

                    String strquali = txtQuali.getText().toString();
                    String feeshourly = ed_feeshourly.getText().toString();
                    String feesmonthly = ed_feesmonthly.getText().toString();


                    if (!dob.isEmpty() && !address.isEmpty() && !landmark.isEmpty() && !cityid.isEmpty() && !zoneid.isEmpty() && !strmedium.isEmpty() && !strboard.isEmpty() && !strclass.isEmpty() && !strsubject.isEmpty() && !contactno.isEmpty() && !strfromtime.isEmpty() && !strtotime.isEmpty() && !feeshourly.isEmpty() && !feesmonthly.isEmpty()) {

                        if (!fullname.matches(NamePattern) || !(fullname.length() < 3 || fullname.length() > 25)) {
                            if (!address.matches(NamePattern) || !(address.length() < 3 || address.length() > 150)) {
                                if (!landmark.matches(NamePattern) || !(landmark.length() < 3 || landmark.length() > 80)) {
                                    if (!contactno.matches(MobilePattern)) {
                                        if (!hasNullOrEmptyDrawable(imgPhoto)) {

                                            updateStudent(username, fullname, dob, gander, address, landmark, cityid, zoneid, subareaid, strifother, strmedium, strboard, intrteaching, strclass, strsubject, contactno, landlineno, otherno, strfromtime, strtotime, strexperience, strquali, feeshourly, feesmonthly);

                                        } else {
                                            Toast.makeText(getActivity(), "Please find the photo", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(getActivity(), "Mobile Number not valid", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Landmark should be 3 to 80 characters", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Address should be 3 to 150 characters", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Full Name should be 3 to 25 characters", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.fill, Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }
        return view_profile;
    }



    private void getTeacherProfile() {

        final SpotsDialog dialog = new SpotsDialog(context);
        dialog.show();

        String url = AppConfig.GETSTUDENTUSERNAME_URL + "?s_username=" + loginuser;
        Log.d(TAG, url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    dialog.dismiss();

                    JSONArray ja = response.getJSONArray("getuser");

                    JSONObject obj = ja.getJSONObject(0);


                    String st_code = obj.getString("st_code");
                    String st_name = obj.getString("st_name");
                    String st_activateemail = obj.getString("st_activateemail");

                    ed_email.setText("Email Address: " + st_activateemail);
                    teachercode.setText("Student Code: " + st_code);

                    ed_fullname.setText(st_name);

                } catch (Exception exception) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static boolean hasNullOrEmptyDrawable(ImageView iv) {
        Drawable drawable = iv.getDrawable();
        BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;

        return bitmapDrawable == null || bitmapDrawable.getBitmap() == null;
    }

    private void updateStudent(final String username, final String fullname, final String dob, final String gander, final String address, final String landmark, final String cityid, final String zoneid, final String subareaid, final String strifother, final String strmedium, final String strboard, final String intrteaching, final String strclass, final String strsubject, final String contactno, final String landlineno, final String otherno, final String strfromtime, final String strtotime, final String strexperience, final String strquali, final String feeshourly, final String feesmonthly) {

        String url_student = AppConfig.UPDATE_STUDENT_URL;
        Log.d(TAG, url_student);


        String tag_string_req = "req_update";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url_student, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    String message = jObj.getString("message");

                    if (success == 1) {
                        Toast.makeText(context,
                                message, Toast.LENGTH_SHORT).show();

                        StudentProfileFragment fragment = new StudentProfileFragment();
                        FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();

                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Please Update Photo", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String image = getStringImage(bitmap);

                Map<String, String> params = new HashMap<String, String>();

                params.put("st_username", username);
                params.put("st_name", fullname);
                params.put("st_dob", dob);
                params.put("st_gander", gander);
                params.put("st_address", address);
                params.put("st_landmark", landmark);
                params.put("st_city", cityid);
                params.put("st_zone", zoneid);
                params.put("st_subarea", subareaid);
                params.put("st_typetutor", intrteaching);
                params.put("st_class", strclass);
                params.put("st_subject", strsubject);

                params.put("st_medium", strmedium);
                params.put("st_board", strboard);
                params.put("st_other", strifother);

                params.put("st_contactno", contactno);
                params.put("st_landline", landlineno);
                params.put("st_otherno", otherno);

                params.put("st_fromtime", strfromtime);
                params.put("st_totime", strtotime);
                params.put("st_fees_hourly", feeshourly);
                params.put("st_fees_monthly", feesmonthly);
                params.put("st_photo", image);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imgPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void setBirthField() {

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        ed_dateofbirth.setInputType(InputType.TYPE_NULL);
        ed_dateofbirth.requestFocus();

        dateofbirthimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == dateofbirthimg) {
                    datedialog.show();
                }
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datedialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ed_dateofbirth.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    //Used for getting Class Data from the Json Array
    public void getSpCityData() {

        String url_class = AppConfig.SELECT_CITY_URL;
        Log.d(TAG, url_class);

        StringRequest stringRequest = new StringRequest(url_class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject j = new JSONObject(response);
                            int success = j.getInt("success");

                            if (success == 1) {

                                city_result = j.getJSONArray("citylist");

                                getCityName(city_result);
                            } else if (success == 0) {
                                Toast.makeText(context, "No Records Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //Used for getting Class Name from the Json Object
    private void getCityName(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                citylist.add(json.getString("city_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        city_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, citylist) {


            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = super.getView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");

                ((TextView) view1).setTypeface(faceGautami);

                return view1;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view2 = super.getDropDownView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");
                ((TextView) view2).setTypeface(faceGautami);

                return view2;
            }

        };

        city_adapter.add("Select City");
        sp_city.setAdapter(city_adapter);
        sp_city.setSelection(city_adapter.getCount());

    }


    //Used for getting Class Data from the Json Array
    public void getSpClassData() {

        String url_class = AppConfig.SELECT_CLASS_URL;
        Log.d(TAG, url_class);

        StringRequest stringRequest = new StringRequest(url_class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject j = new JSONObject(response);
                            int success = j.getInt("success");

                            if (success == 1) {

                                class_result = j.getJSONArray("classlist");

                                getClassName(class_result);
                            } else if (success == 0) {
                                Toast.makeText(context, "No Records Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //Used for getting Class Name from the Json Object
    private void getClassName(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                classlist.add(json.getString("class_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        for (int i = 0; i < classlist.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(classlist.get(i));
            h.setSelected(false);
            listArrayClass.add(h);
        }
    }


    //Used for getting Teach Location Data from the Json Array
    public void getSpTeachLocationData() {

        String url_class = AppConfig.SELECT_TEACHLOC_URL;
        Log.d(TAG, url_class);

        StringRequest stringRequest = new StringRequest(url_class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject j = new JSONObject(response);
                            int success = j.getInt("success");

                            if (success == 1) {

                                teachloc_result = j.getJSONArray("teachlist");

                                getTeachLocName(teachloc_result);
                            } else if (success == 0) {
                                Toast.makeText(context, "No Records Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //Used for getting Class Name from the Json Object
    private void getTeachLocName(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                teachloclist.add(json.getString("teach_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        for (int i = 0; i < teachloclist.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(teachloclist.get(i));
            h.setSelected(false);
            listArrayTeachLoc.add(h);
        }
    }


    //Used for getting Subject Data from the Json Array
    public void getSpSubjectData() {

        String url_class = AppConfig.SELECT_SUBJECT_URL;
        Log.d(TAG, url_class);

        StringRequest stringRequest = new StringRequest(url_class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject j = new JSONObject(response);
                            int success = j.getInt("success");

                            if (success == 1) {

                                subject_result = j.getJSONArray("subjectlist");

                                getSubjectName(subject_result);
                            } else if (success == 0) {
                                Toast.makeText(context, "No Records Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //Used for getting Class Name from the Json Object
    private void getSubjectName(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                subjectlist.add(json.getString("subj_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        for (int i = 0; i < subjectlist.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(subjectlist.get(i));
            h.setSelected(false);
            listArraySubject.add(h);
        }
    }


    //Used for getting City ID from the Json Object
    private String getCityID(int position) {
        txtSubareaid.setText("");
        txtZoneid.setText("");

        String classid = "";

        try {
            JSONObject json = city_result.getJSONObject(position);

            classid = json.getString("city_id");
        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
        return classid;
    }

    public void getSpZoneData(String cityid) {

        String url_zone = AppConfig.SELECT_ZONE_URL + cityid;
        frame_zone.setVisibility(View.VISIBLE);
        frame_subarea.setVisibility(View.GONE);

        txtSubareaid.setVisibility(View.GONE);
        txtZoneid.setVisibility(View.GONE);

        Log.d(TAG, url_zone);

        StringRequest stringRequest = new StringRequest(url_zone,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject j = new JSONObject(response);
                            int success = j.getInt("success");

                            if (success == 1) {

                                zone_result = j.getJSONArray("zonelist");

                                getZoneName(zone_result);
                            } else if (success == 0) {
                                frame_zone.setVisibility(View.GONE);
                                frame_subarea.setVisibility(View.GONE);
                                txtSubareaid.setVisibility(View.GONE);
                                txtZoneid.setVisibility(View.GONE);
                                Toast.makeText(context, "No Records Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);
    }

    private void getZoneName(JSONArray j) {

        zonelist.clear();

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                zonelist.add(json.getString("zone_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        zone_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, zonelist) {


            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = super.getView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");

                ((TextView) view1).setTypeface(faceGautami);

                return view1;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view2 = super.getDropDownView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");
                ((TextView) view2).setTypeface(faceGautami);

                return view2;
            }

        };

        zone_adapter.add("Select Zone");
        sp_zone.setAdapter(zone_adapter);
        sp_zone.setSelection(zone_adapter.getCount());

    }

    private String getZoneID(int position) {
        txtSubareaid.setText("");
        String zoneid = "";

        try {
            JSONObject json = zone_result.getJSONObject(position);

            zoneid = json.getString("zone_id");
        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
        return zoneid;
    }


    public void getSpSubareaData(String zoneid) {

        String url_subarea = AppConfig.SELECT_SUBAREA_URL + zoneid;

        frame_zone.setVisibility(View.VISIBLE);
        frame_subarea.setVisibility(View.VISIBLE);

        txtSubareaid.setVisibility(View.GONE);
        txtZoneid.setVisibility(View.GONE);

        Log.d(TAG, url_subarea);

        StringRequest stringRequest = new StringRequest(url_subarea,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject j = new JSONObject(response);
                            int success = j.getInt("success");

                            if (success == 1) {

                                subarea_result = j.getJSONArray("subarealist");

                                getSubareaName(subarea_result);

                            } else {
                                frame_zone.setVisibility(View.VISIBLE);
                                frame_subarea.setVisibility(View.GONE);

                                txtSubareaid.setVisibility(View.GONE);
                                txtZoneid.setVisibility(View.GONE);
                                Toast.makeText(context, "No Records Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);
    }

    private void getSubareaName(JSONArray j) {

        subarealist.clear();

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                subarealist.add(json.getString("area_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        subarea_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, subarealist) {


            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = super.getView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");

                ((TextView) view1).setTypeface(faceGautami);

                return view1;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view2 = super.getDropDownView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");
                ((TextView) view2).setTypeface(faceGautami);

                return view2;
            }

        };

        subarea_adapter.add("Select Subarea");
        sp_subarea.setAdapter(subarea_adapter);
        sp_subarea.setSelection(subarea_adapter.getCount());

    }

    private String getSubareaID(int position) {
        String areaid = "";

        try {
            JSONObject json = subarea_result.getJSONObject(position);

            areaid = json.getString("area_id");
        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
        return areaid;
    }

    public void getSpTimeData() {

        String url_class = AppConfig.SELECT_TIME_URL;
        Log.d(TAG, url_class);

        StringRequest stringRequest = new StringRequest(url_class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject j = new JSONObject(response);
                            int success = j.getInt("success");

                            if (success == 1) {

                                from_result = j.getJSONArray("timelist");

                                getTime(from_result);
                            } else if (success == 0) {
                                Toast.makeText(context, "No Records Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //Used for getting Class Name from the Json Object
    private void getTime(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                fromlist.add(json.getString("from_time"));
                tolist.add(json.getString("to_time"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        from_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, fromlist) {


            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = super.getView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");

                ((TextView) view1).setTypeface(faceGautami);

                return view1;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view2 = super.getDropDownView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");
                ((TextView) view2).setTypeface(faceGautami);

                return view2;
            }

        };

        from_adapter.add("From Time");
        sp_fromtime.setAdapter(from_adapter);
        sp_fromtime.setSelection(from_adapter.getCount());


        to_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, tolist) {


            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view1 = (TextView)super.getView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");
                view1.setTypeface(faceGautami);
                if (view1 instanceof TextView) {
                    textViewId = view1.getId();
                }

                //((TextView) view1).setTypeface(faceGautami);


                return view1;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view2 = super.getDropDownView(position, convertView, parent);

                Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                        "fontRegular.ttf");
                ((TextView) view2).setTypeface(faceGautami);

                return view2;
            }

        };

        to_adapter.add("To Time");
        sp_totime.setAdapter(to_adapter);
        sp_totime.setSelection(to_adapter.getCount());

    }

    public void getSpQualiData() {

        String url_class = AppConfig.SELECT_QUALI_URL;
        Log.d(TAG, url_class);

        StringRequest stringRequest = new StringRequest(url_class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject j = new JSONObject(response);
                            int success = j.getInt("success");

                            if (success == 1) {

                                quali_result = j.getJSONArray("qualilist");

                                getQualiName(quali_result);
                            } else if (success == 0) {
                                Toast.makeText(context, "No Records Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //Used for getting Class Name from the Json Object
    private void getQualiName(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                qualilist.add(json.getString("quali_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        for (int i = 0; i < qualilist.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(qualilist.get(i));
            h.setSelected(false);
            listArrayQuali.add(h);
        }
    }


    //Used for Array Adapter
    public class RequiredSpinnerAdapter<T> extends ArrayAdapter<T> {
        public RequiredSpinnerAdapter(Context context, int textViewResourceId,
                                      List<T> objects) {
            super(context, textViewResourceId, objects);
        }

        int textViewId = 0;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Typeface faceGautami = Typeface.createFromAsset(getActivity().getAssets(),
                    "fontRegular.ttf");
            TextView view5 = (TextView) super.getView(position, convertView, parent);
            view5.setTextColor(Color.parseColor("#303030"));
            view5.setTypeface(faceGautami);
            if (view5 instanceof TextView) {
                textViewId = view5.getId();
            }
            return view5;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            return (row);
        }

        public void setError(View v, CharSequence s) {
            if (textViewId != 0) {
                TextView name = (TextView) v.findViewById(textViewId);
                name.setError(s);
            }
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }


    //Used for Setting CaseSencitive
    public static String toInitCap(String param) {
        if (param != null && param.length() > 0) {
            char[] charArray = param.toCharArray(); // convert into char array
            charArray[0] = Character.toUpperCase(charArray[0]); // set capital letter to first postion
            for (int i = 1; i < charArray.length; i++) {
                charArray[i] = Character.toLowerCase(charArray[i]);
            }
            return new String(charArray); // return desired output
        } else {
            return "";
        }
    }

    //checking network service
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
