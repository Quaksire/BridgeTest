package com.quaksire.boilerplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.quaksire.applog.LogManager;
import com.quaksire.boilerplate.adapter.PupilsAdapter;
import com.quaksire.boilerplate.interfaces.IMainActivity;
import com.quaksire.boilerplate.presenter.IMainActivityPresenter;
import com.quaksire.model.Pupil;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
        extends AppCompatActivity
        implements IMainActivity, PupilsAdapter.OnItemClickListener {

    private final String TAG = "APP-MainActivity";

    /**
     * Activity presenter
     */
    @Inject
    IMainActivityPresenter mPresenter;

    @Inject
    Picasso mPicasso;

    @BindView(R2.id.pupils)
    RecyclerView mPupilsRecyclerView;
    private PupilsAdapter mPupilsAdapter;

    @BindView(R2.id.error)
    TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogManager.i(TAG, "onCreate()");

        ButterKnife.bind(this);

        ((AppApplication) getApplication())
                .getActivityComponent(this)
                .inject(this);

        this.mPupilsAdapter = new PupilsAdapter(this.mPicasso, this);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.mPupilsRecyclerView.setLayoutManager(mLayoutManager);
        this.mPupilsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mPupilsRecyclerView.setAdapter(mPupilsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogManager.i(TAG, "onResume()");
        this.mPresenter.loadPage(1);
    }

    @Override
    public void onClick(Pupil pupil) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.PUPIL_ID, pupil.pupilId);
        MainActivity.this.finish();
        startActivity(intent);
    }

    @Override
    public void displayError(String message) {
        LogManager.i(TAG, "displayError()");
        LogManager.d(TAG, "displayError() - Message: " + message);
        this.mErrorTextView.setVisibility(View.VISIBLE);
        this.mPupilsRecyclerView.setVisibility(View.GONE);
        this.mErrorTextView.setText(message);
    }

    @Override
    public void displayItems(List<Pupil> pupilList) {
        LogManager.i(TAG, "displayItems()");
        LogManager.i(TAG, "displayItems() - Length: " + pupilList.size());
        this.mErrorTextView.setVisibility(View.GONE);
        this.mPupilsRecyclerView.setVisibility(View.VISIBLE);

        this.mPupilsAdapter.swapData(pupilList);
        this.mPupilsAdapter.notifyDataSetChanged();
    }
}
