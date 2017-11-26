package com.quaksire.boilerplate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.quaksire.boilerplate.interfaces.IDetailActivity;
import com.quaksire.boilerplate.presenter.IDetailActivityPresenter;
import com.quaksire.model.Pupil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity
        extends AppCompatActivity
        implements IDetailActivity, View.OnClickListener {

    public static String PUPIL_ID = "PUPIL_ID";

    /**
     * Activity presenter
     */
    @Inject
    IDetailActivityPresenter mPresenter;

    @Inject
    Picasso mPicasso;

    @BindView(R.id.name)
    EditText mNameEditText;

    @BindView(R.id.country)
    EditText mCountryEditText;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int mPupilId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        ((AppApplication) getApplication())
                .getDetailComponent(this)
                .inject(this);

        this.mPupilId = getIntent().getIntExtra(PUPIL_ID, 0);

        setSupportActionBar(this.mToolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mPresenter.loadPupilId(this.mPupilId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            exitDetailsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            this.mPresenter.savePupil(
                    this.mNameEditText.getText().toString(),
                    this.mCountryEditText.getText().toString());
            Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.delete)
    public void deletePupil() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_pupil);
        builder.setMessage(R.string.delete_pupil_message);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.deletePupil();
                exitDetailsActivity();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void exitDetailsActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        DetailActivity.this.finish();
        startActivity(intent);
    }

    @Override
    public void displayPupil(Pupil pupil) {
        this.mNameEditText.setText(pupil.name);
        this.mCountryEditText.setText(pupil.country);

        this.mToolbar.setTitle(pupil.name);
    }

    @Override
    public void displayError(String error) {
        Snackbar.make(this.mNameEditText, error, Snackbar.LENGTH_LONG).show();
    }
}
