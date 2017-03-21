package com.wellthy.www;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wellthy.www.adapterdelegates.AdapterDelegatesManager;
import com.wellthy.www.adapterdelegates.model.DisplayableItem;
import com.wellthy.www.base.BasePresenter;
import com.wellthy.www.utils.DBHelper;
import com.wellthy.www.utils.NetworkUtils;
import com.wellthy.www.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jimitpatel on 21/03/17.
 */

public class PresenterWalaImplClass extends BasePresenter<IMvp.PresenterToView>
        implements IMvp.ViewToPresenter, IMvp.ModelToPresenter {

    private static final String TAG = PresenterWalaImplClass.class.getSimpleName();

    private IMvp.PresenterToModel model;
    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;

    public PresenterWalaImplClass(IMvp.PresenterToView view) {
        super(view);

        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new UserDelegate((Activity) getActivityContext(), this));
    }

    @Override
    public void onDestroy(boolean isConfigurationChanged) {
        mView = null;
        model.onDestroy(isConfigurationChanged);
        if (!isConfigurationChanged)
            model = null;
    }

    @Override
    public void setView(IMvp.PresenterToView view) {
        super.setView(view);
    }

    @Override
    public void setModel(IMvp.PresenterToModel model) {
        this.model = model;
    }

    @Override
    public void callApi() {
        try {
            setList(new ArrayList<DisplayableItem>());
            notifyDatasetChanged();
            if (isRealm()) {
                // Storing in realm selected
                setList(PojoRealmClass.getListFromRealm());
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "callApi: realm wala=" + getItemCount());
                if (getItemCount() > 0) getView().notifyDatasetChanged();
                else {
                    callForRealm();
                }
            } else {
                // Storing in sql selected
                DBHelper dbHelper = new DBHelper(getActivityContext());
                setList(dbHelper.getUserList());
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "callApi: sql wala=" + getItemCount());
                if (getItemCount() > 0) getView().notifyDatasetChanged();
                else {
                    callForSqlite();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(model.getList(), position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(model.getList(), position, holder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        delegatesManager.onBindViewHolder(model.getList(), position, holder, payloads);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state, Drawable drawable) {

    }

    @Override
    public int getItemCount() {
        return model.getItemCount();
    }

    @Override
    public List<DisplayableItem> getList() {
        return model.getList();
    }

    @Override
    public void setList(List<? extends DisplayableItem> objects) {
        model.setList(objects);
    }

    @Override
    public Context getAppContext() {
        return getView().getAppContext();
    }

    @Override
    public Context getActivityContext() {
        return getView().getActivityContext();
    }

    @Override
    public void notifyDatasetChanged() {
        getView().notifyDatasetChanged();
    }

    @Override
    public boolean isRealm() {
        return getView().isRealm();
    }

    private void callForRealm() throws Exception {
        if (NetworkUtils.haveNetworkConnection(getActivityContext())) {
            IMvp client = RetrofitUtils.createService(IMvp.class);
            Callback<List<PojoRealmClass>> callback = new Callback<List<PojoRealmClass>>() {
                @Override
                public void onResponse(Call<List<PojoRealmClass>> call, Response<List<PojoRealmClass>> response) {
                    if (response.isSuccessful()) {
                        List<PojoRealmClass> list = response.body();
                        if (null != list) PojoRealmClass.writeToRealm(list);
                        setList(list);
                        if (model.getItemCount() > 0) getView().notifyDatasetChanged();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onResponse: realm API=" + getItemCount());
                    }
                }

                @Override
                public void onFailure(Call<List<PojoRealmClass>> call, Throwable t) {
                    t.printStackTrace();
                }
            };

            Call<List<PojoRealmClass>> call = client.getUserListForRealm();
            call.enqueue(callback);
        } else {
            Toast.makeText(getAppContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void callForSqlite() throws Exception {
        if (NetworkUtils.haveNetworkConnection(getActivityContext())) {
            Log.d(TAG, "callApi: ");
            IMvp client = RetrofitUtils.createService(IMvp.class);
            Callback<List<PojoSqliteClass>> callback = new Callback<List<PojoSqliteClass>>() {
                @Override
                public void onResponse(Call<List<PojoSqliteClass>> call, Response<List<PojoSqliteClass>> response) {
                    if (response.isSuccessful()) {
                        List<PojoSqliteClass> list = response.body();
                        DBHelper dbHelper = new DBHelper(getActivityContext());
                        int deleteCount = dbHelper.deleteAllRecords();
                        int count = dbHelper.addUsers(list);
                        setList(list);
                        if (model.getItemCount() > 0) getView().notifyDatasetChanged();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onResponse: sqlite API count=" + count + ", delete=" + deleteCount);
                    }
                }

                @Override
                public void onFailure(Call<List<PojoSqliteClass>> call, Throwable t) {
                    t.printStackTrace();
                }
            };

            Call<List<PojoSqliteClass>> call = client.getUserListForSqlite();
            call.enqueue(callback);
        } else {
            Toast.makeText(getAppContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }
}
