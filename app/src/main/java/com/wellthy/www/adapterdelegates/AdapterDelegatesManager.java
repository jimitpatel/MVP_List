package com.wellthy.www.adapterdelegates;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by jimitpatel on 27/12/16.
 */

public class AdapterDelegatesManager<T> {

    static final int FALLBACK_DELEGATE_VIEW_TYPE = Integer.MAX_VALUE - 1;
    private static final List<Object> PAYLOADS_EMPTY_LIST = Collections.emptyList();

    protected SparseArrayCompat<AdapterDelegate<T>> delegates = new SparseArrayCompat<>();
    protected AdapterDelegate<T> fallbackDelegate;

    public AdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate) {
        // algorithm could be improved since there could be holes,
        // but it's very unlikely that we reach Integer.MAX_VALUE and run out of unused indexes

        int viewType = delegates.size();
        while (delegates.get(viewType) != null) {
            viewType++;
            if (FALLBACK_DELEGATE_VIEW_TYPE == viewType) {
                throw new IllegalArgumentException("Oops, we are very close to Integer.MAX_VALUE. It seems that there are no more free and unused view type integers left to add another AdapterDelegate.");
            }
        }
        return addDelegate(viewType, false, delegate);
    }

    public AdapterDelegatesManager<T> addDelegate(int viewType, @NonNull AdapterDelegate<T> delegate) {
        return addDelegate(viewType, false, delegate);
    }

    public AdapterDelegatesManager<T> addDelegate(int viewType, boolean allowReplacingDelegate, @NonNull AdapterDelegate<T> delegate) {
        if (null == delegate)
            throw new NullPointerException("AdapterDelegate is null");

        if (FALLBACK_DELEGATE_VIEW_TYPE == viewType)
            throw new IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered AdapterDelegate is "
                            + delegates.get(viewType));

        delegates.put(viewType, delegate);
        return this;
    }

    public AdapterDelegatesManager<T> removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }

    public int getItemViewType(@NonNull T items, int position) {
        if (null == items)
            throw new NullPointerException("Items datasource is null");

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            AdapterDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(items, position))
                return delegates.keyAt(i);
        }

        if (null != fallbackDelegate)
            return FALLBACK_DELEGATE_VIEW_TYPE;

        throw new NullPointerException("No AdapterDelegate added that matches the position=" + position + " in data source");
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate<T> delegate = getDelegateForViewType(viewType);
        if (null == delegate) {
            throw new NullPointerException("No AdapterDelegate added for ViewType " + viewType);
        }

        RecyclerView.ViewHolder vh = delegate.onCreateViewHolder(parent);
        if (null == vh) {
            throw new NullPointerException("ViewHolder returned from AdapterDelegate "
                    + delegate
                    + " for ViewType ="
                    + viewType
                    + " is null!");
        }
        return vh;
    }

    public void onBindViewHolder(@NonNull T items, int position,
                                 @NonNull RecyclerView.ViewHolder viewHolder, @Nullable List payloads) {

        AdapterDelegate<T> delegate = getDelegateForViewType(viewHolder.getItemViewType());
        if (null == delegate) {
            throw new NullPointerException("No delegate found for item at position = "
                    + position
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        delegate.onBindViewHolder(items, position, viewHolder, payloads);
    }

    public void onBindViewHolder(@NonNull T items, int position,
                                 @NonNull RecyclerView.ViewHolder viewHolder) {
        onBindViewHolder(items, position, viewHolder, PAYLOADS_EMPTY_LIST);
    }

    public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(viewHolder.getItemViewType());
        if (null == delegate) {
            throw new NullPointerException("No delegate found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        delegate.onViewRecycled(viewHolder);
    }

    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder viewHolder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(viewHolder.getItemViewType());
        if (null == delegate) {
            throw new NullPointerException("No delegate found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        return delegate.onFailedToRecycleView(viewHolder);
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(viewHolder.getItemViewType());
        if (null == delegate) {
            throw new NullPointerException("No delegate found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        delegate.onViewAttachedToWindow(viewHolder);
    }

    public void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(viewHolder.getItemViewType());
        if (null == delegate) {
            throw new NullPointerException("No delegate found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        delegate.onViewDetachedFromWindow(viewHolder);
    }

    public AdapterDelegatesManager<T> setFallbackDelegate(@Nullable AdapterDelegate<T> fallbackDelegate) {
        this.fallbackDelegate = fallbackDelegate;
        return this;
    }

    public int getViewType(@NonNull AdapterDelegate<T> delegate) {
        if (null == delegate) {
            throw new NullPointerException("Delegate is null");
        }

        int index = delegates.indexOfValue(delegate);
        if (index == -1) {
            return -1;
        }
        return delegates.keyAt(index);
    }

    @Nullable
    public AdapterDelegate<T> getDelegateForViewType(int viewType) {
        AdapterDelegate<T> delegate = delegates.get(viewType);
        if (null == delegate) {
            if (fallbackDelegate == null) {
                return null;
            } else {
                return fallbackDelegate;
            }
        }

        return delegate;
    }

    @Nullable
    public AdapterDelegate<T> getFallbackDelegate() {
        return fallbackDelegate;
    }

    public int getSize() {
        return delegates.size();
    }
}
