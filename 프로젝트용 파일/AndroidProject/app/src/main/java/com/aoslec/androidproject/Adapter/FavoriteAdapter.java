package com.aoslec.androidproject.Adapter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Bean.FavoriteLatLongBean;
import com.aoslec.androidproject.Fragment.Main_FavoriteFragment;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.SQLite.FavoriteInfo;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<FavoriteLatLongBean> data=null;
    private LayoutInflater inflater=null;

    public FavoriteAdapter(Context mcontext, int layout, ArrayList<FavoriteLatLongBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView favorite_current_location,favorite_current_temp;
        public ImageView favorite_current_heart;
        public LottieAnimationView favorite_laCover;
        public FavoriteInfo favoriteInfo;
        public ConstraintLayout favorite_currentView;
        SQLiteDatabase DB;
        public ViewHolder(View convertView){
            super(convertView);
            favorite_current_location=convertView.findViewById(R.id.favorite_current_location);
            favorite_current_temp=convertView.findViewById(R.id.favorite_current_temp);
            favorite_laCover=convertView.findViewById(R.id.favorite_laCover);
            favorite_current_heart=convertView.findViewById(R.id.favorite_current_heart);
            favoriteInfo=new FavoriteInfo(convertView.getContext());
            favorite_currentView=convertView.findViewById(R.id.favorite_currentView);

            favorite_currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();

                    if(position!=RecyclerView.NO_POSITION) {
                        SaveSharedPreferences.setLat(convertView.getContext(), data.get(position).getLatitude());
                        SaveSharedPreferences.setLong(convertView.getContext(), data.get(position).getLongitude());
                        SaveSharedPreferences.setLocation(convertView.getContext(), data.get(position).getLocation());
                    }

                    Toast.makeText(convertView.getContext(),"클릭됨!",Toast.LENGTH_SHORT).show();

                }
            });

            favorite_current_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB=favoriteInfo.getWritableDatabase();
                    int position=getAdapterPosition();
                    String lat=data.get(position).getLatitude();
                    String Long=data.get(position).getLongitude();

                    String query="select heart from favorite where latitude='" + lat + "' and longitude='"+Long+"';";
                    Cursor cursor=DB.rawQuery(query,null);
                    String heart="";
                    while(cursor.moveToNext()) {
                        heart=cursor.getString(0);
                    }


                    if(position!=RecyclerView.NO_POSITION){
                        if(heart.equals("Y")) {
                            String query2 = "UPDATE favorite set heart='N' where latitude='" + lat + "' and longitude='"+Long+"';";
                            DB.execSQL(query2);
                            favorite_current_heart.setImageResource(R.drawable.ic_favorite);
                            ((MainActivity)mcontext).refresh();
                        }
                        if(heart.equals("N")){
                            String query2 = "UPDATE favorite set heart='Y' where latitude='" + lat + "' and longitude='"+Long+"';";
                            DB.execSQL(query2);
                            favorite_current_heart.setImageResource(R.drawable.ic_favorite_red);
                        }
                    }


                }
            });
        }
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_current_weather,parent,false);
        FavoriteAdapter.ViewHolder viewHolder=new FavoriteAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
        holder.favorite_current_location.setText(data.get(position).getLocation());
        holder.favorite_current_temp.setText(Integer.toString(data.get(position).getTemp())+"°");

        int id=data.get(position).getId();

        if(id>=200&&id<=232) holder.favorite_laCover.setAnimation(R.raw.thunder_rain);
        else if(id>=300&&id<=321) holder.favorite_laCover.setAnimation(R.raw.rainy);
        else if(id>=500&&id<=531) holder.favorite_laCover.setAnimation(R.raw.rain);
        else if(id>=600&&id<=622) holder.favorite_laCover.setAnimation(R.raw.snow);
        else if(id==800) holder.favorite_laCover.setAnimation(R.raw.sunny);
        else if(id>=800&&id<=802) holder.favorite_laCover.setAnimation(R.raw.cloudy_sun);
        else if(id>=803) holder.favorite_laCover.setAnimation(R.raw.cloudy);
        else holder.favorite_laCover.setAnimation(R.raw.cloudy);

        int iTemp=data.get(position).getTemp();
        if (iTemp>=30){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color1));
        }else if (iTemp<30 && iTemp>=25){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color2));
        }else if (iTemp<25 && iTemp>=20){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color3));
        }else if (iTemp<20 && iTemp>=15){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color4));
        }else if (iTemp<15 && iTemp>=10){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color7));
        }else if (iTemp<10 && iTemp>=5){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color8));
        }else if (iTemp<5 && iTemp>=0){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color9));
        }else if (iTemp<0 && iTemp>=-5){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color10));
        }else if (iTemp<-5){
            holder.favorite_currentView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color11));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}

