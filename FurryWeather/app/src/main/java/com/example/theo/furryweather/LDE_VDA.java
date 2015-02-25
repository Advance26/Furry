package com.example.theo.furryweather;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Roc5 on 19/02/2015.
 */
public class LDE_VDA {
    public LDE_VDA(){

    }

    public int compute(double[]data){
        return 0;
    }

    public int computeShort(ArrayList<Double> data,Context context){
        Log.d("LDE_VDA","Computing data...");
        if(data.size() < 1)return -1;
        else if(data.size() == 1)return 0;
        Log.d("LDE_VDA",data.toString());
        double lastValue = data.get(0);
        data.remove(0);
        double moyenne = 0;
        double nbValue = 0;
        for(int i=0; i<data.size();i++){
            moyenne = moyenne + data.get(i).doubleValue();
            nbValue++;
        }
        moyenne = moyenne / nbValue;
        Log.d("LDE_VDA","Input:"+data.toString());
        Log.d("LDE_VDA","Moyenne:"+moyenne);
        Log.d("LDE_VDA","LastValue"+lastValue);
        double pourcentage = 0;
        if(moyenne < 0){
            pourcentage = -moyenne;
        }
        else{
            pourcentage = moyenne;
        }
        pourcentage = pourcentage + pourcentage*0.2;
        if(lastValue > (moyenne + pourcentage) || lastValue < (moyenne - pourcentage)){
            //Trigger the variation
            Log.d("LDE_VDA","Variation detectée !");
            sendNotif(context,"Attention temperature de "+lastValue+" °C");
        }

        return 1;
    }

    public void sendNotif(Context context,String text){
        NotificationManager NM;
        NM=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification(R.drawable.ic_stat_name,"Variation Detectée !",System.currentTimeMillis());
        PendingIntent pending= PendingIntent.getActivity(context, 0, new Intent(), 0);
        notify.setLatestEventInfo(context, "FurryWeather", text,pending);
        NM.notify(0, notify);
    }

}
