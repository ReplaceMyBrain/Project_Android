package com.aoslec.androidproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.aoslec.androidproject.Bean.ExplainBean;
import com.aoslec.androidproject.R;

import java.util.List;

public class ExplainAdapter extends RecyclerView.Adapter<ExplainAdapter.MyViewHolder> {

    List<ExplainBean> list;

    public ExplainAdapter(List<ExplainBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.explain_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExplainAdapter.MyViewHolder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.contents.setText(list.get(position).getContents());
        holder.img.setAnimation(list.get(position).getImages());
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView contents;
        LottieAnimationView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Explain_Title);
            contents = itemView.findViewById(R.id.Explain_Contents);
            img = itemView.findViewById(R.id.Explain_image);




        }
    }
}
