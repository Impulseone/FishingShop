package com.skynet.fishingshop.view.main.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.CartProduct;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.orderConfirmation.OrderConfirmationFragment;
import com.skynet.fishingshop.view.main.productsList.ProductsListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductTileView> {

    private List<CartProduct> products;

    public CartProductsAdapter(List<CartProduct> products) {
        this.products = products;
    }

    @NotNull
    @Override
    public CartProductTileView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product_tile_view, parent, false);
        return new CartProductTileView(view,this, products);
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