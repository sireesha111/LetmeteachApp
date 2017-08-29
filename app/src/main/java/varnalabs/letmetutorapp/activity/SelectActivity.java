package varnalabs.letmetutorapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import varnalabs.letmetutorapp.R;
import me.drakeet.materialdialog.MaterialDialog;

public class SelectActivity extends AppCompatActivity {

    LinearLayout layout_student, layout_teacher;
    TextView txtstu, txttutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //Used for Typeface fontstyles
        final Typeface font = Typeface.createFromAsset(getAssets(),
                "fontRegular.ttf");



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

            layout_student = (LinearLayout) findViewById(R.id.layout_student);

            txttutor = (TextView) findViewById(R.id.txttutor);
            txtstu = (TextView) findViewById(R.id.txtstu);

            txttutor.setTypeface(font, Typeface.BOLD);
            txtstu.setTypeface(font, Typeface.BOLD);

            layout_teacher = (LinearLayout) findViewById(R.id.layout_teacher);

            layout_student.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_student = new Intent(getApplicationContext(), StudentLoginActivity.class);
                    //intent_student.putExtra("teach", "Students");

                    Context  ctx   = getApplicationContext();
                    SharedPreferences sharedPreferences = ctx.getSharedPreferences("selectpref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Students", "Students");
                    editor.commit();

                    startActivity(intent_student);
                    finish();
                }
            });

            layout_teacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_student = new Intent(getApplicationContext(), TeacherLoginActivity.class);
                   // intent_student.putExtra("teach", "Teacher");
                    Context  ctx   = getApplicationContext();
                    SharedPreferences sharedPreferences = ctx.getSharedPreferences("selectpref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("teach", "Teacher");
                    editor.commit();
                    startActivity(intent_student);
                    finish();
                }
            });


        }
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
        new AlertDialog.Builder(this).setTitle("Sample")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }


}
