package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.androidproject.Bean.FavoriteHistoryBean;
import com.aoslec.androidproject.Bean.SelectBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.SQLite.FavoriteInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteHistoryAdapter extends RecyclerView.Adapter<FavoriteHistoryAdapter.ViewHolder> {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<FavoriteHistoryBean> data=null;
    private LayoutInflater inflater=null;

    public FavoriteHistoryAdapter(Context mcontext, int layout, ArrayList<FavoriteHistoryBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView history_location;
        public ImageView history_heart;
        public FavoriteInfo favoriteInfo;
        public ArrayList<SelectBean> selectBeans;
        public ViewHolder(View convertView){
            super(convertView);
            history_location=convertView.findViewById(R.id.history_location);
            history_heart=convertView.findViewById(R.id.history_heart);
            favoriteInfo=new FavoriteInfo(convertView.getContext());
            selectBeans=new ArrayList<SelectBean>();

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();

                    if(position!=RecyclerView.NO_POSITION) {
                        SaveSharedPreferences.setLat(convertView.getContext(), data.get(position).getLatitude());
                        SaveSharedPreferences.setLong(convertView.getContext(), data.get(position).getLongitude());
                        SaveSharedPreferences.setLocation(convertView.getContext(), data.get(position).getLocation());
                    }

                }
            });

            history_heart.setOnClickListener(new View.OnClickListener() {
                SQLiteDatabase DB;
                @Override
                public void onClick(View v) {
                    DB=favoriteInfo.getWritableDatabase();
                    int position=getAdapterPosition();
                    int id=data.get(position).getId();
                    String query="select heart from favorite where id='"+id+"';";
                    Cursor cursor=DB.rawQuery(query,null);
                    String heart="";
                    while(cursor.moveToNext()) {
                        heart=cursor.getString(0);
                    }

                    if(position!=RecyclerView.NO_POSITION){
                        if(heart.equals("Y")) {
                            String query2 = "UPDATE favorite set heart='N' where id='" + id + "';";
                            DB.execSQL(query2);
                            history_heart.setImageResource(R.drawable.ic_favorite);
                        }
                        if(heart.equals("N")){
                            String query2 = "UPDATE favorite set heart='Y' where id='" + id + "';";
                            DB.execSQL(query2);
                            history_heart.setImageResource(R.drawable.ic_favorite_red);
                        }
                    }

                }
            });
        }
    }

    @Override
    public FavoriteHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_history_location,parent,false);
        FavoriteHistoryAdapter.ViewHolder viewHolder=new FavoriteHistoryAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteHistoryAdapter.ViewHolder holder, int position) {
        holder.history_location.setText(data.get(position).getLocation());
        if(data.get(position).getHeart().equals("Y")) holder.history_heart.setImageResource(R.drawable.ic_favorite_red);
        else holder.history_heart.setImageResource(R.drawable.ic_favorite);
    }

    @Override
    public int getItemCount() {
        if(data.size()>5) return 5;
        else return data.size();
    }



}

