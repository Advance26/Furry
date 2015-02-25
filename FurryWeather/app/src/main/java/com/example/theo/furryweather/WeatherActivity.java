package com.example.theo.furryweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class WeatherActivity extends ActionBarActivity {

    private WeatherFragment weatherFrag = new WeatherFragment();
    private WeatherService weatherService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, weatherFrag)
                    .commit();
        }
        weatherService = new WeatherService();
        this.startService(new Intent(this,weatherService.getClass()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);*/
        if(item.getItemId() == R.id.change_city){
            showCityDialog();
        }
        if(item.getItemId() == R.id.change_localisationMode){
            showLocalisationDialog();
        }
        if(item.getItemId() == R.id.exitApp){
            stopService(new Intent(this,weatherService.getClass()));
            this.finish();

        }
        return false;

    }


    private void showCityDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }

    public void changeCity(String city) {
        WeatherFragment wf = (WeatherFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        wf.changeCity(city);
        new WeatherPreference(this).setCity(city);
    }

    public void showLocalisationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Localisation Settings");
        builder.setMessage("Geolocalisation ");
        final Switch sw = new Switch(this);

        builder.setView(sw);
        final boolean switchState = new WeatherPreference(this.getApplicationContext()).getGeoLoc();
        Log.d("WeatherActivity","GeoLocSwitchStateBefore : "+switchState);

        sw.setChecked(switchState);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sw.isChecked()){
                    changeLocMode(true);
                    WeatherFragment wf = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                    wf.updateWeatherDataLocation(new WeatherPreference(getApplicationContext()).getLatitude(),
                            new WeatherPreference(getApplicationContext()).getLongitude());
                }
                else{
                    changeLocMode(false);
                    WeatherFragment wf = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                    wf.updateWeatherDataCity(new WeatherPreference(getApplicationContext()).getCity());
                }
            }
        });

        changeLocMode(switchState);
        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Il n'y a rien
            }
        });

        builder.show();
    }

    public void changeLocMode(boolean a){
        new WeatherPreference(this).setGeoLoc(a);
        //Toast.makeText(this,"GeoLoc is : "+a+new WeatherPreference(this).getGeoLoc(),Toast.LENGTH_SHORT).show();
    }

}

