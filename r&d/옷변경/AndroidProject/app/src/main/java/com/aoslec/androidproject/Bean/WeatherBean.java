package com.aoslec.androidproject.Bean;

public class WeatherBean {
    private String dateTimeISO;
    private int maxTempC;
    private int minTempC;
    private int avgTempC;
    private int tempC;
    private int maxFeelslikeC;
    private int minFeelslikeC;
    private int avgFeelslikeC;
    private int feelslikeC;
    private int pop;
    private String weather;
    private String icon;

    public WeatherBean(String dateTimeISO, int maxTempC, int minTempC, int avgTempC, int tempC, int maxFeelslikeC, int minFeelslikeC, int avgFeelslikeC, int feelslikeC, int pop, String weather, String icon) {
        this.dateTimeISO = dateTimeISO;
        this.maxTempC = maxTempC;
        this.minTempC = minTempC;
        this.avgTempC = avgTempC;
        this.tempC = tempC;
        this.maxFeelslikeC = maxFeelslikeC;
        this.minFeelslikeC = minFeelslikeC;
        this.avgFeelslikeC = avgFeelslikeC;
        this.feelslikeC = feelslikeC;
        this.pop = pop;
        this.weather = weather;
        this.icon = icon;
    }

    public String getDateTimeISO() {
        return dateTimeISO;
    }

    public void setDateTimeISO(String dateTimeISO) {
        this.dateTimeISO = dateTimeISO;
    }

    public int getMaxTempC() {
        return maxTempC;
    }

    public void setMaxTempC(int maxTempC) {
        this.maxTempC = maxTempC;
    }

    public int getMinTempC() {
        return minTempC;
    }

    public void setMinTempC(int mintTempC) {
        this.minTempC = mintTempC;
    }

    public int getAvgTempC() {
        return avgTempC;
    }

    public void setAvgTempC(int avgTemC) {
        this.avgTempC = avgTemC;
    }

    public int getTempC() {
        return tempC;
    }

    public void setTempC(int tempC) {
        this.tempC = tempC;
    }

    public int getMaxFeelslikeC() {
        return maxFeelslikeC;
    }

    public void setMaxFeelslikeC(int maxFeelslikeC) {
        this.maxFeelslikeC = maxFeelslikeC;
    }

    public int getMinFeelslikeC() {
        return minFeelslikeC;
    }

    public void setMinFeelslikeC(int minFeelslikeC) {
        this.minFeelslikeC = minFeelslikeC;
    }

    public int getAvgFeelslikeC() {
        return avgFeelslikeC;
    }

    public void setAvgFeelslikeC(int avgFeelslikeC) {
        this.avgFeelslikeC = avgFeelslikeC;
    }

    public int getFeelslikeC() {
        return feelslikeC;
    }

    public void setFeelslikeC(int feelslikeC) {
        this.feelslikeC = feelslikeC;
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
