package by.android.evgen.vkclientexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.adapter.VkDialogsAdapter;
import by.android.evgen.vkclientexample.helper.GetDialogs;
import by.android.evgen.vkclientexample.helper.GetFriends;
import by.android.evgen.vkclientexample.helper.VkOAuthHelper;
import by.android.evgen.vkclientexample.adapter.VkFriendsAdapter;
import by.android.evgen.vkclientexample.listener.RecyclerItemClickListener;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.dialog.Items;
import by.android.evgen.vkclientexample.model.dialog.Result;
import by.android.evgen.vkclientexample.model.users.Response;
import by.android.evgen.vkclientexample.model.users.Users;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by evgen on 25.03.2015.
 */
public class FriendsActivity extends ActionBarActivity {


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String USER_DATA = "user_data";
    private UserData mUserData;
    private UserData mMainUserData;
    private RecyclerView mRecyclerViewDialogs;
    private SwipeRefreshLayout mSwipeRefreshLayoutDialogs;
    private UserData mUserDataDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        mRecyclerView = (RecyclerView) findViewById(R.id.friends_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        TabHost tabs = (TabHost) findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Friends");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Dialogs");
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
            }

            @Override
            public void onError(Exception e) {

            }
        }, VkOAuthHelper.sign(Api.USER_GET), Users.class);
    }


}
