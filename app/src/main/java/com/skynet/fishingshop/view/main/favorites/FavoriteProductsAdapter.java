package com.skynet.fishingshop.view.main.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;

public class FavoriteProductsAdapter extends RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteProductView>{
    @NonNull
    @Override
    public FavoriteProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_product_tile_view, parent, false);
        return new FavoriteProductView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteProductView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class FavoriteProductView extends RecyclerView.ViewHolder {
        public FavoriteProductView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
