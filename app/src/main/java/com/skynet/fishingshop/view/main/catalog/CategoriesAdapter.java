package com.skynet.fishingshop.view.main.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryTileView> {

    @Override
    public CategoryTileView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_tile_view, parent, false);
        return new CategoryTileView(view);
    }

    @Override
    public void onBindViewHolder(final CategoryTileView holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public static class CategoryTileView extends RecyclerView.ViewHolder {

        public CategoryTileView(View view) {
            super(view);
        }
    }
}