package com.angeljedi.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.domain.Review;
import com.angeljedi.popularmovies.ui.widgets.ReviewViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private Context context;

    private List<Review> reviewList = new ArrayList<>();

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.getAuthorTextView().setText(review.getAuthor());
        holder.getContentTextView().setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void changeReviewList(List<Review> reviewList) {
        if (reviewList != null) {
            this.reviewList = reviewList;
            notifyDataSetChanged();
        }
    }
}
