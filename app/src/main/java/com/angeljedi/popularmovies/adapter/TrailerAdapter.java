package com.angeljedi.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.domain.Trailer;
import com.angeljedi.popularmovies.ui.widgets.TrailerViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private Context context;

    private List<Trailer> trailerList = new ArrayList<>();
    private TrailerViewHolder.ClickListener clickListener;

    public TrailerAdapter(Context context, TrailerViewHolder.ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        holder.setTrailer(trailer);
        holder.getNameTextView().setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void changeTrailerList(List<Trailer> trailerList) {
        if (trailerList != null) {
            this.trailerList = trailerList;
            notifyDataSetChanged();
        }
    }
}
