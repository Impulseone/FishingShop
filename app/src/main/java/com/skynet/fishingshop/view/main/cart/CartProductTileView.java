package com.skynet.fishingshop.view.main.cart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.CartProduct;

public class CartProductTileView extends RecyclerView.ViewHolder {

    private CartProduct cartProduct;

    public CartProductTileView(@NonNull View itemView) {
        super(itemView);
    }

    public void setView(CartProduct cartProduct) {
        this.cartProduct = cartProduct;
        setImage();
        setPrice();
        setProductName();
        setProductDescription();
        setProductCount();
    }

    private void setImage() {
        ImageView imageView = itemView.findViewById(R.id.product_image);
        Glide.with(itemView.getContext()).load(cartProduct.imagePath).into(imageView);
    }

    private void setPrice() {
        ((TextView) itemView.findViewById(R.id.product_price)).setText(cartProduct.price + " руб.");
    }

    private void setProductName(){
        ((TextView) itemView.findViewById(R.id.product_name)).setText(cartProduct.name);
    }

    private void setProductDescription(){
        ((TextView) itemView.findViewById(R.id.product_description)).setText(cartProduct.description);
    }

    private void setProductCount(){
        ((TextView) itemView.findViewById(R.id.product_count)).setText(cartProduct.count+" шт.");
    }
}
