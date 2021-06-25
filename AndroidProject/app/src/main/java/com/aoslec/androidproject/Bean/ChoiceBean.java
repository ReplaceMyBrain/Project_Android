package com.aoslec.androidproject.Bean;

public class ChoiceBean {

    String item;
    boolean select;

    public ChoiceBean(String item, boolean select) {
        this.item = item;
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
