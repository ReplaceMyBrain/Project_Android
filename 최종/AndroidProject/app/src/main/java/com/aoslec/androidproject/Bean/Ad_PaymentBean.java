package com.aoslec.androidproject.Bean;

/**
 * Created by biso on 2021/06/23.
 */
public class Ad_PaymentBean {
    public String ad_count;

    public String ad_id;
    public String ad_title;
    public String ad_url;
    public String ad_image;
    public String ad_price;
    public String ad_location;
    public String ad_indate;
    public String ad_outdate;

    public String payment_id;
    public String payment_indate;
    public String payment_outdate;



    public Ad_PaymentBean() {
    }


    public Ad_PaymentBean(String ad_count) {
        this.ad_count = ad_count;
    }

    public Ad_PaymentBean(String ad_id, String ad_title, String ad_url, String ad_image, String ad_price, String ad_location, String ad_indate, String ad_outdate, String payment_id, String payment_indate, String payment_outdate) {
        this.ad_id = ad_id;
        this.ad_title = ad_title;
        this.ad_url = ad_url;
        this.ad_image = ad_image;
        this.ad_price = ad_price;
        this.ad_location = ad_location;
        this.ad_indate = ad_indate;
        this.ad_outdate = ad_outdate;
        this.payment_id = payment_id;
        this.payment_indate = payment_indate;
        this.payment_outdate = payment_outdate;
    }


    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getAd_image() {
        return ad_image;
    }

    public void setAd_image(String ad_image) {
        this.ad_image = ad_image;
    }

    public String getAd_price() {
        return ad_price;
    }

    public void setAd_price(String ad_price) {
        this.ad_price = ad_price;
    }

    public String getAd_location() {
        return ad_location;
    }

    public void setAd_location(String ad_location) {
        this.ad_location = ad_location;
    }

    public String getAd_indate() {
        return ad_indate;
    }

    public void setAd_indate(String ad_indate) {
        this.ad_indate = ad_indate;
    }

    public String getAd_outdate() {
        return ad_outdate;
    }

    public void setAd_outdate(String ad_outdate) {
        this.ad_outdate = ad_outdate;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_indate() {
        return payment_indate;
    }

    public void setPayment_indate(String payment_indate) {
        this.payment_indate = payment_indate;
    }

    public String getPayment_outdate() {
        return payment_outdate;
    }

    public void setPayment_outdate(String payment_outdate) {
        this.payment_outdate = payment_outdate;
    }

    public String getAd_count() {
        return ad_count;
    }

    public void setAd_count(String ad_count) {
        this.ad_count = ad_count;
    }
}