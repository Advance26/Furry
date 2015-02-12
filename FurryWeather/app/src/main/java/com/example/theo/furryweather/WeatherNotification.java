package com.example.theo.furryweather;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Theo on 12/02/2015.
 */
public class WeatherNotification extends WeatherActivity{

    private NotificationCompat builder;
    private Notification notification;

    public WeatherNotification(){

    }

    public void sendBasicNotification(String title, String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(R.drawable.ic_launcher);

        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(8, notification);
    }

}
