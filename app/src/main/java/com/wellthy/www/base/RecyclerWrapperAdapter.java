package com.wellthy.www.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimit Patel on 11/12/15.
 * <p>This is the wrapper class for all RecyclerAdapter to reduce the re-coding of certain repeated code</p>
 */
public abstract class RecyclerWrapperAdapter<E> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context context;
    protected List<E> objects;
    protected E e;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setObjects(List<E> objects) {
        this.objects = objects;
        notifyDataSetChanged();
        notifyItemRangeChanged(0, objects.size());
    }

    public void add(@NonNull E object) {
        objects.add(object);
        if (objects.size() > 1)
            notifyItemInserted(objects.size() - 1);
        else
            notifyDataSetChanged();
    }

    public void addAll(@NonNull ArrayList<E> object) {
        int positionStart = objects.size();
        if (positionStart > 0) {
            objects.addAll(object);
            notifyItemRangeInserted(positionStart, object.size());
        } else {
            objects = new ArrayList<>();
            objects.addAll(object);
            notifyDataSetChanged();
        }
    }

    public void add(int position, @NonNull E object) {
        if (position < objects.size() && position >= 0) {
            objects.add(position, object);
            notifyItemInserted(position);
        } else if (position >= objects.size()) {
            add(object);
        }
    }

    public void set(int position, @NonNull E object) {
        if (position < objects.size() && position >= 0) {
            objects.set(position, object);
            notifyItemChanged(position);
        } else if (position >= objects.size()) {
            add(object);
        }
    }

    public void remove(@NonNull E object) {
        objects.remove(object);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (position >=0 && position < objects.size()) {
            objects.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeAll() {
        if (getItemCount() > 0) {
            objects = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    public E getItem(int position) {
        if (position >= getItemCount() || 0 > position)
            return null;
        return objects.get(position);
    }

    public boolean sameClassAs(Object object) {
        if (null != objects && 0 < objects.size() && null != object) {
            e = objects.get(0);
            return object.getClass() == e.getClass();
        }
        throw new NullPointerException("Class not defined");
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
