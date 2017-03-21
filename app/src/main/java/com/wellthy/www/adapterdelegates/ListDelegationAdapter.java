package com.wellthy.www.adapterdelegates;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by jimitpatel on 27/12/16.
 */

public class ListDelegationAdapter<T extends List<?>> extends AbsDelegationAdapter<T> {

    public ListDelegationAdapter() {
    }

    public ListDelegationAdapter(@NonNull AdapterDelegatesManager<T> delegatesManager) {
        super(delegatesManager);
    }

    @Override
    public int getItemCount() {
        return null == items ? 0 : items.size();
    }
}
