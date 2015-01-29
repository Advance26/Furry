package com.example.theo.furryweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.sql.SQLException;

/**
 * Created by Roc5 on 29/01/2015.
 */
public class WeatherDataSource {
    private SQLiteDatabase database;
    private DBManager dbm;
    private String[] allColumns = {"id","city","date","temperature","wind","condition","description","humidity"};

    public WeatherDataSource(Context context){
        dbm = new  DBManager(context);
    }

    public void open() throws SQLException{
        database = dbm.getWritableDatabase();
    }
    public void close(){
        dbm.close();
    }
    public void insertWDS(WeatherData wd){
        ContentValues cv = new ContentValues();
        cv.put("id",wd.getId());
        cv.put("city",wd.getCity());
        cv.put("dataDate",wd.getDate());
        cv.put("temperature",wd.getTemperature());
        cv.put("wind",wd.getWind());
        cv.put("condition",wd.getCondition());
        cv.put("description",wd.getDescription());
        cv.put("humudity",wd.getHumidity());

        database.insert("weather",null,cv);

    }
}
