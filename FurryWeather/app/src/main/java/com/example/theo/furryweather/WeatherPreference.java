package com.example.theo.furryweather;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Theo on 23/01/2015.
 */
public class WeatherPreference {

    SharedPreferences prefs;

    public WeatherPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
    public String getCity(){
        return prefs.getString("city", "Paris, FR");
    }
    public void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }

    public void setGeoLoc(boolean a){
       prefs.edit().putBoolean("geoLoc",a);
    }
    public boolean getGeoLoc(){
        return prefs.getBoolean("geoLoc",true);
    }
}
