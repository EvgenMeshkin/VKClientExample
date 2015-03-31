package by.android.evgen.vkclientexample;

import com.activeandroid.ActiveAndroid;

/**
 * Created by evgen on 28.03.2015.
 */
public class SpringApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
