package com.example.babul.wsmt.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.babul.wsmt.R;
import com.example.babul.wsmt.models.Handset;
import com.example.babul.wsmt.models.ItemsDetail;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {


    private ClickCallback callback;
    private List<Handset> pList;
    private Context context;
    private int value;

    public interface ClickCallback {
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumOfReels, tvRemaingReel, tvReelSize;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);        }
    }


    public MyListAdapter(Context mContext, List<Handset> moviesList, ClickCallback clickCallback) {
        context = mContext;
        this.pList = moviesList;
        this.callback = clickCallback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylist_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tvName.setText(pList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                holder.itemView.setBackgroundColor(context.getColor(R.color.colorPrimary));
                holder.tvName.setTextColor(context.getColor(R.color.white));
                callback.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pList.size();

    }
}



