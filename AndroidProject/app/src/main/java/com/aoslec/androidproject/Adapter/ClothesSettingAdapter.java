package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.androidproject.Activity.ClothesActivity;
import com.aoslec.androidproject.Bean.ClothesBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ClothesSettingAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<ClothesBean> data = null;
    private LayoutInflater inflater = null;

    public ClothesSettingAdapter(Context mContext, int layout, ArrayList<ClothesBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getTemperature();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        String url = saveSharedPreferences.getUrl(convertView.getContext());
        String clothesColor = saveSharedPreferences.getClothesColor(convertView.getContext());

        TextView temp = convertView.findViewById(R.id.ClothesList_temp);
        ImageView item1 = convertView.findViewById(R.id.ClothesList_item1);
        ImageView item2 = convertView.findViewById(R.id.ClothesList_item2);
        ImageView item3 = convertView.findViewById(R.id.ClothesList_item3);
        ImageView item4 = convertView.findViewById(R.id.ClothesList_item4);
        ImageView item5 = convertView.findViewById(R.id.ClothesList_item5);

        temp.setText(data.get(position).getTemperature());
        Glide.with(convertView)
                .load(url+clothesColor+data.get(position).getItem1())
                .into(item1);
        Glide.with(convertView)
                .load(url+clothesColor+data.get(position).getItem2())
                .into(item2);
        Glide.with(convertView)
                .load(url+clothesColor+data.get(position).getItem3())
                .into(item3);
        Glide.with(convertView)
                .load(url+clothesColor+data.get(position).getItem4())
                .into(item4);
        Glide.with(convertView)
                .load(url+clothesColor+data.get(position).getItem5())
                .into(item5);


        switch (position){
            case 1: convertView.setBackgroundColor(0x9BF6FF);
                    break;
            case 2: convertView.setBackgroundColor(0xA0C4FF);
                break;
            case 3: convertView.setBackgroundColor(0xBDB2FF);
                break;
            case 4: convertView.setBackgroundColor(0xFFC6FF);
                break;
            case 5: convertView.setBackgroundColor(0xFDFFB6);
                break;
            case 6: convertView.setBackgroundColor(0xCAFFBF);
                break;
            case 7: convertView.setBackgroundColor(0xFDFFBF);
                break;
            case 8: convertView.setBackgroundColor(0xFFD6A5);
                break;
            case 9: convertView.setBackgroundColor(0xFFADAD);
                break;

        }

        return convertView;
    }
}
