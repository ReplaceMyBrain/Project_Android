package com.aoslec.androidproject.Share;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {
    //도우
    static final String lang = "lang";
    static final String clothesColor = "clothesColor";

    //성준
    static final String Lat="Lat";
    static final String Long="Long";
    static final String Location="Location";

    //서린
    static final String FIRST_VISIT_USER = "firstVisitUser";

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
        return getSharedPreferences(ctx).getString(clothesColor, "c");
    }


    //<위치 정보>
    //값을 넣어줄때 사용
    public static void setLat(Context ctx, String sLat) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Lat, sLat);
        editor.apply();
    }

    //값을 불러올때 사용
    public static String getLat(Context ctx) {
        return getSharedPreferences(ctx).getString(Lat, "37.5642135");
    }

    //값을 넣어줄때 사용
    public static void setLong(Context ctx, String sLong) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Long, sLong);
        editor.apply();
    }

    //값을 불러올때 사용
    public static String getLong(Context ctx) {
        return getSharedPreferences(ctx).getString(Long, "127.0016985");
    }

    //값을 넣어줄때 사용
    public static void setLocation(Context ctx, String sLocation) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Location, sLocation);
        editor.apply();
    }

    //값을 불러올때 사용
    public static String getLocation(Context ctx) {
        return getSharedPreferences(ctx).getString(Location, "서울");
    }

    public static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setFirstVisitUser(Context context, String firstVisitUser){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(FIRST_VISIT_USER, firstVisitUser);
        editor.apply();
    }

    public static String getFirstVisitUser(Context context){
        return getSharedPreferences(context).getString(FIRST_VISIT_USER, "y");
    }

    public static void clearAllData(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
