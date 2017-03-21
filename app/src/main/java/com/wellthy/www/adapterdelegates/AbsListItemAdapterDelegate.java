package com.wellthy.www.adapterdelegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jimitpatel on 27/12/16.
 */

public abstract class AbsListItemAdapterDelegate<I extends T, T, VH extends RecyclerView.ViewHolder> extends AdapterDelegate<List<T>> {

    @Override
    protected boolean isForViewType(@NonNull List<T> items, int position) {
        return isForViewType(items, position);
    }

    @NonNull
    @Override
    protected abstract VH onCreateViewHolder(ViewGroup parent);

    @Override
    protected void onBindViewHolder(@NonNull List<T> items, int position, @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {
        onBindViewHolder((I) items.get(position), (VH) holder, payloads);
    }

    protected abstract void onBindViewHolder(@NonNull I item, @NonNull VH viewHolder, @NonNull List<Object> payloads);

    protected abstract boolean isForViewType(@NonNull T item, @NonNull List<T> items, int position);
}
