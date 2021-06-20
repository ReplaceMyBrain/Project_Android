package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.WeatherBean;
import com.aoslec.androidproject.R;

import java.util.ArrayList;



public class WeatherAdapter extends BaseAdapter {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<WeatherBean> data=null;
    private LayoutInflater inflater=null;

    public WeatherAdapter(Context mcontext, int layout, ArrayList<WeatherBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getDateTimeISO();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);
        TextView dateTimeISO = convertView.findViewById(R.id.list_dateTimeISO);
        TextView maxTempC = convertView.findViewById(R.id.list_maxTempC);
        TextView minTempC = convertView.findViewById(R.id.list_minTempC);
        TextView avgTempC = convertView.findViewById(R.id.list_avgTempC);
        TextView tempC = convertView.findViewById(R.id.list_tempC);
        TextView maxFeelslikeC = convertView.findViewById(R.id.list_maxFeelslikeC);
        TextView minFeelslikeC = convertView.findViewById(R.id.list_minFeelslikeC);
        TextView avgFeelslikeC = convertView.findViewById(R.id.list_avgFeelslikeC);
        TextView feelslikeC = convertView.findViewById(R.id.list_feelslikeC);
        TextView pop = convertView.findViewById(R.id.list_pop);
        TextView weather = convertView.findViewById(R.id.list_weather);
        TextView icon = convertView.findViewById(R.id.list_icon);

        dateTimeISO.setText("시간 : " + data.get(position).getDateTimeISO());
        maxTempC.setText("최고기온 : " + data.get(position).getMaxTempC()+"도");
        minTempC.setText("최저기온  : " + data.get(position).getMinTempC()+"도");
        avgTempC.setText("평균기온 : " + data.get(position).getAvgTempC()+"도");
        tempC.setText("현재기온 : " + data.get(position).getTempC()+"도");
        maxFeelslikeC.setText("체감 최고기온 : " + data.get(position).getMaxFeelslikeC()+"도");
        minFeelslikeC.setText("체감 최저기온 : " + data.get(position).getMinFeelslikeC()+"도");
        avgFeelslikeC.setText("체감 평균기온 : " + data.get(position).getAvgFeelslikeC()+"도");
        feelslikeC.setText("체감 현재기온 : " + data.get(position).getFeelslikeC()+"도");
        pop.setText("강수확률 : " + data.get(position).getPop()+"%");
        weather.setText("날씨 : " + data.get(position).getWeather());
        icon.setText("이미지 : " + data.get(position).getIcon());


        return convertView;
    }
}

