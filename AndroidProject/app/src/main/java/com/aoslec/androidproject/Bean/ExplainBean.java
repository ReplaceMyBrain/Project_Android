package com.aoslec.androidproject.Bean;

public class ExplainBean {

    String title;
    String contents;
    int images;

    public ExplainBean(String title, String contents, int images) {
        this.title = title;
        this.contents = contents;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }


}
