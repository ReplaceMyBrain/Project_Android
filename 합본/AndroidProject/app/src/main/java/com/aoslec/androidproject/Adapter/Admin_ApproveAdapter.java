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

public class Admin_ApproveAdapter extends BaseAdapter {

    private Context mContext = null;
    private  int layout = 0;     // 리스트뷰아나에 있는 레이아웃
    private ArrayList<Ad_requestBean> data = null;
    private LayoutInflater inflater = null;

    public Admin_ApproveAdapter(Context mContext, int layout, ArrayList<Ad_requestBean> data) {
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
        convertView = inflater.inflate(this.layout,parent,false);
        Log.v("Massage", data.get(position).getTitle());

        String sUrl = ShareVar.sUrl;

        TextView item_title = convertView.findViewById(R.id.item_title);
        ImageView item_image = convertView.findViewById(R.id.item_image);

        item_title.setText("광고명 : " + data.get(position).getTitle());

        Glide.with(mContext)
                .load(sUrl+"adImage/"+data.get(position).getImage())
                .error(R.drawable.ad)
                .into(item_image);


        return convertView;
    }
}
