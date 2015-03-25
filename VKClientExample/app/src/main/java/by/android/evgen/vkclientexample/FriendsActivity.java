package by.android.evgen.vkclientexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import by.android.evgen.vkclientexample.adapter.VkFriendsAdapter;
import by.android.evgen.vkclientexample.model.Response;
import by.android.evgen.vkclientexample.model.Result;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by evgen on 25.03.2015.
 */
public class FriendsActivity extends ActionBarActivity implements ISpringCallback<Result>{


    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        mRecyclerView = (RecyclerView)findViewById(R.id.friends_view);
//        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        new SpringParser().executeInThread(this, VkOAuthHelper.sign(Api.FRIENDS_GET));

    }

    @Override
    public void onDataLoadStart() {

    }

    @Override
    public void onDone(Result data) {
          Log.d("OnDone", "************************************************************************" + data.response.items[0].first_name + data.response.count);
        //  VkFriendsAdapter adapter = new VkFriendsAdapter(this, data.response.items);
          mRecyclerView.setAdapter(new VkFriendsAdapter(this, data.response.items));
    }

    @Override
    public void onError(Exception e) {

    }
}
