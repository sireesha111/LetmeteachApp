
package catalogitsolutions.letmetutorapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.sacot41.scviewpager.DotsView;
import com.dev.sacot41.scviewpager.SCPositionAnimation;
import com.dev.sacot41.scviewpager.SCViewAnimation;
import com.dev.sacot41.scviewpager.SCViewAnimationUtil;
import com.dev.sacot41.scviewpager.SCViewPager;
import com.dev.sacot41.scviewpager.SCViewPagerAdapter;

import catalogitsolutions.letmetutorapp.R;
import catalogitsolutions.letmetutorapp.activity.StudentActivity;
import catalogitsolutions.letmetutorapp.app.AppConfig;
import me.drakeet.materialdialog.MaterialDialog;


public class StudentDashboardFragment extends Fragment implements StudentActivity.OnBackPressedListener {

    Activity context;
    View view_dashboard;
    private static final String TAG = StudentDashboardFragment.class.getSimpleName();

    Typeface font_text;

    private static final int NUM_PAGES = 5;

    private SCViewPager mViewPager;
    private SCViewPagerAdapter mPageAdapter;
    private DotsView mDotsView;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((StudentActivity) getActivity()).setOnBackPressedListener(this);
    }

    public void doBack() {
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle("Let Me Teach")
                .setMessage("Are you sure to close the App?")
                .setPositiveButton(
                        "OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                startActivity(intent);
                                context.finish();
                                System.exit(0);
                            }
                        }
                )
                .setNegativeButton(
                        "CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();

                            }
                        }
                )
                .show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_dashboard = inflater.inflate(R.layout.fragment_dashboard, container, false);
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

            /*TextView main = (TextView) view_profile.findViewById(R.id.main);
            TextView main1 = (TextView) view_profile.findViewById(R.id.main1);*/

            SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.SHARED_TEACHER_PREF_NAME, Context.MODE_PRIVATE);
            String username = sharedPreferences.getString(AppConfig.STUDENTSUSER_SHARED_PREF, "Not Available");

            font_text = Typeface.createFromAsset(context.getAssets(),
                    "fontRegular.ttf");

            /*String intcapparent = toInitCap(username);
            main.setText(intcapparent);
            main.setTypeface(font_text);
            main1.setTypeface(font_text);*/

            mViewPager = (SCViewPager) view_dashboard.findViewById(R.id.viewpager_main_activity);
            mDotsView = (DotsView) view_dashboard.findViewById(R.id.dotsview_main);
            mDotsView.setDotRessource(R.drawable.dot_selected, R.drawable.dot_unselected);
            mDotsView.setNumberOfPage(NUM_PAGES);

            mPageAdapter = new SCViewPagerAdapter(getFragmentManager());
            mPageAdapter.setNumberOfPage(NUM_PAGES);
            mPageAdapter.setFragmentBackgroundColor(R.color.theme_100);
            mViewPager.setAdapter(mPageAdapter);

            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    mDotsView.selectDot(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            final Point size = SCViewAnimationUtil.getDisplaySize(getActivity());

            View nameTag = view_dashboard.findViewById(R.id.imageview_main_activity_name_tag);
            SCViewAnimation nameTagAnimation = new SCViewAnimation(nameTag);
            nameTagAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 0,0,-size.y/2));
            mViewPager.addAnimation(nameTagAnimation);

            View currentlyWork = view_dashboard.findViewById(R.id.imageview_main_activity_currently_work);
            SCViewAnimation currentlyWorkAnimation = new SCViewAnimation(currentlyWork);
            currentlyWorkAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 0, -size.x, 0));
            mViewPager.addAnimation(currentlyWorkAnimation);

            View atSkex = view_dashboard.findViewById(R.id.imageview_main_activity_at_skex);
            SCViewAnimationUtil.prepareViewToGetSize(atSkex);
            SCViewAnimation atSkexAnimation = new SCViewAnimation(atSkex);
            atSkexAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 0, 0, -( size.y - atSkex.getHeight() )));
            atSkexAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 1, -size.x, 0));
            mViewPager.addAnimation(atSkexAnimation);

            View mobileView = view_dashboard.findViewById(R.id.imageview_main_activity_mobile);
            SCViewAnimation mobileAnimation = new SCViewAnimation(mobileView);
            mobileAnimation.startToPosition((int)(size.x*1.5), null);
            mobileAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 0, -(int)(size.x*1.5), 0));
            mobileAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 1, -(int)(size.x*1.5), 0));
            mViewPager.addAnimation(mobileAnimation);

            View djangoView = view_dashboard.findViewById(R.id.imageview_main_activity_django_python);
            SCViewAnimation djangoAnimation = new SCViewAnimation(djangoView);
            djangoAnimation.startToPosition(null, -size.y);
            djangoAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 0, 0, size.y));
            djangoAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 1, 0, size.y));
            mViewPager.addAnimation(djangoAnimation);

            View commonlyView = view_dashboard.findViewById(R.id.imageview_main_activity_commonly);
            SCViewAnimation commonlyAnimation = new SCViewAnimation(commonlyView);
            commonlyAnimation.startToPosition(size.x, null);
            commonlyAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 0, -size.x, 0));
            commonlyAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 1, -size.x, 0));
            mViewPager.addAnimation(commonlyAnimation);

            View butView = view_dashboard.findViewById(R.id.imageview_main_activity_but);
            SCViewAnimation butAnimation = new SCViewAnimation(butView);
            butAnimation.startToPosition(size.x, null);
            butAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 1, -size.x,0));
            butAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 2, -size.x,0));
            mViewPager.addAnimation(butAnimation);

            View diplomeView = view_dashboard.findViewById(R.id.imageview_main_activity_diploma);
            SCViewAnimation diplomeAnimation = new SCViewAnimation(diplomeView);
            diplomeAnimation.startToPosition((size.x *2), null);
            diplomeAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 1, -size.x*2,0));
            diplomeAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 2, -size.x*2 ,0));
            mViewPager.addAnimation(diplomeAnimation);

            View whyView = view_dashboard.findViewById(R.id.imageview_main_activity_why);
            SCViewAnimation whyAnimation = new SCViewAnimation(whyView);
            whyAnimation.startToPosition(size.x, null);
            whyAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 1, -size.x, 0));
            whyAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 2, -size.x, 0));
            mViewPager.addAnimation(whyAnimation);

            View futureView = view_dashboard.findViewById(R.id.imageview_main_future);
            SCViewAnimation futureAnimation = new SCViewAnimation(futureView);
            futureAnimation.startToPosition(null, -size.y);
            futureAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 2, 0, size.y));
            futureAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 3, -size.x, 0));
            mViewPager.addAnimation(futureAnimation);

            View arduinoView = view_dashboard.findViewById(R.id.imageview_main_arduino);
            SCViewAnimation arduinoAnimation = new SCViewAnimation(arduinoView);
            arduinoAnimation.startToPosition(size.x * 2, null);
            arduinoAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 2, - size.x *2, 0));
            arduinoAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 3, - size.x, 0));
            mViewPager.addAnimation(arduinoAnimation);

            View raspberryView = view_dashboard.findViewById(R.id.imageview_main_raspberry_pi);
            SCViewAnimation raspberryAnimation = new SCViewAnimation(raspberryView);
            raspberryAnimation.startToPosition(-size.x, null);
            raspberryAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 2, size.x, 0));
            raspberryAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 3, -size.x, 0));
            mViewPager.addAnimation(raspberryAnimation);

            View connectedDeviceView = view_dashboard.findViewById(R.id.imageview_main_connected_device);
            SCViewAnimation connectedDeviceAnimation = new SCViewAnimation(connectedDeviceView);
            connectedDeviceAnimation.startToPosition((int)(size.x *1.5), null);
            connectedDeviceAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 2, -(int) (size.x * 1.5), 0));
            connectedDeviceAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 3,  - size.x, 0));
            mViewPager.addAnimation(connectedDeviceAnimation);

            View checkOutView = view_dashboard.findViewById(R.id.imageview_main_check_out);
            SCViewAnimation checkOutAnimation = new SCViewAnimation(checkOutView);
            checkOutAnimation.startToPosition(size.x, null);
            checkOutAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 3, -size.x, 0));
            mViewPager.addAnimation(checkOutAnimation);

            View linkedinView = view_dashboard.findViewById(R.id.textview_main_linkedin_link);
            SCViewAnimation linkedinAnimation = new SCViewAnimation(linkedinView);
            linkedinAnimation.startToPosition(size.x, null);
            linkedinAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 3, -size.x, 0));
            mViewPager.addAnimation(linkedinAnimation);

            View githubView = view_dashboard.findViewById(R.id.textview_main_github_link);
            SCViewAnimation githubAnimation = new SCViewAnimation(githubView);
            githubAnimation.startToPosition(size.x, null);
            githubAnimation.addPageAnimation(new SCPositionAnimation(getActivity(), 3, -size.x, 0));
            mViewPager.addAnimation(githubAnimation);


        }
        return view_dashboard;
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
