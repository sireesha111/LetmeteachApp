
package catalogitsolutions.letmetutorapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import catalogitsolutions.letmetutorapp.R;
import catalogitsolutions.letmetutorapp.activity.StudentActivity;
import catalogitsolutions.letmetutorapp.app.AppConfig;
import catalogitsolutions.letmetutorapp.app.AppController;
import dmax.dialog.SpotsDialog;
import me.drakeet.materialdialog.MaterialDialog;


public class StudentProfileFragment extends Fragment implements StudentActivity.OnBackPressedListener {

    Activity context;
    View view_profile;
    private static final String TAG = StudentProfileFragment.class.getSimpleName();

    Typeface font_text;
    TextView txt_tcode, txt_tusername, txt_temailaddr, txt_tname, txt_tdob, txt_tgendar, txt_taddr, txt_tlandmark, txt_tcity, txt_tzone, txt_tsubarea, txt_tteaching, txt_tclass, txt_tteachloc, txt_tsubj, txt_tcontactno, txt_tfromtime, txt_ttotime, txt_texperience, txt_tqualification, txt_tfeeshourly, txt_tfeesmonthly;

    TextView txt_tboard, txt_tother, txt_tmedium, txt_tlandlineno, txt_totherno;

    NetworkImageView thumbNail;

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

        view_profile = inflater.inflate(R.layout.fragment_studentprofile, container, false);
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

            SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.SHARED_STUDENTS_PREF_NAME, Context.MODE_PRIVATE);
            String username = sharedPreferences.getString(AppConfig.STUDENTSUSER_SHARED_PREF, "Not Available");

            Log.d(TAG+": username", username);

            font_text = Typeface.createFromAsset(context.getAssets(),
                    "fontRegular.ttf");

            txt_tcode = (TextView) view_profile.findViewById(R.id.txt_tcode);
            txt_tusername = (TextView) view_profile.findViewById(R.id.txt_tusername);
            txt_temailaddr = (TextView) view_profile.findViewById(R.id.txt_temailaddr);
            txt_tname = (TextView) view_profile.findViewById(R.id.txt_tname);
            txt_tdob = (TextView) view_profile.findViewById(R.id.txt_tdob);
            txt_tgendar = (TextView) view_profile.findViewById(R.id.txt_tgendar);
            txt_taddr = (TextView) view_profile.findViewById(R.id.txt_taddr);
            txt_tlandmark = (TextView) view_profile.findViewById(R.id.txt_tlandmark);
            txt_tcity = (TextView) view_profile.findViewById(R.id.txt_tcity);
            txt_tzone = (TextView) view_profile.findViewById(R.id.txt_tzone);
            txt_tsubarea = (TextView) view_profile.findViewById(R.id.txt_tsubarea);
            txt_tteaching = (TextView) view_profile.findViewById(R.id.txt_tteaching);
            txt_tclass = (TextView) view_profile.findViewById(R.id.txt_tclass);
            txt_tsubj = (TextView) view_profile.findViewById(R.id.txt_tsubj);
            txt_tcontactno = (TextView) view_profile.findViewById(R.id.txt_tcontactno);
            txt_tfromtime = (TextView) view_profile.findViewById(R.id.txt_tfromtime);
            txt_ttotime = (TextView) view_profile.findViewById(R.id.txt_ttotime);
            txt_tfeeshourly = (TextView) view_profile.findViewById(R.id.txt_tfeeshourly);
            txt_tfeesmonthly = (TextView) view_profile.findViewById(R.id.txt_tfeesmonthly);

            txt_tmedium = (TextView) view_profile.findViewById(R.id.txt_tmedium);
            txt_tboard = (TextView) view_profile.findViewById(R.id.txt_tboard);
            txt_tother = (TextView) view_profile.findViewById(R.id.txt_tother);

            txt_tlandlineno = (TextView) view_profile.findViewById(R.id.txt_tlandlineno);
            txt_totherno = (TextView) view_profile.findViewById(R.id.txt_totherno);

            thumbNail = (NetworkImageView) view_profile.findViewById(R.id.imgPhoto);
            thumbNail.setVisibility(View.VISIBLE);
            thumbNail.setDefaultImageResId(R.drawable.ic_profile);
            thumbNail.setErrorImageResId(R.drawable.ic_profile);
            thumbNail.setAdjustViewBounds(true);


            checkViewDetails(username);


        }
        return view_profile;
    }

    private void checkViewDetails(final String loginuser) {
        String url = AppConfig.CHECKSTUDENTSDETAIL_URL +"?user="+loginuser;
        Log.d(TAG, url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int success = response.getInt("success");

                    if (success == 1) {

                        getTeacherProfile(loginuser);

                    } else if (success == 0) {

                        StudentEditProfileFragment fragment = new StudentEditProfileFragment();
                        FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();


                    }

                } catch (JSONException jsn_exception) {
                    jsn_exception.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }




    private void getTeacherProfile(String loginuser) {

        final SpotsDialog dialog = new SpotsDialog(context);
        dialog.show();

        String url = AppConfig.GETSTUDENTUSERNAME_URL +"?s_username="+loginuser;
        Log.d(TAG, url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    dialog.dismiss();

                    JSONArray ja = response.getJSONArray("getuser");

                    JSONObject obj = ja.getJSONObject(0);

                    String t_id = obj.getString("st_id");
                    String t_code = obj.getString("st_code");
                    String t_name = obj.getString("st_name");
                    String t_dob = obj.getString("st_dob");
                    String t_gander = obj.getString("st_gander");
                    String t_address = obj.getString("st_address");
                    String t_landmark = obj.getString("st_landmark");
                    String t_city = obj.getString("st_city");
                    String t_zone = obj.getString("st_zone");
                    String t_subarea = obj.getString("st_subarea");
                    String t_teaching = obj.getString("st_typetutor");
                    String t_class = obj.getString("st_class");
                    String t_subject = obj.getString("st_subject");
                    String t_medium = obj.getString("st_medium");
                    String t_board = obj.getString("st_board");
                    String t_other = obj.getString("st_other");

                    String t_contactno = obj.getString("st_mobieno");
                    String t_landline = obj.getString("st_landline");
                    String t_otherno = obj.getString("st_otherno");
                    String t_fromtime = obj.getString("st_fromtime");
                    String t_totime = obj.getString("st_totime");
                    String t_fees_hourly = obj.getString("st_fees_hourly");
                    String t_fees_monthly = obj.getString("st_fees_monthly");
                    String t_photo = obj.getString("st_photo");
                    String t_username = obj.getString("st_username");
                    String t_activateemail = obj.getString("st_activateemail");
                    String t_password = obj.getString("st_password");
                    String t_status = obj.getString("st_status");

                    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                    thumbNail.setImageUrl(AppConfig.BASE_URL + t_photo, imageLoader);
                    //Loading image from below url into imageView
                    Picasso.with(getActivity()).load(AppConfig.BASE_URL + t_photo).resize(200, 200).into(thumbNail);

                    Spanned tcode = Html.fromHtml("<b>Student Code &nbsp;: </b>  " + t_code);
                    txt_tcode.setText(tcode);
                    txt_tcode.setTypeface(font_text);

                    Spanned tuser = Html.fromHtml("<b>Username &nbsp;: </b>  " + t_username);
                    txt_temailaddr.setText(tuser);
                    txt_temailaddr.setTypeface(font_text);

                    Spanned temail = Html.fromHtml("<b>Email Address &nbsp;: </b>  " + t_activateemail);
                    txt_tusername.setText(temail);
                    txt_tusername.setTypeface(font_text);

                    Spanned tfullname = Html.fromHtml("<b>Full Name &nbsp;: </b>  " + t_name);
                    txt_tname.setText(tfullname);
                    txt_tname.setTypeface(font_text);

                    Spanned tdob = Html.fromHtml("<b>Date of Birth &nbsp;: </b>  " + t_dob);
                    txt_tdob.setText(tdob);
                    txt_tdob.setTypeface(font_text);

                    Spanned tgender = Html.fromHtml("<b>Gender &nbsp;: </b>  " + t_gander);
                    txt_tgendar.setText(tgender);
                    txt_tgendar.setTypeface(font_text);

                    Spanned taddr = Html.fromHtml("<b>Address &nbsp;: </b>  " + t_address);
                    txt_taddr.setText(taddr);
                    txt_taddr.setTypeface(font_text);

                    Spanned tlandmark = Html.fromHtml("<b>Landmark &nbsp;: </b>  " + t_landmark);
                    txt_tlandmark.setText(tlandmark);
                    txt_tlandmark.setTypeface(font_text);

                    Spanned tcity = Html.fromHtml("<b>City &nbsp;: </b>  " + t_city);
                    txt_tcity.setText(tcity);
                    txt_tcity.setTypeface(font_text);

                    Spanned tzone = Html.fromHtml("<b>Zone &nbsp;: </b>  " + t_zone);
                    txt_tzone.setText(tzone);
                    txt_tzone.setTypeface(font_text);

                    if(t_subarea.equals("null")) {

                        Spanned tsubarea = Html.fromHtml("<b>Subarea &nbsp;: </b> -");
                        txt_tsubarea.setText(tsubarea);
                        txt_tsubarea.setTypeface(font_text);
                    }else{
                        Spanned tsubarea = Html.fromHtml("<b>Subarea &nbsp;: </b>  " + t_subarea);
                        txt_tsubarea.setText(tsubarea);
                        txt_tsubarea.setTypeface(font_text);
                    }

                    Spanned tteaching = Html.fromHtml("<b>Teaching &nbsp;: </b>  " + t_teaching);
                    txt_tteaching.setText(tteaching);
                    txt_tteaching.setTypeface(font_text);

                    Spanned tmedium = Html.fromHtml("<b>Medium &nbsp;: </b>  " + t_medium);
                    txt_tmedium.setText(tmedium);
                    txt_tmedium.setTypeface(font_text);

                    Spanned tboard = Html.fromHtml("<b>Board &nbsp;: </b>  " + t_board);
                    txt_tboard.setText(tboard);
                    txt_tboard.setTypeface(font_text);

                    Spanned tother = Html.fromHtml("<b>If Other(Specify) &nbsp;: </b>  " + t_other);
                    txt_tother.setText(tother);
                    txt_tother.setTypeface(font_text);

                    Spanned tclass = Html.fromHtml("<b>Class &nbsp;: </b>  " + t_class);
                    txt_tclass.setText(tclass);
                    txt_tclass.setTypeface(font_text);
                    Log.d("class : ", t_class);


                    Spanned tsubject = Html.fromHtml("<b>Subject &nbsp;: </b>  " + t_subject);
                    txt_tsubj.setText(tsubject);
                    txt_tsubj.setTypeface(font_text);

                    Spanned tcontactno = Html.fromHtml("<b>Mobile Number &nbsp;: </b>  " + t_contactno);
                    txt_tcontactno.setText(tcontactno);
                    txt_tcontactno.setTypeface(font_text);

                    Spanned tlandline = Html.fromHtml("<b>Landline Number &nbsp;: </b>  " + t_landline);
                    txt_tlandlineno.setText(tlandline);
                    txt_tlandlineno.setTypeface(font_text);

                    Spanned totherno = Html.fromHtml("<b>Other Number &nbsp;: </b>  " + t_otherno);
                    txt_totherno.setText(totherno);
                    txt_totherno.setTypeface(font_text);

                    Spanned tfromtime = Html.fromHtml("<b>Time &nbsp;: </b>  " + t_fromtime+" to "+t_totime);
                    txt_tfromtime.setText(tfromtime);
                    txt_tfromtime.setTypeface(font_text);



                    Spanned tfees_hourly = Html.fromHtml("<b>Fees Hourly &nbsp;: </b>  \u20B9" + t_fees_hourly+".00");
                    txt_tfeeshourly.setText(tfees_hourly);
                    txt_tfeeshourly.setTypeface(font_text);

                    Spanned tfees_monthly = Html.fromHtml("<b>Fees Monthly &nbsp;: </b>  \u20B9" + t_fees_monthly+".00");
                    txt_tfeesmonthly.setText(tfees_monthly);
                    txt_tfeesmonthly.setTypeface(font_text);


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
