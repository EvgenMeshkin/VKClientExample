package by.android.evgen.vkclientexample.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Yauheni_Meshkin on 6/4/2015.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(getApplicationContext(), intent);
    }

}