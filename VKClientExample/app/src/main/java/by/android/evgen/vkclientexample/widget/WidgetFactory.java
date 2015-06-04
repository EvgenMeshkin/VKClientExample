package by.android.evgen.vkclientexample.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.activity.FriendsActivity;

/**
 * Created by Yauheni_Meshkin on 6/4/2015.
 */
public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<String> data;
    ArrayList<String> newData = new ArrayList<>();
    Context context;
    SimpleDateFormat sdf;
    int widgetID;

    WidgetFactory(Context ctx, Intent intent) {
        context = ctx;
        sdf = new SimpleDateFormat("HH:mm:ss");
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        newData = intent.getStringArrayListExtra(FriendsActivity.USER_DATA);
     }



    @Override
    public void onCreate() {
        data = new ArrayList<String>();
     }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.item_widget);
        rView.setTextViewText(R.id.tvItemText, data.get(position));
        return rView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        data.clear();
        data.addAll(newData);
    }

    @Override
    public void onDestroy() {

    }

}