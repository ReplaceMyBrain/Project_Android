package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.Ad_PaymentBean;
import com.aoslec.androidproject.R;

import java.util.ArrayList;

/**
 * Created by biso on 2021/06/24.
 */
public class CountAdpter extends BaseAdapter {

    String urlAddr;

    private ArrayList<Ad_PaymentBean> data = null;
    private Context mContext = null;
    private  int layout = 0;
    private LayoutInflater inflater = null;

    public CountAdpter(Context mContext, int layout, ArrayList<Ad_PaymentBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.v("Message", String.valueOf(data.size()));
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getAd_count();
    }

    @Override
    public long getItemId(int position) {
        //getItemId -> getItem
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);

        TextView tv_title;
        TextView tv_link;
        TextView tv_indate;
        TextView tv_outdate;

        tv_title = convertView.findViewById(R.id.tv_title_custom_ad);
        tv_link = convertView.findViewById(R.id.tv_link_custom_ad);
        tv_indate = convertView.findViewById(R.id.tv_indate_custom_ad);
        tv_outdate = convertView.findViewById(R.id.tv_outdate_custom_ad);


        tv_title.setText(data.get(position).getAd_title());
        tv_link.setText(data.get(position).getAd_url());
        tv_indate.setText(data.get(position).getAd_indate());
        tv_outdate.setText(data.get(position).getAd_outdate());

        return convertView;
    }

}
