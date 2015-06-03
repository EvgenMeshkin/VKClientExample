package by.android.evgen.vkclientexample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.activity.FriendsActivity;
import by.android.evgen.vkclientexample.helper.GetDialogs;
import by.android.evgen.vkclientexample.helper.GetFriends;
import by.android.evgen.vkclientexample.helper.VkOAuthHelper;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.users.Users;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by Yauheni_Meshkin on 6/3/2015.
 */
public class DialogFragment extends Fragment {


    private RecyclerView mRecyclerViewDialogs;
    private SwipeRefreshLayout mSwipeRefreshLayoutDialogs;
    private UserData mMainUserData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_dialog, null);

        mRecyclerViewDialogs = (RecyclerView) content.findViewById(R.id.dialogs_view);
        RecyclerView.LayoutManager layoutManagerDialogs = new LinearLayoutManager(getActivity());
        mRecyclerViewDialogs.setLayoutManager(layoutManagerDialogs);
        mSwipeRefreshLayoutDialogs = (SwipeRefreshLayout) content.findViewById(R.id.dialog_container);
        new SpringParser().executeInThread(new ISpringCallback() {
            @Override
            public void onDataLoadStart() {

            }

            @Override
            public void onDone(Object dataUser) {
                Users user = (Users) dataUser;
                mMainUserData = new UserData(user.response[0].id, user.response[0].first_name, user.response[0].photo_200_orig);
                new GetDialogs(getActivity(), mMainUserData, mRecyclerViewDialogs, mSwipeRefreshLayoutDialogs);

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


        return content;
    }

}