package by.android.evgen.vkclientexample.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import by.android.evgen.vkclientexample.Api;
import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.activity.FriendsActivity;
import by.android.evgen.vkclientexample.helper.GetFriends;
import by.android.evgen.vkclientexample.helper.VkOAuthHelper;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.dialog.Items;
import by.android.evgen.vkclientexample.model.dialog.Result;
import by.android.evgen.vkclientexample.model.users.Users;
import by.android.evgen.vkclientexample.spring.ISpringCallback;
import by.android.evgen.vkclientexample.spring.SpringParser;

/**
 * Created by Yauheni_Meshkin on 6/4/2015.
 */
public class WidgetProvider extends AppWidgetProvider {

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
    }

    void updateWidget(Context context, AppWidgetManager appWidgetManager,
                      int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.widget);

        setUpdateTV(rv, context, appWidgetId);

        setList(rv, context, appWidgetId, appWidgetManager);

        setListClick(rv, context, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
                R.id.lvList);
    }

    void setUpdateTV(RemoteViews rv, Context context, int appWidgetId) {
        rv.setTextViewText(R.id.tvUpdate,
                "Friends VK");
        Intent updIntent = new Intent(context, WidgetProvider.class);
        updIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                new int[]{appWidgetId});
        PendingIntent updPIntent = PendingIntent.getBroadcast(context,
                appWidgetId, updIntent, 0);
        rv.setOnClickPendingIntent(R.id.tvUpdate, updPIntent);
    }


    void setList(final RemoteViews rv, final Context context, final int appWidgetId, final AppWidgetManager appWidgetManager) {
        new SpringParser().executeInThread(new ISpringCallback<Result>() {
            @Override
            public void onDataLoadStart() {

            }

            @Override
            public void onDone(Result data) {
                Items[] dataArray = data.response.items;
                ArrayList<Items> mList = new ArrayList<>(Arrays.asList(dataArray));
                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    items.add(mList.get(i).first_name);
                }
                Intent adapter = new Intent(context, WidgetService.class);
                adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                adapter.putExtra(FriendsActivity.USER_DATA,
                        items);

                rv.setRemoteAdapter(R.id.lvList, adapter);
                appWidgetManager.updateAppWidget(appWidgetId, rv);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
                        R.id.lvList);
            }

            @Override
            public void onError(Exception e) {

            }
        }, VkOAuthHelper.sign(getUrl(10, 0)), Result.class);

    }

    void setListClick(RemoteViews rv, Context context, int appWidgetId) {

    }

    private String getUrl(int count, int offset) {
        return Api.FRIENDS_GET + "&count=" + count + "&offset=" + offset;
    }

}
