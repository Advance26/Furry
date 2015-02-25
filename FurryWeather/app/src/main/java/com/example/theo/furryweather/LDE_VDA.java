package com.example.theo.furryweather;

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

    public int computeShort(ArrayList<Double> data){
        Log.d("LDE_VDA","Computing data...");
        if(data.size() < 1)return -1;
        else if(data.size() == 1)return 0;
        Log.d("LDE_VDA",data.toString());
        double lastValue = data.get(data.size()-1);
        data.remove(data.size()-1);
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
        }

        return 1;
    }
}
