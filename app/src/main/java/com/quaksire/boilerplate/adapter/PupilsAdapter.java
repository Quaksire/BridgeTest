package com.quaksire.boilerplate.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quaksire.applog.LogManager;
import com.quaksire.boilerplate.R;
import com.quaksire.model.Pupil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julio.
 */

public class PupilsAdapter extends RecyclerView.Adapter<PupilsViewHolder> {

    private final String TAG = "APP-PupilsAdapter";

    /**
     * List of pupils to display in the adapter
     */
    private List<Pupil> mPupils;

    /**
     * Image library
     */
    private Picasso mPicasso;

    /**
     * Item click callback interface
     */
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(Pupil pupil);
    }

    public PupilsAdapter(Picasso picasso, OnItemClickListener onItemClickListener) {
        this.mPicasso = picasso;
        this.mOnItemClickListener = onItemClickListener;
        this.mPupils = new ArrayList<>();
    }

    public void swapData(List<Pupil> pupilList) {
        this.mPupils = pupilList;
    }

    @Override
    public PupilsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pupils, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick((Pupil) view.getTag());
            }
        });

        return new PupilsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PupilsViewHolder holder, int position) {
        Pupil pupil = mPupils.get(position);

        holder.mView.setTag(pupil);

        holder.mNameTextView.setText(pupil.name);
        holder.mCountryTextView.setText(pupil.country);
        LogManager.d(TAG, "Url: " + pupil.image);

        //Avoid same image gets duplicated when scrolling
        holder.mImage.setImageBitmap(null);
        this.mPicasso.cancelRequest(holder.mImage);

        this.mPicasso.load(pupil.image.replaceAll(" ", "%20"))
                .resize(200, 200)
                .centerCrop()
                .config(Bitmap.Config.RGB_565)
                .error(R.drawable.error)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return this.mPupils.size();
    }
}
