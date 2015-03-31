package by.android.evgen.vkclientexample.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.adapter.VkDialogsAdapter;
import by.android.evgen.vkclientexample.helper.VkOAuthHelper;
import by.android.evgen.vkclientexample.listener.RecyclerItemClickListener;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.users.Users;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;


public class MainActivity extends ActionBarActivity {
    public static final int REQUEST_CODE_VK = 0;
    private Button mFriends;
    private Button mMessage;
    private UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFriends = (Button) findViewById(R.id.friends);
        mMessage = (Button) findViewById(R.id.message);
        mFriends.setEnabled(false);
        mMessage.setEnabled(false);
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this, VkLoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_VK);
    }

    public void onFriendsClick(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    public void onShowDialogs(View view) {
        Intent intent = new Intent(this, DialogsActivity.class);
        intent.putExtra(DialogsActivity.USER_DATA, mUserData);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VK && resultCode == RESULT_OK)  {
            Toast.makeText(this, "You logged in " + RESULT_OK, Toast.LENGTH_SHORT).show();

            new SpringParser().executeInThread(new ISpringCallback() {
                @Override
                public void onDataLoadStart() {

                }

                @Override
                public void onDone(Object dataUser) {
                    Users user = (Users)dataUser;
                    mUserData = new UserData(user.response[0].id, user.response[0].first_name, user.response[0].photo_200_orig);
                    mFriends.setEnabled(true);
                    mMessage.setEnabled(true);
                }

                @Override
                public void onError(Exception e) {

                }
            }, VkOAuthHelper.sign(Api.USER_GET), Users.class);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
