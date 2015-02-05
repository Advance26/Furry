package com.example.theo.furryweather;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Roc5 on 22/01/2015.
 */
public class WeatherService extends Service {

    private LocationManager locationMgr = null;
    private LocationListener onLocationChange = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            Double altitude = location.getAltitude();

            Toast.makeText(getBaseContext(),"Location : "+latitude+","+longitude,Toast.LENGTH_SHORT).show();

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

        //Gestion DB
        WeatherDataSource wds = new WeatherDataSource(this.getBaseContext());
        wds.open();

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
    /*@Override
    public void onStart(Intent intent,int startId){
        Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();
    }*/

}
