package by.android.evgen.vkclientexample.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.activity.FriendsActivity;
import by.android.evgen.vkclientexample.activity.MessageActivity;
import by.android.evgen.vkclientexample.adapter.VkFriendsAdapter;
import by.android.evgen.vkclientexample.listener.RecyclerItemClickListener;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.dialog.Items;
import by.android.evgen.vkclientexample.model.dialog.Result;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by evgen on 03.04.2015.
 */
public class GetFriends implements ISpringCallback<Result> {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private UserData mUserData;
    private UserData mMainUserData;
    private Context mContext;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    public static final int COUNT = 20;
    private VkFriendsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private List<Items> mList;

    public GetFriends(Context mContext, RecyclerView mRecyclerView, SwipeRefreshLayout mSwipeRefreshLayout, UserData mMainUserData, LinearLayoutManager mLayoutManager) {
        this.mRecyclerView = mRecyclerView;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.mUserData = mUserData;
        this.mMainUserData = mMainUserData;
        this.mContext = mContext;
        this.mLayoutManager = mLayoutManager;
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new SpringParser().executeInThread(GetFriends.this, VkOAuthHelper.sign(Api.FRIENDS_GET), Result.class);
            }
        });
        new SpringParser().executeInThread(this, VkOAuthHelper.sign(getUrl(COUNT, 0)), Result.class);
    }

    @Override
    public void onDataLoadStart() {

    }

    @Override
    public void onDone(Result data) {
        Items[] dataArray = data.response.items;
        mList = new ArrayList<>(Arrays.asList(dataArray));
        mAdapter = new VkFriendsAdapter(mContext, mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d("**********************", view.getTag().toString());
                        Items user = (Items)view.getTag();
                        mUserData = new UserData(user.id, user.first_name, user.photo_200_orig);
                        Intent intent = new Intent();
                        intent.setClass(mContext, MessageActivity.class);
                        intent.putExtra(MessageActivity.USER_ID, mUserData);
                        intent.putExtra(FriendsActivity.USER_DATA, mMainUserData);
                        mContext.startActivity(intent);
                    }
                })
        );

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int count = mAdapter.getItemCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    mList.add(null);
//                    mAdapter.notifyItemInserted(mList.size());
                    new SpringParser().executeInThread(new ISpringCallback<Result>() {
                        @Override
                        public void onDataLoadStart() {

                        }

                        @Override
                        public void onDone(Result data) {
                            Items[] dataArray = data.response.items;

//                            if (data != null && data.size() == COUNT) {
//                                isPagingEnabled = true;
//                                listView.addFooterView(footerProgress, null, false);
//                            } else {
//                                isPagingEnabled = false;
//                                listView.removeFooterView(footerProgress);
//                            }
//                            mList.remove(mList.size() - 1);
//                            mAdapter.notifyItemRemoved(mList.size());
                            if (data != null) {
                                List<Items> newList = new ArrayList<Items>(Arrays.asList(dataArray));
                                mList.addAll(newList);
                            }
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    }, VkOAuthHelper.sign(getUrl(COUNT, count)), Result.class);

                    loading = true;
                }

            }
        });

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(Exception e) {

    }

    private String getUrl(int count, int offset) {
        return Api.FRIENDS_GET + "&count="+count+"&offset="+offset;
    }

}
