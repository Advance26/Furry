package com.example.theo.furryweather;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


/**
 * Created by Theo on 12/02/2015.
 */
public class WeatherNotification extends Activity{

    public void sendBasicNotification(String eventtext, Context ctx){
        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher,
                "hey", System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this
        // notification
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, WeatherActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(ctx, "Title", eventtext,
                contentIntent);

        // Send the notification.
        mNM.notify("Title", 0, notification);
    }

}
