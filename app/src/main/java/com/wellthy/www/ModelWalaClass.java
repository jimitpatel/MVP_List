package com.wellthy.www;

import com.wellthy.www.adapterdelegates.model.DisplayableItem;

import java.util.List;

/**
 * Created by jimitpatel on 21/03/17.
 */

public class ModelWalaClass implements IMvp.PresenterToModel {

    IMvp.ModelToPresenter mPresenter;
    List<DisplayableItem> objects;

    public ModelWalaClass(IMvp.ModelToPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroy(boolean isConfigurationChanged) {
        if (!ApplicationLevelClass.getInstance().getRealm().isClosed())
            ApplicationLevelClass.getInstance().getRealm().close();
    }

    @Override
    public List<DisplayableItem> getList() {
        return objects;
    }

    @Override
    public void setList(List<? extends DisplayableItem> objects) {
        this.objects = (List<DisplayableItem>) objects;
    }

    @Override
    public int getItemCount() {
        if (null == objects)
            return 0;
        return objects.size();
    }
}
