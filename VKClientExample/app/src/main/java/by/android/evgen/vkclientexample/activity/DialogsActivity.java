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
import by.android.evgen.vkclientexample.adapter.VkDialogsAdapter;
import by.android.evgen.vkclientexample.listener.RecyclerItemClickListener;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.dialog.Result;
import by.android.evgen.vkclientexample.model.users.Response;
import by.android.evgen.vkclientexample.model.users.Users;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by evgen on 28.03.2015.
 */
public class DialogsActivity extends ActionBarActivity implements ISpringCallback<Result> {


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String USER_DATA ="user_data";
    private UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);
        mRecyclerView = (RecyclerView)findViewById(R.id.friends_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new SpringParser().executeInThread(DialogsActivity.this, VkOAuthHelper.sign(Api.DIALOG_GET), Result.class);
            }
        });
        new SpringParser().executeInThread(this, VkOAuthHelper.sign(Api.DIALOG_GET), Result.class);

    }

    @Override
    public void onDataLoadStart() {

    }

    @Override
    public void onDone(final Result data) {
        String strId = null;
        for (int i = 0; i < data.response.items.length; i++) {
            strId = strId + data.response.items[i].message.user_id + "," ;
        }
        new SpringParser().executeInThread(new ISpringCallback() {
            @Override
            public void onDataLoadStart() {

            }

            @Override
            public void onDone(Object dataUser) {
                Users user = (Users)dataUser;
 //              user.save();
                mRecyclerView.setAdapter(new VkDialogsAdapter(DialogsActivity.this, data.response.items, user));
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(DialogsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.d("*************************", view.getTag().toString());
                                Response user = (Response)view.getTag();
                                mUserData = new UserData(user.id, user.first_name, user.photo_200_orig);
                                Intent intent = new Intent();
                                intent.setClass(DialogsActivity.this, MessageActivity.class);
                                intent.putExtra(MessageActivity.USER_ID, mUserData);
                                intent.putExtra(USER_DATA, getIntent().getParcelableExtra(USER_DATA));
                                startActivity(intent);
                            }
                        })
                );

            }

            @Override
            public void onError(Exception e) {

            }
        }, VkOAuthHelper.sign(Api.USERS_GET + strId), Users.class);

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(Exception e) {

    }
}

