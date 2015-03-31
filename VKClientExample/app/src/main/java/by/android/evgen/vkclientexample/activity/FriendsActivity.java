package by.android.evgen.vkclientexample.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.helper.VkOAuthHelper;
import by.android.evgen.vkclientexample.adapter.VkFriendsAdapter;
import by.android.evgen.vkclientexample.model.dialog.Result;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by evgen on 25.03.2015.
 */
public class FriendsActivity extends ActionBarActivity implements ISpringCallback<Result>{


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
    public void onDone(Result data) {
        mRecyclerView.setAdapter(new VkFriendsAdapter(this, data.response.items));
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(Exception e) {

    }
}
