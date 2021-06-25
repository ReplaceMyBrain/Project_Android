package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.User;
import com.aoslec.androidproject.R;

import java.util.ArrayList;

/**
 * Created by biso on 2021/06/22.
 */
public class MyPage_User_Adapter extends BaseAdapter {

    private Context mContext = null;
    private  int layout = 0;
    private ArrayList<User> data = null;
    private LayoutInflater inflater = null;

    public MyPage_User_Adapter(Context mContext, int layout, ArrayList<User> data) {
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
        return data.get(position).getEmail();
    }

    @Override
    public long getItemId(int position) {
        //getItemId -> getItem
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);
        TextView tv_1 = convertView.findViewById(R.id.linear2_tv1);
        TextView tv_2 = convertView.findViewById(R.id.linear2_tv2);
        TextView tv_3 = convertView.findViewById(R.id.linear2_tv3);
        TextView tv_4 = convertView.findViewById(R.id.linear2_tv4);
        TextView tv_5 = convertView.findViewById(R.id.linear2_tv5);
        TextView tv_6 = convertView.findViewById(R.id.linear2_tv6);

//        tv_1.setText("학번 : "+ data.get(position).getScode());
//        tv_2.setText("이름 : "+ data.get(position).getSname());
//        tv_3.setText("전공 : "+ data.get(position).getSdept());
//        tv_4.setText("전화번호 : "+ data.get(position).getSphone());

        return convertView;
    }
}