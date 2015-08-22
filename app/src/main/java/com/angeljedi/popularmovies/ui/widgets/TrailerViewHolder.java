package com.angeljedi.popularmovies.ui.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.angeljedi.popularmovies.R;

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private ClickListener clickListener;

    public TrailerViewHolder(View view, ClickListener clickListener) {
        super(view);
        nameTextView = (TextView) view.findViewById(R.id.trailer_name);
        this.clickListener = clickListener;
    }

    private void movieClicked() {
        clickListener.onTrailerClicked(getAdapterPosition());
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public interface ClickListener {
        void onTrailerClicked(int position);
    }
}
