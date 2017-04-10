package com.wellthy.www;

import android.content.Context;

import com.wellthy.www.adapterdelegates.model.DisplayableItem;
import com.wellthy.www.base.IRecyclerBaseViewToPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jimitpatel on 21/03/17.
 */

public interface IMvp {

    interface ViewToPresenter extends IRecyclerBaseViewToPresenter, Pojo {
        void onDestroy(boolean isConfigurationChanged);
        void setView(PresenterToView view);
        void setModel(PresenterToModel model);
        void setDelegates();

        void callApi();
    }

    interface PresenterToView extends IContext, ApiCallback, IMenuSelect {

    }

    interface PresenterToModel extends Pojo {
        void onDestroy(boolean isConfigurationChanged);
    }

    interface ModelToPresenter extends IContext, ApiCallback, IMenuSelect {

    }

    interface Pojo {
        List<DisplayableItem> getList();
        void setList(List<? extends DisplayableItem> objects);
        int getItemCount();
    }

    interface IMenuSelect {
        boolean isRealm();
    }

    interface IContext {
        Context getAppContext();
        Context getActivityContext();
    }

    interface ApiCallback {
        void notifyDatasetChanged();
        void display(String message);
    }

    @GET("users")
    Call<List<PojoRealmClass>> getUserListForRealm();

    @GET("users")
    Call<List<PojoSqliteClass>> getUserListForSqlite();
}
