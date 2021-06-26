package com.aoslec.androidproject.Bean;

public class SelectBean {
    private int id;
    private boolean select;

    public SelectBean(int id, boolean select) {
        this.id = id;
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
