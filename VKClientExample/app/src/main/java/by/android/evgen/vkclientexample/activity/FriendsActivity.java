package by.android.evgen.vkclientexample.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TabHost;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.adapter.FragmentPagerSheduleAdapter;
import by.android.evgen.vkclientexample.fragment.DialogFragment;
import by.android.evgen.vkclientexample.fragment.FriendsFragment;
import by.android.evgen.vkclientexample.helper.GetDialogs;
import by.android.evgen.vkclientexample.helper.GetFriends;
import by.android.evgen.vkclientexample.helper.VkOAuthHelper;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.users.Users;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;
import by.android.evgen.vkclientexample.view.SlidingTabLayout;

/**
 * Created by evgen on 25.03.2015.
 */
public class FriendsActivity extends AppCompatActivity {

    public static final String TAG = FriendsActivity.class.getSimpleName();
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String EMPTY = "";
    String SENDER_ID = "89313241901";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String USER_DATA = "user_data";
    private UserData mMainUserData;
    private RecyclerView mRecyclerViewDialogs;
    private SwipeRefreshLayout mSwipeRefreshLayoutDialogs;
    GoogleCloudMessaging gcm;
    String regid;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Toolbar toolbar = (Toolbar) findViewById(R.id.awesome_toolbar);
        setSupportActionBar(toolbar);

        List<Fragment> mTabs = new ArrayList<>();
        mTabs.add(new FriendsFragment());
        mTabs.add(new DialogFragment());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new FragmentPagerSheduleAdapter(getSupportFragmentManager(), mTabs));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }

            @Override
            public int getDividerColor(int position) {
                return Color.WHITE;
            }

        });


    }









        /*mRecyclerView = (RecyclerView) findViewById(R.id.friends_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        TabHost tabs = (TabHost) findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec(getString(R.string.tagoune));
        spec.setContent(R.id.tab1);
        spec.setIndicator(getString(R.string.friends));
        tabs.addTab(spec);
        spec = tabs.newTabSpec(getString(R.string.tagTwo));
        spec.setContent(R.id.tab2);
        spec.setIndicator(getString(R.string.dialogs));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mRecyclerViewDialogs = (RecyclerView) findViewById(R.id.dialogs_view);
        RecyclerView.LayoutManager layoutManagerDialogs = new LinearLayoutManager(this);
        mRecyclerViewDialogs.setLayoutManager(layoutManagerDialogs);
        mSwipeRefreshLayoutDialogs = (SwipeRefreshLayout) findViewById(R.id.dialog_container);
        new SpringParser().executeInThread(new ISpringCallback() {
            @Override
            public void onDataLoadStart() {

            }

            @Override
            public void onDone(Object dataUser) {
                Users user = (Users) dataUser;
                mMainUserData = new UserData(user.response[0].id, user.response[0].first_name, user.response[0].photo_200_orig);
                new GetFriends(FriendsActivity.this, mRecyclerView, mSwipeRefreshLayout, mMainUserData, layoutManager);
                new GetDialogs(FriendsActivity.this, mMainUserData, mRecyclerViewDialogs, mSwipeRefreshLayoutDialogs);

                new SpringParser().executeInThread(new ISpringCallback() {
                    @Override
                    public void onDataLoadStart() {
                    }

                    @Override
                    public void onDone(Object dataUser) {
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                }, VkOAuthHelper.sign(Api.REGISTER_DEVICE + VkOAuthHelper.sToken), String.class);

            }

            @Override
            public void onError(Exception e) {

            }
        }, VkOAuthHelper.sign(Api.USER_GET), Users.class);

        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(this);

            if (regid.isEmpty()) {
                registerInBackground();
            }

        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, EMPTY);
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return EMPTY;
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return EMPTY;
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(FriendsActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(FriendsActivity.this);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    sendRegistrationIdToBackend();
                    storeRegistrationId(FriendsActivity.this, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.i(TAG, "GOOD");
            }
        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private void sendRegistrationIdToBackend() {
        // Your implementation here.
    }*/

}
