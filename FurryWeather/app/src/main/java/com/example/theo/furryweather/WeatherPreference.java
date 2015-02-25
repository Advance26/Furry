package com.example.theo.furryweather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Theo on 23/01/2015.
 */
public class WeatherPreference {

    SharedPreferences prefs;

    public WeatherPreference(Context context){
        prefs = context.getSharedPreferences("com.furry.furryweather",Context.MODE_PRIVATE);
        //prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
    public String getCity(){
        return prefs.getString("city", "Paris, FR");
    }
    public void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }

    public void setLatitude(String lat){
        prefs.edit().putString("latitude",lat).commit();
    }
    public String getLatitude(){
        return prefs.getString("latitude","0.0");
    }
    public void setLongitude(String lng){
        prefs.edit().putString("longitude",lng).commit();
    }
    public String getLongitude(){
        return prefs.getString("longitude","0.0");
    }

    public void setGeoLoc(boolean a){
       prefs.edit().putBoolean("geoLoc",a).commit();
    }
    public boolean getGeoLoc(){
        return prefs.getBoolean("geoLoc",false);
    }
}
