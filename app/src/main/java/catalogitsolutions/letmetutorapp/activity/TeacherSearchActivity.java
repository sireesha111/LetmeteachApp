package catalogitsolutions.letmetutorapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import catalogitsolutions.letmetutorapp.R;
import catalogitsolutions.letmetutorapp.adapter.SearchStudentListAdapter;
import catalogitsolutions.letmetutorapp.app.AppConfig;
import catalogitsolutions.letmetutorapp.app.AppController;
import catalogitsolutions.letmetutorapp.fragment.TeacherSearchFragment;
import catalogitsolutions.letmetutorapp.model.SearchStudent;
import me.drakeet.materialdialog.MaterialDialog;

public class TeacherSearchActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ListView listView;
    private SearchStudentListAdapter searchStudentListAdapter;
    private List<SearchStudent> birthdaysList = new ArrayList<SearchStudent>();
    LinearLayout emptyElement;
    ProgressDialog PD;
    private static final String TAG = TeacherSearchActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherserach);

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
        idLogo.setText("Student Search");
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

            emptyElement = (LinearLayout) findViewById(R.id.emptyElement);

            PD = new ProgressDialog(TeacherSearchActivity.this);
            PD.setMessage("Loading...Please wait...");
            PD.setCancelable(false);

            listView = (ListView) findViewById(R.id.list);
            searchStudentListAdapter = new SearchStudentListAdapter(TeacherSearchActivity.this, birthdaysList);
            listView.setAdapter(searchStudentListAdapter);

            listView.setEmptyView(findViewById(R.id.emptyElement));
            emptyElement.setVisibility(View.GONE);

            Intent i = getIntent();
            String strclass = i.getStringExtra("strclass");
            String strsubject = i.getStringExtra("strsubject");
            String strcity = i.getStringExtra("strcity");
            String strzone = i.getStringExtra("strzone");


            getTeacherSearch(strclass, strsubject, strcity, strzone);




        }
    }

    private void getTeacherSearch(String strclass, String strsubject, String strcity, String strzone) {
        PD.show();

        String url_teacher = AppConfig.SEARCHTEACHER_URL +"?class="+strclass+"&subject="+strsubject+"&city="+strcity+"&zone="+strzone;
        Log.d(TAG, url_teacher);

        birthdaysList.clear();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url_teacher, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                listView.setEmptyView(findViewById(R.id.emptyElement));
                emptyElement.setVisibility(View.GONE);

                try {
                    int success = response.getInt("success");

                    if (success == 1) {
                        JSONArray ja = response.getJSONArray("birth_days");

                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject obj = ja.getJSONObject(i);

                            SearchStudent birhtdays = new SearchStudent();

                            birhtdays.setSt_name(obj.getString("st_name"));
                            birhtdays.setSt_photo(obj.getString("st_photo"));
                            birhtdays.setSt_code(obj.getString("st_code"));
                            birhtdays.setSt_age(obj.getString("st_age"));
                            birhtdays.setSt_gander(obj.getString("st_gander"));


                            birthdaysList.add(birhtdays);
                            PD.dismiss();

                        }

                    } else if (success == 0) {
                        listView.setEmptyView(findViewById(R.id.emptyElement));
                        emptyElement.setVisibility(View.VISIBLE);
                        PD.dismiss();
                    }


                } catch (JSONException jsn_exception) {
                    jsn_exception.printStackTrace();
                }

                searchStudentListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
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
