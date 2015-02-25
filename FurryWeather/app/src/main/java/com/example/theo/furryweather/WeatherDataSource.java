package com.example.theo.furryweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.Comment;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Roc5 on 29/01/2015.
 */
public class WeatherDataSource {
    private SQLiteDatabase database;
    private DBManager dbm;
    private String[] allColumns = {"id","city","date","temperature","wind","condition","description","humidity","pressure","dt","sunrise","sunset"};

    public WeatherDataSource(Context context){
        dbm = new  DBManager(context);
    }

    public void open(){
        try{
            database = dbm.getWritableDatabase();
        }
        catch(Exception e){
            Log.d("WeatherDataSource", "Erreur weatherdatassource");
        }
    }
    public void close(){
        dbm.close();
    }

    public void insertWDS(WeatherData wd){
        ContentValues cv = new ContentValues();
        //On ne gere pas les id c'est autoincrement
        cv.put("city",wd.getCity());
        cv.put("dataDate",wd.getDate());
        cv.put("temperature",wd.getTemperature());
        cv.put("wind",wd.getWind());
        cv.put("condition",wd.getCondition());
        cv.put("description",wd.getDescription());
        cv.put("humidity",wd.getHumidity());
        cv.put("pressure",wd.getPressure());
        cv.put("dt",wd.getDt());
        cv.put("sunrise",wd.getSunrise());
        cv.put("sunset",wd.getSunset());

        database.insert("weather",null,cv);

    }

    public ArrayList<WeatherData> getNLastDaysAverage(int N){
        ArrayList<WeatherData> out = new ArrayList<>();
        Log.w("WeatherDataSource","Before the while");
        String query = "select id,city,dataDate,temperature," +
                "wind,condition,description,humidity,pressure," +
                "dt,sunrise,sunset from weather" +
                " order by dataDate DESC,id DESC limit "+N;
        //Pas de ';' en fin de requete
        Cursor cur = database.rawQuery(query,new String[]{});

        int i = 0;
        while(i < cur.getCount()){
            cur.moveToPosition(i);
            WeatherData temp = new WeatherData();
            temp.setId(cur.getInt(0));
            temp.setCity(cur.getString(1));
            temp.setDate(cur.getString(2));
            temp.setTemperature(cur.getDouble(3));
            temp.setWind(cur.getDouble(4));
            temp.setCondition(cur.getString(5));
            temp.setDescription(cur.getString(6));
            temp.setHumidity(cur.getDouble(7));
            temp.setPressure(cur.getDouble(8));
            temp.setDt(cur.getInt(9));
            temp.setSunrise(cur.getInt(10));
            temp.setSunset(cur.getInt(11));
            out.add(temp);
            i++;
        }
        cur.close();

        return out;
    }
}
