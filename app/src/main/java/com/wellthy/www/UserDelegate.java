package com.wellthy.www;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wellthy.www.adapterdelegates.AdapterDelegate;
import com.wellthy.www.adapterdelegates.model.DisplayableItem;
import com.wellthy.www.base.BasePresenter;
import com.wellthy.www.custom_views.FontTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jimitpatel on 22/03/17.
 */

public class UserDelegate extends AdapterDelegate<List<DisplayableItem>> {

    protected LayoutInflater inflater;
    protected Activity activity;
    protected BasePresenter mPresenter;

    public UserDelegate(Activity activity, BasePresenter presenter) {
        this.activity = activity;
        inflater = activity.getLayoutInflater();
        mPresenter = presenter;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof PojoRealmClass || items.get(position) instanceof PojoSqliteClass;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new UserViewHolder(activity, inflater.inflate(R.layout.listitem_users, parent, false), mPresenter);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ((UserViewHolder) holder).onBindViewHolder(items, position, payloads);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        protected Activity activity;
        protected BasePresenter mPresenter;

        @BindView(R.id.lbl_user) FontTextView lblUser;
        @BindView(R.id.lbl_mobile) FontTextView lblMobile;

        public UserViewHolder(Activity activity, View itemView, BasePresenter presenter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.activity = activity;
            mPresenter = presenter;
        }

        protected void onBindViewHolder(@NonNull final List<DisplayableItem> items, int position, @NonNull List<Object> payloads) {
            if (items.get(position) instanceof PojoRealmClass) {
                PojoRealmClass data = (PojoRealmClass) items.get(position);
                lblUser.setText(data.userId);
                lblMobile.setText(data.mobile);
            } else if (items.get(position) instanceof PojoSqliteClass) {
                PojoSqliteClass data = (PojoSqliteClass) items.get(position);
                lblUser.setText(data.userId);
                lblMobile.setText(data.mobile);
            }
        }
    }
}
