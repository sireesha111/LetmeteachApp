
/*Developed By: Catalog IT Solutions. Copyright © 2017. Catalog. All Rights Reserved.This product is protected by copyright
 laws. Unauthorized reproduction or distribution of this product,or any portion of it,may result in severe civil and
 criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 File Name: SendMailActivity.java
 Created By: Sireesha A
 Created On: 02/Jan/2017
 Last Modified By: Sireesha A
 Last Modified On: 22/Feb/2017
 Purpose: To Send Mail Activity
  */

package varnalabs.letmetutorapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import varnalabs.letmetutorapp.R;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Sireesha on 8/30/2016.
 */
public class SendMailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    //Send button
    private Button buttonSend;
    String emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmail);

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
        //Used for Checking Internet Connection
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


            final Typeface font = Typeface.createFromAsset(getAssets(),
                    "fontRegular.ttf");

            TextView idLogo = (TextView) toolbar.findViewById(R.id.idLogo);
            idLogo.setText("XpertEDU");
            idLogo.setTypeface(font);

            Intent i = getIntent();
            emailid = i.getStringExtra("emailid");

            editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            editTextSubject = (EditText) findViewById(R.id.editTextSubject);
            editTextMessage = (EditText) findViewById(R.id.editTextMessage);

            buttonSend = (Button) findViewById(R.id.buttonSend);

            //Adding click listener
            buttonSend.setOnClickListener(this);
            editTextEmail.setText(emailid.toString());
            editTextEmail.setTypeface(font);
            editTextSubject.setTypeface(font);
            editTextMessage.setTypeface(font);
            buttonSend.setTypeface(font);
        }

    }
    //Purpose: Used for sending an email
    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        if(!email.isEmpty() && !subject.isEmpty() && !message.isEmpty()) {

            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()) {
                editTextEmail.setError("Invalid Email");
                return;
            }else{
                SendMail sm = new SendMail(this, email, subject, message);
                sm.execute();

                editTextSubject.setText("");
                editTextMessage.setText("");
            }
        }else{
            Toast.makeText(SendMailActivity.this,"One or more fields are empty",Toast.LENGTH_SHORT).show();
        }

    }

    //Purpose: Used for sending an email
    @Override
    public void onClick(View view) {
        sendEmail();
    }

    // Used for checking network service
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
