package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.Ad_requestBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.ShareVar;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Admin_RequestAdapter extends BaseAdapter {

    private Context mContext = null;
    private  int layout = 0;     // 리스트뷰하나나에 있는 레이아웃
    private ArrayList<Ad_requestBean> data = null;
    private LayoutInflater inflater = null;


    public Admin_RequestAdapter(Context mContext, int layout, ArrayList<Ad_requestBean> data) {
        Log.v("ggg","어댑터 들어옴");

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
        return data.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String sUrl = ShareVar.sUrl;

        convertView = inflater.inflate(this.layout,parent,false);
        TextView title = convertView.findViewById(R.id.approve_simple_title);

        ImageView iv_image = convertView.findViewById(R.id.approve_simple_image);


        title.setText("광고명 : " + data.get(position).getTitle());

        Glide.with(mContext)
                .load(sUrl+"adImage/"+data.get(position).getImage())
                .error(R.drawable.ad)
                .into(iv_image);
        Log.v("ggg", "Glide.with 완료");


        return convertView;
    }
}
