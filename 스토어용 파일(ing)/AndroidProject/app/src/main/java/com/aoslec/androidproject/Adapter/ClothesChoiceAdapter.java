package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aoslec.androidproject.Bean.ChoiceBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ClothesChoiceAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<ChoiceBean> data = null;
    private LayoutInflater inflater = null;
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
    String clothesColor;

    public ClothesChoiceAdapter(Context mContext, int layout, ArrayList<ChoiceBean> data) {
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
        return data.get(position).getItem();
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

        ImageView iv = convertView.findViewById(R.id.Choice_iv);
        ImageView ivc = convertView.findViewById(R.id.Choice_ivC);

        Glide.with(convertView)
                .load(getImage(clothesColor+data.get(position).getItem()))
                .error("")
                .into(iv);

        if (data.get(position).isSelect() == true){
            ivc.setVisibility(View.VISIBLE);
        }else {
            ivc.setVisibility(View.INVISIBLE);
        }

        return convertView;

    }

    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable",mContext.getPackageName());

        return drawableResourceId;
    }
}
