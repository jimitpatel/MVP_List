package com.wellthy.www;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.wellthy.www.base.CommonAdapter;
import com.wellthy.www.base.StateMaintainer;
import com.wellthy.www.custom_views.font.CustomTypefaceSpan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMvp.PresenterToView {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    IMvp.ViewToPresenter mPresenter;
    CommonAdapter mAdapter;
    boolean isRealm = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.way_to_store, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.realm: {
                isRealm = true;
                mPresenter.callApi();
                break;
            }
            case R.id.sqlite: {
                isRealm = false;
                mPresenter.callApi();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupMVP();
        initViews();
        mPresenter.callApi();
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void notifyDatasetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    private void setupMVP() {
        // Check if StateMaintainer has been created
        StateMaintainer mStateMaintainer = new StateMaintainer(MainActivity.class.getName(), getSupportFragmentManager());
        if (mStateMaintainer.firstTimeIn()) {
            // Create the Presenter
            PresenterWalaImplClass presenter = new PresenterWalaImplClass(this);

            // Create the Model
            ModelWalaClass model = new ModelWalaClass(presenter);

            // Set Presenter model
            presenter.setModel(model);

            // Set the presenter as interface to limit the communication with it
            mPresenter = presenter;
        } else {
            // Get The Presenter from StateMaintainer
            mPresenter = mStateMaintainer.get(MainActivity.class.getName());
            // Update the view in presenter
            mPresenter.setView(this);
        }
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        SpannableString toolbarTitle = new SpannableString(toolbar.getTitle());
        String toolbarFont = getResources().getString(R.string.circular_bold);
        CustomTypefaceSpan toolbarTypefaceSpan = new CustomTypefaceSpan(toolbarFont, this);
        toolbarTitle.setSpan(toolbarTypefaceSpan, 0, toolbarTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        toolbar.setTitle(toolbarTitle);

        mAdapter = new CommonAdapter(mPresenter);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean isRealm() {
        return isRealm;
    }
}
