
package varnalabs.letmetutorapp.fragment;

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

import varnalabs.letmetutorapp.R;
import varnalabs.letmetutorapp.activity.TeacherActivity;
import varnalabs.letmetutorapp.app.AppConfig;
import varnalabs.letmetutorapp.app.AppController;
import dmax.dialog.SpotsDialog;
import me.drakeet.materialdialog.MaterialDialog;


public class TeacherProfileFragment extends Fragment implements TeacherActivity.OnBackPressedListener {

    Activity context;
    View view_profile;
    private static final String TAG = TeacherProfileFragment.class.getSimpleName();

    Typeface font_text;
    TextView txt_tcode, txt_tusername, txt_temailaddr, txt_tname, txt_tdob, txt_tgendar, txt_taddr, txt_tlandmark, txt_tcity, txt_tzone, txt_tsubarea, txt_tteaching, txt_tclass, txt_tteachloc, txt_tsubj, txt_tcontactno, txt_tfromtime, txt_ttotime, txt_texperience, txt_tqualification, txt_tfeeshourly, txt_tfeesmonthly;

    NetworkImageView thumbNail;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TeacherActivity) getActivity()).setOnBackPressedListener(this);
    }

    public void doBack() {
        TeacherDashboardFragment fragment = new TeacherDashboardFragment();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_profile = inflater.inflate(R.layout.fragment_teacherprofile, container, false);
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
            txt_tteachloc = (TextView) view_profile.findViewById(R.id.txt_tteachloc);
            txt_tsubj = (TextView) view_profile.findViewById(R.id.txt_tsubj);
            txt_tcontactno = (TextView) view_profile.findViewById(R.id.txt_tcontactno);
            txt_tfromtime = (TextView) view_profile.findViewById(R.id.txt_tfromtime);
            txt_ttotime = (TextView) view_profile.findViewById(R.id.txt_ttotime);
            txt_texperience = (TextView) view_profile.findViewById(R.id.txt_texperience);
            txt_tqualification = (TextView) view_profile.findViewById(R.id.txt_tqualification);
            txt_tfeeshourly = (TextView) view_profile.findViewById(R.id.txt_tfeeshourly);
            txt_tfeesmonthly = (TextView) view_profile.findViewById(R.id.txt_tfeesmonthly);

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
        String url = AppConfig.CHECKTEACHERDETAIL_URL +"?user="+loginuser;
        Log.d(TAG, url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int success = response.getInt("success");

                    if (success == 1) {

                        getTeacherProfile(loginuser);

                    } else if (success == 0) {

                        TeacherEditProfileFragment fragment = new TeacherEditProfileFragment();
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

        String url = AppConfig.GETTEACHERUSERNAME_URL +"?t_username="+loginuser;
        Log.d(TAG, url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    dialog.dismiss();

                    JSONArray ja = response.getJSONArray("getuser");

                    JSONObject obj = ja.getJSONObject(0);

                    String t_id = obj.getString("t_id");
                    String t_code = obj.getString("t_code");
                    String t_name = obj.getString("t_name");
                    String t_dob = obj.getString("t_dob");
                    String t_gander = obj.getString("t_gander");
                    String t_address = obj.getString("t_address");
                    String t_landmark = obj.getString("t_landmark");
                    String t_city = obj.getString("t_city");
                    String t_zone = obj.getString("t_zone");
                    String t_subarea = obj.getString("t_subarea");
                    String t_teaching = obj.getString("t_teaching");
                    String t_class = obj.getString("t_class");
                    String t_teachlocation = obj.getString("t_teachlocation");
                    String t_subject = obj.getString("t_subject");
                    String t_contactno = obj.getString("t_contactno");
                    String t_fromtime = obj.getString("t_fromtime");
                    String t_totime = obj.getString("t_totime");
                    String t_experience = obj.getString("t_experience");
                    String t_qualification = obj.getString("t_qualification");
                    String t_fees_hourly = obj.getString("t_fees_hourly");
                    String t_fees_monthly = obj.getString("t_fees_monthly");
                    String t_photo = obj.getString("t_photo");
                    String t_username = obj.getString("t_username");
                    String t_activateemail = obj.getString("t_activateemail");
                    String t_password = obj.getString("t_password");
                    String t_status = obj.getString("t_status");

                    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                    thumbNail.setImageUrl(AppConfig.BASE_URL + t_photo, imageLoader);
                    //Loading image from below url into imageView
                    Picasso.with(getActivity()).load(AppConfig.BASE_URL + t_photo).resize(200, 200).into(thumbNail);

                    Spanned tcode = Html.fromHtml("<b>Tutor Code &nbsp;: </b>  " + t_code);
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

                    Spanned tclass = Html.fromHtml("<b>Class &nbsp;: </b>  " + t_class);
                    txt_tclass.setText(tclass);
                    txt_tclass.setTypeface(font_text);

                    Spanned tteachlocation = Html.fromHtml("<b>Location for Teaching &nbsp;: </b>  " + t_teachlocation);
                    txt_tteachloc.setText(tteachlocation);
                    txt_tteachloc.setTypeface(font_text);

                    Spanned tsubject = Html.fromHtml("<b>Subject &nbsp;: </b>  " + t_subject);
                    txt_tsubj.setText(tsubject);
                    txt_tsubj.setTypeface(font_text);

                    Spanned tcontactno = Html.fromHtml("<b>Contact Number &nbsp;: </b>  " + t_contactno);
                    txt_tcontactno.setText(tcontactno);
                    txt_tcontactno.setTypeface(font_text);

                    Spanned tfromtime = Html.fromHtml("<b>Time &nbsp;: </b>  " + t_fromtime+" to "+t_totime);
                    txt_tfromtime.setText(tfromtime);
                    txt_tfromtime.setTypeface(font_text);

                    Spanned texperience = Html.fromHtml("<b>Experience &nbsp;: </b>  " + t_experience);
                    txt_texperience.setText(texperience);
                    txt_texperience.setTypeface(font_text);

                    Spanned tqualification = Html.fromHtml("<b>Qualification &nbsp;: </b>  " + t_qualification);
                    txt_tqualification.setText(tqualification);
                    txt_tqualification.setTypeface(font_text);

                    Spanned tfees_hourly = Html.fromHtml("<b>Fees Hourly &nbsp;: </b>  ₹" + t_fees_hourly+".00");
                    txt_tfeeshourly.setText(tfees_hourly);
                    txt_tfeeshourly.setTypeface(font_text);

                    Spanned tfees_monthly = Html.fromHtml("<b>Fees Monthly &nbsp;: </b>  ₹" + t_fees_monthly+".00");
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
