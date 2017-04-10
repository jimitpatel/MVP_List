package com.wellthy.www;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link PresenterWalaImplClass}
 */
@RunWith(MockitoJUnitRunner.class)
public class PresenterWalaImplClass_Test {

    private static final String TAG = PresenterWalaImplClass_Test.class.getSimpleName();

    @Mock private IMvp.PresenterToView view;
    @Mock private IMvp.PresenterToModel model;
    @Mock private IMvp service;

    @Captor ArgumentCaptor<Call<List<PojoRealmClass>>> captorCall;
    @Captor ArgumentCaptor<Callback<List<PojoRealmClass>>> captorCallback;
    @Captor ArgumentCaptor<Response<List<PojoRealmClass>>> captorResponse;

    private PresenterWalaImplClass mPresenter;


    @Before
    public void setUpPresenter() {
        mPresenter = new PresenterWalaImplClass(view, service);
        mPresenter.setModel(model);
    }

    @Test
    public void checkForListPassedToViews() {
        List<PojoSqliteClass> list = new ArrayList<>();
        PojoSqliteClass pojo = new PojoSqliteClass();
        pojo.mobile = "1234567890";
        pojo.userId = "1";
        list.add(pojo);

        pojo = new PojoSqliteClass();
        pojo.mobile = "0987654321";
        pojo.userId = "2";
        list.add(pojo);

        pojo = new PojoSqliteClass();
        pojo.mobile = "5432167890";
        pojo.userId = "3";
        list.add(pojo);

        mPresenter.setList(list);
        verify(view, times(1)).notifyDatasetChanged();
    }

    @Test
    public void checkForEmptyListPassedToViews() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        List<PojoSqliteClass> list = new ArrayList<>();
        mPresenter.setList(list);
        verify(view, times(1)).display(captor.capture());
        Assert.assertThat(captor.getValue(), is(Constants.ERROR));
    }
}