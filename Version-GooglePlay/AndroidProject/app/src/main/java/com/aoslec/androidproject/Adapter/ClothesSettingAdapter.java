package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.aoslec.androidproject.Bean.ClothesBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ClothesSettingAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<ClothesBean> data = null;
    private LayoutInflater inflater = null;
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
    String clothesColor;

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

        clothesColor = saveSharedPreferences.getClothesColor(mContext);

        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
        }

        CardView cardView = convertView.findViewById(R.id.clothesList_card);
        TextView temp = convertView.findViewById(R.id.ClothesList_temp);
        ImageView item1 = convertView.findViewById(R.id.ClothesList_item1);
        ImageView item2 = convertView.findViewById(R.id.ClothesList_item2);
        ImageView item3 = convertView.findViewById(R.id.ClothesList_item3);
        ImageView item4 = convertView.findViewById(R.id.ClothesList_item4);
        ImageView item5 = convertView.findViewById(R.id.ClothesList_item5);

        temp.setText(data.get(position).getTemperature());
        Glide.with(convertView)
                .load(getImage(clothesColor+data.get(position).getItem1()))
                .error("")
                .into(item1);
        Glide.with(convertView)
                .load(getImage(clothesColor+data.get(position).getItem2()))
                .error("")
                .into(item2);
        Glide.with(convertView)
                .load(getImage(clothesColor+data.get(position).getItem3()))
                .error("")
                .into(item3);
        Glide.with(convertView)
                .load(getImage(clothesColor+data.get(position).getItem4()))
                .error("")
                .into(item4);
        Glide.with(convertView)
                .load(getImage(clothesColor+data.get(position).getItem5()))
                .error("")
                .into(item5);

        if(position == 0) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color11));
        }
        if(position == 1) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color10));
        }
        if(position == 2) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color9));
        }
        if(position == 3) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color8));
        }
        if(position == 4) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color7));
        }
        if(position == 5) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color4));
        }
        if(position == 6) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color3));
        }
        if(position == 7) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color2));
        }
        if(position == 8) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color1));
        }

        return convertView;
    }

    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable",mContext.getPackageName());

        return drawableResourceId;
    }
}//
