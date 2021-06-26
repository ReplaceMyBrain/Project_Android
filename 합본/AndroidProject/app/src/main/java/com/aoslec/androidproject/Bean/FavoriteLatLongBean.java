package com.aoslec.androidproject.Bean;

public class FavoriteLatLongBean {
    private String latitude;
    private String longitude;
    private String location;
    private int temp;
    private int id;

    public FavoriteLatLongBean(String latitude, String longitude, String location, int temp, int id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.temp = temp;
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
