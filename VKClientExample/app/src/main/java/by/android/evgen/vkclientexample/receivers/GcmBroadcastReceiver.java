package by.android.evgen.vkclientexample.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import by.android.evgen.vkclientexample.service.GcmIntentService;

/**
 * Created by evgen on 18.04.2015.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, GcmIntentService.class);
        serviceIntent.putExtras(intent.getExtras());

        startWakefulService(context, serviceIntent);
    }

}
