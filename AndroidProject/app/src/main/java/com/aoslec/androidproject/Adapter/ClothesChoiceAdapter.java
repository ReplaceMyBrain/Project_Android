package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.androidproject.Bean.ChoiceBean;
import com.aoslec.androidproject.Bean.ClothesBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ClothesChoiceAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<ChoiceBean> data = null;
    private LayoutInflater inflater = null;
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
    String url,clothesColor;

    public ClothesChoiceAdapter(Context mContext, int layout, ArrayList<ChoiceBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.v("ggg", "아탑터 안이다!");
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

        Log.v("ggg", "아탑터 겟뷰 시작");

        url = saveSharedPreferences.getUrl(mContext);
        clothesColor = saveSharedPreferences.getClothesColor(mContext);

        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
        }

        TextView cb = convertView.findViewById(R.id.Choice_cb);
        ImageView iv = convertView.findViewById(R.id.Choice_iv);

        cb.setText(data.get(position).getItem());

        Glide.with(convertView)
                .load(url+clothesColor+data.get(position).getItem())
                .error("")
                .into(iv);

        return convertView;

    }
}
