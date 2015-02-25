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

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    Handler fetchHandler;


    private LocationListener onLocationChange = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();

            new WeatherPreference(getApplicationContext()).setLatitude(String.valueOf(latitude));
            new WeatherPreference(getApplicationContext()).setLongitude(String.valueOf(longitude));

            //Toast.makeText(getBaseContext(),"Location : "+latitude+","+longitude,Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(this,"Service created",Toast.LENGTH_SHORT).show();
        //Gestion geolocalisation
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,5,onLocationChange);
        locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,5,onLocationChange);

        fetchHandler = new Handler();

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
        /*WeatherDataSource wds = new WeatherDataSource(this.getBaseContext());
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
        */

        super.onCreate();
    }
    @Override
    public void onDestroy(){
        timer.cancel();
        super.onDestroy();
        locationMgr.removeUpdates(onLocationChange);
        Toast.makeText(this,"Bye",Toast.LENGTH_SHORT).show();
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        //Seems to be the new method the other one is deprecated
        //Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();
        //return START_STICKY;
        return super.onStartCommand(intent,flags,startId);
    }

    private void refreshWeather(){
        //Toast.makeText(this,"Timer now !",Toast.LENGTH_SHORT).show();
        //Refresh la position et la meteo
        //Check si l'user veut une ville ou la geo
        new Thread(){
            public void run(){
                boolean geoOn = new WeatherPreference(getApplicationContext()).getGeoLoc();
                JSONObject data;
                if(geoOn){
                    data = RemoteFetch.getJSON(getApplicationContext(),String.valueOf(latitude),String.valueOf(longitude));
                    fetchHandler.post(new Runnable(){
                       public void run() {
                           //Toast.makeText(getApplicationContext(),"Activation GeoLocalisation",Toast.LENGTH_SHORT).show();
                       }
                    });
                    //Update via geolocalisation

                }
                else{
                    data = RemoteFetch.getJSON(getApplicationContext(),new WeatherPreference(getApplicationContext()).getCity());
                    fetchHandler.post(new Runnable(){
                        public void run(){
                            //Toast.makeText(getApplicationContext(),"Activation city",Toast.LENGTH_SHORT).show();
                        }
                    });
                    //weatherFrag.updateWeatherDataCity(new WeatherPreference(this).getCity());

                }
                if(data == null){
                    Log.d("WeatherService","Error to update the weather");

                    /*fetchHandler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext(),"Error to update the weather",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
                else{
                    WeatherData wd = new WeatherData();
                    try {
                        WeatherDataSource wds = new WeatherDataSource(getApplicationContext());
                        wds.open();
                        JSONObject main = data.getJSONObject("main");
                        JSONObject details = data.getJSONArray("weather").getJSONObject(0);

                        wd.setHumidity(main.getDouble("humidity"));
                        wd.setDescription(details.getString("description"));
                        wd.setCondition(details.getString("main"));
                        wd.setWind(data.getJSONObject("wind").getDouble("speed"));
                        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm");
                        Date curDate = new Date();
                        wd.setDate(dateFormat.format(curDate));
                        //Date date = new Date(data.getLong("dt")*1000);
                        //wd.setDate(date.getYear()+":"+date.getMonth()+":"+date.getDay()+" "+date.getHours()+":"+date.getMinutes());
                        //wd.setDate("2015:02:05 13:52");
                        wd.setCity(data.getString("name") + ", " + data.getJSONObject("sys").getString("country"));
                        wd.setTemperature(main.getDouble("temp"));
                        wds.insertWDS(wd);
                        final double tempTemp = wd.getTemperature();
                        fetchHandler.post(new Runnable(){
                            public void run(){
                                //Toast.makeText(getApplicationContext(),"Data stored ! (temp="+tempTemp+")",Toast.LENGTH_SHORT).show();
                            }
                        });
                        wds.close();
                    }
                    catch(Exception e){
                        //Toast.makeText(this,"Error while fetching some data",Toast.LENGTH_SHORT).show();
                        Log.d("WeatherService","Error while fetching some data : "+e.toString()+wd.toString());
                    }


                }
                LDE_VDA detect = new LDE_VDA();
                WeatherDataSource wds = new WeatherDataSource(getApplicationContext());
                wds.open();
                final ArrayList<WeatherData>dataToTransform = wds.getNLastDaysAverage(3);
                fetchHandler.post(new Runnable() {
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "TestData=(" + dataToTransform.get(0).getTemperature() + ")", Toast.LENGTH_SHORT).show();
                    }
                });
                ArrayList<Double>dataToCompute = new ArrayList<Double>();
                for(int i=0;i<dataToTransform.size();i++){
                    dataToCompute.add(Double.valueOf(dataToTransform.get(i).getTemperature()));
                }
                wds.close();
                detect.computeShort(dataToCompute,getApplicationContext());
            }
        }.start();

        /*WeatherFragment wf = (WeatherFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);*/



    }

}
