package by.android.evgen.vkclientexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.R;
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
public class FriendsActivity extends ActionBarActivity implements ISpringCallback<Result>{


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String USER_DATA ="user_data";
    private UserData mUserData;
    private UserData mMainUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        mRecyclerView = (RecyclerView)findViewById(R.id.friends_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new SpringParser().executeInThread(FriendsActivity.this, VkOAuthHelper.sign(Api.FRIENDS_GET), Result.class);
            }
        });
        new SpringParser().executeInThread(this, VkOAuthHelper.sign(Api.FRIENDS_GET), Result.class);

    }

    @Override
    public void onDataLoadStart() {

    }

    @Override
    public void onDone(final Result data) {

        new SpringParser().executeInThread(new ISpringCallback() {
            @Override
            public void onDataLoadStart() {

            }

            @Override
            public void onDone(Object dataUser) {
                Users user = (Users)dataUser;
                mMainUserData = new UserData(user.response[0].id, user.response[0].first_name, user.response[0].photo_200_orig);
                mRecyclerView.setAdapter(new VkFriendsAdapter(FriendsActivity.this, data.response.items));
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(FriendsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.d("*************************", view.getTag().toString());
                                Items user = (Items)view.getTag();
                                mUserData = new UserData(user.id, user.first_name, user.photo_200_orig);
                                Intent intent = new Intent();
                                intent.setClass(FriendsActivity.this, MessageActivity.class);
                                intent.putExtra(MessageActivity.USER_ID, mUserData);
                                intent.putExtra(USER_DATA, mMainUserData);
                                startActivity(intent);
                            }
                        })
                );
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        }, VkOAuthHelper.sign(Api.USER_GET), Users.class);


    }

    @Override
    public void onError(Exception e) {

    }
}
