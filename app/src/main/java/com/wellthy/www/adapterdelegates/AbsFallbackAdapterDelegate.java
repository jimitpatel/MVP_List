package com.wellthy.www.adapterdelegates;

import android.support.annotation.NonNull;

/**
 * Basically this class is never needed as so many view types won't be there in practical scenario
 *
 * Created by jimitpatel on 27/12/16.
 */
public abstract class AbsFallbackAdapterDelegate<T> extends AdapterDelegate<T> {

    @Override
    protected boolean isForViewType(@NonNull T items, int position) {
        return true;
    }
}
