package com.aoslec.androidproject.Bean;

public class HourlyWeatherBean {
    //시간별 정보
    private int hourly_id;
    private String hourly_main;
    private String hourly_description;
    private String hourly_time;
    private int hourly_temp;
    private int hourly_feels_like;
    private int hourly_humidity;
    private double hourly_uvi;
    private int hourly_pop;

    public HourlyWeatherBean(int hourly_id, String hourly_main, String hourly_description, String hourly_time, int hourly_temp, int hourly_feels_like, int hourly_humidity, double hourly_uvi, int hourly_pop) {
        this.hourly_id = hourly_id;
        this.hourly_main = hourly_main;
        this.hourly_description = hourly_description;
        this.hourly_time = hourly_time;
        this.hourly_temp = hourly_temp;
        this.hourly_feels_like = hourly_feels_like;
        this.hourly_humidity = hourly_humidity;
        this.hourly_uvi = hourly_uvi;
        this.hourly_pop = hourly_pop;
    }

    public int getHourly_id() {
        return hourly_id;
    }

    public void setHourly_id(int hourly_id) {
        this.hourly_id = hourly_id;
    }

    public String getHourly_main() {
        return hourly_main;
    }

    public void setHourly_main(String hourly_main) {
        this.hourly_main = hourly_main;
    }

    public String getHourly_description() {
        return hourly_description;
    }

    public void setHourly_description(String hourly_description) {
        this.hourly_description = hourly_description;
    }

    public String getHourly_time() {
        return hourly_time;
    }

    public void setHourly_time(String hourly_time) {
        this.hourly_time = hourly_time;
    }

    public int getHourly_temp() {
        return hourly_temp;
    }

    public void setHourly_temp(int hourly_temp) {
        this.hourly_temp = hourly_temp;
    }

    public int getHourly_feels_like() {
        return hourly_feels_like;
    }

    public void setHourly_feels_like(int hourly_feels_like) {
        this.hourly_feels_like = hourly_feels_like;
    }

    public int getHourly_humidity() {
        return hourly_humidity;
    }

    public void setHourly_humidity(int hourly_humidity) {
        this.hourly_humidity = hourly_humidity;
    }

    public double getHourly_uvi() {
        return hourly_uvi;
    }

    public void setHourly_uvi(double hourly_uvi) {
        this.hourly_uvi = hourly_uvi;
    }

    public int getHourly_pop() {
        return hourly_pop;
    }

    public void setHourly_pop(int hourly_pop) {
        this.hourly_pop = hourly_pop;
    }
}
