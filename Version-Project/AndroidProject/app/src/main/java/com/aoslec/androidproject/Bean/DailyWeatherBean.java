package com.aoslec.androidproject.Bean;

public class DailyWeatherBean {
    //일별 정보
    private int daily_id;
    private String daily_main;
    private String daily_description;
    private String daily_time;
    private int daily_temp_max;
    private int daily_temp_min;
    private int daily_feels_like;
    private int daily_humidity;
    private double daily_uvi;
    private int daily_pop;

    public DailyWeatherBean(int daily_id, String daily_main, String daily_description, String daily_time, int daily_temp_max,int daily_temp_min, int daily_feels_like, int daily_humidity, double daily_uvi, int daily_pop) {
        this.daily_id = daily_id;
        this.daily_main = daily_main;
        this.daily_description = daily_description;
        this.daily_time = daily_time;
        this.daily_temp_max = daily_temp_max;
        this.daily_temp_min=daily_temp_min;
        this.daily_feels_like = daily_feels_like;
        this.daily_humidity = daily_humidity;
        this.daily_uvi = daily_uvi;
        this.daily_pop = daily_pop;
    }

    public int getDaily_id() {
        return daily_id;
    }

    public void setDaily_id(int daily_id) {
        this.daily_id = daily_id;
    }

    public String getDaily_main() {
        return daily_main;
    }

    public void setDaily_main(String daily_main) {
        this.daily_main = daily_main;
    }

    public String getDaily_description() {
        return daily_description;
    }

    public void setDaily_description(String daily_description) {
        this.daily_description = daily_description;
    }

    public String getDaily_time() {
        return daily_time;
    }

    public void setDaily_time(String daily_time) {
        this.daily_time = daily_time;
    }

    public int getDaily_temp_max() {
        return daily_temp_max;
    }

    public void setDaily_temp_max(int daily_temp_max) {
        this.daily_temp_max = daily_temp_max;
    }

    public int getDaily_temp_min() {
        return daily_temp_min;
    }

    public void setDaily_temp_min(int daily_temp_min) {
        this.daily_temp_min = daily_temp_min;
    }

    public int getDaily_feels_like() {
        return daily_feels_like;
    }

    public void setDaily_feels_like(int daily_feels_like) {
        this.daily_feels_like = daily_feels_like;
    }

    public int getDaily_humidity() {
        return daily_humidity;
    }

    public void setDaily_humidity(int daily_humidity) {
        this.daily_humidity = daily_humidity;
    }

    public double getDaily_uvi() {
        return daily_uvi;
    }

    public void setDaily_uvi(double daily_uvi) {
        this.daily_uvi = daily_uvi;
    }

    public int getDaily_pop() {
        return daily_pop;
    }

    public void setDaily_pop(int daily_pop) {
        this.daily_pop = daily_pop;
    }
}
