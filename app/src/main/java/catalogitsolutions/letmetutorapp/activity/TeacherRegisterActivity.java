package catalogitsolutions.letmetutorapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import catalogitsolutions.letmetutorapp.R;
import catalogitsolutions.letmetutorapp.app.AppConfig;
import catalogitsolutions.letmetutorapp.app.AppController;
import me.drakeet.materialdialog.MaterialDialog;

public class TeacherRegisterActivity extends AppCompatActivity {

    private static final String TAG = TeacherRegisterActivity.class.getSimpleName();

    private Toolbar toolbar;

    EditText ed_username, ed_email, ed_password, ed_confpassword;

    CoordinatorLayout coordinatorLayout;

    Button bt_register;

    String UsernamePattern = "^[a-z0-9_-]{3,10}$";

    TextView txtuser,txtemail, txtpwd, txtcpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(TeacherRegisterActivity.this, TeacherLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Used for Typeface fontstyles
        final Typeface font = Typeface.createFromAsset(getAssets(),
                "fontRegular.ttf");

        TextView idLogo = (TextView) toolbar.findViewById(R.id.idLogo);
        idLogo.setText("Register as Teacher");
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

            txtuser = (TextView) findViewById(R.id.txtuser);
            txtemail = (TextView) findViewById(R.id.txtemail);
            txtpwd = (TextView) findViewById(R.id.txtpwd);
            txtcpwd = (TextView) findViewById(R.id.txtcpwd);

            txtuser.setTypeface(font);
            txtemail.setTypeface(font);
            txtpwd.setTypeface(font);
            txtcpwd.setTypeface(font);



            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

            ed_username = (EditText) findViewById(R.id.ed_username);
            ed_email = (EditText) findViewById(R.id.ed_email);
            ed_password = (EditText) findViewById(R.id.ed_password);
            ed_confpassword = (EditText) findViewById(R.id.ed_confpassword);

            ed_username.setTypeface(font);
            ed_email.setTypeface(font);
            ed_password.setTypeface(font);
            ed_confpassword.setTypeface(font);



            bt_register = (Button) findViewById(R.id.bt_register);
            bt_register.setTypeface(font, Typeface.BOLD);

            bt_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String username = ed_username.getText().toString();
                    String email = ed_email.getText().toString();
                    String password = ed_password.getText().toString();
                    String confpassword = ed_confpassword.getText().toString();

                    if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confpassword.isEmpty()) {

                        if(!username.matches(UsernamePattern) || !(username.length() < 3) || !(username.length() > 10)){
                            if(isValidEmaillId(email.trim())){
                                if (!(password.length() < 5)) {
                                    if (password.equals(confpassword)) {
                                        register(username, email, password);
                                    } else {
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "Password Not Matched", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(coordinatorLayout, "Password should be 5 to 12 characters", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                            }else{
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, "Invalid Email Address.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }else{
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "Username should be 3 to 10 characters", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    } else {

                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Please fill the fields", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }


                }
            });



        }
    }

    private void register(final String username, final String email, final String password) {

        String tag_string_req = "register_teacher";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.TEACHER_REGISTER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Teacher Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    String message = jObj.getString("message");

                    if (success == 1) {

                        Toast.makeText(getApplicationContext(), "Teacher Successfully Created.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherLoginActivity.class);
                        //intent.putExtra("teach", "Teacher");
                        Context  ctx   = getApplicationContext();
                        SharedPreferences sharedPreferences = ctx.getSharedPreferences("selectpref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("teach", "Teacher");
                        editor.commit();
                        startActivity(intent);


                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                        snackbar.show();
                        //Toast.makeText(getApplicationContext(), "Invoice is not created", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                //sDept, sMonth, sYear, sInvoiceAmount, sRecDate, sTDS, sSalaryAmt, sCompanyAmt

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
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
        //this.finish();
        Intent intent = new Intent(TeacherRegisterActivity.this, SelectActivity.class);
        startActivity(intent);
        finish();
    }
}
