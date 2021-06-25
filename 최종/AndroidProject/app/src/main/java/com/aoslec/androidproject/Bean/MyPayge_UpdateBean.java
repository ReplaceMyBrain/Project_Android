package com.aoslec.androidproject.Bean;

/**
 * Created by biso on 2021/06/23.
 */
public class MyPayge_UpdateBean {
    public String title;
    public String miniTitle;
    public String content;
    public String text1;
    public String text2;
    public String btnOpen;
    public String btnUpdate;

    public MyPayge_UpdateBean(String title, String miniTitle, String content, String text1, String text2, String btnOpen, String btnUpdate) {
        this.title = title;
        this.miniTitle = miniTitle;
        this.content = content;
        this.text1 = text1;
        this.text2 = text2;
        this.btnOpen = btnOpen;
        this.btnUpdate = btnUpdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMiniTitle() {
        return miniTitle;
    }

    public void setMiniTitle(String miniTitle) {
        this.miniTitle = miniTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getBtnOpen() {
        return btnOpen;
    }

    public void setBtnOpen(String btnOpen) {
        this.btnOpen = btnOpen;
    }

    public String getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(String btnUpdate) {
        this.btnUpdate = btnUpdate;
    }
}
