package com.aoslec.androidproject.Bean;

public class Ad_requestBean {
    String title, url, price, email, image, adid;

    public Ad_requestBean(String title, String url, String price, String email, String image, String adid) {
        this.title = title;
        this.url = url;
        this.price = price;
        this.email = email;
        this.image = image;
        this.adid = adid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }
}

