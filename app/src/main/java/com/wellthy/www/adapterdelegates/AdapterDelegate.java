package com.wellthy.www.adapterdelegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jimitpatel on 27/12/16.
 */

public abstract class AdapterDelegate<T> {

    protected abstract boolean isForViewType(@NonNull T items, int position);

    @NonNull
    protected abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    protected abstract void onBindViewHolder(@NonNull T items, int position,
                                             @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads);

    protected void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {}

    protected boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return false;
    }

    protected void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {}

    protected void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {}
}
