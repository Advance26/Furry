package com.example.theo.furryweather;

/**
 * Created by Roc5 on 29/01/2015.
 */
public class WeatherData {
    private int id;
    private String city;
    private String date;
    private double temperature;
    private double wind;
    private String condition;
    private String description;
    private double humidity;
    public WeatherData(){}
    public WeatherData(int id, String city, String date, double temperature, double wind, String condition, String description, double humidity) {
        this.id = id;
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.wind = wind;
        this.condition = condition;
        this.description = description;
        this.humidity = humidity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
