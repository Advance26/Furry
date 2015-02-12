package com.example.theo.furryweather;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Roc5 on 22/01/2015.
 */
public class WeatherService extends Service {

    private LocationManager locationMgr = null;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Timer timer;

    private LocationListener onLocationChange = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();

            Toast.makeText(getBaseContext(),"Location : "+latitude+","+longitude,Toast.LENGTH_SHORT).show();
            //Put new coordonates in the database

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };//onLocationChange;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        Toast.makeText(this,"Service created",Toast.LENGTH_SHORT).show();
        //Gestion geolocalisation
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,5,onLocationChange);
        locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,5,onLocationChange);

        timer = new Timer();
        final Handler handler = new Handler();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                handler.post(new Runnable(){
                    public void run(){
                        refreshWeather();
                    }
                });

            }
        },0,10000);

        //Gestion DB
        WeatherDataSource wds = new WeatherDataSource(this.getBaseContext());
        wds.open();
        WeatherData wd = new WeatherData();
        wd.setHumidity(0.5);
        wd.setDescription("This is the first data");
        wd.setCondition("Neigeux");
        wd.setWind(30);
        wd.setDate("2015:02:05 13:52");
        wd.setCity("Lyon");
        wd.setTemperature(-30);
        //wds.insertWDS(wd);

        Toast.makeText(this,"DBNbRows : "+wds.getNLastDaysAverage(2).size(),Toast.LENGTH_SHORT).show();

        wds.close();


        super.onCreate();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        locationMgr.removeUpdates(onLocationChange);
        Toast.makeText(this,"Service destroyed",Toast.LENGTH_SHORT).show();
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        //Seems to be the new method the other one is deprecated
        Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();
        //return START_STICKY;
        return super.onStartCommand(intent,flags,startId);
    }

    private void refreshWeather(){
        Toast.makeText(this,"Timer now !",Toast.LENGTH_SHORT).show();
        //Refresh la position et la meteo
    }

}
