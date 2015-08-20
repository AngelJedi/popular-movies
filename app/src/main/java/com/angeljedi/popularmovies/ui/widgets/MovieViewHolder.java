package com.angeljedi.popularmovies.ui.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.angeljedi.popularmovies.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private ImageView posterImageView;
    private ClickListener clickListener;

    public MovieViewHolder(View view, ClickListener clickListener) {
        super(view);
        posterImageView = (ImageView) view.findViewById(R.id.movie_thumbnail);
        posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClicked();
            }
        });
        this.clickListener = clickListener;
    }

    private void movieClicked() {
        clickListener.onMovieClicked(getAdapterPosition());
    }

    public ImageView getPosterImageView() {
        return posterImageView;
    }

    public interface ClickListener {
        void onMovieClicked(int position);
    }
}
