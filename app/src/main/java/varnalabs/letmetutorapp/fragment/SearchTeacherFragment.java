
package varnalabs.letmetutorapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import varnalabs.letmetutorapp.R;
import varnalabs.letmetutorapp.activity.StudentActivity;
import varnalabs.letmetutorapp.activity.SearchTeacherActivity;
import varnalabs.letmetutorapp.app.AppConfig;
import me.drakeet.materialdialog.MaterialDialog;


public class SearchTeacherFragment extends Fragment implements StudentActivity.OnBackPressedListener {

    Activity context;
    View view;
    private static final String TAG = SearchTeacherFragment.class.getSimpleName();

    Button btnSearch;

    Typeface font_text;

    Spinner sp_subject, sp_class, sp_city, sp_zone, sp_quali;
    TextView txt_subject, txt_class, txt_city, txt_zone, heading, txt_quali;

    private JSONArray subject_result;
    ArrayList<String> subjectlist;
    private RequiredSpinnerAdapter subject_adapter;

    private JSONArray class_result;
    ArrayList<String> classeslist;
    private RequiredSpinnerAdapter class_adapter;

    private JSONArray quali_result;
    ArrayList<String> qualilist;
    private RequiredSpinnerAdapter quali_adapter;

    private JSONArray city_result;
    ArrayList<String> citylist;
    private RequiredSpinnerAdapter city_adapter;

    private JSONArray zone_result;
    ArrayList<String> zoneslist;
    private RequiredSpinnerAdapter zone_adapter;


    FrameLayout frame_zone;

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

        view = inflater.inflate(R.layout.fragment_teachersearch, container, false);
        context=getActivity();
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

            SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.SHARED_TEACHER_PREF_NAME, Context.MODE_PRIVATE);
            String username = sharedPreferences.getString(AppConfig.TEACHERUSER_SHARED_PREF, "Not Available");

            font_text = Typeface.createFromAsset(context.getAssets(),
                    "fontRegular.ttf");


            sp_subject = (Spinner) view.findViewById(R.id.sp_subject);
            sp_class = (Spinner) view.findViewById(R.id.sp_class);
            sp_city = (Spinner) view.findViewById(R.id.sp_city);
            sp_zone = (Spinner) view.findViewById(R.id.sp_zone);
            sp_quali = (Spinner) view.findViewById(R.id.sp_quali);

            btnSearch = (Button) view.findViewById(R.id.btnSearch);
            btnSearch.setTypeface(font_text, Typeface.BOLD);

            heading = (TextView) view.findViewById(R.id.heading);
            heading.setTypeface(font_text, Typeface.BOLD);

            txt_subject = (TextView) view.findViewById(R.id.txt_subject);
            txt_class = (TextView) view.findViewById(R.id.txt_class);
            txt_city = (TextView) view.findViewById(R.id.txt_city);
            txt_zone = (TextView) view.findViewById(R.id.txt_zone);
            txt_quali = (TextView) view.findViewById(R.id.txt_quali);

            frame_zone = (FrameLayout) view.findViewById(R.id.frame_zone);

            classeslist = new ArrayList<String>();
            subjectlist = new ArrayList<String>();
            citylist = new ArrayList<String>();
            zoneslist = new ArrayList<String>();
            qualilist = new ArrayList<String>();

            getSpClassData();
            getSpSubjectData();
            getSpCityData();
            getSpQualiData();

            sp_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    parent.post(new Runnable() {
                        @Override
                        public void run() {
                            sp_subject.requestFocusFromTouch();
                        }
                    });

                    if (sp_subject.getSelectedItem() == "Select Subject") {

                        parent.post(new Runnable() {
                            @Override
                            public void run() {
                                sp_subject.requestFocusFromTouch();
                            }
                        });

                    } else {

                        txt_subject.setText(sp_subject.getSelectedItem().toString());


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            sp_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    parent.post(new Runnable() {
                        @Override
                        public void run() {
                            sp_class.requestFocusFromTouch();
                        }
                    });

                    if (sp_class.getSelectedItem() == "Select Class") {

                        parent.post(new Runnable() {
                            @Override
                            public void run() {
                                sp_class.requestFocusFromTouch();
                            }
                        });

                    } else {
                       txt_class.setText(sp_class.getSelectedItem().toString());


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            sp_quali.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    parent.post(new Runnable() {
                        @Override
                        public void run() {
                            sp_quali.requestFocusFromTouch();
                        }
                    });

                    if (sp_quali.getSelectedItem() == "Select Qualification") {

                        parent.post(new Runnable() {
                            @Override
                            public void run() {
                                sp_quali.requestFocusFromTouch();
                            }
                        });

                    } else {

                        txt_quali.setText(sp_quali.getSelectedItem().toString());


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

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
                        txt_city.setText(getCityID(position));
                        String cityid = txt_city.getText().toString();

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
                        txt_zone.setText(getZoneID(position));

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String strclass = txt_class.getText().toString();
                    String strsubject = txt_subject.getText().toString();
                    String strcity = txt_city.getText().toString();
                    String strzone = txt_zone.getText().toString();
                    String strquali = txt_quali.getText().toString();

                    Log.d(TAG, "Class:"+strclass);
                    Log.d(TAG, "Subject:"+strsubject);
                    Log.d(TAG, "City:"+strcity);
                    Log.d(TAG, "Zone:"+strzone);
                    Log.d(TAG, "Quali:"+strquali);

                    if(!strclass.isEmpty() && !strsubject.isEmpty() && !strcity.isEmpty() && !strzone.isEmpty()){
                        Intent intent = new Intent(getActivity(), SearchTeacherActivity.class);
                        intent.putExtra("strclass", strclass);
                        intent.putExtra("strsubject", strsubject);
                        intent.putExtra("strcity", strcity);
                        intent.putExtra("strzone", strzone);
                        intent.putExtra("strquali", strquali);
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, "Please all the fill", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
        return view;
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

    private void getQualiName(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                qualilist.add(json.getString("quali_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        quali_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, qualilist) {


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

        quali_adapter.add("Select Qualification");
        sp_quali.setAdapter(quali_adapter);
        sp_quali.setSelection(quali_adapter.getCount());

    }

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

    private void getClassName(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                classeslist.add(json.getString("class_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        class_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, classeslist) {


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

        class_adapter.add("Select Class");
        sp_class.setAdapter(class_adapter);
        sp_class.setSelection(class_adapter.getCount());

    }

    private String getClassID(int position) {

        String classid = "";

        try {
            JSONObject json = class_result.getJSONObject(position);

            classid = json.getString("class_id");
        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
        return classid;
    }


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

    private void getSubjectName(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                subjectlist.add(json.getString("subj_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        subject_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, subjectlist) {


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

        subject_adapter.add("Select Subject");
        sp_subject.setAdapter(subject_adapter);
        sp_subject.setSelection(subject_adapter.getCount());
    }

    private String getSubjectID(int position) {

        String subjid = "";

        try {
            JSONObject json = subject_result.getJSONObject(position);

            subjid = json.getString("subj_id");
        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
        return subjid;
    }


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

    private String getCityID(int position) {
        txt_zone.setText("");

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

        zoneslist.clear();

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                zoneslist.add(json.getString("zone_name"));
            } catch (JSONException jsonexception) {
                jsonexception.printStackTrace();
            }
        }

        zone_adapter = new RequiredSpinnerAdapter(context, R.layout.custom_textview_to_spinner, zoneslist) {


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
        String zoneid = "";

        try {
            JSONObject json = zone_result.getJSONObject(position);

            zoneid = json.getString("zone_id");
        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
        return zoneid;
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
