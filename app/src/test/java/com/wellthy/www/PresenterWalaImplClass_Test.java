package com.wellthy.www;

import com.wellthy.www.adapterdelegates.AdapterDelegatesManager;
import com.wellthy.www.adapterdelegates.model.DisplayableItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link PresenterWalaImplClass}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { PresenterWalaImplClass.class })
public class PresenterWalaImplClass_Test {

    private static final String TAG = PresenterWalaImplClass_Test.class.getSimpleName();

    @Mock private PresenterWalaImplClass mPresenter;
    @Mock private Callback<List<PojoRealmClass>> callbackRealm;
    @Mock private Callback<List<PojoSqliteClass>> callbackSqlite;
    @Mock private Call<List<PojoRealmClass>> callRealm;
    @Mock private Call<List<PojoSqliteClass>> callSqlite;
    @Mock private IMvp.PresenterToModel model;
    @Mock private IMvp mockInterface;

    @Captor private ArgumentCaptor<Callback<List<PojoRealmClass>>> argumentCaptorRealm;

    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;

    @Before
    public void setUpPresenter() {
        model = mock(ModelWalaClass.class);
        delegatesManager = (AdapterDelegatesManager<List<DisplayableItem>>) mock(AdapterDelegatesManager.class);
        delegatesManager.addDelegate(mock(UserDelegate.class));

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void testRealmApi() throws Exception {
        mPresenter.setModel(model);
        mPresenter.setRealmCallback(callbackRealm);
        mPresenter.setRealmCall(callRealm);

        mPresenter.callForRealm();
        verify(mockInterface).getUserListForRealm().enqueue(argumentCaptorRealm.capture());
    }
}