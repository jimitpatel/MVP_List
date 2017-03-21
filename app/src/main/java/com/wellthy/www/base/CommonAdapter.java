package com.wellthy.www.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wellthy.www.adapterdelegates.model.DisplayableItem;

import java.util.List;

/**
 * Created by jimitpatel on 23/02/17.
 */
public class CommonAdapter extends RecyclerWrapperAdapter<DisplayableItem> {

    IRecyclerBaseViewToPresenter mPresenter;

    public CommonAdapter(IRecyclerBaseViewToPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        return mPresenter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mPresenter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mPresenter.onBindViewHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        mPresenter.onBindViewHolder(holder, position, payloads);
    }
}