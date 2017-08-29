package varnalabs.letmetutorapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import varnalabs.letmetutorapp.R;
import varnalabs.letmetutorapp.app.AppConfig;
import varnalabs.letmetutorapp.app.AppController;
import me.drakeet.materialdialog.MaterialDialog;

public class TeacherDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private static final String TAG = TeacherDetailsActivity.class.getSimpleName();

    NetworkImageView thumbNail;

    LinearLayout llstaffdetails;

    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 1;

    TextView txtpreferedarea, txtname, txtcode, txttypetutor, txtSexage, txtclass, txtcity, txtsubject, txtarea, txtsubarea, txtboard, txtcontactttime, txtfeesrange, txtaddress, txtmobileno, txtEmailid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherdetails);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        //Used for Typeface fontstyles
        final Typeface font = Typeface.createFromAsset(getAssets(),
                "fontRegular.ttf");

        TextView idLogo = (TextView) toolbar.findViewById(R.id.idLogo);
        idLogo.setText("Tutor Details");
        idLogo.setTypeface(font);

        // checking internet connection
        if (!haveNetworkConnection()) {
            final MaterialDialog mMaterialDialog = new MaterialDialog(this);
            mMaterialDialog.setTitle("No Internet Connection")
                    .setMessage("You are offline please check your internet connection")

                    .setPositiveButton(
                            "OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                    finish();
                                }
                            }
                    )

                    .show();
        } else {

            llstaffdetails = (LinearLayout) findViewById(R.id.llstaffdetails);

            TextView txt1 = (TextView) findViewById(R.id.txt1);
            TextView txt2 = (TextView) findViewById(R.id.txt2);
            TextView txt3 = (TextView) findViewById(R.id.txt3);
            TextView txt4 = (TextView) findViewById(R.id.txt4);
            TextView txt5 = (TextView) findViewById(R.id.txt5);
            TextView txt6 = (TextView) findViewById(R.id.txt6);
            TextView txt7 = (TextView) findViewById(R.id.txt7);
            TextView txt8 = (TextView) findViewById(R.id.txt8);
            TextView txt9 = (TextView) findViewById(R.id.txt9);
            TextView txt10 = (TextView) findViewById(R.id.txt10);
            TextView txt11 = (TextView) findViewById(R.id.txt11);
            TextView txt12 = (TextView) findViewById(R.id.txt12);
            TextView txt13 = (TextView) findViewById(R.id.txt13);

            txt1.setTypeface(font, Typeface.BOLD);
            txt2.setTypeface(font, Typeface.BOLD);
            txt3.setTypeface(font, Typeface.BOLD);
            txt4.setTypeface(font, Typeface.BOLD);
            txt5.setTypeface(font, Typeface.BOLD);
            txt6.setTypeface(font, Typeface.BOLD);
            txt7.setTypeface(font, Typeface.BOLD);
            txt8.setTypeface(font, Typeface.BOLD);
            txt9.setTypeface(font, Typeface.BOLD);
            txt10.setTypeface(font, Typeface.BOLD);
            txt11.setTypeface(font, Typeface.BOLD);
            txt12.setTypeface(font, Typeface.BOLD);
            txt13.setTypeface(font, Typeface.BOLD);


            Intent i = getIntent();
            String studentid = i.getStringExtra("studentid");
            String strclass = i.getStringExtra("strclass");
            String strsubject = i.getStringExtra("strsubject");
            String strcity = i.getStringExtra("strcity");
            String strzone = i.getStringExtra("strzone");
            String strquali = i.getStringExtra("strquali");

            txtname = (TextView) findViewById(R.id.txtname);
            txtcode = (TextView) findViewById(R.id.txtcode);
            txttypetutor = (TextView) findViewById(R.id.txttypetutor);
            txtSexage = (TextView) findViewById(R.id.txtSexage);
            txtclass = (TextView) findViewById(R.id.txtclass);
            txtsubject = (TextView) findViewById(R.id.txtsubject);
            txtcity = (TextView) findViewById(R.id.txtcity);
            txtarea = (TextView) findViewById(R.id.txtarea);
            txtsubarea = (TextView) findViewById(R.id.txtsubarea);
            txtboard = (TextView) findViewById(R.id.txtboard);
            txtcontactttime = (TextView) findViewById(R.id.txtcontactttime);
            txtfeesrange = (TextView) findViewById(R.id.txtfeesrange);
            txtaddress = (TextView) findViewById(R.id.txtaddress);
            txtmobileno = (TextView) findViewById(R.id.txtmobileno);
            txtEmailid = (TextView) findViewById(R.id.txtEmailid);
            txtpreferedarea = (TextView) findViewById(R.id.txtpreferedarea);

            txtclass.setText(""+strclass);
            txtsubject.setText(""+strsubject);

            txtname.setTypeface(font, Typeface.BOLD);
            txtcode.setTypeface(font);
            txttypetutor.setTypeface(font);
            txtpreferedarea.setTypeface(font);
            txtSexage.setTypeface(font);
            txtclass.setTypeface(font);
            txtsubject.setTypeface(font);
            txtcity.setTypeface(font);
            txtsubarea.setTypeface(font);
            txtboard.setTypeface(font);
            txtcontactttime.setTypeface(font);
            txtfeesrange.setTypeface(font);
            txtaddress.setTypeface(font);
            txtmobileno.setTypeface(font);
            txtEmailid.setTypeface(font);

            final LinearLayout btnDialCall = (LinearLayout) findViewById(R.id.btnDialCall);
            //To make call
            btnDialCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (view == btnDialCall) {
                        //permission check and request call function
                        call();
                    }
                }
            });

            LinearLayout btnSendmail = (LinearLayout) findViewById(R.id.btnSendmail);

            btnSendmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), SendMailActivity.class);
                    intent.putExtra("emailid", txtEmailid.getText().toString());
                    startActivity(intent);
                }
            });



            getTeacherDetails(studentid);
        }
    }

    private void getTeacherDetails(String studentid) {

        String url_teacher = AppConfig.TEACHERDETAILS_URL +"?id="+studentid;
        url_teacher=url_teacher.replaceAll(" ", "%20");

        Log.d(TAG, url_teacher);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url_teacher, null, new Response.Listener<JSONObject>() {

            String strexperience, strname, strteaching, strcode, strteachloc, strsex, strsubarea, strtime, straddr, strmobileno, stremail, bitmap, strfeeshourly, strfeesmonthly, strlandmark, strzone, strcity;


            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray ja = response.getJSONArray("getuser");

                    JSONObject obj = ja.getJSONObject(0);

                    bitmap = obj.getString("t_photo");
                    strname = obj.getString("t_name");
                    strcode = obj.getString("t_code");
                    strteaching = obj.getString("t_teaching");
                    strteachloc = obj.getString("t_teachlocation");
                    strsex = obj.getString("t_gander")+" "+obj.getString("t_age");
                    strsubarea = obj.getString("t_subarea");
                    strcity = obj.getString("t_city");
                    strzone = obj.getString("t_zone");
                    strexperience = obj.getString("t_experience");
                    strtime = obj.getString("t_fromtime")+" to "+obj.getString("t_totime");
                    strfeeshourly = obj.getString("t_fees_hourly");
                    strfeesmonthly = obj.getString("t_fees_monthly");
                    straddr = obj.getString("t_address");
                    strlandmark = obj.getString("t_landmark");
                    strmobileno = obj.getString("t_contactno");
                    stremail = obj.getString("t_activateemail");

                    thumbNail = (NetworkImageView) findViewById(R.id.thumbnail);

                    llstaffdetails.setVisibility(View.VISIBLE);
                    thumbNail.setVisibility(View.VISIBLE);

                    thumbNail.setDefaultImageResId(R.drawable.ic_profile);
                    thumbNail.setErrorImageResId(R.drawable.ic_profile);
                    thumbNail.setAdjustViewBounds(true);

                    //bitmap = obj.getString("t_photo");

                    bitmap = bitmap.replaceAll(" ", "%20");


                    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                    thumbNail.setImageUrl(AppConfig.IMAGES + bitmap, imageLoader);



                } catch (Exception exception) {

                }

                txtcity.setText(""+strcity);
                txtarea.setText(""+strzone);




                txtname.setText(capitalize(strname));
                txtcode.setText("Tutor Code: "+strcode);
                txttypetutor.setText("Interested in Teaching Type: "+strteaching);
                txtSexage.setText(strsex);
                txtboard.setText(strexperience);
                txtcontactttime.setText(strtime);
                txtpreferedarea.setText(strteachloc);
                //txtfeesrange.setText("Hourly: "+strfeeshourly+", Monthly: "+strfeesmonthly);
                //txtaddress.setText(straddr+"Landmark:"+strlandmark);
                txtmobileno.setText(strmobileno);
                txtEmailid.setText(stremail);

                if(strsubarea.equals("null") || strsubarea.equals("")){
                    txtsubarea.setText("--");
                }else{
                    txtsubarea.setText(strsubarea);
                }

                Spanned tfees_monthly = Html.fromHtml("<b>Hourly &nbsp;: </b>  ₹" + strfeeshourly+".00, <b>Monthly &nbsp;: </b>  ₹"+strfeesmonthly+".00");
                txtfeesrange.setText(tfees_monthly);

                Spanned taddr = Html.fromHtml(straddr+"<br>Landmark: "+strlandmark);
                txtaddress.setText(taddr);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }

    //Used for to make voice call
    private void call() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
        } else {
            //Open call function
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + txtmobileno.getText().toString()));
            startActivity(intent);

            Log.d("mobileno", txtmobileno.getText().toString());
        }
    }
    // Permission is granted
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                call();
            } else {
            }
        }
    }
    // Used for Case Sensitive
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

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }



    //checking network service
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
