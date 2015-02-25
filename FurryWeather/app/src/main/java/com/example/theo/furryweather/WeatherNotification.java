package com.example.theo.furryweather;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class WeatherNotification extends Activity{

    Context context;
    public WeatherNotification(Context context){
        this.context = context;
    }

    public void sendNotification(String text){
        NotificationManager NM;
        NM=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification(android.R.drawable.stat_notify_more,"Variation Detectée !",System.currentTimeMillis());
        PendingIntent pending= PendingIntent.getActivity(context, 0, new Intent(), 0);
        notify.setLatestEventInfo(context, "FurryWeather", text,pending);
        NM.notify(0, notify);
    }

}
