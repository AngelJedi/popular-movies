package com.angeljedi.popularmovies.ui.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.angeljedi.popularmovies.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    private TextView authorTextView;
    private TextView contentTextView;

    public ReviewViewHolder(View view) {
        super(view);
        authorTextView = (TextView) view.findViewById(R.id.review_author);
        contentTextView = (TextView) view.findViewById(R.id.review_content);
    }

    public TextView getAuthorTextView() {
        return authorTextView;
    }

    public TextView getContentTextView() {
        return contentTextView;
    }
}
