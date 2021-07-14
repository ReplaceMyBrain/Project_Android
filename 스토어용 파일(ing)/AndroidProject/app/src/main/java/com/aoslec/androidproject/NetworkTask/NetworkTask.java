package com.aoslec.androidproject.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.DailyWeatherBean;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class NetworkTask extends AsyncTask<Integer,String,Object> {
    Context context=null;
    String mAddr=null;
    ProgressDialog progressDialog=null;
    ArrayList<CurrentWeatherBean> current_weathers;
    ArrayList<HourlyWeatherBean> hourly_weathers;
    ArrayList<DailyWeatherBean> daily_weathers;

    //Network Task를 검색, 입력, 수정, 삭제 구분없이 하나로 사용하기 위해 생성자 변수 추가
    String where=null;

    public NetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.current_weathers = new ArrayList<CurrentWeatherBean>();
        this.daily_weathers=new ArrayList<DailyWeatherBean>();
        this.hourly_weathers=new ArrayList<HourlyWeatherBean>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get . . . .");
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer=new StringBuffer();
        InputStream inputStream=null;
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader=null;
        String result=null;

        try{
            URL url=new URL(mAddr);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream=httpURLConnection.getInputStream();
                inputStreamReader=new InputStreamReader(inputStream);
                bufferedReader=new BufferedReader(inputStreamReader);

                while(true){
                    String strline=bufferedReader.readLine();
                    if(strline==null) break;
                    stringBuffer.append(strline+"\n");
                }
                if(where.equals("current")||where.equals("daily")||where.equals("hourly")){
                    parserSelect(stringBuffer.toString());
                }else{
                    result=parserAction(stringBuffer.toString());
                }
            }else{

            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader!=null) bufferedReader.close();
                if(inputStreamReader!=null) inputStreamReader.close();
                if(inputStream!=null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(where.equals("current")){
            return current_weathers;
        }else if(where.equals("hourly")){
            return hourly_weathers;
        }else if(where.equals("daily")){
            return daily_weathers;
        }else{
            return result;
        }
    }

    private String parserAction(String str){
        String returnValue=null;
        try{
            JSONObject jsonObject=new JSONObject(str);
            returnValue=jsonObject.getString("result");
        }catch(Exception e){
            e.printStackTrace();
        }

        return returnValue;
    }

    private void parserSelect(String str){
        try{
            current_weathers.clear();
            hourly_weathers.clear();
            daily_weathers.clear();
            JSONObject jsonObject=new JSONObject(str);
            String timezone=jsonObject.optString("timezone","위치 못 불러옴");
            long current_time=jsonObject.getJSONObject("current").optLong("dt",0);
            int current_temp=jsonObject.getJSONObject("current").optInt("temp",0);
            int current_feels_like=jsonObject.getJSONObject("current").optInt("feels_like",0);
            int current_humidity=jsonObject.getJSONObject("current").optInt("humidity",0);
            double current_uvi=jsonObject.getJSONObject("current").optDouble("uvi",0);
            JSONArray jsonArray_current=new JSONArray(jsonObject.getJSONObject("current").getString("weather"));
            for(int i=0;i<jsonArray_current.length();i++){
                JSONObject current_weatherInfo=(JSONObject)jsonArray_current.get(i);
                int current_id=current_weatherInfo.optInt("id",0);
                String current_main=current_weatherInfo.optString("main","날씨 못 불러옴");
                String current_description=current_weatherInfo.optString("description","설명 못 불러옴");

                CurrentWeatherBean current=new CurrentWeatherBean(timezone,current_time,current_temp,current_feels_like,current_humidity,current_uvi,current_id,current_main,current_description);
                current_weathers.add(current);
            }

            JSONArray jsonArray1=new JSONArray(jsonObject.getString("hourly"));
            JSONArray jsonArray2=new JSONArray(jsonObject.getString("daily"));




            for(int i=0;i<jsonArray1.length();i++){ //시간별 날짜 정보 jsonArray1 에서 처리
                JSONObject jsonObject2=(JSONObject)jsonArray1.get(i);
                long time=Long.parseLong(jsonObject2.optString("dt",""));
                String hourly_time=unixToHourTime(time);
                int hourly_temp=jsonObject2.optInt("temp",0);
                int hourly_feels_like=jsonObject2.optInt("feels_like",0);
                int hourly_humidity=jsonObject2.optInt("humidity",0);
                double hourly_uvi=jsonObject2.optDouble("uvi",0);
                int hourly_pop=(int)Math.round(jsonObject2.optDouble("pop",0)*100);
                JSONArray jsonArray3=new JSONArray(jsonObject2.getString("weather"));
                for(int j=0;j<jsonArray3.length();j++){
                    JSONObject jsonObject3=(JSONObject)jsonArray3.get(j);
                    int hourly_id=jsonObject3.optInt("id",0);
                    String hourly_main=jsonObject3.optString("main","날씨 못 가져옴");
                    String hourly_description=jsonObject3.optString("description","설명 못 가져옴");

                    HourlyWeatherBean list=new HourlyWeatherBean(hourly_id,hourly_main,hourly_description,hourly_time,hourly_temp,hourly_feels_like,hourly_humidity,hourly_uvi,hourly_pop);
                    hourly_weathers.add(list);

                }
            }

            for(int i=0;i<jsonArray2.length();i++){
                JSONObject jsonObject4=(JSONObject)jsonArray2.get(i);
                long time=Long.parseLong(jsonObject4.optString("dt",""));
                String daily_time=unixToDayTime(time);
                int daily_temp_min=jsonObject4.getJSONObject("temp").optInt("min",0);
                int daily_temp_max=jsonObject4.getJSONObject("temp").optInt("max",0);
                int daily_feels_like=jsonObject4.getJSONObject("feels_like").optInt("day",0);
                int daily_humidity=jsonObject4.optInt("humidity",0);
                int daily_pop=(int)Math.round(jsonObject4.optDouble("pop",0)*100);
                double daily_uvi=jsonObject4.optDouble("uvi",0);
                JSONArray jsonArray4=new JSONArray(jsonObject4.getString("weather"));
                for(int j=0;j<jsonArray4.length();j++){
                    JSONObject jsonObject5=(JSONObject)jsonArray4.get(j);
                    int daily_id=jsonObject5.optInt("id",0);
                    String daily_main=jsonObject5.optString("main","날씨 못 가져옴");
                    String daily_description=jsonObject5.optString("description","설명 못 가져옴");

                    DailyWeatherBean list=new DailyWeatherBean(daily_id,daily_main,daily_description,daily_time,daily_temp_max,daily_temp_min,daily_feels_like,daily_humidity,daily_uvi,daily_pop);
                    daily_weathers.add(list);
                }
            }




        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //unix 시간 변환
    private String unixToDayTime(Long time){
        Date date=new Date(time*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("MM.dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String formattedTime=sdf.format(date);
        return formattedTime;
    }

    private String unixToHourTime(Long time){
        Date date=new Date(time*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:00");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String formattedTime=sdf.format(date);
        return formattedTime;
    }

    // -------------------관리자 페이지 부분 ---------------




}

