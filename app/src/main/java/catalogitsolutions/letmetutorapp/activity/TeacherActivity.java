package catalogitsolutions.letmetutorapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;

import catalogitsolutions.letmetutorapp.R;
import catalogitsolutions.letmetutorapp.app.AppConfig;
import catalogitsolutions.letmetutorapp.app.AppController;
import catalogitsolutions.letmetutorapp.fragment.TeacherDashboardFragment;
import catalogitsolutions.letmetutorapp.fragment.TeacherProfileFragment;
import catalogitsolutions.letmetutorapp.fragment.TeacherSearchFragment;
import me.drakeet.materialdialog.MaterialDialog;

public class TeacherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MENU_ITEMS = 12;
    private final ArrayList<View> mMenuItems = new ArrayList<>(MENU_ITEMS);
    SessionManager manager;
    Typeface font_text;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    private static final String TAG = TeacherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        manager = new SessionManager();
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

            TextView idLogo = (TextView) toolbar.findViewById(R.id.idLogo);
            idLogo.setText("Sample - Teacher");
            idLogo.setTypeface(font_text);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            // Used for updating the main content by replacing fragments
            TeacherDashboardFragment fragment = new TeacherDashboardFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

            final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            final Menu navMenu = navigationView.getMenu();
            navigationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    for (int i = 0, length = navMenu.size(); i < length; i++) {
                        final MenuItem item = navMenu.getItem(i);
                        navigationView.findViewsWithText(mMenuItems, item.getTitle(), View.FIND_VIEWS_WITH_TEXT);
                    }

                    for (final View menuItem : mMenuItems) {
                        ((TextView) menuItem).setTypeface(font_text, Typeface.NORMAL);
                    }
                }
            });

            // Navigation view header
            View header = navigationView.getHeaderView(0);

            TextView txtUsername = (TextView) header.findViewById(R.id.txtUsername);
            TextView logoid = (TextView) header.findViewById(R.id.logoid);
            SharedPreferences sharedPreferences = getSharedPreferences(AppConfig.SHARED_TEACHER_PREF_NAME, Context.MODE_PRIVATE);
            String username = sharedPreferences.getString(AppConfig.TEACHERUSER_SHARED_PREF, "Not Available");


            txtUsername.setText(username);
            logoid.setTypeface(font_text);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //noinspection SimplifiableIfStatement
            logout();
        }

        return super.onOptionsItemSelected(item);
    }


    //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
    // This method will trigger on item Click of navigation menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int navitem_id = item.getItemId();

        if (navitem_id == R.id.item_navigation_drawer_dashboard) {
            TeacherDashboardFragment fragment = new TeacherDashboardFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (navitem_id == R.id.item_navigation_drawer_profile) {
            TeacherProfileFragment fragment = new TeacherProfileFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (navitem_id == R.id.item_navigation_drawer_search) {
            TeacherSearchFragment fragment = new TeacherSearchFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (navitem_id == R.id.item_navigation_drawer_share) {
            // launch new intent instead of loading fragment


        } else if (navitem_id == R.id.item_navigation_drawer_contactus) {
            // launch new intent instead of loading fragment


        }   else if (navitem_id == R.id.item_navigation_drawer_logout) {
            logout();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //Purpose: Used for logout the Appliaction
    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPreferences preferences = getSharedPreferences(AppConfig.SHARED_TEACHER_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(AppConfig.LOGGEDIN_SHARED_PREF, false);
                        editor.putString(AppConfig.TEACHERUSER_SHARED_PREF, "");
                        editor.commit();
                        manager.setPreferences1(TeacherActivity.this, "status", "0");
                        finish();
                        Intent intent = new Intent(TeacherActivity.this, TeacherLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawer != null) {
            toggle.syncState();
        }
    }
    //Purpose: Used for screen orientation (landscape,portarait)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        String s = "";

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            s = "Landscape orientation\n";
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            s = "Portrait orientation\n";
        }

        s += "onConfigurationChanged() was called " + ((AppController) getApplicationContext()).inc() + " times";
    }

    protected OnBackPressedListener onBackPressedListener;

    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }
}
