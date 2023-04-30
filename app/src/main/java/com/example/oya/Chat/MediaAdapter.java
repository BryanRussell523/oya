package com.example.oya.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.R;

import java.util.ArrayList;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder>{
    ArrayList<String> mediaList;
    Context context;
    public MediaAdapter(Context context, ArrayList<String> mediaList){
        this.context = context;
        this.mediaList = mediaList;

    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media,null,false);
        MediaViewHolder mediaViewHolder = new MediaViewHolder(layoutView);

        return mediaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        //GLIDE IMPLEMENTATION WAS USED TO POPULATE IMAGEVIEW


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MediaViewHolder extends RecyclerView.ViewHolder{
        public MediaViewHolder(View itemView){
            super(itemView);
        }

    }
}
