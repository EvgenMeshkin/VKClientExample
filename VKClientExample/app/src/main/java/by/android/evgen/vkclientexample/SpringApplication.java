package by.android.evgen.vkclientexample;

import com.activeandroid.ActiveAndroid;
import com.splunk.mint.Mint;

/**
 * Created by evgen on 28.03.2015.
 */
public class SpringApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Mint.initAndStartSession(this, "67db0308");
    }

}
