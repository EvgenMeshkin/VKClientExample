package by.android.evgen.vkclientexample.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.activity.FriendsActivity;
import by.android.evgen.vkclientexample.activity.MessageActivity;
import by.android.evgen.vkclientexample.adapter.VkDialogsAdapter;
import by.android.evgen.vkclientexample.listener.RecyclerItemClickListener;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.dialog.Result;
import by.android.evgen.vkclientexample.model.users.Response;
import by.android.evgen.vkclientexample.model.users.Users;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by evgen on 04.04.2015.
 */
public class GetDialogs {

    private UserData mMainUserData;
    private RecyclerView mRecyclerViewDialogs;
    private SwipeRefreshLayout mSwipeRefreshLayoutDialogs;
    private UserData mUserDataDialogs;
    private Context mContext;

    public GetDialogs(final Context mContext, final UserData mMainUserData, final RecyclerView mRecyclerViewDialogs, final SwipeRefreshLayout mSwipeRefreshLayoutDialogs) {
        this.mMainUserData = mMainUserData;
        this.mRecyclerViewDialogs = mRecyclerViewDialogs;
        this.mSwipeRefreshLayoutDialogs = mSwipeRefreshLayoutDialogs;
        this.mContext = mContext;
        new SpringParser().executeInThread(new ISpringCallback<Result>() {
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
                        mRecyclerViewDialogs.setAdapter(new VkDialogsAdapter(mContext, data.response.items, user));
                        mRecyclerViewDialogs.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Response user = (Response)view.getTag();
                                        mUserDataDialogs = new UserData(user.id, user.first_name, user.photo_200_orig);
                                        Intent intent = new Intent();
                                        intent.setClass(mContext, MessageActivity.class);
                                        intent.putExtra(MessageActivity.USER_ID, mUserDataDialogs);
                                        intent.putExtra(FriendsActivity.USER_DATA, mMainUserData);
                                        mContext.startActivity(intent);
                                    }
                                })
                        );

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                }, VkOAuthHelper.sign(Api.USERS_GET + strId), Users.class);

                if (mSwipeRefreshLayoutDialogs.isRefreshing()) {
                    mSwipeRefreshLayoutDialogs.setRefreshing(false);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        }, VkOAuthHelper.sign(Api.DIALOG_GET), Result.class);

    }

}
