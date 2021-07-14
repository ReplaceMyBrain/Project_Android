package com.aoslec.androidproject.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.aoslec.androidproject.Activity.GPSActivity;
import com.aoslec.androidproject.Adapter.DailyWeatherAdapter;
import com.aoslec.androidproject.Adapter.HourlyWeatherAdapter;
import com.aoslec.androidproject.Bean.ClothesBean;
import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.DailyWeatherBean;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.NetworkTask.NetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SQLite.ClothesSQLite;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Main_WeatherFragment extends Fragment {
    RecyclerView rvHourWeather;
    RecyclerView rvDailyWeather;
    RecyclerView.Adapter hourlyAdapter;
    RecyclerView.Adapter dailyAdapter;
    RecyclerView.LayoutManager hourlylayoutManager,dailylayoutManager;
    LinearLayout main_GPS, dayLL;


    ArrayList<CurrentWeatherBean> current_weathers;
    ArrayList<HourlyWeatherBean> hourly_weathers;
    ArrayList<DailyWeatherBean> daily_weathers;

    CurrentWeatherBean current_weather;

    TextView main_tvTemp,main_tvLocation,main_time;
    LottieAnimationView main_laCover;

    private String Lat="";
    private String Long="";
    private String Location="";


    //옷차림 추천
    ClothesSQLite clothesSQLite;
    ArrayList<ClothesBean> clothesBeans = new ArrayList<>();
    ImageView item1, item2, item3, item4, item5, item6;
    String sTemp,sItem1, sItem2, sItem3, sItem4, sItem5;
    String url,clothesColor;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_weather, container, false);


        //옷차림 추천란
        clothesSQLite = new ClothesSQLite(getActivity());

        item1 = view.findViewById(R.id.main_item1);
        item2 = view.findViewById(R.id.main_item2);
        item3 = view.findViewById(R.id.main_item3);
        item4 = view.findViewById(R.id.main_item4);
        item5 = view.findViewById(R.id.main_item5);
        item6 = view.findViewById(R.id.main_item6);


        url = SaveSharedPreferences.getUrl(getContext());
        clothesColor = SaveSharedPreferences.getClothesColor(getContext());

        //////////

        dayLL = view.findViewById(R.id.main_weather_LL);

        rvHourWeather=view.findViewById(R.id.main_rvHourWeather);
        rvDailyWeather=view.findViewById(R.id.main_rvDailyWeather);

        hourlylayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        hourlylayoutManager.canScrollHorizontally();
        dailylayoutManager=new LinearLayoutManager(getContext());

        rvHourWeather.setLayoutManager(hourlylayoutManager);
        rvDailyWeather.setLayoutManager(dailylayoutManager);
        main_tvTemp=view.findViewById(R.id.main_tvTemp);
        main_tvLocation=view.findViewById(R.id.main_tvLocation);
        main_laCover=view.findViewById(R.id.main_laCover);
        main_GPS=view.findViewById(R.id.main_GPS);
        main_time=view.findViewById(R.id.main_time);

        Long=SaveSharedPreferences.getLong(getContext());
        Lat=SaveSharedPreferences.getLat(getContext());
        Location=SaveSharedPreferences.getLocation(getContext());

        GetTime();
        GetCurrentData();
        GetHourlyData();
        GetDailyData();

        main_GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), GPSActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Long=SaveSharedPreferences.getLong(getContext());
        Lat=SaveSharedPreferences.getLat(getContext());
        Location=SaveSharedPreferences.getLocation(getContext());

        GetTime();
        GetCurrentData();
        GetHourlyData();
        GetDailyData();

        //옷차림 추천
        GetClothes();
    }

    //옷차림 메소드
    private void GetClothes() {
        SQLiteDatabase DB;

       // int iTemp = Integer.parseInt(main_tvTemp.getText().toString().trim().substring(0,main_tvTemp.getText().toString().trim().length()-1));

        int iTemp = 6;

        Log.v("ggg","iTemp? " + iTemp);
        //범위 정해주기
        if (iTemp>=30){
            sTemp = "30º ▲";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color1));
        }else if (iTemp<30 && iTemp>=25){
            sTemp = "25º ~ 30º";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color2));
        }else if (iTemp<25 && iTemp>=20){
            sTemp = "20º ~ 25º";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color3));
        }else if (iTemp<20 && iTemp>=15){
            sTemp = "15º ~ 20º";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color4));
        }else if (iTemp<15 && iTemp>=10){
            sTemp = "10º ~ 15º";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color7));
        }else if (iTemp<10 && iTemp>=5){
            sTemp = "5º ~ 10º";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color8));
        }else if (iTemp<5 && iTemp>=0){
            sTemp = "0º ~ 5º";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color9));
        }else if (iTemp<0 && iTemp>=-5){
            sTemp = "0º ~ -5º";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color10));
        }else if (iTemp<-5){
            sTemp = "-5º ▼";
            dayLL.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color11));
        }

        Log.v("ggg","sTemp? " + sTemp);
        //값 가져오기
        try{
            clothesBeans.clear();
            DB = clothesSQLite.getReadableDatabase();
            String query = "Select item1, item2, item3, item4, item5 FROM clothes WHERE Temperature='"+ sTemp + "';";
            Cursor cursor = DB.rawQuery(query, null);

            while (cursor.moveToNext()){
                sItem1 = cursor.getString(0);
                sItem2 = cursor.getString(1);
                sItem3 = cursor.getString(2);
                sItem4 = cursor.getString(3);
                sItem5 = cursor.getString(4);
                Log.v("ggg","sItem1? " + sItem1);

                ClothesBean item = new ClothesBean(sItem1,sItem2,sItem3,sItem4,sItem5);
                clothesBeans.add(item);
            }
            cursor.close();
            clothesSQLite.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("ggg","값 가져옴?? " + clothesBeans.get(0).getItem1());
        //뷰어 넣어주기
        Glide.with(getActivity())
                .load(url+clothesColor+clothesBeans.get(0).getItem1())
                .error("")
                .into(item1);
        Glide.with(getActivity())
                .load(url+clothesColor+clothesBeans.get(0).getItem2())
                .error("")
                .into(item2);
        Glide.with(getActivity())
                .load(url+clothesColor+clothesBeans.get(0).getItem3())
                .error("")
                .into(item3);
        Glide.with(getActivity())
                .load(url+clothesColor+clothesBeans.get(0).getItem4())
                .error("")
                .into(item4);
        Glide.with(getActivity())
                .load(url+clothesColor+clothesBeans.get(0).getItem5())
                .error("")
                .into(item5);
    }

    private void GetTime(){
        long now=System.currentTimeMillis();
        Date mDate=new Date(now);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM.dd  hh:mm");
        String getTime=simpleDateFormat.format(mDate);

        main_time.setText(getTime);
    }

    private void GetDailyData(){
        try{
            String urlAddr="https://api.openweathermap.org/data/2.5/onecall?lat="+Lat+"&lon="+Long+"&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"daily");
            Object obj=networkTask.execute().get();
            daily_weathers= (ArrayList<DailyWeatherBean>) obj;

            dailyAdapter=new DailyWeatherAdapter(getActivity(),R.layout.recycler_day_weather,daily_weathers);
            rvDailyWeather.setAdapter(dailyAdapter);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void GetHourlyData(){
        try{
            String urlAddr="https://api.openweathermap.org/data/2.5/onecall?lat="+Lat+"&lon="+Long+"&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"hourly");
            Object obj=networkTask.execute().get();
            hourly_weathers= (ArrayList<HourlyWeatherBean>) obj;

            hourlyAdapter=new HourlyWeatherAdapter(getActivity(),R.layout.recycler_hourly_weather,hourly_weathers);
            rvHourWeather.setAdapter(hourlyAdapter);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void GetCurrentData(){
        main_tvTemp=getActivity().findViewById(R.id.main_tvTemp);
        main_tvLocation=getActivity().findViewById(R.id.main_tvLocation);
        main_laCover=getActivity().findViewById(R.id.main_laCover);
        try{
            String urlAddr="https://api.openweathermap.org/data/2.5/onecall?lat="+Lat+"&lon="+Long+"&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

            Log.d("main_weather",urlAddr);

            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"current");
            Object obj=networkTask.execute().get();
            current_weathers= (ArrayList<CurrentWeatherBean>) obj;

            current_weather= current_weathers.get(0);

            //발표용 날씨조작
            main_tvTemp.setText(6+"°");

            //main_tvTemp.setText(Integer.toString(current_weather.getCurrent_temp())+"°");
            main_tvLocation.setText(Location);

            //int id=current_weather.getCurrent_id();

            //발표용 날씨조작
             int id = 500;

            if(id>=200&&id<=232) {main_laCover.setAnimation(R.raw.thunder_rain); rainItem();}
            else if(id>=300&&id<=321) {main_laCover.setAnimation(R.raw.rainy);rainItem();}
            else if(id>=500&&id<=531) {main_laCover.setAnimation(R.raw.rain);rainItem();}
            else if(id>=600&&id<=622) main_laCover.setAnimation(R.raw.snow);
            else if(id==800) {main_laCover.setAnimation(R.raw.sunny);sunnyItem();}
            else if(id>=800&&id<=802) main_laCover.setAnimation(R.raw.cloudy_sun);
            else if(id>=803) main_laCover.setAnimation(R.raw.cloudy);
            else main_laCover.setAnimation(R.raw.cloudy);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //날씨별 아이템 추가
    private void rainItem() {
        Glide.with(getActivity())
                .load(url+clothesColor+"ac2.png")
                .error("")
                .into(item6);
    }
    private void sunnyItem() {
        Glide.with(getActivity())
                .load(url+clothesColor+"ac1.png")
                .error("")
                .into(item6);
    }

}