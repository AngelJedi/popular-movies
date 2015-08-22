package com.angeljedi.popularmovies.ui.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.domain.Trailer;

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    private ImageView playImageView;
    private TextView nameTextView;
    private ClickListener clickListener;

    private Trailer trailer;

    public TrailerViewHolder(View view, ClickListener clickListener) {
        super(view);
        nameTextView = (TextView) view.findViewById(R.id.trailer_name);
        playImageView = (ImageView) view.findViewById(R.id.play);
        playImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playClicked();
            }
        });
        this.clickListener = clickListener;
    }

    private void playClicked() {
        clickListener.onPlayClicked(trailer.getKey());
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public interface ClickListener {
        void onPlayClicked(String trailerKey);
    }
}
