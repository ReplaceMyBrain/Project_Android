package com.aoslec.androidproject.Share;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {
    //도우
    static final String lang = "lang";
    static final String clothesColor = "clothesColor";
    static final String url = "url";

    //성준
    static final String Lat="Lat";
    static final String Long="Long";
    static final String Location="Location";

    //서린
    static final String FIRST_VISIT_USER = "firstVisitUser";
    static final String PREF_IS_LOGIN = "isLogin";
    static final String PREF_AUTO_LOGIN = "autoLogin";
    static final String LOGIN_METHOD = "";

    static final String PREF_EMAIL = "email";
    static final String PREF_PW = "pw";
    static final String PREF_NAME = "name";
    static final String PREF_IMAGE = "image";
    static final String PREF_PHONE = "phone";

    //<서버 url>

    //값을 불러올때 사용 url 본인것으로 바꾸면 됨.
    public static String getUrl(Context ctx) {
        return getSharedPreferences(ctx).getString(url, "http://172.30.1.11:8080/project/");
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
        return getSharedPreferences(ctx).getString(lang, "en");
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

    public static void setPrefIsLogin(Context ctx, String isLogin) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_IS_LOGIN, isLogin);
        editor.apply();
    }

    public static String getPrefIsLogin(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_IS_LOGIN, "n");
    }


    public static void setPrefAutoLogin(Context ctx, String autoLogin) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_AUTO_LOGIN, autoLogin);
        editor.apply();
    }

    public static String getPrefAutoLogin(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_AUTO_LOGIN, "n");
    }

    public static void setPrefEmail(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_EMAIL, id);
        editor.apply();
    }

    public static String getPrefEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_EMAIL, "");
    }

    public static void setPrefName(Context ctx, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NAME, name);
        editor.apply();
    }

    public static String getPrefName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_NAME, "");
    }

    public static void setPrefPw(Context ctx, String pw) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PW, pw);
        editor.apply();
    }

    public static String getPrefPw(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_PW, "");
    }

    public static void setPrefImage(Context ctx, String image) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_IMAGE, image);
        editor.apply();
    }

    public static String getPrefImage(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_IMAGE, null);
    }

    public static void setPrefPhone(Context ctx, String phone) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PHONE, phone);
        editor.apply();
    }

    public static String getPrefPhone(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_PHONE, "");
    }

    public static void setLoginMethod(Context ctx, String loginmethod) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOGIN_METHOD, loginmethod);
        editor.apply();
    }

    public static String getLoginMethod(Context ctx) {
        return getSharedPreferences(ctx).getString(LOGIN_METHOD, "");
    }

    public static void clearAllData(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
