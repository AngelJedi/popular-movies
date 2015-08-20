package com.angeljedi.popularmovies.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class AsyncLoader<T> extends AsyncTaskLoader<T> {
    private T data = null;

    public AsyncLoader(Context context) {
        super(context);
    }

    protected void onStartLoading() {
        if (this.data != null) {
            this.deliverResult(this.data);
        }

        if (this.takeContentChanged() || this.data == null) {
            this.forceLoad();
        }

    }

    protected void onStopLoading() {
        this.cancelLoad();
    }

    public void onCanceled(T data) {
        this.data = null;
    }

    protected void onReset() {
        super.onReset();
        this.onStopLoading();
        this.data = null;
    }

    public void deliverResult(T data) {
        if (!this.isReset()) {
            this.data = data;
            if (this.isStarted()) {
                super.deliverResult(data);
            }

        }
    }
}
