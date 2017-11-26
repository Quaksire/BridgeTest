package com.quaksire.boilerplate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quaksire.boilerplate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Julio.
 */

class PupilsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView mNameTextView;

    @BindView(R.id.country)
    TextView mCountryTextView;

    @BindView(R.id.image)
    ImageView mImage;

    View mView;

    PupilsViewHolder(View view) {
        super(view);
        this.mView = view;

        ButterKnife.bind(this, view);
    }

}
