package com.skynet.fish_shop.view.main.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.CartProduct;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductTileView> {

    private List<CartProduct> products;
    private CartFragment.ChangePrice changePrice;

    public CartProductsAdapter(List<CartProduct> products, CartFragment.ChangePrice changePrice) {
        this.products = products;
        this.changePrice = changePrice;
    }

    @NotNull
    @Override
    public CartProductTileView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product_tile_view, parent, false);
        return new CartProductTileView(view,this, products, changePrice);
    }

    @Override
    public void onBindViewHolder(final CartProductTileView holder, int position) {
        holder.setView(products.get(position),position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}