package com.example.theo.furryweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Roc5 on 29/01/2015.
 */
public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context){
        super(context,"FurryWeather.db",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table weather ( id integer primary key autoincremennt, " +
                "city text, dataDate text, temperature real, wind real, condition text, " +
                "description text, humidity real);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS weather");
        onCreate(db);
    }
}
