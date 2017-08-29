
/*Developed By: Catalog IT Solutions. Copyright © 2017. Catalog. All Rights Reserved.This product is protected by copyright
 laws. Unauthorized reproduction or distribution of this product,or any portion of it,may result in severe civil and
 criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 File Name: TeacherLoginActivity.java
 Created By: Srinivas A and Deepika I
 Created On: 02/Jan/2017
 Last Modified By: Deepika I
 Last Modified On: 24/Jan/2017
 Purpose: To Admin Login
  */

package varnalabs.letmetutorapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import varnalabs.letmetutorapp.R;
import varnalabs.letmetutorapp.app.AppConfig;
import me.drakeet.materialdialog.MaterialDialog;

public class StudentLoginActivity extends AppCompatActivity {

    EditText ed_username, ed_password;

    Button btnLogin;

    TextView txtSignup;

    private CoordinatorLayout coordinatorLayout;
    Typeface font_text;

    TextView txtHeading;

    private boolean loggedIn = false;

    SessionManager manager;

    private ProgressDialog pDialog;

    private static final String TAG = StudentLoginActivity.class.getSimpleName();

    String teaching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = new SessionManager();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //checking internet connection
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
            font_text = Typeface.createFromAsset(getAssets(),
                    "fontRegular.ttf");
            ed_username = (EditText) findViewById(R.id.ed_username);
            ed_password = (EditText) findViewById(R.id.ed_password);
            btnLogin = (Button) findViewById(R.id.btnLogin);
            txtHeading = (TextView) findViewById(R.id.txtHeading);
            txtHeading.setText("Let Me Teach - Student");
            txtHeading.setTypeface(font_text, Typeface.BOLD);


            txtSignup = (TextView) findViewById(R.id.txtSignup);

            txtSignup.setTypeface(font_text, Typeface.BOLD);

            coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                    .coordinatorLayout);

            ed_username.setTypeface(font_text);
            ed_password.setTypeface(font_text);
            btnLogin.setTypeface(font_text);



            //Intent i = getIntent();
            //teaching = i.getStringExtra("teach");

            //Toast.makeText(this, "teach="+teaching, Toast.LENGTH_SHORT).show();

            //Log.d(TAG, "teach = "+teaching);

            Context ctx = getApplicationContext();
            //String strSavedValue = null;
            SharedPreferences sharedPreferences = ctx.getSharedPreferences("selectpref", Context.MODE_PRIVATE);

            teaching = sharedPreferences.getString("Students", null);

            //Toast.makeText(this, "teach="+teaching, Toast.LENGTH_SHORT).show();

            Log.d(TAG, "teach = "+teaching);



            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = ed_username.getText().toString();
                    String password = ed_password.getText().toString();

                    if (!username.isEmpty() && !password.isEmpty()) {



                            checkStudentLogin(username, password);

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Please enter the credentials!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });

            txtSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent_te = new Intent(StudentLoginActivity.this, StudentRegisterActivity.class);
                    startActivity(intent_te);
                    finish();


                }
            });
        }
    }
    //Purpose: Used for sessions
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppConfig.SHARED_STUDENTS_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(AppConfig.LOGGEDIN_SHARED_PREF, false);
        if (loggedIn) {
            Intent intent = new Intent(StudentLoginActivity.this, StudentActivity.class);
            startActivity(intent);
        }
    }

    //Purpose: Used for validating login status
    private void checkStudentLogin(final String strusername, final String strpassword) {

        pDialog.setMessage("Loading ... ");
        showDialog();
        Log.d(TAG, AppConfig.STUDENT_LOGIN_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.STUDENT_LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                hideDialog();

                if (response.equalsIgnoreCase(AppConfig.LOGIN_SUCCESS)) {

                    SharedPreferences sharedPreferences = StudentLoginActivity.this.getSharedPreferences(AppConfig.SHARED_STUDENTS_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(AppConfig.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(AppConfig.STUDENTSUSER_SHARED_PREF, strusername);
                    editor.commit();
                    manager.setPreferences(StudentLoginActivity.this, "status", "1");
                    String status = manager.getPreferences(StudentLoginActivity.this, "status");
                    Log.d("status", status);

                    ed_username.setText("");
                    ed_password.setText("");

                    Intent intent = new Intent(StudentLoginActivity.this, StudentActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ed_username.setText("");
                    ed_password.setText("");
                    Toast.makeText(StudentLoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    hideDialog();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                ed_username.setText("");
                ed_password.setText("");

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("student_username", strusername);
                params.put("student_password", strpassword);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //checking network services
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //this.finish();
        Intent intent = new Intent(StudentLoginActivity.this, SelectActivity.class);
        startActivity(intent);
        finish();
    }
}
