package by.android.evgen.vkclientexample;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.splunk.mint.Mint;

/**
 * Created by evgen on 28.03.2015.
 */
public class SpringApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Mint.initAndStartSession(this, "67db0308");
    }

}
