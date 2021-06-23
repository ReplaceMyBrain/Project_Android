package com.aoslec.androidproject.SaveSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {
    static final String lang = "lang";
    static final String clothesColor = "clothesColor";
    static final String url = "url";

    //생성자
    public static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    //<서버 url>

    //값을 불러올때 사용 url 본인것으로 바꾸면 됨.
    public static String getUrl(Context ctx) {
        return getSharedPreferences(ctx).getString(url, "http://192.168.43.46:8080/project/");
    }

    public static void setUrl(Context ctx, String sUrl) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(url, sUrl);
        editor.apply();
    }

    //<언어설정>

    //값을 넣어줄때 사용
    public static void setLangMethod(Context ctx, String code) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(lang, code);
        editor.apply();
    }

    //값을 불러올때 사용
    public static String getLangMethod(Context ctx) {
        return getSharedPreferences(ctx).getString(lang, "ko");
    }


    //<옷 색설정>

    //값을 넣어줄때 사용
    public static void setClothesColor(Context ctx, String sclothesColor) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(clothesColor, sclothesColor);
        editor.apply();
    }

    //값을 불러올때 사용
    public static String getClothesColor(Context ctx) {
        return getSharedPreferences(ctx).getString(clothesColor, "none/");
    }



}//--------------