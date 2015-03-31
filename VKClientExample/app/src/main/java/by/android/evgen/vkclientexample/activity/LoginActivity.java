package by.android.evgen.vkclientexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import by.android.evgen.vkclientexample.R;

/**
 * Created by evgen on 22.03.2015.
 */
public class LoginActivity extends ActionBarActivity {

    public static final int REQUEST_CODE_VK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button cancelBtn = (Button) findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    public void onLoginClick(View view) {
        Toast.makeText(this, "implement me", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VkLoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_VK);
    }


    public void onVkAuthClick(View view) {
        Intent intent = new Intent(this, VkLoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_VK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VK && resultCode == RESULT_OK)  {
            setResult(RESULT_OK);
            finish();
        }
    }
}