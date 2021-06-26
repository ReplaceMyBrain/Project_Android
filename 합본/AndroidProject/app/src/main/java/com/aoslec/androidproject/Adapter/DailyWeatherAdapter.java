package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.aoslec.androidproject.Bean.DailyWeatherBean;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<DailyWeatherBean> data=null;
    private LayoutInflater inflater=null;

    public DailyWeatherAdapter(Context mcontext, int layout, ArrayList<DailyWeatherBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView list_daily_pop,list_daily_temp_min,list_daily_temp_max,list_daily_time;
        public LottieAnimationView daily_LAweather;
        public int hourly_id;
        public ViewHolder(View convertView){
            super(convertView);
            list_daily_pop = convertView.findViewById(R.id.list_daily_pop);
            list_daily_temp_min = convertView.findViewById(R.id.list_daily_temp_max);
            list_daily_temp_max = convertView.findViewById(R.id.list_daily_temp_min);
            list_daily_time = convertView.findViewById(R.id.list_daily_time);
            daily_LAweather=convertView.findViewById(R.id.daily_LAweather);
        }
    }

    @NonNull
    @NotNull
    @Override
    public DailyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_day_weather,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.list_daily_pop.setText("강수확률 : " + data.get(position).getDaily_pop()+"%");
        holder.list_daily_temp_max.setText("최고 : " + data.get(position).getDaily_temp_max()+"°");
        holder.list_daily_temp_min.setText("최저 : " + data.get(position).getDaily_temp_min()+"°");
        holder.list_daily_time.setText(data.get(position).getDaily_time());

        int id=data.get(position).getDaily_id();

        if(id>=200&&id<=232) holder.daily_LAweather.setAnimation(R.raw.thunder_rain);
        else if(id>=300&&id<=321) holder.daily_LAweather.setAnimation(R.raw.rainy);
        else if(id>=500&&id<=531) holder.daily_LAweather.setAnimation(R.raw.rain);
        else if(id>=600&&id<=622) holder.daily_LAweather.setAnimation(R.raw.snow);
        else if(id==800) holder.daily_LAweather.setAnimation(R.raw.sunny);
        else if(id>=800&&id<=802) holder.daily_LAweather.setAnimation(R.raw.cloudy_sun);
        else if(id>=803) holder.daily_LAweather.setAnimation(R.raw.cloudy);
        else holder.daily_LAweather.setAnimation(R.raw.cloudy);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}


