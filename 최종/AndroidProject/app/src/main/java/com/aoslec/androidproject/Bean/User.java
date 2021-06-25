package com.aoslec.androidproject.Bean;

/**
 * Created by biso on 2021/06/18.
 */
public class User {

    public String email;
    public String name;
    public String pw;
    public String phone;
    public String image;
    public String indate;
    public String outdate;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String pw, String image) {
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.image = image;
    }

    public User(String email, String name, String pw, String phone, String image, String indate, String outdate) {
        this.email = email;
        this.name = name;
        this.pw = pw;
        this.phone = phone;
        this.image = image;
        this.indate = indate;
        this.outdate = outdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getOutdate() {
        return outdate;
    }

    public void setOutdate(String outdate) {
        this.outdate = outdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    //FireBase DB
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

