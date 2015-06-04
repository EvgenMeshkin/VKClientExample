package by.android.evgen.vkclientexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.adapter.VkHistoryAdapter;
import by.android.evgen.vkclientexample.helper.VkOAuthHelper;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.history.History;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by evgen on 28.03.2015.
 */
public class MessageActivity extends ActionBarActivity implements ISpringCallback<History> {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String USER_ID = "user id";
    private UserData mUserFrom;
    private UserData mUser;
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.awesome_toolbar);
        setSupportActionBar(toolbar);
        mUserFrom = (UserData)this.getIntent().getParcelableExtra(USER_ID);
        mRecyclerView = (RecyclerView)findViewById(R.id.friends_view);
        ImageView userFrom = (ImageView)findViewById(R.id.imageFrom);
        Picasso.with(this).load(mUserFrom.getUser_image()).into(userFrom);
        mUser = (UserData)getIntent().getParcelableExtra(FriendsActivity.USER_DATA);
        /*ImageView user = (ImageView)findViewById(R.id.imageUser);
        Picasso.with(this).load(mUser.getUser_image()).into(user);*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        mEdit = (EditText)findViewById(R.id.editText);
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
               /* String editMessage = mEdit.getText().toString();
                if (!TextUtils.isEmpty(editMessage)) {
                    sendMessage(editMessage);
                    return true;
                }*/
                return false;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new SpringParser().executeInThread(MessageActivity.this, VkOAuthHelper.sign(Api.HISTORY_GET + mUserFrom.getUser_id()), History.class);
            }
        });
        new SpringParser().executeInThread(MessageActivity.this, VkOAuthHelper.sign(Api.HISTORY_GET + mUserFrom.getUser_id()), History.class);
    }

    @Override
    public void onDataLoadStart() {

    }

    @Override
    public void onDone(History data) {
        mRecyclerView.setAdapter(new VkHistoryAdapter(MessageActivity.this, data.response.items, mUser, mUserFrom));
    }

    @Override
    public void onError(Exception e) {

    }

    public void send(View view) {
        String editMessage = mEdit.getText().toString();
        if (!TextUtils.isEmpty(editMessage)) {
           sendMessage(editMessage);
            mEdit.setText("");
        }
    }

    private void sendMessage(String message) {
        new SpringParser().executeInThread(new ISpringCallback() {
            @Override
            public void onDataLoadStart() {

            }

            @Override
            public void onDone(Object data) {
                new SpringParser().executeInThread(MessageActivity.this, VkOAuthHelper.sign(Api.HISTORY_GET + mUserFrom.getUser_id()), History.class);
            }

            @Override
            public void onError(Exception e) {

            }
        }, VkOAuthHelper.sign(Api.getSendUrl(mUserFrom.getUser_id(), message)), String.class);
    }
}
