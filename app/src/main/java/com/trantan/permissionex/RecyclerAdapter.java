package com.trantan.permissionex;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private ArrayList<String> mImagePaths;

    public RecyclerAdapter(ArrayList<String> imagePaths) {
        mImagePaths = imagePaths;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setData(mImagePaths.get(i));
    }

    @Override
    public int getItemCount() {
        return mImagePaths == null ? 0 : mImagePaths.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
        }

        public void setData(String imagePath) {
            Glide.with(itemView.getContext()).load(imagePath).into(mImage);
        }
    }
}
