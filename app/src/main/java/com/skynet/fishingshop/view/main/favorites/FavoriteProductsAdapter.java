package com.skynet.fishingshop.view.main.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.FavoritesProduct;

import java.util.List;

public class FavoriteProductsAdapter extends RecyclerView.Adapter<FavoriteProductTileView> {

    private final List<FavoritesProduct> products;

    public FavoriteProductsAdapter(List<FavoritesProduct> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public FavoriteProductTileView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_product_tile_view, parent, false);
        return new FavoriteProductTileView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteProductTileView holder, int position) {
        holder.setView(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
