package com.sajorahasan.audiorouter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class ToggleWidget extends AppWidgetProvider {
    private static final String widgetClicked = "widgetClicked";

    public void onReceive(Context context, Intent intent) {
        int i = 0;
        if (widgetClicked.equals(intent.getAction())) {
            boolean isChecked = GenericUtility.getBoolFromSharedPrefsForKey("Force", context);
            ToggleAudioManager toggleAudioManager = new ToggleAudioManager();
            if (toggleAudioManager.State()) {
                boolean z;
                String str = "Force";
                if (isChecked) {
                    z = false;
                } else {
                    z = true;
                }
                GenericUtility.setBoolToSharedPrefsForKey(str, z, context);
                if (isChecked) {
                    i = 1;
                }
                toggleAudioManager.ForMedia(i);
            }
            onUpdate(context, AppWidgetManager.getInstance(context), intent.getIntArrayExtra("appWidgetIds"));
            return;
        }
        super.onReceive(context, intent);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.toggle_widget);
        remoteViews.setViewVisibility(R.id.loading_spinner, 4);
        remoteViews.setOnClickPendingIntent(R.id.toggleButtonWidget, getPendingSelfIntent(context, widgetClicked));
        ComponentName thisWidget = new ComponentName(context, ToggleWidget.class);
        if (GenericUtility.getBoolFromSharedPrefsForKey("Force", context)) {
            remoteViews.setImageViewResource(R.id.toggleButtonWidget, R.drawable.on);
        } else {
            remoteViews.setImageViewResource(R.id.toggleButtonWidget, R.drawable.off);
        }
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    public void onEnabled(Context context) {
    }

    public void onDisabled(Context context) {
    }

    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
