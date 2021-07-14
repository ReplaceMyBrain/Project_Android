package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<HourlyWeatherBean> data=null;
    private LayoutInflater inflater=null;

    public HourlyWeatherAdapter(Context mcontext, int layout, ArrayList<HourlyWeatherBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView hourly_time,hourly_temp,hourly_pop;
        public LottieAnimationView hourly_LAweather;
        public int hourly_id;
        public ViewHolder(View convertView){
            super(convertView);
            hourly_time = convertView.findViewById(R.id.Hourly_time);
            hourly_temp=convertView.findViewById(R.id.Hourly_temp);
            hourly_pop=convertView.findViewById(R.id.Hourly_pop);
            hourly_LAweather=convertView.findViewById(R.id.hourly_LAweather);
        }
    }

    @Override
    public HourlyWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hourly_weather,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourlyWeatherAdapter.ViewHolder holder, int position) {
        holder.hourly_temp.setText(data.get(position).getHourly_temp()+"°");
        holder.hourly_pop.setText(mcontext.getString(R.string.pop)+" "+data.get(position).getHourly_pop()+"%");
        holder.hourly_time.setText(data.get(position).getHourly_time());

        int id=data.get(position).getHourly_id();

        if(id>=200&&id<=232) holder.hourly_LAweather.setAnimation(R.raw.thunder_rain);
        else if(id>=300&&id<=321) holder.hourly_LAweather.setAnimation(R.raw.rainy);
        else if(id>=500&&id<=531) holder.hourly_LAweather.setAnimation(R.raw.rain);
        else if(id>=600&&id<=622) holder.hourly_LAweather.setAnimation(R.raw.snow);
        else if(id==800) holder.hourly_LAweather.setAnimation(R.raw.sunny);
        else if(id>=800&&id<=802) holder.hourly_LAweather.setAnimation(R.raw.cloudy_sun);
        else if(id>=803) holder.hourly_LAweather.setAnimation(R.raw.cloudy);
        else holder.hourly_LAweather.setAnimation(R.raw.cloudy);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}

