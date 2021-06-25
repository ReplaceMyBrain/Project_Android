package com.aoslec.androidproject.Bean;

public class CurrentWeatherBean {
    //현재 정보
    private String timezone;
    private Long current_time;
    private int current_temp;
    private int current_feels_like;
    private int current_humidity;
    private double current_uvi;
    private int current_id;
    private String current_main;
    private String current_description;

    public CurrentWeatherBean(String timezone, Long current_time, int current_temp, int current_feels_like, int current_humidity, double current_uvi, int current_id, String current_main, String current_description) {
        this.timezone = timezone;
        this.current_time = current_time;
        this.current_temp = current_temp;
        this.current_feels_like = current_feels_like;
        this.current_humidity = current_humidity;
        this.current_uvi = current_uvi;
        this.current_id = current_id;
        this.current_main = current_main;
        this.current_description = current_description;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Long getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(Long current_time) {
        this.current_time = current_time;
    }

    public int getCurrent_temp() {
        return current_temp;
    }

    public void setCurrent_temp(int current_temp) {
        this.current_temp = current_temp;
    }

    public int getCurrent_feels_like() {
        return current_feels_like;
    }

    public void setCurrent_feels_like(int current_feels_like) {
        this.current_feels_like = current_feels_like;
    }

    public int getCurrent_humidity() {
        return current_humidity;
    }

    public void setCurrent_humidity(int current_humidity) {
        this.current_humidity = current_humidity;
    }

    public double getCurrent_uvi() {
        return current_uvi;
    }

    public void setCurrent_uvi(double current_uvi) {
        this.current_uvi = current_uvi;
    }

    public int getCurrent_id() {
        return current_id;
    }

    public void setCurrent_id(int current_id) {
        this.current_id = current_id;
    }

    public String getCurrent_main() {
        return current_main;
    }

    public void setCurrent_main(String current_main) {
        this.current_main = current_main;
    }

    public String getCurrent_description() {
        return current_description;
    }

    public void setCurrent_description(String current_description) {
        this.current_description = current_description;
    }
}