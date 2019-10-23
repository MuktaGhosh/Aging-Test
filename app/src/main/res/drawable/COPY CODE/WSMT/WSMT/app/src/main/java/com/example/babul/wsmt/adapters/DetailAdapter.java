package com.example.babul.wsmt.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.babul.wsmt.R;
import com.example.babul.wsmt.models.ItemsDetail;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {

    private List<ItemsDetail> pList;
    private Context context;
    private int value;



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumOfReels, tvRemaingReel, tvReelSize;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvNumOfReels = view.findViewById(R.id.tvNumOfReel);
            tvRemaingReel = view.findViewById(R.id.tvReamingReel);
            tvRemaingReel.setTextColor(Color.BLACK);
            tvReelSize = view.findViewById(R.id.tvReelSize);
        }
    }


    public DetailAdapter(Context mContext, List<ItemsDetail> moviesList) {
        context = mContext;
        this.pList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tvName.setText(pList.get(position).getItemName());
        holder.tvNumOfReels.setText(pList.get(position).getNumberOfReels());
        holder.tvRemaingReel.setText(pList.get(position).getReels_scaned());
        holder.tvReelSize.setText(pList.get(position).getReelSize());

    }

    @Override
    public int getItemCount() {
        return pList.size();

    }
}


